package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPUInstructions0xCBD0_0xCBDF {
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

    // op code 0xCBD0
    @Test
    void set_2_b_test() {}

    // op code 0xCBD1
    @Test
    void set_2_c_test() {}

    // op code 0xCBD2
    @Test
    void set_2_d_test() {}

    // op code 0xCBD3
    @Test
    void set_2_e_test() {}

    // op code 0xCBD4
    @Test
    void set_2_h_test() {}

    // op code 0xCBD5
    @Test
    void set_2_l_test() {}

    // op code 0xCBD6
    @Test
    void set_2_hlp_test() {}

    // op code 0xCBD7
    @Test
    void set_2_a_test() {}

    // op code 0xCBD8
    @Test
    void set_3_b_test() {}

    // op code 0xCBD9
    @Test
    void set_3_c_test() {}

    // op code 0xCBDA
    @Test
    void set_3_d_test() {}

    // op code 0xCBDB
    @Test
    void set_3_e_test() {}

    // op code 0xCBDC
    @Test
    void set_3_h_test() {}

    // op code 0xCBDD
    @Test
    void set_3_l_test() {}

    // op code 0xCBDE
    @Test
    void set_3_hlp_test() {}

    // op code 0xCBDF
    @Test
    void set_3_a_test() {}
}
