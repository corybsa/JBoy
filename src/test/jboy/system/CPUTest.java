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
    void setFlagsTest() {
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
    void resetFlagsTest() {
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
    void complexFlagLogicTest() {
        cpu.setFlags(CPU.FLAG_ZERO | CPU.FLAG_CARRY);
        assertEquals(0b10010000, cpu.getF(), "ZERO and CARRY flags should be set.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_CARRY);
        assertEquals(0b00000000, cpu.getF(), "No flags should be set.");

        cpu.setFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_CARRY);
        assertEquals(0b11010000, cpu.getF(), "ZERO, SUB and CARRY flags should be set.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_CARRY);
        assertEquals(0b01000000, cpu.getF(), "Only SUB should be set.");
    }
}