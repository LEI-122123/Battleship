/**
 * **Representa uma Posição (coordenada) no tabuleiro de Batalha Naval.**
 * Esta classe armazena a linha e a coluna, e o estado da célula (ocupada por navio, atingida por tiro).
 * Implementa a interface {@code IPosition}.
 *
 * @author [Seu Nome/Nome da Equipe, se aplicável]
 * @version 1.0
 */
package iscteiul.ista.battleship;

import java.util.Objects;

public class Position implements IPosition {
    /** O índice da linha desta posição. */
    private int row;
    /** O índice da coluna desta posição. */
    private int column;
    /** Indica se esta posição está ocupada por uma parte de um navio. */
    private boolean isOccupied;
    /** Indica se esta posição foi atingida por um tiro. */
    private boolean isHit;

    /**
     * **Cria uma nova instância de Posição (Position).**
     * Inicializa as coordenadas e define o estado inicial como não ocupada e não atingida.
     *
     * @param row O índice da linha.
     * @param column O índice da coluna.
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
        this.isOccupied = false;
        this.isHit = false;
    }

    /**
     * **Obtém o índice da linha desta posição.**
     *
     * @return O valor inteiro da linha.
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * **Obtém o índice da coluna desta posição.**
     *
     * @return O valor inteiro da coluna.
     */
    @Override
    public int getColumn() {
        return column;
    }


    /**
     * **Gera o código hash para este objeto Position.**
     *
     * @return O código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(column, isHit, isOccupied, row);
    }

    /**
     * **Compara esta posição com outro objeto para verificar a igualdade.**
     * Duas posições são consideradas iguais se tiverem a mesma linha e coluna.
     *
     * @param otherPosition O objeto a ser comparado (tipicamente outra {@code IPosition}).
     * @return {@code true} se as coordenadas forem iguais; {@code false} caso contrário.
     */
    @Override
    public boolean equals(Object otherPosition) {
        if (this == otherPosition)
            return true;
        if (otherPosition instanceof IPosition) {
            IPosition other = (IPosition) otherPosition;
            return (this.getRow() == other.getRow() && this.getColumn() == other.getColumn());
        } else {
            return false;
        }
    }

    /**
     * **Verifica se esta posição é adjacente (vizinha) a outra posição.**
     * Considera adjacência em todas as 8 direções (diagonal ou ortogonal).
     *
     * @param other A outra posição ({@code IPosition}) para comparação.
     * @return {@code true} se a diferença absoluta de linhas e colunas for no máximo 1; {@code false} caso contrário.
     */
    @Override
    public boolean isAdjacentTo(IPosition other) {
        return (Math.abs(this.getRow() - other.getRow()) <= 1 && Math.abs(this.getColumn() - other.getColumn()) <= 1);
    }

    /**
     * **Marca esta posição como ocupada por um navio.**
     */
    @Override
    public void occupy() {
        isOccupied = true;
    }

    /**
     * **Marca esta posição como atingida por um tiro.**
     */
    @Override
    public void shoot() {
        isHit = true;
    }

    /**
     * **Verifica se esta posição está ocupada por alguma parte de um navio.**
     *
     * @return {@code true} se a posição estiver ocupada; {@code false} caso contrário.
     */
    @Override
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * **Verifica se esta posição foi atingida por um tiro.**
     *
     * @return {@code true} se a posição foi atingida; {@code false} caso contrário.
     */
    @Override
    public boolean isHit() {
        return isHit;
    }

    /**
     * **Fornece uma representação em String desta posição.**
     *
     * @return Uma string formatada com os valores da linha e coluna.
     */
    @Override
    public String toString() {
        return ("Linha = " + row + " Coluna = " + column);
    }

}