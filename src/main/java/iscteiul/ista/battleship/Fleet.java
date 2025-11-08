/**
 * **Representa a Frota (Fleet)** de navios de um jogador no jogo Batalha Naval.
 * Esta classe é responsável por gerir a coleção de navios, adicionar novas embarcações
 * verificando o tamanho máximo da frota, colisão e limites do tabuleiro.
 * Implementa a interface {@code IFleet}.
 *
 * @author [Seu Nome/Nome da Equipe, se aplicável]
 * @version 1.0
 */
package iscteiul.ista.battleship;

import java.util.ArrayList;
import java.util.List;

public class Fleet implements IFleet {
    /**
     * **Imprime no console a representação em String de todos os navios fornecidos na lista.**
     * É uma operação auxiliar estática.
     *
     * @param ships A lista de navios ({@code List<IShip>}) a serem impressos.
     */
    static void printShips(List<IShip> ships) {
        for (IShip ship : ships)
            System.out.println(ship);
    }

    // -----------------------------------------------------

    /** A lista interna que armazena todos os navios pertencentes a esta frota. */
    private List<IShip> ships;

    /**
     * **Cria uma nova instância de Frota (Fleet).**
     * Inicializa a lista de navios como um novo {@code ArrayList}.
     */
    public Fleet() {
        ships = new ArrayList<>();
    }

    /**
     * **Obtém a lista de todos os navios desta frota.**
     *
     * @return A lista de navios ({@code List<IShip>}).
     */
    @Override
    public List<IShip> getShips() {
        return ships;
    }

    /**
     * **Tenta adicionar um navio à frota.**
     * A adição só é bem-sucedida se:
     * 1. O tamanho da frota não exceder o limite máximo ({@code FLEET_SIZE}).
     * 2. O navio estiver totalmente dentro dos limites do tabuleiro.
     * 3. Não houver risco de colisão (o navio não está demasiado próximo de um navio existente).
     *
     * @param s O navio ({@code IShip}) a ser adicionado.
     * @return {@code true} se o navio foi adicionado com sucesso; {@code false} caso contrário.
     */
    @Override
    public boolean addShip(IShip s) {
        boolean result = false;
        if ((ships.size() <= FLEET_SIZE) && (isInsideBoard(s)) && (!colisionRisk(s))) {
            ships.add(s);
            result = true;
        }
        return result;
    }

    /**
     * **Obtém uma lista de navios que pertencem a uma determinada categoria (tipo).**
     *
     * @param category A categoria (nome) dos navios a procurar (ex: "Nau", "Caravela").
     * @return Uma lista ({@code List<IShip>}) contendo apenas os navios da categoria especificada.
     */
    @Override
    public List<IShip> getShipsLike(String category) {
        List<IShip> shipsLike = new ArrayList<>();
        for (IShip s : ships)
            if (s.getCategory().equals(category))
                shipsLike.add(s);

        return shipsLike;
    }

    /**
     * **Obtém uma lista de navios que ainda estão a flutuar (não afundados).**
     *
     * @return Uma lista ({@code List<IShip>}) contendo apenas os navios que ainda não foram afundados.
     */
    @Override
    public List<IShip> getFloatingShips() {
        List<IShip> floatingShips = new ArrayList<>();
        for (IShip s : ships)
            if (s.stillFloating())
                floatingShips.add(s);

        return floatingShips;
    }

    /**
     * **Verifica se existe um navio numa posição específica do tabuleiro.**
     *
     * @param pos A posição ({@code IPosition}) a ser verificada.
     * @return O navio ({@code IShip}) que ocupa essa posição, ou {@code null} se não houver navio.
     */
    @Override
    public IShip shipAt(IPosition pos) {
        for (int i = 0; i < ships.size(); i++)
            if (ships.get(i).occupies(pos))
                return ships.get(i);
        return null;
    }

    /**
     * **Verifica se um determinado navio está totalmente dentro dos limites do tabuleiro.**
     * Assume-se que as constantes {@code BOARD_SIZE} estão definidas.
     *
     * @param s O navio ({@code IShip}) a ser verificado.
     * @return {@code true} se o navio estiver totalmente dentro do tabuleiro; {@code false} caso contrário.
     */
    private boolean isInsideBoard(IShip s) {
        return (s.getLeftMostPos() >= 0 && s.getRightMostPos() <= BOARD_SIZE - 1 && s.getTopMostPos() >= 0
                && s.getBottomMostPos() <= BOARD_SIZE - 1);
    }

    /**
     * **Verifica se existe risco de colisão entre o novo navio e qualquer navio já existente na frota.**
     * Utiliza o método auxiliar {@code tooCloseTo} da interface {@code IShip}.
     *
     * @param s O navio ({@code IShip}) a ser verificado contra os navios existentes.
     * @return {@code true} se houver risco de colisão (ou seja, se estiver muito próximo); {@code false} caso contrário.
     */
    private boolean colisionRisk(IShip s) {
        for (int i = 0; i < ships.size(); i++) {
            if (ships.get(i).tooCloseTo(s))
                return true;
        }
        return false;
    }


    /**
     * **Imprime o estado completo da frota no console, detalhando todos os navios,**
     * **os que ainda flutuam e os agrupados por categoria (Galeão, Fragata, Nau, Caravela, Barca).**
     */
    public void printStatus() {
        printAllShips();
        printFloatingShips();
        printShipsByCategory("Galeao");
        printShipsByCategory("Fragata");
        printShipsByCategory("Nau");
        printShipsByCategory("Caravela");
        printShipsByCategory("Barca");
    }

    /**
     * **Imprime no console todos os navios de uma frota pertencentes a uma determinada categoria.**
     *
     * @param category A categoria (nome) de navios de interesse a serem impressos.
     */
    public void printShipsByCategory(String category) {
        assert category != null;

        printShips(getShipsLike(category));
    }

    /**
     * **Imprime no console apenas os navios da frota que ainda não foram afundados (estão a flutuar).**
     */
    public void printFloatingShips() {
        printShips(getFloatingShips());
    }

    /**
     * **Imprime no console todos os navios pertencentes à frota.**
     */
    void printAllShips() {
        printShips(ships);
    }

}