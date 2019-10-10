package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xCB90_0xCB9F {
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

    // op code 0xCB90
    @Test
    void res_2_b_test() {
        rom[0x100] = 0x06; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 2,b
        rom[0x103] = 0x90;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFB, cpu.getB(), "The B register should equal 0xFB.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB91
    @Test
    void res_2_c_test() {
        rom[0x100] = 0x0E; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 2,c
        rom[0x103] = 0x91;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFB, cpu.getC(), "The C register should equal 0xFB.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB92
    @Test
    void res_2_d_test() {
        rom[0x100] = 0x16; // ld d,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 2,d
        rom[0x103] = 0x92;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFB, cpu.getD(), "The D register should equal 0xFB.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB93
    @Test
    void res_2_e_test() {
        rom[0x100] = 0x1E; // ld e,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 2,e
        rom[0x103] = 0x93;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFB, cpu.getE(), "The E register should equal 0xFB.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB94
    @Test
    void res_2_h_test() {
        rom[0x100] = 0x26; // ld h,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 2,h
        rom[0x103] = 0x94;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFB, cpu.getH(), "The H register should equal 0xFB.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB95
    @Test
    void res_2_l_test() {
        rom[0x100] = 0x2E; // ld l,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 2,l
        rom[0x103] = 0x95;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFB, cpu.getL(), "The L register should equal 0xFB.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB96
    @Test
    void res_2_hlp_test() {
        memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // res 2,(hl)
        rom[0x104] = 0x96;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFB, cpu.getHL(), "The value pointed to by the HL register should equal 0xFB.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCB97
    @Test
    void res_2_a_test() {
        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 2,a
        rom[0x103] = 0x97;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFB, cpu.getA(), "The A register should equal 0xFB.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB98
    @Test
    void res_3_b_test() {
        rom[0x100] = 0x06; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 3,b
        rom[0x103] = 0x98;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xF7, cpu.getB(), "The B register should equal 0xF7.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB99
    @Test
    void res_3_c_test() {
        rom[0x100] = 0x0E; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 3,c
        rom[0x103] = 0x99;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xF7, cpu.getC(), "The C register should equal 0xF7.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB9A
    @Test
    void res_3_d_test() {
        rom[0x100] = 0x16; // ld d,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 3,d
        rom[0x103] = 0x9A;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xF7, cpu.getD(), "The D register should equal 0xF7.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB9B
    @Test
    void res_3_e_test() {
        rom[0x100] = 0x1E; // ld e,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 3,e
        rom[0x103] = 0x9B;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xF7, cpu.getE(), "The E register should equal 0xF7.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB9C
    @Test
    void res_3_h_test() {
        rom[0x100] = 0x26; // ld h,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 3,h
        rom[0x103] = 0x9C;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xF7, cpu.getH(), "The H register should equal 0xF7.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB9D
    @Test
    void res_3_l_test() {
        rom[0x100] = 0x2E; // ld l,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 3,l
        rom[0x103] = 0x9D;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xF7, cpu.getL(), "The L register should equal 0xF7.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB9E
    @Test
    void res_3_hlp_test() {
        memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // res 3,(hl)
        rom[0x104] = 0x9E;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xF7, cpu.getHL(), "The value pointed to by the HL register should equal 0xF7.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCB9F
    @Test
    void res_3_a_test() {
        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 3,a
        rom[0x103] = 0x9F;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xF7, cpu.getA(), "The A register should equal 0xF7.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }
}
