package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CPUInstructions0xCB70_0xCB7F {
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

    // op code 0xCB70
    @Test
    void bit_6_b_test() {}

    // op code 0xCB71
    @Test
    void bit_6_c_test() {}

    // op code 0xCB72
    @Test
    void bit_6_d_test() {}

    // op code 0xCB73
    @Test
    void bit_6_e_test() {}

    // op code 0xCB74
    @Test
    void bit_6_h_test() {}

    // op code 0xCB75
    @Test
    void bit_6_l_test() {}

    // op code 0xCB76
    @Test
    void bit_6_hlp_test() {}

    // op code 0xCB77
    @Test
    void bit_6_a_test() {}

    // op code 0xCB78
    @Test
    void bit_7_b_test() {}

    // op code 0xCB79
    @Test
    void bit_7_c_test() {}

    // op code 0xCB7A
    @Test
    void bit_7_d_test() {}

    // op code 0xCB7B
    @Test
    void bit_7_e_test() {}

    // op code 0xCB7C
    @Test
    void bit_7_h_test() {}

    // op code 0xCB7D
    @Test
    void bit_7_l_test() {}

    // op code 0xCB7E
    @Test
    void bit_7_hlp_test() {}

    // op code 0xCB7F
    @Test
    void bit_7_a_test() {}
}
