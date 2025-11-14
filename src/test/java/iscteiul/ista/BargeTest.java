package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import static iscteiul.ista.battleship.Compass.*;

class BargeTest {

    @Test
    @DisplayName("Construtor: define categoria, orientação e posição de referência")
    void construtor_defineCategoriaOrientacaoEPosicaoReferencia() {
        Position p = new Position(4, 2);
        Barge b = new Barge(EAST, p);

        assertEquals("Barca", b.getCategory(), "Categoria deve ser 'Barca'");
        assertEquals(EAST, b.getBearing(), "A orientação deve ser a fornecida");
        assertEquals(p, b.getPosition(), "A posição de referência deve ser a fornecida");
    }

    @Test
    @DisplayName("Tamanho e lista de posições: contém um único elemento igual à posição de referência")
    void tamanho_e_listaDePosicoes_tem_um_unico_elemento_igual_a_posicao_referencia() {
        Position p = new Position(7, 5);
        Barge b = new Barge(NORTH, p); // orientação irrelevante para tamanho 1

        assertEquals(1, b.getSize(), "Barca tem tamanho 1");
        assertNotNull(b.getPositions(), "Lista de posições não deve ser null");
        assertEquals(1, b.getPositions().size(), "Deve conter exatamente 1 posição");
        assertEquals(new Position(7, 5), b.getPositions().get(0),
                "A única posição ocupada deve coincidir com a posição de referência");
    }

    @Test
    @DisplayName("Orientações diferentes não afetam a posição por ser tamanho 1")
    void diferentes_orientacoes_nao_afetam_a_posicao_por_ser_tamanho_1() {
        Position p = new Position(2, 9);

        Barge bN = new Barge(NORTH, p);
        Barge bS = new Barge(SOUTH, p);
        Barge bE = new Barge(EAST, p);
        Barge bW = new Barge(WEST, p);

        assertEquals(p, bN.getPositions().get(0));
        assertEquals(p, bS.getPositions().get(0));
        assertEquals(p, bE.getPositions().get(0));
        assertEquals(p, bW.getPositions().get(0));
    }
}