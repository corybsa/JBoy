package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUTest {
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

    @Test
    void testSetFlags() {
        cpu.setFlags(CPU.FLAG_ZERO);
        assertEquals(0b10000000, cpu.getF(), "Only ZERO flag should be set.");

        cpu.setFlags(CPU.FLAG_SUB);
        assertEquals(0b11000000, cpu.getF(), "ZERO and SUB flag should be set");

        cpu.setFlags(CPU.FLAG_HALF);
        assertEquals(0b11100000, cpu.getF(), "ZERO, SUB and HALF_CARRY flags should be set.");

        cpu.setFlags(CPU.FLAG_CARRY);
        assertEquals(0b11110000, cpu.getF(), "All flags should be set.");
    }

    @Test
    void testResetFlags() {
        cpu.setFlags(0b11110000);
        assertEquals(0b11110000, cpu.getF(), "All flags should be set.");

        cpu.resetFlags(CPU.FLAG_ZERO);
        assertEquals(0b01110000, cpu.getF(), "SUB, HALF_CARRY and CARRY flags should be set.");

        cpu.resetFlags(CPU.FLAG_SUB);
        assertEquals(0b00110000, cpu.getF(), "HALF_CARRY and CARRY flags should be set.");

        cpu.resetFlags(CPU.FLAG_HALF);
        assertEquals(0b00010000, cpu.getF(), "Only HALF_CARRY flag should be set.");

        cpu.resetFlags(CPU.FLAG_CARRY);
        assertEquals(0b00000000, cpu.getF(), "No flags should be set.");
    }

    @Test
    void testComplexFlagLogic() {
        cpu.setFlags(CPU.FLAG_ZERO | CPU.FLAG_CARRY);
        assertEquals(0b10010000, cpu.getF(), "ZERO and CARRY flags should be set.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_CARRY);
        assertEquals(0b00000000, cpu.getF(), "No flags should be set.");

        cpu.setFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_CARRY);
        assertEquals(0b11010000, cpu.getF(), "ZERO, SUB and CARRY flags should be set.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_CARRY);
        assertEquals(0b01000000, cpu.getF(), "Only SUB should be set.");
    }

    // op code 0x00
    @Test
    void testNOP() {
        rom[0x100] = 0x00; // nop

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x101, cpu.getPC(), "PC should equal 0x101");
    }

    // op code 0x01
    @Test
    void test_LD_BC_NN() {
        rom[0x100] = 0x01; // ld bc,0x895F
        rom[0x101] = 0x5F;
        rom[0x102] = 0x89;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x895F, cpu.getBC(), "The BC register should be equal to 0x895F");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103");
    }

    // op code 0x02
    @Test
    void test_LD_BC_A() {
        rom[0x100] = 0x01; // ld bc,0x7F50
        rom[0x101] = 0x50;
        rom[0x102] = 0x7F;
        rom[0x103] = 0x3E; // ld a,0x50
        rom[0x104] = 0x50;
        rom[0x105] = 0x02; // ld (bc),a

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x7F50, cpu.getBC(), "The BC register should be equal to 0x7F50.");

        cpu.tick();
        assertEquals(0x50, cpu.getA(), "The A register should equal 0x50.");

        cpu.tick();
        assertEquals(0x50, memory.getByteAt(cpu.getBC()), "The BC register should be equal to 0x50");
        assertEquals(0x106, cpu.getPC(), "PC should equal 0x106");
    }

    // op code 0x06
    @Test
    void test_LD_B_N() {
        rom[0x100] = 0x06; // ld b,0xF3
        rom[0x101] = 0xF3;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xF3, cpu.getB(), "B should equal 0xF3");
        assertEquals(0x102, cpu.getPC(), "PC should equal 0x102");
    }

    // op code 0x07
    @Test
    void test_RLCA() {
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

    // op code 0x0A
    @Test
    void test_LD_A_BC() {
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

    // op code 0x3E
    @Test
    void test_LD_A_N() {
        rom[0x100] = 0x3E; // ld a,0xA9
        rom[0x101] = 0xA9;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xA9, cpu.getA(), "The A register should equal 0xA9");
        assertEquals(0x102, cpu.getPC(), "PC should equal 0x102");
    }
}