package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import static iscteiul.ista.battleship.Compass.*;

class CaravelTest {

    @Test
    void checkAssertions() {
        boolean enabled = false;
        assert enabled = true;

        System.out.println("Assertions enabled? " + enabled);
    }

    @Test
    @DisplayName("Construtor define categoria, orientação e posição de referência")
    void construtor_defineCategoriaOrientacaoEPosicaoReferencia() {
        Position p = new Position(3, 4);
        Caravel c = new Caravel(WEST, p);

        NullPointerException ex1 = assertThrows(NullPointerException.class,() -> new Caravel(null, p));
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class,() -> new Caravel(UNKNOWN, p));

        assertEquals("ERROR! invalid bearing for the caravel", ex1.getMessage());
        assertEquals("ERROR! invalid bearing for the caravel", ex2.getMessage());

        assertEquals("Caravela", c.getCategory(), "Categoria deve ser 'Caravela'");
        assertEquals(WEST, c.getBearing(), "A orientação deve ser a fornecida");
        assertEquals(p, c.getPosition(), "A posição de referência (do Ship) deve ser a fornecida");
    }

    @Test
    @DisplayName("Tamanho e posições verticais iguais para NORTH e SOUTH")
    void tamanho_e_listaDePosicoes_vertical_eh_semelhante_para_north_e_south() {
        Position p = new Position(5, 7);
        Caravel cNorth = new Caravel(NORTH, p);
        Caravel cSouth = new Caravel(SOUTH, p);

        // Pelo teu código, tanto NORTH como SOUTH dão (linha, col) e (linha+1, col)
        assertEquals(2, cNorth.getSize());
        assertEquals(2, cNorth.getPositions().size());
        assertEquals(new Position(5, 7), cNorth.getPositions().get(0));
        assertEquals(new Position(6, 7), cNorth.getPositions().get(1));

        assertEquals(2, cSouth.getSize());
        assertEquals(2, cSouth.getPositions().size());
        assertEquals(new Position(5, 7), cSouth.getPositions().get(0));
        assertEquals(new Position(6, 7), cSouth.getPositions().get(1));
    }

    @Test
    @DisplayName("Tamanho e posições horizontais iguais para EAST e WEST")
    void tamanho_e_listaDePosicoes_horizontal_eh_semelhante_para_east_e_west() {
        Position p = new Position(2, 3);
        Caravel cEast = new Caravel(EAST, p);
        Caravel cWest = new Caravel(WEST, p);

        // Pelo teu código, tanto EAST como WEST dão (linha, col) e (linha, col+1)
        assertEquals(2, cEast.getSize());
        assertEquals(2, cEast.getPositions().size());
        assertEquals(new Position(2, 3), cEast.getPositions().get(0));
        assertEquals(new Position(2, 4), cEast.getPositions().get(1));

        assertEquals(2, cWest.getSize());
        assertEquals(2, cWest.getPositions().size());
        assertEquals(new Position(2, 3), cWest.getPositions().get(0));
        assertEquals(new Position(2, 4), cWest.getPositions().get(1));
    }
}
