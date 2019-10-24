package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0x60_0x6F {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

    @BeforeAll
    static void testBeforeAll() {
        memory = new Memory();
        cpu = new CPU(memory, null);
    }

    @BeforeEach
    void setUp() {
        cpu.setPC(0x100);
        cpu.setSP(0xFFFE);
        rom = new int[0x7FFF];
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
    }

    @AfterEach
    void tearDown() {
        rom = null;
    }

    // op code 0x60
    @Test
    void ld_h_b_test() {
        rom[0x100] = 0x06; // ld b,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x60; // ld h,b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getH(), "The H register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x61
    @Test
    void ld_h_c_test() {
        rom[0x100] = 0x0E; // ld c,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x61; // ld h,c

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getH(), "The H register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x62
    @Test
    void ld_h_d_test() {
        rom[0x100] = 0x16; // ld d,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x62; // ld h,d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getH(), "The H register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x63
    @Test
    void ld_h_e_test() {
        rom[0x100] = 0x1E; // ld e,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x63; // ld h,e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getH(), "The H register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x64
    @Test
    void ld_h_h_test() {
        rom[0x100] = 0x26; // ld h,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x64; // ld h,h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getH(), "The H register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x65
    @Test
    void ld_h_l_test() {
        rom[0x100] = 0x2E; // ld l,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x65; // ld h,l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getH(), "The H register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x66
    @Test
    void ld_h_hlp_test() {
        memory.setByteAt(0xC000, 0x50);

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x66; // ld h,(hl)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getH(), "The H register should equal 0x50.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0x67
    @Test
    void ld_h_a_test() {
        rom[0x100] = 0x3E; // ld a,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x67; // ld h,a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getH(), "The H register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x68
    @Test
    void ld_l_b_test() {
        rom[0x100] = 0x06; // ld b,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x68; // ld l,b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getL(), "The L register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x69
    @Test
    void ld_l_c_test() {
        rom[0x100] = 0x0E; // ld c,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x69; // ld l,c

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getL(), "The L register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x6A
    @Test
    void ld_l_d_test() {
        rom[0x100] = 0x16; // ld d,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x6A; // ld l,d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getL(), "The L register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x6B
    @Test
    void ld_l_e_test() {
        rom[0x100] = 0x1E; // ld e,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x6B; // ld l,e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getL(), "The L register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x6C
    @Test
    void ld_l_h_test() {
        rom[0x100] = 0x26; // ld h,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x6C; // ld l,h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getL(), "The L register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x6D
    @Test
    void ld_l_l_test() {
        rom[0x100] = 0x2E; // ld l,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x6D; // ld l,l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getL(), "The L register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x6E
    @Test
    void ld_l_hlp_test() {
        memory.setByteAt(0xC000, 0x50);

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x6E; // ld l,(hl)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getL(), "The L register should equal 0x50.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0x6F
    @Test
    void ld_l_a_test() {
        rom[0x100] = 0x3E; // ld a,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x6F; // ld l,a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getL(), "The L register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }
}
