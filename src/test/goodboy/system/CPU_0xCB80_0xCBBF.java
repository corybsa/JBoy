package test.goodboy.system;

import goodboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPU_0xCB80_0xCBBF {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

    static class CPU_0xCB80_0xCB8F {
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

        // op code 0xCB80
        @Test
        void res_0_b_test() {
            rom[0x100] = 0x06; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 0,b
            rom[0x103] = 0x80;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.B, "The B register should equal 0xFE.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB81
        @Test
        void res_0_c_test() {
            rom[0x100] = 0x0E; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 0,c
            rom[0x103] = 0x81;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.C, "The C register should equal 0xFE.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB82
        @Test
        void res_0_d_test() {
            rom[0x100] = 0x16; // ld d,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 0,d
            rom[0x103] = 0x82;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.D, "The D register should equal 0xFE.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB83
        @Test
        void res_0_e_test() {
            rom[0x100] = 0x1E; // ld e,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 0,e
            rom[0x103] = 0x83;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.E, "The E register should equal 0xFE.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB84
        @Test
        void res_0_h_test() {
            rom[0x100] = 0x26; // ld h,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 0,h
            rom[0x103] = 0x84;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.H, "The H register should equal 0xFE.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB85
        @Test
        void res_0_l_test() {
            rom[0x100] = 0x2E; // ld l,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 0,l
            rom[0x103] = 0x85;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.L, "The L register should equal 0xFE.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB86
        @Test
        void res_0_hlp_test() {
            memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // res 0,(hl)
            rom[0x104] = 0x86;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by the HL register should equal 0xFE.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB87
        @Test
        void res_0_a_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 0,a
            rom[0x103] = 0x87;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.A, "The A register should equal 0xFE.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB88
        @Test
        void res_1_b_test() {
            rom[0x100] = 0x06; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 1,b
            rom[0x103] = 0x88;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFD, cpu.registers.B, "The B register should equal 0xFD.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB89
        @Test
        void res_1_c_test() {
            rom[0x100] = 0x0E; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 1,c
            rom[0x103] = 0x89;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFD, cpu.registers.C, "The C register should equal 0xFD.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB8A
        @Test
        void res_1_d_test() {
            rom[0x100] = 0x16; // ld d,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 1,d
            rom[0x103] = 0x8A;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFD, cpu.registers.D, "The D register should equal 0xFD.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB8B
        @Test
        void res_1_e_test() {
            rom[0x100] = 0x1E; // ld e,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 1,e
            rom[0x103] = 0x8B;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFD, cpu.registers.E, "The E register should equal 0xFD.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB8C
        @Test
        void res_1_h_test() {
            rom[0x100] = 0x26; // ld h,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 1,h
            rom[0x103] = 0x8C;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFD, cpu.registers.H, "The H register should equal 0xFD.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB8D
        @Test
        void res_1_l_test() {
            rom[0x100] = 0x2E; // ld l,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 1,l
            rom[0x103] = 0x8D;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFD, cpu.registers.L, "The L register should equal 0xFD.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB8E
        @Test
        void res_1_hlp_test() {
            memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // res 1,(hl)
            rom[0x104] = 0x8E;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFD, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by the HL register should equal 0xFD.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB8F
        @Test
        void res_1_a_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 1,a
            rom[0x103] = 0x8F;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFD, cpu.registers.A, "The A register should equal 0xFD.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }
    }

    static class CPU_0xCB90_0xCB9F {
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

        // op code 0xCB90
        @Test
        void res_2_b_test() {
            rom[0x100] = 0x06; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 2,b
            rom[0x103] = 0x90;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFB, cpu.registers.B, "The B register should equal 0xFB.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB91
        @Test
        void res_2_c_test() {
            rom[0x100] = 0x0E; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 2,c
            rom[0x103] = 0x91;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFB, cpu.registers.C, "The C register should equal 0xFB.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB92
        @Test
        void res_2_d_test() {
            rom[0x100] = 0x16; // ld d,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 2,d
            rom[0x103] = 0x92;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFB, cpu.registers.D, "The D register should equal 0xFB.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB93
        @Test
        void res_2_e_test() {
            rom[0x100] = 0x1E; // ld e,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 2,e
            rom[0x103] = 0x93;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFB, cpu.registers.E, "The E register should equal 0xFB.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB94
        @Test
        void res_2_h_test() {
            rom[0x100] = 0x26; // ld h,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 2,h
            rom[0x103] = 0x94;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFB, cpu.registers.H, "The H register should equal 0xFB.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB95
        @Test
        void res_2_l_test() {
            rom[0x100] = 0x2E; // ld l,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 2,l
            rom[0x103] = 0x95;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFB, cpu.registers.L, "The L register should equal 0xFB.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB96
        @Test
        void res_2_hlp_test() {
            memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // res 2,(hl)
            rom[0x104] = 0x96;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFB, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by the HL register should equal 0xFB.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB97
        @Test
        void res_2_a_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 2,a
            rom[0x103] = 0x97;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFB, cpu.registers.A, "The A register should equal 0xFB.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB98
        @Test
        void res_3_b_test() {
            rom[0x100] = 0x06; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 3,b
            rom[0x103] = 0x98;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xF7, cpu.registers.B, "The B register should equal 0xF7.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB99
        @Test
        void res_3_c_test() {
            rom[0x100] = 0x0E; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 3,c
            rom[0x103] = 0x99;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xF7, cpu.registers.C, "The C register should equal 0xF7.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB9A
        @Test
        void res_3_d_test() {
            rom[0x100] = 0x16; // ld d,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 3,d
            rom[0x103] = 0x9A;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xF7, cpu.registers.D, "The D register should equal 0xF7.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB9B
        @Test
        void res_3_e_test() {
            rom[0x100] = 0x1E; // ld e,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 3,e
            rom[0x103] = 0x9B;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xF7, cpu.registers.E, "The E register should equal 0xF7.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB9C
        @Test
        void res_3_h_test() {
            rom[0x100] = 0x26; // ld h,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 3,h
            rom[0x103] = 0x9C;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xF7, cpu.registers.H, "The H register should equal 0xF7.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB9D
        @Test
        void res_3_l_test() {
            rom[0x100] = 0x2E; // ld l,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 3,l
            rom[0x103] = 0x9D;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xF7, cpu.registers.L, "The L register should equal 0xF7.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB9E
        @Test
        void res_3_hlp_test() {
            memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // res 3,(hl)
            rom[0x104] = 0x9E;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xF7, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by the HL register should equal 0xF7.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB9F
        @Test
        void res_3_a_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 3,a
            rom[0x103] = 0x9F;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xF7, cpu.registers.A, "The A register should equal 0xF7.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }
    }

    static class CPU_0xCBA0_0xCBAF {
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

        // op code 0xCBA0
        @Test
        void res_4_b_test() {
            rom[0x100] = 0x06; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 4,b
            rom[0x103] = 0xA0;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xEF, cpu.registers.B, "The B register should equal 0xEF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBA1
        @Test
        void res_4_c_test() {
            rom[0x100] = 0x0E; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 4,c
            rom[0x103] = 0xA1;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xEF, cpu.registers.C, "The C register should equal 0xEF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBA2
        @Test
        void res_4_d_test() {
            rom[0x100] = 0x16; // ld d,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 4,d
            rom[0x103] = 0xA2;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xEF, cpu.registers.D, "The D register should equal 0xEF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBA3
        @Test
        void res_4_e_test() {
            rom[0x100] = 0x1E; // ld e,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 4,e
            rom[0x103] = 0xA3;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xEF, cpu.registers.E, "The E register should equal 0xEF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBA4
        @Test
        void res_4_h_test() {
            rom[0x100] = 0x26; // ld h,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 4,h
            rom[0x103] = 0xA4;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xEF, cpu.registers.H, "The H register should equal 0xEF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBA5
        @Test
        void res_4_l_test() {
            rom[0x100] = 0x2E; // ld l,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 4,l
            rom[0x103] = 0xA5;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xEF, cpu.registers.L, "The L register should equal 0xEF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBA6
        @Test
        void res_4_hlp_test() {
            memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // res 4,(hl)
            rom[0x104] = 0xA6;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xEF, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by the HL register should equal 0xEF.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCBA7
        @Test
        void res_4_a_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 4,a
            rom[0x103] = 0xA7;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xEF, cpu.registers.A, "The A register should equal 0xEF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBA8
        @Test
        void res_5_b_test() {
            rom[0x100] = 0x06; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 5,b
            rom[0x103] = 0xA8;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xDF, cpu.registers.B, "The B register should equal 0xDF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBA9
        @Test
        void res_5_c_test() {
            rom[0x100] = 0x0E; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 5,c
            rom[0x103] = 0xA9;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xDF, cpu.registers.C, "The C register should equal 0xDF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBAA
        @Test
        void res_5_d_test() {
            rom[0x100] = 0x16; // ld d,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 5,d
            rom[0x103] = 0xAA;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xDF, cpu.registers.D, "The D register should equal 0xDF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBAB
        @Test
        void res_5_e_test() {
            rom[0x100] = 0x1E; // ld e,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 5,e
            rom[0x103] = 0xAB;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xDF, cpu.registers.E, "The E register should equal 0xDF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBAC
        @Test
        void res_5_h_test() {
            rom[0x100] = 0x26; // ld h,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 5,h
            rom[0x103] = 0xAC;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xDF, cpu.registers.H, "The H register should equal 0xDF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBAD
        @Test
        void res_5_l_test() {
            rom[0x100] = 0x2E; // ld l,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 5,l
            rom[0x103] = 0xAD;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xDF, cpu.registers.L, "The L register should equal 0xDF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBAE
        @Test
        void res_5_hlp_test() {
            memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // res 5,(hl)
            rom[0x104] = 0xAE;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xDF, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by the HL register should equal 0xDF.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCBAF
        @Test
        void res_5_a_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 5,a
            rom[0x103] = 0xAF;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xDF, cpu.registers.A, "The A register should equal 0xDF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }
    }

    static class CPU_0xCBB0_0xCBBF {
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

        // op code 0xCBB0
        @Test
        void res_6_b_test() {
            rom[0x100] = 0x06; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 6,b
            rom[0x103] = 0xB0;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xBF, cpu.registers.B, "The B register should equal 0xBF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBB1
        @Test
        void res_6_c_test() {
            rom[0x100] = 0x0E; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 6,c
            rom[0x103] = 0xB1;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xBF, cpu.registers.C, "The C register should equal 0xBF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBB2
        @Test
        void res_6_d_test() {
            rom[0x100] = 0x16; // ld d,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 6,d
            rom[0x103] = 0xB2;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xBF, cpu.registers.D, "The D register should equal 0xBF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBB3
        @Test
        void res_6_e_test() {
            rom[0x100] = 0x1E; // ld e,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 6,e
            rom[0x103] = 0xB3;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xBF, cpu.registers.E, "The E register should equal 0xBF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBB4
        @Test
        void res_6_h_test() {
            rom[0x100] = 0x26; // ld h,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 6,h
            rom[0x103] = 0xB4;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xBF, cpu.registers.H, "The H register should equal 0xBF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBB5
        @Test
        void res_6_l_test() {
            rom[0x100] = 0x2E; // ld l,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 6,l
            rom[0x103] = 0xB5;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xBF, cpu.registers.L, "The L register should equal 0xBF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBB6
        @Test
        void res_6_hlp_test() {
            memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // res 6,(hl)
            rom[0x104] = 0xB6;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xBF, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by the HL register should equal 0xBF.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCBB7
        @Test
        void res_6_a_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 6,a
            rom[0x103] = 0xB7;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xBF, cpu.registers.A, "The A register should equal 0xBF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBB8
        @Test
        void res_7_b_test() {
            rom[0x100] = 0x06; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 7,b
            rom[0x103] = 0xB8;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x7F, cpu.registers.B, "The B register should equal 0x7F.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBB9
        @Test
        void res_7_c_test() {
            rom[0x100] = 0x0E; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 7,c
            rom[0x103] = 0xB9;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x7F, cpu.registers.C, "The C register should equal 0x7F.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBBA
        @Test
        void res_7_d_test() {
            rom[0x100] = 0x16; // ld d,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 7,d
            rom[0x103] = 0xBA;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x7F, cpu.registers.D, "The D register should equal 0x7F.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBBB
        @Test
        void res_7_e_test() {
            rom[0x100] = 0x1E; // ld e,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 7,e
            rom[0x103] = 0xBB;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x7F, cpu.registers.E, "The E register should equal 0x7F.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBBC
        @Test
        void res_7_h_test() {
            rom[0x100] = 0x26; // ld h,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 7,h
            rom[0x103] = 0xBC;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x7F, cpu.registers.H, "The H register should equal 0x7F.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBBD
        @Test
        void res_7_l_test() {
            rom[0x100] = 0x2E; // ld l,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 7,l
            rom[0x103] = 0xBD;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x7F, cpu.registers.L, "The L register should equal 0x7F.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCBBE
        @Test
        void res_7_hlp_test() {
            memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // res 7,(hl)
            rom[0x104] = 0xBE;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x7F, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by the HL register should equal 0x7F.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCBBF
        @Test
        void res_7_a_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xCB; // res 7,a
            rom[0x103] = 0xBF;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x7F, cpu.registers.A, "The A register should equal 0x7F.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }
    }
}
