/**
 * **Interface que define o contrato para uma Posição (IPosition) no tabuleiro** de Batalha Naval.
 * Representa uma célula individual e o seu estado (ocupada por um navio, atingida por um tiro).
 *
 * @author fba
 * @version 1.0
 */
package iscteiul.ista.battleship;

/**
 * @author fba
 */
public interface IPosition {
    /**
     * **Obtém o índice da linha (Row) desta posição.**
     *
     * @return O valor inteiro da linha.
     */
    int getRow();

    /**
     * **Obtém o índice da coluna (Column) desta posição.**
     *
     * @return O valor inteiro da coluna.
     */
    int getColumn();

    /**
     * **Compara esta posição com outro objeto para verificar a igualdade.**
     * Duas posições são consideradas iguais se tiverem a mesma linha e coluna.
     *
     * @param other O objeto a ser comparado.
     * @return {@code true} se o objeto for uma {@code IPosition} com as mesmas coordenadas; {@code false} caso contrário.
     */
    boolean equals(Object other);

    /**
     * **Verifica se esta posição é adjacente (vizinha) a outra posição.**
     * Adjacência pode ser definida como estar na mesma linha ou coluna e a uma distância de 1.
     *
     * @param other A outra posição ({@code IPosition}) para comparação.
     * @return {@code true} se as posições forem adjacentes; {@code false} caso contrário.
     */
    boolean isAdjacentTo(IPosition other);

    /**
     * **Marca esta posição como ocupada por um navio.**
     *
     * @throws IllegalStateException Se a posição já estiver ocupada.
     */
    void occupy();

    /**
     * **Marca esta posição como atingida por um tiro.**
     * Normalmente é chamado quando um navio é atingido nesta célula.
     *
     * @throws IllegalStateException Se a posição já tiver sido atingida.
     */
    void shoot();

    /**
     * **Verifica se esta posição está ocupada por alguma parte de um navio.**
     *
     * @return {@code true} se a posição estiver ocupada; {@code false} caso contrário.
     */
    boolean isOccupied();

    /**
     * **Verifica se esta posição foi atingida por um tiro.**
     *
     * @return {@code true} se a posição foi atingida; {@code false} caso contrário.
     */
    boolean isHit();
}