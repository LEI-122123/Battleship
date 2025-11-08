/**
 * **Representa o Jogo (Game) de Batalha Naval.**
 * Esta classe é responsável por gerir o estado do jogo, registar os tiros
 * (shots) efetuados, verificar acertos (hits) e afundamentos (sinks),
 * e acompanhar as estatísticas do jogo (tiros inválidos, repetidos, etc.).
 * Implementa a interface {@code IGame}.
 *
 * @author fba
 * @version 1.0
 */
package iscteiul.ista.battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fba
 */
public class Game implements IGame {
    /** A frota de navios associada a este jogo. */
    private IFleet fleet;
    /** Lista das posições onde foram efetuados tiros válidos e não repetidos. */
    private List<IPosition> shots;

    /** Contador de tiros que caíram fora do tabuleiro (inválidos). */
    private Integer countInvalidShots;
    /** Contador de tiros que caíram em posições já atingidas (repetidos). */
    private Integer countRepeatedShots;
    /** Contador de tiros que acertaram efetivamente um navio (hits). */
    private Integer countHits;
    /** Contador de navios que foram afundados (sinks). */
    private Integer countSinks;


    /**
     * **Cria uma nova instância do Jogo (Game).**
     * Inicializa o jogo com uma frota de navios e define os contadores de estatísticas a zero.
     *
     * @param fleet A frota ({@code IFleet}) a ser utilizada no jogo.
     */
    public Game(IFleet fleet) {
        shots = new ArrayList<>();
        countInvalidShots = 0;
        countRepeatedShots = 0;
        countHits = 0; // Inicializar contadores que faltavam
        countSinks = 0;
        this.fleet = fleet;
    }

    /**
     * **Executa um tiro numa determinada posição do tabuleiro.**
     * Atualiza as estatísticas do jogo.
     * Se o tiro for válido e não repetido, verifica se atinge um navio.
     *
     * @param pos A posição ({@code IPosition}) onde o tiro é efetuado.
     * @return O navio ({@code IShip}) que foi afundado por este tiro, ou {@code null} se o navio não foi afundado
     * ou se não houve acerto (miss).
     */
    @Override
    public IShip fire(IPosition pos) {
        if (!validShot(pos))
            countInvalidShots++;
        else { // valid shot!
            if (repeatedShot(pos))
                countRepeatedShots++;
            else {
                shots.add(pos);
                IShip s = fleet.shipAt(pos);
                if (s != null) {
                    s.shoot(pos);
                    countHits++;
                    if (!s.stillFloating()) {
                        countSinks++;
                        return s;
                    }
                }
            }
        }
        return null;
    }

    /**
     * **Obtém a lista de todas as posições onde foram efetuados tiros válidos e não repetidos.**
     *
     * @return A lista ({@code List<IPosition>}) dos tiros efetuados.
     */
    @Override
    public List<IPosition> getShots() {
        return shots;
    }

    /**
     * **Obtém o número total de tiros repetidos efetuados.**
     *
     * @return O número de tiros repetidos.
     */
    @Override
    public int getRepeatedShots() {
        return this.countRepeatedShots;
    }

    /**
     * **Obtém o número total de tiros inválidos (fora do tabuleiro) efetuados.**
     *
     * @return O número de tiros inválidos.
     */
    @Override
    public int getInvalidShots() {
        return this.countInvalidShots;
    }

    /**
     * **Obtém o número total de acertos (hits) efetuados em navios.**
     *
     * @return O número de acertos.
     */
    @Override
    public int getHits() {
        return this.countHits;
    }

    /**
     * **Obtém o número total de navios que foram afundados (sunk ships).**
     *
     * @return O número de navios afundados.
     */
    @Override
    public int getSunkShips() {
        return this.countSinks;
    }

    /**
     * **Obtém o número de navios que ainda estão a flutuar (não afundados).**
     *
     * @return O número de navios restantes.
     */
    @Override
    public int getRemainingShips() {
        List<IShip> floatingShips = fleet.getFloatingShips();
        return floatingShips.size();
    }

    /**
     * **Verifica se uma determinada posição de tiro está dentro dos limites do tabuleiro.**
     * Assume-se que {@code Fleet.BOARD_SIZE} define o tamanho do tabuleiro.
     *
     * @param pos A posição ({@code IPosition}) a ser verificada.
     * @return {@code true} se o tiro for válido (dentro do tabuleiro); {@code false} caso contrário.
     */
    private boolean validShot(IPosition pos) {
        return (pos.getRow() >= 0 && pos.getRow() <= Fleet.BOARD_SIZE && pos.getColumn() >= 0
                && pos.getColumn() <= Fleet.BOARD_SIZE);
    }

    /**
     * **Verifica se um tiro numa determinada posição já foi efetuado anteriormente.**
     *
     * @param pos A posição ({@code IPosition}) a ser verificada.
     * @return {@code true} se o tiro for repetido; {@code false} caso contrário.
     */
    private boolean repeatedShot(IPosition pos) {
        for (int i = 0; i < shots.size(); i++)
            if (shots.get(i).equals(pos))
                return true;
        return false;
    }

    /**
     * **Imprime no console a representação visual do tabuleiro com posições marcadas.**
     * O tabuleiro é construído com base numa lista de posições e um marcador específico.
     *
     * @param positions A lista de posições (tiros, navios, etc.) a marcar no tabuleiro.
     * @param marker O caractere usado para marcar as posições (ex: 'X', '#').
     */
    public void printBoard(List<IPosition> positions, Character marker) {
        char[][] map = new char[Fleet.BOARD_SIZE][Fleet.BOARD_SIZE];

        for (int r = 0; r < Fleet.BOARD_SIZE; r++)
            for (int c = 0; c < Fleet.BOARD_SIZE; c++)
                map[r][c] = '.'; // Inicializa o tabuleiro com '.'

        for (IPosition pos : positions)
            map[pos.getRow()][pos.getColumn()] = marker; // Marca as posições fornecidas

        for (int row = 0; row < Fleet.BOARD_SIZE; row++) {
            for (int col = 0; col < Fleet.BOARD_SIZE; col++)
                System.out.print(map[row][col]);
            System.out.println(); // Nova linha no final de cada linha do tabuleiro
        }

    }


    /**
     * **Imprime no console o tabuleiro mostrando apenas os tiros válidos que foram efetuados.**
     * Os tiros são marcados com 'X'.
     */
    public void printValidShots() {
        printBoard(getShots(), 'X');
    }


    /**
     * **Imprime no console o tabuleiro mostrando a posição atual de todos os navios da frota.**
     * Os navios são marcados com '#'.
     */
    public void printFleet() {
        List<IPosition> shipPositions = new ArrayList<IPosition>();

        for (IShip s : fleet.getShips())
            shipPositions.addAll(s.getPositions());

        printBoard(shipPositions, '#');
    }

}