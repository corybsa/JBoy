package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CPUInstructions0x50_0x5F {
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

    // op code 0x50
    @Test
    void ld_d_b_test() {}

    // op code 0x51
    @Test
    void ld_d_c_test() {}

    // op code 0x52
    @Test
    void ld_d_d_test() {}

    // op code 0x53
    @Test
    void ld_d_e_test() {}

    // op code 0x54
    @Test
    void ld_d_h_test() {}

    // op code 0x55
    @Test
    void ld_d_l_test() {}

    // op code 0x56
    @Test
    void ld_d_hlp_test() {}

    // op code 0x57
    @Test
    void ld_d_a_test() {}

    // op code 0x58
    @Test
    void ld_e_b_test() {}

    // op code 0x59
    @Test
    void ld_e_c_test() {}

    // op code 0x5A
    @Test
    void ld_e_d_test() {}

    // op code 0x5B
    @Test
    void ld_e_e_test() {}

    // op code 0x5C
    @Test
    void ld_e_h_test() {}

    // op code 0x5D
    @Test
    void ld_e_l_test() {}

    // op code 0x5E
    @Test
    void ld_e_hlp_test() {}

    // op code 0x5F
    @Test
    void ld_e_a_test() {}
}
