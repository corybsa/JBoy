package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CPUInstructions0x90_0x9F {
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

    // op code 0x90
    @Test
    void sub_b_test() {}

    // op code 0x91
    @Test
    void sub_c_test() {}

    // op code 0x92
    @Test
    void sub_d_test() {}

    // op code 0x93
    @Test
    void sub_e_test() {}

    // op code 0x94
    @Test
    void sub_h_test() {}

    // op code 0x95
    @Test
    void sub_l_test() {}

    // op code 0x96
    @Test
    void sub_hlp_test() {}

    // op code 0x97
    @Test
    void sub_a_test() {}

    // op code 0x98
    @Test
    void sbc_a_b_test() {}

    // op code 0x99
    @Test
    void sbc_a_c_test() {}

    // op code 0x9A
    @Test
    void sbc_a_d_test() {}

    // op code 0x9B
    @Test
    void sbc_a_e_test() {}

    // op code 0x9C
    @Test
    void sbc_a_h_test() {}

    // op code 0x9D
    @Test
    void sbc_a_l_test() {}

    // op code 0x9E
    @Test
    void sbc_a_hlp_test() {}

    // op code 0x9F
    @Test
    void sbc_a_a_test() {}
}
