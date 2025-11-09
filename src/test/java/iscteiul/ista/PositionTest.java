package iscteiul.ista;

import iscteiul.ista.battleship.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTest
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
    @DisplayName("Test constructor and getters")
    void testConstructorAndGetters() {
        Position pos = new Position( 2, 3);
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
