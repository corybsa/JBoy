package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xCB70_0xCB7F {
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
