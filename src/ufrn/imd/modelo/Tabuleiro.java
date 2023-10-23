package ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Classe de modelo que contém as funções que dizem respeito ao Tabuleiro de um
 * jogador e às suas respectivas células.
 *
 * @version 1.1
 * @since 1.0
 */

public class Tabuleiro {
    private final int tamMax = 10;
    private ObjectProperty<Celula> celulas[][];
    private List<Navio> navios;
    private boolean turnoJogador = true;

    public Tabuleiro() {
        preencherCelulas();
        navios = new ArrayList<>();
    }

    /**
     * Preenche o tabuleiro com células e define o conteúdo das células para um
     * "mar" de batalha naval vazio ('~').
     * 
     * @version 1.1
     * @since 1.0
     * 
     */
    public void preencherCelulas() {
        celulas = new ObjectProperty[tamMax][tamMax];
        for (int linha = 0; linha < tamMax; linha++) {
            for (int col = 0; col < tamMax; col++) {
                celulas[linha][col] = new SimpleObjectProperty<>(new Celula());
            }
        }
    }

    public void exibirTabuleiroConsole() {
        for (int linha = 0; linha < tamMax; linha++) {
            System.out.print("\n");
            for (int col = 0; col < tamMax; col++) {
                System.out.print(getCelula(linha, col).getConteudo() + " ");
            }
        }
    }

    /**
     * Posiciona um determinado navio no Tabuleiro.
     * 
     * @param celulaInicio
     * @param celulaFinal
     * 
     * @version 1.2
     * @since 1.0
     */
    // TODO adicionar aviso de coordenadas invalidas
    // TODO verificacao vertical e horizontal ao posicionar
    public boolean posicionarNavio(Navio navio, HashSet<Coordenada> coordenadas) {
        boolean interromperFuncao = false;

        for (Coordenada coordenada : coordenadas) {
            if (!posicaoValida(coordenada.getX(), coordenada.getY())) {
                System.out.println("Posicao invalida!");
                interromperFuncao = true;
                break;
            }
            if (!getCelula(coordenada.getX(), coordenada.getY()).estaVazia()) {
                System.out.println("Ja existe!");
                interromperFuncao = true;
                break;
            }
        }

        if (interromperFuncao)
            return false;

        for (Coordenada coordenada : coordenadas) {
            getCelula(coordenada.getX(), coordenada.getY()).addParteNavio();
        }

        System.out.println("Adicionando navio!");
        navio.setPosicoesOcupadas(coordenadas);
        navios.add(navio);

        return true;
    }

    /**
     * Verifica se a posição escolhida é válida para que ocorra o disparo de um
     * jogador na célula
     * 
     * @param linha
     * @param coluna
     * @return
     * 
     * @version 1.1
     * @since 1.0
     */
    public boolean verificarTiro(int linha, int coluna) {
        if (!posicaoValida(linha, coluna))
            return false;

        return getCelula(linha, coluna).estaVazia();

    }

    /**
     * Verifica se a posição está contida no tabuleiro delimitado a 10x10, evitando
     * vazamento.
     * 
     * @param linha
     * @param coluna
     * @return
     * 
     * @version 1.0
     * @since 1.0
     */
    public boolean posicaoValida(int linha, int coluna) {
        if (linha >= tamMax || linha < 0 || coluna >= tamMax || coluna < 0) {
            return false;
        }
        return true;
    }

    /**
     * Sorteia uma coordenada aleatória limitada ao tamanho do campo.
     * 
     * @return
     * 
     * @version 1.1
     * @since 1.0
     */
    public Coordenada aleatorizaCoordenada() {
        Coordenada coordenada = new Coordenada(new Random().nextInt(10), new Random().nextInt(10));
        return coordenada;

    }

    public int getTamMax() {
        return tamMax;
    }

    public ObjectProperty<Celula>[][] getCelulas() {
        return celulas;
    }

    /**
     * Atira em uma célula com coordenadas (x, y) e comunica o resultado ao jogador.
     * 
     * @param x
     * @param y
     * @return
     * 
     * @version 1.1
     * @since 1.1
     */

     

    public boolean atirar(int x, int y) {
        Celula celula = getCelula(x, y);
        if (!posicaoValida(x, y))
            return false; // TODO Mudar para exceção
        else if (celula.estaVazia()) {
            celula.atingir();
            return false;
        } else if (celula.foiAtingida())
            return false; // TODO Mudar para exceção 2
        else {
            celula.atingir();
            System.out.println("Atingiu uma parte de navio!");
            if (navioAfundou(getNavioByCoordenadas(x, y))) {
                System.out.println("E Afundou o navio todo!");
            }
        }

        return true;
    }

    /**
     * Verifica se um navio está nas coordenadas (x, y).
     * 
     * @param x
     * @param y
     * @return
     * 
     * @version 1.1
     * @since 1.1
     */

    public Navio getNavioByCoordenadas(int x, int y) {
        Coordenada coordenada = new Coordenada(x, y);
        for (Navio navio : navios) {
            if (navio.getPosicoesOcupadas().contains(coordenada)) {
                return navio;
            } else {
                continue;
            }
        }
        return null;
    }

    public Celula getCelula(int linha, int coluna) {
        return celulas[linha][coluna].get();
    }

    // TODO descobrir o que é objectproperty
    public void setCelula(int linha, int coluna) {
        ObjectProperty<Celula> propriedadeCelula = celulas[linha][coluna];

        // Celula celula = getCelula(linha, coluna);

        /*
         * propriedadeCelula.addListener((observable, oldValue, newValue) -> {
         * //atualizarBotao(button, newValue);
         * });
         */

        // button.setOnAction(this::onClick);

    }

    // TODO migrar pra controller
    /*
     * private void atualizarBotao(Button button, Celula celula) {
     * // Lógica para atualizar a aparência do botão com base na célula
     * boolean estaVazia = celula.estaVazia();
     * boolean atingida = celula.foiAtingida();
     * 
     * // Define o texto do botão com base nas propriedades da célula
     * if (atingida && !estaVazia) {
     * button.setText("X"); // Navio atingido
     * } else {
     * button.setText("~");
     * }
     * 
     * // Define a desabilitação do botão com base na propriedade isHit
     * button.setDisable(atingida);
     * }
     */

    public void handleButtonClick(int linha, int coluna) {
        Celula celula = getCelula(linha, coluna);
        // System.out.println("Linha: " + linha + "Coluna:" + coluna);
        if (turnoJogador) {

            // trocarTurnoJogador();
        } else {
            // atirar
        }
        // TODO migrar pra controller atualizarBotao(button, celula);
    }

    public boolean getTurnoJogador() {
        return this.turnoJogador;
    }

    public void trocarTurnoJogador() {

    }

    /**
     * Verifica se um navio afundou.
     * 
     * @param navio
     * @return
     * 
     * @version 1.1
     * @since 1.1
     */

    // verificar as coordenadas do navio
    public boolean navioAfundou(Navio navio) {
        for (Coordenada coordenada : navio.getPosicoesOcupadas()) {
            Celula celula = getCelula(coordenada.getX(), coordenada.getY());
            if (!celula.foiAtingida()) {
                return false;
            }
        }
        // navios.remove(navio);
        return true;
    }

    public List<Navio> getNavios() {
        return this.navios;
    }


    public int naviosPerdidos(){
        int naviosPerdidos = 0;

            for( Navio x : navios){
                if(navioAfundou(x)){
                    naviosPerdidos += 1;
                }
            }
            return naviosPerdidos;
        }

    
    


    





}

// TODO verificar se necessario

/*
 * TODO switch (eixoDiferente) {
 * case "linha":
 * for (int linha = celulaInicio.getX(); linha < celulaFinal.getX(); linha++) {
 * 
 * }
 * }
 */
/*
 * public String qualEixoMuda(Celula celulaInicio, Celula celulaFinal) {
 * if (celulaFinal.getX() - celulaInicio.getX() != 0) {
 * return "linha";
 * } else if (celulaFinal.getY() - celulaInicio.getY() != 0) {
 * return "coluna";
 * }
 * return "Posicao inadequada!";
 * }
 */