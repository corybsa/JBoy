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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import jboy.disassembler.Disassembler;
import jboy.other.GameBoyInfo;
import jboy.system.CPU;
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

    // TODO: definitely need to refactor this when I'm done.
    private void openDebugWindow() {
        GridPane layout = new GridPane();
        Label pc = new Label("PC: 0x100");
        Label sp = new Label("SP: 0xFFFE");
        Label af = new Label("AF: 0x0000");
        Label bc = new Label("BC: 0x0000");
        Label de = new Label("DE: 0x0000");
        Label hl = new Label("HL: 0x0000");
        Label interruptFlags = new Label("IF: 0x00");
        Label interruptEnable = new Label("IE: 0x00");
        Label ime = new Label("IME: off");
        Label lcdc = new Label("LCDC: 0x00");

        CheckBox z = new CheckBox("Z") {
            @Override
            public void arm() {
                // nothing.
            }
        };

        CheckBox n = new CheckBox("N") {
            @Override
            public void arm() {
                // nothing.
            }
        };

        CheckBox h = new CheckBox("H") {
            @Override
            public void arm() {
                // nothing.
            }
        };

        CheckBox c = new CheckBox("C") {
            @Override
            public void arm() {
                // nothing.
            }
        };

        Button tick = new Button("Tick");
        tick.setOnAction(x -> this.gameBoy.tick());

        Button run = new Button("Run to breakpoint");
        run.setOnAction(x -> this.gameBoy.runToBreakpoint());

        Button reset = new Button("Reset");
        reset.setOnAction(x -> this.gameBoy.resetCpu());

        Label lblBreakpoint = new Label("Enter a breakpoint: ");
        TextField tfBreakpoint = new TextField();

        Label lblBreakpoints = new Label("Breakpoints:");
        ListView<String> breakpoints = new ListView<>();

        Button addBreakpoint = new Button("Add breakpoint");
        addBreakpoint.setOnAction(x -> {
            String text = tfBreakpoint.getText();

            if(!text.isEmpty() && !text.isBlank()) {
                this.gameBoy.addBreakpoint(Integer.parseInt(tfBreakpoint.getText(), 16));
            }

            tfBreakpoint.setText("");
        });

        Button removeBreakpoint = new Button("Remove breakpoint");
        removeBreakpoint.setOnAction(x -> {
            int index = breakpoints.getSelectionModel().getSelectedIndex();

            if(index >= 0) {
                this.gameBoy.removeBreakpoint(breakpoints.getSelectionModel().getSelectedIndex());
            }
        });

        Scene debugScene = new Scene(layout);
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
                int flags = info.getCpuInfo().getCpu().getAF();

                pc.setText(info.getCpuInfo().getPC());
                sp.setText(info.getCpuInfo().getSP());
                af.setText(info.getCpuInfo().getAF());
                bc.setText(info.getCpuInfo().getBC());
                de.setText(info.getCpuInfo().getDE());
                hl.setText(info.getCpuInfo().getHL());
                interruptFlags.setText(info.getCpuInfo().getInterruptFlags());
                interruptEnable.setText(info.getCpuInfo().getInterruptEnable());
                ime.setText(info.getCpuInfo().getIME());
                lcdc.setText(info.getMemoryInfo().getLCDC());

                z.setSelected((flags & CPU.FLAG_ZERO) == CPU.FLAG_ZERO);
                n.setSelected((flags & CPU.FLAG_SUB) == CPU.FLAG_SUB);
                h.setSelected((flags & CPU.FLAG_HALF) == CPU.FLAG_HALF);
                c.setSelected((flags & CPU.FLAG_CARRY) == CPU.FLAG_CARRY);

                breakpoints.getItems().clear();
                breakpoints.getItems().addAll(info.getCpuInfo().getBreakpoints());
            });
        });

        layout.add(af, 0, 0);
        layout.add(bc, 0, 1);
        layout.add(de, 0, 2);
        layout.add(hl, 0, 3);
        layout.add(sp, 0, 4);
        layout.add(pc, 0, 5);

        layout.add(ime, 1, 0);
        layout.add(interruptEnable, 1, 1);
        layout.add(interruptFlags, 1, 2);
        layout.add(lcdc, 1, 3);

        layout.add(z, 2, 0);
        layout.add(n, 2, 1);
        layout.add(h, 2, 2);
        layout.add(c, 2, 3);

        layout.add(run, 1, 990);
        layout.add(tick, 0, 990);
        layout.add(reset, 2, 990);

        layout.add(lblBreakpoint, 0, 998);
        layout.add(tfBreakpoint, 1, 998);
        layout.add(addBreakpoint, 0, 999);

        layout.add(lblBreakpoints, 0, 1000);
        layout.add(breakpoints, 0, 1001, 2, 5);
        layout.add(removeBreakpoint, 0, 1007);

        this.gameBoy.resetCpu();
        debugWindow.show();
    }
}
