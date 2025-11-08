/**
 * **Representa uma Caravela (Caravel)**, um navio de **tamanho 2** no jogo Batalha Naval.
 * A Caravela ocupa duas células consecutivas e pode ser posicionada na vertical ou horizontal.
 *
 * @author [Seu Nome/Nome da Equipe, se aplicável]
 * @version 1.0
 */
package iscteiul.ista.battleship;

public class Caravel extends Ship {
    /** O tamanho fixo da Caravela, que é 2. */
    private static final Integer SIZE = 2;
    /** O nome oficial da embarcação. */
    private static final String NAME = "Caravela";

    /**
     * **Cria uma nova instância de Caravela (Caravel).**
     * Inicializa a Caravela com um nome, orientação e a posição inicial.
     * Adiciona as duas posições que o navio ocupa, dependendo da sua orientação (Norte, Sul, Leste, Oeste).
     *
     * @param bearing A orientação (Norte, Sul, Leste, Oeste) em que a caravela está a flutuar.
     * @param pos     O ponto inicial (canto superior/esquerdo) para posicionar a Caravela no tabuleiro.
     * @throws NullPointerException Se a orientação fornecida (bearing) for nula.
     * @throws IllegalArgumentException Se a orientação fornecida for um valor inválido.
     */
    public Caravel(Compass bearing, IPosition pos) throws NullPointerException, IllegalArgumentException {
        super(Caravel.NAME, bearing, pos);

        if (bearing == null)
            throw new NullPointerException("ERROR! invalid bearing for the caravel");

        switch (bearing) {
            case NORTH:
            case SOUTH:
                // Posiciona na vertical
                for (int r = 0; r < SIZE; r++)
                    getPositions().add(new Position(pos.getRow() + r, pos.getColumn()));
                break;
            case EAST:
            case WEST:
                // Posiciona na horizontal
                for (int c = 0; c < SIZE; c++)
                    getPositions().add(new Position(pos.getRow(), pos.getColumn() + c));
                break;
            default:
                throw new IllegalArgumentException("ERROR! invalid bearing for the caravel");
        }

    }

    /**
     * **Obtém o tamanho da Caravela.**
     *
     * @return O tamanho da caravela (sempre 2).
     */
    @Override
    public Integer getSize() {
        return SIZE;
    }

}