package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPU_0xCB40_0xCB7F {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

    static class CPU_0xCB40_0xCB4F {
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

        // op code 0xCB40
        @Test
        void bit_0_b_test() {
            rom[0x100] = 0x06; // ld b,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // bit 0,b
            rom[0x103] = 0x40;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0x04
            rom[0x101] = 0x04;
            rom[0x102] = 0xCB; // bit 0,b
            rom[0x103] = 0x40;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB41
        @Test
        void bit_0_c_test() {
            rom[0x100] = 0x0E; // ld c,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // bit 0,c
            rom[0x103] = 0x41;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0x04
            rom[0x101] = 0x04;
            rom[0x102] = 0xCB; // bit 0,c
            rom[0x103] = 0x41;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB42
        @Test
        void bit_0_d_test() {
            rom[0x100] = 0x16; // ld d,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // bit 0,d
            rom[0x103] = 0x42;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld d,0x04
            rom[0x101] = 0x04;
            rom[0x102] = 0xCB; // bit 0,d
            rom[0x103] = 0x42;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB43
        @Test
        void bit_0_e_test() {
            rom[0x100] = 0x1E; // ld e,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // bit 0,e
            rom[0x103] = 0x43;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0x04
            rom[0x101] = 0x04;
            rom[0x102] = 0xCB; // bit 0,e
            rom[0x103] = 0x43;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB44
        @Test
        void bit_0_h_test() {
            rom[0x100] = 0x26; // ld h,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // bit 0,h
            rom[0x103] = 0x44;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0x04
            rom[0x101] = 0x04;
            rom[0x102] = 0xCB; // bit 0,h
            rom[0x103] = 0x44;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB45
        @Test
        void bit_0_l_test() {
            rom[0x100] = 0x2E; // ld l,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // bit 0,l
            rom[0x103] = 0x45;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0x04
            rom[0x101] = 0x04;
            rom[0x102] = 0xCB; // bit 0,l
            rom[0x103] = 0x45;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB46
        @Test
        void bit_0_hlp_test() {
            memory.setByteAt(0xC000, 0x01); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // bit 0,(hl)
            rom[0x104] = 0x46;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            memory.setByteAt(0xC000, 0x04); // This is the value that HL will point to.

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB47
        @Test
        void bit_0_a_test() {
            rom[0x100] = 0x3E; // ld a,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0xCB; // bit 0,a
            rom[0x103] = 0x47;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0x04
            rom[0x101] = 0x04;
            rom[0x102] = 0xCB; // bit 0,a
            rom[0x103] = 0x47;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB48
        @Test
        void bit_1_b_test() {
            rom[0x100] = 0x06; // ld b,0x02
            rom[0x101] = 0x02;
            rom[0x102] = 0xCB; // bit 1,b
            rom[0x103] = 0x48;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 1,b
            rom[0x103] = 0x48;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB49
        @Test
        void bit_1_c_test() {
            rom[0x100] = 0x0E; // ld c,0x02
            rom[0x101] = 0x02;
            rom[0x102] = 0xCB; // bit 1,c
            rom[0x103] = 0x49;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 1,c
            rom[0x103] = 0x49;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB4A
        @Test
        void bit_1_d_test() {
            rom[0x100] = 0x16; // ld d,0x02
            rom[0x101] = 0x02;
            rom[0x102] = 0xCB; // bit 1,d
            rom[0x103] = 0x4A;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld d,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 1,d
            rom[0x103] = 0x4A;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB4B
        @Test
        void bit_1_e_test() {
            rom[0x100] = 0x1E; // ld e,0x02
            rom[0x101] = 0x02;
            rom[0x102] = 0xCB; // bit 1,e
            rom[0x103] = 0x4B;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 1,e
            rom[0x103] = 0x4B;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB4C
        @Test
        void bit_1_h_test() {
            rom[0x100] = 0x26; // ld h,0x02
            rom[0x101] = 0x02;
            rom[0x102] = 0xCB; // bit 1,h
            rom[0x103] = 0x4C;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 1,h
            rom[0x103] = 0x4C;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB4D
        @Test
        void bit_1_l_test() {
            rom[0x100] = 0x2E; // ld l,0x02
            rom[0x101] = 0x02;
            rom[0x102] = 0xCB; // bit 1,l
            rom[0x103] = 0x4D;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 1,l
            rom[0x103] = 0x4D;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB4E
        @Test
        void bit_1_hlp_test() {
            memory.setByteAt(0xC000, 0x02); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // bit 1,(hl)
            rom[0x104] = 0x4E;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB4F
        @Test
        void bit_1_a_test() {
            rom[0x100] = 0x3E; // ld a,0x02
            rom[0x101] = 0x02;
            rom[0x102] = 0xCB; // bit 1,a
            rom[0x103] = 0x4F;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 1,a
            rom[0x103] = 0x4F;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }
    }

    static class CPU_0xCB50_0xCB5F {
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

        // op code 0xCB50
        @Test
        void bit_2_b_test() {
            rom[0x100] = 0x06; // ld b,0x04
            rom[0x101] = 0x04;
            rom[0x102] = 0xCB; // bit 2,b
            rom[0x103] = 0x50;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 2,b
            rom[0x103] = 0x50;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB51
        @Test
        void bit_2_c_test() {
            rom[0x100] = 0x0E; // ld c,0x04
            rom[0x101] = 0x04;
            rom[0x102] = 0xCB; // bit 2,c
            rom[0x103] = 0x51;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 2,c
            rom[0x103] = 0x51;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB52
        @Test
        void bit_2_d_test() {
            rom[0x100] = 0x16; // ld d,0x04
            rom[0x101] = 0x04;
            rom[0x102] = 0xCB; // bit 2,d
            rom[0x103] = 0x52;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld d,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 2,d
            rom[0x103] = 0x52;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB53
        @Test
        void bit_2_e_test() {
            rom[0x100] = 0x1E; // ld e,0x04
            rom[0x101] = 0x04;
            rom[0x102] = 0xCB; // bit 2,e
            rom[0x103] = 0x53;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 2,e
            rom[0x103] = 0x53;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB54
        @Test
        void bit_2_h_test() {
            rom[0x100] = 0x26; // ld h,0x04
            rom[0x101] = 0x04;
            rom[0x102] = 0xCB; // bit 2,h
            rom[0x103] = 0x54;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 2,h
            rom[0x103] = 0x54;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB55
        @Test
        void bit_2_l_test() {
            rom[0x100] = 0x2E; // ld l,0x04
            rom[0x101] = 0x04;
            rom[0x102] = 0xCB; // bit 2,l
            rom[0x103] = 0x55;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 2,l
            rom[0x103] = 0x55;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB56
        @Test
        void bit_2_hlp_test() {
            memory.setByteAt(0xC000, 0x04); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // bit 2,(hl)
            rom[0x104] = 0x56;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB57
        @Test
        void bit_2_a_test() {
            rom[0x100] = 0x3E; // ld a,0x04
            rom[0x101] = 0x04;
            rom[0x102] = 0xCB; // bit 2,a
            rom[0x103] = 0x57;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 2,a
            rom[0x103] = 0x57;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB58
        @Test
        void bit_3_b_test() {
            rom[0x100] = 0x06; // ld b,0x08
            rom[0x101] = 0x08;
            rom[0x102] = 0xCB; // bit 3,b
            rom[0x103] = 0x58;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 3,b
            rom[0x103] = 0x58;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB59
        @Test
        void bit_3_c_test() {
            rom[0x100] = 0x0E; // ld c,0x08
            rom[0x101] = 0x08;
            rom[0x102] = 0xCB; // bit 3,c
            rom[0x103] = 0x59;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 3,c
            rom[0x103] = 0x59;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB5A
        @Test
        void bit_3_d_test() {
            rom[0x100] = 0x16; // ld d,0x08
            rom[0x101] = 0x08;
            rom[0x102] = 0xCB; // bit 3,d
            rom[0x103] = 0x5A;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld d,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 3,d
            rom[0x103] = 0x5A;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB5B
        @Test
        void bit_3_e_test() {
            rom[0x100] = 0x1E; // ld e,0x08
            rom[0x101] = 0x08;
            rom[0x102] = 0xCB; // bit 3,e
            rom[0x103] = 0x5B;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 3,e
            rom[0x103] = 0x5B;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB5C
        @Test
        void bit_3_h_test() {
            rom[0x100] = 0x26; // ld h,0x08
            rom[0x101] = 0x08;
            rom[0x102] = 0xCB; // bit 3,h
            rom[0x103] = 0x5C;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 3,h
            rom[0x103] = 0x5C;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB5D
        @Test
        void bit_3_l_test() {
            rom[0x100] = 0x2E; // ld l,0x08
            rom[0x101] = 0x08;
            rom[0x102] = 0xCB; // bit 3,l
            rom[0x103] = 0x5D;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 3,l
            rom[0x103] = 0x5D;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB5E
        @Test
        void bit_3_hlp_test() {
            memory.setByteAt(0xC000, 0x08); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // bit 3,(hl)
            rom[0x104] = 0x5E;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB5F
        @Test
        void bit_3_a_test() {
            rom[0x100] = 0x3E; // ld a,0x08
            rom[0x101] = 0x08;
            rom[0x102] = 0xCB; // bit 3,a
            rom[0x103] = 0x5F;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 3,a
            rom[0x103] = 0x5F;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }
    }

    static class CPU_0xCB60_0xCB6F {
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

        // op code 0xCB60
        @Test
        void bit_4_b_test() {
            rom[0x100] = 0x06; // ld b,0x10
            rom[0x101] = 0x10;
            rom[0x102] = 0xCB; // bit 4,b
            rom[0x103] = 0x60;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 4,b
            rom[0x103] = 0x60;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB61
        @Test
        void bit_4_c_test() {
            rom[0x100] = 0x0E; // ld c,0x10
            rom[0x101] = 0x10;
            rom[0x102] = 0xCB; // bit 4,c
            rom[0x103] = 0x61;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 4,c
            rom[0x103] = 0x61;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB62
        @Test
        void bit_4_d_test() {
            rom[0x100] = 0x16; // ld d,0x10
            rom[0x101] = 0x10;
            rom[0x102] = 0xCB; // bit 4,d
            rom[0x103] = 0x62;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld d,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 4,d
            rom[0x103] = 0x62;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB63
        @Test
        void bit_4_e_test() {
            rom[0x100] = 0x1E; // ld e,0x10
            rom[0x101] = 0x10;
            rom[0x102] = 0xCB; // bit 4,e
            rom[0x103] = 0x63;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 4,e
            rom[0x103] = 0x63;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB64
        @Test
        void bit_4_h_test() {
            rom[0x100] = 0x26; // ld h,0x10
            rom[0x101] = 0x10;
            rom[0x102] = 0xCB; // bit 4,h
            rom[0x103] = 0x64;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 4,h
            rom[0x103] = 0x64;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB65
        @Test
        void bit_4_l_test() {
            rom[0x100] = 0x2E; // ld l,0x10
            rom[0x101] = 0x10;
            rom[0x102] = 0xCB; // bit 4,l
            rom[0x103] = 0x65;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 4,l
            rom[0x103] = 0x65;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB66
        @Test
        void bit_4_hlp_test() {
            memory.setByteAt(0xC000, 0x10); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // bit 4,(hl)
            rom[0x104] = 0x66;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB67
        @Test
        void bit_4_a_test() {
            rom[0x100] = 0x3E; // ld a,0x10
            rom[0x101] = 0x10;
            rom[0x102] = 0xCB; // bit 4,a
            rom[0x103] = 0x67;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 4,a
            rom[0x103] = 0x67;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB68
        @Test
        void bit_5_b_test() {
            rom[0x100] = 0x06; // ld b,0x20
            rom[0x101] = 0x20;
            rom[0x102] = 0xCB; // bit 5,b
            rom[0x103] = 0x68;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 5,b
            rom[0x103] = 0x68;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB69
        @Test
        void bit_5_c_test() {
            rom[0x100] = 0x0E; // ld c,0x20
            rom[0x101] = 0x20;
            rom[0x102] = 0xCB; // bit 5,c
            rom[0x103] = 0x69;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 5,c
            rom[0x103] = 0x69;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB6A
        @Test
        void bit_5_d_test() {
            rom[0x100] = 0x16; // ld d,0x20
            rom[0x101] = 0x20;
            rom[0x102] = 0xCB; // bit 5,d
            rom[0x103] = 0x6A;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld d,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 5,d
            rom[0x103] = 0x6A;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB6B
        @Test
        void bit_5_e_test() {
            rom[0x100] = 0x1E; // ld e,0x20
            rom[0x101] = 0x20;
            rom[0x102] = 0xCB; // bit 5,e
            rom[0x103] = 0x6B;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 5,e
            rom[0x103] = 0x6B;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB6C
        @Test
        void bit_5_h_test() {
            rom[0x100] = 0x26; // ld h,0x20
            rom[0x101] = 0x20;
            rom[0x102] = 0xCB; // bit 5,h
            rom[0x103] = 0x6C;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 5,h
            rom[0x103] = 0x6C;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB6D
        @Test
        void bit_5_l_test() {
            rom[0x100] = 0x2E; // ld l,0x20
            rom[0x101] = 0x20;
            rom[0x102] = 0xCB; // bit 5,l
            rom[0x103] = 0x6D;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 5,l
            rom[0x103] = 0x6D;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB6E
        @Test
        void bit_5_hlp_test() {
            memory.setByteAt(0xC000, 0x20); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // bit 5,(hl)
            rom[0x104] = 0x6E;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB6F
        @Test
        void bit_5_a_test() {
            rom[0x100] = 0x3E; // ld a,0x20
            rom[0x101] = 0x20;
            rom[0x102] = 0xCB; // bit 5,a
            rom[0x103] = 0x6F;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 5,a
            rom[0x103] = 0x6F;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }
    }

    static class CPU_0xCB70_0xCB7F {
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

        // op code 0xCB70
        @Test
        void bit_6_b_test() {
            rom[0x100] = 0x06; // ld b,0x40
            rom[0x101] = 0x40;
            rom[0x102] = 0xCB; // bit 6,b
            rom[0x103] = 0x70;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 6,b
            rom[0x103] = 0x70;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB71
        @Test
        void bit_6_c_test() {
            rom[0x100] = 0x0E; // ld c,0x40
            rom[0x101] = 0x40;
            rom[0x102] = 0xCB; // bit 6,c
            rom[0x103] = 0x71;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 6,c
            rom[0x103] = 0x71;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB72
        @Test
        void bit_6_d_test() {
            rom[0x100] = 0x16; // ld d,0x40
            rom[0x101] = 0x40;
            rom[0x102] = 0xCB; // bit 6,d
            rom[0x103] = 0x72;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld d,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 6,d
            rom[0x103] = 0x72;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB73
        @Test
        void bit_6_e_test() {
            rom[0x100] = 0x1E; // ld e,0x40
            rom[0x101] = 0x40;
            rom[0x102] = 0xCB; // bit 6,e
            rom[0x103] = 0x73;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 6,e
            rom[0x103] = 0x73;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB74
        @Test
        void bit_6_h_test() {
            rom[0x100] = 0x26; // ld h,0x40
            rom[0x101] = 0x40;
            rom[0x102] = 0xCB; // bit 6,h
            rom[0x103] = 0x74;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 6,h
            rom[0x103] = 0x74;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB75
        @Test
        void bit_6_l_test() {
            rom[0x100] = 0x2E; // ld l,0x40
            rom[0x101] = 0x40;
            rom[0x102] = 0xCB; // bit 6,l
            rom[0x103] = 0x75;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 6,l
            rom[0x103] = 0x75;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB76
        @Test
        void bit_6_hlp_test() {
            memory.setByteAt(0xC000, 0x40); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // bit 6,(hl)
            rom[0x104] = 0x76;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB77
        @Test
        void bit_6_a_test() {
            rom[0x100] = 0x3E; // ld a,0x40
            rom[0x101] = 0x40;
            rom[0x102] = 0xCB; // bit 6,a
            rom[0x103] = 0x77;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 6,a
            rom[0x103] = 0x77;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB78
        @Test
        void bit_7_b_test() {
            rom[0x100] = 0x06; // ld b,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // bit 7,b
            rom[0x103] = 0x78;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 7,b
            rom[0x103] = 0x78;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB79
        @Test
        void bit_7_c_test() {
            rom[0x100] = 0x0E; // ld c,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // bit 7,c
            rom[0x103] = 0x79;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 7,c
            rom[0x103] = 0x79;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB7A
        @Test
        void bit_7_d_test() {
            rom[0x100] = 0x16; // ld d,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // bit 7,d
            rom[0x103] = 0x7A;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld d,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 7,d
            rom[0x103] = 0x7A;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB7B
        @Test
        void bit_7_e_test() {
            rom[0x100] = 0x1E; // ld e,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // bit 7,e
            rom[0x103] = 0x7B;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 7,e
            rom[0x103] = 0x7B;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB7C
        @Test
        void bit_7_h_test() {
            rom[0x100] = 0x26; // ld h,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // bit 7,h
            rom[0x103] = 0x7C;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 7,h
            rom[0x103] = 0x7C;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB7D
        @Test
        void bit_7_l_test() {
            rom[0x100] = 0x2E; // ld l,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // bit 7,l
            rom[0x103] = 0x7D;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 7,l
            rom[0x103] = 0x7D;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCB7E
        @Test
        void bit_7_hlp_test() {
            memory.setByteAt(0xC000, 0x80); // This is the value that HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0xCB; // bit 7,(hl)
            rom[0x104] = 0x7E;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xCB7F
        @Test
        void bit_7_a_test() {
            rom[0x100] = 0x3E; // ld a,0x80
            rom[0x101] = 0x80;
            rom[0x102] = 0xCB; // bit 7,a
            rom[0x103] = 0x7F;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0xCB; // bit 7,a
            rom[0x103] = 0x7F;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }
    }
}
