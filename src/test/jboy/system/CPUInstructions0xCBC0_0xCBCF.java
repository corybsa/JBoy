package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPUInstructions0xCBC0_0xCBCF {
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

    // op code 0xCBC0
    @Test
    void set_0_b_test() {
        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 0,b
        rom[0x103] = 0xC0;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x01, cpu.getB(), "The B register should equal 0x01.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBC1
    @Test
    void set_0_c_test() {
        rom[0x100] = 0x0E; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 0,c
        rom[0x103] = 0xC1;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x01, cpu.getC(), "The C register should equal 0x01.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBC2
    @Test
    void set_0_d_test() {
        rom[0x100] = 0x16; // ld d,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 0,d
        rom[0x103] = 0xC2;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x01, cpu.getD(), "The D register should equal 0x01.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBC3
    @Test
    void set_0_e_test() {
        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 0,e
        rom[0x103] = 0xC3;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x01, cpu.getE(), "The E register should equal 0x01.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBC4
    @Test
    void set_0_h_test() {
        rom[0x100] = 0x26; // ld h,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 0,h
        rom[0x103] = 0xC4;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x01, cpu.getH(), "The H register should equal 0x01.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBC5
    @Test
    void set_0_l_test() {
        rom[0x100] = 0x2E; // ld l,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 0,l
        rom[0x103] = 0xC5;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x01, cpu.getL(), "The L register should equal 0x01.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBC6
    @Test
    void set_0_hlp_test() {
        rom[0x7000] = 0x00; // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0x7000
        rom[0x101] = 0x00;
        rom[0x102] = 0x70;
        rom[0x103] = 0xCB; // set 0,(hl)
        rom[0x104] = 0xC6;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x01, cpu.getHL(), "The value pointed to by the HL register should equal 0x01.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCBC7
    @Test
    void set_0_a_test() {
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 0,a
        rom[0x103] = 0xC7;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x01, cpu.getA(), "The A register should equal 0x01.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBC8
    @Test
    void set_1_b_test() {
        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 1,b
        rom[0x103] = 0xC8;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x02, cpu.getB(), "The B register should equal 0x02.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBC9
    @Test
    void set_1_c_test() {
        rom[0x100] = 0x0E; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 1,c
        rom[0x103] = 0xC9;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x02, cpu.getC(), "The C register should equal 0x02.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBCA
    @Test
    void set_1_d_test() {
        rom[0x100] = 0x16; // ld d,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 1,d
        rom[0x103] = 0xCA;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x02, cpu.getD(), "The D register should equal 0x02.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBCB
    @Test
    void set_1_e_test() {
        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 1,e
        rom[0x103] = 0xCB;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x02, cpu.getE(), "The E register should equal 0x02.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBCC
    @Test
    void set_1_h_test() {
        rom[0x100] = 0x26; // ld h,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 1,h
        rom[0x103] = 0xCC;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x02, cpu.getH(), "The H register should equal 0x02.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBCD
    @Test
    void set_1_l_test() {
        rom[0x100] = 0x2E; // ld l,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 1,l
        rom[0x103] = 0xCD;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x02, cpu.getL(), "The L register should equal 0x02.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBCE
    @Test
    void set_1_hlp_test() {
        rom[0x7000] = 0x00; // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0x7000
        rom[0x101] = 0x00;
        rom[0x102] = 0x70;
        rom[0x103] = 0xCB; // set 1,(hl)
        rom[0x104] = 0xCE;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x02, cpu.getHL(), "The value pointed to by the HL register should equal 0x02.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCBCF
    @Test
    void set_1_a_test() {
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 1,a
        rom[0x103] = 0xCF;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x02, cpu.getA(), "The A register should equal 0x02.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }
}
