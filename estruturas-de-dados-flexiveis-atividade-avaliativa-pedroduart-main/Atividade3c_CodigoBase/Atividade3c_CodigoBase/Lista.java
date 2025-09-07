
import java.util.function.Function;
import java.util.function.Predicate;

public class Lista<E>{

	private Celula<E> primeiro;
	private Celula<E> ultimo;
	private int tamanho;

    /** Cria uma lista vazia com elemento sentinela */
	public Lista() {
		
		Celula<E> sentinela = new Celula<>();
		
		this.primeiro = this.ultimo = sentinela;
		this.tamanho = 0;
	}
	
    /**
     * Indica se a lista está vazia ou não
     * @return TRUE/FALSE conforme a lista esteja vazia ou não 
     */
	public boolean vazia() {
		
		return (this.primeiro == this.ultimo);
	}
	
    /**
     * Insere um elemento na posição final da lista.
     * @param elemento Elemento a ser inserido.
     */
    public void inserir(E elemento) {
        inserir(elemento, tamanho);
    }

    /**
     * Insere um novo elemento na posição indicada. A posição máxima é o tamanho da lista e a mínima é 0.
     * @param novo Elemento a ser inserido.
     * @param posicao Posição de referência para inserção.
     * @throws IndexOutOfBoundsException em caso de posição inválida
     */
	public void inserir(E novo, int posicao) {
		
		Celula<E> anterior, novaCelula, proximaCelula;
		
		if ((posicao < 0) || (posicao > this.tamanho))
			throw new IndexOutOfBoundsException("Não foi possível inserir o item na lista: "
					+ "a posição informada é inválida!");
		
		anterior = this.primeiro;
		for (int i = 0; i < posicao; i++)
			anterior = anterior.getProximo();
				
		novaCelula = new Celula<>(novo);
			
		proximaCelula = anterior.getProximo();
			
		anterior.setProximo(novaCelula);
		novaCelula.setProximo(proximaCelula);
			
		if (posicao == this.tamanho)  // a inserção ocorreu na última posição da lista
			this.ultimo = novaCelula;
			
		this.tamanho++;		
	}

    /**
     * Remove o último elemento da lista.
     * @return O elemento removido
     * @throws IllegalStateException em caso de lista vazia
     */
	public E remover() {
        return remover(tamanho-1);
     }
 
     
     /**
      * Remove um elemento da posição indicada. Se a lista estiver vazia ou a posição for inválida (<0 ou >=tamanho),
      * gera exceções. O primeiro elemento da lista é considerado como posição 0.
      * @param posicao Posição do elemento a ser retirado (>=0 e < tamanho)
      * @return Elemento removido da lista
      * @throws IllegalStateException se a lista estiver vazia
      * @throws IndexOutOfBoundsException em caso de posição inválida
      */
	public E remover(int posicao) {
		
		Celula<E> anterior, celulaRemovida, proximaCelula;
		
		if (vazia())
			throw new IllegalStateException("Não foi possível remover o item da lista: "
					+ "a lista está vazia!");
		
		if ((posicao < 0) || (posicao >= this.tamanho ))
			throw new IndexOutOfBoundsException("Não foi possível remover o item da lista: "
					+ "a posição informada é inválida!");
			
		anterior = this.primeiro;
		for (int i = 0; i < posicao; i++)
			anterior = anterior.getProximo();
				
		celulaRemovida = anterior.getProximo();
				
		proximaCelula = celulaRemovida.getProximo();
				
		anterior.setProximo(proximaCelula);
		celulaRemovida.setProximo(null);
				
		if (celulaRemovida == this.ultimo)
			this.ultimo = anterior;
				
		this.tamanho--;
				
		return (celulaRemovida.getItem());	
	}

    /**
     * Retorna, sem retirar, um elemento na posição indicada pelo parâmetro. A primeira posição da lista é 
     * considerada posição 0 e, assim, a última é (tamanho-1). Lança exceções para lista vazia ou posições inválidas.
     * @param posicao Posição do elemento a ser consultado (>=0 e < tamanho)
     * @return O elemento da posição indicada na lista (consulta sem retirada)
     * @throws IllegalStateException se a lista estiver vazia
     * @throws IndexOutOfBoundsException em caso de posição inválida 
     */
    public E elementoNaPosicao(int posicao) {
		
		Celula<E> aux;
		
		if (vazia())
			throw new IllegalStateException("Não foi possível consultar o item da lista: "
					+ "a lista está vazia!");
		
		if ((posicao < 0) || (posicao >= this.tamanho ))
			throw new IndexOutOfBoundsException("Não foi possível consultar o item da lista: "
					+ "a posição informada é inválida! Tamaho da lista: "+tamanho);
			
		aux = primeiro.getProximo();
		for (int i = 0; i < posicao; i++)
			aux = aux.getProximo();
				
		return (aux.getItem());	
	}

    /**
     * Localiza um elemento na lista de acordo com um Predicado. Retorna o primeiro elemento na lista
     * que atende àquele predicado ou nulo caso não exista. Causa exceções em listas vazias.
     * @param condicional Predicado com a condição para encontrar um elemento.
     * @return O primeiro elemento encontrado que atenda à condição ou nulo, caso não haja.
     * @throws IllegalStateException se a lista estiver vazia
     */
    public E localizar(Predicate<E> condicional) {	
		Celula<E> aux;
		
		if (vazia())
			throw new IllegalStateException("Não foi possível localizar o item na lista: "
					+ "a lista está vazia!");
		
        aux = primeiro.getProximo();
        while (aux != null) {
            if(condicional.test(aux.getItem()))
                return aux.getItem();
            aux = aux.getProximo();
        }
        return null;	
	}

    /**
     * Conta quantos elementos na lista atendem à condição estabelecida pelo predicado.
     * @param condicional Predicado com a condição para verificação de elementos na lista
     * @return inteiro com a quantidade de elementos que atendem ao predicado (inteiro não-negativo)
     */
    public int contarRepeticoes(Predicate<E> condicional) {
		int contador = 0;
		Celula<E> aux = primeiro.getProximo(); // começa após a sentinela
	
		while (aux != null) {
			if (condicional.test(aux.getItem())) {
				contador++;
			}
			aux = aux.getProximo();
		}
	
		return contador;
	}
	

    /**
	 * Calcula e retorna o valor médio de um determinado atributo dos elementos da lista,
	 * utilizando uma função de extração fornecida.
	 *
	 * @param extrator uma função que extrai um valor numérico (Double) de cada elemento da lista.
	 * @return o valor médio dos atributos extraídos dos elementos.
	 */
	public double calcularValorTotal(Function<E, Double> extrator) {
		
		double soma = 0;
		Celula<E> aux = primeiro.getProximo();
		
		if (vazia()) {
			throw new IllegalStateException("A lista está vazia!");
		}
		
		while (aux != null) {
			soma += extrator.apply(aux.getItem());
			aux = aux.getProximo();
		}
		return soma;
	}

    /**
     * Retorna a quantidade atual de elementos na lista.
     * @return Inteiro não negativo com a quantidade atual de elementos na lista.
     */
    public int tamanho(){
        return tamanho;
    }

    /**
     * Retorna uma string com informação detalhada de cada elemento da lista. 
     * A string indica as posições dos elementos, iniciando-se em 0.
     * @return Uma string com as informações de cada elemento da lista
     */
    @Override
	public String toString() {
		Celula<E> aux;
		StringBuilder listaString = new StringBuilder("A Lista está vazia!");
		
	    if(!vazia()){
            int contador = 0;
			aux = primeiro.getProximo();
			while (aux != null) {
                String dado = String.format("Posição %d: %s\n", contador, aux.getItem().toString());
				listaString.append(dado);
				aux = aux.getProximo();
                contador++;
			}
		} 	
        return listaString.toString();
	}
}
