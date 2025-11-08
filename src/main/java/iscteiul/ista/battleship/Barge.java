/**
 * **Representa uma Barca (Barge)**, um tipo de navio de **tamanho 1** no jogo Batalha Naval.
 * É a embarcação mais pequena.
 *
 * @author [Seu Nome/Nome da Equipe, se aplicável]
 * @version 1.0
 */
package iscteiul.ista.battleship;

public class Barge extends Ship {
    /** O tamanho fixo da Barca, que é 1. */
    private static final Integer SIZE = 1;
    /** O nome oficial da embarcação. */
    private static final String NAME = "Barca";

    /**
     * **Cria uma nova instância de Barca (Barge).**
     * Inicializa a Barca com um nome, orientação e a posição inicial.
     * Adiciona a única posição que o navio ocupa à lista de posições.
     *
     * @param bearing A orientação (Norte, Sul, Leste, Oeste) em que a barca está a flutuar.
     * @param pos     A posição superior esquerda (canto superior) onde a barca será colocada no tabuleiro.
     */
    public Barge(Compass bearing, IPosition pos) {
        super(Barge.NAME, bearing, pos);
        getPositions().add(new Position(pos.getRow(), pos.getColumn()));
    }

    /**
     * **Obtém o tamanho da Barca.**
     *
     * @return O tamanho da barca (sempre 1).
     */
    @Override
    public Integer getSize() {
        return SIZE;
    }

}