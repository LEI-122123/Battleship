package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static iscteiul.ista.battleship.Compass.*;

class CarrackTest {

    @DisplayName("Construtor: categoria, orientação e posição de referência")
    @Test
    void construtor_defineCategoriaOrientacaoEPosicaoReferencia() {
        Position p = new Position(2, 3);
        Carrack n = new Carrack(NORTH, p);

        assertEquals("Nau", n.getCategory(), "Categoria deve ser 'Nau'");
        assertEquals(NORTH, n.getBearing(), "A orientação deve ser a fornecida");
        assertEquals(p, n.getPosition(), "A posição de referência (do Ship) deve ser a fornecida");
    }

    @DisplayName("Tamanho e posições verticais iguais para NORTH e SOUTH")
    @Test
    void tamanho_e_listaDePosicoes_vertical_igual_para_north_e_south() {
        Position p = new Position(5, 7);
        Carrack nNorth = new Carrack(NORTH, p);
        Carrack nSouth = new Carrack(SOUTH, p);

        // A implementação parece crescer sempre +1 na linha
        assertEquals(3, nNorth.getSize());
        assertEquals(3, nNorth.getPositions().size());
        assertEquals(new Position(5, 7), nNorth.getPositions().get(0));
        assertEquals(new Position(6, 7), nNorth.getPositions().get(1));
        assertEquals(new Position(7, 7), nNorth.getPositions().get(2));

        assertEquals(3, nSouth.getSize());
        assertEquals(3, nSouth.getPositions().size());
        assertEquals(new Position(5, 7), nSouth.getPositions().get(0));
        assertEquals(new Position(6, 7), nSouth.getPositions().get(1));
        assertEquals(new Position(7, 7), nSouth.getPositions().get(2));
    }

    @DisplayName("Tamanho e posições horizontais iguais para EAST e WEST")
    @Test
    void tamanho_e_listaDePosicoes_horizontal_igual_para_east_e_west() {
        Position p = new Position(2, 3);
        Carrack nEast = new Carrack(EAST, p);
        Carrack nWest = new Carrack(WEST, p);

        // A implementação parece crescer sempre +1 na coluna
        assertEquals(3, nEast.getSize());
        assertEquals(3, nEast.getPositions().size());
        assertEquals(new Position(2, 3), nEast.getPositions().get(0));
        assertEquals(new Position(2, 4), nEast.getPositions().get(1));
        assertEquals(new Position(2, 5), nEast.getPositions().get(2));

        assertEquals(3, nWest.getSize());
        assertEquals(3, nWest.getPositions().size());
        assertEquals(new Position(2, 3), nWest.getPositions().get(0));
        assertEquals(new Position(2, 4), nWest.getPositions().get(1));
        assertEquals(new Position(2, 5), nWest.getPositions().get(2));
    }
}
