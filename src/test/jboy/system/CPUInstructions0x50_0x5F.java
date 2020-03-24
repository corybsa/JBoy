package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0x50_0x5F {
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

    // op code 0x50
    @Test
    void ld_d_b_test() {
        rom[0x100] = 0x06; // ld b,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x50; // ld d,b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.D, "The D register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x51
    @Test
    void ld_d_c_test() {
        rom[0x100] = 0x0E; // ld c,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x51; // ld d,c

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.D, "The D register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x52
    @Test
    void ld_d_d_test() {
        rom[0x100] = 0x16; // ld d,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x52; // ld d,d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.D, "The D register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x53
    @Test
    void ld_d_e_test() {
        rom[0x100] = 0x1E; // ld e,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x53; // ld d,e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.D, "The D register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x54
    @Test
    void ld_d_h_test() {
        rom[0x100] = 0x26; // ld h,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x54; // ld d,h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.D, "The D register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x55
    @Test
    void ld_d_l_test() {
        rom[0x100] = 0x2E; // ld l,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x55; // ld d,l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.D, "The D register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x56
    @Test
    void ld_d_hlp_test() {
        memory.setByteAt(0xC000, 0x50);

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x56; // ld d,(hl)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.D, "The D register should equal 0x50.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x57
    @Test
    void ld_d_a_test() {
        rom[0x100] = 0x3E; // ld a,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x57; // ld d,a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.D, "The D register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x58
    @Test
    void ld_e_b_test() {
        rom[0x100] = 0x06; // ld b,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x58; // ld e,b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.E, "The E register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x59
    @Test
    void ld_e_c_test() {
        rom[0x100] = 0x0E; // ld c,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x59; // ld e,c

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.E, "The E register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x5A
    @Test
    void ld_e_d_test() {
        rom[0x100] = 0x16; // ld d,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x5A; // ld e,d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.E, "The E register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x5B
    @Test
    void ld_e_e_test() {
        rom[0x100] = 0x1E; // ld e,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x5B; // ld e,e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.E, "The E register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x5C
    @Test
    void ld_e_h_test() {
        rom[0x100] = 0x26; // ld h,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x5C; // ld e,h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.E, "The E register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x5D
    @Test
    void ld_e_l_test() {
        rom[0x100] = 0x2E; // ld l,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x5D; // ld e,l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.E, "The E register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x5E
    @Test
    void ld_e_hlp_test() {
        memory.setByteAt(0xC000, 0x50);

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x5E; // ld e,(hl)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.E, "The E register should equal 0x50.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x5F
    @Test
    void ld_e_a_test() {
        rom[0x100] = 0x3E; // ld a,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x5F; // ld e,a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.registers.E, "The E register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }
}
