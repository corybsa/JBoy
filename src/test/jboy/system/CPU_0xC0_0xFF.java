package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPU_0xC0_0xFF {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

    static class CPU_0xC0_0xCF {
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

        // op code 0xC0
        @Test
        void ret_nz_test() {
            memory.setByteAt(0xFFFD, 0x01);
            memory.setByteAt(0xFFFC, 0x03);

            cpu.setFlags(CPU.Flags.ZERO);
            cpu.registers.SP = 0xFFFC;
            cpu.registers.PC = 0x1234;

            rom[0x1234] = 0xC0; // ret nz

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC");
            assertEquals(0x1235, cpu.registers.PC, "The PC should equal 0x1235");

            cpu.resetFlags(CPU.Flags.ZERO);
            cpu.registers.PC = 0x1234;
            cpu.tick();
            assertEquals(0xFFFE, cpu.registers.SP, "The SP should equal 0xFFFE");
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103");
        }

        // op code 0xC1
        @Test
        void pop_bc_test() {
            memory.setByteAt(0xFFFD, 0xBE);
            memory.setByteAt(0xFFFC, 0xEF);
            cpu.registers.SP = 0xFFFC;

            rom[0x100] = 0xC1; // pop bc

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xBEEF, cpu.registers.getBC(), "The BC register should equal 0xBEEF.");
            assertEquals(0xFFFE, cpu.registers.SP, "The SP should equal 0xFFFE.");
            assertEquals(0x101, cpu.registers.PC, "The PC should equal 0x101");
        }

        // op code 0xC2
        @Test
        void jp_nz_xx_test() {
            cpu.resetFlags(CPU.Flags.ZERO);
            rom[0x100] = 0xC2; // jp nz,0x8000
            rom[0x101] = 0x00;
            rom[0x102] = 0x80;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0x8000, cpu.registers.PC, "The PC should equal 0x8000.");

            cpu.setFlags(CPU.Flags.ZERO);
            cpu.registers.PC = 0x100;
            cpu.tick();
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103.");
        }

        // op code 0xC3
        @Test
        void jp_xx_test() {
            rom[0x100] = 0xC3; // jp 0x8000
            rom[0x101] = 0x00;
            rom[0x102] = 0x80;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0x8000, cpu.registers.PC, "The PC should equal 0x8000.");
        }

        // op code 0xC4
        @Test
        void call_nz_xx_test() {
            cpu.setFlags(CPU.Flags.ZERO);
            rom[0x100] = 0xC4; // call nz,0x1234
            rom[0x101] = 0x34;
            rom[0x102] = 0x12;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFE, cpu.registers.SP, "The SP should equal 0xFFFE");
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO);
            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC");
            assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01");
            assertEquals(0x03, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x03");
            assertEquals(0x1234, cpu.registers.PC, "The PC should equal 0x1234");
        }

        // op code 0xC5
        @Test
        void push_bc_test() {
            rom[0x100] = 0x01; // ld bc,0xBEEF
            rom[0x101] = 0xEF;
            rom[0x102] = 0xBE;
            rom[0x103] = 0xC5; // push bc

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC");
            assertEquals(0xBE, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0xEF");
            assertEquals(0xEF, memory.getByteAt(0xFFFC), "The value at address 0xFFFC should equal 0xBE");
            assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
        }

        // op code 0xC6
        @Test
        void add_a_x_test() {
            rom[0x100] = 0x3E; // ld a,0x3A
            rom[0x101] = 0x3A;
            rom[0x102] = 0xC6; // add a,0xC6
            rom[0x103] = 0xC6;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xC7
        @Test
        void rst_00_test() {
            rom[0x100] = 0xC7; // rst 0x00

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC.");
            assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01.");
            assertEquals(0x01, memory.getByteAt(0xFFFC), "The value at address 0xFFFC should equal 0x01.");
            assertEquals(0x00, cpu.registers.PC, "The PC should equal 0x00.");
        }

        // op code 0xC8
        @Test
        void ret_z_test() {
            memory.setByteAt(0xFFFD, 0x01);
            memory.setByteAt(0xFFFC, 0x03);

            cpu.setFlags(CPU.Flags.ZERO);
            cpu.registers.SP = 0xFFFC;
            cpu.registers.PC = 0x1234;

            rom[0x1234] = 0xC8; // ret z

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFE, cpu.registers.SP, "The SP should equal 0xFFFE");
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103");

            cpu.resetFlags(CPU.Flags.ZERO);
            cpu.registers.SP = 0xFFFC;
            cpu.registers.PC = 0x1234;
            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFE");
            assertEquals(0x1235, cpu.registers.PC, "The PC should equal 0x1235");
        }

        // op code 0xC9
        @Test
        void ret_test() {
            memory.setByteAt(0xFFFD, 0x01);
            memory.setByteAt(0xFFFC, 0x03);

            cpu.registers.SP = 0xFFFC;
            cpu.registers.PC = 0x1234;

            rom[0x1234] = 0xC9; // ret

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFE, cpu.registers.SP, "The SP should equal 0xFFFE");
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103");
        }

        // op code 0xCA
        @Test
        void jp_z_xx_test() {
            cpu.resetFlags(CPU.Flags.ZERO);
            rom[0x100] = 0xCA; // jp z,0x8000
            rom[0x101] = 0x00;
            rom[0x102] = 0x80;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103.");

            cpu.setFlags(CPU.Flags.ZERO);
            cpu.registers.PC = 0x100;
            cpu.tick();
            assertEquals(0x8000, cpu.registers.PC, "The PC should equal 0x8000.");
        }

        // op code 0xCC
        @Test
        void call_z_xx_test() {
            cpu.resetFlags(CPU.Flags.ZERO);
            rom[0x100] = 0xCC; // call z,0x1234
            rom[0x101] = 0x34;
            rom[0x102] = 0x12;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFE, cpu.registers.SP, "The SP should equal 0xFFFE");
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103");

            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.ZERO);
            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC");
            assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01");
            assertEquals(0x03, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x03");
            assertEquals(0x1234, cpu.registers.PC, "The PC should equal 0x1234");
        }

        // op code 0xCD
        @Test
        void call_xx_test() {
            rom[0x100] = 0xCD; // call 0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC");
            assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01");
            assertEquals(0x03, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x03");
            assertEquals(0xC000, cpu.registers.PC, "The PC should equal 0xC000");
        }

        // op code 0xCE
        @Test
        void adc_a_x_test() {
            cpu.setFlags(CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0xE1
            rom[0x101] = 0xE1;
            rom[0x102] = 0xCE; // adc a,0x1E
            rom[0x103] = 0x1E;

            memory.loadROM(rom);
            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xCF
        @Test
        void rst_08_test() {
            rom[0x100] = 0xCF; // rst 0x08

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC.");
            assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01.");
            assertEquals(0x01, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x00.");
            assertEquals(0x08, cpu.registers.PC, "The PC should equal 0x08.");
        }
    }

    static class CPU_0xD0_0xDF {
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

        // op code 0xD0
        @Test
        void ret_nc_test() {
            memory.setByteAt(0xFFFD, 0x01);
            memory.setByteAt(0xFFFC, 0x03);

            cpu.setFlags(CPU.Flags.CARRY);
            cpu.registers.PC = 0x1234;
            cpu.registers.SP = 0xFFFC;

            rom[0x1234] = 0xD0; // ret nc

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFE");
            assertEquals(0x1235, cpu.registers.PC, "The PC should equal 0x1235");

            cpu.resetFlags(CPU.Flags.CARRY);
            cpu.registers.PC = 0x1234;
            cpu.tick();
            assertEquals(0xFFFE, cpu.registers.SP, "The SP should equal 0xFFFE");
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103");
        }

        // op code 0xD1
        @Test
        void pop_de_test() {
            memory.setByteAt(0xFFFD, 0xBE);
            memory.setByteAt(0xFFFC, 0xEF);
            cpu.registers.SP = 0xFFFC;

            rom[0x100] = 0xD1; // pop de

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xBEEF, cpu.registers.getDE(), "The DE register should equal 0xBEEF.");
            assertEquals(0xFFFE, cpu.registers.SP, "The SP should equal 0xFFFE.");
            assertEquals(0x101, cpu.registers.PC, "The PC should equal 0x101");
        }

        // op code 0xD2
        @Test
        void jp_nc_xx_test() {
            cpu.resetFlags(CPU.Flags.CARRY);
            rom[0x100] = 0xD2; // jp nc,0x8000
            rom[0x101] = 0x00;
            rom[0x102] = 0x80;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0x8000, cpu.registers.PC, "The PC should equal 0x8000.");

            cpu.setFlags(CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.tick();
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103.");
        }

        // op code 0xD4
        @Test
        void call_nc_xx_test() {
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0xD4; // call nc,0x1234
            rom[0x101] = 0x34;
            rom[0x102] = 0x12;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFE, cpu.registers.SP, "The SP should equal 0xFFFE");
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.CARRY);
            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC");
            assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01");
            assertEquals(0x03, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x03");
            assertEquals(0x1234, cpu.registers.PC, "The PC should equal 0x1234");
        }

        // op code 0xD5
        @Test
        void push_de_test() {
            rom[0x100] = 0x11; // ld de,0xBEEF
            rom[0x101] = 0xEF;
            rom[0x102] = 0xBE;
            rom[0x103] = 0xD5; // push de

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC");
            assertEquals(0xBE, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0xBE");
            assertEquals(0xEF, memory.getByteAt(0xFFFC), "The value at address 0xFFFC should equal 0xEF");
            assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
        }

        // op code 0xD6
        @Test
        void sub_x_test() {
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0xD6; // sub 0x3E
            rom[0x103] = 0x3E;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0xD6; // sub 0x0F
            rom[0x103] = 0x0F;

            cpu.tick();
            cpu.tick();
            assertEquals(0x2F, cpu.registers.A, "The A register should equal 0x2F.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3E
            rom[0x101] = 0x3E;
            rom[0x102] = 0xD6; // sub 0x40
            rom[0x103] = 0x40;

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.A, "The A register should equal 0xFE.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.CARRY, cpu.registers.F, "The ZERO, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xD7
        @Test
        void rst_10_test() {
            rom[0x100] = 0xD7; // rst 0x10

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC.");
            assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01.");
            assertEquals(0x01, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x00.");
            assertEquals(0x10, cpu.registers.PC, "The PC should equal 0x10.");
        }

        // op code 0xD8
        @Test
        void ret_c_test() {
            memory.setByteAt(0xFFFD, 0x01);
            memory.setByteAt(0xFFFC, 0x03);

            cpu.setFlags(CPU.Flags.CARRY);
            cpu.registers.SP = 0xFFFC;
            cpu.registers.PC = 0x1234;

            rom[0x1234] = 0xD8; // ret c

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFE, cpu.registers.SP, "The SP should equal 0xFFFE");
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103");

            cpu.resetFlags(CPU.Flags.CARRY);
            cpu.registers.SP = 0xFFFC;
            cpu.registers.PC = 0x1234;
            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFE");
            assertEquals(0x1235, cpu.registers.PC, "The PC should equal 0x1235");
        }

        // op code 0xD9
        @Test
        void reti_test() {
            // TODO: don't know how to test this.
        }

        // op code 0xDA
        @Test
        void jp_c_xx_test() {
            cpu.resetFlags(CPU.Flags.CARRY);
            rom[0x100] = 0xDA; // jp c,0x8000
            rom[0x101] = 0x00;
            rom[0x102] = 0x80;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103.");

            cpu.setFlags(CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.tick();
            assertEquals(0x8000, cpu.registers.PC, "The PC should equal 0x8000.");
        }

        // op code 0xDC
        @Test
        void call_c_xx_test() {
            cpu.resetFlags(CPU.Flags.CARRY);
            rom[0x100] = 0xDC; // call z,0x1234
            rom[0x101] = 0x34;
            rom[0x102] = 0x12;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFE, cpu.registers.SP, "The SP should equal 0xFFFE");
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103");

            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);
            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC");
            assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01");
            assertEquals(0x03, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x03");
            assertEquals(0x1234, cpu.registers.PC, "The PC should equal 0x1234");
        }

        // op code 0xDE
        @Test
        void sbc_a_x_test() {
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0xDE; // sbc a,0x2A
            rom[0x103] = 0x2A;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x10, cpu.registers.A, "The A register should equal 0x10.");
            assertEquals(CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0xDE; // sbc a,0x3A
            rom[0x103] = 0x3A;

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0xDE; // sbc a,0x4F
            rom[0x103] = 0x4F;

            cpu.tick();
            cpu.tick();
            assertEquals(0xEB, cpu.registers.A, "The A register should equal 0xEB.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The SUB, HALF_CARRY and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0xDF
        @Test
        void rst_18_test() {
            rom[0x100] = 0xDF; // rst 0x18

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC.");
            assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01.");
            assertEquals(0x01, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x00.");
            assertEquals(0x18, cpu.registers.PC, "The PC should equal 0x18.");
        }
    }

    static class CPU_0xE0_0xEF {
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

        // op code 0xE0
        @Test
        void ldh_xp_a_test() {
            rom[0x100] = 0x3E; // ld a,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0xE0; // ldh 0x15,a
            rom[0x103] = 0x15;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, memory.getByteAt(0xFF15), "The value at address 0xFF10 should be 0x50.");
            assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
        }

        // op code 0xE1
        @Test
        void pop_hl_test() {
            memory.setByteAt(0xFFFD, 0xBE);
            memory.setByteAt(0xFFFC, 0xEF);
            cpu.registers.SP = 0xFFFC;

            rom[0x100] = 0xE1; // pop hl

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xBEEF, cpu.registers.getHL(), "The HL register should equal 0xBEEF.");
            assertEquals(0xFFFE, cpu.registers.SP, "The SP should equal 0xFFFE.");
            assertEquals(0x101, cpu.registers.PC, "The PC should equal 0x101");
        }

        // op code 0xE2
        @Test
        void ld_cp_a_test() {
            rom[0x100] = 0x3E; // ld a,0x69
            rom[0x101] = 0x69;
            rom[0x102] = 0x0E; // ld c,0x15
            rom[0x103] = 0x15;
            rom[0x104] = 0xE2; // ld (c),a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x69, cpu.registers.A, "The A register should be 0x69.");
            assertEquals(0x15, cpu.registers.C, "The C register should be 0x10.");
            assertEquals(0x69, memory.getByteAt(0xFF15), "The value at address 0xFF10 should be 0x69.");
            assertEquals(0x105, cpu.registers.PC, "The PC should equal 0x105.");
        }

        // op code 0xE5
        @Test
        void push_hl_test() {
            rom[0x100] = 0x21; // ld hl,0xBEEF
            rom[0x101] = 0xEF;
            rom[0x102] = 0xBE;
            rom[0x103] = 0xE5; // push hl

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC");
            assertEquals(0xBE, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0xEF");
            assertEquals(0xEF, memory.getByteAt(0xFFFC), "The value at address 0xFFFC should equal 0xBE");
            assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
        }

        // op code 0xE6
        @Test
        void and_x_test() {
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0xE6; // and 0x3F
            rom[0x103] = 0x3F;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x1A, cpu.registers.A, "The A register should equal 0x1A.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x5A
            rom[0x101] = 0x5A;
            rom[0x102] = 0xE6; // and 0x00
            rom[0x103] = 0x00;

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
        }

        // op code 0xE7
        @Test
        void rst_20_test() {
            rom[0x100] = 0xE7; // rst 0x20

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC.");
            assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01.");
            assertEquals(0x01, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x00.");
            assertEquals(0x20, cpu.registers.PC, "The PC should equal 0x20.");
        }

        // op code 0xE8
        @Test
        void add_sp_x_test() {
            cpu.registers.SP = 0xFFF0;

            rom[0x100] = 0xE8; // add sp,0x05
            rom[0x101] = 0x05;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFF5, cpu.registers.SP, "The SP should equal 0xFFF5.");
            assertEquals(0x102, cpu.registers.PC, "PC should equal 0x102.");
        }

        // op code 0xE9
        @Test
        void jp_hlp_test() {
            rom[0x100] = 0x21; // ld hl,0x2000
            rom[0x101] = 0x00;
            rom[0x102] = 0x20;
            rom[0x103] = 0xE9; // jp (hl)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x2000, cpu.registers.PC, "PC should equal 0x2000.");
        }

        // op code 0xEA
        @Test
        void ld_xxp_a_test() {
            rom[0x100] = 0x3E; // ld a,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0xEA; // ld (0xC000),a
            rom[0x103] = 0x00;
            rom[0x104] = 0xC0;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, memory.getByteAt(0xC000), "The value at address 0xC000 should equal 0x50.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0xEE
        @Test
        void xor_x_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xEE; // xor 0xFF
            rom[0x103] = 0xFF;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO, cpu.registers.F, "The ZERO flag should be set.");
            assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;

            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xEE; // xor 0x0F
            rom[0x103] = 0x0F;

            cpu.tick();
            cpu.tick();
            assertEquals(0xF0, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
        }

        // op code 0xEF
        @Test
        void rst_28_test() {
            rom[0x100] = 0xEF; // rst 0x28

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC.");
            assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01.");
            assertEquals(0x01, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x00.");
            assertEquals(0x28, cpu.registers.PC, "The PC should equal 0x28.");
        }
    }

    static class CPU_0xF0_0xFF {
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

        // op code 0xF0
        @Test
        void ldh_a_xp_test() {
            memory.setByteAt(0xFFF0, 0x50);

            rom[0x100] = 0xF0; // ld a,(x)
            rom[0x101] = 0xF0;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
            assertEquals(0x102, cpu.registers.PC, "The PC should equal 0x102.");
        }

        // op code 0xF1
        @Test
        void pop_af_test() {
            memory.setByteAt(0xFFFD, 0xBE);
            memory.setByteAt(0xFFFC, 0xEF);
            cpu.registers.SP = 0xFFFC;

            rom[0x100] = 0xF1; // pop af

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xBEE0, cpu.registers.getAF(), "The AF register should equal 0xBEE0.");
            assertEquals(0xFFFE, cpu.registers.SP, "The SP should equal 0xFFFE.");
            assertEquals(0x101, cpu.registers.PC, "The PC should equal 0x101");
        }

        // op code 0xF2
        @Test
        void ld_a_cp_test() {
            memory.setByteAt(0xFFF0, 0x50);

            rom[0x100] = 0x0E; // ld c,0xF0
            rom[0x101] = 0xF0;
            rom[0x102] = 0xF2; // ld a,(c)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103.");
        }

        // op code 0xF3
        @Test
        void di_test() {
            // TODO: disables master interrupt (sets IE to 0).
        }

        // op code 0xF5
        @Test
        void push_af_test() {
            cpu.setFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0xC0
            rom[0x101] = 0xC0;
            rom[0x102] = 0xF5; // push af

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC");
            assertEquals(0xC0, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0xC0");
            assertEquals(0xF0, memory.getByteAt(0xFFFC), "The value at address 0xFFFC should equal 0xF0");
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x104.");
        }

        // op code 0xF6
        @Test
        void or_x_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xF6; // or 0xFF
            rom[0x103] = 0xFF;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFF, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;

            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xF6; // or 0x0F
            rom[0x103] = 0x0F;

            cpu.tick();
            cpu.tick();
            assertEquals(0xFF, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
        }

        // op code 0xF7
        @Test
        void rst_30_test() {
            rom[0x100] = 0xF7; // rst 0x0F7

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC.");
            assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01.");
            assertEquals(0x01, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x00.");
            assertEquals(0x30, cpu.registers.PC, "The PC should equal 0x30.");
        }

        // op code 0xF8
        @Test
        void ld_hl_sp_x_test() {
            cpu.registers.SP = 0xEFFF;

            rom[0x100] = 0xF8; // ld hl, sp+x
            rom[0x101] = 0x01;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xF000, cpu.registers.getHL(), "The HL register should equal 0x50.");
            assertEquals(0x102, cpu.registers.PC, "The PC should equal 0x102.");
        }

        // op code 0xF9
        @Test
        void ld_sp_hl_test() {
            rom[0x100] = 0x21; // ld hl,0xFFF0
            rom[0x101] = 0xF0;
            rom[0x102] = 0xFF;
            rom[0x103] = 0xF9; // ld sp,hl

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFFF0, cpu.registers.SP, "The SP should equal 0xFFF0.");
            assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
        }

        // op code 0xFA
        @Test
        void ld_a_xxp_test() {
            memory.setByteAt(0xC000, 0x50);

            rom[0x100] = 0xFA; // ld a,(0xC000)
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103.");
        }

        // op code 0xFB
        @Test
        void ei_test() {
            // TODO: enables master interrupt (sets IE to 1).
        }

        // op code 0xFE
        @Test
        void cp_x_test() {
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0xFE; // cp 0x2F
            rom[0x103] = 0x2F;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0xFE; // cp 0x3C
            rom[0x103] = 0x3C;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "The ZERO and SUB flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            rom[0x100] = 0x3E; // ld a,0x3C
            rom[0x101] = 0x3C;
            rom[0x102] = 0xFE; // cp 0x40
            rom[0x103] = 0x40;

            cpu.tick();
            cpu.tick();
            assertEquals(CPU.Flags.SUB | CPU.Flags.CARRY, cpu.registers.F, "The SUB and CARRY flags should be set.");
            assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
        }

        // op code 0xFF
        @Test
        void rst_38_test() {
            rom[0x100] = 0xFF; // rst 0x38

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFFC, cpu.registers.SP, "The SP should equal 0xFFFC.");
            assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01.");
            assertEquals(0x01, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x00.");
            assertEquals(0x38, cpu.registers.PC, "The PC should equal 0x38.");
        }
    }
}
