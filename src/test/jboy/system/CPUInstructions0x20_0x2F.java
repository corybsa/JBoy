package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
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
        cpu = new CPU(memory);
    }

    @BeforeEach
    void setUp() {
        cpu.setPC(0x100);
        rom = new int[0x7FFF];
    }

    @AfterEach
    void tearDown() {
        rom = null;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
    }

    // op code 0x20
    @Test
    void jr_nz_x_test() {
        rom[0x100] = 0x20; // jr nz,0x05
        rom[0x101] = 0x05;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x106, cpu.getPC(), "PC should equal 0x106.");

        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_ZERO);

        cpu.tick();
        assertEquals(0x102, cpu.getPC(), "PC should equal 0x102.");
    }

    // op code 0x21
    @Test
    void ld_hl_xx_test() {
        rom[0x100] = 0x21; // ld hl,0xDEAD
        rom[0x101] = 0xAD;
        rom[0x102] = 0xDE;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xDEAD, cpu.getHL(), "The HL register should equal 0xDEAD.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x10#.");
    }

    // op code 0x22
    @Test
    void ldi_hlp_a_test() {
        rom[0xC000] = 0x00; // The address that HL points to.

        rom[0x100] = 0x3E; // ld a,0x56;
        rom[0x101] = 0x56;
        rom[0x102] = 0x22; // ld (hl+),a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x56, memory.getByteAt(0xC000), "The address 0xC000 should contain the value 0x56.");
        assertEquals(0xC001, cpu.getHL(), "The HL register should equal 0xC001.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
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
        assertEquals(0x8009, cpu.getHL(), "The HL register should equal 0x8009.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
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
        assertEquals(0x01, cpu.getH(), "The H register should equal 0x01.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x26; // ld h,0x0F
        rom[0x101] = 0x0F;
        rom[0x102] = 0x24; // inc h

        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.getH(), "The H register should equal 0x10.");
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x26; // ld h,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x24; // inc h

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getH(), "The H register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
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
        assertEquals(0xFE, cpu.getH(), "The H register should equal 0xFE.");
        assertEquals(CPU.FLAG_SUB, cpu.getF(), "The SUB flag should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");

        // Test a decrement that results in zero.
        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        rom[0x100] = 0x26; // ld h,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0x25; // dec h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getH(), "The H register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "ZERO and SUB flags should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");

        // Test a half carry.
        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        rom[0x100] = 0x26; // ld h,0x10
        rom[0x101] = 0x10;
        rom[0x102] = 0x25; // dec h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x0F, cpu.getH(), "The H register should equal 0x0F.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");

        // Test decrementing zero.
        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        rom[0x100] = 0x26; // ld h,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x25; // dec h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFF, cpu.getH(), "The H register should equal 0xFF.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x26
    @Test
    void ld_h_x_test() {
        rom[0x100] = 0x26; // ld h,0x50
        rom[0x101] = 0x50;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x50, cpu.getH(), "The H register should equal 0x50.");
        assertEquals(0x102, cpu.getPC(), "PC should equal 0x102.");
    }

    // op code 0x27
    @Test
    void daa_test() {
        // ?????????????????????????????????????????????????????
        // TODO: dunno how to test this...
        /*rom[0x100] = 0x3E; // ld a,0x45
        rom[0x101] = 0x45;
        rom[0x102] = 0xC6; // add a,0x38
        rom[0x103] = 0x38;
        rom[0x104] = 0x27; // daa

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x83, cpu.getA(), "The A register should equal 0x02.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);

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
        assertEquals(0x45, cpu.getA(), "The A register should equal 0x45.");
        assertEquals(0x106, cpu.getPC(), "PC should equal 0x106.");*/
    }

    // op code 0x28
    @Test
    void jr_z_x_test() {
        rom[0x100] = 0x28; // jr z,0x05
        rom[0x101] = 0x05;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x102, cpu.getPC(), "PC should equal 0x102.");

        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_ZERO);

        cpu.tick();
        assertEquals(0x106, cpu.getPC(), "PC should equal 0x106.");
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
        assertEquals(0x1446, cpu.getHL(), "The HL register should equal 0x0605.");
        assertEquals(CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The HALF_CARRY and CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x2A
    @Test
    void ldi_a_hlp_test() {
        rom[0xC000] = 0x56; // The address that HL points to.

        rom[0x100] = 0x21; // ld hl,0xC000;
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x2A; // ld a,(hl+)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x56, cpu.getA(), "The A register should contain the value 0x56.");
        assertEquals(0xC001, cpu.getHL(), "The HL register should equal 0xC001.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0x2B
    @Test
    void dec_hl_test() {
        rom[0x100] = 0x21; // ld hl,0x20
        rom[0x102] = 0x20;
        rom[0x103] = 0x2B; // dec hl

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x21, cpu.getHL(), "The HL register should equal 0x21.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
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
        assertEquals(0x01, cpu.getL(), "The L register should equal 0x01.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x2E; // ld l,0x0F
        rom[0x101] = 0x0F;
        rom[0x102] = 0x2C; // inc l

        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.getL(), "The L register should equal 0x10.");
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x2E; // ld l,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x2C; // inc l

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getL(), "The L register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
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
        assertEquals(0xFE, cpu.getL(), "The L register should equal 0xFE.");
        assertEquals(CPU.FLAG_SUB, cpu.getF(), "The SUB flag should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");

        // Test a decrement that results in zero.
        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        rom[0x100] = 0x2E; // ld l,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0x2D; // dec l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getL(), "The L register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "ZERO and SUB flags should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");

        // Test a half carry.
        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        rom[0x100] = 0x2E; // ld l,0x10
        rom[0x101] = 0x10;
        rom[0x102] = 0x2D; // dec l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x0F, cpu.getL(), "The L register should equal 0x0F.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");

        // Test decrementing zero.
        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        rom[0x100] = 0x2E; // ld l,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x2D; // dec l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFF, cpu.getL(), "The L register should equal 0xFF.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x2E
    @Test
    void ld_l_x_test() {
        rom[0x100] = 0x2E; // ld l,0x01
        rom[0x101] = 0x01;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x01, cpu.getL(), "The L register should equal 0x01.");
        assertEquals(0x102, cpu.getPC(), "The PC should equal 0x102.");
    }

    // op code 0x2F
    @Test
    void cpl_test() {
        rom[0x100] = 0x00; // ls a,0x35
        rom[0x101] = 0x35;
        rom[0x102] = 0x2F; // cpl

        cpu.tick();
        cpu.tick();
        assertEquals(0xCA, cpu.getA(), "The A register should equal 0xCA.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103.");
    }
}
