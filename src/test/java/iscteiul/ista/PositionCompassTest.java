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

    @ParameterizedTest
    @ValueSource(chars = {'n', 's', 'e', 'o', 'u', 'z', 'a', 'b'})
    void TestCharToCompass(char ch)
    {
        Compass a = charToCompass( ch);
        assertTrue( a.ordinal() <= UNKNOWN.ordinal() );
    }

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
