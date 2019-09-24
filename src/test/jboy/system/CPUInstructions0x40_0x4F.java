package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CPUInstructions0x40_0x4F {
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

    // op code 0x40
    @Test
    void ld_b_b_test() {}

    // op code 0x41
    @Test
    void ld_b_c_test() {}

    // op code 0x42
    @Test
    void ld_b_d_test() {}

    // op code 0x43
    @Test
    void ld_b_e_test() {}

    // op code 0x44
    @Test
    void ld_b_h_test() {}

    // op code 0x45
    @Test
    void ld_b_l_test() {}

    // op code 0x46
    @Test
    void ld_b_hlp_test() {}

    // op code 0x47
    @Test
    void ld_b_a_test() {}

    // op code 0x48
    @Test
    void ld_c_b_test() {}

    // op code 0x49
    @Test
    void ld_c_c_test() {}

    // op code 0x4A
    @Test
    void ld_c_d_test() {}

    // op code 0x4B
    @Test
    void ld_c_e_test() {}

    // op code 0x4C
    @Test
    void ld_c_h_test() {}

    // op code 0x4D
    @Test
    void ld_c_l_test() {}

    // op code 0x4E
    @Test
    void ld_c_hlp_test() {}

    // op code 0x4F
    @Test
    void ld_c_a_test() {}
}
