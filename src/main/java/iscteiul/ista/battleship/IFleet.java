/**
 * **Interface que define o contrato para uma Frota (Fleet) de navios** no jogo Batalha Naval.
 * Responsável por gerir a coleção de navios, controlar o seu estado e fornecer
 * métodos para consulta.
 *
 * @author [Seu Nome/Nome da Equipe, se aplicável]
 * @version 1.0
 */
package iscteiul.ista.battleship;

import java.util.List;

public interface IFleet {
    /** O tamanho máximo do tabuleiro (ex: 10x10). */
    Integer BOARD_SIZE = 10;
    /** O número máximo de navios permitido na frota. */
    Integer FLEET_SIZE = 10;

    /**
     * **Obtém a lista de todos os navios pertencentes a esta frota.**
     *
     * @return Uma lista ({@code List<IShip>}) contendo todos os navios.
     */
    List<IShip> getShips();

    /**
     * **Adiciona um navio à frota.**
     * Deve incluir a lógica de verificação de limites do tabuleiro e colisão.
     *
     * @param s O navio ({@code IShip}) a ser adicionado.
     * @return {@code true} se o navio foi adicionado com sucesso; {@code false} caso contrário.
     */
    boolean addShip(IShip s);

    /**
     * **Obtém uma lista de navios que pertencem a uma determinada categoria (tipo).**
     *
     * @param category A categoria (nome) dos navios a procurar (ex: "Nau", "Caravela").
     * @return Uma lista ({@code List<IShip>}) contendo apenas os navios da categoria especificada.
     */
    List<IShip> getShipsLike(String category);

    /**
     * **Obtém uma lista de navios que ainda estão a flutuar (não afundados).**
     *
     * @return Uma lista ({@code List<IShip>}) contendo apenas os navios que ainda não foram afundados.
     */
    List<IShip> getFloatingShips();

    /**
     * **Verifica se existe um navio numa posição específica do tabuleiro.**
     *
     * @param pos A posição ({@code IPosition}) a ser verificada.
     * @return O navio ({@code IShip}) que ocupa essa posição, ou {@code null} se não houver navio.
     */
    IShip shipAt(IPosition pos);

    /**
     * **Imprime o estado completo da frota no console.**
     * Inclui detalhes sobre todos os navios, os que flutuam e os agrupados por categoria.
     */
    void printStatus();
}