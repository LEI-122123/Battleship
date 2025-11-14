package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

public class CompassTest
{
    @BeforeEach
    void setUp()
    {
        //
    }

    @AfterEach
    void tearDown()
    {
        //
    }

    @Test
    @DisplayName( "test GetDirection() valid inputs" )
    void testCompassGetDirection() {
        assertEquals('n', Compass.NORTH.getDirection());
        assertEquals('s', Compass.SOUTH.getDirection());
        assertEquals('e', Compass.EAST.getDirection());
        assertEquals('o', Compass.WEST.getDirection());
        assertEquals('u', Compass.UNKNOWN.getDirection());
    }

    @Test
    @DisplayName( "test toString() expected outputs" )
    void testCompassToString() {
        assertEquals("n", Compass.NORTH.toString());
        assertEquals("s", Compass.SOUTH.toString());
        assertEquals("e", Compass.EAST.toString());
        assertEquals("o", Compass.WEST.toString());
        assertEquals("u", Compass.UNKNOWN.toString());
    }

    @ParameterizedTest
    @ValueSource(chars = {'n', 's', 'e', 'o', 'u', 'z', 'a', 'b'})
    @DisplayName( "test charToCompass() expected outputs" )
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
}
