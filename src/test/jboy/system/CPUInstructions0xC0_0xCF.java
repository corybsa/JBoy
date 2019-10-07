package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xC0_0xCF {
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

    // op code 0xC0
    @Test
    void ret_nz_test() {}

    // op code 0xC1
    @Test
    void pop_bc_test() {}

    // op code 0xC2
    @Test
    void jp_nz_xx_test() {
        cpu.resetFlags(CPU.FLAG_ZERO);
        rom[0x100] = 0xC2; // jp nz,0x8000
        rom[0x101] = 0x00;
        rom[0x102] = 0x80;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103.");

        cpu.setFlags(CPU.FLAG_ZERO);
        cpu.setPC(0x100);
        cpu.tick();
        assertEquals(0x8000, cpu.getPC(), "The PC should equal 0x8000.");
    }

    // op code 0xC3
    @Test
    void jp_xx_test() {
        rom[0x100] = 0xC3; // jp 0x8000
        rom[0x101] = 0x00;
        rom[0x102] = 0x80;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x8000, cpu.getPC(), "The PC should equal 0x8000.");
    }

    // op code 0xC4
    @Test
    void call_nz_xx_test() {}

    // op code 0xC5
    @Test
    void push_bc_test() {}

    // op code 0xC6
    @Test
    void add_a_x_test() {
        rom[0x100] = 0x3E; // ld a,0x3A
        rom[0x101] = 0x3A;
        rom[0x102] = 0xC6; // add a,0xC6
        rom[0x103] = 0xC6;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xC7
    @Test
    void rst_00_test() {}

    // op code 0xC8
    @Test
    void ret_z_test() {}

    // op code 0xC9
    @Test
    void ret_test() {}

    // op code 0xCA
    @Test
    void jp_z_xx_test() {
        cpu.resetFlags(CPU.FLAG_ZERO);
        rom[0x100] = 0xCA; // jp z,0x8000
        rom[0x101] = 0x00;
        rom[0x102] = 0x80;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x8000, cpu.getPC(), "The PC should equal 0x8000.");

        cpu.setFlags(CPU.FLAG_ZERO);
        cpu.setPC(0x100);
        cpu.tick();
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103.");
    }

    // op code 0xCC
    @Test
    void call_z_xx_test() {}

    // op code 0xCD
    @Test
    void call_xx_test() {}

    // op code 0xCE
    @Test
    void adc_a_x_test() {
        cpu.setFlags(CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0xE1
        rom[0x101] = 0xE1;
        rom[0x102] = 0x8D; // adc a,0x1E
        rom[0x103] = 0x1E;

        memory.loadROM(rom);
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCF
    @Test
    void rst_08_test() {}
}
