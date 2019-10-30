package sample;

import io.reactivex.disposables.Disposable;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.GridPane;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        if(this.gameThread != null) {
            this.gameThread.interrupt();
        }

        if(this.debugInfo != null) {
            this.debugInfo.dispose();
        }

        this.displaySubscription.dispose();

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
        MenuItem miDebug = new MenuItem("Debug");

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

    private void loadRom(File file) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(file.getPath()));
            int[] rom = new int[bytes.length];

            for(int i = 0; i < bytes.length; i++) {
                rom[i] = bytes[i] & 0xFF;
            }

            this.displaySubscription = this.gameBoy.getDisplay().subscribe(this::drawImage);

            this.gameBoy.loadROM(rom);

            gameThread = new Thread(this.gameBoy);
            gameThread.start();

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
                Display.WIDTH / 4
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
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(this.stage);

        if(file != null) {
            this.loadRom(file);
        }

        GridPane layout = new GridPane();
        Label pc = new Label("PC: 0x100");
        Label af = new Label("AF: 0x0000");
        Label bc = new Label("BC: 0x0000");
        Label de = new Label("DE: 0x0000");
        Label hl = new Label("HL: 0x0000");
        Scene debugScene = new Scene(layout);
        Stage debugWindow = new Stage();

        debugWindow.setTitle("Debugger");
        debugWindow.setScene(debugScene);
        debugWindow.setHeight(200);
        debugWindow.setWidth(200);

        debugWindow.setX(Screen.getPrimary().getVisualBounds().getWidth() / 2);
        debugWindow.setY(Screen.getPrimary().getVisualBounds().getHeight() / 2);

        GameBoyInfo gbInfo = this.gameBoy.getInfo();
        this.debugInfo = gbInfo.subscribe(info -> {
            Platform.runLater(() -> {
                StringBuilder sb = new StringBuilder("PC: 0x");
                sb.append(String.format("%4s", Integer.toString(info.getCpuInfo().getCpu().getPC(), 16).toUpperCase()).replace(" ", "0"));
                pc.setText(sb.toString());
                sb.delete(0, sb.length());

                sb.append("AF: 0x").append(String.format("%4s", Integer.toString(info.getCpuInfo().getCpu().getAF(), 16).toUpperCase()).replace(" ", "0"));
                af.setText(sb.toString());
                sb.delete(0, sb.length());

                sb.append("BC: 0x").append(String.format("%4s", Integer.toString(info.getCpuInfo().getCpu().getBC(), 16).toUpperCase()).replace(" ", "0"));
                bc.setText(sb.toString());
                sb.delete(0, sb.length());

                sb.append("DE: 0x").append(String.format("%4s", Integer.toString(info.getCpuInfo().getCpu().getDE(), 16).toUpperCase()).replace(" ", "0"));
                de.setText(sb.toString());
                sb.delete(0, sb.length());

                sb.append("HL: 0x").append(String.format("%4s", Integer.toString(info.getCpuInfo().getCpu().getHL(), 16).toUpperCase()).replace(" ", "0"));
                hl.setText(sb.toString());
            });
        });

        layout.add(pc, 0, 0);
        layout.add(af, 0, 1);
        layout.add(bc, 0, 2);
        layout.add(de, 0, 3);
        layout.add(hl, 0, 4);

        debugWindow.show();
    }
}
