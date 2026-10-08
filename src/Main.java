import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;


// Classe que representa um nó da pilha
class NoPilha {
    private int x;
    private int y;
    private NoPilha abaixo;

    public NoPilha(int x, int y) {
        this.x = x;
        this.y = y;
        this.abaixo = null;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public NoPilha getAbaixo() { return abaixo; }
    public void setAbaixo(NoPilha abaixo) { this.abaixo = abaixo; }
}

// Classe que representa a pilha
class Pilha {
    private NoPilha topo;

    public Pilha() {
        this.topo = null;
    }

    public void push(int x, int y) {
        NoPilha novo = new NoPilha(x, y);
        novo.setAbaixo(this.topo);
        this.topo = novo;
    }

    public NoPilha pop() {
        if (isEmpty()) {
            return null;
        }
        NoPilha removido = this.topo;
        this.topo = this.topo.getAbaixo();
        removido.setAbaixo(null);
        return removido;
    }

    public boolean isEmpty() {
        return this.topo == null;
    }
}

// Classe que representa um nó da fila
class NoFila {
    private int x;
    private int y;
    private NoFila proximo;

    public NoFila(int x, int y) {
        this.x = x;
        this.y = y;
        this.proximo = null;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public NoFila getProximo() { return proximo; }
    public void setProximo(NoFila proximo) { this.proximo = proximo; }
}

// Classe que representa a fila
class Fila {
    private NoFila inicio;
    private NoFila fim;

    public Fila() {
        this.inicio = null;
        this.fim = null;
    }

    public void enqueue(int x, int y) {
        NoFila novo = new NoFila(x, y);

        if (isEmpty()) {
            this.inicio = novo;
            this.fim = novo;
        } else {
            this.fim.setProximo(novo);
            this.fim = novo;
        }
    }

    public NoFila dequeue() {
        if (isEmpty()) {
            return null;
        }
        NoFila removido = this.inicio;
        this.inicio = this.inicio.getProximo();

        if (this.inicio == null) {
            this.fim = null;
        }

        removido.setProximo(null);
        return removido;
    }

    public boolean isEmpty() {
        return this.inicio == null;
    }
}


// Classe responsável por carregar e salvar imagens
class ImageService {
    private BufferedImage imagemAtual; // Armazena a imagem carregada atualmente

    public boolean carregarImagem(String caminho) {
        try {
            File arquivo = new File(caminho);
            this.imagemAtual = ImageIO.read(arquivo);

            if (this.imagemAtual == null) {
                System.out.println("Erro: O caminho especificado não contém uma imagem válida.");
                return false;
            }

            System.out.println("Sucesso! Imagem carregada (Largura: " + imagemAtual.getWidth() + "px, Altura: " + imagemAtual.getHeight() + "px)");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao abrir a imagem. Tem certeza que o caminho '" + caminho + "' tá certo?");
            return false;
        }
    }

    public void salvarImagem(String caminhoSaida) { // Salva a imagem atual em um arquivo BMP
        try {
            if (this.imagemAtual == null) {
                System.out.println("Erro: Nenhuma imagem foi carregada ainda.");
                return;
            }

            File arquivoSaida = new File(caminhoSaida);

            ImageIO.write(this.imagemAtual, "bmp", arquivoSaida);
            System.out.println("Imagem salva em: " + caminhoSaida);
            
        } catch (Exception e) {
            System.out.println("Erro ao salvar a imagem: " + e.getMessage());
        }
    }

    public BufferedImage getImagemAtual() {
        return this.imagemAtual;
    }
}




// Classe principal do programa
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ImageService geradorImagens = new ImageService(); // Instância do serviço de imagens

        while (true) {
            System.out.println("\n=================================");
            System.out.println("1 - Executar com pilha");
            System.out.println("2 - Executar com fila");
            System.out.println("3 - Escolher imagem");
            System.out.println("4 - Escolher coordenada de inicio");
            System.out.println("0 - Encerrar");
            System.out.println("=================================");
            int opcao = input.nextInt();
            input.nextLine(); // Limpar o buffer

            switch (opcao) {
                // Executar com pilha
                case 1:
                    System.out.println("Executando com pilha...");
                    geradorImagens.salvarImagem("saida.bmp"); 
                    break;
                // Executar com fila
                case 2:
                    System.out.println("Executando com fila...");
                    break;
                // Escolher imagem
                case 3:
                    System.out.println("Digite o caminho da imagem:");
                    String caminho = input.nextLine();
                    geradorImagens.carregarImagem(caminho);
                    break;
                // Escolher coordenada de inicio
                case 4:
                    System.out.println("Escolhendo coordenada de inicio...");
                    break;
                // Encerrar
                case 0:
                    System.out.println("Encerrando...");
                    input.close(); 
                    return;
                // Opção inválida
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}