package ufrn.imd.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ufrn.imd.modelo.Celula;
import ufrn.imd.modelo.Coordenada;
import ufrn.imd.modelo.Corveta;
import ufrn.imd.modelo.Destroyer;
import ufrn.imd.modelo.Fragata;
import ufrn.imd.modelo.Jogador;
import ufrn.imd.modelo.Navio;
import ufrn.imd.modelo.Submarino;
import ufrn.imd.modelo.Tabuleiro;

public class BatalhaNavalController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane GridPaneAliado;

    @FXML
    private GridPane GridPaneInimigo;

    private Jogador user;

    private Jogador bot;

    private BatalhaNavalView batalhaNavalView;

    private Navio navioAtual;

    @FXML
    private ToggleButton buttonCorveta;

    @FXML
    private ToggleButton buttonDestroyer;

    @FXML
    private ToggleButton buttonFragata;

    @FXML
    private ToggleButton buttonSubmarino;

    @FXML
    private VBox TelaPrincipal;

    @FXML
    private Rectangle retanguloNavioAtual;

    @FXML
    private Label labelDerrotas;

    @FXML
    private Label labelVitorias;

    @FXML
    void initialize() {
        inicializarTabuleiros();
        GridPaneAliado.setOnMouseMoved(this::onMouseMovedGridPane);
        assert GridPaneAliado != null
                : "fx:id=\"GridPaneAliado\" was not injected: check your FXML file 'batalhanaval.fxml'.";
        assert GridPaneInimigo != null
                : "fx:id=\"GridPaneInimigo\" was not injected: check your FXML file 'batalhanaval.fxml'.";
    }

    public BatalhaNavalController() {
        TelaPrincipal = new VBox();
        GridPaneAliado = new GridPane();
        GridPaneInimigo = new GridPane();
        user = new Jogador();
        user.definirComoHumano();
        bot = new Jogador();
        initialize();
        posicionarNaviosIA();
    }

    //TODO n funciona
    public void reiniciarJogo() {
        user.reiniciarTabuleiro();
        user.trocarPermissaoPosicionamentoNavios();
        // TODO trocarTurno
        bot.reiniciarTabuleiro();
        bot.trocarPermissaoPosicionamentoNavios();

        buttonCorveta.setDisable(false);
        buttonDestroyer.setDisable(false);
        buttonFragata.setDisable(false);
        buttonSubmarino.setDisable(false);

        GridPaneAliado.getChildren().clear();
        GridPaneInimigo.getChildren().clear();

        TelaPrincipal.getChildren().remove(GridPaneAliado);
        TelaPrincipal.getChildren().remove(GridPaneInimigo);

        GridPaneAliado = new GridPane();
        GridPaneInimigo = new GridPane();
        initialize();
        posicionarNaviosIA();

    }

    private void inicializarTabuleiros() {
        for (int linha = 0; linha < 10; linha++) {
            for (int coluna = 0; coluna < 10; coluna++) {
                criarBotaoAliado(linha, coluna);
                criarBotaoInimigo(linha, coluna);
            }
        }
    }

    private void criarBotaoAliado(int linha, int coluna) {
        Button buttonAliado = new Button();
        buttonAliado.setPrefWidth(30);
        buttonAliado.setPrefHeight(30);
        buttonAliado.setStyle("-fx-background-color: blue");
        buttonAliado.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                rotacionarNavio();
            } else if (e.getButton() == MouseButton.PRIMARY) {
                handleButtonClickCampoAliado(linha, coluna, user, buttonAliado);
            }
        });
        // buttonAliado.setOnContextMenuRequested( (ContextMenuEvent e) ->
        // handleButtonClick(linha, coluna, user, buttonAliado));

        user.getTabuleiro().setCelula(linha, coluna);
        GridPaneAliado.add(buttonAliado, coluna, linha);
        // GridPane.setColumnIndex(buttonAliado, coluna);
        // GridPane.setRowIndex(buttonAliado, linha);
        // Restante da lógica para configuração do botão aliado
    }

    private void rotacionarNavio() {
        if (navioAtual != null && retanguloNavioAtual != null) {
            if (navioAtual.estaHorizontal()) {
                retanguloNavioAtual.setRotate(90);
                navioAtual.trocarPosicao();
            } else if (!navioAtual.estaHorizontal()) {
                System.out.println("ain papai");
                retanguloNavioAtual.setRotate(180);
                navioAtual.trocarPosicao();
            }
        }
    }

    private void criarBotaoInimigo(int linha, int coluna) {
        Button buttonInimigo = new Button();
        buttonInimigo.setPrefWidth(30);
        buttonInimigo.setPrefHeight(30);
        buttonInimigo.setStyle("-fx-background-color: blue");
        // TODO está em user porque se presume que o bot não vá usar o clique do botão.
        // A diferenciação é feita por causa dos diferentes tabuleiros.
        buttonInimigo.setOnAction(e -> handleButtonClickCampoInimigo(linha, coluna, user, buttonInimigo));

        bot.getTabuleiro().setCelula(linha, coluna);
        GridPaneInimigo.add(buttonInimigo, coluna, linha);
        // Restante da lógica para configuração do botão inimigo
    }

    // TODO popup posicao invalida
    // TODO if de atirar ->
    // TODO evento de impedir rotacao em locais invalidos
    // TODO se for invalido a posição, ja adicionar na vertical em vez do horizontal
    // padrao
    public void handleButtonClickCampoAliado(int linha, int coluna, Jogador jogador, Button button) {
        // size no arraylist de navios, obter o total de navios adicionados e joga no
        // jogador

        if (jogador.podeJogar() && !jogador.posicionouNavios()) {
            GridPaneInimigo.setDisable(true);

            if (navioAtual != null) {

                HashSet<Coordenada> coordenadas = new HashSet<>();
                for (int x = 0; x < navioAtual.getTamanho(); x++) {
                    if (navioAtual.estaHorizontal()) {
                        coordenadas.add(new Coordenada(linha, coluna + x));
                    } else if (!navioAtual.estaHorizontal()) {
                        coordenadas.add(new Coordenada(linha + x, coluna));
                    }
                }

                boolean posicionadoOk = jogador.getTabuleiro().posicionarNavio(navioAtual, coordenadas);

                if (posicionadoOk) {
                    System.out.println("Posicionou!");
                    for (int x = 0; x < navioAtual.getTamanho(); x++) {
                        Node noAdjacente;
                        if (navioAtual.estaHorizontal()) {
                            noAdjacente = getNodeByRowColumnIndex(linha, coluna + x, GridPaneAliado);
                        } else {
                            noAdjacente = getNodeByRowColumnIndex(linha + x, coluna, GridPaneAliado);
                        }

                        if (noAdjacente != null) {
                            colorirBotoes((Button) noAdjacente, navioAtual);
                        }
                    }
                    desativarBotaoNavio();
                    retanguloNavioAtual.setVisible(false);
                    retanguloNavioAtual = null;
                    System.out.println("ok!");

                    if (jogador.getTabuleiro().getNavios().size() == jogador.getNumeroDeNavios()) {
                        jogador.trocarPermissaoPosicionamentoNavios();
                        GridPaneAliado.setDisable(true);
                        GridPaneInimigo.setDisable(false);
                    }
                }

            }

        }

    }

    public void handleButtonClickCampoInimigo(int linha, int coluna, Jogador jogador, Button button) {
        if (jogador.eHumano()) {
            if (jogador.podeJogar() && jogador.posicionouNavios()) {
                System.out.println("clicou no inimigo");
                if (bot.getTabuleiro().atirar(linha, coluna)) {

                    Navio navioAtingido = bot.getTabuleiro().getNavioByCoordenadas(linha, coluna);
                    if (navioAtingido != null) {
                        button.setStyle("-fx-background-color: " + navioAtingido.getCor() + ";");
                    }

                    if (verificarDerrota(bot)) {
                        jogador.aumentarVitorias();
                        labelVitorias.setText("Vitorias:: " + jogador.getVitorias());
                        // GridPaneInimigo.setDisable(true);
                        reiniciarJogo();
                        return;

                    }

                }
                // Tiro deu errado (célula não possui navio ou já foi atingida) e célula não
                // possui navio
                else if (!bot.getTabuleiro().atirar(linha, coluna)
                        && bot.getTabuleiro().getCelula(linha, coluna).estaVazia()) {
                    button.setStyle("-fx-background-color: pink");
                    button.setDisable(true);
                }
            }
        } else if (!jogador.eHumano()) {

            // TODO trocar jogador em cima por bot?
        }
    }

    private void posicionarNaviosIA() {

        for (int ii = 0; ii < bot.getNumeroDeNavios(); ii++) {
            switch (ii) {
                case 0:
                    navioAtual = new Corveta();
                    break;

                case 1:
                    navioAtual = new Submarino();
                    break;

                case 2:
                    navioAtual = new Fragata();
                    break;

                case 3:
                    navioAtual = new Destroyer();
                    break;
            }
            if (navioAtual != null) {
                // HashSet<Coordenada> coordenadas = new HashSet<>();
                HashSet<Coordenada> coordenadas = new HashSet<>();
                Coordenada coordenadaInicial;
                do {
                    coordenadaInicial = bot.getTabuleiro().aleatorizaCoordenada();
                } while (!bot.getTabuleiro().posicaoValida(coordenadaInicial.getX(), coordenadaInicial.getY()));

                System.out.println("INICIAL X:" + coordenadaInicial.getX());
                System.out.println("INICIAL Y:" + coordenadaInicial.getY());

                navioAtual.setEstaHorizontal(new Random().nextBoolean());
                for (int x = 0; x < navioAtual.getTamanho(); x++) {

                    System.out.println(navioAtual.getClass() + " ESTA HORIZONTAL: " + navioAtual.estaHorizontal());
                    if (navioAtual.estaHorizontal()) {
                        coordenadas.add(new Coordenada(coordenadaInicial.getX(), coordenadaInicial.getY() + x));
                    } else if (!navioAtual.estaHorizontal()) {
                        coordenadas.add(new Coordenada(coordenadaInicial.getX() + x, coordenadaInicial.getY()));

                    }
                }

                boolean posicionadoOk = bot.getTabuleiro().posicionarNavio(navioAtual, coordenadas);
                if (posicionadoOk) {
                    System.out.println("Navio ok: " + navioAtual.getClass());
                } else {
                    System.out.println("Deu errado! Tentando novamente no navio... " + navioAtual.getClass());
                    ii--;
                }
                navioAtual = null;
            }

        }
    }

    private Node getNodeByRowColumnIndex(final int linha, final int coluna, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == linha && GridPane.getColumnIndex(node) == coluna) {
                return node;
            }
        }
        return null;
    }

    void colorirBotoes(Button button, Navio navioAtual) {
        button.setStyle("-fx-background-color: " + navioAtual.getCor());
    }

    void desativarBotaoNavio() {
        if (navioAtual instanceof Corveta) {
            buttonCorveta.setDisable(true);
        } else if (navioAtual instanceof Submarino) {
            buttonSubmarino.setDisable(true);
        } else if (navioAtual instanceof Destroyer) {
            buttonDestroyer.setDisable(true);
        } else if (navioAtual instanceof Fragata) {
            buttonFragata.setDisable(true);
        }
        navioAtual = null;
    }

    void criarRetanguloNavio(MouseEvent event, Navio navioAtual) {
        if (retanguloNavioAtual == null) {
            retanguloNavioAtual = new Rectangle(event.getX(), event.getY(), 30 * navioAtual.getTamanho(), 30);
            retanguloNavioAtual.setFill(Color.web(navioAtual.getCor()));
            retanguloNavioAtual.setVisible(true);
            GridPaneAliado.getChildren().add(retanguloNavioAtual);
        }
    }

    @FXML
    void clickedCorveta(MouseEvent event) {
        if (navioAtual != null)
            return;
        navioAtual = new Corveta();
        criarRetanguloNavio(event, navioAtual);

        System.out.println("Criou navio!");

        // Rectangle retangulo = new Rectangle(event.getX(), event.getY(),
        // 30*navioAtual.getTamanho(), 30);
        // retangulo.setFill(Color.GREEN);

        // Group root = new Group(rectangle);
        // TelaPrincipal.getChildren().add(retangulo);
    }

    @FXML
    void clickedDestroyer(MouseEvent event) {
        if (navioAtual != null)
            return;
        navioAtual = new Destroyer();
        criarRetanguloNavio(event, navioAtual);
    }

    @FXML
    void clickedFragata(MouseEvent event) {
        if (navioAtual != null)
            return;
        navioAtual = new Fragata();
        criarRetanguloNavio(event, navioAtual);
    }

    @FXML
    void clickedSubmarino(MouseEvent event) {
        if (navioAtual != null)
            return;
        navioAtual = new Submarino();
        criarRetanguloNavio(event, navioAtual);
    }

    @FXML
    void onMouseMovedGridPane(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        if (retanguloNavioAtual != null) {
            retanguloNavioAtual.setTranslateX(x);
            retanguloNavioAtual.setTranslateY(y);
        }
    }

    public Navio getNavioAtual() {
        return this.navioAtual;
    }

    public void setNavioAtual(Navio navioAtual) {
        this.navioAtual = navioAtual;
    }

    public boolean verificarDerrota(Jogador jogador) {
        if (jogador.getTabuleiro().naviosPerdidos() == 4) {
            return true;
        }
        return false;
    }

}
