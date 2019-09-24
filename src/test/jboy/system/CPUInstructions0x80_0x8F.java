package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CPUInstructions0x80_0x8F {
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

    // op code 0x80
    @Test
    void add_a_b_test() {}

    // op code 0x81
    @Test
    void add_a_c_test() {}

    // op code 0x82
    @Test
    void add_a_d_test() {}

    // op code 0x83
    @Test
    void add_a_e_test() {}

    // op code 0x84
    @Test
    void add_a_h_test() {}

    // op code 0x85
    @Test
    void add_a_l_test() {}

    // op code 0x86
    @Test
    void add_a_hlp_test() {}

    // op code 0x87
    @Test
    void add_a_a_test() {}

    // op code 0x88
    @Test
    void adc_a_b_test() {}

    // op code 0x89
    @Test
    void adc_a_c_test() {}

    // op code 0x8A
    @Test
    void adc_a_d_test() {}

    // op code 0x8B
    @Test
    void adc_a_e_test() {}

    // op code 0x8C
    @Test
    void adc_a_h_test() {}

    // op code 0x8D
    @Test
    void adc_a_l_test() {}

    // op code 0x8E
    @Test
    void adc_a_hlp_test() {}

    // op code 0x8F
    @Test
    void adc_a_a_test() {}
}
