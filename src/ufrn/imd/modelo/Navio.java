package ufrn.imd.modelo;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe de modelo de Navio.
 *
 * @version 1.1
 * @since 1.1
 */

public class Navio extends ParteNavio {
    protected int quantidadeDisparos;
    protected int alcanceDisparo;
    protected int tamanho;
    //protected boolean afundou;
    private HashSet<Coordenada> posicoesOcupadas;
    protected char charCorrespondente;
    protected boolean estaHorizontal;
    protected String cor;

    public Navio () {
        quantidadeDisparos = 1;
        alcanceDisparo = 0;
        this.posicoesOcupadas = new HashSet<>();
        charCorrespondente = 'N';
        tamanho = 2;
        estaHorizontal = true;
    }

    public int getQuantidadeDisparos() {
        return quantidadeDisparos;
    }
    public void setQuantidadeDisparos(int quantidadeDisparos) {
        this.quantidadeDisparos = quantidadeDisparos;
    }



     public boolean estaHorizontal() {
        return this.estaHorizontal;
    }


    public void setEstaHorizontal(boolean estaHorizontal) {
        this.estaHorizontal = estaHorizontal;
    }


    public void trocarPosicao() {
        if (estaHorizontal == true) estaHorizontal = false;
        else if (estaHorizontal == false) estaHorizontal = true;
    }


    public int getAlcanceDisparo() {
        return this.alcanceDisparo;
    }

    public void setAlcanceDisparo(int alcanceDisparo) {
        this.alcanceDisparo = alcanceDisparo;
    }
    

    public int getTamanho() {
        return this.tamanho;
    }

    /**
     * Adiciona uma coordenada no conjunto de posições ocupadas.
     * 
     * @version 1.1
     * @since 1.1
     * 
     */
    public void adicionarPosicao(int x, int y) {
        Coordenada coordenada = new Coordenada(x, y);
        posicoesOcupadas.add(coordenada);
    }

    public HashSet<Coordenada> getPosicoesOcupadas() {
        return this.posicoesOcupadas;
    }

    public void setPosicoesOcupadas(HashSet<Coordenada> posicoesOcupadas) {
        this.posicoesOcupadas = posicoesOcupadas;
    }


    public String getCor() {
        return this.cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }


    /* TODO verificar se necessário
    public boolean afundou() {
        for (Coordenada coordenada : posicoesOcupadas) {
            if (!tiros.contains(coordenada)) {
                return false;
            }
        }
        return true;
    }*/

}
