package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPUInstructions0xCBA0_0xCBAF {
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

    // op code 0xCBA0
    @Test
    void res_4_b_test() {}

    // op code 0xCBA1
    @Test
    void res_4_c_test() {}

    // op code 0xCBA2
    @Test
    void res_4_d_test() {}

    // op code 0xCBA3
    @Test
    void res_4_e_test() {}

    // op code 0xCBA4
    @Test
    void res_4_h_test() {}

    // op code 0xCBA5
    @Test
    void res_4_l_test() {}

    // op code 0xCBA6
    @Test
    void res_4_hlp_test() {}

    // op code 0xCBA7
    @Test
    void res_4_a_test() {}

    // op code 0xCBA8
    @Test
    void res_5_b_test() {}

    // op code 0xCBA9
    @Test
    void res_5_c_test() {}

    // op code 0xCBAA
    @Test
    void res_5_d_test() {}

    // op code 0xCBAB
    @Test
    void res_5_e_test() {}

    // op code 0xCBAC
    @Test
    void res_5_h_test() {}

    // op code 0xCBAD
    @Test
    void res_5_l_test() {}

    // op code 0xCBAE
    @Test
    void res_5_hlp_test() {}

    // op code 0xCBAF
    @Test
    void res_5_a_test() {}
}
