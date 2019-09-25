package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CPUInstructions0xCB60_0xCB6F {
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

    // op code 0xCB60
    @Test
    void bit_4_b_test() {}

    // op code 0xCB61
    @Test
    void bit_4_c_test() {}

    // op code 0xCB62
    @Test
    void bit_4_d_test() {}

    // op code 0xCB63
    @Test
    void bit_4_e_test() {}

    // op code 0xCB64
    @Test
    void bit_4_h_test() {}

    // op code 0xCB65
    @Test
    void bit_4_l_test() {}

    // op code 0xCB66
    @Test
    void bit_4_hlp_test() {}

    // op code 0xCB67
    @Test
    void bit_4_a_test() {}

    // op code 0xCB68
    @Test
    void bit_5_b_test() {}

    // op code 0xCB69
    @Test
    void bit_5_c_test() {}

    // op code 0xCB6A
    @Test
    void bit_5_d_test() {}

    // op code 0xCB6B
    @Test
    void bit_5_e_test() {}

    // op code 0xCB6C
    @Test
    void bit_5_h_test() {}

    // op code 0xCB6D
    @Test
    void bit_5_l_test() {}

    // op code 0xCB6E
    @Test
    void bit_5_hlp_test() {}

    // op code 0xCB6F
    @Test
    void bit_5_a_test() {}
}
