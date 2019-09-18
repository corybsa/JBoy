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
    static private byte[] rom;

    @BeforeAll
    static void testBeforeAll() {
        memory = new Memory();
        cpu = new CPU(memory);
    }

    @BeforeEach
    void setUp() {
        rom = new byte[0x7FFF];
    }

    @AfterEach
    void tearDown() {
        rom = null;
    }

    // op code 0x00
    @Test
    void testNOP() {
        rom[0x100] = 0x00;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x101, cpu.getPC(), "PC should equal 0x101");
    }

    // op code 0x01
    @Test
    void test_LD_BC_NN() {
        rom[0x100] = 0x01;
        rom[0x101] = 0x5F;
        rom[0x102] = (byte)0x89;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals((short)0x8900 + (byte)0x5F, cpu.getBC(), "The BC register should be equal to 0x895F");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103");
    }

    // TODO: Run this test
    // op code 0x02
    @Test
    void test_LD_BC_A() {
        rom[0x100] = 0x01; // load the next byte into BC
        rom[0x101] = (byte)0xAA;
        rom[0x102] = 0x02; // load the A into BC

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getBC(), "The BC register should be equal to 0x00");
    }

    // op code 0x06
    @Test
    void test_LD_B_N() {
        rom[0x100] = 0x06;
        rom[0x101] = (byte)0xF3;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals((byte)0xF3, cpu.getB(), "B should equal 0xF3");
        assertEquals(0x102, cpu.getPC(), "PC should equal 0x102");
    }
}