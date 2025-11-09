package iscteiul.ista;

import static iscteiul.ista.battleship.Compass.*;
import static org.junit.jupiter.api.Assertions.*;

import iscteiul.ista.battleship.*;

import org.junit.jupiter.api.*;

public class ShipTest
{
    static class DummyShip extends Ship
    {
        public DummyShip( String category, Compass bearing, IPosition pos )
        {
            super( category, bearing, pos );
        }

        @Override
        public Integer getSize()
        {
            return 2;
        }

    }

    private DummyShip ship;
    private Position pos1;
    private Position pos2;

    @BeforeEach
    void setUp() {
        pos1 = new Position(0, 0);
        pos2 = new Position(0, 1);
        ship = new DummyShip("dummy", NORTH, pos1);
        ship.getPositions().add(pos1);
        ship.getPositions().add(pos2);
    }

    @AfterEach
    void tearDown()
    {
        ship = null;
        pos1 = null;
        pos2 = null;
    }

    // Abstract class behavior tests

    @DisplayName("Test Ship Null Assertions")
    @Test
    void NullShip() {
        assertNotNull( ship );

        assertThrows(AssertionError.class, () -> {
            new DummyShip("other", null, new Position(0,0));
        });

        assertThrows(AssertionError.class, () -> {
            new DummyShip("other", UNKNOWN, null);
        });
    }

    @DisplayName("Test Ship Category and Bearing")
    @Test
    void testGetCategoryAndBearing() {
        assertEquals("dummy", ship.getCategory());
        assertEquals(NORTH, ship.getBearing());
    }

    @Test
    void testGetPosition() {
        assertEquals(pos1, ship.getPosition());
    }

    @DisplayName("Test Ship stillFloating() before and after shot positions")
    @Test
    void testStillFloating() {
        assertTrue(ship.stillFloating());
        pos1.shoot();
        pos2.shoot();
        assertFalse(ship.stillFloating());
    }

    @DisplayName("Test Ship occupies positions")
    @Test
    void testOccupies()
    {
        assertTrue( ship.occupies( pos1 ) );
        assertTrue( ship.occupies( pos2 ) );
        assertFalse( ship.occupies( new Position( 5, 5 ) ) );
    }

    @DisplayName("Test Ship tooCloseTo() overload methods")
    @Test
    void testTooCloseToPositionOrShip() {
        // Adjacent position
        Position near = new Position(0, 2);
        assertTrue(ship.tooCloseTo(near));

        Position closeTo = new Position(1, 0);

        DummyShip anotherShip = new DummyShip("another", NORTH, closeTo);
        anotherShip.getPositions().add(closeTo);
        assertTrue(ship.tooCloseTo(anotherShip));

    }

    @DisplayName("Check extremes methods")
    @Test
    void testGetExtremes() {
        assertEquals(0, ship.getTopMostPos());
        assertEquals(0, ship.getBottomMostPos());
        assertEquals(0, ship.getLeftMostPos());
        assertEquals(1, ship.getRightMostPos());
    }

    @DisplayName("Dummy shoot itself test")
    @Test
    void testShoot() {
        ship.shoot(pos1);
        assertTrue(pos1.isHit());
        assertFalse(pos2.isHit());
    }

    // Static factory tests

//    @Test
//    void testBuildShipValid() {
//        Ship s = Ship.buildShip("barca", EAST, new Position(0,0));
//        assertNotNull(s);
//        assertEquals("barca", s.getCategory());
//    }
//
//    @Test
//    void testBuildShipInvalid() {
//        Ship s = Ship.buildShip("unknownShipType", UNKNOWN, new Position(0,0));
//        assertNull(s);
//    }

    //  Cannot perform tests of the factory method while it is package-private
}
