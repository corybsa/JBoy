package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xF0_0xFF {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

    @BeforeAll
    static void testBeforeAll() {
        memory = new Memory();
        Display display = new Display(memory);
        GPU gpu = new GPU(memory, display);
        Timers timers = new Timers(memory);

        display.setDrawFunction((tiles) -> null);
        memory.setGpuRef(gpu);

        cpu = new CPU(memory, gpu, timers);
    }

    @BeforeEach
    void setUp() {
        cpu.registers.PC = 0x100;
        cpu.registers.SP = 0xFFFE;
        rom = new int[0x7FFF];
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
    }

    @AfterEach
    void tearDown() {
        rom = null;
    }

    // op code 0xF0
    @Test
    void ldh_a_xp_test() {
        memory.setByteAt(0xFFF0, 0x50);

        rom[0x100] = 0xF0; // ld a,(x)
        rom[0x101] = 0xF0;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
        assertEquals(0x102, cpu.registers.PC, "The PC should equal 0x102.");
    }

    // op code 0xF1
    @Test
    void pop_af_test() {
        memory.setByteAt(0xFFFD, 0xBE);
        memory.setByteAt(0xFFFC, 0xEF);
        cpu.registers.SP = 0xFFFC;

        rom[0x100] = 0xF1; // pop af

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xBEE0, cpu.registers.getAF(), "The AF register should equal 0xBEE0.");
        assertEquals(0xFFFE, cpu.registers.SP, "The SP should equal 0xFFFE.");
        assertEquals(0x101, cpu.registers.PC, "The PC should equal 0x101");
    }

    // op code 0xF2
    @Test
    void ld_a_cp_test() {
        memory.setByteAt(0xFFF0, 0x50);

        rom[0x100] = 0x0E; // ld c,0xF0
        rom[0x101] = 0xF0;
        rom[0x102] = 0xF2; // ld a,(c)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103.");
    }

    // op code 0xF3
    @Test
    void di_test() {
        // TODO: disables master interrupt (sets IE to 0).
    }

    // op code 0xF5
    @Test
    void push_af_test() {
        cpu.setFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0xC0
        rom[0x101] = 0xC0;
        rom[0x102] = 0xF5; // push af

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC");
        assertEquals(0xC0, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0xC0");
        assertEquals(0xF0, memory.getByteAt(0xFFFC), "The value at address 0xFFFC should equal 0xF0");
        assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x104.");
    }

    // op code 0xF6
    @Test
    void or_x_test() {
        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xF6; // or 0xFF
        rom[0x103] = 0xFF;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFF, cpu.registers.A, "The A register should equal 0x00.");
        assertEquals(0x00, cpu.registers.F, "No flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.registers.PC = 0x100;

        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xF6; // or 0x0F
        rom[0x103] = 0x0F;

        cpu.tick();
        cpu.tick();
        assertEquals(0xFF, cpu.registers.A, "The A register should equal 0x00.");
        assertEquals(0x00, cpu.registers.F, "No flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
    }

    // op code 0xF7
    @Test
    void rst_30_test() {
        rom[0x100] = 0xF7; // rst 0x0F7

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC.");
        assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01.");
        assertEquals(0x01, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x00.");
        assertEquals(0x30, cpu.registers.PC, "The PC should equal 0x30.");
    }

    // op code 0xF8
    @Test
    void ld_hl_sp_x_test() {
        cpu.registers.SP = 0xEFFF;

        rom[0x100] = 0xF8; // ld hl, sp+x
        rom[0x101] = 0x01;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xF000, cpu.registers.getHL(), "The HL register should equal 0x50.");
        assertEquals(0x102, cpu.registers.PC, "The PC should equal 0x102.");
    }

    // op code 0xF9
    @Test
    void ld_sp_hl_test() {
        rom[0x100] = 0x21; // ld hl,0xFFF0
        rom[0x101] = 0xF0;
        rom[0x102] = 0xFF;
        rom[0x103] = 0xF9; // ld sp,hl

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFFF0, cpu.registers.SP, "The SP should equal 0xFFF0.");
        assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
    }

    // op code 0xFA
    @Test
    void ld_a_xxp_test() {
        memory.setByteAt(0xC000, 0x50);

        rom[0x100] = 0xFA; // ld a,(0xC000)
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103.");
    }

    // op code 0xFB
    @Test
    void ei_test() {
        // TODO: enables master interrupt (sets IE to 1).
    }

    // op code 0xFE
    @Test
    void cp_x_test() {
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0xFE; // cp 0x2F
        rom[0x103] = 0x2F;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.registers.PC = 0x100;
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0xFE; // cp 0x3C
        rom[0x103] = 0x3C;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.registers.PC = 0x100;
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0xFE; // cp 0x40
        rom[0x103] = 0x40;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_CARRY, cpu.registers.F, "The SUB and CARRY flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
    }

    // op code 0xFF
    @Test
    void rst_38_test() {
        rom[0x100] = 0xFF; // rst 0x38

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC.");
        assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01.");
        assertEquals(0x01, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x00.");
        assertEquals(0x38, cpu.registers.PC, "The PC should equal 0x38.");
    }
}
