package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPUInstructions0xCB50_0xCB5F {
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

    // op code 0xCB50
    @Test
    void bit_2_b_test() {}

    // op code 0xCB51
    @Test
    void bit_2_c_test() {}

    // op code 0xCB52
    @Test
    void bit_2_d_test() {}

    // op code 0xCB53
    @Test
    void bit_2_e_test() {}

    // op code 0xCB54
    @Test
    void bit_2_h_test() {}

    // op code 0xCB55
    @Test
    void bit_2_l_test() {}

    // op code 0xCB56
    @Test
    void bit_2_hlp_test() {}

    // op code 0xCB57
    @Test
    void bit_2_a_test() {}

    // op code 0xCB58
    @Test
    void bit_3_b_test() {}

    // op code 0xCB59
    @Test
    void bit_3_c_test() {}

    // op code 0xCB5A
    @Test
    void bit_3_d_test() {}

    // op code 0xCB5B
    @Test
    void bit_3_e_test() {}

    // op code 0xCB5C
    @Test
    void bit_3_h_test() {}

    // op code 0xCB5D
    @Test
    void bit_3_l_test() {}

    // op code 0xCB5E
    @Test
    void bit_3_hlp_test() {}

    // op code 0xCB5F
    @Test
    void bit_3_a_test() {}
}
