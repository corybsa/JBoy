package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CPUInstructions0xC0_0xCF {
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

    // op code 0xC0
    @Test
    void ret_nz_test() {}

    // op code 0xC1
    @Test
    void pop_bc_test() {}

    // op code 0xC2
    @Test
    void jp_nz_xx_test() {}

    // op code 0xC3
    @Test
    void jp_xx_test() {}

    // op code 0xC4
    @Test
    void call_nz_xx_test() {}

    // op code 0xC5
    @Test
    void push_bc_test() {}

    // op code 0xC6
    @Test
    void add_a_x_test() {}

    // op code 0xC7
    @Test
    void rst_00_test() {}

    // op code 0xC8
    @Test
    void ret_z_test() {}

    // op code 0xC9
    @Test
    void ret_test() {}

    // op code 0xCA
    @Test
    void jp_z_xx_test() {}

    // op code 0xCC
    @Test
    void call_z_xx_test() {}

    // op code 0xCD
    @Test
    void call_xx_test() {}

    // op code 0xCE
    @Test
    void adc_a_n_test() {}

    // op code 0xCF
    @Test
    void rst_08_test() {}
}
