package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CPUInstructions0x30_0x3F {
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

    // op code 0x30
    @Test
    void jr_nc_x_test() {}

    // op code 0x31
    @Test
    void ld_sp_xx_test() {}

    // op code 0x32
    @Test
    void ldd_hlp_a_test() {}

    // op code 0x33
    @Test
    void inc_sp_test() {}

    // op code 0x34
    @Test
    void inc_hlp_test() {}

    // op code 0x35
    @Test
    void dec_hlp_test() {}

    // op code 0x36
    @Test
    void ld_hlp_x_test() {}

    // op code 0x37
    @Test
    void scf_test() {}

    // op code 0x38
    @Test
    void jr_c_x_test() {}

    // op code 0x39
    @Test
    void add_hl_sp_test() {}

    // op code 0x3A
    @Test
    void ldd_a_hlp_test() {}

    // op code 0x3B
    @Test
    void dec_sp_test() {}

    // op code 0x3C
    @Test
    void inc_a_test() {}

    // op code 0x3D
    @Test
    void dec_a_test() {}

    // op code 0x3E
    @Test
    void ld_a_x_test() {
        rom[0x100] = 0x3E; // ld a,0xA9
        rom[0x101] = 0xA9;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xA9, cpu.getA(), "The A register should equal 0xA9");
        assertEquals(0x102, cpu.getPC(), "PC should equal 0x102");
    }

    // op code 0x3F
    @Test
    void ccf_test() {}
}
