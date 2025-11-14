package iscteiul.ista.battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Test class for class Frigate.
 * Author: [user.name]
 * Date: 2025-11-14 21:00
 *
 * Cyclomatic Complexity:
 * - constructor: 5 (Cobre 5 caminhos: null, NORTH/SOUTH, EAST/WEST, UNKNOWN)
 * - getSize(): 1
 */
@DisplayName("Testes Unitários para a Fragata (Frigate - Tamanho 4)")
public class FrigateTest {

    private Frigate frigate;
    // Posição inicial de referência para testes: Linha 5, Coluna 5
    private final IPosition START_POS = new Position(5, 5);

    /** Configuração antes de cada teste (se necessário). */
    @BeforeEach
    public void setUp() {
        // Inicialização feita nos testes aninhados, conforme o Compass.
    }

    /** Limpeza após cada teste. */
    @AfterEach
    public void tearDown() {
        frigate = null;
    }

    // =========================================================
    // =============== TESTS FOR CONSTRUCTOR (CC = 5) ==========
    // =========================================================

    @Nested
    @DisplayName("Construtor: Frigate(Compass, IPosition) [CC=5]")
    class ConstructorTests {

        /**
         * Caminho 1/5: Testa o path de bearing nulo.
         * Cobre a condição que verifica se o bearing é nulo (tratado na superclasse Ship),
         * que lança um AssertionError (conforme observado nos logs).
         */
        @Test
        @DisplayName("Frigate1(): Deve lançar AssertionError se bearing for null (caminho 1/5)")
        void frigate1() {
            assertThrows(AssertionError.class,
                    () -> new Frigate(null, START_POS),
                    "Caminho 1/5: Esperava AssertionError para bearing nulo (proveniente de Ship).");
        }

        /**
         * Caminho 2/5: Test NORTH orientation. Cobre o case NORTH/SOUTH. (Vertical)
         * Esperado: (5,5), (6,5), (7,5), (8,5)
         */
        @Test
        @DisplayName("Frigate2(): Criação orientada a NORTH (Verifica posições verticais - caminho 2/5)")
        void frigate2() {
            frigate = new Frigate(Compass.NORTH, START_POS);

            List<IPosition> pos = frigate.getPositions();
            assertAll("Verificação de posições NORTH",
                    () -> assertEquals(4, pos.size(), "Erro: esperado 4 posições."),
                    // R0: (5, 5) -> Posição inicial
                    () -> assertEquals(5, pos.get(0).getRow(), "R0 deve ser 5"),
                    () -> assertEquals(5, pos.get(0).getColumn(), "C0 deve ser 5"),
                    // R3: (8, 5) -> Última posição
                    () -> assertEquals(8, pos.get(3).getRow(), "R3 deve ser 8"),
                    () -> assertEquals(5, pos.get(3).getColumn(), "C3 deve ser 5")
            );
        }

        /**
         * Caminho 3/5: Test SOUTH orientation. Cobre o case NORTH/SOUTH. (Vertical)
         * Esperado: (5,5), (6,5), (7,5), (8,5)
         */
        @Test
        @DisplayName("Frigate3(): Criação orientada a SOUTH (Verifica posições verticais - caminho 3/5)")
        void frigate3() {
            frigate = new Frigate(Compass.SOUTH, START_POS);

            List<IPosition> pos = frigate.getPositions();
            assertAll("Verificação de posições SOUTH",
                    () -> assertEquals(4, pos.size(), "Erro: esperado 4 posições."),
                    // R0: (5, 5) -> Posição inicial
                    () -> assertEquals(5, pos.get(0).getRow(), "R0 deve ser 5"),
                    () -> assertEquals(5, pos.get(0).getColumn(), "C0 deve ser 5"),
                    // R3: (8, 5) -> Última posição
                    () -> assertEquals(8, pos.get(3).getRow(), "R3 deve ser 8")
            );
        }

        /**
         * Caminho 4/5: Test EAST orientation. Cobre o case EAST/WEST. (Horizontal)
         * Esperado: (5,5), (5,6), (5,7), (5,8). Note que a linha (row) é constante.
         */
        @Test
        @DisplayName("Frigate4(): Criação orientada a EAST (Verifica posições horizontais - caminho 4/5)")
        void frigate4() {
            frigate = new Frigate(Compass.EAST, START_POS);

            List<IPosition> pos = frigate.getPositions();
            assertAll("Verificação de posições EAST",
                    () -> assertEquals(4, pos.size(), "Erro: esperado 4 posições."),
                    // R0: (5, 5) -> Posição inicial
                    () -> assertEquals(5, pos.get(0).getRow(), "R0 deve ser 5 (Linha constante)"),
                    () -> assertEquals(5, pos.get(0).getColumn(), "C0 deve ser 5"),
                    // R3: (5, 8) -> Última posição
                    () -> assertEquals(5, pos.get(3).getRow(), "R3 deve ser 5 (Linha constante)"),
                    () -> assertEquals(8, pos.get(3).getColumn(), "C3 deve ser 8")
            );
        }

        /**
         * Caminho 5/5: Test WEST orientation. Cobre o case EAST/WEST. (Horizontal)
         * Esperado: (5,5), (5,6), (5,7), (5,8). Note que a linha (row) é constante.
         */
        @Test
        @DisplayName("Frigate5(): Criação orientada a WEST (Verifica posições horizontais - caminho 5/5)")
        void frigate5() {
            frigate = new Frigate(Compass.WEST, START_POS);

            List<IPosition> pos = frigate.getPositions();
            assertAll("Verificação de posições WEST",
                    () -> assertEquals(4, pos.size(), "Erro: esperado 4 posições."),
                    // R0: (5, 5) -> Posição inicial
                    () -> assertEquals(5, pos.get(0).getRow(), "R0 deve ser 5 (Linha constante)"),
                    () -> assertEquals(5, pos.get(0).getColumn(), "C0 deve ser 5"),
                    // R3: (5, 8) -> Última posição
                    () -> assertEquals(5, pos.get(3).getRow(), "R3 deve ser 5 (Linha constante)"),
                    () -> assertEquals(8, pos.get(3).getColumn(), "C3 deve ser 8")
            );
        }

        /**
         * Caminho Adicional (Default): Test invalid orientation (default case -> exception).
         * Cobre a condição `default` do switch.
         */
        @Test
        @DisplayName("Frigate6(): Deve lançar IllegalArgumentException para Compass.UNKNOWN (caminho default)")
        void frigate6() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Frigate(Compass.UNKNOWN, START_POS),
                    "Caminho Default: Esperava IllegalArgumentException para Compass.UNKNOWN.");
        }
    }

    // =========================================================
    // =================== TESTS FOR getSize() (CC = 1) ========
    // =========================================================

    /**
     * getSize() – CC = 1 (Caminho Único)
     */
    @Test
    @DisplayName("getSize(): Deve retornar 4 (tamanho fixo)")
    public void getSize() {
        frigate = new Frigate(Compass.NORTH, START_POS);
        assertEquals(4, frigate.getSize(),
                "Erro: esperado size 4 mas obteve " + frigate.getSize());
    }
}