package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CPUInstructions0xE0_0xEF {
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

    // op code 0xE0
    @Test
    void ldh_xp_a_test() {}

    // op code 0xE1
    @Test
    void pop_hl_test() {}

    // op code 0xE2
    @Test
    void ld_cp_a_test() {}

    // op code 0xE5
    @Test
    void push_hl_test() {}

    // op code 0xE6
    @Test
    void and_x_test() {}

    // op code 0xE7
    @Test
    void rst_20_test() {}

    // op code 0xE8
    @Test
    void add_sp_x_test() {}

    // op code 0xE9
    @Test
    void jp_hlp_test() {}

    // op code 0xEA
    @Test
    void ld_xxp_a_test() {}

    // op code 0xEE
    @Test
    void xor_x_test() {}

    // op code 0xEF
    @Test
    void rst_28_test() {}
}
