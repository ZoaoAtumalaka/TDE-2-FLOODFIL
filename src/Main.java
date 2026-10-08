import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;

class Posicao {
    public int x;
    public int y;

    public Posicao(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class NoPilha {
    public Posicao pos;
    public NoPilha abaixo;

    public NoPilha(Posicao pos) {
        this.pos = pos;
        this.abaixo = null;
    }
}

class Pilha {
    public NoPilha topo;

    public Pilha() {
        this.topo = null;
    }

    public void push(Posicao pos) {
        NoPilha no_novo = new NoPilha(pos);
        no_novo.abaixo = this.topo;
        this.topo = no_novo;
    }

    public Posicao pop() {
        if (this.topo == null) return null;

        NoPilha no_removido = this.topo;
        this.topo = this.topo.abaixo;
        return no_removido.pos;
    }

    public boolean isEmpty() {
        return this.topo == null;
    }
}

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
class ImagemService {
    public BufferedImage imagem_atual;

    public void carregarImagem(String caminho) {
        try {
            File arquivo = new File(caminho);
            this.imagem_atual = ImageIO.read(arquivo);

            if (this.imagem_atual == null) {
                System.out.println("Erro ao carregar imagem.");
            } else {
                System.out.println("Imagem carregada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao abrir arquivo.");
        }
    }

    public void salvarImagem(String caminho_saida, boolean exibir_mensagem) {
        try {
            if (this.imagem_atual != null) {
                File arquivo_saida = new File(caminho_saida);
                ImageIO.write(this.imagem_atual, "bmp", arquivo_saida);
                if (exibir_mensagem) {
                    System.out.println("Imagem salva: " + caminho_saida);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar imagem.");
        }
    }
}
class FloodFill {

    public static void preencherComPilha(ImagemService service, int startX, int startY, int novaCor) {
        BufferedImage img = service.imagem_atual;
        int largura = img.getWidth();
        int altura = img.getHeight();

        if (startX < 0 || startX >= largura || startY < 0 || startY >= altura) {
            System.out.println("Coordenada fora da imagem.");
            return;
        }

        int cor_original = img.getRGB(startX, startY);
        if (cor_original == novaCor) {
            System.out.println("Cor igual à original.");
            return;
        }

        Pilha pilha = new Pilha();
        pilha.push(new Posicao(startX, startY));

        int passos = 0;
        int frames = 0;

        while (!pilha.isEmpty()) {
            Posicao pos_atual = pilha.pop();
            int x = pos_atual.x;
            int y = pos_atual.y;

            if (x < 0 || x >= largura || y < 0 || y >= altura) continue;
            if (img.getRGB(x, y) != cor_original) continue;

            img.setRGB(x, y, novaCor);
            passos++;

            if (passos % 300 == 0) {
                frames++;
                service.salvarImagem(String.format("pilha_passo_%04d.bmp", frames), false);
            }

            pilha.push(new Posicao(x, y - 1));
            pilha.push(new Posicao(x, y + 1));
            pilha.push(new Posicao(x - 1, y));
            pilha.push(new Posicao(x + 1, y));
        }

        frames++;
        service.salvarImagem(String.format("pilha_passo_%04d.bmp", frames), true);
        System.out.println("Concluído. Pixels alterados: " + passos);
    }

}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ImagemService imagemService = new ImagemService();

        int X_inicial = -1;
        int Y_inicial = -1;

        while (true) {
            System.out.println("1 - Executar com pilha");
            System.out.println("2 - Executar com fila");
            System.out.println("3 - Escolher imagem");
            System.out.println("4 - Escolher coordenada de inicio");
            System.out.println("0 - Encerrar");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                // Executar com pilha
                case 1:
                    if (imagemService.imagem_atual == null) {
                        System.out.println("Carregue uma imagem primeiro.");
                    } else if (X_inicial == -1 || Y_inicial == -1) {
                        System.out.println("Defina a coordenada inicial primeiro.");
                    } else {
                        System.out.println("Digite a nova cor RGB (R G B):");
                        int r = scanner.nextInt();
                        int g = scanner.nextInt();
                        int b = scanner.nextInt();
                        int nova_cor = new Color(r, g, b).getRGB();

                        System.out.println("Executando com pilha...");
                        FloodFill.preencherComPilha(imagemService, X_inicial, Y_inicial, nova_cor);
                    }
                    break;
                // Executar com fila
                case 2:
                    System.out.println("Executando com fila...");
                    break;
                // Escolher imagem
                case 3:
                    System.out.print("Caminho da imagem: ");
                    String B = scanner.nextLine();
                    imagemService.carregarImagem(B);
                    break;
                // Escolher coordenada de inicio
                case 4:
                    System.out.println("Escolhendo coordenada de inicio...");
                    break;
                // Encerrar
                case 0:
                    System.out.println("Encerrando...");
                    scanner.close();
                    return;
                // Opção inválida
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}