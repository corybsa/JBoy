package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CPUInstructions0xCB20_0xCB2F {
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

    // op code 0xCB20
    @Test
    void sla_b_test() {}

    // op code 0xCB21
    @Test
    void sla_c_test() {}

    // op code 0xCB22
    @Test
    void sla_d_test() {}

    // op code 0xCB23
    @Test
    void sla_e_test() {}

    // op code 0xCB24
    @Test
    void sla_h_test() {}

    // op code 0xCB25
    @Test
    void sla_l_test() {}

    // op code 0xCB26
    @Test
    void sla_hlp_test() {}

    // op code 0xCB27
    @Test
    void sla_a_test() {}

    // op code 0xCB28
    @Test
    void sra_b_test() {}

    // op code 0xCB29
    @Test
    void sra_c_test() {}

    // op code 0xCB2A
    @Test
    void sra_d_test() {}

    // op code 0xCB2B
    @Test
    void sra_e_test() {}

    // op code 0xCB2C
    @Test
    void sra_h_test() {}

    // op code 0xCB2D
    @Test
    void sra_l_test() {}

    // op code 0xCB2E
    @Test
    void sra_hlp_test() {}

    // op code 0xCB2F
    @Test
    void sra_a_test() {}
}
