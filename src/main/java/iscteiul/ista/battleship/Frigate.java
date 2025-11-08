/**
 * **Representa uma Fragata (Frigate)**, um navio de **tamanho 4** no jogo Batalha Naval.
 * A Fragata é um dos maiores navios e ocupa quatro células consecutivas no tabuleiro,
 * podendo ser posicionada na vertical ou horizontal.
 *
 * @author [Seu Nome/Nome da Equipe, se aplicável]
 * @version 1.0
 */
package iscteiul.ista.battleship;

public class Frigate extends Ship {
    /** O tamanho fixo da Fragata, que é 4. */
    private static final Integer SIZE = 4;
    /** O nome oficial da embarcação, "Fragata". */
    private static final String NAME = "Fragata";

    /**
     * **Cria uma nova instância de Fragata (Frigate).**
     * Inicializa a Fragata com um nome, orientação e a posição inicial.
     * Calcula e adiciona as quatro posições que o navio ocupa, dependendo da sua orientação.
     *
     * @param bearing A orientação (Norte, Sul, Leste, Oeste) em que a fragata está a flutuar.
     * @param pos     O ponto inicial (canto superior/esquerdo) para posicionar a Fragata no tabuleiro.
     * @throws IllegalArgumentException Se a orientação fornecida (bearing) for um valor inválido.
     */
    public Frigate(Compass bearing, IPosition pos) throws IllegalArgumentException {
        super(Frigate.NAME, bearing, pos);
        switch (bearing) {
            case NORTH:
            case SOUTH:
                // Posiciona na vertical, ocupando 4 células
                for (int r = 0; r < SIZE; r++)
                    getPositions().add(new Position(pos.getRow() + r, pos.getColumn()));
                break;
            case EAST:
            case WEST:
                // Posiciona na horizontal, ocupando 4 células
                for (int c = 0; c < SIZE; c++)
                    getPositions().add(new Position(pos.getRow(), pos.getColumn() + c));
                break;
            default:
                throw new IllegalArgumentException("ERROR! invalid bearing for thr frigate");
        }
    }

    /**
     * **Obtém o tamanho da Fragata.**
     *
     * @return O tamanho da fragata (sempre 4).
     */
    @Override
    public Integer getSize() {
        return Frigate.SIZE;
    }

}