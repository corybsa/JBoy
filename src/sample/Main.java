package sample;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jboy.disassembler.Disassembler;
import jboy.system.CPU;
import jboy.system.GameBoy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main extends Application {
    private Stage stage;
    private ListView<String> listView;
    private GameBoy gameBoy;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.stage = primaryStage;
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("JBoy");

        VBox vbox = new VBox();
        MenuBar menuBar = createMenuBar();
        this.listView = new ListView<>();

        vbox.getChildren().add(menuBar);
        vbox.getChildren().add(this.listView);
        Scene scene = new Scene(vbox, 300, 275);

        primaryStage.setScene(scene);
        primaryStage.show();
        this.gameBoy = new GameBoy();
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

        miDisassemble.setOnAction(x -> {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(this.stage);

            if(file != null) {
                this.disassemble(file);
            }
        });

        mActions.getItems().add(miDisassemble);

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
}
