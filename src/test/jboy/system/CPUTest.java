package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUTest {
    static private Memory memory;
    static private CPU cpu;

    @BeforeAll
    static void testBeforeAll() {
        memory = new Memory();
        LCD lcd = new LCD(memory);
        GPU gpu = new GPU(memory, lcd);
        Timers timers = new Timers(memory);

        lcd.setDrawFunction((tiles) -> null);
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

    @Test
    void nestedInterruptTest() {
        int[] rom = new int[0x800000];

        // vblank interrupt
        rom[0x40] = 0x3E; // ld a, 0x42
        rom[0x41] = 0x42;
        rom[0x42] = 0xFB; // ei
        rom[0x43] = 0x00; // nop
        rom[0x44] = 0xC9; // ret

        // joypad interrupt
        rom[0x60] = 0x0E; // ld c, 0x42
        rom[0x61] = 0x42;
        rom[0x62] = 0xC9; // ret

        // main
        rom[0x100] = 0x06; // ld b, 0x42
        rom[0x101] = 0x42;
        rom[0x102] = 0xFB; // ei
        rom[0x103] = 0x00; // nop
        rom[0x104] = 0x76; // halt

        memory.loadROM(rom);

        int flags = memory.getByteAt(IORegisters.INTERRUPT_FLAGS);
        int ie = memory.getByteAt(IORegisters.INTERRUPT_ENABLE);

        // enable vblank
        memory.setByteAt(IORegisters.INTERRUPT_FLAGS, flags | Interrupts.VBLANK);
        memory.setByteAt(IORegisters.INTERRUPT_ENABLE, ie | Interrupts.VBLANK);

        cpu.tick(); // ld b, 0x42
        assertEquals(0x42, cpu.registers.B, "B should be 0x42");
        assertEquals(0x102, cpu.registers.PC, "PC should be 0x102");

        cpu.tick(); // ei
        assertEquals(0x103, cpu.registers.PC, "PC should be 0x103");

        cpu.tick(); // nop
        assertTrue(cpu.getIME(), "IME should be enabled on the cycle after ei");
        assertEquals(0x104, cpu.registers.PC, "PC should be 0x104");

        cpu.tick(); // jump to interrupt vector
        assertEquals(0x40, cpu.registers.PC, "PC should be 0x40");
        assertFalse(cpu.getIME(), "IME should be disabled after calling an interrupt");
        assertEquals(0x01, memory.getByteAt(0xFFFD), "0x0104 should be pushed to the stack");
        assertEquals(0x04, memory.getByteAt(0xFFFC), "0x0104 should be pushed to the stack");


        // should be in vblank interrupt vector
        cpu.tick(); // ld a, 0x42
        assertEquals(0x42, cpu.registers.A, "A should be 0x42");
        assertEquals(0x42, cpu.registers.PC, "PC should be 0x42");

        // simulating a joypad interrupt enable
        memory.setByteAt(IORegisters.INTERRUPT_FLAGS, flags | Interrupts.JOYPAD);
        memory.setByteAt(IORegisters.INTERRUPT_ENABLE, ie | Interrupts.JOYPAD);

        cpu.tick(); // ei
        assertEquals(0x43, cpu.registers.PC, "PC should be 0x103");

        cpu.tick(); // nop
        assertTrue(cpu.getIME(), "IME should be enabled on the cycle after ei");
        assertEquals(0x44, cpu.registers.PC, "PC should be 0x44");

        cpu.tick(); // jump to interrupt vector
        assertEquals(0x60, cpu.registers.PC, "PC should be 0x60");
        assertFalse(cpu.getIME(), "IME should be disabled after calling an interrupt");
        assertEquals(0x00, memory.getByteAt(0xFFFB), "0x0044 should be pushed to the stack");
        assertEquals(0x44, memory.getByteAt(0xFFFA), "0x0044 should be pushed to the stack");


        // should be in joypad interrupt vector
        cpu.tick(); // ld c, 0x42
        assertEquals(0x42, cpu.registers.C, "C should be 0x42");
        assertEquals(0x62, cpu.registers.PC, "PC should be 0x62");

        cpu.tick(); // ret
        assertEquals(0x44, cpu.registers.PC, "should return to vblank interrupt vector and PC should be 0x44");


        // back in vblank
        cpu.tick(); // ret
        assertEquals(0x104, cpu.registers.PC, "should return to main and PC should be 0x104");

        cpu.tick(); // halt
        assertTrue(cpu.isHalted, "should halt cpu");
    }
}