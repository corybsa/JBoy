package test.goodboy.system;

import goodboy.system.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class GameBoyTest {
    static GameBoy gameBoy;

    static int[] getRom(String filename) {
        try {
            File file = new File(filename);

            byte[] bytes = Files.readAllBytes(Paths.get(file.getPath()));
            int[] rom = new int[bytes.length];

            for(int i = 0; i < bytes.length; i++) {
                rom[i] = bytes[i] & 0xFF;
            }

            return rom;
        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    static int getBreakpoint(String target, String filename) {
        try {
            File file = new File(filename);
            int breakpoint = 0x100;

            for(String line : Files.readAllLines(Paths.get(file.getPath()))) {
                if(line.contains(target)) {
                    breakpoint = Integer.parseInt(line.substring(3, 7), 16);
                    break;
                }
            }

            return breakpoint;
        } catch(IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    static boolean runTest(String filename) {
        System.out.print("Running " + filename + " -> ");

        filename = filename.substring(0, filename.lastIndexOf('.'));
        gameBoy.loadROM(getRom(filename + ".gb"));
        int success = getBreakpoint("quit@success", filename + ".sym");
        int failure = getBreakpoint("quit@failure", filename + ".sym");
        long start = Instant.now().getEpochSecond();
        long now;
        boolean timeout = false;

        do {
            gameBoy.tick();

            if(gameBoy.getCpu().registers.PC == failure) {
                break;
            }

            now = Instant.now().getEpochSecond();

            // wait 5 seconds. this probably means the test is in an infinite loop
            if((now - start) > 5) {
                timeout = true;
                break;
            }
        } while(gameBoy.getCpu().registers.PC != success);

        if(gameBoy.getCpu().registers.PC == failure) {
            System.out.println("Test failed.\n");
            return false;
        } else if(timeout) {
            System.out.println("Test timed out.\n");
            return false;
        } else {
            System.out.println("Test OK\n");
            return true;
        }
    }

    static boolean runFolder(String folderName) {
        File folder = new File(folderName);
        boolean testStatus = true;

        for(File file : Objects.requireNonNull(folder.listFiles())) {
            if(!file.isDirectory()) {
                int extindex = file.getName().lastIndexOf('.');

                if(file.getName().substring(extindex + 1).equals("gb")) {
                    boolean runStatus = runTest(file.getPath());

                    if(!runStatus) {
                        testStatus = false;
                    }
                }
            }
        }

        return testStatus;
    }

    // **********************
    // ACCEPTANCE
    // **********************
    static class MooneyeAcceptanceTest {
        @BeforeAll
        static void testBeforeAll() {
            gameBoy = new GameBoy();
            gameBoy.getLCD().setDrawFunction((x) -> null);
        }

        @BeforeEach
        void setUp() {
            gameBoy.reset();
        }

        @Test
        void mooneye_acceptance_Tests() {
            boolean passed = runFolder("resources/roms/tests/mooneye/acceptance");
            assertTrue(passed);
        }
    }

    static class MooneyeAcceptanceBitTest {
        @BeforeAll
        static void testBeforeAll() {
            gameBoy = new GameBoy();
            gameBoy.getLCD().setDrawFunction((x) -> null);
        }

        @BeforeEach
        void setUp() {
            gameBoy.reset();
        }

        @Test
        void mooneye_acceptance_Tests() {
            boolean passed = runFolder("resources/roms/tests/mooneye/acceptance/bits");
            assertTrue(passed);
        }
    }

    static class MooneyeAcceptanceInstrTest {
        @BeforeAll
        static void testBeforeAll() {
            gameBoy = new GameBoy();
            gameBoy.getLCD().setDrawFunction((x) -> null);
        }

        @BeforeEach
        void setUp() {
            gameBoy.reset();
        }

        @Test
        void mooneye_acceptance_Tests() {
            boolean passed = runFolder("resources/roms/tests/mooneye/acceptance/instr");
            assertTrue(passed);
        }
    }

    static class MooneyeAcceptanceInterruptsTest {
        @BeforeAll
        static void testBeforeAll() {
            gameBoy = new GameBoy();
            gameBoy.getLCD().setDrawFunction((x) -> null);
        }

        @BeforeEach
        void setUp() {
            gameBoy.reset();
        }

        @Test
        void mooneye_acceptance_Tests() {
            boolean passed = runFolder("resources/roms/tests/mooneye/acceptance/interrupts");
            assertTrue(passed);
        }
    }

    static class MooneyeAcceptanceOamDmaTest {
        @BeforeAll
        static void testBeforeAll() {
            gameBoy = new GameBoy();
            gameBoy.getLCD().setDrawFunction((x) -> null);
        }

        @BeforeEach
        void setUp() {
            gameBoy.reset();
        }

        @Test
        void mooneye_acceptance_Tests() {
            boolean passed = runFolder("resources/roms/tests/mooneye/acceptance/oam_dma");
            assertTrue(passed);
        }
    }

    static class MooneyeAcceptancePpuTest {
        @BeforeAll
        static void testBeforeAll() {
            gameBoy = new GameBoy();
            gameBoy.getLCD().setDrawFunction((x) -> null);
        }

        @BeforeEach
        void setUp() {
            gameBoy.reset();
        }

        @Test
        void mooneye_acceptance_Tests() {
            boolean passed = runFolder("resources/roms/tests/mooneye/acceptance/ppu");
            assertTrue(passed);
        }
    }

    static class MooneyeAcceptanceSerialTest {
        @BeforeAll
        static void testBeforeAll() {
            gameBoy = new GameBoy();
            gameBoy.getLCD().setDrawFunction((x) -> null);
        }

        @BeforeEach
        void setUp() {
            gameBoy.reset();
        }

        @Test
        void mooneye_acceptance_Tests() {
            boolean passed = runFolder("resources/roms/tests/mooneye/acceptance/serial");
            assertTrue(passed);
        }
    }

    static class MooneyeAcceptanceTimerTest {
        @BeforeAll
        static void testBeforeAll() {
            gameBoy = new GameBoy();
            gameBoy.getLCD().setDrawFunction((x) -> null);
        }

        @BeforeEach
        void setUp() {
            gameBoy.reset();
        }

        @Test
        void mooneye_acceptance_Tests() {
            boolean passed = runFolder("resources/roms/tests/mooneye/acceptance/timer");
            assertTrue(passed);
        }
    }

    // **********************
    // EMULATOR ONLY
    // **********************
    static class MooneyeEmulatorOnlyMbc1Test {
        @BeforeAll
        static void testBeforeAll() {
            gameBoy = new GameBoy();
            gameBoy.getLCD().setDrawFunction((x) -> null);
        }

        @BeforeEach
        void setUp() {
            gameBoy.reset();
        }

        @Test
        void mooneye_acceptance_Tests() {
            boolean passed = runFolder("resources/roms/tests/mooneye/emulator-only/mbc1");
            assertTrue(passed);
        }
    }

    static class MooneyeEmulatorOnlyMbc2Test {
        @BeforeAll
        static void testBeforeAll() {
            gameBoy = new GameBoy();
            gameBoy.getLCD().setDrawFunction((x) -> null);
        }

        @BeforeEach
        void setUp() {
            gameBoy.reset();
        }

        @Test
        void mooneye_acceptance_Tests() {
            boolean passed = runFolder("resources/roms/tests/mooneye/emulator-only/mbc2");
            assertTrue(passed);
        }
    }

    static class MooneyeEmulatorOnlyMbc5Test {
        @BeforeAll
        static void testBeforeAll() {
            gameBoy = new GameBoy();
            gameBoy.getLCD().setDrawFunction((x) -> null);
        }

        @BeforeEach
        void setUp() {
            gameBoy.reset();
        }

        @Test
        void mooneye_acceptance_Tests() {
            boolean passed = runFolder("resources/roms/tests/mooneye/emulator-only/mbc5");
            assertTrue(passed);
        }
    }

    // **********************
    // MANUAL ONLY
    // **********************
    static class MooneyeManualOnlyTest {
        @BeforeAll
        static void testBeforeAll() {
            gameBoy = new GameBoy();
            gameBoy.getLCD().setDrawFunction((x) -> null);
        }

        @BeforeEach
        void setUp() {
            gameBoy.reset();
        }

        @Test
        void mooneye_acceptance_Tests() {
            boolean passed = runFolder("resources/roms/tests/mooneye/manual-only");
            assertTrue(passed);
        }
    }

    // **********************
    // MISC
    // **********************
    static class MooneyeMiscTest {
        @BeforeAll
        static void testBeforeAll() {
            gameBoy = new GameBoy();
            gameBoy.getLCD().setDrawFunction((x) -> null);
        }

        @BeforeEach
        void setUp() {
            gameBoy.reset();
        }

        @Test
        void mooneye_acceptance_Tests() {
            boolean passed = runFolder("resources/roms/tests/mooneye/misc");
            assertTrue(passed);
        }
    }

    static class MooneyeMiscBitsTest {
        @BeforeAll
        static void testBeforeAll() {
            gameBoy = new GameBoy();
            gameBoy.getLCD().setDrawFunction((x) -> null);
        }

        @BeforeEach
        void setUp() {
            gameBoy.reset();
        }

        @Test
        void mooneye_acceptance_Tests() {
            boolean passed = runFolder("resources/roms/tests/mooneye/misc/bits");
            assertTrue(passed);
        }
    }

    static class MooneyeMiscPpuTest {
        @BeforeAll
        static void testBeforeAll() {
            gameBoy = new GameBoy();
            gameBoy.getLCD().setDrawFunction((x) -> null);
        }

        @BeforeEach
        void setUp() {
            gameBoy.reset();
        }

        @Test
        void mooneye_acceptance_Tests() {
            boolean passed = runFolder("resources/roms/tests/mooneye/misc/ppu");
            assertTrue(passed);
        }
    }

    // **********************
    // UTILS
    // **********************
    static class MooneyeUtilsTest {
        @BeforeAll
        static void testBeforeAll() {
            gameBoy = new GameBoy();
            gameBoy.getLCD().setDrawFunction((x) -> null);
        }

        @BeforeEach
        void setUp() {
            gameBoy.reset();
        }

        @Test
        void mooneye_acceptance_Tests() {
            boolean passed = runFolder("resources/roms/tests/mooneye/utils");
            assertTrue(passed);
        }
    }
}
