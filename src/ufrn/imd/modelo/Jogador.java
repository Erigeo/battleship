package ufrn.imd.modelo;

public class Jogador {
    private int vitorias;
    private int derrotas;
    private Tabuleiro tabuleiro;
    private boolean podeJogar;
    private boolean posicionouNavios;
    private int numeroDeNavios;
    private boolean eHumano;

    public Jogador() {
        vitorias = 0;
        derrotas = 0;
        tabuleiro = new Tabuleiro();
        podeJogar = true;
        posicionouNavios = false;
        numeroDeNavios = 4;

        eHumano = false;
    }

    public int getVitorias() {
        return vitorias;
    }

    public void aumentarVitorias() {
        this.vitorias++;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void aumentarDerrotas() {
        this.derrotas++;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public boolean podeJogar() {
        return this.podeJogar;
    }

    public void trocarTurno() {
        if (podeJogar == false)
            podeJogar = true;
        if (podeJogar == true)
            podeJogar = false;
    }

    public boolean posicionouNavios() {
        return this.posicionouNavios;
    }

    public void trocarPermissaoPosicionamentoNavios() {
        if (posicionouNavios == true) posicionouNavios = false;
        if (posicionouNavios == false) posicionouNavios = true;
    }

    public int getNumeroDeNavios() {
        return numeroDeNavios;
    }

    public void setNumeroDeNavios(int numeroDeNavios) {
        this.numeroDeNavios = numeroDeNavios;
    }

    public void definirComoHumano() {
        this.eHumano = true;
    }
    
    public boolean eHumano() {
        return this.eHumano;
    }


    public void reiniciarTabuleiro() {
        this.tabuleiro = null;
        this.tabuleiro = new Tabuleiro();
    }

    


}
