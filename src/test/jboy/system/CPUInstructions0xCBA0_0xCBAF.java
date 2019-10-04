package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xCBA0_0xCBAF {
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
        rom = new int[0x7FFF];
    }

    @AfterEach
    void tearDown() {
        rom = null;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
    }

    // op code 0xCBA0
    @Test
    void res_4_b_test() {
        rom[0x100] = 0x06; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 4,b
        rom[0x103] = 0xA0;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xEF, cpu.getB(), "The B register should equal 0xEF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBA1
    @Test
    void res_4_c_test() {
        rom[0x100] = 0x0E; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 4,c
        rom[0x103] = 0xA1;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xEF, cpu.getC(), "The C register should equal 0xEF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBA2
    @Test
    void res_4_d_test() {
        rom[0x100] = 0x16; // ld d,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 4,d
        rom[0x103] = 0xA2;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xEF, cpu.getD(), "The D register should equal 0xEF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBA3
    @Test
    void res_4_e_test() {
        rom[0x100] = 0x1E; // ld e,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 4,e
        rom[0x103] = 0xA3;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xEF, cpu.getE(), "The E register should equal 0xEF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBA4
    @Test
    void res_4_h_test() {
        rom[0x100] = 0x26; // ld h,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 4,h
        rom[0x103] = 0xA4;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xEF, cpu.getH(), "The H register should equal 0xEF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBA5
    @Test
    void res_4_l_test() {
        rom[0x100] = 0x2E; // ld l,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 4,l
        rom[0x103] = 0xA5;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xEF, cpu.getL(), "The L register should equal 0xEF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBA6
    @Test
    void res_4_hlp_test() {
        memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // res 4,(hl)
        rom[0x104] = 0xA6;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xEF, cpu.getHL(), "The value pointed to by the HL register should equal 0xEF.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCBA7
    @Test
    void res_4_a_test() {
        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 4,a
        rom[0x103] = 0xA7;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xEF, cpu.getA(), "The A register should equal 0xEF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBA8
    @Test
    void res_5_b_test() {
        rom[0x100] = 0x06; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 5,b
        rom[0x103] = 0xA8;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xDF, cpu.getB(), "The B register should equal 0xDF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBA9
    @Test
    void res_5_c_test() {
        rom[0x100] = 0x0E; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 5,c
        rom[0x103] = 0xA9;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xDF, cpu.getC(), "The C register should equal 0xDF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBAA
    @Test
    void res_5_d_test() {
        rom[0x100] = 0x16; // ld d,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 5,d
        rom[0x103] = 0xAA;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xDF, cpu.getD(), "The D register should equal 0xDF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBAB
    @Test
    void res_5_e_test() {
        rom[0x100] = 0x1E; // ld e,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 5,e
        rom[0x103] = 0xAB;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xDF, cpu.getE(), "The E register should equal 0xDF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBAC
    @Test
    void res_5_h_test() {
        rom[0x100] = 0x26; // ld h,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 5,h
        rom[0x103] = 0xAC;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xDF, cpu.getH(), "The H register should equal 0xDF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBAD
    @Test
    void res_5_l_test() {
        rom[0x100] = 0x2E; // ld l,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 5,l
        rom[0x103] = 0xAD;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xDF, cpu.getL(), "The L register should equal 0xDF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBAE
    @Test
    void res_5_hlp_test() {
        memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // res 5,(hl)
        rom[0x104] = 0xAE;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xDF, cpu.getHL(), "The value pointed to by the HL register should equal 0xDF.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCBAF
    @Test
    void res_5_a_test() {
        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 5,a
        rom[0x103] = 0xAF;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xDF, cpu.getA(), "The A register should equal 0xDF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }
}
