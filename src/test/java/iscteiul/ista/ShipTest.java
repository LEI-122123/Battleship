package iscteiul.ista.battleship;

import static iscteiul.ista.battleship.Compass.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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

    static class SizeOneShip extends Ship
    {
        public SizeOneShip( String category, Compass bearing, IPosition pos )
        {
            super( category, bearing, pos );
        }
        @Override
        public Integer getSize()
        {
            return 1;
        }
    }

    private Ship ship;
    private Position pos1;
    private Position pos2;

    @BeforeEach
    void setUp() {
        pos1 = new Position(0, 1);
        pos2 = new Position(0, 2);
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

//        Ship anonShip = new Ship("Zero Size", NORTH, pos1) {
//            @Override public Integer getSize() { return 0; }
//        };
//        assertFalse(anonShip.stillFloating());

        assertTrue(ship.stillFloating());
        pos1.shoot();
        assertTrue(ship.stillFloating());
        pos2.shoot();
        assertFalse(ship.stillFloating());
    }

    @DisplayName("Test Ship occupies() positions")
    @Test
    void testOccupies()
    {
        //  Assertion test
        assertThrows( AssertionError.class, () ->
        {
            ship.occupies(null);
        });

        //  Size zero, never gets in for-loop
//        Ship anonShip = new Ship("Zero Size", NORTH, pos1) {
//                        @Override public Integer getSize() { return 0; }
//        };
//        assertFalse(anonShip.occupies(pos1));

        assertTrue( ship.occupies( pos1 ) );
        assertTrue( ship.occupies( pos2 ) );
        assertFalse( ship.occupies( new Position( 5, 5 ) ) );
    }

    @DisplayName("Test Ship tooCloseTo() overload methods")
    @Test
    void testTooCloseToPositionOrShip() {

        //  tooCloseTo IPosition

//        This method does not assert null
//        assertThrows( AssertionError.class, () ->
//        {
//            ship.tooCloseTo((IPosition) null);
//        });

        Position near = new Position(0, 0);
        Position notNear = new Position(5, 5);
        assertTrue(ship.tooCloseTo(near));
        assertFalse(ship.tooCloseTo(notNear));

        //  Zero-Sized ship
//        Ship anonShip = new Ship("Zero Size", NORTH, pos1) {
//            @Override public Integer getSize() { return 0; }
//        };
//        assertFalse(anonShip.tooCloseTo(near));

        //  tooCloseTo IShip

        assertThrows( AssertionError.class, () ->
        {
            ship.tooCloseTo((IShip) null);
        });

        Ship nearShip = new DummyShip("another", NORTH, near);
        nearShip.getPositions().add(near);
        assertTrue(ship.tooCloseTo(nearShip));

        Ship notNearShip = new DummyShip("AndAnother", NORTH, notNear);
        notNearShip.getPositions().add(notNear);
        assertFalse(ship.tooCloseTo(notNearShip));

        //  Zero-Sized ship
        //  assertFalse(ship.tooCloseTo(anonShip));
    }

    @DisplayName("Check extremes methods")
    @Test
    void testGetExtremes() {
        assertEquals(0, ship.getTopMostPos());
        assertEquals(0, ship.getBottomMostPos());
        assertEquals(1, ship.getLeftMostPos());
        assertEquals(2, ship.getRightMostPos());

        ship.getPositions().remove(pos1);
        ship.getPositions().remove(pos2);

        ship.getPositions().add(pos2);
        ship.getPositions().add(pos1);

        assertEquals(1, ship.getLeftMostPos());
        assertEquals(2, ship.getRightMostPos());

        //

        Position pos3 = new Position(1, 0);
        Position pos4 = new Position(2, 0);

        Ship otherShip = new DummyShip("another", EAST, pos4);
        otherShip.getPositions().add(otherShip.getPosition());
        otherShip.getPositions().add(pos3);

        assertEquals(1, otherShip.getTopMostPos());
        assertEquals(2, otherShip.getBottomMostPos());
        assertEquals(0, otherShip.getLeftMostPos());
        assertEquals(0, otherShip.getRightMostPos());

        otherShip.getPositions().remove(pos3);
        otherShip.getPositions().remove(pos4);

        otherShip.getPositions().add(pos3);
        otherShip.getPositions().add(pos4);

        assertEquals(1, otherShip.getTopMostPos());
        assertEquals(2, otherShip.getBottomMostPos());

        //  Not hitting for-loop test:
        Ship size1ship = new SizeOneShip("Size One", EAST, new Position(0,0));
        size1ship.getPositions().add(size1ship.getPosition());

        assertEquals(0, size1ship.getTopMostPos());
        assertEquals(0, size1ship.getBottomMostPos());
        assertEquals(0, size1ship.getLeftMostPos());
        assertEquals(0, size1ship.getRightMostPos());

    }

    @DisplayName("Dummy shoots itself test")
    @Test
    void testShoot() {

        assertThrows( AssertionError.class, () ->
        {
            ship.shoot(null);
        });

        assertFalse(pos1.isHit());
        ship.shoot(pos1);
        assertTrue(pos1.isHit());

        assertFalse(pos2.isHit());
        ship.shoot(pos2);
        assertTrue(pos2.isHit());
    }

    // Static factory tests

    @ParameterizedTest
    @CsvSource({
            "barca, Barca",
            "caravela, Caravela",
            "nau, Nau",
            "fragata, Fragata",
            "galeao, Galeao"
    })
    @DisplayName("Test possible Factory method outputs")
    void testBuildShipValidOutputs(String shipkind, String expected)
    {
        Ship s = Ship.buildShip(shipkind, EAST, pos1);
        assertNotNull(s);
        assertEquals(expected, s.getCategory());

        assertEquals("[" + expected + " " + EAST + " " + "Linha = 0 Coluna = 1]", s.toString());

    }

    @Test
    void testBuildShipInvalid() {
        Ship s = Ship.buildShip("unknownShipType", UNKNOWN, new Position(0,0));
        assertNull(s);
    }

}
