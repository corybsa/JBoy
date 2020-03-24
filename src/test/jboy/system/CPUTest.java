package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUTest {
    static private CPU cpu;

    @BeforeAll
    static void testBeforeAll() {
        Memory memory = new Memory();
        Display display = new Display(memory);
        GPU gpu = new GPU(memory, display);
        Timers timers = new Timers(memory);

        display.setDrawFunction((tiles) -> null);
        memory.setGpuRef(gpu);

        cpu = new CPU(memory, gpu, timers);
    }

    @BeforeEach
    void setUp() {
        cpu.registers.PC = 0x100;
        cpu.registers.SP = 0xFFFE;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
    }

    @Test
    void setFlagsTest() {
        cpu.setFlags(CPU.Flags.ZERO);
        assertEquals(0b10000000, cpu.registers.F, "Only ZERO flag should be set.");

        cpu.setFlags(CPU.Flags.SUB);
        assertEquals(0b11000000, cpu.registers.F, "ZERO and SUB flag should be set");

        cpu.setFlags(CPU.Flags.HALF);
        assertEquals(0b11100000, cpu.registers.F, "ZERO, SUB and HALF_CARRY flags should be set.");

        cpu.setFlags(CPU.Flags.CARRY);
        assertEquals(0b11110000, cpu.registers.F, "All flags should be set.");
    }

    @Test
    void resetFlagsTest() {
        cpu.setFlags(0b11110000);
        assertEquals(0b11110000, cpu.registers.F, "All flags should be set.");

        cpu.resetFlags(CPU.Flags.ZERO);
        assertEquals(0b01110000, cpu.registers.F, "SUB, HALF_CARRY and CARRY flags should be set.");

        cpu.resetFlags(CPU.Flags.SUB);
        assertEquals(0b00110000, cpu.registers.F, "HALF_CARRY and CARRY flags should be set.");

        cpu.resetFlags(CPU.Flags.HALF);
        assertEquals(0b00010000, cpu.registers.F, "Only HALF_CARRY flag should be set.");

        cpu.resetFlags(CPU.Flags.CARRY);
        assertEquals(0b00000000, cpu.registers.F, "No flags should be set.");
    }

    @Test
    void complexFlagLogicTest() {
        cpu.setFlags(CPU.Flags.ZERO | CPU.Flags.CARRY);
        assertEquals(0b10010000, cpu.registers.F, "ZERO and CARRY flags should be set.");

        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.CARRY);
        assertEquals(0b00000000, cpu.registers.F, "No flags should be set.");

        cpu.setFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.CARRY);
        assertEquals(0b11010000, cpu.registers.F, "ZERO, SUB and CARRY flags should be set.");

        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.CARRY);
        assertEquals(0b01000000, cpu.registers.F, "Only SUB should be set.");
    }

    @Test
    void manipulateRegistersTest() {
        cpu.registers.A = 0xAA;
        cpu.registers.B = 0xBB;
        cpu.registers.C = 0xCC;
        cpu.registers.D = 0xDD;
        cpu.registers.E = 0xEE;
        cpu.registers.F = 0xFF & 0xF0;
        cpu.registers.H = 0x11;
        cpu.registers.L = 0x22;
        assertEquals(0xAA, cpu.registers.A, "The A register should equal 0xAA");
        assertEquals(0xBB, cpu.registers.B, "The B register should equal 0xBB");
        assertEquals(0xCC, cpu.registers.C, "The C register should equal 0xCC");
        assertEquals(0xDD, cpu.registers.D, "The D register should equal 0xDD");
        assertEquals(0xEE, cpu.registers.E, "The E register should equal 0xEE");
        assertEquals(0xF0, cpu.registers.F, "The F register should equal 0xF0");
        assertEquals(0x11, cpu.registers.H, "The H register should equal 0x11");
        assertEquals(0x22, cpu.registers.L, "The L register should equal 0x22");
        assertEquals(0xAAF0, cpu.registers.getAF(), "THE AF register should equal 0xAAF0");
        assertEquals(0xBBCC, cpu.registers.getBC(), "THE BC register should equal 0xBBCC");
        assertEquals(0xDDEE, cpu.registers.getDE(), "THE DE register should equal 0xDDEE");
        assertEquals(0x1122, cpu.registers.getHL(), "THE HL register should equal 0x1122");

        cpu.registers.setAF(0x00);
        cpu.registers.setBC(0x00);
        cpu.registers.setDE(0x00);
        cpu.registers.setHL(0x00);
        assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00");
        assertEquals(0x00, cpu.registers.B, "The B register should equal 0x00");
        assertEquals(0x00, cpu.registers.C, "The C register should equal 0x00");
        assertEquals(0x00, cpu.registers.D, "The D register should equal 0x00");
        assertEquals(0x00, cpu.registers.E, "The E register should equal 0x00");
        assertEquals(0x00, cpu.registers.F, "The F register should equal 0x00");
        assertEquals(0x00, cpu.registers.H, "The H register should equal 0x00");
        assertEquals(0x00, cpu.registers.L, "The L register should equal 0x00");
        assertEquals(0x0000, cpu.registers.getAF(), "THE AF register should equal 0x0000");
        assertEquals(0x0000, cpu.registers.getBC(), "THE BC register should equal 0x0000");
        assertEquals(0x0000, cpu.registers.getDE(), "THE DE register should equal 0x0000");
        assertEquals(0x0000, cpu.registers.getHL(), "THE HL register should equal 0x0000");
    }
}