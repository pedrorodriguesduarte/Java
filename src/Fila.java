import java.util.function.Function;
import java.util.function.Predicate;
import java.util.NoSuchElementException;

public class Fila<E> {

    private Celula<E> frente;
    private Celula<E> tras;

    public Fila() {
        Celula<E> sentinela = new Celula<E>();
        frente = sentinela;
        tras = sentinela;
    }

    public boolean vazia() {
        return frente == tras;
    }

    public void inserir(E item) {
        Celula<E> nova = new Celula<E>(item, null);
        tras.setProximo(nova);
        tras = nova;
    }

    public E remover() {
        if (vazia()) {
            throw new NoSuchElementException("A fila está vazia!");
        }
        Celula<E> primeira = frente.getProximo();
        E item = primeira.getItem();
        frente.setProximo(primeira.getProximo());
        if (primeira == tras) {
            tras = frente;
        }
        return item;
    }

    public E consultarInicio() {
        if (vazia()) {
            throw new NoSuchElementException("A fila está vazia!");
        }
        return frente.getProximo().getItem();
    }

    public boolean isEmpty() {
        return vazia();
    }

    // 🔹 TAREFA 2
    public double calcularValorMedio(Function<E, Double> extrator, int quantidade) {
        if (quantidade <= 0 || vazia()) {
            throw new IllegalArgumentException("Quantidade inválida ou fila vazia!");
        }

        double soma = 0.0;
        int cont = 0;
        Fila<E> auxiliar = new Fila<>();

        // percorre a fila sem perder os elementos
        while (!vazia() && cont < quantidade) {
            E elemento = remover();
            soma += extrator.apply(elemento);
            auxiliar.inserir(elemento);
            cont++;
        }

        // devolve os elementos para a fila original
        while (!auxiliar.vazia()) {
            inserir(auxiliar.remover());
        }

        return soma / cont;
    }

    // 🔹 TAREFA 3
    public Fila<E> filtrar(Predicate<E> condicional, int quantidade) {
        if (quantidade <= 0 || vazia()) {
            throw new IllegalArgumentException("Quantidade inválida ou fila vazia!");
        }

        Fila<E> novaFila = new Fila<>();
        Fila<E> auxiliar = new Fila<>();
        int cont = 0;

        // percorre os primeiros "quantidade" elementos
        while (!vazia() && cont < quantidade) {
            E elemento = remover();
            if (condicional.test(elemento)) {
                novaFila.inserir(elemento);
            }
            auxiliar.inserir(elemento);
            cont++;
        }

        // devolve tudo pra fila original
        while (!auxiliar.vazia()) {
            inserir(auxiliar.remover());
        }

        return novaFila;
    }
}
