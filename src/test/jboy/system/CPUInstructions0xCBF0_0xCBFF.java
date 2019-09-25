package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CPUInstructions0xCBF0_0xCBFF {
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

    // op code 0xCBF0
    @Test
    void set_6_b_test() {}

    // op code 0xCBF1
    @Test
    void set_6_c_test() {}

    // op code 0xCBF2
    @Test
    void set_6_d_test() {}

    // op code 0xCBF3
    @Test
    void set_6_e_test() {}

    // op code 0xCBF4
    @Test
    void set_6_h_test() {}

    // op code 0xCBF5
    @Test
    void set_6_l_test() {}

    // op code 0xCBF6
    @Test
    void set_6_hlp_test() {}

    // op code 0xCBF7
    @Test
    void set_6_a_test() {}

    // op code 0xCBF8
    @Test
    void set_7_b_test() {}

    // op code 0xCBF9
    @Test
    void set_7_c_test() {}

    // op code 0xCBFA
    @Test
    void set_7_d_test() {}

    // op code 0xCBFB
    @Test
    void set_7_e_test() {}

    // op code 0xCBFC
    @Test
    void set_7_h_test() {}

    // op code 0xCBFD
    @Test
    void set_7_l_test() {}

    // op code 0xCBFE
    @Test
    void set_7_hlp_test() {}

    // op code 0xCBFF
    @Test
    void set_7_a_test() {}
}
