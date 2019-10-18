package sample;

import io.reactivex.Observable;
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
import javafx.scene.image.WritableImage;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {
    private Stage stage;
    private ListView<String> listView;
    private GameBoy gameBoy;
    private GraphicsContext graphicsContext;
    private Thread gameThread;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.stage = primaryStage;
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("JBoy");

        VBox vbox = new VBox();
        MenuBar menuBar = createMenuBar();
        this.listView = new ListView<>();

        vbox.getChildren().add(menuBar);
        Canvas canvas = new Canvas();
        this.graphicsContext = canvas.getGraphicsContext2D();
//        gc.getPixelWriter().setPixels();
//        this.vbox.getChildren().add(this.listView);
        vbox.getChildren().add(canvas);
        Scene scene = new Scene(vbox, 300, 275);

        primaryStage.setScene(scene);
        primaryStage.show();
        this.gameBoy = new GameBoy();
    }

    @Override
    public void stop() throws Exception {
        this.gameThread.interrupt();
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

            int[] nintendo = this.gameBoy.getNintendo();
            System.out.println(Arrays.toString(nintendo));
            WritableImage img = new WritableImage(100, 100);
            PixelWriter pw = this.graphicsContext.getPixelWriter();
            pw.setPixels(0, 0, 100, 100, PixelFormat.getIntArgbInstance(), nintendo, 0, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        GridPane layout = new GridPane();
        Label pc = new Label("PC: 0");
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
                pc.setText(Integer.toString(info.getCpuInfo().getCpu().getPC(), 16));
            });
        });
        /*Observable<GameBoyInfo> stream = Observable.create(subscriber -> {
            while(true) {
                if(subscriber.isDisposed()) {
                    break;
                }

                subscriber.onNext(gbInfo);
            }
        });

        stream.subscribe(
                info -> {
                    System.out.println(info.getCpuInfo().getCpu().getPC());
                },
                err -> {},
                () -> System.out.println("Complete")
        );*/

        layout.add(pc, 0, 0);

        debugWindow.show();
    }
}
