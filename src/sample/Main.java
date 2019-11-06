package sample;

import io.reactivex.disposables.Disposable;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import jboy.disassembler.Disassembler;
import jboy.other.GameBoyInfo;
import jboy.system.Display;
import jboy.system.GameBoy;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main extends Application {
    private Stage stage;
    private ListView<String> listView = new ListView<>();
    private GameBoy gameBoy;
    private GraphicsContext graphicsContext;
    private PixelWriter pixelWriter;
    private PixelFormat<ByteBuffer> pixelFormat;

    private Thread gameThread;
    private Disposable debugInfo;
    private Disposable displaySubscription;
    private Disposable gpuSubscription;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        primaryStage.setTitle("JBoy");

        VBox vbox = new VBox();
        MenuBar menuBar = createMenuBar();
        Canvas canvas = new Canvas(Display.WIDTH, Display.HEIGHT);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.pixelWriter = this.graphicsContext.getPixelWriter();
        this.pixelFormat = PixelFormat.getByteBgraInstance();

        vbox.getChildren().add(menuBar);
        vbox.getChildren().add(canvas);

        // TODO: gotta figure out how to size the scene
        Scene scene = new Scene(vbox, Display.WIDTH, Display.HEIGHT + 29);

        primaryStage.setScene(scene);
        primaryStage.show();
        this.gameBoy = new GameBoy();
    }

    @Override
    public void stop() throws Exception {
        this.dispose();

        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private MenuBar createMenuBar() {
        MenuBar mbMenu = new MenuBar();

        mbMenu.getMenus().add(createFileMenu());
        mbMenu.getMenus().add(createActionsMenu());

        return mbMenu;
    }

    private Menu createFileMenu() {
        Menu mFile = new Menu("File");
        MenuItem miLoadRom = new MenuItem("Load Rom...");

        miLoadRom.setOnAction(x -> {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(this.stage);

            if(file != null) {
                this.loadRom(file);
            }
        });

        mFile.getItems().add(miLoadRom);

        return mFile;
    }

    private Menu createActionsMenu() {
        Menu mActions = new Menu("Actions");
        MenuItem miDisassemble = new MenuItem("Disassemble...");
        MenuItem miDebug = new MenuItem("Show debugger");

        miDisassemble.setOnAction(x -> {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(this.stage);

            if(file != null) {
                this.disassemble(file);
            }
        });

        miDebug.setOnAction(x -> {
            this.openDebugWindow();
        });

        mActions.getItems().add(miDisassemble);
        mActions.getItems().add(miDebug);

        return mActions;
    }

    private void dispose() {
        if(this.gameThread != null) {
            this.gameThread.interrupt();
        }

        if(this.debugInfo != null) {
            this.debugInfo.dispose();
        }

        if(this.displaySubscription != null) {
            this.displaySubscription.dispose();
        }

        if(this.gpuSubscription != null) {
            this.gpuSubscription.dispose();
        }

        this.gameBoy.unsubscribe();
    }

    private void loadRom(File file) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(file.getPath()));
            int[] rom = new int[bytes.length];

            for(int i = 0; i < bytes.length; i++) {
                rom[i] = bytes[i] & 0xFF;
            }

            this.displaySubscription = this.gameBoy.getDisplay().subscribe(this::drawImage);
            this.gpuSubscription = this.gameBoy.getGpu().subscribe(fps -> {
                Platform.runLater(() -> this.stage.setTitle("JBoy | " + fps.toString()));
            });

            this.gameBoy.loadROM(rom);

            this.gameThread = new Thread(this.gameBoy);
            this.gameThread.start();

            System.out.println(this.gameBoy.getCartridgeInfo());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawImage(byte[] data) {
        this.pixelWriter.setPixels(
                0,
                0,
                Display.WIDTH,
                Display.HEIGHT,
                this.pixelFormat,
                data,
                0,
                Display.WIDTH * 4
        );
    }

    private void disassemble(File file) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(file.getPath()));
            Disassembler disassembler = new Disassembler(bytes);
            disassembler.disassemble();

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    ArrayList<String> list = disassembler.getDisassemblyList();

                    for(var item : list) {
                        listView.getItems().add(item);
                    }

                    return null;
                }
            };

            new Thread(task).start();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void openDebugWindow() {
        DebugWindow dbgWindow = new DebugWindow();

        dbgWindow.createRegisters();
        dbgWindow.createFlagCheckboxes();
        dbgWindow.createCpuControls(this.gameBoy);
        dbgWindow.createBreakpointControls(this.gameBoy);

        Scene debugScene = new Scene(dbgWindow.getLayout());
        Stage debugWindow = new Stage();

        debugWindow.setTitle("Debugger");
        debugWindow.setScene(debugScene);
        debugWindow.setHeight(400);
        debugWindow.setWidth(400);

        debugWindow.setX(Screen.getPrimary().getVisualBounds().getWidth() / 2);
        debugWindow.setY(Screen.getPrimary().getVisualBounds().getHeight() / 2);

        this.gameBoy.setIsDebugging(true);

        GameBoyInfo gbInfo = this.gameBoy.getInfo();
        this.debugInfo = gbInfo.subscribe(info -> {
            Platform.runLater(() -> {
                dbgWindow.updateWindow(info);
            });
        });

        this.gameBoy.resetCpu();
        debugWindow.show();
    }
}
