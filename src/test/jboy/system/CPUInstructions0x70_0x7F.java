package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CPUInstructions0x70_0x7F {
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

    // op code 0x70
    @Test
    void ld_hlp_b_test() {}

    // op code 0x71
    @Test
    void ld_hlp_c_test() {}

    // op code 0x72
    @Test
    void ld_hlp_d_test() {}

    // op code 0x73
    @Test
    void ld_hlp_e_test() {}

    // op code 0x74
    @Test
    void ld_hlp_h_test() {}

    // op code 0x75
    @Test
    void ld_hlp_l_test() {}

    // op code 0x76
    @Test
    void ld_hlp_hlp_test() {}

    // op code 0x77
    @Test
    void ld_hlp_a_test() {}

    // op code 0x78
    @Test
    void ld_a_b_test() {}

    // op code 0x79
    @Test
    void ld_a_c_test() {}

    // op code 0x7A
    @Test
    void ld_a_d_test() {}

    // op code 0x7B
    @Test
    void ld_a_e_test() {}

    // op code 0x7C
    @Test
    void ld_a_h_test() {}

    // op code 0x7D
    @Test
    void ld_a_l_test() {}

    // op code 0x7E
    @Test
    void ld_a_hlp_test() {}

    // op code 0x7F
    @Test
    void ld_a_a_test() {}
}
