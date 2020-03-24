package test.jboy.system;

import jboy.system.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0x30_0x3F {
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
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
    }

    @AfterEach
    void tearDown() {
        rom = null;
    }

    // op code 0x30
    @Test
    void jr_nc_x_test() {
        rom[0x100] = 0x30; // jr nc,0x05
        rom[0x101] = 0x05;

        memory.loadROM(rom);

        cpu.resetFlags(CPU.FLAG_CARRY);
        cpu.tick();
        assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");

        cpu.registers.PC = 0x100;
        cpu.setFlags(CPU.FLAG_CARRY);

        cpu.tick();
        assertEquals(0x102, cpu.registers.PC, "PC should equal 0x102.");
    }

    // op code 0x31
    @Test
    void ld_sp_xx_test() {
        rom[0x100] = 0x31; // ld sp,0xFFF0
        rom[0x101] = 0xF0;
        rom[0x102] = 0xFF;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFF0, cpu.registers.SP, "The SP register should equal 0xC0DE.");
        assertEquals(0x103, cpu.registers.PC, "The PC should equal 0x103.");
    }

    // op code 0x32
    @Test
    void ldd_hlp_a_test() {
        memory.setByteAt(0xC001, 0x00); // The memory address that HL points to.

        rom[0x100] = 0x3E; // ld a,0x50
        rom[0x101] = 0x50;
        rom[0x102] = 0x21; // ld hl,0xC001
        rom[0x103] = 0x01;
        rom[0x104] = 0xC0;
        rom[0x105] = 0x32; // ld (hl-),a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x50, memory.getByteAt(0xC001), "The address 0xC001 should contain the value 0x50.");
        assertEquals(0xC000, cpu.registers.getHL(), "The HL register should equal 0xC000.");
        assertEquals(0x106, cpu.registers.PC, "The PC should equal 0x106.");
    }

    // op code 0x33
    @Test
    void inc_sp_test() {
        rom[0x100] = 0x31; // ld sp,0x0001
        rom[0x101] = 0x01;
        rom[0x102] = 0x00;
        rom[0x103] = 0x33; // inc sp

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x0002, cpu.registers.SP, "The SP register should equal 0x0002.");
        assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
    }

    // op code 0x34
    @Test
    void inc_hlp_test() {
        memory.setByteAt(0xC000, 0x00); // The value HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x34; // inc (hl)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x01, memory.getByteAt(0xC000), "The address should contain 0x01.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0x35
    @Test
    void dec_hlp_test() {
        memory.setByteAt(0xC000, 0x01); // The value HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x35; // dec (hl)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, memory.getByteAt(0xC000), "The address should contain 0x00.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0x36
    @Test
    void ld_hlp_x_test() {
        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x36; // ld (hl),0x50
        rom[0x104] = 0x50;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x50, memory.getByteAt(0xC000), "The address should contain 0x50.");
        assertEquals(0x105, cpu.registers.PC, "PC should equal 0x106.");
    }

    // op code 0x37
    @Test
    void scf_test() {
        rom[0x100] = 0x37; // scf

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
    }

    // op code 0x38
    @Test
    void jr_c_x_test() {
        rom[0x100] = 0x38; // jr c,0x05
        rom[0x101] = 0x05;

        memory.loadROM(rom);

        cpu.resetFlags(CPU.FLAG_CARRY);
        cpu.tick();
        assertEquals(0x102, cpu.registers.PC, "PC should equal 0x102.");

        cpu.registers.PC = 0x100;
        cpu.setFlags(CPU.FLAG_CARRY);

        cpu.tick();
        assertEquals(0x106, cpu.registers.PC, "PC should equal 0x106.");
    }

    // op code 0x39
    @Test
    void add_hl_sp_test() {
        rom[0x100] = 0x31; // ld sp,0x0605
        rom[0x101] = 0x05;
        rom[0x102] = 0x06;
        rom[0x103] = 0x21; // ld hl,0x8A23
        rom[0x104] = 0x23;
        rom[0x105] = 0x8A;
        rom[0x106] = 0x39; // add hl,sp

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x9028, cpu.registers.getHL(), "The HL register should equal 0x9028.");
        assertEquals(CPU.FLAG_HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
        assertEquals(0x107, cpu.registers.PC, "PC should equal 0x107.");

        cpu.registers.PC = 0x100;
        rom[0x100] = 0x31; // ld sp,0xFFFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xFF;
        rom[0x103] = 0x21; // ld hl,0xFFFF
        rom[0x104] = 0xFF;
        rom[0x105] = 0xFF;
        rom[0x106] = 0x39; // add hl,sp

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xFFFE, cpu.registers.getHL(), "The HL register should equal 0x0C0A.");
        assertEquals(CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.registers.F, "The HALF_CARRY and CARRY flag should be set.");
        assertEquals(0x107, cpu.registers.PC, "PC should equal 0x107.");
    }

    // op code 0x3A
    @Test
    void ldd_a_hlp_test() {
        memory.setByteAt(0xC000, 0x3C);

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0x3A; // ld a,(hl-)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x3C, cpu.registers.A, "The A register should equal 0x3C.");
        assertEquals(0xBFFF, cpu.registers.getHL(), "The HL register should equal 0xBFFF.");
        assertEquals(0x104, cpu.registers.PC, "PC should equal 0x104.");
    }

    // op code 0x3B
    @Test
    void dec_sp_test() {
        rom[0x100] = 0x31; // ld sp,0x0001
        rom[0x101] = 0x01;
        rom[0x102] = 0x00;
        rom[0x103] = 0x3B; // dec sp

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x0000, cpu.registers.SP, "The SP register should equal 0x0000.");
        assertEquals(0x104, cpu.registers.PC, "The PC should equal 0x104.");
    }

    // op code 0x3C
    @Test
    void inc_a_test() {
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x3C; // inc a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x01, cpu.registers.A, "The A register should equal 0x01.");
        assertEquals(0x00, cpu.registers.F, "No flags should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0x0F
        rom[0x101] = 0x0F;
        rom[0x102] = 0x3C; // inc a

        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.registers.A, "The A register should equal 0x10.");
        assertEquals(CPU.FLAG_HALF, cpu.registers.F, "The HALF_CARRY flag should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x3C; // inc a

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.registers.F, "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x3D
    @Test
    void dec_a_test() {
        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x3D; // dec a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.registers.A, "The A register should equal 0xFE.");
        assertEquals(CPU.FLAG_SUB, cpu.registers.F, "The SUB flag should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

        // Test a decrement that results in zero.
        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0x3D; // dec a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.registers.A, "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.registers.F, "ZERO and SUB flags should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

        // Test a half carry.
        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x10
        rom[0x101] = 0x10;
        rom[0x102] = 0x3D; // dec a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x0F, cpu.registers.A, "The A register should equal 0x0F.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");

        // Test decrementing zero.
        cpu.registers.PC = 0x100;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x3D; // dec a

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFF, cpu.registers.A, "The A register should equal 0xFF.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.registers.F, "The SUB and HALF_CARRY flags should be set.");
        assertEquals(0x103, cpu.registers.PC, "PC should equal 0x103.");
    }

    // op code 0x3E
    @Test
    void ld_a_x_test() {
        rom[0x100] = 0x3E; // ld a,0xA9
        rom[0x101] = 0xA9;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xA9, cpu.registers.A, "The A register should equal 0xA9");
        assertEquals(0x102, cpu.registers.PC, "PC should equal 0x102");
    }

    // op code 0x3F
    @Test
    void ccf_test() {
        rom[0x100] = 0x3F; // ccf

        memory.loadROM(rom);

        cpu.resetFlags(CPU.FLAG_CARRY);
        cpu.tick();
        assertEquals(CPU.FLAG_CARRY, cpu.registers.F, "The CARRY flag should be set.");
        assertEquals(0x101, cpu.registers.PC, "The PC should equal 0x101.");

        cpu.setFlags(CPU.FLAG_CARRY);
        cpu.registers.PC = 0x100;
        cpu.tick();
        assertEquals(0x00, cpu.registers.F, "No flags should be set.");
        assertEquals(0x101, cpu.registers.PC, "The PC should equal 0x101.");
    }
}
