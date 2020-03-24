package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xE0_0xEF {
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
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
    }

    @AfterEach
    void tearDown() {
        rom = null;
    }

    // op code 0xE0
    @Test
    void ldh_xp_a_test() {
        rom[0x100] = 0x3E; // ld a,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0xE0; // ldh 0x15,a
        rom[0x103] = 0x15;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, memory.getByteAt(0xFF15), "The value at address 0xFF10 should be 0x50.");
        assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
    }

    // op code 0xE1
    @Test
    void pop_hl_test() {
        memory.setByteAt(0xFFFD, 0xBE);
        memory.setByteAt(0xFFFC, 0xEF);
        cpu.registers.SP = 0xFFFC;

        rom[0x100] = 0xE1; // pop hl

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xBEEF, cpu.registers.getHL(), "The HL register should equal 0xBEEF.");
        assertEquals(0xFFFE, cpu.registers.SP, "The SP should equal 0xFFFE.");
        assertEquals(0x101, cpu.registers.PC, "The PC should equal 0x101");
    }

    // op code 0xE2
    @Test
    void ld_cp_a_test() {
        rom[0x100] = 0x3E; // ld a,0x69
        rom[0x101] = 0x69;
        rom[0x102] = 0x0E; // ld c,0x15
        rom[0x103] = 0x15;
        rom[0x104] = 0xE2; // ld (c),a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x69, cpu.registers.A, "The A register should be 0x69.");
        assertEquals(0x15, cpu.registers.C, "The C register should be 0x10.");
        assertEquals(0x69, memory.getByteAt(0xFF15), "The value at address 0xFF10 should be 0x69.");
        assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
    }

    // op code 0xE5
    @Test
    void push_hl_test() {
        rom[0x100] = 0x21; // ld hl,0xBEEF
        rom[0x101] = 0xEF;
        rom[0x102] = 0xBE;
        rom[0x103] = 0xE5; // push hl

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC");
        assertEquals(0xBE, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0xEF");
        assertEquals(0xEF, memory.getByteAt(0xFFFC), "The value at address 0xFFFC should equal 0xBE");
        assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
    }

    // op code 0xE6
    @Test
    void and_x_test() {
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0xE6; // and 0x3F
        rom[0x103] = 0x3F;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x1A, cpu.registers.A, "The A register should equal 0x1A.");
        assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");

        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
        cpu.registers.PC = 0x100;
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0xE6; // and 0x00
        rom[0x103] = 0x00;

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
    }

    // op code 0xE7
    @Test
    void rst_20_test() {
        rom[0x100] = 0xE7; // rst 0x20

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC.");
        assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01.");
        assertEquals(0x01, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x00.");
        assertEquals(0x20, cpu.registers.PC, "The PC should equal 0x20.");
    }

    // op code 0xE8
    @Test
    void add_sp_x_test() {
        cpu.registers.SP = 0xFFF0;

        rom[0x100] = 0xE8; // add sp,0x05
        rom[0x101] = 0x05;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFF5, cpu.registers.SP, "The SP should equal 0xFFF5.");
        assertEquals(0x102, cpu.registers.PC, "PC should equal 0x102.");
    }

    // op code 0xE9
    @Test
    void jp_hlp_test() {
        rom[0x100] = 0x21; // ld hl,0x2000
        rom[0x101] = 0x00;
        rom[0x102] = 0x20;
        rom[0x103] = 0xE9; // jp (hl)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x2000, cpu.registers.PC, "PC should equal 0x2000.");
    }

    // op code 0xEA
    @Test
    void ld_xxp_a_test() {
        rom[0x100] = 0x3E; // ld a,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0xEA; // ld (0xC000),a
        rom[0x103] = 0x00;
        rom[0x104] = 0xC0;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, memory.getByteAt(0xC000), "The value at address 0xC000 should equal 0x50.");
        assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
    }

    // op code 0xEE
    @Test
    void xor_x_test() {
        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xEE; // xor 0xFF
        rom[0x103] = 0xFF;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
        assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");

        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
        cpu.registers.PC = 0x100;

        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xEE; // xor 0x0F
        rom[0x103] = 0x0F;

        cpu.tick();
        cpu.tick();
        assertEquals(0xF0, cpu.registers.A, "The A register should equal 0x00.");
        assertEquals(0x00, cpu.registers.F, "No flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
    }

    // op code 0xEF
    @Test
    void rst_28_test() {
        rom[0x100] = 0xEF; // rst 0x28

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC.");
        assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01.");
        assertEquals(0x01, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x00.");
        assertEquals(0x28, cpu.registers.PC, "The PC should equal 0x28.");
    }
}
