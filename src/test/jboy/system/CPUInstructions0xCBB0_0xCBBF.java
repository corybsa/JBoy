package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xCBB0_0xCBBF {
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

    // op code 0xCBB0
    @Test
    void res_6_b_test() {
        rom[0x100] = 0x06; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 6,b
        rom[0x103] = 0xB0;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xBF, cpu.getB(), "The B register should equal 0xBF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBB1
    @Test
    void res_6_c_test() {
        rom[0x100] = 0x0E; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 6,c
        rom[0x103] = 0xB1;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xBF, cpu.getC(), "The C register should equal 0xBF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBB2
    @Test
    void res_6_d_test() {
        rom[0x100] = 0x16; // ld d,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 6,d
        rom[0x103] = 0xB2;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xBF, cpu.getD(), "The D register should equal 0xBF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBB3
    @Test
    void res_6_e_test() {
        rom[0x100] = 0x1E; // ld e,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 6,e
        rom[0x103] = 0xB3;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xBF, cpu.getE(), "The E register should equal 0xBF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBB4
    @Test
    void res_6_h_test() {
        rom[0x100] = 0x26; // ld h,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 6,h
        rom[0x103] = 0xB4;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xBF, cpu.getH(), "The H register should equal 0xBF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBB5
    @Test
    void res_6_l_test() {
        rom[0x100] = 0x2E; // ld l,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 6,l
        rom[0x103] = 0xB5;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xBF, cpu.getL(), "The L register should equal 0xBF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBB6
    @Test
    void res_6_hlp_test() {
        memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // res 6,(hl)
        rom[0x104] = 0xB6;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xBF, memory.getByteAt(cpu.getHL()), "The value pointed to by the HL register should equal 0xBF.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCBB7
    @Test
    void res_6_a_test() {
        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 6,a
        rom[0x103] = 0xB7;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xBF, cpu.getA(), "The A register should equal 0xBF.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBB8
    @Test
    void res_7_b_test() {
        rom[0x100] = 0x06; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 7,b
        rom[0x103] = 0xB8;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x7F, cpu.getB(), "The B register should equal 0x7F.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBB9
    @Test
    void res_7_c_test() {
        rom[0x100] = 0x0E; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 7,c
        rom[0x103] = 0xB9;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x7F, cpu.getC(), "The C register should equal 0x7F.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBBA
    @Test
    void res_7_d_test() {
        rom[0x100] = 0x16; // ld d,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 7,d
        rom[0x103] = 0xBA;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x7F, cpu.getD(), "The D register should equal 0x7F.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBBB
    @Test
    void res_7_e_test() {
        rom[0x100] = 0x1E; // ld e,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 7,e
        rom[0x103] = 0xBB;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x7F, cpu.getE(), "The E register should equal 0x7F.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBBC
    @Test
    void res_7_h_test() {
        rom[0x100] = 0x26; // ld h,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 7,h
        rom[0x103] = 0xBC;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x7F, cpu.getH(), "The H register should equal 0x7F.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBBD
    @Test
    void res_7_l_test() {
        rom[0x100] = 0x2E; // ld l,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 7,l
        rom[0x103] = 0xBD;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x7F, cpu.getL(), "The L register should equal 0x7F.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCBBE
    @Test
    void res_7_hlp_test() {
        memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // res 7,(hl)
        rom[0x104] = 0xBE;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x7F, memory.getByteAt(cpu.getHL()), "The value pointed to by the HL register should equal 0x7F.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCBBF
    @Test
    void res_7_a_test() {
        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // res 7,a
        rom[0x103] = 0xBF;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x7F, cpu.getA(), "The A register should equal 0x7F.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }
}
