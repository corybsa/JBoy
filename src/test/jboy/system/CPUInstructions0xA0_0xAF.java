package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CPUInstructions0xA0_0xAF {
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

    // op code 0xA0
    @Test
    void and_b_test() {}

    // op code 0xA1
    @Test
    void and_c_test() {}

    // op code 0xA2
    @Test
    void and_d_test() {}

    // op code 0xA3
    @Test
    void and_e_test() {}

    // op code 0xA4
    @Test
    void and_h_test() {}

    // op code 0xA5
    @Test
    void and_l_test() {}

    // op code 0xA6
    @Test
    void and_hlp_test() {}

    // op code 0xA7
    @Test
    void and_a_test() {}

    // op code 0xA8
    @Test
    void xor_b_test() {}

    // op code 0xA9
    @Test
    void xor_c_test() {}

    // op code 0xAA
    @Test
    void xor_d_test() {}

    // op code 0xAB
    @Test
    void xor_e_test() {}

    // op code 0xAC
    @Test
    void xor_h_test() {}

    // op code 0xAD
    @Test
    void xor_l_test() {}

    // op code 0xAE
    @Test
    void xor_hlp_test() {}

    // op code 0xAF
    @Test
    void xor_a_test() {}
}
