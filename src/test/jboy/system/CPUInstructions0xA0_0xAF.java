package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xA0_0xAF {
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

    // op code 0xA0
    @Test
    void and_b_test() {
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x06; // ld b,0x3F
        rom[0x103] = 0x3F;
        rom[0x104] = 0xA0; // and b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x1A, cpu.getA(), "The A register should equal 0x1A.");
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x06; // ld b,0x00
        rom[0x103] = 0x00;
        rom[0x104] = 0xA0; // and b

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xA1
    @Test
    void and_c_test() {
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x0E; // ld c,0x3F
        rom[0x103] = 0x3F;
        rom[0x104] = 0xA1; // and c

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x1A, cpu.getA(), "The A register should equal 0x1A.");
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x0E; // ld c,0x00
        rom[0x103] = 0x00;
        rom[0x104] = 0xA1; // and c

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xA2
    @Test
    void and_d_test() {
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x16; // ld d,0x3F
        rom[0x103] = 0x3F;
        rom[0x104] = 0xA2; // and d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x1A, cpu.getA(), "The A register should equal 0x1A.");
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x16; // ld d,0x00
        rom[0x103] = 0x00;
        rom[0x104] = 0xA2; // and d

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xA3
    @Test
    void and_e_test() {
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x1E; // ld e,0x3F
        rom[0x103] = 0x3F;
        rom[0x104] = 0xA3; // and e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x1A, cpu.getA(), "The A register should equal 0x1A.");
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x1E; // ld e,0x00
        rom[0x103] = 0x00;
        rom[0x104] = 0xA3; // and e

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xA4
    @Test
    void and_h_test() {
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x26; // ld h,0x3F
        rom[0x103] = 0x3F;
        rom[0x104] = 0xA4; // and h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x1A, cpu.getA(), "The A register should equal 0x1A.");
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x26; // ld h,0x00
        rom[0x103] = 0x00;
        rom[0x104] = 0xA4; // and h

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xA5
    @Test
    void and_l_test() {
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x2E; // ld l,0x3F
        rom[0x103] = 0x3F;
        rom[0x104] = 0xA5; // and l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x1A, cpu.getA(), "The A register should equal 0x1A.");
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x2E; // ld l,0x00
        rom[0x103] = 0x00;
        rom[0x104] = 0xA5; // and l

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xA6
    @Test
    void and_hlp_test() {
        memory.setByteAt(0xC000, 0x3F);
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0x21; // ld hl,0xC000
        rom[0x103] = 0x00;
        rom[0x104] = 0xC0;
        rom[0x104] = 0xA6; // and (hl)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x1A, cpu.getA(), "The A register should equal 0x1A.");
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        memory.setByteAt(0xC000, 0x00);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xA7
    @Test
    void and_a_test() {
        rom[0x100] = 0x3E; // ld a,0x5A
        rom[0x101] = 0x5A;
        rom[0x102] = 0xA7; // and a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x5A, cpu.getA(), "The A register should equal 0x5A.");
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xA7; // and a

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103.");
    }

    // op code 0xA8
    @Test
    void xor_b_test() {
        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x06; // ld b,0xFF
        rom[0x103] = 0xFF;
        rom[0x104] = 0xA8; // xor b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);

        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x06; // ld b,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0xA8; // xor b

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xF0, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xA9
    @Test
    void xor_c_test() {
        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x0E; // ld c,0xFF
        rom[0x103] = 0xFF;
        rom[0x104] = 0xA9; // xor c

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);

        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x0E; // ld c,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0xA9; // xor c

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xF0, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xAA
    @Test
    void xor_d_test() {
        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x16; // ld d,0xFF
        rom[0x103] = 0xFF;
        rom[0x104] = 0xAA; // xor d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);

        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x16; // ld d,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0xAA; // xor d

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xF0, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xAB
    @Test
    void xor_e_test() {
        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x1E; // ld e,0xFF
        rom[0x103] = 0xFF;
        rom[0x104] = 0xAB; // xor e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);

        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x1E; // ld e,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0xAB; // xor e

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xF0, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xAC
    @Test
    void xor_h_test() {
        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x26; // ld h,0xFF
        rom[0x103] = 0xFF;
        rom[0x104] = 0xAC; // xor h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);

        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x26; // ld h,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0xAC; // xor h

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xF0, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xAD
    @Test
    void xor_l_test() {
        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x2E; // ld l,0xFF
        rom[0x103] = 0xFF;
        rom[0x104] = 0xAD; // xor l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);

        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x2E; // ld l,0x0F
        rom[0x103] = 0x0F;
        rom[0x104] = 0xAD; // xor l

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xF0, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xAE
    @Test
    void xor_hlp_test() {
        memory.setByteAt(0xC000, 0xFF);
        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x104] = 0xAE; // xor (hl)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        memory.setByteAt(0xC000, 0x0F);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xF0, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }

    // op code 0xAF
    @Test
    void xor_a_test() {
        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x104] = 0xAF; // xor a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x105, cpu.getPC(), "The PC should equal 0x105.");
    }
}
