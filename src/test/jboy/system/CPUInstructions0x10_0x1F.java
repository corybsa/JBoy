package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0x10_0x1F {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

    @BeforeAll
    static void testBeforeAll() {
        memory = new Memory();
        LCD lcd = new LCD(memory);
        GPU gpu = new GPU(memory, lcd);
        Timers timers = new Timers(memory);

        lcd.setDrawFunction((tiles) -> null);
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

    // op code 0x10
    @Test
    void stop_test() {}

    // op code 0x11
    @Test
    void ld_de_xx_test() {
        rom[0x100] = 0x11; // ld de,0x895F
        rom[0x101] = 0x5F;
        rom[0x102] = 0x89;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x895F, cpu.registers.getDE(), "The DE register should equal 0x895F.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x12
    @Test
    void ld_dep_a_test() {
        memory.setByteAt(0xC000, 0x00);

        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x11; // ld de,0xC000
        rom[0x103] = 0x00;
        rom[0x104] = 0xC0;
        rom[0x105] = 0x12; // ld (de),a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xFF, memory.getByteAt(cpu.registers.getDE()), "The value pointed to by DE should be 0xFF.");
        assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");
    }

    // op code 0x13
    @Test
    void inc_de_test() {
        rom[0x100] = 0x11; // ld de,0x7000
        rom[0x101] = 0x00;
        rom[0x102] = 0x70;
        rom[0x103] = 0x13; // inc de

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x7001, cpu.registers.getDE(), "The DE register should equal 0x7001.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0x14
    @Test
    void inc_d_test() {
        rom[0x100] = 0x16; // ld d,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x14; // inc d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x01, cpu.registers.D, "The D register should equal 0x01.");
        assertEquals(0x00, cpu.registers.F, "No flags should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x16; // ld d,0x0F
        rom[0x101] = 0x0F;
        rom[0x102] = 0x14; // inc d

        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.registers.D, "The D register should equal 0x10.");
        assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x16; // ld d,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x14; // inc d

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.D, "The D register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x15
    @Test
    void dec_d_test() {
        rom[0x100] = 0x16; // ld d,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x15; // dec d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.registers.D, "The D register should equal 0xFE.");
        assertEquals(CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

        // Test a decrement that results in zero.
        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
        rom[0x100] = 0x16; // ld d,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0x15; // dec d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.D, "The D register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "ZERO and SUB flags should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

        // Test a half carry.
        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
        rom[0x100] = 0x16; // ld d,0x10
        rom[0x101] = 0x10;
        rom[0x102] = 0x15; // dec d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x0F, cpu.registers.D, "The D register should equal 0x0F.");
        assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

        // Test decrementing zero.
        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
        rom[0x100] = 0x16; // ld d,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x15; // dec d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFF, cpu.registers.D, "The D register should equal 0xFF.");
        assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x16
    @Test
    void ld_d_x_test() {
        rom[0x100] = 0x16; // ld d,0xCD
        rom[0x101] = 0xCD;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xCD, cpu.registers.D, "The D register should equal 0xCD.");
        assertEquals(0x102, cpu.registers.PC, "PC should equal 0x102.");
    }

    // op code 0x17
    @Test
    void rla_test() {
        rom[0x100] = 0x3E; // ld a,0x95
        rom[0x101] = 0x95;
        rom[0x102] = 0x17; // rla

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x2A, cpu.registers.A, "The A register should equal 0x2B.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x18
    @Test
    void jr_x_test() {
        rom[0x100] = 0x18; // jr 0x05;
        rom[0x101] = 0x05;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x107, cpu.registers.PC, "PC should equal 0x107.");
    }

    // op code 0x19
    @Test
    void add_hl_de_test() {
        rom[0x100] = 0x11; // ld de,0x0605
        rom[0x101] = 0x05;
        rom[0x102] = 0x06;
        rom[0x103] = 0x21; // ld hl,0x8A23
        rom[0x104] = 0x23;
        rom[0x105] = 0x8A;
        rom[0x106] = 0x19; // add hl,de

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x9028, cpu.registers.getHL(), "The HL register should equal 0x0605.");
        assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
        assertEquals(0x107, cpu.registers.PC, "PC should equal 0x107.");
    }

    // op code 0x1A
    @Test
    void ld_a_dep_test() {
        memory.setByteAt(0xC000, 0xFF); // This is the value that DE will point to.

        rom[0x100] = 0x11; // ld de,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x1A; // ld a,(de)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFF, cpu.registers.A, "The A register should equal 0xFF.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0x1B
    @Test
    void dec_de_test() {
        rom[0x100] = 0x11; // ld de,0x0605
        rom[0x101] = 0x05;
        rom[0x102] = 0x06;
        rom[0x103] = 0x1B; // dec de

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x0604, cpu.registers.getDE(), "The DE register should equal 0x0604.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0x1C
    @Test
    void inc_e_test() {
        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x1C; // inc e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x01, cpu.registers.E, "The E register should equal 0x01.");
        assertEquals(0x00, cpu.registers.F, "No flags should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x1E; // ld e,0x0F
        rom[0x101] = 0x0F;
        rom[0x102] = 0x1C; // inc e

        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.registers.E, "The E register should equal 0x10.");
        assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x1E; // ld e,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x1C; // inc e

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.E, "The E register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x1D
    @Test
    void dec_e_test() {
        rom[0x100] = 0x1E; // ld e,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x1D; // dec e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.registers.E, "The E register should equal 0xFE.");
        assertEquals(CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

        // Test a decrement that results in zero.
        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
        rom[0x100] = 0x1E; // ld e,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0x1D; // dec e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.E, "The E register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "ZERO and SUB flags should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

        // Test a half carry.
        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
        rom[0x100] = 0x1E; // ld e,0x10
        rom[0x101] = 0x10;
        rom[0x102] = 0x1D; // dec e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x0F, cpu.registers.E, "The E register should equal 0x0F.");
        assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

        // Test decrementing zero.
        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x1D; // dec e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFF, cpu.registers.E, "The E register should equal 0xFF.");
        assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x1E
    @Test
    void ld_e_x_test() {
        rom[0x100] = 0x1E; // ld e,0xCD
        rom[0x101] = 0xCD;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xCD, cpu.registers.E, "The E register should equal 0xCD.");
        assertEquals(0x102, cpu.registers.PC, "PC should equal 0x102.");
    }

    // op code 0x1F
    @Test
    void rra_test() {
        rom[0x100] = 0x3E; // ld a,0x81
        rom[0x101] = 0x81;
        rom[0x102] = 0x1F; // rra

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x40, cpu.registers.A, "The A register should equal 0x40.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

        cpu.setFlags(CPU.Flags.CARRY);
        cpu.registers.PC = 0x100;

        cpu.tick();
        cpu.tick();
        assertEquals(0xC0, cpu.registers.A, "The A register should equal 0xC0.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }
}