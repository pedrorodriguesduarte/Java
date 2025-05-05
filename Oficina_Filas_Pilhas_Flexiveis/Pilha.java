import java.util.NoSuchElementException;

public class Pilha<E> {

    private Celula<E> topo;
    private Celula<E> fundo;

    public Pilha() {
        Celula<E> sentinela = new Celula<E>();
        fundo = sentinela;
        topo = sentinela;
    }

    public boolean vazia() {
        return fundo == topo;
    }

    public void empilhar(E item) {
        topo = new Celula<E>(item, topo);
    }

    public E desempilhar() {
        E desempilhado = consultarTopo();
        topo = topo.getProximo();
        return desempilhado;
    }

    public E consultarTopo() {
        if (vazia()) {
            throw new NoSuchElementException("Nao há nenhum item na pilha!");
        }
        return topo.getItem();
    }

    private Pilha<E> inverterPilha(Pilha<E> pilha) {
        Pilha<E> pilhaInvertida = new Pilha<>();
        for (Celula<E> i = pilha.topo; i != pilha.fundo; i = i.getProximo()) {
            pilhaInvertida.empilhar(i.getItem());
        }
        return pilhaInvertida;
    }

    public Pilha<E> subPilha(int numItens) {
        Pilha<E> subPilha = new Pilha<>();
        for (Celula<E> i = topo; i != fundo && numItens > 0; i = i.getProximo(), numItens--) {
            subPilha.empilhar(i.getItem());
        }
        subPilha = inverterPilha(subPilha);
        return subPilha;
    }

    public void imprimir() {
        System.out.println("Produtos recentes (do mais recente para o mais antigo):");
        for (Celula<E> i = topo; i != fundo; i = i.getProximo()) {
            System.out.println(i.getItem());
        }
    }
}
