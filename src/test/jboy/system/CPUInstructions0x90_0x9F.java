package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0x90_0x9F {
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

    // op code 0x90
    @Test
    void sub_b_test() {
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x06; // ld b,0x3E
        rom[0x103] = 0x3E;
        rom[0x104] = 0x90; // sub b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x06; // ld b,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0x90; // sub b

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x2F, cpu.getA(), "The A register should equal 0x2F.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x06; // ld b,0x40
        rom[0x103] = 0x40;
        rom[0x104] = 0x90; // sub b

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.getA(), "The A register should equal 0xFE.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x91
    @Test
    void sub_c_test() {
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x0E; // ld c,0x3E
        rom[0x103] = 0x3E;
        rom[0x104] = 0x91; // sub c

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x0E; // ld c,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0x91; // sub c

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x2F, cpu.getA(), "The A register should equal 0x2F.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x0E; // ld c,0x40
        rom[0x103] = 0x40;
        rom[0x104] = 0x91; // sub c

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.getA(), "The A register should equal 0xFE.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x92
    @Test
    void sub_d_test() {
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x16; // ld d,0x3E
        rom[0x103] = 0x3E;
        rom[0x104] = 0x92; // sub d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x16; // ld d,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0x92; // sub d

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x2F, cpu.getA(), "The A register should equal 0x2F.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x16; // ld d,0x40
        rom[0x103] = 0x40;
        rom[0x104] = 0x92; // sub d

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.getA(), "The A register should equal 0xFE.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x93
    @Test
    void sub_e_test() {
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x1E; // ld e,0x3E
        rom[0x103] = 0x3E;
        rom[0x104] = 0x93; // sub e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x1E; // ld e,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0x93; // sub e

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x2F, cpu.getA(), "The A register should equal 0x2F.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x1E; // ld e,0x40
        rom[0x103] = 0x40;
        rom[0x104] = 0x93; // sub e

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.getA(), "The A register should equal 0xFE.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x94
    @Test
    void sub_h_test() {
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x26; // ld h,0x3E
        rom[0x103] = 0x3E;
        rom[0x104] = 0x94; // sub h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x26; // ld h,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0x94; // sub h

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x2F, cpu.getA(), "The A register should equal 0x2F.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x26; // ld h,0x40
        rom[0x103] = 0x40;
        rom[0x104] = 0x94; // sub h

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.getA(), "The A register should equal 0xFE.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x95
    @Test
    void sub_l_test() {
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x2E; // ld l,0x3E
        rom[0x103] = 0x3E;
        rom[0x104] = 0x95; // sub l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x2E; // ld l,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0x95; // sub l

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x2F, cpu.getA(), "The A register should equal 0x2F.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x2E; // ld l,0x40
        rom[0x103] = 0x40;
        rom[0x104] = 0x95; // sub l

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.getA(), "The A register should equal 0xFE.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x96
    @Test
    void sub_hlp_test() {
        memory.setByteAt(0xC000, 0x3E);

        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x21; // ld hl,0xC000
        rom[0x103] = 0x00;
        rom[0x104] = 0xC0;
        rom[0x105] = 0x96; // sub (hl)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x106, cpu.getPC(), "PC should equal 0x106.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        memory.setByteAt(0xC000, 0x0F);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x2F, cpu.getA(), "The A register should equal 0x2F.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x106, cpu.getPC(), "PC should equal 0x106.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        memory.setByteAt(0xC000, 0x40);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.getA(), "The A register should equal 0xFE.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x106, cpu.getPC(), "PC should equal 0x106.");
    }

    // op code 0x97
    @Test
    void sub_a_test() {
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0x97; // sub a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x98
    @Test
    void sbc_a_b_test() {
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x06; // ld b,0x2A
        rom[0x103] = 0x2A;
        rom[0x104] = 0x98; // sbc a,b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.getA(), "The A register should equal 0x10.");
        assertEquals(CPU.FLAG_SUB, cpu.getF(), "The SUB flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x06; // ld b,0x3A
        rom[0x103] = 0x3A;
        rom[0x104] = 0x98; // sbc a,b

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x06; // ld b,0x4F
        rom[0x103] = 0x4F;
        rom[0x104] = 0x98; // sbc a,b

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xEB, cpu.getA(), "The A register should equal 0xEB.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The SUB, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x99
    @Test
    void sbc_a_c_test() {
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x0E; // ld c,0x2A
        rom[0x103] = 0x2A;
        rom[0x104] = 0x99; // sbc a,c

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.getA(), "The A register should equal 0x10.");
        assertEquals(CPU.FLAG_SUB, cpu.getF(), "The SUB flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x0E; // ld c,0x3A
        rom[0x103] = 0x3A;
        rom[0x104] = 0x99; // sbc a,c

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x0E; // ld c,0x4F
        rom[0x103] = 0x4F;
        rom[0x104] = 0x99; // sbc a,c

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xEB, cpu.getA(), "The A register should equal 0xEB.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The SUB, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x9A
    @Test
    void sbc_a_d_test() {
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x16; // ld d,0x2A
        rom[0x103] = 0x2A;
        rom[0x104] = 0x9A; // sbc a,d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.getA(), "The A register should equal 0x10.");
        assertEquals(CPU.FLAG_SUB, cpu.getF(), "The SUB flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x16; // ld d,0x3A
        rom[0x103] = 0x3A;
        rom[0x104] = 0x9A; // sbc a,d

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x16; // ld d,0x4F
        rom[0x103] = 0x4F;
        rom[0x104] = 0x9A; // sbc a,d

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xEB, cpu.getA(), "The A register should equal 0xEB.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The SUB, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x9B
    @Test
    void sbc_a_e_test() {
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x1E; // ld e,0x2A
        rom[0x103] = 0x2A;
        rom[0x104] = 0x9B; // sbc a,e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.getA(), "The A register should equal 0x10.");
        assertEquals(CPU.FLAG_SUB, cpu.getF(), "The SUB flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x1E; // ld e,0x3A
        rom[0x103] = 0x3A;
        rom[0x104] = 0x9B; // sbc a,e

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x1E; // ld e,0x4F
        rom[0x103] = 0x4F;
        rom[0x104] = 0x9B; // sbc a,e

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xEB, cpu.getA(), "The A register should equal 0xEB.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The SUB, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x9C
    @Test
    void sbc_a_h_test() {
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x26; // ld h,0x2A
        rom[0x103] = 0x2A;
        rom[0x104] = 0x9C; // sbc a,h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.getA(), "The A register should equal 0x10.");
        assertEquals(CPU.FLAG_SUB, cpu.getF(), "The SUB flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x26; // ld h,0x3A
        rom[0x103] = 0x3A;
        rom[0x104] = 0x9C; // sbc a,h

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x26; // ld h,0x4F
        rom[0x103] = 0x4F;
        rom[0x104] = 0x9C; // sbc a,h

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xEB, cpu.getA(), "The A register should equal 0xEB.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The SUB, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x9D
    @Test
    void sbc_a_l_test() {
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x2E; // ld l,0x2A
        rom[0x103] = 0x2A;
        rom[0x104] = 0x9D; // sbc a,l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.getA(), "The A register should equal 0x10.");
        assertEquals(CPU.FLAG_SUB, cpu.getF(), "The SUB flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x2E; // ld l,0x3A
        rom[0x103] = 0x3A;
        rom[0x104] = 0x9D; // sbc a,l

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x2E; // ld l,0x4F
        rom[0x103] = 0x4F;
        rom[0x104] = 0x9D; // sbc a,l

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xEB, cpu.getA(), "The A register should equal 0xEB.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The SUB, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0x9E
    @Test
    void sbc_a_hlp_test() {
        cpu.setFlags(CPU.FLAG_CARRY);
        memory.setByteAt(0xC000, 0x2A);

        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x21; // ld hl,0xC000
        rom[0x103] = 0x00;
        rom[0x104] = 0xC0;
        rom[0x105] = 0x9E; // sbc a,(hl)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.getA(), "The A register should equal 0x10.");
        assertEquals(CPU.FLAG_SUB, cpu.getF(), "The SUB flag should be set.");
        assertEquals(0x106, cpu.getPC(), "PC should equal 0x106.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_CARRY);
        memory.setByteAt(0xC000, 0x3A);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x106, cpu.getPC(), "PC should equal 0x106.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_CARRY);
        memory.setByteAt(0xC000, 0x4F);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xEB, cpu.getA(), "The A register should equal 0xEB.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The SUB, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x106, cpu.getPC(), "PC should equal 0x106.");
    }

    // op code 0x9F
    @Test
    void sbc_a_a_test() {
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x9F; // sbc a,a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xFF, cpu.getA(), "The A register should equal 0xFF.");
        assertEquals(CPU.FLAG_SUB, cpu.getF(), "The SUB flag should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0x9F; // sbc a,a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The SUB flag should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }
}
