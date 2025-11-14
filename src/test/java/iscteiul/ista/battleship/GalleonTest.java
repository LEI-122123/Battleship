/**
 * Test class for class Galleon.
 * Author: ${user.name}
 * Date: 2025-11-14 12:35
 *
 * Cyclomatic Complexity:
 * - constructor: 6
 * - getSize(): 1
 */

package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class GalleonTest {

    private Galleon galleon;

    /** Setup before each test. */
    @BeforeEach
    public void setUp() {
        galleon = new Galleon(Compass.NORTH, new Position(0, 0));
    }

    /** Teardown after each test. */
    @AfterEach
    public void tearDown() {
        galleon = null;
    }

    // ===============================================================
    // =============== TESTS FOR CONSTRUCTOR (CC = 6) =================
    // ===============================================================

    /**
     * constructor1() → Test null bearing (NullPointerException).
     */
    @Test
    public void constructor1() {
        assertThrows(NullPointerException.class,
                () -> new Galleon(null, new Position(0, 0)),
                "Error: expected NullPointerException for null bearing but no exception thrown");
    }

    /**
     * constructor2() → Test NORTH orientation (fillNorth).
     */
    @Test
    public void constructor2() {
        Galleon g = new Galleon(Compass.NORTH, new Position(1, 1));
        List<IPosition> pos = g.getPositions();

        assertAll("NORTH shape",
                () -> assertEquals(5, pos.size(),
                        "Error: expected 5 positions for NORTH but got " + pos.size()),

                // The 3 horizontal top cells
                () -> assertEquals(1, pos.get(0).getRow(), "Error: wrong row for NORTH cell0"),
                () -> assertEquals(1, pos.get(0).getColumn(), "Error: wrong col for NORTH cell0"),
                () -> assertEquals(1, pos.get(1).getRow(), "Error: wrong row for NORTH cell1"),
                () -> assertEquals(2, pos.get(2).getColumn(), "Error: wrong col for NORTH cell2"),

                // Middle vertical extension
                () -> assertEquals(2, pos.get(3).getRow(), "Error: wrong row NORTH cell3"),
                () -> assertEquals(2, pos.get(3).getColumn(), "Error: wrong col NORTH cell3"),

                // Bottom vertical extension
                () -> assertEquals(3, pos.get(4).getRow(), "Error: wrong row NORTH cell4"),
                () -> assertEquals(2, pos.get(4).getColumn(), "Error: wrong col NORTH cell4")
        );
    }

    /**
     * constructor3() → Test EAST orientation (fillEast).
     */
    @Test
    public void constructor3() {
        Galleon g = new Galleon(Compass.EAST, new Position(3, 3));
        List<IPosition> pos = g.getPositions();

        assertAll("EAST shape",
                () -> assertEquals(5, pos.size(),
                        "Error: expected 5 positions for EAST but got " + pos.size()),

                // Top block
                () -> assertEquals(3, pos.get(0).getRow(), "Error: wrong row EAST cell0"),
                () -> assertEquals(3, pos.get(0).getColumn(), "Error: wrong col EAST cell0"),

                // Middle horizontal group (row+1)
                () -> assertEquals(4, pos.get(1).getRow(), "Error: wrong row EAST cell1"),
                () -> assertEquals(1, pos.get(1).getColumn() - 2, "Error: wrong relative col EAST cell1"),

                // Bottom cell
                () -> assertEquals(5, pos.get(4).getRow(), "Error: wrong row EAST cell4")
        );
    }

    /**
     * constructor4() → Test SOUTH orientation (fillSouth).
     */
    @Test
    public void constructor4() {
        Galleon g = new Galleon(Compass.SOUTH, new Position(2, 2));
        List<IPosition> pos = g.getPositions();

        assertAll("SOUTH shape",
                () -> assertEquals(5, pos.size(),
                        "Error: expected 5 positions for SOUTH but got " + pos.size()),

                // Vertical first two cells
                () -> assertEquals(2, pos.get(0).getRow(), "Error: wrong row SOUTH cell0"),
                () -> assertEquals(2, pos.get(0).getColumn(), "Error: wrong col SOUTH cell0"),
                () -> assertEquals(3, pos.get(1).getRow(), "Error: wrong row SOUTH cell1"),

                // Horizontal base line of 3 cells (row+2)
                () -> assertEquals(4, pos.get(2).getRow(), "Error: wrong row SOUTH cell2"),
                () -> assertEquals(2, pos.get(2).getColumn(), "Error: wrong col SOUTH cell2")
        );
    }

    /**
     * constructor5() → Test WEST orientation (fillWest).
     */
    @Test
    public void constructor5() {
        Galleon g = new Galleon(Compass.WEST, new Position(5, 5));
        List<IPosition> pos = g.getPositions();

        assertAll("WEST shape",
                () -> assertEquals(5, pos.size(),
                        "Error: expected 5 positions for WEST but got " + pos.size()),

                // Top cell
                () -> assertEquals(5, pos.get(0).getRow(), "Error: wrong row WEST cell0"),
                () -> assertEquals(5, pos.get(0).getColumn(), "Error: wrong col WEST cell0"),

                // Middle horizontal 3 cells
                () -> assertEquals(6, pos.get(1).getRow(), "Error: wrong row WEST cell1")
        );
    }

    /**
     * constructor6() → Test invalid bearing (default case) using a mock invalid enum-like value.
     * Since enums cannot be extended, we simulate by casting an int to Compass.
     */
    @Test
    public void constructor6() {
        // Simulate invalid enum value by casting an impossible number
        Compass fake = (Compass) (Object) Integer.valueOf(999);

        assertThrows(IllegalArgumentException.class,
                () -> new Galleon(fake, new Position(0, 0)),
                "Error: expected IllegalArgumentException for invalid bearing but did not get it");
    }

    // ===============================================================
    // ====================== TEST FOR getSize() ======================
    // ===============================================================

    /**
     * getSize() – CC = 1
     */
    @Test
    public void getSize() {
        assertEquals(5, galleon.getSize(),
                "Error: expected size 5 but got " + galleon.getSize());
    }
}
