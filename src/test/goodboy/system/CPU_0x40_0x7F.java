package test.goodboy.system;

import goodboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPU_0x40_0x7F {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

    static class CPU_0x40_0x4F {
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

        // op code 0x40
        @Test
        void ld_b_b_test() {
            rom[0x100] = 0x06; // ld b,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x40; // ld b,b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.B, "The B register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x41
        @Test
        void ld_b_c_test() {
            rom[0x100] = 0x0E; // ld c,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x41; // ld b,c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.B, "The B register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x42
        @Test
        void ld_b_d_test() {
            rom[0x100] = 0x16; // ld d,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x42; // ld b,d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.B, "The B register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x43
        @Test
        void ld_b_e_test() {
            rom[0x100] = 0x1E; // ld e,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x43; // ld b,e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.B, "The B register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x44
        @Test
        void ld_b_h_test() {
            rom[0x100] = 0x26; // ld h,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x44; // ld b,h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.B, "The B register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x45
        @Test
        void ld_b_l_test() {
            rom[0x100] = 0x2E; // ld l,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x45; // ld b,l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.B, "The B register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x46
        @Test
        void ld_b_hlp_test() {
            memory.setByteAt(0xC000, 0x50);

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x46; // ld b,(hl)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.B, "The B register should equal 0x50.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0x47
        @Test
        void ld_b_a_test() {
            rom[0x100] = 0x3E; // ld a,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x47; // ld b,a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.B, "The B register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x48
        @Test
        void ld_c_b_test() {
            rom[0x100] = 0x06; // ld b,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x48; // ld c,b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.C, "The C register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x49
        @Test
        void ld_c_c_test() {
            rom[0x100] = 0x0E; // ld c,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x49; // ld c,c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.C, "The C register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x4A
        @Test
        void ld_c_d_test() {
            rom[0x100] = 0x16; // ld d,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x4A; // ld c,d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.C, "The C register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x4B
        @Test
        void ld_c_e_test() {
            rom[0x100] = 0x1E; // ld e,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x4B; // ld c,e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.C, "The C register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x4C
        @Test
        void ld_c_h_test() {
            rom[0x100] = 0x26; // ld h,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x4C; // ld c,h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.C, "The C register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x4D
        @Test
        void ld_c_l_test() {
            rom[0x100] = 0x2E; // ld l,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x4D; // ld c,l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.C, "The C register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x4E
        @Test
        void ld_c_hlp_test() {
            memory.setByteAt(0xC000, 0x50);

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x4E; // ld c,(hl)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.C, "The C register should equal 0x50.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0x4F
        @Test
        void ld_c_a_test() {
            rom[0x100] = 0x3E; // ld a,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x4F; // ld c,a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.C, "The C register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }
    }

    static class CPU_0x50_0x5F {
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

        // op code 0x50
        @Test
        void ld_d_b_test() {
            rom[0x100] = 0x06; // ld b,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x50; // ld d,b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.D, "The D register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x51
        @Test
        void ld_d_c_test() {
            rom[0x100] = 0x0E; // ld c,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x51; // ld d,c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.D, "The D register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x52
        @Test
        void ld_d_d_test() {
            rom[0x100] = 0x16; // ld d,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x52; // ld d,d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.D, "The D register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x53
        @Test
        void ld_d_e_test() {
            rom[0x100] = 0x1E; // ld e,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x53; // ld d,e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.D, "The D register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x54
        @Test
        void ld_d_h_test() {
            rom[0x100] = 0x26; // ld h,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x54; // ld d,h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.D, "The D register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x55
        @Test
        void ld_d_l_test() {
            rom[0x100] = 0x2E; // ld l,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x55; // ld d,l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.D, "The D register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x56
        @Test
        void ld_d_hlp_test() {
            memory.setByteAt(0xC000, 0x50);

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x56; // ld d,(hl)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.D, "The D register should equal 0x50.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x57
        @Test
        void ld_d_a_test() {
            rom[0x100] = 0x3E; // ld a,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x57; // ld d,a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.D, "The D register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x58
        @Test
        void ld_e_b_test() {
            rom[0x100] = 0x06; // ld b,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x58; // ld e,b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.E, "The E register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x59
        @Test
        void ld_e_c_test() {
            rom[0x100] = 0x0E; // ld c,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x59; // ld e,c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.E, "The E register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x5A
        @Test
        void ld_e_d_test() {
            rom[0x100] = 0x16; // ld d,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x5A; // ld e,d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.E, "The E register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x5B
        @Test
        void ld_e_e_test() {
            rom[0x100] = 0x1E; // ld e,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x5B; // ld e,e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.E, "The E register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x5C
        @Test
        void ld_e_h_test() {
            rom[0x100] = 0x26; // ld h,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x5C; // ld e,h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.E, "The E register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x5D
        @Test
        void ld_e_l_test() {
            rom[0x100] = 0x2E; // ld l,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x5D; // ld e,l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.E, "The E register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x5E
        @Test
        void ld_e_hlp_test() {
            memory.setByteAt(0xC000, 0x50);

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x5E; // ld e,(hl)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.E, "The E register should equal 0x50.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x5F
        @Test
        void ld_e_a_test() {
            rom[0x100] = 0x3E; // ld a,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x5F; // ld e,a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.E, "The E register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }
    }

    static class CPU_0x60_0x6F {
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

        // op code 0x60
        @Test
        void ld_h_b_test() {
            rom[0x100] = 0x06; // ld b,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x60; // ld h,b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.H, "The H register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x61
        @Test
        void ld_h_c_test() {
            rom[0x100] = 0x0E; // ld c,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x61; // ld h,c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.H, "The H register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x62
        @Test
        void ld_h_d_test() {
            rom[0x100] = 0x16; // ld d,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x62; // ld h,d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.H, "The H register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x63
        @Test
        void ld_h_e_test() {
            rom[0x100] = 0x1E; // ld e,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x63; // ld h,e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.H, "The H register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x64
        @Test
        void ld_h_h_test() {
            rom[0x100] = 0x26; // ld h,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x64; // ld h,h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.H, "The H register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x65
        @Test
        void ld_h_l_test() {
            rom[0x100] = 0x2E; // ld l,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x65; // ld h,l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.H, "The H register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x66
        @Test
        void ld_h_hlp_test() {
            memory.setByteAt(0xC000, 0x50);

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x66; // ld h,(hl)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.H, "The H register should equal 0x50.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0x67
        @Test
        void ld_h_a_test() {
            rom[0x100] = 0x3E; // ld a,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x67; // ld h,a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.H, "The H register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x68
        @Test
        void ld_l_b_test() {
            rom[0x100] = 0x06; // ld b,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x68; // ld l,b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.L, "The L register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x69
        @Test
        void ld_l_c_test() {
            rom[0x100] = 0x0E; // ld c,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x69; // ld l,c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.L, "The L register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x6A
        @Test
        void ld_l_d_test() {
            rom[0x100] = 0x16; // ld d,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x6A; // ld l,d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.L, "The L register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x6B
        @Test
        void ld_l_e_test() {
            rom[0x100] = 0x1E; // ld e,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x6B; // ld l,e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.L, "The L register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x6C
        @Test
        void ld_l_h_test() {
            rom[0x100] = 0x26; // ld h,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x6C; // ld l,h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.L, "The L register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x6D
        @Test
        void ld_l_l_test() {
            rom[0x100] = 0x2E; // ld l,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x6D; // ld l,l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.L, "The L register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x6E
        @Test
        void ld_l_hlp_test() {
            memory.setByteAt(0xC000, 0x50);

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x6E; // ld l,(hl)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.L, "The L register should equal 0x50.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0x6F
        @Test
        void ld_l_a_test() {
            rom[0x100] = 0x3E; // ld a,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x6F; // ld l,a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.L, "The L register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }
    }

    static class CPU_0x70_0x7F {
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
            rom = new int[0x7FFF];
            cpu.reset();
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
        }

        @AfterEach
        void tearDown() {
            rom = null;
        }

        // op code 0x70
        @Test
        void ld_hlp_b_test() {
            memory.setByteAt(0xC000, 0x50);

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x06; // ld b,0xFF
            rom[0x104] = 0xFF;
            rom[0x105] = 0x70; // ld (hl),b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xFF, memory.getByteAt(0xC000), "The value at 0xC000 should equal 0xFF.");
            assertEquals(0x106, cpu.registers.PC, "The PC register should equal 0x106.");
        }

        // op code 0x71
        @Test
        void ld_hlp_c_test() {
            memory.setByteAt(0xC000, 0x50);

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x0E; // ld c,0xFF
            rom[0x104] = 0xFF;
            rom[0x105] = 0x71; // ld (hl),c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xFF, memory.getByteAt(0xC000), "The value at 0xC000 should equal 0xFF.");
            assertEquals(0x106, cpu.registers.PC, "The PC register should equal 0x106.");
        }

        // op code 0x72
        @Test
        void ld_hlp_d_test() {
            memory.setByteAt(0xC000, 0x50);

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x16; // ld d,0xFF
            rom[0x104] = 0xFF;
            rom[0x105] = 0x72; // ld (hl),d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xFF, memory.getByteAt(0xC000), "The value at 0xC000 should equal 0xFF.");
            assertEquals(0x106, cpu.registers.PC, "The PC register should equal 0x106.");
        }

        // op code 0x73
        @Test
        void ld_hlp_e_test() {
            memory.setByteAt(0xC000, 0x50);

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x1E; // ld e,0xFF
            rom[0x104] = 0xFF;
            rom[0x105] = 0x73; // ld (hl),e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xFF, memory.getByteAt(0xC000), "The value at 0xC000 should equal 0xFF.");
            assertEquals(0x106, cpu.registers.PC, "The PC register should equal 0x106.");
        }

        // op code 0x74
        @Test
        void ld_hlp_h_test() {
            memory.setByteAt(0xC000, 0x50);

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x74; // ld (hl),h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xC0, memory.getByteAt(0xC000), "The value at 0xC000 should equal 0xC0.");
            assertEquals(0x104, cpu.registers.PC, "The PC register should equal 0x104.");
        }

        // op code 0x75
        @Test
        void ld_hlp_l_test() {
            memory.setByteAt(0xC000, 0x50);

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x75; // ld (hl),l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, memory.getByteAt(0xC000), "The value at 0xC000 should equal 0x00.");
            assertEquals(0x104, cpu.registers.PC, "The PC register should equal 0x104.");
        }

        // op code 0x76
        @Test
        void halt_test() {
            // halt the cpu normally
            rom[0x100] = 0xFB; // ei
            rom[0x101] = 0x00; // nop
            rom[0x102] = 0x76; // halt

            memory.loadROM(rom);

            assertFalse(cpu.getIME(), "The IME should be disabled by default.");
            assertFalse(cpu.isHalted, "The CPU should not be halted by default.");

            cpu.tick();
            cpu.tick();
            cpu.tick();

            assertEquals(0x103, cpu.registers.PC);
            assertTrue(cpu.getIME(), "The IME should be enabled.");
            assertTrue(cpu.isHalted, "The CPU should be halted.");
            assertEquals(12, cpu.cycles);

            // TODO: test interrupt calls
        }

        @Test
        void halt_bug_test() {
            rom[0x100] = 0xAF; // xor a
            rom[0x101] = 0x76; // halt
            rom[0x102] = 0x3C; // inc a

            memory.loadROM(rom);

            memory.setByteAt(IORegisters.INTERRUPT_ENABLE, 0x00);
            memory.setByteAt(IORegisters.INTERRUPT_FLAGS, 0x00);

            assertFalse(cpu.getIME());

            cpu.tick(); // xor a
            assertEquals(0x101, cpu.registers.PC, "should execute normally");

            memory.setByteAt(IORegisters.INTERRUPT_ENABLE, 0x1D);
            memory.setByteAt(IORegisters.INTERRUPT_FLAGS, 0xCA);

            cpu.tick(); // halt
            assertEquals(0x102, cpu.registers.PC, "should increment normally");
            assertFalse(cpu.isHalted, "should not halt");
            assertTrue(cpu.haltBug, "should activate halt bug");

            cpu.tick(); // inc a
            assertEquals(0x102, cpu.registers.PC, "should not increment PC due to halt bug");
            assertEquals(0x01, cpu.registers.A, "should increment A by 1");

            cpu.tick(); // inc a
            assertEquals(0x103, cpu.registers.PC, "should increment PC normally");
            assertEquals(0x02, cpu.registers.A, "should increment A by 1");
            assertFalse(cpu.isHalted, "should not be halted");
            assertEquals(16, cpu.cycles, "should have taken 16 clock cycles");
        }

        @Test
        void halt_bug2_test() {
            rom[0x100] = 0x76; // halt
            rom[0x101] = 0xFA; // ld a, (0x1284)
            rom[0x102] = 0x84;
            rom[0x103] = 0x12;

            memory.loadROM(rom);

            cpu.registers.setDE(0xC000);
            memory.setByteAt(0x84FA, 0x42);
            memory.setByteAt(IORegisters.INTERRUPT_ENABLE, 0x1D);
            memory.setByteAt(IORegisters.INTERRUPT_FLAGS, 0xCA);

            assertFalse(cpu.getIME(), "IME should be disabled");

            cpu.tick(); // halt
            assertEquals(0x101, cpu.registers.PC, "should increment PC normally");
            assertFalse(cpu.isHalted, "should not halt CPU");
            assertTrue(cpu.haltBug, "should activate halt bug");

            cpu.tick(); // ld a, (0x84FA) ; this operation is borked by the halt bug
            assertEquals(0x103, cpu.registers.PC, "should increment PC by an unintended amount");
            assertEquals(0x42, cpu.registers.A, "should load an unintended value into A");

            cpu.tick(); // ld (de), a ; this operation was created due to the halt bug
            assertEquals(0x104, cpu.registers.PC, "should increment PC appropriately for the bugged instruction");
            assertEquals(0x42, memory.getByteAt(0xC000), "should load the value of A into the address pointed to by DE");
            assertFalse(cpu.isHalted, "should not be halted");
            assertEquals(28, cpu.cycles, "should have taken 28 clock cycles");
        }

        @Test
        void halt_bug_freeze_test() {
            rom[0x100] = 0x76; // halt
            rom[0x101] = 0x76; // halt

            memory.loadROM(rom);

            memory.setByteAt(IORegisters.INTERRUPT_ENABLE, 0x1D);
            memory.setByteAt(IORegisters.INTERRUPT_FLAGS, 0xCA);

            assertFalse(cpu.getIME(), "IME should be disabled");

            // no matter how many ticks we do it's always going to be a halt instruction, so let's tick just 5 times
            cpu.tick(); // halt
            assertEquals(0x101, cpu.registers.PC, "should increment PC normally");
            assertFalse(cpu.isHalted, "should not be halted");
            assertTrue(cpu.haltBug, "should activate halt bug");

            cpu.tick(); // halt
            assertEquals(0x101, cpu.registers.PC, "should 'freeze' PC due to halt bug");

            cpu.tick(); // halt
            assertEquals(0x101, cpu.registers.PC, "should 'freeze' PC due to halt bug");

            cpu.tick(); // halt
            assertEquals(0x101, cpu.registers.PC, "should 'freeze' PC due to halt bug");

            cpu.tick(); // halt
            assertEquals(0x101, cpu.registers.PC, "should 'freeze' PC due to halt bug");
            assertFalse(cpu.isHalted, "should not be halted");
            assertEquals(20, cpu.cycles, "should have taken 20 clock cycles");
        }

        // op code 0x77
        @Test
        void ld_hlp_a_test() {
            memory.setByteAt(0xC000, 0x50);

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x3E; // ld a,0xFF
            rom[0x104] = 0xFF;
            rom[0x105] = 0x77; // ld (hl),a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xFF, memory.getByteAt(0xC000), "The value at 0xC000 should equal 0xFF.");
            assertEquals(0x106, cpu.registers.PC, "The PC register should equal 0x106.");
        }

        // op code 0x78
        @Test
        void ld_a_b_test() {
            rom[0x100] = 0x06; // ld b,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x78; // ld a,b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x79
        @Test
        void ld_a_c_test() {
            rom[0x100] = 0x0E; // ld c,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x79; // ld a,c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x7A
        @Test
        void ld_a_d_test() {
            rom[0x100] = 0x16; // ld d,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x7A; // ld a,d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x7B
        @Test
        void ld_a_e_test() {
            rom[0x100] = 0x1E; // ld e,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x7B; // ld a,e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x7C
        @Test
        void ld_a_h_test() {
            rom[0x100] = 0x26; // ld h,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x7C; // ld a,h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x7D
        @Test
        void ld_a_l_test() {
            rom[0x100] = 0x2E; // ld l,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x7D; // ld a,l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x7E
        @Test
        void ld_a_hlp_test() {
            memory.setByteAt(0xC000, 0x50);

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x7E; // ld a,(hl)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0x7F
        @Test
        void ld_a_a_test() {
            rom[0x100] = 0x3E; // ld a,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x7F; // ld a,a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }
    }
}
