package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xB0_0xBF {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

    @BeforeAll
    static void testBeforeAll() {
        memory = new Memory();
        cpu = new CPU(memory, null);
    }

    @BeforeEach
    void setUp() {
        cpu.setPC(0x100);
        cpu.setSP(0xFFFE);
        rom = new int[0x7FFF];
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
    }

    @AfterEach
    void tearDown() {
        rom = null;
    }

    // op code 0xB0
    @Test
    void or_b_test() {
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x06; // ld b,0x00
        rom[0x103] = 0x00;
        rom[0x104] = 0xB0; // or b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x06; // ld b,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0xB0; // or b

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x5F, cpu.getA(), "The A register should equal 0x5F.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xB1
    @Test
    void or_c_test() {
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x0E; // ld c,0x00
        rom[0x103] = 0x00;
        rom[0x104] = 0xB1; // or c

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x0E; // ld c,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0xB1; // or c

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x5F, cpu.getA(), "The A register should equal 0x5F.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xB2
    @Test
    void or_d_test() {
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x16; // ld d,0x00
        rom[0x103] = 0x00;
        rom[0x104] = 0xB2; // or d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x16; // ld d,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0xB2; // or d

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x5F, cpu.getA(), "The A register should equal 0x5F.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xB3
    @Test
    void or_e_test() {
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x1E; // ld e,0x00
        rom[0x103] = 0x00;
        rom[0x104] = 0xB3; // or e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x1E; // ld e,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0xB3; // or e

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x5F, cpu.getA(), "The A register should equal 0x5F.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xB4
    @Test
    void or_h_test() {
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x26; // ld h,0x00
        rom[0x103] = 0x00;
        rom[0x104] = 0xB4; // or h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x26; // ld h,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0xB4; // or h

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x5F, cpu.getA(), "The A register should equal 0x5F.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xB5
    @Test
    void or_l_test() {
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x2E; // ld l,0x00
        rom[0x103] = 0x00;
        rom[0x104] = 0xB5; // or l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x2E; // ld l,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0xB5; // or l

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x5F, cpu.getA(), "The A register should equal 0x5F.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xB6
    @Test
    void or_hlp_test() {
        memory.setByteAt(0xC000, 0x00);

        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x21; // ld hl,0xC000
        rom[0x103] = 0x00;
        rom[0x104] = 0xC0;
        rom[0x105] = 0xB6; // or (hl)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x5A, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(0x00, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x106, cpu.getPC(), "PC should equal 0x106.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        memory.setByteAt(0xC000, 0x0F);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x5F, cpu.getA(), "The A register should equal 0x5F.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x106, cpu.getPC(), "PC should equal 0x106.");
    }

    // op code 0xB7
    @Test
    void or_a_test() {
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0xB7; // or a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x5A, cpu.getA(), "The A register should equal 0x5A.");
        assertEquals(0x00, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0xB8
    @Test
    void cp_b_test() {
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x06; // ld b,0x2F
        rom[0x103] = 0x2F;
        rom[0x104] = 0xB8; // cp b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x06; // ld b,0x3C
        rom[0x103] = 0x3C;
        rom[0x104] = 0xB8; // cp b

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x06; // ld b,0x40
        rom[0x103] = 0x40;
        rom[0x104] = 0xB8; // cp b

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_CARRY, cpu.getF(), "The SUB and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xB9
    @Test
    void cp_c_test() {
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x0E; // ld c,0x2F
        rom[0x103] = 0x2F;
        rom[0x104] = 0xB9; // cp c

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x0E; // ld c,0x3C
        rom[0x103] = 0x3C;
        rom[0x104] = 0xB9; // cp c

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x0E; // ld c,0x40
        rom[0x103] = 0x40;
        rom[0x104] = 0xB9; // cp c

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_CARRY, cpu.getF(), "The SUB and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xBA
    @Test
    void cp_d_test() {
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x16; // ld d,0x2F
        rom[0x103] = 0x2F;
        rom[0x104] = 0xBA; // cp d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x16; // ld d,0x3C
        rom[0x103] = 0x3C;
        rom[0x104] = 0xBA; // cp d

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x16; // ld d,0x40
        rom[0x103] = 0x40;
        rom[0x104] = 0xBA; // cp d

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_CARRY, cpu.getF(), "The SUB and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xBB
    @Test
    void cp_e_test() {
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x1E; // ld e,0x2F
        rom[0x103] = 0x2F;
        rom[0x104] = 0xBB; // cp e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x1E; // ld e,0x3C
        rom[0x103] = 0x3C;
        rom[0x104] = 0xBB; // cp e

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x1E; // ld e,0x40
        rom[0x103] = 0x40;
        rom[0x104] = 0xBB; // cp e

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_CARRY, cpu.getF(), "The SUB and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xBC
    @Test
    void cp_h_test() {
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x26; // ld h,0x2F
        rom[0x103] = 0x2F;
        rom[0x104] = 0xBC; // cp h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x26; // ld h,0x3C
        rom[0x103] = 0x3C;
        rom[0x104] = 0xBC; // cp h

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x26; // ld h,0x40
        rom[0x103] = 0x40;
        rom[0x104] = 0xBC; // cp h

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_CARRY, cpu.getF(), "The SUB and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xBD
    @Test
    void cp_l_test() {
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x2E; // ld l,0x2F
        rom[0x103] = 0x2F;
        rom[0x104] = 0xBD; // cp l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x2E; // ld l,0x3C
        rom[0x103] = 0x3C;
        rom[0x104] = 0xBD; // cp l

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x2E; // ld l,0x40
        rom[0x103] = 0x40;
        rom[0x104] = 0xBD; // cp l

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_CARRY, cpu.getF(), "The SUB and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xBE
    @Test
    void cp_hlp_test() {
        memory.setByteAt(0xC000, 0x2F);

        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x21; // ld hl,0xC000
        rom[0x103] = 0x00;
        rom[0x104] = 0xC0;
        rom[0x105] = 0xBE; // cp (hl)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x106, cpu.getPC(), "The PC should equal 0x106.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        memory.setByteAt(0xC000, 0x3C);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x106, cpu.getPC(), "The PC should equal 0x106.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        memory.setByteAt(0xC000, 0x40);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_CARRY, cpu.getF(), "The SUB and CARRY flags should be set.");
        assertEquals(0x106, cpu.getPC(), "The PC should equal 0x106.");
    }

    // op code 0xBF
    @Test
    void cp_a_test() {
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x06; // ld b,0x2F
        rom[0x103] = 0x2F;
        rom[0x104] = 0xB8; // cp b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x06; // ld b,0x3C
        rom[0x103] = 0x3C;
        rom[0x104] = 0xB8; // cp b

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3C
        rom[0x101] = 0x3C;
        rom[0x102] = 0x06; // ld b,0x40
        rom[0x103] = 0x40;
        rom[0x104] = 0xB8; // cp b

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_CARRY, cpu.getF(), "The SUB and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }
}
