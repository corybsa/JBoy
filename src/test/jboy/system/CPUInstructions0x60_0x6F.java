package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CPUInstructions0x60_0x6F {
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

    // op code 0x60
    @Test
    void ld_h_b_test() {}

    // op code 0x61
    @Test
    void ld_h_c_test() {}

    // op code 0x62
    @Test
    void ld_h_d_test() {}

    // op code 0x63
    @Test
    void ld_h_e_test() {}

    // op code 0x64
    @Test
    void ld_h_h_test() {}

    // op code 0x65
    @Test
    void ld_h_l_test() {}

    // op code 0x66
    @Test
    void ld_h_hlp_test() {}

    // op code 0x67
    @Test
    void ld_h_a_test() {}

    // op code 0x68
    @Test
    void ld_l_b_test() {}

    // op code 0x69
    @Test
    void ld_l_c_test() {}

    // op code 0x6A
    @Test
    void ld_l_d_test() {}

    // op code 0x6B
    @Test
    void ld_l_e_test() {}

    // op code 0x6C
    @Test
    void ld_l_h_test() {}

    // op code 0x6D
    @Test
    void ld_l_l_test() {}

    // op code 0x6E
    @Test
    void ld_l_hlp_test() {}

    // op code 0x6F
    @Test
    void ld_l_a_test() {}
}
