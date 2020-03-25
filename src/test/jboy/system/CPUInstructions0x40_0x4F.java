package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0x40_0x4F {
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

    // op code 0x40
    @Test
    void ld_b_b_test() {
        rom[0x100] = 0x06; // ld b,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x40; // ld b,b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.B, "The B register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x41
    @Test
    void ld_b_c_test() {
        rom[0x100] = 0x0E; // ld c,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x41; // ld b,c

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.B, "The B register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x42
    @Test
    void ld_b_d_test() {
        rom[0x100] = 0x16; // ld d,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x42; // ld b,d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.B, "The B register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x43
    @Test
    void ld_b_e_test() {
        rom[0x100] = 0x1E; // ld e,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x43; // ld b,e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.B, "The B register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x44
    @Test
    void ld_b_h_test() {
        rom[0x100] = 0x26; // ld h,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x44; // ld b,h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.B, "The B register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x45
    @Test
    void ld_b_l_test() {
        rom[0x100] = 0x2E; // ld l,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x45; // ld b,l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.B, "The B register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x46
    @Test
    void ld_b_hlp_test() {
        memory.setByteAt(0xC000, 0x50);

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x46; // ld b,(hl)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.B, "The B register should equal 0x50.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0x47
    @Test
    void ld_b_a_test() {
        rom[0x100] = 0x3E; // ld a,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x47; // ld b,a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.B, "The B register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x48
    @Test
    void ld_c_b_test() {
        rom[0x100] = 0x06; // ld b,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x48; // ld c,b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.C, "The C register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x49
    @Test
    void ld_c_c_test() {
        rom[0x100] = 0x0E; // ld c,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x49; // ld c,c

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.C, "The C register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x4A
    @Test
    void ld_c_d_test() {
        rom[0x100] = 0x16; // ld d,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x4A; // ld c,d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.C, "The C register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x4B
    @Test
    void ld_c_e_test() {
        rom[0x100] = 0x1E; // ld e,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x4B; // ld c,e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.C, "The C register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x4C
    @Test
    void ld_c_h_test() {
        rom[0x100] = 0x26; // ld h,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x4C; // ld c,h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.C, "The C register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x4D
    @Test
    void ld_c_l_test() {
        rom[0x100] = 0x2E; // ld l,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x4D; // ld c,l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.C, "The C register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x4E
    @Test
    void ld_c_hlp_test() {
        memory.setByteAt(0xC000, 0x50);

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x4E; // ld c,(hl)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.C, "The C register should equal 0x50.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0x4F
    @Test
    void ld_c_a_test() {
        rom[0x100] = 0x3E; // ld a,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x4F; // ld c,a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.C, "The C register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }
}
