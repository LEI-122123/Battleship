package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

public class PositionCompassTest
{
//    private Position pos1;
//    private Position pos2;

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

    @Test
    @DisplayName("Test constructor and getters")
    void testConstructorAndGetters() {
        Position pos = new Position(2, 3);
        assertEquals(2, pos.getRow(), "Row should be 2");
        assertEquals(3, pos.getColumn(), "Column should be 3");
        assertFalse(pos.isOccupied(), "Position should not be occupied initially");
        assertFalse(pos.isHit(), "Position should not be hit initially");
    }

    @Test
    @DisplayName("Test occupy and shoot methods")
    void testOccupyAndShoot() {
        Position pos = new Position(1, 1);
        pos.occupy();
        assertTrue(pos.isOccupied(), "Position should be occupied after occupy()");
        pos.shoot();
        assertTrue(pos.isHit(), "Position should be hit after shoot()");
    }

    @ParameterizedTest(name = "Position ({0},{1}) is adjacent to ({2},{3}) -> {4}")
    @CsvSource({
            "0,0,0,1,true",
            "0,0,1,1,true",
            "0,0,1,0,true",
            "0,0,2,2,false",
            "3,3,4,5,false"
    })
    @DisplayName("Test adjacency between positions")
    void testIsAdjacentTo(int row1, int col1, int row2, int col2, boolean expected) {
        Position pos1 = new Position(row1, col1);
        Position pos2 = new Position(row2, col2);
        assertEquals(expected, pos1.isAdjacentTo(pos2));
    }

    @Test
    @DisplayName("Test equals and hashCode")
    void testEqualsAndHashCode() {
        Position pos1 = new Position(2, 3);
        Position pos2 = new Position(2, 3);
        Position pos3 = new Position(3, 2);

        // equals
        assertEquals(pos1, pos2, "Positions with same row and column should be equal");
        assertNotEquals(pos1, pos3, "Positions with different row/column should not be equal");

        // hashCode
        assertEquals(pos1.hashCode(), pos2.hashCode(), "Hashcodes should match for equal positions");
    }

    @Test
    @DisplayName("Test Position \"toString\" method")
    void testPositionToString() {
        Position pos = new Position(5, 7);
        assertEquals("Linha = 5 Coluna = 7", pos.toString());
    }

}
