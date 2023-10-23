package ufrn.imd.dao;

import ufrn.imd.modelo.Jogador;

public class BatalhaNavalDAO {
    
    private Jogador user;
    private Jogador inimigo;

    public BatalhaNavalDAO() {
    user = new Jogador();
    inimigo = new Jogador();
    }

    public Jogador getUser() {
        return user;
    }

    public Jogador getInimigo() {
        return inimigo;
    }

    public void exibirTabuleiroConsole(Jogador jogador) {
        jogador.getTabuleiro().exibirTabuleiroConsole();
    }


}
