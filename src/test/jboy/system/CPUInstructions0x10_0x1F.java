package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0x10_0x1F {
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

    // op code 0x10
    @Test
    void stop_test() {}

    // op code 0x11
    @Test
    void ld_de_xx_test() {}

    // op code 0x12
    @Test
    void ld_dep_a_test() {}

    // op code 0x13
    @Test
    void inc_de_test() {}

    // op code 0x14
    @Test
    void inc_d_test() {}

    // op code 0x15
    @Test
    void dec_d_test() {}

    // op code 0x16
    @Test
    void ld_d_x_test() {}

    // op code 0x17
    @Test
    void rla_test() {}

    // op code 0x18
    @Test
    void jr_x_test() {}

    // op code 0x19
    @Test
    void add_hl_de_test() {}

    // op code 0x1A
    @Test
    void ld_a_dep_test() {}

    // op code 0x1B
    @Test
    void dec_de_test() {}

    // op code 0x1C
    @Test
    void inc_e_test() {}

    // op code 0x1D
    @Test
    void dec_e_test() {}

    // op code 0x1E
    @Test
    void ld_e_n_test() {}

    // op code 0x1F
    @Test
    void rra_test() {}
}