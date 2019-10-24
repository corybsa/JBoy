package test.jboy.system;

import jboy.system.CPU;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUTest {
    static private CPU cpu;

    @BeforeAll
    static void testBeforeAll() {
        cpu = new CPU(null, null);
    }

    @BeforeEach
    void setUp() {
        cpu.setPC(0x100);
        cpu.setSP(0xFFFE);
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

    @Test
    void manipulateRegistersTest() {
        cpu.setA(0xAA);
        cpu.setB(0xBB);
        cpu.setC(0xCC);
        cpu.setD(0xDD);
        cpu.setE(0xEE);
        cpu.setF(0xFF);
        cpu.setH(0x11);
        cpu.setL(0x22);
        assertEquals(0xAA, cpu.getA(), "The A register should equal 0xAA");
        assertEquals(0xBB, cpu.getB(), "The B register should equal 0xBB");
        assertEquals(0xCC, cpu.getC(), "The C register should equal 0xCC");
        assertEquals(0xDD, cpu.getD(), "The D register should equal 0xDD");
        assertEquals(0xEE, cpu.getE(), "The E register should equal 0xEE");
        assertEquals(0xF0, cpu.getF(), "The F register should equal 0xF0");
        assertEquals(0x11, cpu.getH(), "The H register should equal 0x11");
        assertEquals(0x22, cpu.getL(), "The L register should equal 0x22");
        assertEquals(0xAAF0, cpu.getAF(), "THE AF register should equal 0xAAF0");
        assertEquals(0xBBCC, cpu.getBC(), "THE BC register should equal 0xBBCC");
        assertEquals(0xDDEE, cpu.getDE(), "THE DE register should equal 0xDDEE");
        assertEquals(0x1122, cpu.getHL(), "THE HL register should equal 0x1122");

        cpu.setAF(0x00);
        cpu.setBC(0x00);
        cpu.setDE(0x00);
        cpu.setHL(0x00);
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00");
        assertEquals(0x00, cpu.getB(), "The B register should equal 0x00");
        assertEquals(0x00, cpu.getC(), "The C register should equal 0x00");
        assertEquals(0x00, cpu.getD(), "The D register should equal 0x00");
        assertEquals(0x00, cpu.getE(), "The E register should equal 0x00");
        assertEquals(0x00, cpu.getF(), "The F register should equal 0x00");
        assertEquals(0x00, cpu.getH(), "The H register should equal 0x00");
        assertEquals(0x00, cpu.getL(), "The L register should equal 0x00");
        assertEquals(0x0000, cpu.getAF(), "THE AF register should equal 0x0000");
        assertEquals(0x0000, cpu.getBC(), "THE BC register should equal 0x0000");
        assertEquals(0x0000, cpu.getDE(), "THE DE register should equal 0x0000");
        assertEquals(0x0000, cpu.getHL(), "THE HL register should equal 0x0000");
    }
}