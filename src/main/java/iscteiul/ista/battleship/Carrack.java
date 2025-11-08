/**
 * **Representa uma Nau (Carrack)**, um navio de **tamanho 3** no jogo Batalha Naval.
 * A Nau é um navio de porte médio que ocupa três células consecutivas no tabuleiro.
 *
 * @author [Seu Nome/Nome da Equipe, se aplicável]
 * @version 1.0
 */
package iscteiul.ista.battleship;

public class Carrack extends Ship {
    /** O tamanho fixo da Nau, que é 3. */
    private static final Integer SIZE = 3;
    /** O nome oficial da embarcação, "Nau". */
    private static final String NAME = "Nau";

    /**
     * **Cria uma nova instância de Nau (Carrack).**
     * Inicializa a Nau com um nome, orientação e a posição inicial.
     * Calcula e adiciona as três posições que o navio ocupa, dependendo da sua orientação.
     *
     * @param bearing A orientação (Norte, Sul, Leste, Oeste) em que a nau está a flutuar.
     * @param pos     O ponto inicial (canto superior/esquerdo) para posicionar a Nau no tabuleiro.
     * @throws IllegalArgumentException Se a orientação fornecida (bearing) for um valor inválido.
     */
    public Carrack(Compass bearing, IPosition pos) throws IllegalArgumentException {
        super(Carrack.NAME, bearing, pos);
        switch (bearing) {
            case NORTH:
            case SOUTH:
                // Posiciona na vertical, ocupando 3 células (r, r+1, r+2)
                for (int r = 0; r < SIZE; r++)
                    getPositions().add(new Position(pos.getRow() + r, pos.getColumn()));
                break;
            case EAST:
            case WEST:
                // Posiciona na horizontal, ocupando 3 células (c, c+1, c+2)
                for (int c = 0; c < SIZE; c++)
                    getPositions().add(new Position(pos.getRow(), pos.getColumn() + c));
                break;
            default:
                throw new IllegalArgumentException("ERROR! invalid bearing for the carrack");
        }
    }

    /**
     * **Obtém o tamanho da Nau.**
     *
     * @return O tamanho da nau (sempre 3).
     */
    @Override
    public Integer getSize() {
        return Carrack.SIZE;
    }

}