package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xCBE0_0xCBEF {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

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
        rom = new int[0x7FFF];
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
    }

    @AfterEach
    void tearDown() {
        rom = null;
    }

    // op code 0xCBE0
    @Test
    void set_4_b_test() {
        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 4,b
        rom[0x103] = 0xE0;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.registers.B, "The B register should equal 0x10.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCBE1
    @Test
    void set_4_c_test() {
        rom[0x100] = 0x0E; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 4,c
        rom[0x103] = 0xE1;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.registers.C, "The C register should equal 0x10.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCBE2
    @Test
    void set_4_d_test() {
        rom[0x100] = 0x16; // ld d,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 4,d
        rom[0x103] = 0xE2;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.registers.D, "The D register should equal 0x10.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCBE3
    @Test
    void set_4_e_test() {
        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 4,e
        rom[0x103] = 0xE3;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.registers.E, "The E register should equal 0x10.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCBE4
    @Test
    void set_4_h_test() {
        rom[0x100] = 0x26; // ld h,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 4,h
        rom[0x103] = 0xE4;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.registers.H, "The H register should equal 0x10.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCBE5
    @Test
    void set_4_l_test() {
        rom[0x100] = 0x2E; // ld l,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 4,l
        rom[0x103] = 0xE5;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.registers.L, "The L register should equal 0x10.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCBE6
    @Test
    void set_4_hlp_test() {
        memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // set 4,(hl)
        rom[0x104] = 0xE6;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x10, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by the HL register should equal 0x10.");
        assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
    }

    // op code 0xCBE7
    @Test
    void set_4_a_test() {
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 4,a
        rom[0x103] = 0xE7;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.registers.A, "The A register should equal 0x10.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCBE8
    @Test
    void set_5_b_test() {
        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 5,b
        rom[0x103] = 0xE8;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x20, cpu.registers.B, "The B register should equal 0x20.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCBE9
    @Test
    void set_5_c_test() {
        rom[0x100] = 0x0E; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 5,c
        rom[0x103] = 0xE9;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x20, cpu.registers.C, "The C register should equal 0x20.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCBEA
    @Test
    void set_5_d_test() {
        rom[0x100] = 0x16; // ld d,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 5,d
        rom[0x103] = 0xEA;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x20, cpu.registers.D, "The D register should equal 0x20.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCBEB
    @Test
    void set_5_e_test() {
        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 5,e
        rom[0x103] = 0xEB;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x20, cpu.registers.E, "The E register should equal 0x20.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCBEC
    @Test
    void set_5_h_test() {
        rom[0x100] = 0x26; // ld h,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 5,h
        rom[0x103] = 0xEC;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x20, cpu.registers.H, "The H register should equal 0x20.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCBED
    @Test
    void set_5_l_test() {
        rom[0x100] = 0x2E; // ld l,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 5,l
        rom[0x103] = 0xED;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x20, cpu.registers.L, "The L register should equal 0x20.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCBEE
    @Test
    void set_5_hlp_test() {
        memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // set 5,(hl)
        rom[0x104] = 0xEE;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x20, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by the HL register should equal 0x20.");
        assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
    }

    // op code 0xCBEF
    @Test
    void set_5_a_test() {
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // set 5,a
        rom[0x103] = 0xEF;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x20, cpu.registers.A, "The A register should equal 0x20.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }
}
