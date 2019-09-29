package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0x20_0x2F {
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

    // op code 0x20
    @Test
    void jr_nz_x_test() {}

    // op code 0x21
    @Test
    void ld_hl_xx_test() {}

    // op code 0x22
    @Test
    void ldi_hlp_a_test() {}

    // op code 0x23
    @Test
    void inc_hl_test() {}

    // op code 0x24
    @Test
    void inc_h_test() {}

    // op code 0x25
    @Test
    void dec_h_test() {}

    // op code 0x26
    @Test
    void ld_h_x_test() {}

    // op code 0x27
    @Test
    void daa_test() {}

    // op code 0x28
    @Test
    void jr_z_x_test() {}

    // op code 0x29
    @Test
    void add_hl_hl_test() {
        rom[0x101] = 0x21; // ld hl,0x8A23
        rom[0x102] = 0x23;
        rom[0x103] = 0x8A;
        rom[0x104] = 0x29; // add hl,hl

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x1446, cpu.getHL(), "The HL register should equal 0x0605.");
        assertEquals(CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The HALF_CARRY and CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x2A
    @Test
    void ldi_a_hlp_test() {}

    // op code 0x2B
    @Test
    void dec_hl_test() {}

    // op code 0x2C
    @Test
    void inc_l_test() {}

    // op code 0x2D
    @Test
    void dec_l_test() {}

    // op code 0x2E
    @Test
    void ld_l_x_test() {}

    // op code 0x2F
    @Test
    void cpl_test() {}
}
