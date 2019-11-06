package sample;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import jboy.other.GameBoyInfo;
import jboy.system.CPU;
import jboy.system.GameBoy;

class DebugWindow {
    private GridPane layout;

    private Label pc;
    private Label sp;
    private Label af;
    private Label bc;
    private Label de;
    private Label hl;
    private Label interruptFlags;
    private Label interruptEnable;
    private Label ime;
    private Label lcdc;
    private Label ly;
    private Label lcdStat;

    private CheckBox z;
    private CheckBox n;
    private CheckBox h;
    private CheckBox c;

    private TextField tfBreakpoint;
    private ListView<String> breakpoints;

    DebugWindow() {
        this.layout = new GridPane();
    }

    GridPane getLayout() {
        return this.layout;
    }

    void createRegisters() {
        this.pc = new Label("PC: 0x100");
        this.sp = new Label("SP: 0xFFFE");
        this.af = new Label("AF: 0x0000");
        this.bc = new Label("BC: 0x0000");
        this.de = new Label("DE: 0x0000");
        this.hl = new Label("HL: 0x0000");
        this.interruptFlags = new Label("IF: 0x00");
        this.interruptEnable = new Label("IE: 0x00");
        this.ime = new Label("IME: off");
        this.lcdc = new Label("LCDC: 0x00");
        this.ly = new Label("LY: 0x00");
        this.lcdStat = new Label("STAT: 0x00");

        this.layout.add(this.af, 0, 0);
        this.layout.add(this.bc, 0, 1);
        this.layout.add(this.de, 0, 2);
        this.layout.add(this.hl, 0, 3);
        this.layout.add(this.sp, 0, 4);
        this.layout.add(this.pc, 0, 5);

        this.layout.add(this.lcdc, 1, 0);
        this.layout.add(this.lcdStat, 1, 1);
        this.layout.add(this.ly, 1, 2);
        this.layout.add(this.ime, 1, 3);
        this.layout.add(this.interruptEnable, 1, 4);
        this.layout.add(this.interruptFlags, 1, 5);
    }

    void createFlagCheckboxes() {
        this.z = new CheckBox("Z") {
            @Override
            public void arm() {
                // nothing.
            }
        };

        this.n = new CheckBox("N") {
            @Override
            public void arm() {
                // nothing.
            }
        };

        this.h = new CheckBox("H") {
            @Override
            public void arm() {
                // nothing.
            }
        };

        this.c = new CheckBox("C") {
            @Override
            public void arm() {
                // nothing.
            }
        };

        this.layout.add(z, 2, 0);
        this.layout.add(n, 2, 1);
        this.layout.add(h, 2, 2);
        this.layout.add(c, 2, 3);
    }

    void createCpuControls(GameBoy gameBoy) {
        Button tick = new Button("Tick");
        tick.setOnAction(x -> gameBoy.tick());

        Button reset = new Button("Reset");
        reset.setOnAction(x -> gameBoy.resetCpu());

        Button run = new Button("Run to breakpoint");
        run.setOnAction(x -> gameBoy.runToBreakpoint());

        this.layout.add(run, 1, 19);
        this.layout.add(tick, 0, 19);
        this.layout.add(reset, 2, 19);
    }

    void createBreakpointControls(GameBoy gameBoy) {
        this.breakpoints = new ListView<>();

        Label lblBreakpoint = new Label("Enter a breakpoint: ");

        this.tfBreakpoint = new TextField();
        this.tfBreakpoint.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER) {
                this.addBreakpoint(gameBoy);
            }
        });

        Label lblBreakpoints = new Label("Breakpoints:");

        Button addBreakpoint = new Button("Add breakpoint");
        addBreakpoint.setOnAction(x -> this.addBreakpoint(gameBoy));

        Button removeBreakpoint = new Button("Remove breakpoint");
        removeBreakpoint.setOnAction(x -> {
            int index = this.breakpoints.getSelectionModel().getSelectedIndex();

            if(index >= 0) {
                gameBoy.removeBreakpoint(this.breakpoints.getSelectionModel().getSelectedIndex());
            }
        });

        this.layout.add(lblBreakpoint, 0, 21);
        this.layout.add(tfBreakpoint, 1, 21);
        this.layout.add(addBreakpoint, 0, 22);

        this.layout.add(lblBreakpoints, 0, 23);
        this.layout.add(this.breakpoints, 0, 24, 2, 5);
        this.layout.add(removeBreakpoint, 0, 29);
    }

    private void addBreakpoint(GameBoy gameBoy) {
        String text = tfBreakpoint.getText();

        if(!text.isEmpty() && !text.isBlank()) {
            gameBoy.addBreakpoint(Integer.parseInt(tfBreakpoint.getText(), 16));
        }

        tfBreakpoint.setText("");
    }

    void updateWindow(GameBoyInfo info) {
        int flags = info.getCpuInfo().getCpu().getAF();

        this.pc.setText(info.getCpuInfo().getPC());
        this.sp.setText(info.getCpuInfo().getSP());
        this.af.setText(info.getCpuInfo().getAF());
        this.bc.setText(info.getCpuInfo().getBC());
        this.de.setText(info.getCpuInfo().getDE());
        this.hl.setText(info.getCpuInfo().getHL());
        this.interruptFlags.setText(info.getCpuInfo().getInterruptFlags());
        this.interruptEnable.setText(info.getCpuInfo().getInterruptEnable());
        this.ime.setText(info.getCpuInfo().getIME());
        this.lcdc.setText(info.getMemoryInfo().getLCDC());
        this.ly.setText(info.getMemoryInfo().getLY());
        this.lcdStat.setText(info.getMemoryInfo().getLCDStatus());

        this.z.setSelected((flags & CPU.FLAG_ZERO) == CPU.FLAG_ZERO);
        this.n.setSelected((flags & CPU.FLAG_SUB) == CPU.FLAG_SUB);
        this.h.setSelected((flags & CPU.FLAG_HALF) == CPU.FLAG_HALF);
        this.c.setSelected((flags & CPU.FLAG_CARRY) == CPU.FLAG_CARRY);

        this.breakpoints.getItems().clear();
        this.breakpoints.getItems().addAll(info.getCpuInfo().getBreakpoints());
    }
}
