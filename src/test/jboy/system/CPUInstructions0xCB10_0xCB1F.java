package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPUInstructions0xCB10_0xCB1F {
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

    // op code 0xCB10
    @Test
    void rl_b_test() {}

    // op code 0xCB11
    @Test
    void rl_c_test() {}

    // op code 0xCB12
    @Test
    void rl_d_test() {}

    // op code 0xCB13
    @Test
    void rl_e_test() {}

    // op code 0xCB14
    @Test
    void rl_h_test() {}

    // op code 0xCB15
    @Test
    void rl_l_test() {}

    // op code 0xCB16
    @Test
    void rl_hlp_test() {}

    // op code 0xCB17
    @Test
    void rl_a_test() {}

    // op code 0xCB18
    @Test
    void rr_b_test() {}

    // op code 0xCB19
    @Test
    void rr_c_test() {}

    // op code 0xCB1A
    @Test
    void rr_d_test() {}

    // op code 0xCB1B
    @Test
    void rr_e_test() {}

    // op code 0xCB1C
    @Test
    void rr_h_test() {}

    // op code 0xCB1D
    @Test
    void rr_l_test() {}

    // op code 0xCB1E
    @Test
    void rr_hlp_test() {}

    // op code 0xCB1F
    @Test
    void rr_a_test() {}
}
