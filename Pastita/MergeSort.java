import java.util.Arrays;
import java.lang.reflect.Array;

public class MergeSort<T extends Comparable<T>> implements IOrdenador<T> {
    private int comparacoes;
    private int movimentacoes;
    private double tempoOrdenacao;
    private double inicio;

    private double nanoToMilli = 1.0/1_000_000;

    @Override
    public int getComparacoes() {
        return comparacoes;
    }

    @Override
    public int getMovimentacoes() {
        return movimentacoes;
    }

    @Override
    public double getTempoOrdenacao() {
        return tempoOrdenacao;
    }

    public Class<?> getClasseDoConteudo(T[] vetor) {
        for (T elemento : vetor) {
            if (elemento != null) {
                return elemento.getClass();
            }
        }
        return null; // Se o vetor estiver vazio ou só tiver nulos
    }

    private void iniciar(){
        this.comparacoes = 0;
        this.movimentacoes = 0;
        this.inicio = System.nanoTime();
    }

    private void terminar(){
        this.tempoOrdenacao = (System.nanoTime() - this.inicio) * nanoToMilli;
    }

    /**
    * Algoritmo de ordenação Mergesort.
    * @param int esq: início do array a ser ordenado
    * @param int dir: fim do array a ser ordenado
    */
    // 1.a chamada do método mergesort: esq: 0; dir: array.length - 1
    private void mergesort(T[] array, int esq, int dir) {
        if (esq < dir) {
            int meio = (esq + dir) / 2;
            mergesort(array, esq, meio);
            mergesort(array, meio + 1, dir);
            intercalar(array, esq, meio, dir);
        }
    }

    /**
    * Algoritmo que intercala os elementos localizados entre as posições esq e dir
    * @param int esq: início do array a ser ordenado
    * @param int meio: posição do meio do array a ser ordenado
    * @param int dir: fim do array a ser ordenado
    */ 
    private void intercalar(T[] array, int esq, int meio, int dir) {

        int n1, n2, i, j, k;

        //Definir tamanho dos dois subarrays
        n1 = meio - esq + 1;
        n2 = dir - meio;

        T[] a1 = (T[]) Array.newInstance(getClasseDoConteudo(array), n1);
        T[] a2 = (T[]) Array.newInstance(getClasseDoConteudo(array), n2);

        //Inicializar primeiro subarray
        for (i = 0; i < n1; i++) {
            a1[i] = array[esq + i];
            movimentacoes++;
        }

        //Inicializar segundo subarray
        for (j = 0; j < n2; j++) {
            a2[j] = array[meio + j + 1];
            movimentacoes++;
        }

        //Intercalação propriamente dita
        for (i = j = 0, k = esq; (i < n1 && j < n2); k++) {
            comparacoes++;
            movimentacoes++;
            if (a1[i].compareTo(a2[j]) <= 0) 
                array[k] = a1[i++];
            else
                array[k] = a2[j++];
        }

        for (i = j = 0, k = esq; (i < n1 && j < n2); k++) {
            comparacoes++;
            movimentacoes++;
            if (a1[i].compareTo(a2[j]) <= 0)
                array[k] = a1[i++];
            else
                array[k] = a2[j++];
        }
        
        if (i == n1)
            for (; k <= dir; k++) {
                array[k] = a2[j++];
                movimentacoes++;
            }
        else
            for (; k <= dir; k++) {
                array[k] = a1[i++];
                movimentacoes++;
            }
    }

    @Override
    public T[] ordenar(T[] dados) {
        T[] dadosOrdenados = Arrays.copyOf(dados, dados.length);
        int tamanho = dadosOrdenados.length;
        iniciar();
        mergesort(dadosOrdenados, 0, tamanho - 1);
        terminar();
        return dadosOrdenados;
    }
}
