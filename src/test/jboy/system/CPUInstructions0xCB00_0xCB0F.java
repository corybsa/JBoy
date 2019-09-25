package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPUInstructions0xCB00_0xCB0F {
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

    // op code 0xCB00
    @Test
    void rlc_b_test() {}

    // op code 0xCB01
    @Test
    void rlc_c_test() {}

    // op code 0xCB02
    @Test
    void rlc_d_test() {}

    // op code 0xCB03
    @Test
    void rlc_e_test() {}

    // op code 0xCB04
    @Test
    void rlc_h_test() {}

    // op code 0xCB05
    @Test
    void rlc_l_test() {}

    // op code 0xCB06
    @Test
    void rlc_hlp_test() {}

    // op code 0xCB07
    @Test
    void rlc_a_test() {}

    // op code 0xCB08
    @Test
    void rrc_b_test() {}

    // op code 0xCB09
    @Test
    void rrc_c_test() {}

    // op code 0xCB0A
    @Test
    void rrc_d_test() {}

    // op code 0xCB0B
    @Test
    void rrc_e_test() {}

    // op code 0xCB0C
    @Test
    void rrc_h_test() {}

    // op code 0xCB0D
    @Test
    void rrc_l_test() {}

    // op code 0xCB0E
    @Test
    void rrc_hlp_test() {}

    // op code 0xCB0F
    @Test
    void rrc_a_test() {}
}
