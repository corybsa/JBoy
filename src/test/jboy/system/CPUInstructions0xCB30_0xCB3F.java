package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CPUInstructions0xCB30_0xCB3F {
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

    // op code 0xCB30
    @Test
    void swap_b_test() {}

    // op code 0xCB31
    @Test
    void swap_c_test() {}

    // op code 0xCB32
    @Test
    void swap_d_test() {}

    // op code 0xCB33
    @Test
    void swap_e_test() {}

    // op code 0xCB34
    @Test
    void swap_h_test() {}

    // op code 0xCB35
    @Test
    void swap_l_test() {}

    // op code 0xCB36
    @Test
    void swap_hlp_test() {}

    // op code 0xCB37
    @Test
    void swap_a_test() {}

    // op code 0xCB38
    @Test
    void srl_b_test() {}

    // op code 0xCB39
    @Test
    void srl_c_test() {}

    // op code 0xCB3A
    @Test
    void srl_d_test() {}

    // op code 0xCB3B
    @Test
    void srl_e_test() {}

    // op code 0xCB3C
    @Test
    void srl_h_test() {}

    // op code 0xCB3D
    @Test
    void srl_l_test() {}

    // op code 0xCB3E
    @Test
    void srl_hlp_test() {}

    // op code 0xCB3F
    @Test
    void srl_a_test() {}
}
