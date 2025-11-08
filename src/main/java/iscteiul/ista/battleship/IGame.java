/**
 * **Interface que define o contrato para a gestão da lógica do Jogo (Game)** de Batalha Naval.
 * Responsável por gerir os tiros, o registo de acertos, afundamentos e o estado geral do tabuleiro.
 *
 * @author [Seu Nome/Nome da Equipe, se aplicável]
 * @version 1.0
 */
package iscteiul.ista.battleship;

import java.util.List;

public interface IGame {
    /**
     * **Executa um tiro numa determinada posição do tabuleiro.**
     * Este é o método principal para interagir com o jogo.
     *
     * @param pos A posição ({@code IPosition}) onde o tiro é efetuado.
     * @return O navio ({@code IShip}) que foi afundado por este tiro, ou {@code null} se o navio
     * não foi afundado (foi um acerto não final, um tiro na água, inválido ou repetido).
     */
    IShip fire(IPosition pos);

    /**
     * **Obtém a lista de todas as posições onde foram efetuados tiros válidos e não repetidos.**
     *
     * @return A lista ({@code List<IPosition>}) dos tiros efetuados.
     */
    List<IPosition> getShots();

    /**
     * **Obtém o número total de tiros repetidos efetuados.**
     *
     * @return O número de tiros repetidos.
     */
    int getRepeatedShots();

    /**
     * **Obtém o número total de tiros inválidos (fora do tabuleiro) efetuados.**
     *
     * @return O número de tiros inválidos.
     */
    int getInvalidShots();

    /**
     * **Obtém o número total de acertos (hits) efetuados em navios.**
     *
     * @return O número de acertos.
     */
    int getHits();

    /**
     * **Obtém o número total de navios que foram afundados (sunk ships).**
     *
     * @return O número de navios afundados.
     */
    int getSunkShips();

    /**
     * **Obtém o número de navios que ainda estão a flutuar (não afundados).**
     *
     * @return O número de navios restantes.
     */
    int getRemainingShips();

    /**
     * **Imprime no console o tabuleiro mostrando os tiros válidos que foram efetuados.**
     */
    void printValidShots();

    /**
     * **Imprime no console o tabuleiro mostrando a posição atual de todos os navios da frota.**
     */
    void printFleet();
}