/**
 * **Representa um Galeão (Galleon)**, o maior navio de **tamanho 5** no jogo Batalha Naval.
 * O Galeão tem um formato não-linear (complexo/em forma de 'T' ou 'cruz') e a sua
 * posição no tabuleiro depende diretamente dos métodos auxiliares de preenchimento
 * para cada direção cardeal.
 *
 * @author [Seu Nome/Nome da Equipe, se aplicável]
 * @version 1.0
 */
package iscteiul.ista.battleship;

public class Galleon extends Ship {
    /** O tamanho fixo do Galeão, que é 5. */
    private static final Integer SIZE = 5;
    /** O nome oficial da embarcação, "Galeao". */
    private static final String NAME = "Galeao";

    /**
     * **Cria uma nova instância de Galeão (Galleon).**
     * Inicializa o Galeão com um nome, orientação e a posição inicial.
     * Chama o método auxiliar apropriado ({@code fillNorth}, {@code fillSouth}, etc.)
     * para definir as cinco posições que o navio ocupa, dependendo da orientação.
     *
     * @param bearing A orientação (Norte, Sul, Leste, Oeste) em que o galeão está a flutuar.
     * @param pos     O ponto inicial (canto superior/esquerdo) para posicionar o Galeão no tabuleiro.
     * @throws NullPointerException Se a orientação fornecida (bearing) for nula.
     * @throws IllegalArgumentException Se a orientação fornecida for um valor inválido.
     */
    public Galleon(Compass bearing, IPosition pos) throws IllegalArgumentException {
        super(Galleon.NAME, bearing, pos);

        if (bearing == null)
            throw new NullPointerException("ERROR! invalid bearing for the galleon");

        switch (bearing) {
            case NORTH:
                fillNorth(pos);
                break;
            case EAST:
                fillEast(pos);
                break;
            case SOUTH:
                fillSouth(pos);
                break;
            case WEST:
                fillWest(pos);
                break;

            default:
                throw new IllegalArgumentException("ERROR! invalid bearing for the galleon");
        }
    }

    /**
     * **Obtém o tamanho do Galeão.**
     *
     * @return O tamanho do galeão (sempre 5).
     */
    @Override
    public Integer getSize() {
        return Galleon.SIZE;
    }

    /**
     * **Define as cinco posições que o Galeão ocupa, orientadas para Norte.**
     * O formato é tipicamente uma linha horizontal superior de 3 células, com 2 células
     * centradas verticalmente abaixo dela.
     *
     * @param pos A posição de referência (superior esquerda) do Galeão.
     */
    private void fillNorth(IPosition pos) {
        for (int i = 0; i < 3; i++) {
            getPositions().add(new Position(pos.getRow(), pos.getColumn() + i));
        }
        getPositions().add(new Position(pos.getRow() + 1, pos.getColumn() + 1));
        getPositions().add(new Position(pos.getRow() + 2, pos.getColumn() + 1));
    }

    /**
     * **Define as cinco posições que o Galeão ocupa, orientadas para Sul.**
     * O formato preenchido pode ser uma linha vertical (2 células) e uma linha horizontal
     * (3 células) conectadas na base.
     *
     * @param pos A posição de referência (superior esquerda) do Galeão.
     */
    private void fillSouth(IPosition pos) {
        for (int i = 0; i < 2; i++) {
            getPositions().add(new Position(pos.getRow() + i, pos.getColumn()));
        }
        for (int j = 2; j < 5; j++) {
            getPositions().add(new Position(pos.getRow() + 2, pos.getColumn() + j - 3));
        }
    }

    /**
     * **Define as cinco posições que o Galeão ocupa, orientadas para Este (Leste).**
     *
     * @param pos A posição de referência (superior esquerda) do Galeão.
     */
    private void fillEast(IPosition pos) {
        getPositions().add(new Position(pos.getRow(), pos.getColumn()));
        for (int i = 1; i < 4; i++) {
            getPositions().add(new Position(pos.getRow() + 1, pos.getColumn() + i - 3));
        }
        getPositions().add(new Position(pos.getRow() + 2, pos.getColumn()));
    }

    /**
     * **Define as cinco posições que o Galeão ocupa, orientadas para Oeste.**
     *
     * @param pos A posição de referência (superior esquerda) do Galeão.
     */
    private void fillWest(IPosition pos) {
        getPositions().add(new Position(pos.getRow(), pos.getColumn()));
        for (int i = 1; i < 4; i++) {
            getPositions().add(new Position(pos.getRow() + 1, pos.getColumn() + i - 1));
        }
        getPositions().add(new Position(pos.getRow() + 2, pos.getColumn()));
    }

}