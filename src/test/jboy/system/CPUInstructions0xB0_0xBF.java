package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CPUInstructions0xB0_0xBF {
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

    // op code 0xB0
    @Test
    void or_b_test() {}

    // op code 0xB1
    @Test
    void or_c_test() {}

    // op code 0xB2
    @Test
    void or_d_test() {}

    // op code 0xB3
    @Test
    void or_e_test() {}

    // op code 0xB4
    @Test
    void or_h_test() {}

    // op code 0xB5
    @Test
    void or_l_test() {}

    // op code 0xB6
    @Test
    void or_hlp_test() {}

    // op code 0xB7
    @Test
    void or_a_test() {}

    // op code 0xB8
    @Test
    void cp_b_test() {}

    // op code 0xB9
    @Test
    void cp_c_test() {}

    // op code 0xBA
    @Test
    void cp_d_test() {}

    // op code 0xBB
    @Test
    void cp_e_test() {}

    // op code 0xBC
    @Test
    void cp_h_test() {}

    // op code 0xBD
    @Test
    void cp_l_test() {}

    // op code 0xBE
    @Test
    void cp_hlp_test() {}

    // op code 0xBF
    @Test
    void cp_a_test() {}
}
