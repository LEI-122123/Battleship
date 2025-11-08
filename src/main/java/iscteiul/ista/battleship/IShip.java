/**
 * **Interface que define o contrato para qualquer Navio (IShip)** no jogo Batalha Naval.
 * Responsável por gerir as propriedades do navio (tamanho, categoria, posição, orientação)
 * e o seu estado (flutuando, atingido, afundado).
 *
 * @author [Seu Nome/Nome da Equipe, se aplicável]
 * @version 1.0
 */
package iscteiul.ista.battleship;

import java.util.List;

public interface IShip {
    /**
     * **Obtém a categoria (nome/tipo) deste navio.**
     *
     * @return A categoria do navio (ex: "Nau", "Fragata").
     */
    String getCategory();

    /**
     * **Obtém o tamanho deste navio (o número de células que ocupa).**
     *
     * @return O tamanho inteiro do navio.
     */
    Integer getSize();

    /**
     * **Obtém a lista de todas as posições (células) que este navio ocupa no tabuleiro.**
     *
     * @return Uma lista ({@code List<IPosition>}) das posições ocupadas.
     */
    List<IPosition> getPositions();

    /**
     * **Obtém a posição inicial ou de referência (geralmente o canto superior/esquerdo) do navio.**
     *
     * @return A posição de referência ({@code IPosition}).
     */
    IPosition getPosition();

    /**
     * **Obtém a orientação (bearing) deste navio.**
     *
     * @return A orientação ({@code Compass}) do navio (Norte, Sul, Leste, Oeste).
     */
    Compass getBearing();

    /**
     * **Verifica se o navio ainda está a flutuar (ainda não foi afundado).**
     * Um navio afunda quando todas as suas posições são atingidas.
     *
     * @return {@code true} se o navio ainda estiver flutuando; {@code false} caso contrário (afundado).
     */
    boolean stillFloating();

    /**
     * **Obtém o índice da linha mais alta (Top) ocupada pelo navio.**
     *
     * @return O valor inteiro da linha mais alta.
     */
    int getTopMostPos();

    /**
     * **Obtém o índice da linha mais baixa (Bottom) ocupada pelo navio.**
     *
     * @return O valor inteiro da linha mais baixa.
     */
    int getBottomMostPos();

    /**
     * **Obtém o índice da coluna mais à esquerda (Left) ocupada pelo navio.**
     *
     * @return O valor inteiro da coluna mais à esquerda.
     */
    int getLeftMostPos();

    /**
     * **Obtém o índice da coluna mais à direita (Right) ocupada pelo navio.**
     *
     * @return O valor inteiro da coluna mais à direita.
     */
    int getRightMostPos();

    /**
     * **Verifica se o navio ocupa uma determinada posição.**
     *
     * @param pos A posição ({@code IPosition}) a ser verificada.
     * @return {@code true} se o navio ocupar essa posição; {@code false} caso contrário.
     */
    boolean occupies(IPosition pos);

    /**
     * **Verifica se este navio está demasiado perto de outro navio.**
     * Geralmente verifica se há adjacência entre as posições dos dois navios.
     *
     * @param other O outro navio ({@code IShip}) para comparação.
     * @return {@code true} se estiverem demasiado próximos; {@code false} caso contrário.
     */
    boolean tooCloseTo(IShip other);

    /**
     * **Verifica se o navio está demasiado perto de uma posição específica.**
     *
     * @param pos A posição ({@code IPosition}) a ser verificada.
     * @return {@code true} se o navio estiver demasiado próximo da posição; {@code false} caso contrário.
     */
    boolean tooCloseTo(IPosition pos);

    /**
     * **Marca uma posição específica do navio como atingida (shot).**
     * Atualiza o estado da posição e o estado do navio.
     *
     * @param pos A posição ({@code IPosition}) onde o tiro acertou.
     */
    void shoot(IPosition pos);
}