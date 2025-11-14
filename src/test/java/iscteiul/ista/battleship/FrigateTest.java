/**
 * Test class for class Frigate.
 * Author: ${user.name}
 * Date: 2025-11-14 12:00
 *
 * Cyclomatic Complexity:
 * - constructor: 5
 * - getSize(): 1
 */

package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class FrigateTest {

    private Frigate frigate;

    /** Creates a default Frigate before each test. */
    @BeforeEach
    public void setUp() {
        frigate = new Frigate(Compass.NORTH, new Position(0, 0));
    }

    /** Cleans up after each test. */
    @AfterEach
    public void tearDown() {
        frigate = null;
    }

    // =========================================================
    // =============== TESTS FOR CONSTRUCTOR (CC = 5) ==========
    // =========================================================

    /**
     * constructor1() – Test NORTH orientation.
     */
    @Test
    public void constructor1() {
        Frigate f = new Frigate(Compass.NORTH, new Position(2, 5));

        List<IPosition> pos = f.getPositions();
        assertAll("NORTH orientation positions",
                () -> assertEquals(4, pos.size(), "Error: expected 4 positions but got " + pos.size()),
                () -> assertEquals(2, pos.get(0).getRow(), "Error: incorrect row for NORTH r0"),
                () -> assertEquals(5, pos.get(0).getColumn(), "Error: incorrect column for NORTH c0"),
                () -> assertEquals(3, pos.get(1).getRow(), "Error: incorrect row for NORTH r1"),
                () -> assertEquals(4, pos.get(2).getRow(), "Error: incorrect row for NORTH r2"),
                () -> assertEquals(5, pos.get(3).getRow(), "Error: incorrect row for NORTH r3")
        );
    }

    /**
     * constructor2() – Test SOUTH orientation.
     */
    @Test
    public void constructor2() {
        Frigate f = new Frigate(Compass.SOUTH, new Position(1, 3));

        List<IPosition> pos = f.getPositions();
        assertAll("SOUTH orientation positions",
                () -> assertEquals(4, pos.size(), "Error: expected 4 positions but got " + pos.size()),
                () -> assertEquals(1, pos.get(0).getRow(), "Error: incorrect row r0 for SOUTH"),
                () -> assertEquals(3, pos.get(0).getColumn(), "Error: incorrect column c0 for SOUTH"),
                () -> assertEquals(2, pos.get(1).getRow(), "Error: incorrect row r1 for SOUTH")
        );
    }

    /**
     * constructor3() – Test EAST orientation.
     */
    @Test
    public void constructor3() {
        Frigate f = new Frigate(Compass.EAST, new Position(4, 2));

        List<IPosition> pos = f.getPositions();
        assertAll("EAST orientation positions",
                () -> assertEquals(4, pos.size(), "Error: expected 4 positions but got " + pos.size()),
                () -> assertEquals(4, pos.get(0).getRow(), "Error: incorrect row r0 for EAST"),
                () -> assertEquals(2, pos.get(0).getColumn(), "Error: incorrect column c0 for EAST"),
                () -> assertEquals(3, pos.get(1).getColumn(), "Error: incorrect column c1 for EAST")
        );
    }

    /**
     * constructor4() – Test WEST orientation.
     */
    @Test
    public void constructor4() {
        Frigate f = new Frigate(Compass.WEST, new Position(7, 7));

        List<IPosition> pos = f.getPositions();
        assertAll("WEST orientation positions",
                () -> assertEquals(4, pos.size(), "Error: expected 4 positions but got " + pos.size()),
                () -> assertEquals(7, pos.get(0).getRow(), "Error: incorrect row r0 for WEST"),
                () -> assertEquals(7, pos.get(0).getColumn(), "Error: incorrect column c0 for WEST"),
                () -> assertEquals(8, pos.get(1).getColumn(), "Error: expected col=8 but got " + pos.get(1).getColumn())
        );
    }

    /**
     * constructor5() – Test invalid orientation (default case → exception).
     */
    @Test
    public void constructor5() {
        // Use a "fake" illegal value if Compass is enum: simulate by null
        assertThrows(IllegalArgumentException.class,
                () -> new Frigate(null, new Position(0, 0)),
                "Error: expected IllegalArgumentException for invalid bearing but none thrown");
    }

    // =========================================================
    // =================== TESTS FOR getSize() =================
    // =========================================================

    /**
     * getSize() – CC = 1
     */
    @Test
    public void getSize() {
        assertEquals(4, frigate.getSize(),
                "Error: expected size 4 but got " + frigate.getSize());
    }
}
