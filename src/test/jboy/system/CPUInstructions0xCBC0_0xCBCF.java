package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPUInstructions0xCBC0_0xCBCF {
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

    // op code 0xCBC0
    @Test
    void set_0_b_test() {}

    // op code 0xCBC1
    @Test
    void set_0_c_test() {}

    // op code 0xCBC2
    @Test
    void set_0_d_test() {}

    // op code 0xCBC3
    @Test
    void set_0_e_test() {}

    // op code 0xCBC4
    @Test
    void set_0_h_test() {}

    // op code 0xCBC5
    @Test
    void set_0_l_test() {}

    // op code 0xCBC6
    @Test
    void set_0_hlp_test() {}

    // op code 0xCBC7
    @Test
    void set_0_a_test() {}

    // op code 0xCBC8
    @Test
    void set_1_b_test() {}

    // op code 0xCBC9
    @Test
    void set_1_c_test() {}

    // op code 0xCBCA
    @Test
    void set_1_d_test() {}

    // op code 0xCBCB
    @Test
    void set_1_e_test() {}

    // op code 0xCBCC
    @Test
    void set_1_h_test() {}

    // op code 0xCBCD
    @Test
    void set_1_l_test() {}

    // op code 0xCBCE
    @Test
    void set_1_hlp_test() {}

    // op code 0xCBCF
    @Test
    void set_1_a_test() {}
}
