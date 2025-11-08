/**
 * **Representa as quatro direções cardeais (Norte, Sul, Este, Oeste)**,
 * além de um estado **Desconhecido (UNKNOWN)**, utilizado para definir a orientação
 * (bearing) dos navios no jogo Batalha Naval.
 * Cada direção é associada a um caractere que a representa (ex: 'n' para NORTH).
 *
 * @author fba
 * @version 1.0
 */
package iscteiul.ista.battleship;

/**
 * @author fba
 */
public enum Compass {
    /** Norte ('n') */
    NORTH('n'),
    /** Sul ('s') */
    SOUTH('s'),
    /** Este/Leste ('e') */
    EAST('e'),
    /** Oeste ('o') */
    WEST('o'),
    /** Direção Desconhecida ou Inválida ('u') */
    UNKNOWN('u');

    /** Caractere que representa a direção. */
    private final char c;

    /**
     * **Construtor do enum Compass.**
     * Associa um caractere a cada constante do enum.
     *
     * @param c O caractere de representação da direção (ex: 'n', 's', 'e', 'o').
     */
    Compass(char c) {
        this.c = c;
    }

    /**
     * **Obtém o caractere que representa esta direção da bússola.**
     *
     * @return O caractere ('n', 's', 'e', 'o', ou 'u').
     */
    public char getDirection() {
        return c;
    }

    /**
     * **Devolve o caractere de representação da direção como uma string.**
     *
     * @return Uma string contendo o caractere de representação.
     */
    @Override
    public String toString() {
        return "" + c;
    }

    /**
     * **Converte um caractere para a constante Compass correspondente.**
     * É um método auxiliar estático para determinar a direção com base num input de caractere.
     *
     * @param ch O caractere de direção ('n', 's', 'e', 'o').
     * @return A constante Compass correspondente. Devolve {@code UNKNOWN} se o caractere não for reconhecido.
     */
    static Compass charToCompass(char ch) {
        Compass bearing;
        switch (ch) {
            case 'n':
                bearing = NORTH;
                break;
            case 's':
                bearing = SOUTH;
                break;
            case 'e':
                bearing = EAST;
                break;
            case 'o':
                bearing = WEST;
                break;
            default:
                bearing = UNKNOWN;
        }

        return bearing;
    }
}