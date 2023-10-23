package ufrn.imd.modelo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Representa uma célula do jogo de tabuleiro naval.
 *
 * @version 1.1
 * @since 1.0
 */

public class Celula {
    private BooleanProperty atingida;
    private ParteNavio parteNavio;
    private char conteudo;

    public Celula() {
        atingida = new SimpleBooleanProperty(false);
        parteNavio = null;
        conteudo = '~';
    }

    public boolean estaVazia() {
        return parteNavio == null;
    }

    public boolean foiAtingida() {
        return atingida.get();
    }

    public void atingir() {
        atingida.setValue(true);
        if (parteNavio != null) {
            parteNavio.afundar();
        }
    }

    public ParteNavio getParteNavio() {
        return parteNavio;
    }

    public void addParteNavio() {
        this.parteNavio = new ParteNavio();
    }

    // TODO Verificar se necessário
    public char getConteudo() {
        return estaVazia() ? '~' : parteNavio.getCharCorrespondente();
    }

}
