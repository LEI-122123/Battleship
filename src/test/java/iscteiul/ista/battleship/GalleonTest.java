package iscteiul.ista.battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class Galleon.
 * Author: [user.name]
 * Date: 2025-11-14 20:53
 * Cyclomatic Complexity:
 * - Galleon(Compass, IPosition): 7
 * - getSize(): 1
 * - fillNorth(IPosition): 1
 * - fillSouth(IPosition): 2
 * - fillEast(IPosition): 1
 * - fillWest(IPosition): 1
 */
@DisplayName("Testes Unitários para o Galeão (Galleon - Tamanho 5)")
public class GalleonTest {

    private Galleon galleon;
    // Posição inicial de referência para testes: Linha 5, Coluna 5
    private final IPosition START_POS = new Position(5, 5);

    @BeforeEach
    void setUp() {
        // Inicialização feita dentro dos testes do construtor, quando necessário.
    }

    @AfterEach
    void tearDown() {
        // Limpa a instância após cada teste.
        galleon = null;
    }

    // =========================================================================
    // CC = 7 para Galleon(Compass bearing, IPosition pos) - Cobertura de Caminhos
    // =========================================================================

    @Nested
    @DisplayName("Construtor: Galleon(Compass, IPosition) [CC=7]")
    class ConstructorTests {

        /**
         * Caminho 1/7: Testa o path de AssertionError (Assumindo que a classe Ship
         * lança AssertionError em vez de NullPointerException quando o bearing é nulo,
         * o que é inferido pelo log de erro do utilizador).
         * Cobre a condição `if (bearing == null)` ser VERDADEIRA na superclasse.
         */
        @Test
        @DisplayName("Galleon1(): Deve lançar AssertionError se bearing for null (comportamento Ship)")
        void galleon1() {
            assertThrows(AssertionError.class, () -> {
                new Galleon(null, START_POS);
            }, "Caminho 1/7: Esperava AssertionError (de Ship) se o bearing fosse nulo.");
        }

        /**
         * Caminho 2/7: Testa o path do case NORTH.
         * Cobre a condição `if (bearing == null)` ser FALSA + `switch` para NORTH.
         */
        @Test
        @DisplayName("Galleon2(): Criação orientada a NORTH (Verifica fillNorth)")
        void galleon2() {
            galleon = new Galleon(Compass.NORTH, START_POS);
            assertEquals(5, galleon.getPositions().size(), "Caminho 2/7 (NORTH): O Galeão deve ter 5 posições após inicialização.");
        }

        /**
         * Caminho 3/7: Testa o path do case EAST.
         * Cobre a condição `if (bearing == null)` ser FALSA + `switch` para EAST.
         */
        @Test
        @DisplayName("Galleon3(): Criação orientada a EAST (Verifica fillEast)")
        void galleon3() {
            galleon = new Galleon(Compass.EAST, START_POS);
            assertEquals(5, galleon.getPositions().size(), "Caminho 3/7 (EAST): O Galeão deve ter 5 posições após inicialização.");
        }

        /**
         * Caminho 4/7: Testa o path do case SOUTH.
         * Cobre a condição `if (bearing == null)` ser FALSA + `switch` para SOUTH.
         */
        @Test
        @DisplayName("Galleon4(): Criação orientada a SOUTH (Verifica fillSouth)")
        void galleon4() {
            galleon = new Galleon(Compass.SOUTH, START_POS);
            assertEquals(5, galleon.getPositions().size(), "Caminho 4/7 (SOUTH): O Galeão deve ter 5 posições após inicialização.");
        }

        /**
         * Caminho 5/7: Testa o path do case WEST.
         * Cobre a condição `if (bearing == null)` ser FALSA + `switch` para WEST.
         */
        @Test
        @DisplayName("Galleon5(): Criação orientada a WEST (Verifica fillWest)")
        void galleon5() {
            galleon = new Galleon(Compass.WEST, START_POS);
            assertEquals(5, galleon.getPositions().size(), "Caminho 5/7 (WEST): O Galeão deve ter 5 posições após inicialização.");
        }

        /**
         * Caminho 6/7: Testa o path do default (IllegalArgumentException).
         * Cobre a condição `if (bearing == null)` ser FALSA + `switch` para default (com Compass.UNKNOWN).
         */
        @Test
        @DisplayName("Galleon6(): Deve lançar IllegalArgumentException para Compass.UNKNOWN")
        void galleon6() {
            assertThrows(IllegalArgumentException.class, () -> {
                new Galleon(Compass.UNKNOWN, START_POS);
            }, "Caminho 6/7 (DEFAULT): Esperava IllegalArgumentException para um bearing UNKNOWN.");
        }

        /**
         * Caminho 7/7: Reafirma um caminho válido, verificando propriedades herdadas.
         * Garante que o construtor base (`super`) é chamado corretamente, verificando
         * o bearing e o tamanho final das posições.
         */
        @Test
        @DisplayName("Galleon7(): Reafirma caminho válido (Verifica Bearing e inicialização)")
        void galleon7() {
            galleon = new Galleon(Compass.EAST, START_POS);
            assertAll("Verificações de Propriedades da Superclasse Ship e Inicialização",
                    () -> assertEquals(Compass.EAST, galleon.getBearing(), "O bearing armazenado deve ser EAST, verificado via método herdado getBearing()."),
                    // Reconfirmamos o tamanho para garantir que a inicialização do super e o fillEast foram bem-sucedidos.
                    () -> assertEquals(5, galleon.getPositions().size(), "O tamanho final das posições deve ser 5 após o construtor.")
            );
        }
    }

    // =========================================================================
    // CC = 1 para getSize() - Caminho Único
    // =========================================================================

    @Test
    @DisplayName("getSize(): Deve retornar 5 (tamanho fixo)")
    void getSize() {
        galleon = new Galleon(Compass.NORTH, START_POS);
        assertEquals(5, galleon.getSize(), "O método getSize() deve retornar 5 para o Galleon.");
    }

    // =========================================================================
    // CC = 1 para fillNorth(IPosition) - Caminho Único
    // =========================================================================

    /**
     * Esperado (5,5): (5,5), (5,6), (5,7), (6,6), (7,6)
     */
    @Test
    @DisplayName("fillNorth(): Posições geradas corretamente para NORTH")
    void fillNorth() {
        galleon = new Galleon(Compass.NORTH, START_POS);
        List<IPosition> positions = galleon.getPositions();

        Set<IPosition> expected = new HashSet<>(Arrays.asList(
                new Position(5, 5), new Position(5, 6), new Position(5, 7), // Linha 5
                new Position(6, 6), // Linha 6
                new Position(7, 6)  // Linha 7
        ));

        assertAll("Verificação de Posições NORTH",
                () -> assertEquals(5, positions.size(), "fillNorth deve gerar exatamente 5 posições."),
                () -> assertEquals(expected, new HashSet<>(positions), "As posições geradas não correspondem ao formato NORTH ('T' invertido).")
        );
    }

    // =========================================================================
    // CC = 2 para fillSouth(IPosition) - Cobertura dos dois loops 'for'
    // =========================================================================

    @Nested
    @DisplayName("fillSouth(IPosition) [CC=2]")
    class FillSouthTests {

        /**
         * Caminho 1/2: Teste principal, verificando o resultado do primeiro `for` e do segundo `for`.
         * Esperado: (5,5), (6,5) [Primeiro loop], (7,4), (7,5), (7,6) [Segundo loop]
         * Cobre a execução de ambos os loops (i < 2 e j < 5).
         */
        @Test
        @DisplayName("fillSouth1(): Verifica a forma completa (Vertical 2 + Horizontal 3 na base)")
        void fillSouth1() {
            galleon = new Galleon(Compass.SOUTH, START_POS);
            List<IPosition> positions = galleon.getPositions();

            Set<IPosition> expectedAll = new HashSet<>(Arrays.asList(
                    new Position(5, 5), new Position(6, 5), // Vertical (C=5)
                    new Position(7, 4), new Position(7, 5), new Position(7, 6) // Horizontal (R=7)
            ));

            assertAll("Verificação de Posições SOUTH",
                    () -> assertEquals(5, positions.size(), "fillSouth deve gerar exatamente 5 posições."),
                    () -> assertEquals(expectedAll, new HashSet<>(positions), "As posições geradas não correspondem ao formato SOUTH.")
            );
        }

        /**
         * Caminho 2/2: Reafirma o formato para garantir a cobertura de `branch` dos loops.
         * Como os loops são sequenciais, a melhor forma de atingir CC=2 é garantir a execução correta de cada bloco de repetição.
         * Aqui focamos na transição e nos valores dos índices.
         */
        @Test
        @DisplayName("fillSouth2(): Posições nos extremos do segundo loop (j=2, j=4)")
        void fillSouth2() {
            galleon = new Galleon(Compass.SOUTH, START_POS);
            List<IPosition> positions = galleon.getPositions();

            // Posição gerada quando j=2: Position(R+2, C + 2-3) = Position(7, 4)
            assertTrue(positions.contains(new Position(7, 4)), "fillSouth não incluiu a posição (7,4), gerada por j=2.");

            // Posição gerada quando j=4: Position(R+2, C + 4-3) = Position(7, 6)
            assertTrue(positions.contains(new Position(7, 6)), "fillSouth não incluiu a posição (7,6), gerada por j=4.");
        }
    }

    // =========================================================================
    // CC = 1 para fillEast(IPosition) - Caminho Único
    // =========================================================================

    /**
     * Esperado (5,5): (5,5), (7,5), (6,3), (6,4), (6,5)
     */
    @Test
    @DisplayName("fillEast(): Posições geradas corretamente para EAST")
    void fillEast() {
        galleon = new Galleon(Compass.EAST, START_POS);
        List<IPosition> positions = galleon.getPositions();

        Set<IPosition> expected = new HashSet<>(Arrays.asList(
                new Position(5, 5), // Ponto 1
                new Position(7, 5), // Ponto 5
                new Position(6, 3), new Position(6, 4), new Position(6, 5) // Pontos gerados pelo loop
        ));

        assertAll("Verificação de Posições EAST",
                () -> assertEquals(5, positions.size(), "fillEast deve gerar exatamente 5 posições."),
                () -> assertEquals(expected, new HashSet<>(positions), "As posições geradas não correspondem ao formato EAST ('H' assimétrico).")
        );
    }

    // =========================================================================
    // CC = 1 para fillWest(IPosition) - Caminho Único
    // =========================================================================

    /**
     * Esperado (5,5): (5,5), (7,5), (6,5), (6,6), (6,7)
     */
    @Test
    @DisplayName("fillWest(): Posições geradas corretamente para WEST")
    void fillWest() {
        galleon = new Galleon(Compass.WEST, START_POS);
        List<IPosition> positions = galleon.getPositions();

        Set<IPosition> expected = new HashSet<>(Arrays.asList(
                new Position(5, 5), // Ponto 1
                new Position(7, 5), // Ponto 5
                new Position(6, 5), new Position(6, 6), new Position(6, 7) // Pontos gerados pelo loop
        ));

        assertAll("Verificação de Posições WEST",
                () -> assertEquals(5, positions.size(), "fillWest deve gerar exatamente 5 posições."),
                () -> assertEquals(expected, new HashSet<>(positions), "As posições geradas não correspondem ao formato WEST ('T' para a direita).")
        );
    }
}