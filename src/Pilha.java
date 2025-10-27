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

    // Métodos alias para compatibilidade com App.java

    public void push(E item) {
        empilhar(item);
    }

    public E pop() {
        return desempilhar();
    }

    public boolean isEmpty() {
        return vazia();
    }

    /**
     * Cria e devolve uma nova pilha contendo os primeiros numItens elementos
     * do topo da pilha atual.
     * Os elementos são mantidos na mesma ordem em que estavam na pilha original.
     * Caso a pilha atual possua menos elementos do que o valor especificado,
     * uma exceção será lançada.
     *
     * @param numItens o número de itens a serem copiados da pilha original.
     * @return uma nova instância de Pilha<E> contendo os numItens primeiros elementos.
     * @throws IllegalArgumentException se a pilha não contém numItens elementos.
     */
    public Pilha<E> subPilha(int numItens) {
        if (numItens < 0) {
            throw new IllegalArgumentException("Número de itens deve ser positivo");
        }

        Pilha<E> novaPilha = new Pilha<>();
        Pilha<E> pilhaAuxiliar = new Pilha<>();

        int contador = 0;
        Celula<E> atual = topo;

        while (contador < numItens) {
            if (atual == fundo) {
                throw new IllegalArgumentException("A pilha não contém " + numItens + " elementos");
            }
            pilhaAuxiliar.empilhar(atual.getItem());
            atual = atual.getProximo();
            contador++;
        }

        // Inverter a pilha auxiliar para manter a ordem correta na nova pilha
        while (!pilhaAuxiliar.vazia()) {
            novaPilha.empilhar(pilhaAuxiliar.desempilhar());
        }

        return novaPilha;
    }
}
