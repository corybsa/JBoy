package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xCB80_0xCB8F {
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
