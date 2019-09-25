package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CPUInstructions0xCBE0_0xCBEF {
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

    // op code 0xCBE0
    @Test
    void set_4_b_test() {}

    // op code 0xCBE1
    @Test
    void set_4_c_test() {}

    // op code 0xCBE2
    @Test
    void set_4_d_test() {}

    // op code 0xCBE3
    @Test
    void set_4_e_test() {}

    // op code 0xCBE4
    @Test
    void set_4_h_test() {}

    // op code 0xCBE5
    @Test
    void set_4_l_test() {}

    // op code 0xCBE6
    @Test
    void set_4_hlp_test() {}

    // op code 0xCBE7
    @Test
    void set_4_a_test() {}

    // op code 0xCBE8
    @Test
    void set_5_b_test() {}

    // op code 0xCBE9
    @Test
    void set_5_c_test() {}

    // op code 0xCBEA
    @Test
    void set_5_d_test() {}

    // op code 0xCBEB
    @Test
    void set_5_e_test() {}

    // op code 0xCBEC
    @Test
    void set_5_h_test() {}

    // op code 0xCBED
    @Test
    void set_5_l_test() {}

    // op code 0xCBEE
    @Test
    void set_5_hlp_test() {}

    // op code 0xCBEF
    @Test
    void set_5_a_test() {}
}
