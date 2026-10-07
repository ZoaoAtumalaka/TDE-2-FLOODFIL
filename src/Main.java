import java.util.Scanner;
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
public class Main{
    public static void main(String[] args){
        Scanner input=new Scanner(System.in);
        while (true){
            System.out.println("1 - Executar com pilha");
            System.out.println("2 - Executar com fila");
            System.out.println("3 - Escolher imagem");
            System.out.println("4 – Escolher coordenada de inicio");
            System.out.println("0 - Encerrar");
            input.
        }
    }
}