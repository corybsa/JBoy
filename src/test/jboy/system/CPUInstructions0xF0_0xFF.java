package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CPUInstructions0xF0_0xFF {
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

    // op code 0xF0
    @Test
    void ldh_a_xp_test() {}

    // op code 0xF1
    @Test
    void pop_af_test() {}

    // op code 0xF2
    @Test
    void ld_a_cp_test() {}

    // op code 0xF3
    @Test
    void di_test() {}

    // op code 0xF5
    @Test
    void push_af_test() {}

    // op code 0xF6
    @Test
    void or_x_test() {}

    // op code 0xF7
    @Test
    void rst_30_test() {}

    // op code 0xF8
    @Test
    void ld_hl_sp_x_test() {}

    // op code 0xF9
    @Test
    void ld_sp_hl_test() {}

    // op code 0xFA
    @Test
    void ld_a_xx_test() {}

    // op code 0xFB
    @Test
    void ei_test() {}

    // op code 0xFE
    @Test
    void cp_x_test() {}

    // op code 0xFF
    @Test
    void rst_38_test() {}
}
