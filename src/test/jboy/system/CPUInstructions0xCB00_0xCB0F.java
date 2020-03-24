package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xCB00_0xCB0F {
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
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
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
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // rlc b
        rom[0x103] = 0x00;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.B, "The B register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.registers.F, "The ZERO flag should be set.");
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
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x0E; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // rlc c
        rom[0x103] = 0x01;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.C, "The C register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.registers.F, "The ZERO flag should be set.");
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
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x16; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // rlc c
        rom[0x103] = 0x02;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.D, "The D register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.registers.F, "The ZERO flag should be set.");
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
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // rlc e
        rom[0x103] = 0x03;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.E, "The E register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.registers.F, "The ZERO flag should be set.");
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
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x26; // ld h,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // rlc h
        rom[0x103] = 0x04;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.H, "The H register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.registers.F, "The ZERO flag should be set.");
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
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x2E; // ld l,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // rlc l
        rom[0x103] = 0x05;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.L, "The L register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.registers.F, "The ZERO flag should be set.");
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
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
        assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should be 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.registers.F, "The ZERO flag should be set.");
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
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // rlc a
        rom[0x103] = 0x07;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.registers.F, "The ZERO flag should be set.");
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
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // rrc b
        rom[0x103] = 0x08;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.B, "The B register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.registers.F, "The ZERO flag should be set.");
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
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x0E; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // rrc c
        rom[0x103] = 0x09;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.C, "The C register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.registers.F, "The ZERO flag should be set.");
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
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x16; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // rrc c
        rom[0x103] = 0x0A;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.D, "The D register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.registers.F, "The ZERO flag should be set.");
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
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // rrc e
        rom[0x103] = 0x0B;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.E, "The E register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.registers.F, "The ZERO flag should be set.");
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
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x26; // ld h,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // rrc h
        rom[0x103] = 0x0C;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.H, "The H register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.registers.F, "The ZERO flag should be set.");
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
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x2E; // ld l,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // rrc l
        rom[0x103] = 0x0D;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.L, "The L register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.registers.F, "The ZERO flag should be set.");
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
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
        assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should be 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.registers.F, "The ZERO flag should be set.");
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
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // rrc a
        rom[0x103] = 0x0F;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.registers.F, "The ZERO flag should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }
}
