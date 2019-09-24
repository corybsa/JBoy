package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CPUInstructions0xD0_0xDF {
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

    // op code 0xD0
    @Test
    void ret_nc_test() {}

    // op code 0xD1
    @Test
    void pop_de_test() {}

    // op code 0xD2
    @Test
    void jp_nc_xx_test() {}

    // op code 0xD4
    @Test
    void call_nc_xx_test() {}

    // op code 0xD5
    @Test
    void push_de_test() {}

    // op code 0xD6
    @Test
    void sub_x_test() {}

    // op code 0xD7
    @Test
    void rst_10_test() {}

    // op code 0xD8
    @Test
    void ret_c_test() {}

    // op code 0xD9
    @Test
    void reti_test() {}

    // op code 0xDA
    @Test
    void jp_c_xx_test() {}

    // op code 0xDC
    @Test
    void call_c_xx_test() {}

    // op code 0xDE
    @Test
    void sbc_a_x_test() {}

    // op code 0xDF
    @Test
    void rst_18_test() {}
}
