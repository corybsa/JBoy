package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPU_0xCBC0_0xCBFF {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

    static class CPU_0xCBC0_0xCBCF {
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

        // op code 0xCBC0
        @Test
        void set_0_b_test() {
            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 0,b
            rom[0x103] = 0xC0;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x01, cpu.registers.B, "The B register should equal 0x01.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBC1
        @Test
        void set_0_c_test() {
            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 0,c
            rom[0x103] = 0xC1;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x01, cpu.registers.C, "The C register should equal 0x01.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBC2
        @Test
        void set_0_d_test() {
            rom[0x100] = 0x16; // ld d,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 0,d
            rom[0x103] = 0xC2;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x01, cpu.registers.D, "The D register should equal 0x01.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBC3
        @Test
        void set_0_e_test() {
            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 0,e
            rom[0x103] = 0xC3;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x01, cpu.registers.E, "The E register should equal 0x01.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBC4
        @Test
        void set_0_h_test() {
            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 0,h
            rom[0x103] = 0xC4;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x01, cpu.registers.H, "The H register should equal 0x01.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBC5
        @Test
        void set_0_l_test() {
            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 0,l
            rom[0x103] = 0xC5;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x01, cpu.registers.L, "The L register should equal 0x01.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBC6
        @Test
        void set_0_hlp_test() {
            memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // set 0,(hl)
            rom[0x104] = 0xC6;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x01, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by the HL register should equal 0x01.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCBC7
        @Test
        void set_0_a_test() {
            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 0,a
            rom[0x103] = 0xC7;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x01, cpu.registers.A, "The A register should equal 0x01.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBC8
        @Test
        void set_1_b_test() {
            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 1,b
            rom[0x103] = 0xC8;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x02, cpu.registers.B, "The B register should equal 0x02.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBC9
        @Test
        void set_1_c_test() {
            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 1,c
            rom[0x103] = 0xC9;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x02, cpu.registers.C, "The C register should equal 0x02.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBCA
        @Test
        void set_1_d_test() {
            rom[0x100] = 0x16; // ld d,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 1,d
            rom[0x103] = 0xCA;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x02, cpu.registers.D, "The D register should equal 0x02.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBCB
        @Test
        void set_1_e_test() {
            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 1,e
            rom[0x103] = 0xCB;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x02, cpu.registers.E, "The E register should equal 0x02.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBCC
        @Test
        void set_1_h_test() {
            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 1,h
            rom[0x103] = 0xCC;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x02, cpu.registers.H, "The H register should equal 0x02.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBCD
        @Test
        void set_1_l_test() {
            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 1,l
            rom[0x103] = 0xCD;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x02, cpu.registers.L, "The L register should equal 0x02.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBCE
        @Test
        void set_1_hlp_test() {
            memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // set 1,(hl)
            rom[0x104] = 0xCE;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x02, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by the HL register should equal 0x02.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCBCF
        @Test
        void set_1_a_test() {
            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 1,a
            rom[0x103] = 0xCF;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x02, cpu.registers.A, "The A register should equal 0x02.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }
    }

    static class CPU_0xCBD0_0xCBDF {
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

        // op code 0xCBD0
        @Test
        void set_2_b_test() {
            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 2,b
            rom[0x103] = 0xD0;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x04, cpu.registers.B, "The B register should equal 0x04.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBD1
        @Test
        void set_2_c_test() {
            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 2,c
            rom[0x103] = 0xD1;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x04, cpu.registers.C, "The C register should equal 0x04.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBD2
        @Test
        void set_2_d_test() {
            rom[0x100] = 0x16; // ld d,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 2,d
            rom[0x103] = 0xD2;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x04, cpu.registers.D, "The D register should equal 0x04.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBD3
        @Test
        void set_2_e_test() {
            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 2,e
            rom[0x103] = 0xD3;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x04, cpu.registers.E, "The E register should equal 0x04.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBD4
        @Test
        void set_2_h_test() {
            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 2,h
            rom[0x103] = 0xD4;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x04, cpu.registers.H, "The H register should equal 0x04.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBD5
        @Test
        void set_2_l_test() {
            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 2,l
            rom[0x103] = 0xD5;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x04, cpu.registers.L, "The L register should equal 0x04.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBD6
        @Test
        void set_2_hlp_test() {
            memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // set 2,(hl)
            rom[0x104] = 0xD6;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x04, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by the HL register should equal 0x04.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCBD7
        @Test
        void set_2_a_test() {
            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 2,a
            rom[0x103] = 0xD7;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x04, cpu.registers.A, "The A register should equal 0x04.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBD8
        @Test
        void set_3_b_test() {
            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 3,b
            rom[0x103] = 0xD8;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x08, cpu.registers.B, "The B register should equal 0x08.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBD9
        @Test
        void set_3_c_test() {
            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 3,c
            rom[0x103] = 0xD9;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x08, cpu.registers.C, "The C register should equal 0x08.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBDA
        @Test
        void set_3_d_test() {
            rom[0x100] = 0x16; // ld d,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 3,d
            rom[0x103] = 0xDA;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x08, cpu.registers.D, "The D register should equal 0x08.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBDB
        @Test
        void set_3_e_test() {
            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 3,e
            rom[0x103] = 0xDB;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x08, cpu.registers.E, "The E register should equal 0x08.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBDC
        @Test
        void set_3_h_test() {
            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 3,h
            rom[0x103] = 0xDC;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x08, cpu.registers.H, "The H register should equal 0x08.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBDD
        @Test
        void set_3_l_test() {
            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 3,l
            rom[0x103] = 0xDD;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x08, cpu.registers.L, "The L register should equal 0x08.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBDE
        @Test
        void set_3_hlp_test() {
            memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // set 3,(hl)
            rom[0x104] = 0xDE;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x08, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by the HL register should equal 0x08.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCBDF
        @Test
        void set_3_a_test() {
            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 3,a
            rom[0x103] = 0xDF;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x08, cpu.registers.A, "The A register should equal 0x08.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }
    }

    static class CPU_0xCBE0_0xCBEF {
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

    static class CPU_0xCBF0_0xCBFF {
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

        // op code 0xCBF0
        @Test
        void set_6_b_test() {
            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 6,b
            rom[0x103] = 0xF0;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x40, cpu.registers.B, "The B register should equal 0x40.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBF1
        @Test
        void set_6_c_test() {
            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 6,c
            rom[0x103] = 0xF1;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x40, cpu.registers.C, "The C register should equal 0x40.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBF2
        @Test
        void set_6_d_test() {
            rom[0x100] = 0x16; // ld d,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 6,d
            rom[0x103] = 0xF2;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x40, cpu.registers.D, "The D register should equal 0x40.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBF3
        @Test
        void set_6_e_test() {
            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 6,e
            rom[0x103] = 0xF3;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x40, cpu.registers.E, "The E register should equal 0x40.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBF4
        @Test
        void set_6_h_test() {
            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 6,h
            rom[0x103] = 0xF4;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x40, cpu.registers.H, "The H register should equal 0x40.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBF5
        @Test
        void set_6_l_test() {
            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 6,l
            rom[0x103] = 0xF5;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x40, cpu.registers.L, "The L register should equal 0x40.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBF6
        @Test
        void set_6_hlp_test() {
            memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // set 6,(hl)
            rom[0x104] = 0xF6;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x40, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by the HL register should equal 0x40.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCBF7
        @Test
        void set_6_a_test() {
            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 6,a
            rom[0x103] = 0xF7;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x40, cpu.registers.A, "The A register should equal 0x40.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBF8
        @Test
        void set_7_b_test() {
            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 7,b
            rom[0x103] = 0xF8;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x80, cpu.registers.B, "The B register should equal 0x80.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBF9
        @Test
        void set_7_c_test() {
            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 7,c
            rom[0x103] = 0xF9;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x80, cpu.registers.C, "The C register should equal 0x80.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBFA
        @Test
        void set_7_d_test() {
            rom[0x100] = 0x16; // ld d,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 7,d
            rom[0x103] = 0xFA;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x80, cpu.registers.D, "The D register should equal 0x80.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBFB
        @Test
        void set_7_e_test() {
            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 7,e
            rom[0x103] = 0xFB;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x80, cpu.registers.E, "The E register should equal 0x80.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBFC
        @Test
        void set_7_h_test() {
            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 7,h
            rom[0x103] = 0xFC;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x80, cpu.registers.H, "The H register should equal 0x80.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBFD
        @Test
        void set_7_l_test() {
            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 7,l
            rom[0x103] = 0xFD;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x80, cpu.registers.L, "The L register should equal 0x80.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBFE
        @Test
        void set_7_hlp_test() {
            memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // set 7,(hl)
            rom[0x104] = 0xFE;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x80, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by the HL register should equal 0x80.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCBFF
        @Test
        void set_7_a_test() {
            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // set 7,a
            rom[0x103] = 0xFF;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x80, cpu.registers.A, "The A register should equal 0x80.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }
    }
}
