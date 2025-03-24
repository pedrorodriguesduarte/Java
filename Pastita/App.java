import java.util.Arrays;
import java.util.Random;

public class App {
    static final int[] tamanhosTesteGrande =  { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio =   {     12_500,     25_000,      50_000,     100_000,     200_000 };
    static final int[] tamanhosTestePequeno = {          3,          6,          12,          24,          48 };
    static Random aleatorio = new Random();
    static long operacoes;
    static double nanoToMilli = 1.0/1_000_000;
    
    /**
     * Código de teste 1. Este método...
     * @param vetor Vetor com dados para teste.
     * @return Uma resposta que significa....
     */
    static int codigo1(int[] vetor) {
    	int resposta = 0;
        for (int i = 0; i < vetor.length; i += 2) {
            resposta += vetor[i]%2;
            operacoes++;
        }
        return resposta;
    }

    /**
     * Código de teste 2. Este método...
     * @param vetor Vetor com dados para teste.
     * @return Uma resposta que significa....
     */
    static int codigo2(int[] vetor) {
        int contador = 0;
        for (int k = (vetor.length - 1); k > 0; k /= 2) {
        	for (int i = 0; i <= k; i++) {
            	operacoes++;
                contador++;
            }
        }
        return contador;
    }

    /**
     * Código de teste 3. Este método...
     * @param vetor Vetor com dados para teste.
     */
    static void codigo3(int[] vetor) {
        for (int i = 0; i < vetor.length - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < vetor.length; j++) {
            	operacoes++;
                if (vetor[j] < vetor[menor])
                    menor = j;
            }
            int temp = vetor[i];
            vetor[i] = vetor[menor];
            vetor[menor] = temp;
        }
    }

    /**
     * Código de teste 4 (recursivo). Este método...
     * @param n Ponto inicial do algoritmo
     * @return Um inteiro que significa...
     */
    static int codigo4(int n) {    	
    	operacoes++;       
    	if (n <= 2)
            return 1;
        else
            return codigo4(n - 1) + codigo4(n - 2);
    }

    /**
     * Gerador de vetores aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static int[] gerarVetor(int tamanho){
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, tamanho/2);
        }
        return vetor;        
    }

    /**
     * Gerador de vetores de objetos do tipo Integer aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor de Objetos Integer com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static Integer[] gerarVetorObjetos(int tamanho) {
        Integer[] vetor = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, 10 * tamanho);
        }
        return vetor;
    }

    static double marcarTempo(int[] vetor){
            operacoes = 0;
    		long inicio = System.nanoTime();
    		codigo1(vetor);
    		return (System.nanoTime() - inicio)*nanoToMilli;
    }
    
    public static void main0(String[] args) {
        double inicio, duracao;

        for (int tam : tamanhosTesteGrande) {
            int[] vetor = gerarVetor(tam);
            operacoes = 0;
            inicio = System.nanoTime();
            codigo1(vetor);
            duracao = (System.nanoTime() - inicio)*nanoToMilli;
            System.out.printf("Codigo1 - Tamanho: %,d || Operacoes: %,d || Duração: %,2f ms\n", tam, operacoes, duracao);
        }

        for (int tam : tamanhosTesteGrande) {
            int[] vetor = gerarVetor(tam);
            operacoes = 0;
            inicio = System.nanoTime();
            codigo2(vetor);
            duracao = (System.nanoTime() - inicio)*nanoToMilli;
            System.out.printf("Codigo2 - Tamanho: %,d || Operacoes: %,d || Duração: %,2f ms\n", tam, operacoes, duracao);
        }

        for (int tam : tamanhosTesteMedio) {
            int[] vetor = gerarVetor(tam);
            operacoes = 0;
            inicio = System.nanoTime();
            codigo3(vetor);
            duracao = (System.nanoTime() - inicio)*nanoToMilli;
            System.out.printf("Codigo3 - Tamanho: %,d || Operacoes: %,d || Duração: %,2f ms\n", tam, operacoes, duracao);
        }

        for (int tam : tamanhosTestePequeno) {
            operacoes = 0;
            inicio = System.nanoTime();
            codigo4(tam);
            duracao = (System.nanoTime() - inicio)*nanoToMilli;
            System.out.printf("Codigo4 - Tamanho: %,d || Operacoes: %,d || Duração: %,2f ms\n", tam, operacoes, duracao);
        }
    }

    public static void main(String[] args) {
        int tam = 3500;
        Integer[] vetor = gerarVetorObjetos(tam);

        System.out.println("Vetor original:");
        System.out.println(Arrays.toString(vetor));

        /* Método de Ordenação Bolha */
        BubbleSort<Integer> bolha = new BubbleSort<>();
        Integer[] vetorOrdenadoBolha = bolha.ordenar(vetor);

        System.out.println("\nVetor ordenado método Bolha:");
        System.out.println(Arrays.toString(vetorOrdenadoBolha));
        System.out.println("Comparações: " + bolha.getComparacoes());
        System.out.println("Movimentações: " + bolha.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + bolha.getTempoOrdenacao());

        /* Método de Ordenação por Inserção */
        InsertionSort<Integer> insercao = new InsertionSort<>();
        Integer[] vetorOrdenadoInsercao = insercao.ordenar(vetor);

        System.out.println("\nVetor ordenado método Insercao:");
        System.out.println(Arrays.toString(vetorOrdenadoInsercao));
        System.out.println("Comparações: " + insercao.getComparacoes());
        System.out.println("Movimentações: " + insercao.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + insercao.getTempoOrdenacao());

        /* Fazer a implementacao do restante do main para a ordenacao 
        *  com o algoritmo MergeSort */
        //TO DO//

        MergeSort<Integer> merge = new MergeSort<>();

        Integer[] vetorOrdenadoMerge = merge.ordenar(vetor);

        System.out.println("Vetor original:");
        System.out.println(Arrays.toString(vetor));

        System.out.println("\nVetor ordenado método Merge:");
        System.out.println(Arrays.toString(vetorOrdenadoMerge));
        System.out.println("Comparações: " + merge.getComparacoes());
        System.out.println("Movimentações: " + merge.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + merge.getTempoOrdenacao());





    }
}
