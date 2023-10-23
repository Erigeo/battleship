/*import java.util.Arrays;
import java.util.Random;

public class Campo {

    // definir o que vai em cada coordenada
    // inciar o campo yas
    // preencher o campo yas
    // inserir os navios yas

    char[][] tabuleiro = new char[10][10];

    public void preencherCampo() {
        for (char[] x : tabuleiro) {
            Arrays.fill(x, '~');
        }

    }

    
    
    public void inserirNaviodeCertoTipo(int quantidadeDeNavios, Navio x) {
        int[] coordenada = aleatorizador_navios();
        for (int i = 0; i < quantidadeDeNavios; i++) {
            if (x instanceof destroyer) {
                tabuleiro[coordenada[0]][coordenada[1]] = 'N';
                tabuleiro[coordenada[0] + 1][coordenada[1]] = 'N';
                tabuleiro[coordenada[0] - 1][coordenada[1]] = 'N';
                tabuleiro[coordenada[0]][coordenada[1] + 1] = 'N';
                tabuleiro[coordenada[0]][coordenada[1] + 2] = 'N';
                // lembrar de criar um verificador para saber se vai ser -1 ou +1 para os 2
                // ultimos casos acima, uma vez que pode ocorrer a situação de querer
                // ultrapassar o tamanho do campo! no caso do destroyer ele vai sempre precisar
                // de um espaco 3x3 para garantir sua existencia!
            }

        }

    }

    // decidir ainda com o yves quantos tipos de navios vai ter em campo e a sua
    // quantidade!
    public int[] aleatorizador_navios() {
        int[] coordenadas = new int[2];
        for (int i = 0; i < coordenadas.length; i++) {
            coordenadas[i] = new Random().nextInt(10);
        }

        // meu plano é que dependendo do tipo de navio, o aleatorizador sorteei duas
        // casas limitadas atra´ves do random().nextInt(10), e a partir da coordenada
        // sorteada ele guarde no arraylist as outras coordendas necessarias para
        // preencher todo o navio no nosso tabuleiro.

        // vai ser a mesma logica do random, limitado. a partir da primeira coordenada
        // aleatorizada seguimos em frente com as outras!
        return coordenadas;

    }

    public void atirar(Navio x, int coluna, int linha) {
        if (verificarTiro(coluna, linha) == true) {

            // x.atirar(coluna, linha);
            tabuleiro[linha][coluna] = '!';
            x.setPecasRestantes(x.getPecasRestantes() - 1);
        }

        if (x.getAlcanceDisparo() == 3) {
            atirar(x, coluna-1, linha - 1);
        }
    }



    public boolean verificarTiro(int coluna, int linha) {

        if (tabuleiro[linha][coluna] == 'N') {
            return true;
        } else {
            return false;
        }

    }

    // comando atirar vai ser em navios, usar itnerfaces
    // duvida se o inserir navios seria tamebm em navios hehe

}

*/