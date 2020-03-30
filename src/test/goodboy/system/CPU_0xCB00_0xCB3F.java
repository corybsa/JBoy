package test.goodboy.system;

import goodboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPU_0xCB00_0xCB3F {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

    static class CPU_0xCB00_0xCB0F {
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

        // op code 0xCB00
        @Test
        void rlc_b_test() {
            rom[0x100] = 0x06; // ld b,0x85
            rom[0x101] = 0x85;
            rom[0x102] = 0xCB; // rlc b
            rom[0x103] = 0x00;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0B, cpu.registers.B, "The B register should equal 0x0B.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // rlc b
            rom[0x103] = 0x00;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.B, "The B register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB01
        @Test
        void rlc_c_test() {
            rom[0x100] = 0x0E; // ld c,0x85
            rom[0x101] = 0x85;
            rom[0x102] = 0xCB; // rlc c
            rom[0x103] = 0x01;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0B, cpu.registers.C, "The C register should equal 0x0B.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // rlc c
            rom[0x103] = 0x01;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.C, "The C register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB02
        @Test
        void rlc_d_test() {
            rom[0x100] = 0x16; // ld d,0x85
            rom[0x101] = 0x85;
            rom[0x102] = 0xCB; // rlc d
            rom[0x103] = 0x02;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0B, cpu.registers.D, "The D register should equal 0x0B.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // rlc c
            rom[0x103] = 0x02;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.D, "The D register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB03
        @Test
        void rlc_e_test() {
            rom[0x100] = 0x1E; // ld e,0x85
            rom[0x101] = 0x85;
            rom[0x102] = 0xCB; // rlc e
            rom[0x103] = 0x03;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0B, cpu.registers.E, "The E register should equal 0x0B.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // rlc e
            rom[0x103] = 0x03;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.E, "The E register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB04
        @Test
        void rlc_h_test() {
            rom[0x100] = 0x26; // ld h,0x85
            rom[0x101] = 0x85;
            rom[0x102] = 0xCB; // rlc h
            rom[0x103] = 0x04;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0B, cpu.registers.H, "The H register should equal 0x0B.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // rlc h
            rom[0x103] = 0x04;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.H, "The H register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB05
        @Test
        void rlc_l_test() {
            rom[0x100] = 0x2E; // ld l,0x85
            rom[0x101] = 0x85;
            rom[0x102] = 0xCB; // rlc l
            rom[0x103] = 0x05;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0B, cpu.registers.L, "The L register should equal 0x0B.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // rlc l
            rom[0x103] = 0x05;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.L, "The L register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB06
        @Test
        void rlc_hlp_test() {
            memory.setByteAt(0xC000, 0x85); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // rlc (hl)
            rom[0x104] = 0x06;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0B, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should be 0x0B.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should be 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB07
        @Test
        void rlc_a_test() {
            rom[0x100] = 0x3E; // ld a,0x85
            rom[0x101] = 0x85;
            rom[0x102] = 0xCB; // rlc a
            rom[0x103] = 0x07;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0B, cpu.registers.A, "The A register should equal 0x0B.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // rlc a
            rom[0x103] = 0x07;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB08
        @Test
        void rrc_b_test() {
            rom[0x100] = 0x06; // ld b,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // rrc b
            rom[0x103] = 0x08;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x80, cpu.registers.B, "The B register should equal 0x80.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // rrc b
            rom[0x103] = 0x08;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.B, "The B register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB09
        @Test
        void rrc_c_test() {
            rom[0x100] = 0x0E; // ld c,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // rrc c
            rom[0x103] = 0x09;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x80, cpu.registers.C, "The C register should equal 0x80.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // rrc c
            rom[0x103] = 0x09;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.C, "The C register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB0A
        @Test
        void rrc_d_test() {
            rom[0x100] = 0x16; // ld d,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // rrc d
            rom[0x103] = 0x0A;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x80, cpu.registers.D, "The D register should equal 0x80.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // rrc c
            rom[0x103] = 0x0A;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.D, "The D register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB0B
        @Test
        void rrc_e_test() {
            rom[0x100] = 0x1E; // ld e,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // rrc e
            rom[0x103] = 0x0B;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x80, cpu.registers.E, "The E register should equal 0x80.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // rrc e
            rom[0x103] = 0x0B;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.E, "The E register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB0C
        @Test
        void rrc_h_test() {
            rom[0x100] = 0x26; // ld h,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // rrc h
            rom[0x103] = 0x0C;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x80, cpu.registers.H, "The H register should equal 0x80.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // rrc h
            rom[0x103] = 0x0C;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.H, "The H register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB0D
        @Test
        void rrc_l_test() {
            rom[0x100] = 0x2E; // ld l,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // rrc l
            rom[0x103] = 0x0D;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x80, cpu.registers.L, "The L register should equal 0x80.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // rrc l
            rom[0x103] = 0x0D;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.L, "The L register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB0E
        @Test
        void rrc_hlp_test() {
            memory.setByteAt(0xC000, 0x01); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // rrc (hl)
            rom[0x104] = 0x0E;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x80, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should be 0x80.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should be 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB0F
        @Test
        void rrc_a_test() {
            rom[0x100] = 0x3E; // ld a,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // rrc a
            rom[0x103] = 0x0F;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x80, cpu.registers.A, "The A register should equal 0x80.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // rrc a
            rom[0x103] = 0x0F;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }
    }

    static class CPU_0xCB10_0xCB1F {
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

        // op code 0xCB10
        @Test
        void rl_b_test() {
            rom[0x100] = 0x06; // ld b,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // rl b
            rom[0x103] = 0x10;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.B, "The B register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0x11
            rom[0x101] = 0x11;
            rom[0x102] = 0xCB; // rl b
            rom[0x103] = 0x10;

            cpu.tick();
            cpu.tick();
            assertEquals(0x22, cpu.registers.B, "The B register should equal 0x22.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB11
        @Test
        void rl_c_test() {
            rom[0x100] = 0x0E; // ld c,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // rl c
            rom[0x103] = 0x11;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.C, "The C register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0x11
            rom[0x101] = 0x11;
            rom[0x102] = 0xCB; // rl c
            rom[0x103] = 0x11;

            cpu.tick();
            cpu.tick();
            assertEquals(0x22, cpu.registers.C, "The C register should equal 0x22.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB12
        @Test
        void rl_d_test() {
            rom[0x100] = 0x16; // ld d,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // rl d
            rom[0x103] = 0x12;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.D, "The D register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld d,0x11
            rom[0x101] = 0x11;
            rom[0x102] = 0xCB; // rl d
            rom[0x103] = 0x12;

            cpu.tick();
            cpu.tick();
            assertEquals(0x22, cpu.registers.D, "The D register should equal 0x22.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB13
        @Test
        void rl_e_test() {
            rom[0x100] = 0x1E; // ld e,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // rl e
            rom[0x103] = 0x13;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.E, "The E register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0x11
            rom[0x101] = 0x11;
            rom[0x102] = 0xCB; // rl e
            rom[0x103] = 0x13;

            cpu.tick();
            cpu.tick();
            assertEquals(0x22, cpu.registers.E, "The E register should equal 0x22.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB14
        @Test
        void rl_h_test() {
            rom[0x100] = 0x26; // ld h,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // rl h
            rom[0x103] = 0x14;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.H, "The H register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0x11
            rom[0x101] = 0x11;
            rom[0x102] = 0xCB; // rl h
            rom[0x103] = 0x14;

            cpu.tick();
            cpu.tick();
            assertEquals(0x22, cpu.registers.H, "The H register should equal 0x22.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB15
        @Test
        void rl_l_test() {
            rom[0x100] = 0x2E; // ld l,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // rl l
            rom[0x103] = 0x15;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.L, "The L register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0x11
            rom[0x101] = 0x11;
            rom[0x102] = 0xCB; // rl l
            rom[0x103] = 0x15;

            cpu.tick();
            cpu.tick();
            assertEquals(0x22, cpu.registers.L, "The L register should equal 0x22.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB16
        @Test
        void rl_hlp_test() {
            memory.setByteAt(0xC000, 0x80); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // rl (hl)
            rom[0x104] = 0x16;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            memory.setByteAt(0xC000, 0x11); // This is the value that HL will point to.

            cpu.tick();
            cpu.tick();
            assertEquals(0x22, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should equal 0x22.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB17
        @Test
        void rl_a_test() {
            rom[0x100] = 0x3E; // ld a,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // rl a
            rom[0x103] = 0x17;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0x11
            rom[0x101] = 0x11;
            rom[0x102] = 0xCB; // rl a
            rom[0x103] = 0x17;

            cpu.tick();
            cpu.tick();
            assertEquals(0x22, cpu.registers.A, "The A register should equal 0x22.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB18
        @Test
        void rr_b_test() {
            rom[0x100] = 0x06; // ld b,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // rr b
            rom[0x103] = 0x18;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.B, "The B register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0x8A
            rom[0x101] = 0x8A;
            rom[0x102] = 0xCB; // rr b
            rom[0x103] = 0x18;

            cpu.tick();
            cpu.tick();
            assertEquals(0x45, cpu.registers.B, "The B register should equal 0x45.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB19
        @Test
        void rr_c_test() {
            rom[0x100] = 0x0E; // ld c,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // rr c
            rom[0x103] = 0x19;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.C, "The C register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0x8A
            rom[0x101] = 0x8A;
            rom[0x102] = 0xCB; // rr c
            rom[0x103] = 0x19;

            cpu.tick();
            cpu.tick();
            assertEquals(0x45, cpu.registers.C, "The C register should equal 0x45.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB1A
        @Test
        void rr_d_test() {
            rom[0x100] = 0x16; // ld d,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // rr d
            rom[0x103] = 0x1A;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.D, "The D register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld d,0x8A
            rom[0x101] = 0x8A;
            rom[0x102] = 0xCB; // rr d
            rom[0x103] = 0x1A;

            cpu.tick();
            cpu.tick();
            assertEquals(0x45, cpu.registers.D, "The D register should equal 0x45.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB1B
        @Test
        void rr_e_test() {
            rom[0x100] = 0x1E; // ld e,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // rr e
            rom[0x103] = 0x1B;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.E, "The E register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0x8A
            rom[0x101] = 0x8A;
            rom[0x102] = 0xCB; // rr e
            rom[0x103] = 0x1B;

            cpu.tick();
            cpu.tick();
            assertEquals(0x45, cpu.registers.E, "The E register should equal 0x45.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB1C
        @Test
        void rr_h_test() {
            rom[0x100] = 0x26; // ld h,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // rr h
            rom[0x103] = 0x1C;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.H, "The H register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0x8A
            rom[0x101] = 0x8A;
            rom[0x102] = 0xCB; // rr h
            rom[0x103] = 0x1C;

            cpu.tick();
            cpu.tick();
            assertEquals(0x45, cpu.registers.H, "The H register should equal 0x45.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB1D
        @Test
        void rr_l_test() {
            rom[0x100] = 0x2E; // ld l,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // rr l
            rom[0x103] = 0x1D;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.L, "The L register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0x8A
            rom[0x101] = 0x8A;
            rom[0x102] = 0xCB; // rr l
            rom[0x103] = 0x1D;

            cpu.tick();
            cpu.tick();
            assertEquals(0x45, cpu.registers.L, "The L register should equal 0x45.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB1E
        @Test
        void rr_hlp_test() {
            memory.setByteAt(0xC000, 0x01); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // rr (hl)
            rom[0x104] = 0x1E;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            memory.setByteAt(0xC000, 0x8A); // This is the value that HL will point to.

            cpu.tick();
            cpu.tick();
            assertEquals(0x45, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should equal 0x45.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB1F
        @Test
        void rr_a_test() {
            rom[0x100] = 0x3E; // ld a,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // rr a
            rom[0x103] = 0x1F;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0x8A
            rom[0x101] = 0x8A;
            rom[0x102] = 0xCB; // rr a
            rom[0x103] = 0x1F;

            cpu.tick();
            cpu.tick();
            assertEquals(0x45, cpu.registers.A, "The A register should equal 0x45.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }
    }

    static class CPU_0xCB20_0xCB2F {
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

        // op code 0xCB20
        @Test
        void sla_b_test() {
            rom[0x100] = 0x06; // ld b,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // sla b
            rom[0x103] = 0x20;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.B, "The B register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // rl b
            rom[0x103] = 0x20;

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.B, "The B register should equal 0xFE.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB21
        @Test
        void sla_c_test() {
            rom[0x100] = 0x0E; // ld c,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // sla c
            rom[0x103] = 0x21;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.C, "The C register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // sla c
            rom[0x103] = 0x21;

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.C, "The C register should equal 0xFE.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB22
        @Test
        void sla_d_test() {
            rom[0x100] = 0x16; // ld d,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // sla d
            rom[0x103] = 0x22;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.D, "The D register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld d,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // sla d
            rom[0x103] = 0x22;

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.D, "The D register should equal 0xFE.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB23
        @Test
        void sla_e_test() {
            rom[0x100] = 0x1E; // ld e,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // sla e
            rom[0x103] = 0x23;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.E, "The E register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // sla e
            rom[0x103] = 0x23;

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.E, "The E register should equal 0xFE.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB24
        @Test
        void sla_h_test() {
            rom[0x100] = 0x26; // ld h,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // sla h
            rom[0x103] = 0x24;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.H, "The H register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // sla h
            rom[0x103] = 0x24;

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.H, "The H register should equal 0xFE.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB25
        @Test
        void sla_l_test() {
            rom[0x100] = 0x2E; // ld l,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // sla l
            rom[0x103] = 0x25;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.L, "The L register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // sla l
            rom[0x103] = 0x25;

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.L, "The L register should equal 0xFE.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB26
        @Test
        void sla_hlp_test() {
            memory.setByteAt(0xC000, 0x80); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // sla (hl)
            rom[0x104] = 0x26;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should equal 0xFE.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "No flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB27
        @Test
        void sla_a_test() {
            rom[0x100] = 0x3E; // ld a,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // sla a
            rom[0x103] = 0x27;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // sla a
            rom[0x103] = 0x27;

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.A, "The A register should equal 0xFE.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB28
        @Test
        void sra_b_test() {
            rom[0x100] = 0x06; // ld b,0x8A
            rom[0x101] = 0x8A;
            rom[0x102] = 0xCB; // sra b
            rom[0x103] = 0x28;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xC5, cpu.registers.B, "The B register should equal 0xC5.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // sra b
            rom[0x103] = 0x28;

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.B, "The B register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB29
        @Test
        void sra_c_test() {
            rom[0x100] = 0x0E; // ld c,0x8A
            rom[0x101] = 0x8A;
            rom[0x102] = 0xCB; // sra c
            rom[0x103] = 0x29;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xC5, cpu.registers.C, "The C register should equal 0xC5.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // sra c
            rom[0x103] = 0x29;

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.C, "The C register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB2A
        @Test
        void sra_d_test() {
            rom[0x100] = 0x16; // ld d,0x8A
            rom[0x101] = 0x8A;
            rom[0x102] = 0xCB; // sra d
            rom[0x103] = 0x2A;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xC5, cpu.registers.D, "The D register should equal 0xC5.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld d,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // sra d
            rom[0x103] = 0x2A;

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.D, "The D register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB2B
        @Test
        void sra_e_test() {
            rom[0x100] = 0x1E; // ld e,0x8A
            rom[0x101] = 0x8A;
            rom[0x102] = 0xCB; // sra e
            rom[0x103] = 0x2B;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xC5, cpu.registers.E, "The E register should equal 0xC5.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // sra e
            rom[0x103] = 0x2B;

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.E, "The E register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB2C
        @Test
        void sra_h_test() {
            rom[0x100] = 0x26; // ld h,0x8A
            rom[0x101] = 0x8A;
            rom[0x102] = 0xCB; // sra h
            rom[0x103] = 0x2C;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xC5, cpu.registers.H, "The H register should equal 0xC5.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // sra h
            rom[0x103] = 0x2C;

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.H, "The H register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB2D
        @Test
        void sra_l_test() {
            rom[0x100] = 0x2E; // ld l,0x8A
            rom[0x101] = 0x8A;
            rom[0x102] = 0xCB; // sra l
            rom[0x103] = 0x2D;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xC5, cpu.registers.L, "The L register should equal 0xC5.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // sra l
            rom[0x103] = 0x2D;

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.L, "The L register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB2E
        @Test
        void sra_hlp_test() {
            memory.setByteAt(0xC000, 0x8A); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // sra (hl)
            rom[0x104] = 0x2E;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xC5, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should equal 0xC5.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            memory.setByteAt(0xC000, 0x01); // This is the value that HL will point to.

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB2F
        @Test
        void sra_a_test() {
            rom[0x100] = 0x3E; // ld a,0x8A
            rom[0x101] = 0x8A;
            rom[0x102] = 0xCB; // sra a
            rom[0x103] = 0x2F;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xC5, cpu.registers.A, "The A register should equal 0xC5.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // sra a
            rom[0x103] = 0x2F;

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }
    }

    static class CPU_0xCB30_0xCB3F {
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

        // op code 0xCB30
        @Test
        void swap_b_test() {
            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // swap b
            rom[0x103] = 0x30;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.B, "The B register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0xF0
            rom[0x101] = 0xF0;
            rom[0x102] = 0xCB; // swap b
            rom[0x103] = 0x30;

            cpu.tick();
            cpu.tick();
            assertEquals(0x0F, cpu.registers.B, "The B register should equal 0x0F.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB31
        @Test
        void swap_c_test() {
            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // swap c
            rom[0x103] = 0x31;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.C, "The C register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0xF0
            rom[0x101] = 0xF0;
            rom[0x102] = 0xCB; // swap c
            rom[0x103] = 0x31;

            cpu.tick();
            cpu.tick();
            assertEquals(0x0F, cpu.registers.C, "The C register should equal 0x0F.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB32
        @Test
        void swap_d_test() {
            rom[0x100] = 0x16; // ld d,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // swap d
            rom[0x103] = 0x32;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.D, "The D register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld d,0xF0
            rom[0x101] = 0xF0;
            rom[0x102] = 0xCB; // swap d
            rom[0x103] = 0x32;

            cpu.tick();
            cpu.tick();
            assertEquals(0x0F, cpu.registers.D, "The D register should equal 0x0F.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB33
        @Test
        void swap_e_test() {
            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // swap e
            rom[0x103] = 0x33;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.E, "The E register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0xF0
            rom[0x101] = 0xF0;
            rom[0x102] = 0xCB; // swap e
            rom[0x103] = 0x33;

            cpu.tick();
            cpu.tick();
            assertEquals(0x0F, cpu.registers.E, "The E register should equal 0x0F.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB34
        @Test
        void swap_h_test() {
            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // swap h
            rom[0x103] = 0x34;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.H, "The H register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0xF0
            rom[0x101] = 0xF0;
            rom[0x102] = 0xCB; // swap h
            rom[0x103] = 0x34;

            cpu.tick();
            cpu.tick();
            assertEquals(0x0F, cpu.registers.H, "The H register should equal 0x0F.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB35
        @Test
        void swap_l_test() {
            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // swap l
            rom[0x103] = 0x35;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.L, "The L register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0xF0
            rom[0x101] = 0xF0;
            rom[0x102] = 0xCB; // swap l
            rom[0x103] = 0x35;

            cpu.tick();
            cpu.tick();
            assertEquals(0x0F, cpu.registers.L, "The L register should equal 0x0F.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB36
        @Test
        void swap_hlp_test() {
            memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // swap (hl)
            rom[0x104] = 0x36;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            memory.setByteAt(0xC000, 0xF0); // This is the value that HL will point to.

            cpu.tick();
            cpu.tick();
            assertEquals(0x0F, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should equal 0x0F.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB37
        @Test
        void swap_a_test() {
            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // swap a
            rom[0x103] = 0x37;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0xF0
            rom[0x101] = 0xF0;
            rom[0x102] = 0xCB; // swap a
            rom[0x103] = 0x37;

            cpu.tick();
            cpu.tick();
            assertEquals(0x0F, cpu.registers.A, "The A register should equal 0x0F.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB38
        @Test
        void srl_b_test() {
            rom[0x100] = 0x06; // ld b,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // srl b
            rom[0x103] = 0x38;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.B, "The B register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // srl b
            rom[0x103] = 0x38;

            cpu.tick();
            cpu.tick();
            assertEquals(0x7F, cpu.registers.B, "The B register should equal 0x00.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB39
        @Test
        void srl_c_test() {
            rom[0x100] = 0x0E; // ld c,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // srl c
            rom[0x103] = 0x39;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.C, "The C register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // srl c
            rom[0x103] = 0x39;

            cpu.tick();
            cpu.tick();
            assertEquals(0x7F, cpu.registers.C, "The C register should equal 0x00.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB3A
        @Test
        void srl_d_test() {
            rom[0x100] = 0x16; // ld d,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // srl d
            rom[0x103] = 0x3A;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.D, "The D register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld d,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // srl d
            rom[0x103] = 0x3A;

            cpu.tick();
            cpu.tick();
            assertEquals(0x7F, cpu.registers.D, "The D register should equal 0x00.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB3B
        @Test
        void srl_e_test() {
            rom[0x100] = 0x1E; // ld e,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // srl e
            rom[0x103] = 0x3B;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.E, "The E register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // srl e
            rom[0x103] = 0x3B;

            cpu.tick();
            cpu.tick();
            assertEquals(0x7F, cpu.registers.E, "The E register should equal 0x00.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB3C
        @Test
        void srl_h_test() {
            rom[0x100] = 0x26; // ld h,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // srl h
            rom[0x103] = 0x3C;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.H, "The H register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // srl h
            rom[0x103] = 0x3C;

            cpu.tick();
            cpu.tick();
            assertEquals(0x7F, cpu.registers.H, "The H register should equal 0x00.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB3D
        @Test
        void srl_l_test() {
            rom[0x100] = 0x2E; // ld l,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // srl l
            rom[0x103] = 0x3D;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.L, "The L register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // srl l
            rom[0x103] = 0x3D;

            cpu.tick();
            cpu.tick();
            assertEquals(0x7F, cpu.registers.L, "The L register should equal 0x00.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB3E
        @Test
        void srl_hlp_test() {
            memory.setByteAt(0xC000, 0x01); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // srl (hl)
            rom[0x104] = 0x3E;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

            cpu.tick();
            cpu.tick();
            assertEquals(0x7F, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should equal 0xFE.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB3F
        @Test
        void srl_a_test() {
            rom[0x100] = 0x3E; // ld a,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // srl a
            rom[0x103] = 0x3F;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // srl a
            rom[0x103] = 0x3F;

            cpu.tick();
            cpu.tick();
            assertEquals(0x7F, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }
    }
}
