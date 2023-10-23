package ufrn.imd.modelo;

import java.util.HashSet;

/**
 * Representa uma parte de navio no jogo de batalha naval.
 *
 * @version 1.1
 * @since 1.1
 */

public class ParteNavio {
    protected boolean afundou;
    protected char charCorrespondente;

    public ParteNavio() {
        afundou = false;
        charCorrespondente = 'N';
    }

    /**
     * Permite saber se a parte do navio já foi afundada.
     * 
     * @version 1.1
     * @since 1.1
     * 
     */
    public boolean afundou() {
        return afundou;
    }

    public void afundar() {
        afundou = true;
    }

    /**
     * Retorna um caractere representando a entidade.
     * 
     * @version 1.1
     * @since 1.1
     * 
     */
    public char getCharCorrespondente() {
        return this.charCorrespondente;
    }

    public void setCharCorrespondente(char charCorrespondente) {
        this.charCorrespondente = charCorrespondente;
    }

}
