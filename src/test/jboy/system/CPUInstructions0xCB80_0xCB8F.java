package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPUInstructions0xCB80_0xCB8F {
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

    // op code 0xCB80
    @Test
    void res_0_b_test() {}

    // op code 0xCB81
    @Test
    void res_0_c_test() {}

    // op code 0xCB82
    @Test
    void res_0_d_test() {}

    // op code 0xCB83
    @Test
    void res_0_e_test() {}

    // op code 0xCB84
    @Test
    void res_0_h_test() {}

    // op code 0xCB85
    @Test
    void res_0_l_test() {}

    // op code 0xCB86
    @Test
    void res_0_hlp_test() {}

    // op code 0xCB87
    @Test
    void res_0_a_test() {}

    // op code 0xCB88
    @Test
    void res_1_b_test() {}

    // op code 0xCB89
    @Test
    void res_1_c_test() {}

    // op code 0xCB8A
    @Test
    void res_1_d_test() {}

    // op code 0xCB8B
    @Test
    void res_1_e_test() {}

    // op code 0xCB8C
    @Test
    void res_1_h_test() {}

    // op code 0xCB8D
    @Test
    void res_1_l_test() {}

    // op code 0xCB8E
    @Test
    void res_1_hlp_test() {}

    // op code 0xCB8F
    @Test
    void res_1_a_test() {}
}
