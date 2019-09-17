package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUTest {
    @Test
    void testNOP() {
        Memory memory = new Memory();
        CPU cpu = new CPU(memory);

        byte[] rom = new byte[0x7FFF];
        rom[0x100] = 0x00;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x101, cpu.getPC(), "PC should equal 0x101");
    }

    @Test
    void test_LD_BC_NN() {
        Memory memory = new Memory();
        CPU cpu = new CPU(memory);

        byte[] rom = new byte[0x7FFF];
        rom[0x100] = 0x01;
        rom[0x101] = 0x5F;
        rom[0x102] = (byte)0x89;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals((short)0x8900 + (byte)0x5F, cpu.getBC(), "The BC register should be equal to 0x895F");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103");
    }

    @Test
    void test_LD_B_N() {
        Memory memory = new Memory();
        CPU cpu = new CPU(memory);

        byte[] rom = new byte[0x7FFF];
        rom[0x100] = 0x06;
        rom[0x101] = (byte)0xF3;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals((byte)0xF3, cpu.getB(), "B should equal 0xF3");
        assertEquals(0x102, cpu.getPC(), "PC should equal 0x102");
    }
}