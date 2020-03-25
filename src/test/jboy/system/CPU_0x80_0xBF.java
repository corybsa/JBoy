package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPU_0x80_0xBF {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

    static class CPU_0x80_0x8F {
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

        // op code 0x80
        @Test
        void add_a_b_test() {
            rom[0x100] = 0x3E; // ld a,0x3A
            rom[0x101] = 0x3A;
            rom[0x102] = 0x06; // ld b,0xC6
            rom[0x103] = 0xC6;
            rom[0x104] = 0x80; // add a,b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x81
        @Test
        void add_a_c_test() {
            rom[0x100] = 0x3E; // ld a,0x3A
            rom[0x101] = 0x3A;
            rom[0x102] = 0x0E; // ld c,0xC6
            rom[0x103] = 0xC6;
            rom[0x104] = 0x81; // add a,c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x82
        @Test
        void add_a_d_test() {
            rom[0x100] = 0x3E; // ld a,0x3A
            rom[0x101] = 0x3A;
            rom[0x102] = 0x16; // ld d,0xC6
            rom[0x103] = 0xC6;
            rom[0x104] = 0x82; // add a,d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x83
        @Test
        void add_a_e_test() {
            rom[0x100] = 0x3E; // ld a,0x3A
            rom[0x101] = 0x3A;
            rom[0x102] = 0x1E; // ld e,0xC6
            rom[0x103] = 0xC6;
            rom[0x104] = 0x83; // add a,e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x84
        @Test
        void add_a_h_test() {
            rom[0x100] = 0x3E; // ld a,0x3A
            rom[0x101] = 0x3A;
            rom[0x102] = 0x26; // ld h,0xC6
            rom[0x103] = 0xC6;
            rom[0x104] = 0x84; // add a,h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x85
        @Test
        void add_a_l_test() {
            rom[0x100] = 0x3E; // ld a,0x3A
            rom[0x101] = 0x3A;
            rom[0x102] = 0x2E; // ld l,0xC6
            rom[0x103] = 0xC6;
            rom[0x104] = 0x85; // add a,l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x86
        @Test
        void add_a_hlp_test() {
            memory.setByteAt(0xC000, 0xC6);

            rom[0x100] = 0x3E; // ld a,0x3A
            rom[0x101] = 0x3A;
            rom[0x102] = 0x21; // ld hl,0xC000
            rom[0x103] = 0x00;
            rom[0x104] = 0xC0;
            rom[0x105] = 0x86; // add a,(hl)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");
        }

        // op code 0x87
        @Test
        void add_a_a_test() {
            rom[0x100] = 0x3E; // ld a,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0x87; // add a,a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x88
        @Test
        void adc_a_b_test() {
            cpu.setFlags(CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0xE1
            rom[0x101] = 0xE1;
            rom[0x102] = 0x06; // ld b,0x1E
            rom[0x103] = 0x1E;
            rom[0x104] = 0x88; // adc a,b

            memory.loadROM(rom);
            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x89
        @Test
        void adc_a_c_test() {
            cpu.setFlags(CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0xE1
            rom[0x101] = 0xE1;
            rom[0x102] = 0x0E; // ld c,0x1E
            rom[0x103] = 0x1E;
            rom[0x104] = 0x89; // adc a,c

            memory.loadROM(rom);
            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x8A
        @Test
        void adc_a_d_test() {
            cpu.setFlags(CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0xE1
            rom[0x101] = 0xE1;
            rom[0x102] = 0x16; // ld d,0x1E
            rom[0x103] = 0x1E;
            rom[0x104] = 0x8A; // adc a,d

            memory.loadROM(rom);
            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x8B
        @Test
        void adc_a_e_test() {
            cpu.setFlags(CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0xE1
            rom[0x101] = 0xE1;
            rom[0x102] = 0x1E; // ld b,0x1E
            rom[0x103] = 0x1E;
            rom[0x104] = 0x8B; // adc a,b

            memory.loadROM(rom);
            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x8C
        @Test
        void adc_a_h_test() {
            cpu.setFlags(CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0xE1
            rom[0x101] = 0xE1;
            rom[0x102] = 0x26; // ld h,0x1E
            rom[0x103] = 0x1E;
            rom[0x104] = 0x8C; // adc a,h

            memory.loadROM(rom);
            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x8D
        @Test
        void adc_a_l_test() {
            cpu.setFlags(CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0xE1
            rom[0x101] = 0xE1;
            rom[0x102] = 0x2E; // ld l,0x1E
            rom[0x103] = 0x1E;
            rom[0x104] = 0x8D; // adc a,l

            memory.loadROM(rom);
            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x8E
        @Test
        void adc_a_hlp_test() {
            cpu.setFlags(CPU.Flags.CARRY);
            memory.setByteAt(0xC000, 0x1E);

            rom[0x100] = 0x3E; // ld a,0xE1
            rom[0x101] = 0xE1;
            rom[0x102] = 0x21; // ld hl,0xC000
            rom[0x103] = 0x00;
            rom[0x104] = 0xC0;
            rom[0x105] = 0x8E; // adc a,(hl)

            memory.loadROM(rom);
            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");
        }

        // op code 0x8F
        @Test
        void adc_a_a_test() {
            cpu.setFlags(CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0x8F; // adc a,a

            memory.loadROM(rom);
            cpu.tick();
            cpu.tick();
            assertEquals(0x01, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }
    }

    static class CPU_0x90_0x9F {
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

        // op code 0x90
        @Test
        void sub_b_test() {
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x06; // ld b,0x3E
            rom[0x103] = 0x3E;
            rom[0x104] = 0x90; // sub b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x06; // ld b,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0x90; // sub b

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x2F, cpu.registers.A, "The A register should equal 0x2F.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x06; // ld b,0x40
            rom[0x103] = 0x40;
            rom[0x104] = 0x90; // sub b

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.A, "The A register should equal 0xFE.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x91
        @Test
        void sub_c_test() {
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x0E; // ld c,0x3E
            rom[0x103] = 0x3E;
            rom[0x104] = 0x91; // sub c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x0E; // ld c,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0x91; // sub c

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x2F, cpu.registers.A, "The A register should equal 0x2F.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x0E; // ld c,0x40
            rom[0x103] = 0x40;
            rom[0x104] = 0x91; // sub c

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.A, "The A register should equal 0xFE.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x92
        @Test
        void sub_d_test() {
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x16; // ld d,0x3E
            rom[0x103] = 0x3E;
            rom[0x104] = 0x92; // sub d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x16; // ld d,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0x92; // sub d

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x2F, cpu.registers.A, "The A register should equal 0x2F.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x16; // ld d,0x40
            rom[0x103] = 0x40;
            rom[0x104] = 0x92; // sub d

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.A, "The A register should equal 0xFE.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x93
        @Test
        void sub_e_test() {
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x1E; // ld e,0x3E
            rom[0x103] = 0x3E;
            rom[0x104] = 0x93; // sub e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x1E; // ld e,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0x93; // sub e

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x2F, cpu.registers.A, "The A register should equal 0x2F.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x1E; // ld e,0x40
            rom[0x103] = 0x40;
            rom[0x104] = 0x93; // sub e

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.A, "The A register should equal 0xFE.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x94
        @Test
        void sub_h_test() {
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x26; // ld h,0x3E
            rom[0x103] = 0x3E;
            rom[0x104] = 0x94; // sub h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x26; // ld h,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0x94; // sub h

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x2F, cpu.registers.A, "The A register should equal 0x2F.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x26; // ld h,0x40
            rom[0x103] = 0x40;
            rom[0x104] = 0x94; // sub h

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.A, "The A register should equal 0xFE.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x95
        @Test
        void sub_l_test() {
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x2E; // ld l,0x3E
            rom[0x103] = 0x3E;
            rom[0x104] = 0x95; // sub l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x2E; // ld l,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0x95; // sub l

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x2F, cpu.registers.A, "The A register should equal 0x2F.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x2E; // ld l,0x40
            rom[0x103] = 0x40;
            rom[0x104] = 0x95; // sub l

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.A, "The A register should equal 0xFE.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x96
        @Test
        void sub_hlp_test() {
            memory.setByteAt(0xC000, 0x3E);

            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x21; // ld hl,0xC000
            rom[0x103] = 0x00;
            rom[0x104] = 0xC0;
            rom[0x105] = 0x96; // sub (hl)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            memory.setByteAt(0xC000, 0x0F);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x2F, cpu.registers.A, "The A register should equal 0x2F.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            memory.setByteAt(0xC000, 0x40);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.A, "The A register should equal 0xFE.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");
        }

        // op code 0x97
        @Test
        void sub_a_test() {
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0x97; // sub a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x98
        @Test
        void sbc_a_b_test() {
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x06; // ld b,0x2A
            rom[0x103] = 0x2A;
            rom[0x104] = 0x98; // sbc a,b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x10, cpu.registers.A, "The A register should equal 0x10.");
            assertEquals(CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x06; // ld b,0x3A
            rom[0x103] = 0x3A;
            rom[0x104] = 0x98; // sbc a,b

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x06; // ld b,0x4F
            rom[0x103] = 0x4F;
            rom[0x104] = 0x98; // sbc a,b

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xEB, cpu.registers.A, "The A register should equal 0xEB.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The SUB, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x99
        @Test
        void sbc_a_c_test() {
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x0E; // ld c,0x2A
            rom[0x103] = 0x2A;
            rom[0x104] = 0x99; // sbc a,c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x10, cpu.registers.A, "The A register should equal 0x10.");
            assertEquals(CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x0E; // ld c,0x3A
            rom[0x103] = 0x3A;
            rom[0x104] = 0x99; // sbc a,c

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x0E; // ld c,0x4F
            rom[0x103] = 0x4F;
            rom[0x104] = 0x99; // sbc a,c

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xEB, cpu.registers.A, "The A register should equal 0xEB.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The SUB, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x9A
        @Test
        void sbc_a_d_test() {
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x16; // ld d,0x2A
            rom[0x103] = 0x2A;
            rom[0x104] = 0x9A; // sbc a,d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x10, cpu.registers.A, "The A register should equal 0x10.");
            assertEquals(CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x16; // ld d,0x3A
            rom[0x103] = 0x3A;
            rom[0x104] = 0x9A; // sbc a,d

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x16; // ld d,0x4F
            rom[0x103] = 0x4F;
            rom[0x104] = 0x9A; // sbc a,d

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xEB, cpu.registers.A, "The A register should equal 0xEB.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The SUB, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x9B
        @Test
        void sbc_a_e_test() {
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x1E; // ld e,0x2A
            rom[0x103] = 0x2A;
            rom[0x104] = 0x9B; // sbc a,e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x10, cpu.registers.A, "The A register should equal 0x10.");
            assertEquals(CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x1E; // ld e,0x3A
            rom[0x103] = 0x3A;
            rom[0x104] = 0x9B; // sbc a,e

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x1E; // ld e,0x4F
            rom[0x103] = 0x4F;
            rom[0x104] = 0x9B; // sbc a,e

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xEB, cpu.registers.A, "The A register should equal 0xEB.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The SUB, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x9C
        @Test
        void sbc_a_h_test() {
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x26; // ld h,0x2A
            rom[0x103] = 0x2A;
            rom[0x104] = 0x9C; // sbc a,h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x10, cpu.registers.A, "The A register should equal 0x10.");
            assertEquals(CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x26; // ld h,0x3A
            rom[0x103] = 0x3A;
            rom[0x104] = 0x9C; // sbc a,h

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x26; // ld h,0x4F
            rom[0x103] = 0x4F;
            rom[0x104] = 0x9C; // sbc a,h

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xEB, cpu.registers.A, "The A register should equal 0xEB.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The SUB, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x9D
        @Test
        void sbc_a_l_test() {
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x2E; // ld l,0x2A
            rom[0x103] = 0x2A;
            rom[0x104] = 0x9D; // sbc a,l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x10, cpu.registers.A, "The A register should equal 0x10.");
            assertEquals(CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x2E; // ld l,0x3A
            rom[0x103] = 0x3A;
            rom[0x104] = 0x9D; // sbc a,l

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x2E; // ld l,0x4F
            rom[0x103] = 0x4F;
            rom[0x104] = 0x9D; // sbc a,l

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xEB, cpu.registers.A, "The A register should equal 0xEB.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The SUB, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x9E
        @Test
        void sbc_a_hlp_test() {
            cpu.setFlags(CPU.Flags.CARRY);
            memory.setByteAt(0xC000, 0x2A);

            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x21; // ld hl,0xC000
            rom[0x103] = 0x00;
            rom[0x104] = 0xC0;
            rom[0x105] = 0x9E; // sbc a,(hl)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x10, cpu.registers.A, "The A register should equal 0x10.");
            assertEquals(CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
            assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);
            memory.setByteAt(0xC000, 0x3A);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);
            memory.setByteAt(0xC000, 0x4F);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xEB, cpu.registers.A, "The A register should equal 0xEB.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The SUB, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");
        }

        // op code 0x9F
        @Test
        void sbc_a_a_test() {
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x9F; // sbc a,a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFF, cpu.registers.A, "The A register should equal 0xFF.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The SUB flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x9F; // sbc a,a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }
    }

    static class CPU_0xA0_0xAF {
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

        // op code 0xA0
        @Test
        void and_b_test() {
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x06; // ld b,0x3F
            rom[0x103] = 0x3F;
            rom[0x104] = 0xA0; // and b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x1A, cpu.registers.A, "The A register should equal 0x1A.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x06; // ld b,0x00
            rom[0x103] = 0x00;
            rom[0x104] = 0xA0; // and b

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xA1
        @Test
        void and_c_test() {
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x0E; // ld c,0x3F
            rom[0x103] = 0x3F;
            rom[0x104] = 0xA1; // and c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x1A, cpu.registers.A, "The A register should equal 0x1A.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x0E; // ld c,0x00
            rom[0x103] = 0x00;
            rom[0x104] = 0xA1; // and c

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xA2
        @Test
        void and_d_test() {
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x16; // ld d,0x3F
            rom[0x103] = 0x3F;
            rom[0x104] = 0xA2; // and d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x1A, cpu.registers.A, "The A register should equal 0x1A.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x16; // ld d,0x00
            rom[0x103] = 0x00;
            rom[0x104] = 0xA2; // and d

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xA3
        @Test
        void and_e_test() {
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x1E; // ld e,0x3F
            rom[0x103] = 0x3F;
            rom[0x104] = 0xA3; // and e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x1A, cpu.registers.A, "The A register should equal 0x1A.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x1E; // ld e,0x00
            rom[0x103] = 0x00;
            rom[0x104] = 0xA3; // and e

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xA4
        @Test
        void and_h_test() {
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x26; // ld h,0x3F
            rom[0x103] = 0x3F;
            rom[0x104] = 0xA4; // and h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x1A, cpu.registers.A, "The A register should equal 0x1A.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x26; // ld h,0x00
            rom[0x103] = 0x00;
            rom[0x104] = 0xA4; // and h

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xA5
        @Test
        void and_l_test() {
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x2E; // ld l,0x3F
            rom[0x103] = 0x3F;
            rom[0x104] = 0xA5; // and l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x1A, cpu.registers.A, "The A register should equal 0x1A.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x2E; // ld l,0x00
            rom[0x103] = 0x00;
            rom[0x104] = 0xA5; // and l

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xA6
        @Test
        void and_hlp_test() {
            memory.setByteAt(0xC000, 0x3F);
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x21; // ld hl,0xC000
            rom[0x103] = 0x00;
            rom[0x104] = 0xC0;
            rom[0x105] = 0xA6; // and (hl)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x1A, cpu.registers.A, "The A register should equal 0x1A.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x106, cpu.registers.PC, "The PC should equal 0x106.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            memory.setByteAt(0xC000, 0x00);
            cpu.registers.PC = 0x100;

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x106, cpu.registers.PC, "The PC should equal 0x106.");
        }

        // op code 0xA7
        @Test
        void and_a_test() {
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0xA7; // and a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x5A, cpu.registers.A, "The A register should equal 0x5A.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xA7; // and a

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103.");
        }

        // op code 0xA8
        @Test
        void xor_b_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x06; // ld b,0xFF
            rom[0x103] = 0xFF;
            rom[0x104] = 0xA8; // xor b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;

            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x06; // ld b,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0xA8; // xor b

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xF0, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xA9
        @Test
        void xor_c_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x0E; // ld c,0xFF
            rom[0x103] = 0xFF;
            rom[0x104] = 0xA9; // xor c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;

            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x0E; // ld c,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0xA9; // xor c

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xF0, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xAA
        @Test
        void xor_d_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x16; // ld d,0xFF
            rom[0x103] = 0xFF;
            rom[0x104] = 0xAA; // xor d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;

            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x16; // ld d,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0xAA; // xor d

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xF0, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xAB
        @Test
        void xor_e_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x1E; // ld e,0xFF
            rom[0x103] = 0xFF;
            rom[0x104] = 0xAB; // xor e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;

            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x1E; // ld e,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0xAB; // xor e

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xF0, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xAC
        @Test
        void xor_h_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x26; // ld h,0xFF
            rom[0x103] = 0xFF;
            rom[0x104] = 0xAC; // xor h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;

            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x26; // ld h,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0xAC; // xor h

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xF0, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xAD
        @Test
        void xor_l_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x2E; // ld l,0xFF
            rom[0x103] = 0xFF;
            rom[0x104] = 0xAD; // xor l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;

            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x2E; // ld l,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0xAD; // xor l

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xF0, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xAE
        @Test
        void xor_hlp_test() {
            memory.setByteAt(0xC000, 0xFF);
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x21; // ld hl,0xC000
            rom[0x103] = 0x00;
            rom[0x104] = 0xC0;
            rom[0x105] = 0xAE; // xor (hl)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x106, cpu.registers.PC, "The PC should equal 0x106.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            memory.setByteAt(0xC000, 0x0F);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xF0, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x106, cpu.registers.PC, "The PC should equal 0x106.");
        }

        // op code 0xAF
        @Test
        void xor_a_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xAF; // xor a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103.");
        }
    }

    static class CPU_0xB0_0xBF {
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

        // op code 0xB0
        @Test
        void or_b_test() {
            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x06; // ld b,0x00
            rom[0x103] = 0x00;
            rom[0x104] = 0xB0; // or b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x06; // ld b,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0xB0; // or b

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x5F, cpu.registers.A, "The A register should equal 0x5F.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xB1
        @Test
        void or_c_test() {
            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x0E; // ld c,0x00
            rom[0x103] = 0x00;
            rom[0x104] = 0xB1; // or c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x0E; // ld c,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0xB1; // or c

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x5F, cpu.registers.A, "The A register should equal 0x5F.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xB2
        @Test
        void or_d_test() {
            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x16; // ld d,0x00
            rom[0x103] = 0x00;
            rom[0x104] = 0xB2; // or d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x16; // ld d,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0xB2; // or d

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x5F, cpu.registers.A, "The A register should equal 0x5F.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xB3
        @Test
        void or_e_test() {
            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x1E; // ld e,0x00
            rom[0x103] = 0x00;
            rom[0x104] = 0xB3; // or e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x1E; // ld e,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0xB3; // or e

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x5F, cpu.registers.A, "The A register should equal 0x5F.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xB4
        @Test
        void or_h_test() {
            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x26; // ld h,0x00
            rom[0x103] = 0x00;
            rom[0x104] = 0xB4; // or h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x26; // ld h,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0xB4; // or h

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x5F, cpu.registers.A, "The A register should equal 0x5F.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xB5
        @Test
        void or_l_test() {
            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x2E; // ld l,0x00
            rom[0x103] = 0x00;
            rom[0x104] = 0xB5; // or l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x2E; // ld l,0x0F
            rom[0x103] = 0x0F;
            rom[0x104] = 0xB5; // or l

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x5F, cpu.registers.A, "The A register should equal 0x5F.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xB6
        @Test
        void or_hlp_test() {
            memory.setByteAt(0xC000, 0x00);

            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0x21; // ld hl,0xC000
            rom[0x103] = 0x00;
            rom[0x104] = 0xC0;
            rom[0x105] = 0xB6; // or (hl)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x5A, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(0x00, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            memory.setByteAt(0xC000, 0x0F);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x5F, cpu.registers.A, "The A register should equal 0x5F.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");
        }

        // op code 0xB7
        @Test
        void or_a_test() {
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0xB7; // or a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x5A, cpu.registers.A, "The A register should equal 0x5A.");
            assertEquals(0x00, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0xB8
        @Test
        void cp_b_test() {
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x06; // ld b,0x2F
            rom[0x103] = 0x2F;
            rom[0x104] = 0xB8; // cp b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x06; // ld b,0x3C
            rom[0x103] = 0x3C;
            rom[0x104] = 0xB8; // cp b

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x06; // ld b,0x40
            rom[0x103] = 0x40;
            rom[0x104] = 0xB8; // cp b

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.CARRY, cpu.registers.F, "The SUB and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xB9
        @Test
        void cp_c_test() {
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x0E; // ld c,0x2F
            rom[0x103] = 0x2F;
            rom[0x104] = 0xB9; // cp c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x0E; // ld c,0x3C
            rom[0x103] = 0x3C;
            rom[0x104] = 0xB9; // cp c

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x0E; // ld c,0x40
            rom[0x103] = 0x40;
            rom[0x104] = 0xB9; // cp c

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.CARRY, cpu.registers.F, "The SUB and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xBA
        @Test
        void cp_d_test() {
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x16; // ld d,0x2F
            rom[0x103] = 0x2F;
            rom[0x104] = 0xBA; // cp d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x16; // ld d,0x3C
            rom[0x103] = 0x3C;
            rom[0x104] = 0xBA; // cp d

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x16; // ld d,0x40
            rom[0x103] = 0x40;
            rom[0x104] = 0xBA; // cp d

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.CARRY, cpu.registers.F, "The SUB and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xBB
        @Test
        void cp_e_test() {
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x1E; // ld e,0x2F
            rom[0x103] = 0x2F;
            rom[0x104] = 0xBB; // cp e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x1E; // ld e,0x3C
            rom[0x103] = 0x3C;
            rom[0x104] = 0xBB; // cp e

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x1E; // ld e,0x40
            rom[0x103] = 0x40;
            rom[0x104] = 0xBB; // cp e

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.CARRY, cpu.registers.F, "The SUB and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xBC
        @Test
        void cp_h_test() {
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x26; // ld h,0x2F
            rom[0x103] = 0x2F;
            rom[0x104] = 0xBC; // cp h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x26; // ld h,0x3C
            rom[0x103] = 0x3C;
            rom[0x104] = 0xBC; // cp h

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x26; // ld h,0x40
            rom[0x103] = 0x40;
            rom[0x104] = 0xBC; // cp h

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.CARRY, cpu.registers.F, "The SUB and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xBD
        @Test
        void cp_l_test() {
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x2E; // ld l,0x2F
            rom[0x103] = 0x2F;
            rom[0x104] = 0xBD; // cp l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x2E; // ld l,0x3C
            rom[0x103] = 0x3C;
            rom[0x104] = 0xBD; // cp l

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x2E; // ld l,0x40
            rom[0x103] = 0x40;
            rom[0x104] = 0xBD; // cp l

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.CARRY, cpu.registers.F, "The SUB and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xBE
        @Test
        void cp_hlp_test() {
            memory.setByteAt(0xC000, 0x2F);

            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x21; // ld hl,0xC000
            rom[0x103] = 0x00;
            rom[0x104] = 0xC0;
            rom[0x105] = 0xBE; // cp (hl)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x106, cpu.registers.PC, "The PC should equal 0x106.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            memory.setByteAt(0xC000, 0x3C);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x106, cpu.registers.PC, "The PC should equal 0x106.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            memory.setByteAt(0xC000, 0x40);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.CARRY, cpu.registers.F, "The SUB and CARRY flags should be set.");
            assertEquals(0x106, cpu.registers.PC, "The PC should equal 0x106.");
        }

        // op code 0xBF
        @Test
        void cp_a_test() {
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x06; // ld b,0x2F
            rom[0x103] = 0x2F;
            rom[0x104] = 0xB8; // cp b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x06; // ld b,0x3C
            rom[0x103] = 0x3C;
            rom[0x104] = 0xB8; // cp b

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0x06; // ld b,0x40
            rom[0x103] = 0x40;
            rom[0x104] = 0xB8; // cp b

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.CARRY, cpu.registers.F, "The SUB and CARRY flags should be set.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }
    }
}
