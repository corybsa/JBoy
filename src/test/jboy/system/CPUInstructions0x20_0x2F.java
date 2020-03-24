package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0x20_0x2F {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

    @BeforeAll
    static void testBeforeAll() {
        memory = new Memory();
        Display display = new Display(memory);
        GPU gpu = new GPU(memory, display);
        Timers timers = new Timers(memory);

        display.setDrawFunction((tiles) -> null);
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
        assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");

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
        assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");
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
