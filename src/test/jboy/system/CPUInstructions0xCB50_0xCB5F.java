package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xCB50_0xCB5F {
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

    // op code 0xCB50
    @Test
    void bit_2_b_test() {
        rom[0x100] = 0x06; // ld b,0x04
        rom[0x101] = 0x04;
        rom[0x102] = 0xCB; // bit 2,b
        rom[0x103] = 0x50;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 2,b
        rom[0x103] = 0x50;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB51
    @Test
    void bit_2_c_test() {
        rom[0x100] = 0x0E; // ld c,0x04
        rom[0x101] = 0x04;
        rom[0x102] = 0xCB; // bit 2,c
        rom[0x103] = 0x51;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x0E; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 2,c
        rom[0x103] = 0x51;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB52
    @Test
    void bit_2_d_test() {
        rom[0x100] = 0x16; // ld d,0x04
        rom[0x101] = 0x04;
        rom[0x102] = 0xCB; // bit 2,d
        rom[0x103] = 0x52;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x16; // ld d,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 2,d
        rom[0x103] = 0x52;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB53
    @Test
    void bit_2_e_test() {
        rom[0x100] = 0x1E; // ld e,0x04
        rom[0x101] = 0x04;
        rom[0x102] = 0xCB; // bit 2,e
        rom[0x103] = 0x53;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 2,e
        rom[0x103] = 0x53;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB54
    @Test
    void bit_2_h_test() {
        rom[0x100] = 0x26; // ld h,0x04
        rom[0x101] = 0x04;
        rom[0x102] = 0xCB; // bit 2,h
        rom[0x103] = 0x54;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x26; // ld h,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 2,h
        rom[0x103] = 0x54;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB55
    @Test
    void bit_2_l_test() {
        rom[0x100] = 0x2E; // ld l,0x04
        rom[0x101] = 0x04;
        rom[0x102] = 0xCB; // bit 2,l
        rom[0x103] = 0x55;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x2E; // ld l,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 2,l
        rom[0x103] = 0x55;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB56
    @Test
    void bit_2_hlp_test() {
        memory.setByteAt(0xC000, 0x04); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // bit 2,(hl)
        rom[0x104] = 0x56;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCB57
    @Test
    void bit_2_a_test() {
        rom[0x100] = 0x3E; // ld a,0x04
        rom[0x101] = 0x04;
        rom[0x102] = 0xCB; // bit 2,a
        rom[0x103] = 0x57;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 2,a
        rom[0x103] = 0x57;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB58
    @Test
    void bit_3_b_test() {
        rom[0x100] = 0x06; // ld b,0x08
        rom[0x101] = 0x08;
        rom[0x102] = 0xCB; // bit 3,b
        rom[0x103] = 0x58;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 3,b
        rom[0x103] = 0x58;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB59
    @Test
    void bit_3_c_test() {
        rom[0x100] = 0x0E; // ld c,0x08
        rom[0x101] = 0x08;
        rom[0x102] = 0xCB; // bit 3,c
        rom[0x103] = 0x59;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x0E; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 3,c
        rom[0x103] = 0x59;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB5A
    @Test
    void bit_3_d_test() {
        rom[0x100] = 0x16; // ld d,0x08
        rom[0x101] = 0x08;
        rom[0x102] = 0xCB; // bit 3,d
        rom[0x103] = 0x5A;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x16; // ld d,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 3,d
        rom[0x103] = 0x5A;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB5B
    @Test
    void bit_3_e_test() {
        rom[0x100] = 0x1E; // ld e,0x08
        rom[0x101] = 0x08;
        rom[0x102] = 0xCB; // bit 3,e
        rom[0x103] = 0x5B;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 3,e
        rom[0x103] = 0x5B;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB5C
    @Test
    void bit_3_h_test() {
        rom[0x100] = 0x26; // ld h,0x08
        rom[0x101] = 0x08;
        rom[0x102] = 0xCB; // bit 3,h
        rom[0x103] = 0x5C;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x26; // ld h,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 3,h
        rom[0x103] = 0x5C;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB5D
    @Test
    void bit_3_l_test() {
        rom[0x100] = 0x2E; // ld l,0x08
        rom[0x101] = 0x08;
        rom[0x102] = 0xCB; // bit 3,l
        rom[0x103] = 0x5D;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x2E; // ld l,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 3,l
        rom[0x103] = 0x5D;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB5E
    @Test
    void bit_3_hlp_test() {
        memory.setByteAt(0xC000, 0x08); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // bit 3,(hl)
        rom[0x104] = 0x5E;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCB5F
    @Test
    void bit_3_a_test() {
        rom[0x100] = 0x3E; // ld a,0x08
        rom[0x101] = 0x08;
        rom[0x102] = 0xCB; // bit 3,a
        rom[0x103] = 0x5F;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 3,a
        rom[0x103] = 0x5F;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }
}
