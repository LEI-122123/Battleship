package iscteiul.ista.battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class Fleet.
 * Author: ${user.name}
 * Date: 2025-11-14 12:00
 * Cyclomatic Complexity:
 * - constructor: 1
 * - getShips(): 1
 * - addShip(): 4
 * - getShipsLike(): 3
 * - getFloatingShips(): 3
 * - shipAt(): 3
 * - isInsideBoard(): 4
 * - colisionRisk(): 3
 * - printStatus(): 1
 * - printShipsByCategory(): 2
 * - printFloatingShips(): 1
 * - printAllShips(): 1
 * - printShips(): 2
 */
public class FleetTest {

    private Fleet fleet;

    @BeforeEach
    void setUp() {
        fleet = new Fleet();
    }

    @AfterEach
    void tearDown() {
        fleet = null;
    }

    // -------------------------------------------------------------------------
    // Helper test doubles and utilities
    // -------------------------------------------------------------------------

    /**
     * Minimal position implementation used only for tests.
     * Holds row/column and simple state for occupation and hits.
     */
    private static class TestPosition implements IPosition {

        private final int row;
        private final int column;
        private boolean occupied;
        private boolean hit;

        TestPosition() {
            this(0, 0);
        }

        TestPosition(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public int getRow() {
            return row;
        }

        @Override
        public int getColumn() {
            return column;
        }

        @Override
        public boolean isAdjacentTo(IPosition other) {
            if (other == null) {
                return false;
            }
            int dRow = Math.abs(this.row - other.getRow());
            int dCol = Math.abs(this.column - other.getColumn());
            // Adjacent includes diagonals, but not the same cell
            return (dRow <= 1 && dCol <= 1) && !(dRow == 0 && dCol == 0);
        }

        @Override
        public void occupy() {
            this.occupied = true;
        }

        @Override
        public void shoot() {
            this.hit = true;
        }

        @Override
        public boolean isOccupied() {
            return occupied;
        }

        @Override
        public boolean isHit() {
            return hit;
        }
    }

    /**
     * Minimal ship implementation used only for tests.
     * Only the behavior needed by FleetTest is faithfully implemented;
     * the rest is kept simple but consistent.
     */
    private static class TestShip implements IShip {

        private final String category;
        private final boolean floating;
        private final int leftMost;
        private final int rightMost;
        private final int topMost;
        private final int bottomMost;
        private final boolean collisionRisk;
        private final List<IPosition> occupiedPositions;

        TestShip(String category,
                 boolean floating,
                 int leftMost,
                 int rightMost,
                 int topMost,
                 int bottomMost,
                 boolean collisionRisk,
                 List<IPosition> occupiedPositions) {
            this.category = category;
            this.floating = floating;
            this.leftMost = leftMost;
            this.rightMost = rightMost;
            this.topMost = topMost;
            this.bottomMost = bottomMost;
            this.collisionRisk = collisionRisk;
            this.occupiedPositions = new ArrayList<>(occupiedPositions);
        }

        @Override
        public String getCategory() {
            return category;
        }

        @Override
        public Integer getSize() {
            // Size = number of occupied positions in this stub
            return occupiedPositions.size();
        }

        @Override
        public List<IPosition> getPositions() {
            // Defensive copy to avoid external mutation in tests
            return new ArrayList<>(occupiedPositions);
        }

        @Override
        public IPosition getPosition() {
            // Return a "reference" position (first), or null if there are none
            return occupiedPositions.isEmpty() ? null : occupiedPositions.get(0);
        }

        @Override
        public Compass getBearing() {
            // Not needed by FleetTest; keep simple and neutral
            return null;
        }

        @Override
        public boolean stillFloating() {
            return floating;
        }

        @Override
        public boolean occupies(IPosition pos) {
            // Uses equals/contains; TestPosition uses reference equality
            return occupiedPositions.contains(pos);
        }

        @Override
        public int getLeftMostPos() {
            return leftMost;
        }

        @Override
        public int getRightMostPos() {
            return rightMost;
        }

        @Override
        public int getTopMostPos() {
            return topMost;
        }

        @Override
        public int getBottomMostPos() {
            return bottomMost;
        }

        @Override
        public boolean tooCloseTo(IShip s) {
            // For test purposes we control collision behavior via this flag.
            if (collisionRisk) {
                return true;
            }

            // Optional extra realism: check adjacency between positions
            for (IPosition p : occupiedPositions) {
                for (IPosition other : s.getPositions()) {
                    if (p.equals(other) || p.isAdjacentTo(other)) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean tooCloseTo(IPosition pos) {
            for (IPosition p : occupiedPositions) {
                if (p.equals(pos) || p.isAdjacentTo(pos)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void shoot(IPosition pos) {
            // If the ship occupies this position, mark it as hit
            if (occupies(pos) && (pos instanceof TestPosition tp)) {
                tp.shoot();
            }
        }

        @Override
        public String toString() {
            return "TestShip{" +
                    "category='" + category + '\'' +
                    ", floating=" + floating +
                    '}';
        }
    }

    private TestShip createShipInsideBoard(String category, boolean floating, boolean collisionRisk) {
        int boardSize = IFleet.BOARD_SIZE;
        return new TestShip(
                category,
                floating,
                0,
                boardSize - 1,
                0,
                boardSize - 1,
                collisionRisk,
                Collections.singletonList(new TestPosition())
        );
    }

    private TestShip createShipWithBounds(String category,
                                          boolean floating,
                                          int left,
                                          int right,
                                          int top,
                                          int bottom,
                                          boolean collisionRisk) {
        return new TestShip(
                category,
                floating,
                left,
                right,
                top,
                bottom,
                collisionRisk,
                Collections.singletonList(new TestPosition())
        );
    }

    private boolean invokeIsInsideBoard(IShip ship) {
        try {
            Method m = Fleet.class.getDeclaredMethod("isInsideBoard", IShip.class);
            m.setAccessible(true);
            return (boolean) m.invoke(fleet, ship);
        } catch (Exception e) {
            fail("Error: expected successful invocation of isInsideBoard but got exception: " + e.getMessage());
            return false; // unreachable, but required by compiler
        }
    }

    private boolean invokeColisionRisk(IShip ship) {
        try {
            Method m = Fleet.class.getDeclaredMethod("colisionRisk", IShip.class);
            m.setAccessible(true);
            return (boolean) m.invoke(fleet, ship);
        } catch (Exception e) {
            fail("Error: expected successful invocation of colisionRisk but got exception: " + e.getMessage());
            return false;
        }
    }

    private String captureStdOut(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        try {
            action.run();
        } finally {
            System.setOut(originalOut);
        }
        return outContent.toString();
    }

    // -------------------------------------------------------------------------
    // constructor (CC = 1)
    // -------------------------------------------------------------------------

    @Test
    void constructor() {
        assertAll(
                () -> assertNotNull(fleet.getShips(),
                        "Error: expected non-null ships list after constructor but got null"),
                () -> assertEquals(0, fleet.getShips().size(),
                        "Error: expected empty fleet after constructor but got size " + fleet.getShips().size())
        );
    }

    // -------------------------------------------------------------------------
    // getShips (CC = 1)
    // -------------------------------------------------------------------------

    @Test
    void getShips() {
        List<IShip> ships = fleet.getShips();
        assertAll(
                () -> assertNotNull(ships,
                        "Error: expected non-null ships from getShips() but got null"),
                () -> assertSame(ships, fleet.getShips(),
                        "Error: expected getShips() to return same list instance on repeated calls but got different instances")
        );
    }

    // -------------------------------------------------------------------------
    // addShip (CC = 4) -> addShip1(), addShip2(), addShip3(), addShip4()
    // -------------------------------------------------------------------------

    @Test
    void addShip1() {
        // Path: fleet size <= FLEET_SIZE, isInsideBoard true, !colisionRisk true -> ship added
        TestShip ship = createShipInsideBoard("Galeao", true, false);

        boolean result = fleet.addShip(ship);

        assertAll(
                () -> assertTrue(result,
                        "Error: expected addShip to succeed when all conditions are true but got false"),
                () -> assertEquals(1, fleet.getShips().size(),
                        "Error: expected fleet size 1 after successful addShip but got " + fleet.getShips().size()),
                () -> assertSame(ship, fleet.getShips().get(0),
                        "Error: expected added ship to be stored in fleet but found different reference")
        );
    }

    @Test
    void addShip2() {
        // Path: ships.size() > FLEET_SIZE (first condition false) -> ship not added, others short-circuited
        int limit = IFleet.FLEET_SIZE;
        for (int i = 0; i < limit + 1; i++) {
            fleet.getShips().add(createShipInsideBoard("Dummy", true, false));
        }
        int initialSize = fleet.getShips().size();
        TestShip newShip = createShipInsideBoard("Galeao", true, false);

        boolean result = fleet.addShip(newShip);

        assertAll(
                () -> assertFalse(result,
                        "Error: expected addShip to fail when fleet size exceeds FLEET_SIZE but got true"),
                () -> assertEquals(initialSize, fleet.getShips().size(),
                        "Error: expected fleet size to remain " + initialSize + " when addShip fails but got " + fleet.getShips().size())
        );
    }

    @Test
    void addShip3() {
        // Path: size <= FLEET_SIZE, isInsideBoard false, !colisionRisk true -> ship not added
        int boardSize = IFleet.BOARD_SIZE;
        TestShip outOfBoardShip = createShipWithBounds(
                "Galeao",
                true,
                -1,                 // left < 0 -> outside board
                boardSize - 1,
                0,
                boardSize - 1,
                false
        );

        boolean result = fleet.addShip(outOfBoardShip);

        assertAll(
                () -> assertFalse(result,
                        "Error: expected addShip to fail when ship is outside board but got true"),
                () -> assertEquals(0, fleet.getShips().size(),
                        "Error: expected fleet to remain empty when ship is outside board but got size " + fleet.getShips().size())
        );
    }

    @Test
    void addShip4() {
        // Path: size <= FLEET_SIZE, isInsideBoard true, colisionRisk true (so !colisionRisk false) -> ship not added
        TestShip existing = createShipInsideBoard("Galeao", true, true);
        fleet.getShips().add(existing);
        int initialSize = fleet.getShips().size();

        TestShip newShip = createShipInsideBoard("Galeao", true, false);

        boolean result = fleet.addShip(newShip);

        assertAll(
                () -> assertFalse(result,
                        "Error: expected addShip to fail when colisionRisk is true but got true"),
                () -> assertEquals(initialSize, fleet.getShips().size(),
                        "Error: expected fleet size to remain " + initialSize + " when colisionRisk prevents addShip but got " + fleet.getShips().size())
        );
    }

    // -------------------------------------------------------------------------
    // getShipsLike (CC = 3) -> getShipsLike1(), getShipsLike2(), getShipsLike3()
    // -------------------------------------------------------------------------

    @Test
    void getShipsLike1() {
        // Path: no ships in fleet -> empty result
        List<IShip> result = fleet.getShipsLike("Galeao");

        assertEquals(0, result.size(),
                "Error: expected empty list from getShipsLike when fleet has no ships but got size " + result.size());
    }

    @Test
    void getShipsLike2() {
        // Path: loop iterates but no ship matches category
        fleet.getShips().add(createShipInsideBoard("Fragata", true, false));

        List<IShip> result = fleet.getShipsLike("Galeao");

        assertEquals(0, result.size(),
                "Error: expected empty list from getShipsLike when no ship matches category but got size " + result.size());
    }

    @Test
    void getShipsLike3() {
        // Path: loop iterates, some ships match category and some do not
        IShip galeao1 = createShipInsideBoard("Galeao", true, false);
        IShip galeao2 = createShipInsideBoard("Galeao", false, false);
        IShip fragata = createShipInsideBoard("Fragata", true, false);
        fleet.getShips().add(galeao1);
        fleet.getShips().add(galeao2);
        fleet.getShips().add(fragata);

        List<IShip> result = fleet.getShipsLike("Galeao");

        assertAll(
                () -> assertEquals(2, result.size(),
                        "Error: expected 2 Galeao ships from getShipsLike but got " + result.size()),
                () -> assertTrue(result.contains(galeao1),
                        "Error: expected result to contain first Galeao ship but it was missing"),
                () -> assertTrue(result.contains(galeao2),
                        "Error: expected result to contain second Galeao ship but it was missing")
        );
    }

    // -------------------------------------------------------------------------
    // getFloatingShips (CC = 3) -> getFloatingShips1(), getFloatingShips2(), getFloatingShips3()
    // -------------------------------------------------------------------------

    @Test
    void getFloatingShips1() {
        // Path: no ships -> empty list
        List<IShip> result = fleet.getFloatingShips();

        assertEquals(0, result.size(),
                "Error: expected empty list of floating ships when fleet is empty but got size " + result.size());
    }

    @Test
    void getFloatingShips2() {
        // Path: ships exist but none still floating
        fleet.getShips().add(createShipInsideBoard("Galeao", false, false));
        fleet.getShips().add(createShipInsideBoard("Fragata", false, false));

        List<IShip> result = fleet.getFloatingShips();

        assertEquals(0, result.size(),
                "Error: expected empty list of floating ships when none are floating but got size " + result.size());
    }

    @Test
    void getFloatingShips3() {
        // Path: mix of floating and sunk ships
        IShip floating1 = createShipInsideBoard("Galeao", true, false);
        IShip sunk1 = createShipInsideBoard("Galeao", false, false);
        IShip floating2 = createShipInsideBoard("Fragata", true, false);
        fleet.getShips().add(floating1);
        fleet.getShips().add(sunk1);
        fleet.getShips().add(floating2);

        List<IShip> result = fleet.getFloatingShips();

        assertAll(
                () -> assertEquals(2, result.size(),
                        "Error: expected 2 floating ships but got " + result.size()),
                () -> assertTrue(result.contains(floating1),
                        "Error: expected floating ship 1 in result but it was missing"),
                () -> assertTrue(result.contains(floating2),
                        "Error: expected floating ship 2 in result but it was missing")
        );
    }

    // -------------------------------------------------------------------------
    // shipAt (CC = 3) -> shipAt1(), shipAt2(), shipAt3()
    // -------------------------------------------------------------------------

    @Test
    void shipAt1() {
        // Path: no ships, loop not executed -> null
        IShip result = fleet.shipAt(new TestPosition());

        assertNull(result,
                "Error: expected shipAt to return null when fleet has no ships but got non-null");
    }

    @Test
    void shipAt2() {
        // Path: ships exist but none occupies the position -> null
        IPosition pos = new TestPosition();
        IShip ship = createShipInsideBoard("Galeao", true, false);
        fleet.getShips().add(ship); // ship does not have 'pos' in its occupiedPositions

        IShip result = fleet.shipAt(pos);

        assertNull(result,
                "Error: expected shipAt to return null when no ship occupies position but got non-null");
    }

    @Test
    void shipAt3() {
        // Path: at least one ship occupies the position -> that ship returned
        IPosition pos = new TestPosition();
        TestShip occupyingShip = new TestShip(
                "Galeao",
                true,
                0,
                IFleet.BOARD_SIZE - 1,
                0,
                IFleet.BOARD_SIZE - 1,
                false,
                Collections.singletonList(pos)
        );
        fleet.getShips().add(occupyingShip);

        IShip result = fleet.shipAt(pos);

        assertSame(occupyingShip, result,
                "Error: expected shipAt to return occupying ship but got a different instance or null");
    }

    // -------------------------------------------------------------------------
    // isInsideBoard (private, CC = 4) -> isInsideBoard1()..isInsideBoard4()
    // -------------------------------------------------------------------------

    @Test
    void isInsideBoard1() {
        // All bounds inside board -> true
        int size = IFleet.BOARD_SIZE;
        TestShip ship = createShipWithBounds("Test", true, 0, size - 1, 0, size - 1, false);

        boolean inside = invokeIsInsideBoard(ship);

        assertTrue(inside,
                "Error: expected isInsideBoard to return true when all bounds are within board but got false");
    }

    @Test
    void isInsideBoard2() {
        // Left bound < 0 -> false
        int size = IFleet.BOARD_SIZE;
        TestShip ship = createShipWithBounds("Test", true, -1, size - 1, 0, size - 1, false);

        boolean inside = invokeIsInsideBoard(ship);

        assertFalse(inside,
                "Error: expected isInsideBoard to return false when leftMost < 0 but got true");
    }

    @Test
    void isInsideBoard3() {
        // Right bound > BOARD_SIZE - 1 -> false
        int size = IFleet.BOARD_SIZE;
        TestShip ship = createShipWithBounds("Test", true, 0, size, 0, size - 1, false);

        boolean inside = invokeIsInsideBoard(ship);

        assertFalse(inside,
                "Error: expected isInsideBoard to return false when rightMost > BOARD_SIZE - 1 but got true");
    }

    @Test
    void isInsideBoard4() {
        // Bottom bound > BOARD_SIZE - 1 (with others inside) -> false
        int size = IFleet.BOARD_SIZE;
        TestShip ship = createShipWithBounds("Test", true, 0, size - 1, 0, size, false);

        boolean inside = invokeIsInsideBoard(ship);

        assertFalse(inside,
                "Error: expected isInsideBoard to return false when bottomMost > BOARD_SIZE - 1 but got true");
    }

    @Test
    void isInsideBoard5() {
        // topMost < 0 while others valid -> topMost condition false
        int size = IFleet.BOARD_SIZE;
        TestShip ship = createShipWithBounds(
                "Test",
                true,
                0,          // leftMost >= 0
                size - 1,   // rightMost <= BOARD_SIZE - 1
                -1,         // topMost < 0  (missing branch)
                size - 1,   // bottomMost <= BOARD_SIZE - 1
                false
        );

        boolean inside = invokeIsInsideBoard(ship);

        assertFalse(inside,
                "Error: expected isInsideBoard to return false when topMost < 0 but got true");
    }


    // -------------------------------------------------------------------------
    // colisionRisk (private, CC = 3) -> colisionRisk1()..colisionRisk3()
    // -------------------------------------------------------------------------

    @Test
    void colisionRisk1() {
        // No existing ships -> false
        TestShip newShip = createShipInsideBoard("Test", true, false);

        boolean risk = invokeColisionRisk(newShip);

        assertFalse(risk,
                "Error: expected colisionRisk to return false when fleet has no ships but got true");
    }

    @Test
    void colisionRisk2() {
        // Existing ships, none too close -> false
        TestShip existing1 = createShipInsideBoard("Test", true, false);
        TestShip existing2 = createShipInsideBoard("Test", true, false);
        fleet.getShips().add(existing1);
        fleet.getShips().add(existing2);
        TestShip newShip = createShipInsideBoard("Test", true, false);

        boolean risk = invokeColisionRisk(newShip);

        assertFalse(risk,
                "Error: expected colisionRisk to return false when no ship is tooCloseTo new ship but got true");
    }

    @Test
    void colisionRisk3() {
        // At least one ship tooCloseTo -> true
        TestShip safeShip = createShipInsideBoard("Safe", true, false);
        TestShip riskyShip = createShipInsideBoard("Risk", true, true);
        fleet.getShips().add(safeShip);
        fleet.getShips().add(riskyShip);
        TestShip newShip = createShipInsideBoard("Test", true, false);

        boolean risk = invokeColisionRisk(newShip);

        assertTrue(risk,
                "Error: expected colisionRisk to return true when a ship is tooCloseTo new ship but got false");
    }

    // -------------------------------------------------------------------------
    // printStatus (CC = 1)
    // -------------------------------------------------------------------------

    @Test
    void printStatus() {
        String output = captureStdOut(() -> fleet.printStatus());

        assertNotNull(output,
                "Error: expected non-null output from printStatus but got null");
    }

    // -------------------------------------------------------------------------
    // printShipsByCategory (CC = 2) -> printShipsByCategory1(), printShipsByCategory2()
    // -------------------------------------------------------------------------

    @Test
    void printShipsByCategory1() {
        // Normal path: valid category string
        fleet.getShips().add(createShipInsideBoard("Galeao", true, false));

        String output = captureStdOut(() -> fleet.printShipsByCategory("Galeao"));

        assertFalse(output.isEmpty(),
                "Error: expected some console output when printing ships by existing category but got empty string");
    }

    @Test
    void printShipsByCategory2() {
        // Exceptional path: null category should trigger AssertionError (with assertions enabled)
        assertThrows(AssertionError.class,
                () -> fleet.printShipsByCategory(null),
                "Error: expected AssertionError when category is null but no exception was thrown");
    }

    // -------------------------------------------------------------------------
    // printFloatingShips (CC = 1)
    // -------------------------------------------------------------------------

    @Test
    void printFloatingShips() {
        fleet.getShips().add(createShipInsideBoard("Galeao", true, false));
        fleet.getShips().add(createShipInsideBoard("Galeao", false, false));

        String output = captureStdOut(() -> fleet.printFloatingShips());

        assertFalse(output.isEmpty(),
                "Error: expected console output when printing floating ships but got empty string");
    }

    // -------------------------------------------------------------------------
    // printAllShips (CC = 1)
    // -------------------------------------------------------------------------

    @Test
    void printAllShips() {
        fleet.getShips().add(createShipInsideBoard("Galeao", true, false));
        fleet.getShips().add(createShipInsideBoard("Fragata", true, false));

        String output = captureStdOut(fleet::printAllShips);

        assertFalse(output.isEmpty(),
                "Error: expected console output when printing all ships but got empty string");
    }

    // -------------------------------------------------------------------------
    // static printShips (CC = 2) -> printShips1(), printShips2()
    // -------------------------------------------------------------------------

    @Test
    void printShips1() {
        // Path: empty list -> nothing printed
        List<IShip> empty = Collections.emptyList();

        String output = captureStdOut(() -> Fleet.printShips(empty));

        assertEquals("", output,
                "Error: expected no output when printing empty list of ships but got: " + output);
    }

    @Test
    void printShips2() {
        // Path: list with ships -> each ship printed
        List<IShip> list = new ArrayList<>();
        list.add(createShipInsideBoard("Galeao", true, false));
        list.add(createShipInsideBoard("Fragata", false, false));

        String output = captureStdOut(() -> Fleet.printShips(list));

        assertFalse(output.isEmpty(),
                "Error: expected some output when printing non-empty list of ships but got empty string");
    }
}
