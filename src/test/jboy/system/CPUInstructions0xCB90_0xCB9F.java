package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CPUInstructions0xCB90_0xCB9F {
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

    // op code 0xCB90
    @Test
    void res_2_b_test() {}

    // op code 0xCB91
    @Test
    void res_2_c_test() {}

    // op code 0xCB92
    @Test
    void res_2_d_test() {}

    // op code 0xCB93
    @Test
    void res_2_e_test() {}

    // op code 0xCB94
    @Test
    void res_2_h_test() {}

    // op code 0xCB95
    @Test
    void res_2_l_test() {}

    // op code 0xCB96
    @Test
    void res_2_hlp_test() {}

    // op code 0xCB97
    @Test
    void res_2_a_test() {}

    // op code 0xCB98
    @Test
    void res_3_b_test() {}

    // op code 0xCB99
    @Test
    void res_3_c_test() {}

    // op code 0xCB9A
    @Test
    void res_3_d_test() {}

    // op code 0xCB9B
    @Test
    void res_3_e_test() {}

    // op code 0xCB9C
    @Test
    void res_3_h_test() {}

    // op code 0xCB9D
    @Test
    void res_3_l_test() {}

    // op code 0xCB9E
    @Test
    void res_3_hlp_test() {}

    // op code 0xCB9F
    @Test
    void res_3_a_test() {}
}
