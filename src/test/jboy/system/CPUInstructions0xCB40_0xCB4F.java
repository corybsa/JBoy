package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xCB40_0xCB4F {
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
        cpu.setSP(0xFFFE);
        rom = new int[0x7FFF];
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
    }

    @AfterEach
    void tearDown() {
        rom = null;
    }

    // op code 0xCB40
    @Test
    void bit_0_b_test() {
        rom[0x100] = 0x06; // ld b,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // bit 0,b
        rom[0x103] = 0x40;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x06; // ld b,0x04
        rom[0x101] = 0x04;
        rom[0x102] = 0xCB; // bit 0,b
        rom[0x103] = 0x40;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB41
    @Test
    void bit_0_c_test() {
        rom[0x100] = 0x0E; // ld c,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // bit 0,c
        rom[0x103] = 0x41;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x0E; // ld c,0x04
        rom[0x101] = 0x04;
        rom[0x102] = 0xCB; // bit 0,c
        rom[0x103] = 0x41;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB42
    @Test
    void bit_0_d_test() {
        rom[0x100] = 0x16; // ld d,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // bit 0,d
        rom[0x103] = 0x42;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x16; // ld d,0x04
        rom[0x101] = 0x04;
        rom[0x102] = 0xCB; // bit 0,d
        rom[0x103] = 0x42;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB43
    @Test
    void bit_0_e_test() {
        rom[0x100] = 0x1E; // ld e,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // bit 0,e
        rom[0x103] = 0x43;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x1E; // ld e,0x04
        rom[0x101] = 0x04;
        rom[0x102] = 0xCB; // bit 0,e
        rom[0x103] = 0x43;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB44
    @Test
    void bit_0_h_test() {
        rom[0x100] = 0x26; // ld h,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // bit 0,h
        rom[0x103] = 0x44;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x26; // ld h,0x04
        rom[0x101] = 0x04;
        rom[0x102] = 0xCB; // bit 0,h
        rom[0x103] = 0x44;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB45
    @Test
    void bit_0_l_test() {
        rom[0x100] = 0x2E; // ld l,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // bit 0,l
        rom[0x103] = 0x45;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x2E; // ld l,0x04
        rom[0x101] = 0x04;
        rom[0x102] = 0xCB; // bit 0,l
        rom[0x103] = 0x45;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB46
    @Test
    void bit_0_hlp_test() {
        memory.setByteAt(0xC000, 0x01); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // bit 0,(hl)
        rom[0x104] = 0x46;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        memory.setByteAt(0xC000, 0x04); // This is the value that HL will point to.

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCB47
    @Test
    void bit_0_a_test() {
        rom[0x100] = 0x3E; // ld a,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // bit 0,a
        rom[0x103] = 0x47;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0x04
        rom[0x101] = 0x04;
        rom[0x102] = 0xCB; // bit 0,a
        rom[0x103] = 0x47;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB48
    @Test
    void bit_1_b_test() {
        rom[0x100] = 0x06; // ld b,0x02
        rom[0x101] = 0x02;
        rom[0x102] = 0xCB; // bit 1,b
        rom[0x103] = 0x48;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 1,b
        rom[0x103] = 0x48;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB49
    @Test
    void bit_1_c_test() {
        rom[0x100] = 0x0E; // ld c,0x02
        rom[0x101] = 0x02;
        rom[0x102] = 0xCB; // bit 1,c
        rom[0x103] = 0x49;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x0E; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 1,c
        rom[0x103] = 0x49;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB4A
    @Test
    void bit_1_d_test() {
        rom[0x100] = 0x16; // ld d,0x02
        rom[0x101] = 0x02;
        rom[0x102] = 0xCB; // bit 1,d
        rom[0x103] = 0x4A;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x16; // ld d,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 1,d
        rom[0x103] = 0x4A;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB4B
    @Test
    void bit_1_e_test() {
        rom[0x100] = 0x1E; // ld e,0x02
        rom[0x101] = 0x02;
        rom[0x102] = 0xCB; // bit 1,e
        rom[0x103] = 0x4B;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 1,e
        rom[0x103] = 0x4B;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB4C
    @Test
    void bit_1_h_test() {
        rom[0x100] = 0x26; // ld h,0x02
        rom[0x101] = 0x02;
        rom[0x102] = 0xCB; // bit 1,h
        rom[0x103] = 0x4C;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x26; // ld h,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 1,h
        rom[0x103] = 0x4C;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB4D
    @Test
    void bit_1_l_test() {
        rom[0x100] = 0x2E; // ld l,0x02
        rom[0x101] = 0x02;
        rom[0x102] = 0xCB; // bit 1,l
        rom[0x103] = 0x4D;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x2E; // ld l,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 1,l
        rom[0x103] = 0x4D;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB4E
    @Test
    void bit_1_hlp_test() {
        memory.setByteAt(0xC000, 0x02); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // bit 1,(hl)
        rom[0x104] = 0x4E;

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

    // op code 0xCB4F
    @Test
    void bit_1_a_test() {
        rom[0x100] = 0x3E; // ld a,0x02
        rom[0x101] = 0x02;
        rom[0x102] = 0xCB; // bit 1,a
        rom[0x103] = 0x4F;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 1,a
        rom[0x103] = 0x4F;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }
}
