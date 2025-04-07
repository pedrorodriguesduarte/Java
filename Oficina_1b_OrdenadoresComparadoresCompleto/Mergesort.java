import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

public class Mergesort<T extends Comparable<T>> implements IOrdenador<T> {

    private long comparacoes;
    private long movimentacoes;
    private LocalDateTime inicio;
    private LocalDateTime termino;

    public Mergesort() {
        comparacoes = 0;
        movimentacoes = 0;
    }

    @Override
    public T[] ordenar(T[] dados) {
        return ordenar(dados, T::compareTo);
    }

    @Override
    public T[] ordenar(T[] dados, Comparator<T> comparador) {
        T[] dadosOrdenados = Arrays.copyOf(dados, dados.length);
        inicio = LocalDateTime.now();
        mergesort(dadosOrdenados, 0, dadosOrdenados.length - 1, comparador);
        termino = LocalDateTime.now();
        return dadosOrdenados;
    }

    private void mergesort(T[] array, int esquerda, int direita, Comparator<T> comp) {
        if (esquerda < direita) {
            int meio = (esquerda + direita) / 2;
            mergesort(array, esquerda, meio, comp);
            mergesort(array, meio + 1, direita, comp);
            intercalar(array, esquerda, meio, direita, comp);
        }
    }

    private void intercalar(T[] array, int esquerda, int meio, int direita, Comparator<T> comp) {
        int n1 = meio - esquerda + 1;
        int n2 = direita - meio;

        T[] L = Arrays.copyOfRange(array, esquerda, meio + 1);
        T[] R = Arrays.copyOfRange(array, meio + 1, direita + 1);

        int i = 0, j = 0, k = esquerda;
        while (i < n1 && j < n2) {
            comparacoes++;
            if (comp.compare(L[i], R[j]) <= 0) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            movimentacoes++;
            k++;
        }

        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
            movimentacoes++;
        }

        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
            movimentacoes++;
        }
    }

    public long getComparacoes() {
        return comparacoes;
    }

    public long getMovimentacoes() {
        return movimentacoes;
    }

    public double getTempoOrdenacao() {
        return Duration.between(inicio, termino).toMillis();
    }
}
