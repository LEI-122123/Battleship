/**
 * **Classe Abstrata Base para todos os Navios (Ship) no jogo Batalha Naval.**
 * Fornece a implementação comum para a maioria dos métodos definidos em {@code IShip},
 * incluindo a gestão das posições, orientação, verificação de limites e colisões,
 * e o estado de flutuação.
 *
 * @author [Seu Nome/Nome da Equipe, se aplicável]
 * @version 1.0
 */
package iscteiul.ista.battleship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Ship implements IShip {

    /** Constante para a categoria Galeão. */
    private static final String GALEAO = "galeao";
    /** Constante para a categoria Fragata. */
    private static final String FRAGATA = "fragata";
    /** Constante para a categoria Nau (Carrack). */
    private static final String NAU = "nau";
    /** Constante para a categoria Caravela. */
    private static final String CARAVELA = "caravela";
    /** Constante para a categoria Barca. */
    private static final String BARCA = "barca";

    /**
     * **Método estático para construir uma instância específica de Navio (Ship) com base na categoria.**
     * Funciona como um *factory method* simples.
     *
     * @param shipKind A categoria/tipo do navio a ser construído (ex: "caravela", "fragata").
     * @param bearing A orientação ({@code Compass}) do novo navio.
     * @param pos O ponto inicial ({@code Position}) para posicionar o navio.
     * @return Uma instância do tipo de navio correspondente, ou {@code null} se a categoria for desconhecida.
     */
    static Ship buildShip(String shipKind, Compass bearing, Position pos) {
        Ship s;
        switch (shipKind) {
            case BARCA:
                s = new Barge(bearing, pos);
                break;
            case CARAVELA:
                s = new Caravel(bearing, pos);
                break;
            case NAU:
                s = new Carrack(bearing, pos);
                break;
            case FRAGATA:
                s = new Frigate(bearing, pos);
                break;
            case GALEAO:
                s = new Galleon(bearing, pos);
                break;
            default:
                s = null;
        }
        return s;
    }


    /** A categoria (nome) do navio (ex: "Nau"). */
    private String category;
    /** A orientação (Norte, Sul, Este, Oeste) do navio. */
    private Compass bearing;
    /** A posição de referência (superior esquerda) do navio. */
    private IPosition pos;
    /** A lista das posições (células) que este navio ocupa no tabuleiro. */
    protected List<IPosition> positions;


    /**
     * **Construtor da classe base Ship.**
     * Inicializa os atributos fundamentais do navio. As subclasses são responsáveis
     * por preencher a lista {@code positions} de acordo com o seu formato e tamanho.
     *
     * @param category A categoria/nome do navio.
     * @param bearing A orientação ({@code Compass}) do navio.
     * @param pos A posição de referência ({@code IPosition}) do navio.
     */
    public Ship(String category, Compass bearing, IPosition pos) {
        assert bearing != null;
        assert pos != null;

        this.category = category;
        this.bearing = bearing;
        this.pos = pos;
        positions = new ArrayList<>();
    }

    /**
     * **Obtém a categoria (nome/tipo) deste navio.**
     *
     * @return A categoria do navio.
     */
    @Override
    public String getCategory() {
        return category;
    }

    /**
     * **Obtém a lista de todas as posições (células) que este navio ocupa no tabuleiro.**
     *
     * @return A lista ({@code List<IPosition>}) das posições ocupadas.
     */
    public List<IPosition> getPositions() {
        return positions;
    }

    /**
     * **Obtém a posição inicial ou de referência (canto superior/esquerdo) do navio.**
     *
     * @return A posição de referência ({@code IPosition}).
     */
    @Override
    public IPosition getPosition() {
        return pos;
    }

    /**
     * **Obtém a orientação (bearing) deste navio.**
     *
     * @return A orientação ({@code Compass}) do navio.
     */
    @Override
    public Compass getBearing() {
        return bearing;
    }

    /**
     * **Verifica se o navio ainda está a flutuar (ainda não foi afundado).**
     *
     * @return {@code true} se pelo menos uma posição do navio não foi atingida; {@code false} caso contrário (afundado).
     */
    @Override
    public boolean stillFloating() {
        for (int i = 0; i < getSize(); i++)
            if (!getPositions().get(i).isHit())
                return true;
        return false;
    }

    /**
     * **Obtém o índice da linha mais alta (Top) ocupada pelo navio.**
     *
     * @return O valor inteiro da linha mais alta.
     */
    @Override
    public int getTopMostPos() {
        int top = getPositions().get(0).getRow();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getRow() < top)
                top = getPositions().get(i).getRow();
        return top;
    }

    /**
     * **Obtém o índice da linha mais baixa (Bottom) ocupada pelo navio.**
     *
     * @return O valor inteiro da linha mais baixa.
     */
    @Override
    public int getBottomMostPos() {
        int bottom = getPositions().get(0).getRow();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getRow() > bottom)
                bottom = getPositions().get(i).getRow();
        return bottom;
    }

    /**
     * **Obtém o índice da coluna mais à esquerda (Left) ocupada pelo navio.**
     *
     * @return O valor inteiro da coluna mais à esquerda.
     */
    @Override
    public int getLeftMostPos() {
        int left = getPositions().get(0).getColumn();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getColumn() < left)
                left = getPositions().get(i).getColumn();
        return left;
    }

    /**
     * **Obtém o índice da coluna mais à direita (Right) ocupada pelo navio.**
     *
     * @return O valor inteiro da coluna mais à direita.
     */
    @Override
    public int getRightMostPos() {
        int right = getPositions().get(0).getColumn();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getColumn() > right)
                right = getPositions().get(i).getColumn();
        return right;
    }

    /**
     * **Verifica se o navio ocupa uma determinada posição.**
     *
     * @param pos A posição ({@code IPosition}) a ser verificada.
     * @return {@code true} se o navio ocupar essa posição; {@code false} caso contrário.
     */
    @Override
    public boolean occupies(IPosition pos) {
        assert pos != null;

        for (int i = 0; i < getSize(); i++)
            if (getPositions().get(i).equals(pos))
                return true;
        return false;
    }

    /**
     * **Verifica se este navio está demasiado perto de outro navio.**
     * Percorre todas as posições do outro navio e verifica se alguma é adjacente a qualquer posição deste navio.
     *
     * @param other O outro navio ({@code IShip}) para comparação.
     * @return {@code true} se houver adjacência entre qualquer posição dos dois navios; {@code false} caso contrário.
     */
    @Override
    public boolean tooCloseTo(IShip other) {
        assert other != null;

        Iterator<IPosition> otherPos = other.getPositions().iterator();
        while (otherPos.hasNext())
            if (tooCloseTo(otherPos.next()))
                return true;

        return false;
    }

    /**
     * **Verifica se o navio está demasiado perto de uma posição específica.**
     * Um navio está "too close" se qualquer uma das suas posições for adjacente (vizinha, incluindo diagonal)
     * à posição fornecida.
     *
     * @param pos A posição ({@code IPosition}) a ser verificada.
     * @return {@code true} se o navio estiver demasiado próximo da posição; {@code false} caso contrário.
     */
    @Override
    public boolean tooCloseTo(IPosition pos) {
        for (int i = 0; i < this.getSize(); i++)
            if (getPositions().get(i).isAdjacentTo(pos))
                return true;
        return false;
    }


    /**
     * **Marca uma posição específica do navio como atingida (shot).**
     * Percorre as posições e, se encontrar a correspondência, marca-a como atingida.
     *
     * @param pos A posição ({@code IPosition}) onde o tiro acertou.
     */
    @Override
    public void shoot(IPosition pos) {
        assert pos != null;

        for (IPosition position : getPositions()) {
            if (position.equals(pos))
                position.shoot();
        }
    }


    /**
     * **Fornece uma representação em String deste navio.**
     * O formato inclui a categoria, orientação e posição de referência.
     *
     * @return Uma string formatada com os atributos do navio.
     */
    @Override
    public String toString() {
        return "[" + category + " " + bearing + " " + pos + "]";
    }
}