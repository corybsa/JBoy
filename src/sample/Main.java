package sample;

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
import jboy.system.GameBoy;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main extends Application {
    private Stage stage;
    private ListView<String> listView;
    private GameBoy gameBoy;
    private Thread gameThread;

    private GraphicsContext graphicsContext;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.stage = primaryStage;
        primaryStage.setTitle("JBoy");

        VBox vbox = new VBox();
        MenuBar menuBar = createMenuBar();
        Canvas canvas = new Canvas(160, 144);
        this.graphicsContext = canvas.getGraphicsContext2D();

        vbox.getChildren().add(menuBar);


        vbox.getChildren().add(canvas);

        Scene scene = new Scene(vbox, 160, 144);

        primaryStage.setScene(scene);
        primaryStage.show();
        this.gameBoy = new GameBoy();
//        byte[] data = this.createImageData();
//        this.drawImage(data);
    }

    @Override
    public void stop() throws Exception {
        if(this.gameThread != null) {
            this.gameThread.interrupt();
        }

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

            this.gameBoy.loadROM(rom);

            gameThread = new Thread(this.gameBoy);
            gameThread.start();

            System.out.println(this.gameBoy.getCartridgeInfo());

//            this.drawImage(nintendo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] createImageData() {
        System.out.println("Creating image data.");
        byte[] data = new byte[300];

        int i = 0;

        for (int y = 0; y < 10; y++) {
            int r = y * 255 / 10;

            for (int x = 0; x < 10; x++) {
                int g = x * 255 / 10;
                data[i] = (byte)r;
                data[i + 1] = (byte)g;
                i += 3;
            }
        }

        System.out.println("Done.");
        return data;
    }

    private void drawImage(byte[] data) {
        System.out.println("Drawing image.");
        int rows = data.length / 16;
        int stragglers = data.length % 16;
        rows = (int)(rows + Math.ceil(stragglers / 16.0));

        PixelWriter pw = this.graphicsContext.getPixelWriter();
        PixelFormat<ByteBuffer> pf = PixelFormat.getByteRgbInstance();

        for(int y = 0; y < 100; y += 10) {
            for(int x = 0; x < 100; x += 10) {
                pw.setPixels(x, y, 10, 10, pf, data, 0, 30);
            }
        }

        System.out.println("Done.");
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
        Label a = new Label("A: 0x00");
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
        gbInfo.subscribe(info -> {
            Platform.runLater(() -> {
                pc.setText("PC: 0x" + Integer.toString(info.getCpuInfo().getCpu().getPC(), 16));
                a.setText("A: 0x" + Integer.toString(info.getCpuInfo().getCpu().getA(), 16));
                hl.setText("HL: 0x" + Integer.toString(info.getCpuInfo().getCpu().getHL(), 16));
            });
        });

        layout.add(pc, 0, 0);
        layout.add(a, 0, 1);
        layout.add(hl, 0, 2);

        debugWindow.show();
    }
}
