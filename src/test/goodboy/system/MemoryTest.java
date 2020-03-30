package test.goodboy.system;

import goodboy.system.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MemoryTest {
    static private Memory memory;

    @BeforeAll
    static void testBeforeAll() {
        memory = new Memory();
        LCD lcd = new LCD(memory);
        GPU gpu = new GPU(memory, lcd);
        Timers timers = new Timers(memory);

        lcd.setDrawFunction((tiles) -> null);
        memory.setGpuRef(gpu);
    }

    @Test
    void romRangeTest() {
        int[] rom = new int[0x8000];
        rom[0x0000] = 0xBE;
        rom[0x7FFF] = 0xEF;

        memory.loadROM(rom);

        assertEquals(0xBE, memory.getByteAt(0x0000), "The value at 0x0000 should equal 0xBE.");
        assertEquals(0xEF, memory.getByteAt(0x7FFF), "The value at 0x7FFF should equal 0xEF.");
    }

    @Test
    void vramRangeTest() {
        memory.setByteAt(0x8000, 0xBE);
        memory.setByteAt(0x9FFF, 0xEF);

        assertEquals(0xBE, memory.getByteAt(0x8000), "The value at 0x8000 should equal 0xBE.");
        assertEquals(0xEF, memory.getByteAt(0x9FFF), "The value at 0x9FFF should equal 0xEF.");
    }

    @Test
    void sramRangeTest() {
        memory.setByteAt(0xA000, 0xBE);
        memory.setByteAt(0xBFFF, 0xEF);

        assertEquals(0xBE, memory.getByteAt(0xA000), "The value at 0xA000 should equal 0xBE.");
        assertEquals(0xEF, memory.getByteAt(0xBFFF), "The value at 0xBFFF should equal 0xEF.");
    }

    @Test
    void wramRangeTest() {
        memory.setByteAt(0xC000, 0xBE);
        memory.setByteAt(0xDFFF, 0xEF);

        assertEquals(0xBE, memory.getByteAt(0xC000), "The value at 0xC000 should equal 0xBE.");
        assertEquals(0xEF, memory.getByteAt(0xDFFF), "The value at 0xDFFF should equal 0xEF.");
    }

    @Test
    void eramRangeTest() {
        memory.setByteAt(0xE000, 0xBE);
        memory.setByteAt(0xFDFF, 0xEF);

        assertEquals(0xBE, memory.getByteAt(0xE000), "The value at 0xE000 should equal 0xBE.");
        assertEquals(0xEF, memory.getByteAt(0xFDFF), "The value at 0xFDFF should equal 0xEF.");
    }

    @Test
    void oamRangeTest() {
        memory.setByteAt(0xFE00, 0xBE);
        memory.setByteAt(0xFE9F, 0xEF);

        assertEquals(0xBE, memory.getByteAt(0xFE00), "The value at 0xFE00 should equal 0xBE.");
        assertEquals(0xEF, memory.getByteAt(0xFE9F), "The value at 0xFE9F should equal 0xEF.");
    }

    @Test
    void fea0_feffRangeTest() {
        memory.setByteAt(0xFEA0, 0xBE);
        memory.setByteAt(0xFEFF, 0xEF);

        // Reading from this area on DMG always returns 0.
        assertEquals(0x00, memory.getByteAt(0xFEA0), "The value at 0xFEA0 should equal 0x00.");
        assertEquals(0x00, memory.getByteAt(0xFEFF), "The value at 0xFEFF should equal 0x00.");
    }

    @Test
    void ioRangeTest() {
        memory.setByteAt(0xFF00, 0xBE);
        memory.setByteAt(0xFF4B, 0xEF);

        assertEquals(0xFE, memory.getByteAt(0xFF00), "The value at 0xFF00 should equal 0xBE.");
        assertEquals(0xEF, memory.getByteAt(0xFF4B), "The value at 0xFF4B should equal 0xEF.");
    }

    @Test
    void ff4c_ff7fRangeTest() {
        memory.setByteAt(0xFF4C, 0xBE);
        memory.setByteAt(0xFF7F, 0xEF);

        assertEquals(0xBE, memory.getByteAt(0xFF4C), "The value at 0xFF4C should equal 0xBE.");
        assertEquals(0xEF, memory.getByteAt(0xFF7F), "The value at 0xFF7F should equal 0xEF.");
    }

    @Test
    void hramRangeTest() {
        memory.setByteAt(0xFF80, 0xBE);
        memory.setByteAt(0xFFFE, 0xEF);

        assertEquals(0xBE, memory.getByteAt(0xFF80), "The value at 0xFF80 should equal 0xBE.");
        assertEquals(0xEF, memory.getByteAt(0xFFFE), "The value at 0xFFFE should equal 0xEF.");
    }

    @Test
    void imeRangeTest() {
        memory.setByteAt(0xFFFF, 0xBE);

        assertEquals(0xBE, memory.getByteAt(0xFFFF), "The value at 0xFFFF should equal 0xBE.");
    }
}
