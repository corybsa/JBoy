package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xCB20_0xCB2F {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

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

    // op code 0xCB20
    @Test
    void sla_b_test() {
        rom[0x100] = 0x06; // ld b,0x80
        rom[0x101] = 0x80;
        rom[0x102] = 0xCB; // sla b
        rom[0x103] = 0x20;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.B, "The B register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x06; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // rl b
        rom[0x103] = 0x20;

        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.registers.B, "The B register should equal 0xFE.");
        assertEquals(CPU.Flags.CARRY, cpu.registers.F, "No flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCB21
    @Test
    void sla_c_test() {
        rom[0x100] = 0x0E; // ld c,0x80
        rom[0x101] = 0x80;
        rom[0x102] = 0xCB; // sla c
        rom[0x103] = 0x21;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.C, "The C register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x0E; // ld c,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // sla c
        rom[0x103] = 0x21;

        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.registers.C, "The C register should equal 0xFE.");
        assertEquals(CPU.Flags.CARRY, cpu.registers.F, "No flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCB22
    @Test
    void sla_d_test() {
        rom[0x100] = 0x16; // ld d,0x80
        rom[0x101] = 0x80;
        rom[0x102] = 0xCB; // sla d
        rom[0x103] = 0x22;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.D, "The D register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x16; // ld d,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // sla d
        rom[0x103] = 0x22;

        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.registers.D, "The D register should equal 0xFE.");
        assertEquals(CPU.Flags.CARRY, cpu.registers.F, "No flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCB23
    @Test
    void sla_e_test() {
        rom[0x100] = 0x1E; // ld e,0x80
        rom[0x101] = 0x80;
        rom[0x102] = 0xCB; // sla e
        rom[0x103] = 0x23;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.E, "The E register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x1E; // ld e,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // sla e
        rom[0x103] = 0x23;

        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.registers.E, "The E register should equal 0xFE.");
        assertEquals(CPU.Flags.CARRY, cpu.registers.F, "No flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCB24
    @Test
    void sla_h_test() {
        rom[0x100] = 0x26; // ld h,0x80
        rom[0x101] = 0x80;
        rom[0x102] = 0xCB; // sla h
        rom[0x103] = 0x24;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.H, "The H register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x26; // ld h,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // sla h
        rom[0x103] = 0x24;

        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.registers.H, "The H register should equal 0xFE.");
        assertEquals(CPU.Flags.CARRY, cpu.registers.F, "No flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCB25
    @Test
    void sla_l_test() {
        rom[0x100] = 0x2E; // ld l,0x80
        rom[0x101] = 0x80;
        rom[0x102] = 0xCB; // sla l
        rom[0x103] = 0x25;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.L, "The L register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x2E; // ld l,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // sla l
        rom[0x103] = 0x25;

        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.registers.L, "The L register should equal 0xFE.");
        assertEquals(CPU.Flags.CARRY, cpu.registers.F, "No flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCB26
    @Test
    void sla_hlp_test() {
        memory.setByteAt(0xC000, 0x80); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // sla (hl)
        rom[0x104] = 0x26;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
        assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should equal 0xFE.");
        assertEquals(CPU.Flags.CARRY, cpu.registers.F, "No flags should be set.");
        assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
    }

    // op code 0xCB27
    @Test
    void sla_a_test() {
        rom[0x100] = 0x3E; // ld a,0x80
        rom[0x101] = 0x80;
        rom[0x102] = 0xCB; // sla a
        rom[0x103] = 0x27;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // sla a
        rom[0x103] = 0x27;

        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.registers.A, "The A register should equal 0xFE.");
        assertEquals(CPU.Flags.CARRY, cpu.registers.F, "No flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCB28
    @Test
    void sra_b_test() {
        rom[0x100] = 0x06; // ld b,0x8A
        rom[0x101] = 0x8A;
        rom[0x102] = 0xCB; // sra b
        rom[0x103] = 0x28;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xC5, cpu.registers.B, "The B register should equal 0xC5.");
        assertEquals(0x00, cpu.registers.F, "No flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x06; // ld b,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // sra b
        rom[0x103] = 0x28;

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.B, "The B register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCB29
    @Test
    void sra_c_test() {
        rom[0x100] = 0x0E; // ld c,0x8A
        rom[0x101] = 0x8A;
        rom[0x102] = 0xCB; // sra c
        rom[0x103] = 0x29;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xC5, cpu.registers.C, "The C register should equal 0xC5.");
        assertEquals(0x00, cpu.registers.F, "No flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x0E; // ld c,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // sra c
        rom[0x103] = 0x29;

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.C, "The C register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCB2A
    @Test
    void sra_d_test() {
        rom[0x100] = 0x16; // ld d,0x8A
        rom[0x101] = 0x8A;
        rom[0x102] = 0xCB; // sra d
        rom[0x103] = 0x2A;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xC5, cpu.registers.D, "The D register should equal 0xC5.");
        assertEquals(0x00, cpu.registers.F, "No flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x16; // ld d,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // sra d
        rom[0x103] = 0x2A;

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.D, "The D register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCB2B
    @Test
    void sra_e_test() {
        rom[0x100] = 0x1E; // ld e,0x8A
        rom[0x101] = 0x8A;
        rom[0x102] = 0xCB; // sra e
        rom[0x103] = 0x2B;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xC5, cpu.registers.E, "The E register should equal 0xC5.");
        assertEquals(0x00, cpu.registers.F, "No flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x1E; // ld e,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // sra e
        rom[0x103] = 0x2B;

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.E, "The E register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCB2C
    @Test
    void sra_h_test() {
        rom[0x100] = 0x26; // ld h,0x8A
        rom[0x101] = 0x8A;
        rom[0x102] = 0xCB; // sra h
        rom[0x103] = 0x2C;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xC5, cpu.registers.H, "The H register should equal 0xC5.");
        assertEquals(0x00, cpu.registers.F, "No flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x26; // ld h,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // sra h
        rom[0x103] = 0x2C;

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.H, "The H register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCB2D
    @Test
    void sra_l_test() {
        rom[0x100] = 0x2E; // ld l,0x8A
        rom[0x101] = 0x8A;
        rom[0x102] = 0xCB; // sra l
        rom[0x103] = 0x2D;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xC5, cpu.registers.L, "The L register should equal 0xC5.");
        assertEquals(0x00, cpu.registers.F, "No flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x2E; // ld l,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // sra l
        rom[0x103] = 0x2D;

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.L, "The L register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0xCB2E
    @Test
    void sra_hlp_test() {
        memory.setByteAt(0xC000, 0x8A); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // sra (hl)
        rom[0x104] = 0x2E;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xC5, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should equal 0xC5.");
        assertEquals(0x00, cpu.registers.F, "No flags should be set.");
        assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        memory.setByteAt(0xC000, 0x01); // This is the value that HL will point to.

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, memory.getByteAt(cpu.registers.getHL()), "The value pointed to by HL should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
        assertEquals(0x105, cpu.registers.PC, "PC should equal 0x105.");
    }

    // op code 0xCB2F
    @Test
    void sra_a_test() {
        rom[0x100] = 0x3E; // ld a,0x8A
        rom[0x101] = 0x8A;
        rom[0x102] = 0xCB; // sra a
        rom[0x103] = 0x2F;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xC5, cpu.registers.A, "The A register should equal 0xC5.");
        assertEquals(0x00, cpu.registers.F, "No flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.Flags.ZERO | CPU.Flags.SUB | CPU.Flags.HALF | CPU.Flags.CARRY);

        rom[0x100] = 0x3E; // ld a,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // sra a
        rom[0x103] = 0x2F;

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
        assertEquals(CPU.Flags.ZERO | CPU.Flags.CARRY, cpu.registers.F, "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }
}
