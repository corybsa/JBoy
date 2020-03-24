package test.jboy.system;

import jboy.system.*;
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
        Display display = new Display(memory);
        GPU gpu = new GPU(memory, display);
        Timers timers = new Timers(memory);

        display.setDrawFunction((tiles) -> null);
        memory.setGpuRef(gpu);

        cpu = new CPU(memory, gpu, timers);
    }

    @BeforeEach
    void setUp() {
        rom = new int[0x7FFF];
        cpu.reset();
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
        assertEquals(0x106, cpu.registers.PC, "The PC register should equal 0x106.");
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
        assertEquals(0x106, cpu.registers.PC, "The PC register should equal 0x106.");
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
        assertEquals(0x106, cpu.registers.PC, "The PC register should equal 0x106.");
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
        assertEquals(0x106, cpu.registers.PC, "The PC register should equal 0x106.");
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
        assertEquals(0x104, cpu.registers.PC, "The PC register should equal 0x104.");
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
        assertEquals(0x104, cpu.registers.PC, "The PC register should equal 0x104.");
    }

    // op code 0x76
    @Test
    void halt_test() {
        // halt the cpu normally
        rom[0x100] = 0xFB; // ei
        rom[0x101] = 0x00; // nop
        rom[0x102] = 0x76; // halt

        memory.loadROM(rom);

        assertFalse(cpu.getIME(), "The IME should be disabled by default.");
        assertFalse(cpu.isHalted, "The CPU should not be halted by default.");

        cpu.tick();
        cpu.tick();
        cpu.tick();

        assertEquals(0x103, cpu.registers.PC);
        assertTrue(cpu.getIME(), "The IME should be enabled.");
        assertTrue(cpu.isHalted, "The CPU should be halted.");
        assertEquals(12, cpu.cycles);

        // TODO: test interrupt calls
    }

    @Test
    void halt_bug_test() {
        rom[0x100] = 0xAF; // xor a
        rom[0x101] = 0x76; // halt
        rom[0x102] = 0x3C; // inc a

        memory.loadROM(rom);

        memory.setByteAt(IORegisters.INTERRUPT_ENABLE, 0x00);
        memory.setByteAt(IORegisters.INTERRUPT_FLAGS, 0x00);

        assertFalse(cpu.getIME());

        cpu.tick(); // xor a
        assertEquals(0x101, cpu.registers.PC, "should execute normally");

        memory.setByteAt(IORegisters.INTERRUPT_ENABLE, 0x1D);
        memory.setByteAt(IORegisters.INTERRUPT_FLAGS, 0xCA);

        cpu.tick(); // halt
        assertEquals(0x102, cpu.registers.PC, "should increment normally");
        assertFalse(cpu.isHalted, "should not halt");
        assertTrue(cpu.haltBug, "should activate halt bug");

        cpu.tick(); // inc a
        assertEquals(0x102, cpu.registers.PC, "should not increment PC due to halt bug");
        assertEquals(0x01, cpu.registers.A, "should increment A by 1");

        cpu.tick(); // inc a
        assertEquals(0x103, cpu.registers.PC, "should increment PC normally");
        assertEquals(0x02, cpu.registers.A, "should increment A by 1");
        assertFalse(cpu.isHalted, "should not be halted");
        assertEquals(16, cpu.cycles, "should have taken 16 clock cycles");
    }

    @Test
    void halt_bug2_test() {
        rom[0x100] = 0x76; // halt
        rom[0x101] = 0xFA; // ld a, (0x1284)
        rom[0x102] = 0x84;
        rom[0x103] = 0x12;

        memory.loadROM(rom);

        cpu.registers.setDE(0xC000);
        memory.setByteAt(0x84FA, 0x42);
        memory.setByteAt(IORegisters.INTERRUPT_ENABLE, 0x1D);
        memory.setByteAt(IORegisters.INTERRUPT_FLAGS, 0xCA);

        assertFalse(cpu.getIME(), "IME should be disabled");

        cpu.tick(); // halt
        assertEquals(0x101, cpu.registers.PC, "should increment PC normally");
        assertFalse(cpu.isHalted, "should not halt CPU");
        assertTrue(cpu.haltBug, "should activate halt bug");

        cpu.tick(); // ld a, (0x84FA) ; this operation is borked by the halt bug
        assertEquals(0x103, cpu.registers.PC, "should increment PC by an unintended amount");
        assertEquals(0x42, cpu.registers.A, "should load an unintended value into A");

        cpu.tick(); // ld (de), a ; this operation was created due to the halt bug
        assertEquals(0x104, cpu.registers.PC, "should increment PC appropriately for the bugged instruction");
        assertEquals(0x42, memory.getByteAt(0xC000), "should load the value of A into the address pointed to by DE");
        assertFalse(cpu.isHalted, "should not be halted");
        assertEquals(28, cpu.cycles, "should have taken 28 clock cycles");
    }

    @Test
    void halt_bug_freeze_test() {
        rom[0x100] = 0x76; // halt
        rom[0x101] = 0x76; // halt

        memory.loadROM(rom);

        memory.setByteAt(IORegisters.INTERRUPT_ENABLE, 0x1D);
        memory.setByteAt(IORegisters.INTERRUPT_FLAGS, 0xCA);

        assertFalse(cpu.getIME(), "IME should be disabled");

        // no matter how many ticks we do it's always going to be a halt instruction, so let's tick just 5 times
        cpu.tick(); // halt
        assertEquals(0x101, cpu.registers.PC, "should increment PC normally");
        assertFalse(cpu.isHalted, "should not be halted");
        assertTrue(cpu.haltBug, "should activate halt bug");

        cpu.tick(); // halt
        assertEquals(0x101, cpu.registers.PC, "should 'freeze' PC due to halt bug");

        cpu.tick(); // halt
        assertEquals(0x101, cpu.registers.PC, "should 'freeze' PC due to halt bug");

        cpu.tick(); // halt
        assertEquals(0x101, cpu.registers.PC, "should 'freeze' PC due to halt bug");

        cpu.tick(); // halt
        assertEquals(0x101, cpu.registers.PC, "should 'freeze' PC due to halt bug");
        assertFalse(cpu.isHalted, "should not be halted");
        assertEquals(20, cpu.cycles, "should have taken 20 clock cycles");
    }

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
        assertEquals(0x106, cpu.registers.PC, "The PC register should equal 0x106.");
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
        assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
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
        assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
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
        assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
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
        assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
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
        assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
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
        assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
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
        assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
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
        assertEquals(0x50, cpu.registers.A, "The A register should equal 0x50.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }
}
