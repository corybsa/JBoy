package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xCBD0_0xCBDF {
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

    // op code 0xCBD0
    @Test
    void set_2_b_test() {
        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 2,b
        rom[0x103] = 0xD0;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x04, cpu.getB(), "The B register should equal 0x04.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBD1
    @Test
    void set_2_c_test() {
        rom[0x100] = 0x0E; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 2,c
        rom[0x103] = 0xD1;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x04, cpu.getC(), "The C register should equal 0x04.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBD2
    @Test
    void set_2_d_test() {
        rom[0x100] = 0x16; // ld d,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 2,d
        rom[0x103] = 0xD2;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x04, cpu.getD(), "The D register should equal 0x04.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBD3
    @Test
    void set_2_e_test() {
        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 2,e
        rom[0x103] = 0xD3;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x04, cpu.getE(), "The E register should equal 0x04.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBD4
    @Test
    void set_2_h_test() {
        rom[0x100] = 0x26; // ld h,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 2,h
        rom[0x103] = 0xD4;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x04, cpu.getH(), "The H register should equal 0x04.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBD5
    @Test
    void set_2_l_test() {
        rom[0x100] = 0x2E; // ld l,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 2,l
        rom[0x103] = 0xD5;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x04, cpu.getL(), "The L register should equal 0x04.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBD6
    @Test
    void set_2_hlp_test() {
        memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // set 2,(hl)
        rom[0x104] = 0xD6;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x04, memory.getByteAt(cpu.getHL()), "The value pointed to by the HL register should equal 0x04.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCBD7
    @Test
    void set_2_a_test() {
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 2,a
        rom[0x103] = 0xD7;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x04, cpu.getA(), "The A register should equal 0x04.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBD8
    @Test
    void set_3_b_test() {
        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 3,b
        rom[0x103] = 0xD8;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x08, cpu.getB(), "The B register should equal 0x08.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBD9
    @Test
    void set_3_c_test() {
        rom[0x100] = 0x0E; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 3,c
        rom[0x103] = 0xD9;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x08, cpu.getC(), "The C register should equal 0x08.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBDA
    @Test
    void set_3_d_test() {
        rom[0x100] = 0x16; // ld d,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 3,d
        rom[0x103] = 0xDA;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x08, cpu.getD(), "The D register should equal 0x08.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBDB
    @Test
    void set_3_e_test() {
        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 3,e
        rom[0x103] = 0xDB;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x08, cpu.getE(), "The E register should equal 0x08.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBDC
    @Test
    void set_3_h_test() {
        rom[0x100] = 0x26; // ld h,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 3,h
        rom[0x103] = 0xDC;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x08, cpu.getH(), "The H register should equal 0x08.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBDD
    @Test
    void set_3_l_test() {
        rom[0x100] = 0x2E; // ld l,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 3,l
        rom[0x103] = 0xDD;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x08, cpu.getL(), "The L register should equal 0x08.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBDE
    @Test
    void set_3_hlp_test() {
        memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // set 3,(hl)
        rom[0x104] = 0xDE;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x08, memory.getByteAt(cpu.getHL()), "The value pointed to by the HL register should equal 0x08.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCBDF
    @Test
    void set_3_a_test() {
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 3,a
        rom[0x103] = 0xDF;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x08, cpu.getA(), "The A register should equal 0x08.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }
}
