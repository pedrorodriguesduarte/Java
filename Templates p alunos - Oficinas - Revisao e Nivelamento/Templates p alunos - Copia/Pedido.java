import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Pedido {

	/** Quantidade máxima de produtos de um pedido */
	private static final int MAX_PRODUTOS = 10;
	
	/** Porcentagem de desconto para pagamentos à vista */
	private static final double DESCONTO_PG_A_VISTA = 0.15;
	
	/** Vetor para armazenar os produtos do pedido */
	private Produto[] produtos;
	
	/** Data de criação do pedido */
	private LocalDate dataPedido;
	
	/** Indica a quantidade total de produtos no pedido até o momento */
	private int quantProdutos = 0;
	
	/** Indica a forma de pagamento do pedido sendo: 1, pagamento à vista; 2, pagamento parcelado */
	private int formaDePagamento;

    private StringBuilder append;
	
	/** Construtor do pedido.
	 *  Deve criar o vetor de produtos do pedido, 
	 *  armazenar a data e a forma de pagamento informadas para o pedido. 
	 */  
	
        
	
	/**
     * Inclui um produto neste pedido e aumenta a quantidade de produtos armazenados no pedido até o momento.
     * @param novo O produto a ser incluído no pedido
     * @return true/false indicando se a inclusão do produto no pedido foi realizada com sucesso.
     */
	public boolean incluirProduto(Produto novo) {
		if (quantProdutos < MAX_PRODUTOS){
            produtos[quantProdutos++]= novo;
            return true;
        }
		return true;
	}
	
	/**
     * Calcula e retorna o valor final do pedido (soma do valor de venda de todos os produtos do pedido).
     * Caso a forma de pagamento do pedido seja à vista, aplica o desconto correspondente.
     * @return Valor final do pedido (double)
     */
	public double valorFinal() {
		// TODO
		return 0.0;
	}
	
	/**
     * Representação, em String, do pedido.
     * Contém um cabeçalho com sua data e o número de produtos no pedido.
     * Depois, em cada linha, a descrição de cada produto do pedido.
     * Ao final, mostra a forma de pagamento, o percentual de desconto (se for o caso) e o valor a ser pago pelo pedido.
     * Exemplo:
     * Data do pedido: 25/08/2025
     * Pedido com 2 produtos.
     * Produtos no pedido:
     * NOME: Iogurte: R$ 8.00
     * Válido até: 29/08/2025
     * NOME: Guardanapos: R$ 2.75
     * Pedido pago à vista. Percentual de desconto: 15,00%
     * Valor total do pedido: R$ 10.75 
     * @return Uma string contendo dados do pedido conforme especificado (cabeçalho, detalhes, forma de pagamento,
     * percentual de desconto - se for o caso - e valor a pagar)
     */
	@Override
	public String toString() {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder sb= new StringBuilder();
         sb.append("Data : ").append(dataPedido.format(fmt))
        .append("| Pagamento : ").append((formaDePagamento ==1)? "A vista" : "Parcelado")
        .append("\nProdutos :\n");
		
        for (int i = 0; i < quantProdutos; i++) {
            sb.append(" - ").append(produtos[i].toString()).append("\n");
            
        }
        sb.append(" Valor final R$ ").append(String.format("%.2f", valorFinal()));
        return sb.toString();
            


	}
	
	/**
     * Igualdade de pedidos: caso possuam a mesma data. 
     * @param obj Outro pedido a ser comparado 
     * @return booleano true/false conforme o parâmetro possua a data igual ou não a este pedido.
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Pedido)) return false;
        Pedido outro = (Pedido) obj;
        return this.dataPedido.equals(outro.dataPedido)&&
                this.formaDePagamento == outro.formaDePagamento &&
                Arrays.equals(this.produtos, outro.produtos);
    	
    }


public LocalDate getDataPedido(){
    return dataPedido;
}

public int getQuantProdutos(){
    return quantProdutos;
}

public Produto[] geProdutos(){
    return Arrays.copyOf(produtos, quantProdutos);
}

public int getFormaDePagamento(){
    return formaDePagamento;
}
























}