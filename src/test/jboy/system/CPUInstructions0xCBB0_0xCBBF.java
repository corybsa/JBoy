package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPUInstructions0xCBB0_0xCBBF {
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

    // op code 0xCBB0
    @Test
    void res_6_b_test() {}

    // op code 0xCBB1
    @Test
    void res_6_c_test() {}

    // op code 0xCBB2
    @Test
    void res_6_d_test() {}

    // op code 0xCBB3
    @Test
    void res_6_e_test() {}

    // op code 0xCBB4
    @Test
    void res_6_h_test() {}

    // op code 0xCBB5
    @Test
    void res_6_l_test() {}

    // op code 0xCBB6
    @Test
    void res_6_hlp_test() {}

    // op code 0xCBB7
    @Test
    void res_6_a_test() {}

    // op code 0xCBB8
    @Test
    void res_7_b_test() {}

    // op code 0xCBB9
    @Test
    void res_7_c_test() {}

    // op code 0xCBBA
    @Test
    void res_7_d_test() {}

    // op code 0xCBBB
    @Test
    void res_7_e_test() {}

    // op code 0xCBBC
    @Test
    void res_7_h_test() {}

    // op code 0xCBBD
    @Test
    void res_7_l_test() {}

    // op code 0xCBBE
    @Test
    void res_7_hlp_test() {}

    // op code 0xCBBF
    @Test
    void res_7_a_test() {}
}
