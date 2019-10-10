package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xCBF0_0xCBFF {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

    @BeforeAll
    static void testBeforeAll() {
        memory = new Memory();
        cpu = new CPU(memory);
    }

    @BeforeEach
    void setUp() {
        cpu.setPC(0x100);
        cpu.setSP(0xFFFE);
        rom = new int[0x7FFF];
    }

    @AfterEach
    void tearDown() {
        rom = null;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
    }

    // op code 0xCBF0
    @Test
    void set_6_b_test() {
        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 6,b
        rom[0x103] = 0xF0;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x40, cpu.getB(), "The B register should equal 0x40.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBF1
    @Test
    void set_6_c_test() {
        rom[0x100] = 0x0E; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 6,c
        rom[0x103] = 0xF1;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x40, cpu.getC(), "The C register should equal 0x40.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBF2
    @Test
    void set_6_d_test() {
        rom[0x100] = 0x16; // ld d,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 6,d
        rom[0x103] = 0xF2;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x40, cpu.getD(), "The D register should equal 0x40.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBF3
    @Test
    void set_6_e_test() {
        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 6,e
        rom[0x103] = 0xF3;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x40, cpu.getE(), "The E register should equal 0x40.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBF4
    @Test
    void set_6_h_test() {
        rom[0x100] = 0x26; // ld h,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 6,h
        rom[0x103] = 0xF4;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x40, cpu.getH(), "The H register should equal 0x40.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBF5
    @Test
    void set_6_l_test() {
        rom[0x100] = 0x2E; // ld l,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 6,l
        rom[0x103] = 0xF5;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x40, cpu.getL(), "The L register should equal 0x40.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBF6
    @Test
    void set_6_hlp_test() {
        memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // set 6,(hl)
        rom[0x104] = 0xF6;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x40, cpu.getHL(), "The value pointed to by the HL register should equal 0x40.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCBF7
    @Test
    void set_6_a_test() {
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 6,a
        rom[0x103] = 0xF7;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x40, cpu.getA(), "The A register should equal 0x40.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBF8
    @Test
    void set_7_b_test() {
        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 7,b
        rom[0x103] = 0xF8;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x80, cpu.getB(), "The B register should equal 0x80.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBF9
    @Test
    void set_7_c_test() {
        rom[0x100] = 0x0E; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 7,c
        rom[0x103] = 0xF9;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x80, cpu.getC(), "The C register should equal 0x80.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBFA
    @Test
    void set_7_d_test() {
        rom[0x100] = 0x16; // ld d,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 7,d
        rom[0x103] = 0xFA;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x80, cpu.getD(), "The D register should equal 0x80.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBFB
    @Test
    void set_7_e_test() {
        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 7,e
        rom[0x103] = 0xFB;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x80, cpu.getE(), "The E register should equal 0x80.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBFC
    @Test
    void set_7_h_test() {
        rom[0x100] = 0x26; // ld h,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 7,h
        rom[0x103] = 0xFC;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x80, cpu.getH(), "The H register should equal 0x80.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBFD
    @Test
    void set_7_l_test() {
        rom[0x100] = 0x2E; // ld l,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 7,l
        rom[0x103] = 0xFD;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x80, cpu.getL(), "The L register should equal 0x80.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBFE
    @Test
    void set_7_hlp_test() {
        memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // set 7,(hl)
        rom[0x104] = 0xFE;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x80, cpu.getHL(), "The value pointed to by the HL register should equal 0x80.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCBFF
    @Test
    void set_7_a_test() {
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 7,a
        rom[0x103] = 0xFF;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x80, cpu.getA(), "The A register should equal 0x80.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }
}
