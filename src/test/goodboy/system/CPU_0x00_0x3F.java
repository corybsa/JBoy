package test.goodboy.system;

import goodboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPU_0x00_0x3F {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

    static class CPU_0x00_0x0F {
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

        // op code 0x00
        @Test
        void nop_test() {
            rom[0x100] = 0x00; // nop

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0x101, cpu.registers.PC, "PC should equal 0x101.");
        }

        // op code 0x01
        @Test
        void ld_bc_xx_test() {
            rom[0x100] = 0x01; // ld bc,0x895F
            rom[0x101] = 0x5F;
            rom[0x102] = 0x89;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0x895F, cpu.registers.getBC(), "The BC register should equal 0x895F.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x02
        @Test
        void ld_bcp_a_test() {
            rom[0x100] = 0x01; // ld bc,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x3E; // ld a,0x50
            rom[0x104] = 0x50;
            rom[0x105] = 0x02; // ld (bc),a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x50, memory.getByteAt(cpu.registers.getBC()), "The memory address pointed to by BC should equal 0x50.");
            assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");
        }

        // op code 0x03
        @Test
        void inc_bc_test() {
            rom[0x100] = 0x01; // ld bc,0x0001
            rom[0x101] = 0x01;
            rom[0x102] = 0x00;
            rom[0x103] = 0x03; // inc bc

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0002, cpu.registers.getBC(), "The BC register should equal 0x0002.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x101.");
        }

        // op code 0x04
        @Test
        void inc_b_test() {
            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x04; // inc b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x01, cpu.registers.B, "The B register should equal 0x01.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0x0F
            rom[0x101] = 0x0F;
            rom[0x102] = 0x04; // inc b

            cpu.tick();
            cpu.tick();
            assertEquals(0x10, cpu.registers.B, "The B register should equal 0x10.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x06; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x04; // inc b

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.B, "The B register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x05
        @Test
        void dec_b_test() {
            rom[0x100] = 0x06; // ld b,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x05; // dec b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.B, "The B register should equal 0xFE.");
            assertEquals(CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test a decrement that results in zero.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x06; // ld b,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0x05; // dec b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.B, "The B register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "ZERO and SUB flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test a half carry.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x06; // ld b,0x10
            rom[0x101] = 0x10;
            rom[0x102] = 0x05; // dec b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0F, cpu.registers.B, "The B register should equal 0x0F.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test decrementing zero.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x06; // ld b,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x05; // dec b

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFF, cpu.registers.B, "The B register should equal 0xFF.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x06
        @Test
        void ld_b_x_test() {
            rom[0x100] = 0x06; // ld b,0xF3
            rom[0x101] = 0xF3;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xF3, cpu.registers.B, "The B register should equal 0xF3");
            assertEquals(0x102, cpu.registers.PC, "PC should equal 0x102.");
        }

        // op code 0x07
        @Test
        void rlca_test() {
            rom[0x100] = 0x3E; // ld a,0x85
            rom[0x101] = 0x85;
            rom[0x102] = 0x07; // rcla

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0B, cpu.registers.A, "The A register should equal 0x0B.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x08
        @Test
        void ld_xxp_sp_test() {
            rom[0x100] = 0x31; // ld sp,0xFFF8
            rom[0x101] = 0xF8;
            rom[0x102] = 0xFF;
            rom[0x103] = 0x08; // ld (0xC000),sp
            rom[0x104] = 0x00;
            rom[0x105] = 0xC0;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xF8, memory.getByteAt(0xC000), "The memory address pointed to by 0xC000 should equal 0xF8");
            assertEquals(0xFF, memory.getByteAt(0xC001), "The memory address pointed to by 0xC001 should equal 0xFF");
            assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106");
        }

        // op code 0x09
        @Test
        void add_hl_bc_test() {
            rom[0x100] = 0x01; // ld bc,0x0605
            rom[0x101] = 0x05;
            rom[0x102] = 0x06;
            rom[0x103] = 0x21; // ld hl,0x8A23
            rom[0x104] = 0x23;
            rom[0x105] = 0x8A;
            rom[0x106] = 0x09; // add hl,bc

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x9028, cpu.registers.getHL(), "The HL register should equal 0x9028.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x107, cpu.registers.PC, "PC should equal 0x107");
        }

        // op code 0x0A
        @Test
        void ld_a_bc_test() {
            memory.setByteAt(0xC000, 0x12); // this is the value that BC will point to.

            rom[0x100] = 0x01; // ld bc,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x0A; // ld a,(bc)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x12, cpu.registers.A, "The A register should equal 0x12.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104");
        }

        // op code 0x0B
        @Test
        void dec_bc_test() {
            rom[0x100] = 0x01; // ld bc,0x0001
            rom[0x101] = 0x01;
            rom[0x102] = 0x00;
            rom[0x103] = 0x0B; // dec bc

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0000, cpu.registers.getBC(), "The BC register should equal 0x0000");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0x0C
        @Test
        void inc_c_test() {
            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x0C; // inc c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x01, cpu.registers.C, "The C register should equal 0x01.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0x0F
            rom[0x101] = 0x0F;
            rom[0x102] = 0x0C; // inc c

            cpu.tick();
            cpu.tick();
            assertEquals(0x10, cpu.registers.C, "The C register should equal 0x10.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x0E; // ld c,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x0C; // inc c

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.C, "The C register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x0D
        @Test
        void dec_c_test() {
            rom[0x100] = 0x0E; // ld c,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x0D; // dec c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.C, "The C register should equal 0xFE.");
            assertEquals(CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test a decrement that results in zero.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x0E; // ld c,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0x0D; // dec c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.C, "The C register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "ZERO and SUB flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test a half carry.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x0E; // ld c,0x10
            rom[0x101] = 0x10;
            rom[0x102] = 0x0D; // dec c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0F, cpu.registers.C, "The C register should equal 0x0F.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test decrementing zero.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x0E; // ld c,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x0D; // dec c

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFF, cpu.registers.C, "The C register should equal 0xFF.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x0E
        @Test
        void ld_c_x_test() {
            rom[0x100] = 0x0E; // ld c,0xCD
            rom[0x101] = 0xCD;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xCD, cpu.registers.C, "The C register should equal 0xCD.");
            assertEquals(0x102, cpu.registers.PC, "PC should equal 0x102.");
        }

        // op code 0x0F
        @Test
        void rrca_test() {
            rom[0x100] = 0x3E; // ld a,0x3B
            rom[0x101] = 0x3B;
            rom[0x102] = 0x0F; // rrca

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x9D, cpu.registers.A, "The A register should equal 0x9D.");
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            cpu.registers.PC = 0x100;

            rom[0x100] = 0x3E; // ld a,0xAA
            rom[0x101] = 0xAA;
            rom[0x102] = 0x0F; // rrca

            cpu.tick();
            cpu.tick();
            assertEquals(0x55, cpu.registers.A, "The A register should equal 0x55.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }
    }

    static class CPU_0x10_0x1F {
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

        // op code 0x10
        @Test
        void stop_test() {
        }

        // op code 0x11
        @Test
        void ld_de_xx_test() {
            rom[0x100] = 0x11; // ld de,0x895F
            rom[0x101] = 0x5F;
            rom[0x102] = 0x89;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0x895F, cpu.registers.getDE(), "The DE register should equal 0x895F.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x12
        @Test
        void ld_dep_a_test() {
            memory.setByteAt(0xC000, 0x00);

            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x11; // ld de,0xC000
            rom[0x103] = 0x00;
            rom[0x104] = 0xC0;
            rom[0x105] = 0x12; // ld (de),a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xFF, memory.getByteAt(cpu.registers.getDE()), "The value pointed to by DE should be 0xFF.");
            assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");
        }

        // op code 0x13
        @Test
        void inc_de_test() {
            rom[0x100] = 0x11; // ld de,0x7000
            rom[0x101] = 0x00;
            rom[0x102] = 0x70;
            rom[0x103] = 0x13; // inc de

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x7001, cpu.registers.getDE(), "The DE register should equal 0x7001.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0x14
        @Test
        void inc_d_test() {
            rom[0x100] = 0x16; // ld d,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x14; // inc d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x01, cpu.registers.D, "The D register should equal 0x01.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld d,0x0F
            rom[0x101] = 0x0F;
            rom[0x102] = 0x14; // inc d

            cpu.tick();
            cpu.tick();
            assertEquals(0x10, cpu.registers.D, "The D register should equal 0x10.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x16; // ld d,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x14; // inc d

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.D, "The D register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x15
        @Test
        void dec_d_test() {
            rom[0x100] = 0x16; // ld d,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x15; // dec d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.D, "The D register should equal 0xFE.");
            assertEquals(CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test a decrement that results in zero.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x16; // ld d,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0x15; // dec d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.D, "The D register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "ZERO and SUB flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test a half carry.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x16; // ld d,0x10
            rom[0x101] = 0x10;
            rom[0x102] = 0x15; // dec d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0F, cpu.registers.D, "The D register should equal 0x0F.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test decrementing zero.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x16; // ld d,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x15; // dec d

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFF, cpu.registers.D, "The D register should equal 0xFF.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x16
        @Test
        void ld_d_x_test() {
            rom[0x100] = 0x16; // ld d,0xCD
            rom[0x101] = 0xCD;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xCD, cpu.registers.D, "The D register should equal 0xCD.");
            assertEquals(0x102, cpu.registers.PC, "PC should equal 0x102.");
        }

        // op code 0x17
        @Test
        void rla_test() {
            rom[0x100] = 0x3E; // ld a,0x95
            rom[0x101] = 0x95;
            rom[0x102] = 0x17; // rla

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x2A, cpu.registers.A, "The A register should equal 0x2B.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x18
        @Test
        void jr_x_test() {
            rom[0x100] = 0x18; // jr 0x05;
            rom[0x101] = 0x05;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0x107, cpu.registers.PC, "PC should equal 0x107.");
        }

        // op code 0x19
        @Test
        void add_hl_de_test() {
            rom[0x100] = 0x11; // ld de,0x0605
            rom[0x101] = 0x05;
            rom[0x102] = 0x06;
            rom[0x103] = 0x21; // ld hl,0x8A23
            rom[0x104] = 0x23;
            rom[0x105] = 0x8A;
            rom[0x106] = 0x19; // add hl,de

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x9028, cpu.registers.getHL(), "The HL register should equal 0x0605.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x107, cpu.registers.PC, "PC should equal 0x107.");
        }

        // op code 0x1A
        @Test
        void ld_a_dep_test() {
            memory.setByteAt(0xC000, 0xFF); // This is the value that DE will point to.

            rom[0x100] = 0x11; // ld de,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x1A; // ld a,(de)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFF, cpu.registers.A, "The A register should equal 0xFF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0x1B
        @Test
        void dec_de_test() {
            rom[0x100] = 0x11; // ld de,0x0605
            rom[0x101] = 0x05;
            rom[0x102] = 0x06;
            rom[0x103] = 0x1B; // dec de

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0604, cpu.registers.getDE(), "The DE register should equal 0x0604.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0x1C
        @Test
        void inc_e_test() {
            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x1C; // inc e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x01, cpu.registers.E, "The E register should equal 0x01.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0x0F
            rom[0x101] = 0x0F;
            rom[0x102] = 0x1C; // inc e

            cpu.tick();
            cpu.tick();
            assertEquals(0x10, cpu.registers.E, "The E register should equal 0x10.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x1E; // ld e,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x1C; // inc e

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.E, "The E register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x1D
        @Test
        void dec_e_test() {
            rom[0x100] = 0x1E; // ld e,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x1D; // dec e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.E, "The E register should equal 0xFE.");
            assertEquals(CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test a decrement that results in zero.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x1E; // ld e,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0x1D; // dec e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.E, "The E register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "ZERO and SUB flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test a half carry.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x1E; // ld e,0x10
            rom[0x101] = 0x10;
            rom[0x102] = 0x1D; // dec e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0F, cpu.registers.E, "The E register should equal 0x0F.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test decrementing zero.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x1E; // ld e,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x1D; // dec e

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFF, cpu.registers.E, "The E register should equal 0xFF.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x1E
        @Test
        void ld_e_x_test() {
            rom[0x100] = 0x1E; // ld e,0xCD
            rom[0x101] = 0xCD;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xCD, cpu.registers.E, "The E register should equal 0xCD.");
            assertEquals(0x102, cpu.registers.PC, "PC should equal 0x102.");
        }

        // op code 0x1F
        @Test
        void rra_test() {
            rom[0x100] = 0x3E; // ld a,0x81
            rom[0x101] = 0x81;
            rom[0x102] = 0x1F; // rra

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x40, cpu.registers.A, "The A register should equal 0x40.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            cpu.setFlags(CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;

            cpu.tick();
            cpu.tick();
            assertEquals(0xC0, cpu.registers.A, "The A register should equal 0xC0.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }
    }

    static class CPU_0x20_0x2F {
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

        // op code 0x20
        @Test
        void jr_nz_x_test() {
            rom[0x100] = 0x20; // jr nz,0x05
            rom[0x101] = 0x05;

            memory.loadROM(rom);

            cpu.resetFlags(CPU.Flags.ZERO);
            cpu.tick();
            assertEquals(0x107, cpu.registers.PC, "PC should equal 0x107.");

            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.ZERO);

            cpu.tick();
            assertEquals(0x102, cpu.registers.PC, "PC should equal 0x102.");
        }

        // op code 0x21
        @Test
        void ld_hl_xx_test() {
            rom[0x100] = 0x21; // ld hl,0xDEAD
            rom[0x101] = 0xAD;
            rom[0x102] = 0xDE;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xDEAD, cpu.registers.getHL(), "The HL register should equal 0xDEAD.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x22
        @Test
        void ldi_hlp_a_test() {
            memory.setByteAt(0xC000, 0x00); // The address that HL points to.

            rom[0x100] = 0x3E; // ld a,0x56;
            rom[0x101] = 0x56;
            rom[0x102] = 0x21; // ld hl,0xC000
            rom[0x103] = 0x00;
            rom[0x104] = 0xC0;
            rom[0x105] = 0x22; // ld (hl+),a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x56, memory.getByteAt(0xC000), "The address 0xC000 should contain the value 0x56.");
            assertEquals(0xC001, cpu.registers.getHL(), "The HL register should equal 0xC001.");
            assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");
        }

        // op code 0x23
        @Test
        void inc_hl_test() {
            rom[0x100] = 0x21; // ld hl,0x8008
            rom[0x101] = 0x08;
            rom[0x102] = 0x80;
            rom[0x103] = 0x23; // inc hl

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x8009, cpu.registers.getHL(), "The HL register should equal 0x8009.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0x24
        @Test
        void inc_h_test() {
            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x24; // inc h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x01, cpu.registers.H, "The H register should equal 0x01.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0x0F
            rom[0x101] = 0x0F;
            rom[0x102] = 0x24; // inc h

            cpu.tick();
            cpu.tick();
            assertEquals(0x10, cpu.registers.H, "The H register should equal 0x10.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x26; // ld h,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x24; // inc h

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.H, "The H register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x25
        @Test
        void dec_h_test() {
            rom[0x100] = 0x26; // ld h,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x25; // dec h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.H, "The H register should equal 0xFE.");
            assertEquals(CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test a decrement that results in zero.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x26; // ld h,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0x25; // dec h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.H, "The H register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "ZERO and SUB flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test a half carry.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x26; // ld h,0x10
            rom[0x101] = 0x10;
            rom[0x102] = 0x25; // dec h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0F, cpu.registers.H, "The H register should equal 0x0F.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test decrementing zero.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x26; // ld h,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x25; // dec h

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFF, cpu.registers.H, "The H register should equal 0xFF.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x26
        @Test
        void ld_h_x_test() {
            rom[0x100] = 0x26; // ld h,0x50
            rom[0x101] = 0x50;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0x50, cpu.registers.H, "The H register should equal 0x50.");
            assertEquals(0x102, cpu.registers.PC, "PC should equal 0x102.");
        }

        // op code 0x27
        @Test
        void daa_test() {
            rom[0x100] = 0x3E; // ld a,0x45
            rom[0x101] = 0x45;
            rom[0x102] = 0xC6; // add a,0x38
            rom[0x103] = 0x38;
            rom[0x104] = 0x27; // daa

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x83, cpu.registers.A, "The A register should equal 0x25.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;

            rom[0x100] = 0x3E; // ld a,0x45
            rom[0x101] = 0x45;
            rom[0x102] = 0x06; // ld b,0x38
            rom[0x103] = 0x38;
            rom[0x104] = 0x90; // sub b
            rom[0x105] = 0x27; // daa

            cpu.tick();
            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x07, cpu.registers.A, "The A register should equal 0x13.");
            assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");
        }

        // op code 0x28
        @Test
        void jr_z_x_test() {
            rom[0x100] = 0x28; // jr z,0x05
            rom[0x101] = 0x05;

            memory.loadROM(rom);

            cpu.resetFlags(CPU.Flags.ZERO);
            cpu.tick();
            assertEquals(0x102, cpu.registers.PC, "PC should equal 0x102.");

            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.ZERO);

            cpu.tick();
            assertEquals(0x107, cpu.registers.PC, "PC should equal 0x107.");
        }

        // op code 0x29
        @Test
        void add_hl_hl_test() {
            rom[0x101] = 0x21; // ld hl,0x8A23
            rom[0x102] = 0x23;
            rom[0x103] = 0x8A;
            rom[0x104] = 0x29; // add hl,hl

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x1446, cpu.registers.getHL(), "The HL register should equal 0x0605.");
            assertEquals(CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The HALF_CARRY and CARRY flag should be set.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
        }

        // op code 0x2A
        @Test
        void ldi_a_hlp_test() {
            memory.setByteAt(0xC000, 0x56); // The address that HL points to.

            rom[0x100] = 0x21; // ld hl,0xC000;
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x2A; // ld a,(hl+)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x56, cpu.registers.A, "The A register should contain the value 0x56.");
            assertEquals(0xC001, cpu.registers.getHL(), "The HL register should equal 0xC001.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0x2B
        @Test
        void dec_hl_test() {
            rom[0x100] = 0x21; // ld hl,0x2000
            rom[0x101] = 0x00;
            rom[0x102] = 0x20;
            rom[0x103] = 0x2B; // dec hl

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x1FFF, cpu.registers.getHL(), "The HL register should equal 0x1FFF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0x2C
        @Test
        void inc_l_test() {
            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x2C; // inc l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x01, cpu.registers.L, "The L register should equal 0x01.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0x0F
            rom[0x101] = 0x0F;
            rom[0x102] = 0x2C; // inc l

            cpu.tick();
            cpu.tick();
            assertEquals(0x10, cpu.registers.L, "The L register should equal 0x10.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x2E; // ld l,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x2C; // inc l

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.L, "The L register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x2D
        @Test
        void dec_l_test() {
            rom[0x100] = 0x2E; // ld l,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x2D; // dec l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.L, "The L register should equal 0xFE.");
            assertEquals(CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test a decrement that results in zero.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x2E; // ld l,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0x2D; // dec l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.L, "The L register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "ZERO and SUB flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test a half carry.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x2E; // ld l,0x10
            rom[0x101] = 0x10;
            rom[0x102] = 0x2D; // dec l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0F, cpu.registers.L, "The L register should equal 0x0F.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test decrementing zero.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x2E; // ld l,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x2D; // dec l

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFF, cpu.registers.L, "The L register should equal 0xFF.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x2E
        @Test
        void ld_l_x_test() {
            rom[0x100] = 0x2E; // ld l,0x01
            rom[0x101] = 0x01;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0x01, cpu.registers.L, "The L register should equal 0x01.");
            assertEquals(0x102, cpu.registers.PC, "The PC should equal 0x102.");
        }

        // op code 0x2F
        @Test
        void cpl_test() {
            rom[0x100] = 0x3E; // ld a,0x35
            rom[0x101] = 0x35;
            rom[0x102] = 0x2F; // cpl

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xCA, cpu.registers.A, "The A register should equal 0xCA.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103.");
        }
    }

    static class CPU_0x30_0x3F {
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

        // op code 0x30
        @Test
        void jr_nc_x_test() {
            rom[0x100] = 0x30; // jr nc,0x05
            rom[0x101] = 0x05;

            memory.loadROM(rom);

            cpu.resetFlags(CPU.Flags.CARRY);
            cpu.tick();
            assertEquals(0x107, cpu.registers.PC, "PC should equal 0x107.");

            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);

            cpu.tick();
            assertEquals(0x102, cpu.registers.PC, "PC should equal 0x102.");
        }

        // op code 0x31
        @Test
        void ld_sp_xx_test() {
            rom[0x100] = 0x31; // ld sp,0xFFF0
            rom[0x101] = 0xF0;
            rom[0x102] = 0xFF;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xFFF0, cpu.registers.SP, "The SP register should equal 0xC0DE.");
            assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103.");
        }

        // op code 0x32
        @Test
        void ldd_hlp_a_test() {
            memory.setByteAt(0xC001, 0x00); // The memory address that HL points to.

            rom[0x100] = 0x3E; // ld a,0x50
            rom[0x101] = 0x50;
            rom[0x102] = 0x21; // ld hl,0xC001
            rom[0x103] = 0x01;
            rom[0x104] = 0xC0;
            rom[0x105] = 0x32; // ld (hl-),a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x50, memory.getByteAt(0xC001), "The address 0xC001 should contain the value 0x50.");
            assertEquals(0xC000, cpu.registers.getHL(), "The HL register should equal 0xC000.");
            assertEquals(0x106, cpu.registers.PC, "The PC should equal 0x106.");
        }

        // op code 0x33
        @Test
        void inc_sp_test() {
            rom[0x100] = 0x31; // ld sp,0x0001
            rom[0x101] = 0x01;
            rom[0x102] = 0x00;
            rom[0x103] = 0x33; // inc sp

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0002, cpu.registers.SP, "The SP register should equal 0x0002.");
            assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
        }

        // op code 0x34
        @Test
        void inc_hlp_test() {
            memory.setByteAt(0xC000, 0x00); // The value HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x34; // inc (hl)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x01, memory.getByteAt(0xC000), "The address should contain 0x01.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0x35
        @Test
        void dec_hlp_test() {
            memory.setByteAt(0xC000, 0x01); // The value HL will point to.

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x35; // dec (hl)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, memory.getByteAt(0xC000), "The address should contain 0x00.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0x36
        @Test
        void ld_hlp_x_test() {
            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x36; // ld (hl),0x50
            rom[0x104] = 0x50;

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x50, memory.getByteAt(0xC000), "The address should contain 0x50.");
            assertEquals(0x105, cpu.registers.PC, "PC should equal 0x106.");
        }

        // op code 0x37
        @Test
        void scf_test() {
            rom[0x100] = 0x37; // scf

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
        }

        // op code 0x38
        @Test
        void jr_c_x_test() {
            rom[0x100] = 0x38; // jr c,0x05
            rom[0x101] = 0x05;

            memory.loadROM(rom);

            cpu.resetFlags(CPU.Flags.CARRY);
            cpu.tick();
            assertEquals(0x102, cpu.registers.PC, "PC should equal 0x102.");

            cpu.registers.PC = 0x100;
            cpu.setFlags(CPU.Flags.CARRY);

            cpu.tick();
            assertEquals(0x107, cpu.registers.PC, "PC should equal 0x107.");
        }

        // op code 0x39
        @Test
        void add_hl_sp_test() {
            rom[0x100] = 0x31; // ld sp,0x0605
            rom[0x101] = 0x05;
            rom[0x102] = 0x06;
            rom[0x103] = 0x21; // ld hl,0x8A23
            rom[0x104] = 0x23;
            rom[0x105] = 0x8A;
            rom[0x106] = 0x39; // add hl,sp

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0x9028, cpu.registers.getHL(), "The HL register should equal 0x9028.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x107, cpu.registers.PC, "PC should equal 0x107.");

            cpu.registers.PC = 0x100;
            rom[0x100] = 0x31; // ld sp,0xFFFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0xFF;
            rom[0x103] = 0x21; // ld hl,0xFFFF
            rom[0x104] = 0xFF;
            rom[0x105] = 0xFF;
            rom[0x106] = 0x39; // add hl,sp

            cpu.tick();
            cpu.tick();
            cpu.tick();
            assertEquals(0xFFFE, cpu.registers.getHL(), "The HL register should equal 0x0C0A.");
            assertEquals(CPU.Flags.HALF | CPU.Flags.CARRY, cpu.registers.F, "The HALF_CARRY and CARRY flag should be set.");
            assertEquals(0x107, cpu.registers.PC, "PC should equal 0x107.");
        }

        // op code 0x3A
        @Test
        void ldd_a_hlp_test() {
            memory.setByteAt(0xC000, 0x3C);

            rom[0x100] = 0x21; // ld hl,0xC000
            rom[0x101] = 0x00;
            rom[0x102] = 0xC0;
            rom[0x103] = 0x3A; // ld a,(hl-)

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x3C, cpu.registers.A, "The A register should equal 0x3C.");
            assertEquals(0xBFFF, cpu.registers.getHL(), "The HL register should equal 0xBFFF.");
            assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
        }

        // op code 0x3B
        @Test
        void dec_sp_test() {
            rom[0x100] = 0x31; // ld sp,0x0001
            rom[0x101] = 0x01;
            rom[0x102] = 0x00;
            rom[0x103] = 0x3B; // dec sp

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0000, cpu.registers.SP, "The SP register should equal 0x0000.");
            assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
        }

        // op code 0x3C
        @Test
        void inc_a_test() {
            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x3C; // inc a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x01, cpu.registers.A, "The A register should equal 0x01.");
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0x0F
            rom[0x101] = 0x0F;
            rom[0x102] = 0x3C; // inc a

            cpu.tick();
            cpu.tick();
            assertEquals(0x10, cpu.registers.A, "The A register should equal 0x10.");
            assertEquals(CPU.Flags.HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x3C; // inc a

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x3D
        @Test
        void dec_a_test() {
            rom[0x100] = 0x3E; // ld a,0xFF
            rom[0x101] = 0xFF;
            rom[0x102] = 0x3D; // dec a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFE, cpu.registers.A, "The A register should equal 0xFE.");
            assertEquals(CPU.Flags.SUB, cpu.registers.F, "The SUB flag should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test a decrement that results in zero.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x01
            rom[0x101] = 0x01;
            rom[0x102] = 0x3D; // dec a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
            assertEquals(CPU.Flags.ZERO | CPU.Flags.SUB, cpu.registers.F, "ZERO and SUB flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test a half carry.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x10
            rom[0x101] = 0x10;
            rom[0x102] = 0x3D; // dec a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0x0F, cpu.registers.A, "The A register should equal 0x0F.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

            // Test decrementing zero.
            cpu.registers.PC = 0x100;
            cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);
            rom[0x100] = 0x3E; // ld a,0x00
            rom[0x101] = 0x00;
            rom[0x102] = 0x3D; // dec a

            memory.loadROM(rom);

            cpu.tick();
            cpu.tick();
            assertEquals(0xFF, cpu.registers.A, "The A register should equal 0xFF.");
            assertEquals(CPU.Flags.SUB | CPU.Flags.HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
            assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
        }

        // op code 0x3E
        @Test
        void ld_a_x_test() {
            rom[0x100] = 0x3E; // ld a,0xA9
            rom[0x101] = 0xA9;

            memory.loadROM(rom);

            cpu.tick();
            assertEquals(0xA9, cpu.registers.A, "The A register should equal 0xA9");
            assertEquals(0x102, cpu.registers.PC, "PC should equal 0x102");
        }

        // op code 0x3F
        @Test
        void ccf_test() {
            rom[0x100] = 0x3F; // ccf

            memory.loadROM(rom);

            cpu.resetFlags(CPU.Flags.CARRY);
            cpu.tick();
            assertEquals(CPU.Flags.CARRY, cpu.registers.F, "The CARRY flag should be set.");
            assertEquals(0x101, cpu.registers.PC, "The PC should equal 0x101.");

            cpu.setFlags(CPU.Flags.CARRY);
            cpu.registers.PC = 0x100;
            cpu.tick();
            assertEquals(0x00, cpu.registers.F, "No flags should be set.");
            assertEquals(0x101, cpu.registers.PC, "The PC should equal 0x101.");
        }
    }
}
