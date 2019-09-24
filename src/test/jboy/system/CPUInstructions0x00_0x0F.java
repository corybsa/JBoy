package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0x00_0x0F {
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

    // op code 0x00
    @Test
    void nop_test() {
        rom[0x100] = 0x00; // nop

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x101, cpu.getPC(), "PC should equal 0x101");
    }

    // op code 0x01
    @Test
    void ld_bc_xx_test() {
        rom[0x100] = 0x01; // ld bc,0x895F
        rom[0x101] = 0x5F;
        rom[0x102] = 0x89;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x895F, cpu.getBC(), "The BC register should equal 0x895F");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103");
    }

    // op code 0x02
    @Test
    void ld_bc_a_test() {
        rom[0x100] = 0x01; // ld bc,0x7F50
        rom[0x101] = 0x50;
        rom[0x102] = 0x7F;
        rom[0x103] = 0x3E; // ld a,0x50
        rom[0x104] = 0x50;
        rom[0x105] = 0x02; // ld (bc),a

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x7F50, cpu.getBC(), "The BC register should equal 0x7F50.");

        cpu.tick();
        assertEquals(0x50, cpu.getA(), "The A register should equal 0x50.");

        cpu.tick();
        assertEquals(0x50, memory.getByteAt(cpu.getBC()), "The BC register should equal 0x50");
        assertEquals(0x106, cpu.getPC(), "PC should equal 0x106");
    }

    // op code 0x03
    @Test
    void inc_bc_test() {
        rom[0x100] = 0x03;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x01, cpu.getBC(), "The BC register should equal 0x01.");
    }

    // op code 0x04
    @Test
    void inc_b_test() {
        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x04; // inc b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x01, cpu.getB(), "The B register should equal 0x01");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x06; // ld b,0x0F
        rom[0x101] = 0x0F;
        rom[0x102] = 0x04; // inc b

        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.getB(), "The B register should equal 0x10");
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x06; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x04; // inc b

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getB(), "The B register should equal 0x00");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
    }

    // op code 0x05
    @Test
    void dec_b_test() {
        // Test a subtraction.
        rom[0x100] = 0x06; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x05; // dec b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.getB(), "The B register should equal 0xFE");
        assertEquals(CPU.FLAG_SUB, cpu.getF(), "The SUB flag should be set.");

        // Test a subtraction that results in zero.
        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        rom[0x100] = 0x06; // ld b,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0x05; // dec b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getB(), "The B register should equal 0x00");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "ZERO and SUB flags should be set.");

        // Test a half carry.
        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        rom[0x100] = 0x06; // ld b,0x10
        rom[0x101] = 0x10;
        rom[0x102] = 0x05; // dec b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x0F, cpu.getB(), "The B register should equal 0x0F");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The SUB and HALF_CARRY flags should be set.");

        // Test decrementing zero.
        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x05; // dec b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFF, cpu.getB(), "The B register should equal 0xFF");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The SUB and HALF_CARRY flags should be set.");
    }

    // op code 0x06
    @Test
    void ld_b_x_test() {
        rom[0x100] = 0x06; // ld b,0xF3
        rom[0x101] = 0xF3;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xF3, cpu.getB(), "The B register should equal 0xF3");
        assertEquals(0x102, cpu.getPC(), "PC should equal 0x102");
    }

    // op code 0x07
    @Test
    void rlca_test() {
        rom[0x100] = 0x3E; // ld a,0xAA
        rom[0x101] = 0xAA;
        rom[0x102] = 0x07; // rcla

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0b01010101, cpu.getA(), "The A register should equal AA shifted left 1 bit.");
        assertEquals(CPU.FLAG_CARRY, cpu.getF(), "The carry flag should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x104");
    }

    // op code 0x08
    @Test
    void ld_xxp_sp_test() {}

    // op code 0x09
    @Test
    void add_hl_bc_test() {}

    // op code 0x0A
    @Test
    void ld_a_bc_test() {
        // this is the value that BC will point to.
        rom[0x7F00] = 0x12;

        rom[0x100] = 0x01; // ld bc,0x7F00
        rom[0x101] = 0x00;
        rom[0x102] = 0x7F;
        rom[0x103] = 0x0A; // ld a,(bc)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x12, cpu.getA(), "The A register should equal 0x12");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x103");
    }

    // op code 0x0B
    @Test
    void dec_bc_test() {}

    // op code 0x0C
    @Test
    void inc_c_test() {}

    // op code 0x0D
    @Test
    void dec_c_test() {}

    // op code 0x0E
    @Test
    void ld_c_x_test() {}

    // op code 0x0F
    @Test
    void rrca_test() {}
}