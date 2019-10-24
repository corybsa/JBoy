package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0x80_0x8F {
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

    // op code 0x80
    @Test
    void add_a_b_test() {
        rom[0x100] = 0x3E; // ld a,0x3A
        rom[0x101] = 0x3A;
        rom[0x102] = 0x06; // ld b,0xC6
        rom[0x103] = 0xC6;
        rom[0x104] = 0x80; // add a,b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x81
    @Test
    void add_a_c_test() {
        rom[0x100] = 0x3E; // ld a,0x3A
        rom[0x101] = 0x3A;
        rom[0x102] = 0x0E; // ld c,0xC6
        rom[0x103] = 0xC6;
        rom[0x104] = 0x81; // add a,c

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x82
    @Test
    void add_a_d_test() {
        rom[0x100] = 0x3E; // ld a,0x3A
        rom[0x101] = 0x3A;
        rom[0x102] = 0x16; // ld d,0xC6
        rom[0x103] = 0xC6;
        rom[0x104] = 0x82; // add a,d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x83
    @Test
    void add_a_e_test() {
        rom[0x100] = 0x3E; // ld a,0x3A
        rom[0x101] = 0x3A;
        rom[0x102] = 0x1E; // ld e,0xC6
        rom[0x103] = 0xC6;
        rom[0x104] = 0x83; // add a,e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x84
    @Test
    void add_a_h_test() {
        rom[0x100] = 0x3E; // ld a,0x3A
        rom[0x101] = 0x3A;
        rom[0x102] = 0x26; // ld h,0xC6
        rom[0x103] = 0xC6;
        rom[0x104] = 0x84; // add a,h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x85
    @Test
    void add_a_l_test() {
        rom[0x100] = 0x3E; // ld a,0x3A
        rom[0x101] = 0x3A;
        rom[0x102] = 0x2E; // ld l,0xC6
        rom[0x103] = 0xC6;
        rom[0x104] = 0x85; // add a,l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x86
    @Test
    void add_a_hlp_test() {
        memory.setByteAt(0xC000, 0xC6);

        rom[0x100] = 0x3E; // ld a,0x3A
        rom[0x101] = 0x3A;
        rom[0x102] = 0x21; // ld hl,0xC000
        rom[0x103] = 0x00;
        rom[0x104] = 0xC0;
        rom[0x105] = 0x86; // add a,(hl)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x106, cpu.getPC(), "PC should equal 0x106.");
    }

    // op code 0x87
    @Test
    void add_a_a_test() {
        rom[0x100] = 0x3E; // ld a,0x80
        rom[0x101] = 0x80;
        rom[0x102] = 0x87; // add a,a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x88
    @Test
    void adc_a_b_test() {
        cpu.setFlags(CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0xE1
        rom[0x101] = 0xE1;
        rom[0x102] = 0x06; // ld b,0x1E
        rom[0x103] = 0x1E;
        rom[0x104] = 0x88; // adc a,b

        memory.loadROM(rom);
        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x89
    @Test
    void adc_a_c_test() {
        cpu.setFlags(CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0xE1
        rom[0x101] = 0xE1;
        rom[0x102] = 0x0E; // ld c,0x1E
        rom[0x103] = 0x1E;
        rom[0x104] = 0x89; // adc a,c

        memory.loadROM(rom);
        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x8A
    @Test
    void adc_a_d_test() {
        cpu.setFlags(CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0xE1
        rom[0x101] = 0xE1;
        rom[0x102] = 0x16; // ld d,0x1E
        rom[0x103] = 0x1E;
        rom[0x104] = 0x8A; // adc a,d

        memory.loadROM(rom);
        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x8B
    @Test
    void adc_a_e_test() {
        cpu.setFlags(CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0xE1
        rom[0x101] = 0xE1;
        rom[0x102] = 0x1E; // ld b,0x1E
        rom[0x103] = 0x1E;
        rom[0x104] = 0x8B; // adc a,b

        memory.loadROM(rom);
        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x8C
    @Test
    void adc_a_h_test() {
        cpu.setFlags(CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0xE1
        rom[0x101] = 0xE1;
        rom[0x102] = 0x26; // ld h,0x1E
        rom[0x103] = 0x1E;
        rom[0x104] = 0x8C; // adc a,h

        memory.loadROM(rom);
        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x8D
    @Test
    void adc_a_l_test() {
        cpu.setFlags(CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0xE1
        rom[0x101] = 0xE1;
        rom[0x102] = 0x2E; // ld l,0x1E
        rom[0x103] = 0x1E;
        rom[0x104] = 0x8D; // adc a,l

        memory.loadROM(rom);
        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x8E
    @Test
    void adc_a_hlp_test() {
        cpu.setFlags(CPU.FLAG_CARRY);
        memory.setByteAt(0xC000, 0x1E);

        rom[0x100] = 0x3E; // ld a,0xE1
        rom[0x101] = 0xE1;
        rom[0x102] = 0x21; // ld hl,0xC000
        rom[0x103] = 0x00;
        rom[0x104] = 0xC0;
        rom[0x105] = 0x8E; // adc a,(hl)

        memory.loadROM(rom);
        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x106, cpu.getPC(), "PC should equal 0x106.");
    }

    // op code 0x8F
    @Test
    void adc_a_a_test() {
        cpu.setFlags(CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0x80
        rom[0x101] = 0x80;
        rom[0x102] = 0x8F; // adc a,a

        memory.loadROM(rom);
        cpu.tick();
        cpu.tick();
        assertEquals(0x01, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }
}
