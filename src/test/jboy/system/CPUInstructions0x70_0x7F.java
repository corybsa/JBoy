package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0x70_0x7F {
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

    // op code 0x70
    @Test
    void ld_hlp_b_test() {
        memory.setByteAt(0xC000, 0x50);

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x06; // ld b,0xFF
        rom[0x104] = 0xFF;
        rom[0x105] = 0x70; // ld (hl),b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xFF, memory.getByteAt(0xC000), "The value at 0xC000 should equal 0xFF.");
        assertEquals(0x106, cpu.getPC(), "The PC register should equal 0x106.");
    }

    // op code 0x71
    @Test
    void ld_hlp_c_test() {
        memory.setByteAt(0xC000, 0x50);

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x0E; // ld c,0xFF
        rom[0x104] = 0xFF;
        rom[0x105] = 0x71; // ld (hl),c

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xFF, memory.getByteAt(0xC000), "The value at 0xC000 should equal 0xFF.");
        assertEquals(0x106, cpu.getPC(), "The PC register should equal 0x106.");
    }

    // op code 0x72
    @Test
    void ld_hlp_d_test() {
        memory.setByteAt(0xC000, 0x50);

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x16; // ld d,0xFF
        rom[0x104] = 0xFF;
        rom[0x105] = 0x72; // ld (hl),d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xFF, memory.getByteAt(0xC000), "The value at 0xC000 should equal 0xFF.");
        assertEquals(0x106, cpu.getPC(), "The PC register should equal 0x106.");
    }

    // op code 0x73
    @Test
    void ld_hlp_e_test() {
        memory.setByteAt(0xC000, 0x50);

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x1E; // ld e,0xFF
        rom[0x104] = 0xFF;
        rom[0x105] = 0x73; // ld (hl),e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xFF, memory.getByteAt(0xC000), "The value at 0xC000 should equal 0xFF.");
        assertEquals(0x106, cpu.getPC(), "The PC register should equal 0x106.");
    }

    // op code 0x74
    @Test
    void ld_hlp_h_test() {
        memory.setByteAt(0xC000, 0x50);

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x74; // ld (hl),h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xC0, memory.getByteAt(0xC000), "The value at 0xC000 should equal 0xC0.");
        assertEquals(0x104, cpu.getPC(), "The PC register should equal 0x104.");
    }

    // op code 0x75
    @Test
    void ld_hlp_l_test() {
        memory.setByteAt(0xC000, 0x50);

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x75; // ld (hl),l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, memory.getByteAt(0xC000), "The value at 0xC000 should equal 0x00.");
        assertEquals(0x104, cpu.getPC(), "The PC register should equal 0x104.");
    }

    // op code 0x76
    @Test
    void halt_test() {}

    // op code 0x77
    @Test
    void ld_hlp_a_test() {
        memory.setByteAt(0xC000, 0x50);

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x3E; // ld a,0xFF
        rom[0x104] = 0xFF;
        rom[0x105] = 0x77; // ld (hl),a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xFF, memory.getByteAt(0xC000), "The value at 0xC000 should equal 0xFF.");
        assertEquals(0x106, cpu.getPC(), "The PC register should equal 0x106.");
    }

    // op code 0x78
    @Test
    void ld_a_b_test() {
        rom[0x100] = 0x06; // ld b,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x78; // ld a,b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getA(), "The A register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x79
    @Test
    void ld_a_c_test() {
        rom[0x100] = 0x0E; // ld c,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x79; // ld a,c

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getA(), "The A register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x7A
    @Test
    void ld_a_d_test() {
        rom[0x100] = 0x16; // ld d,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x7A; // ld a,d

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getA(), "The A register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x7B
    @Test
    void ld_a_e_test() {
        rom[0x100] = 0x1E; // ld e,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x7B; // ld a,e

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getA(), "The A register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x7C
    @Test
    void ld_a_h_test() {
        rom[0x100] = 0x26; // ld h,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x7C; // ld a,h

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getA(), "The A register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x7D
    @Test
    void ld_a_l_test() {
        rom[0x100] = 0x2E; // ld l,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x7D; // ld a,l

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getA(), "The A register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }

    // op code 0x7E
    @Test
    void ld_a_hlp_test() {
        memory.setByteAt(0xC000, 0x50);

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x7E; // ld a,(hl)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getA(), "The A register should equal 0x50.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0x7F
    @Test
    void ld_a_a_test() {
        rom[0x100] = 0x3E; // ld a,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x7F; // ld a,a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, cpu.getA(), "The A register should equal 0x50.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103.");
    }
}
