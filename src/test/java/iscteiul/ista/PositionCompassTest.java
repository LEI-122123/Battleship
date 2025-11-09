package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static iscteiul.ista.battleship.Compass.*;
import static org.junit.jupiter.api.Assertions.*;

public class PositionCompassTest
{
    private Position pos1;
    private Position pos2;

    @BeforeEach
    void setUp()
    {
        pos1 = new Position( 0, 0 );
        pos2 = new Position( 0, 1 );
    }

    @AfterEach
    void tearDown()
    {
        //
    }

    //  Compass Class Tests

    @Test
    void testCompassGetDirection() {
        assertEquals('n', Compass.NORTH.getDirection());
        assertEquals('s', Compass.SOUTH.getDirection());
        assertEquals('e', Compass.EAST.getDirection());
        assertEquals('o', Compass.WEST.getDirection());
        assertEquals('u', Compass.UNKNOWN.getDirection());
    }

    @Test
    void testCompassToString() {
        assertEquals("n", Compass.NORTH.toString());
        assertEquals("s", Compass.SOUTH.toString());
        assertEquals("e", Compass.EAST.toString());
        assertEquals("o", Compass.WEST.toString());
        assertEquals("u", Compass.UNKNOWN.toString());
    }

    @ParameterizedTest
    @ValueSource(chars = {'n', 's', 'e', 'o', 'u', 'z', 'a', 'b'})
    void TestCharToCompass(char ch)
    {
        Compass expected = switch (ch) {
            case 'n' -> Compass.NORTH;
            case 's' -> Compass.SOUTH;
            case 'e' -> Compass.EAST;
            case 'o' -> Compass.WEST;
            default  -> Compass.UNKNOWN;
        };
        assertEquals(expected, Compass.charToCompass(ch));
    }

    //  Position Class Tests

    @DisplayName( "Dummy shoot itself test" )
    @Test
    @Disabled
    void testShoot()
    {
        //ship.shoot( pos1 );
        assertTrue( pos1.isHit() );
        assertFalse( pos2.isHit() );
    }
}
