package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CPUInstructions0xCB40_0xCB4F {
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

    // op code 0xCB40
    @Test
    void bit_0_b_test() {}

    // op code 0xCB41
    @Test
    void bit_0_c_test() {}

    // op code 0xCB42
    @Test
    void bit_0_d_test() {}

    // op code 0xCB43
    @Test
    void bit_0_e_test() {}

    // op code 0xCB44
    @Test
    void bit_0_h_test() {}

    // op code 0xCB45
    @Test
    void bit_0_l_test() {}

    // op code 0xCB46
    @Test
    void bit_0_hlp_test() {}

    // op code 0xCB47
    @Test
    void bit_0_a_test() {}

    // op code 0xCB48
    @Test
    void bit_1_b_test() {}

    // op code 0xCB49
    @Test
    void bit_1_c_test() {}

    // op code 0xCB4A
    @Test
    void bit_1_d_test() {}

    // op code 0xCB4B
    @Test
    void bit_1_e_test() {}

    // op code 0xCB4C
    @Test
    void bit_1_h_test() {}

    // op code 0xCB4D
    @Test
    void bit_1_l_test() {}

    // op code 0xCB4E
    @Test
    void bit_1_hlp_test() {}

    // op code 0xCB4F
    @Test
    void bit_1_a_test() {}
}
