import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class App {

	/** Nome do arquivo de dados. O arquivo deve estar localizado na raiz do projeto */
    static String nomeArquivoDados;
    
    /** Scanner para leitura de dados do teclado */
    static Scanner teclado;

    /** Lista de produtos cadastrados */
    static Lista<Produto> produtosCadastrados;

    /** Quantidade de produtos cadastrados atualmente na lista */
    static int quantosProdutos = 0;

    static Lista<Pedido> listaPedidos = new Lista<>();
    
    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /** Gera um efeito de pausa na CLI. Espera por um enter para continuar */
    static void pausa() {
        System.out.println("Digite enter para continuar...");
        teclado.nextLine();
    }

    /** Cabeçalho principal da CLI do sistema */
    static void cabecalho() {
        limparTela();
        System.out.println("AEDs II COMÉRCIO DE COISINHAS");
        System.out.println("=============================");
    }
    
    @SuppressWarnings("unchecked")
    static <T extends Number> T lerOpcao(String mensagem, Class<T> classe) {
      
    	T valor =(T)Integer.valueOf(-1);
        
    	System.out.print(mensagem+" ");
    	try {
            valor = (T)classe.getMethod("valueOf", String.class).invoke(valor, teclado.nextLine());
        } catch (IllegalAccessException | IllegalArgumentException 
        		| InvocationTargetException | NoSuchMethodException | SecurityException e) {
            valor = (T)Integer.valueOf(-1); 
        }
        return valor;
    }
    
    /** Imprime o menu principal, lê a opção do usuário e a retorna (int).
     * Perceba que poderia haver uma melhor modularização com a criação de uma classe Menu.
     * @return Um inteiro com a opção do usuário.
    */
    static int menu() {
        cabecalho();
        System.out.println("1 - Listar todos os produtos");
        System.out.println("2 - Procurar por um produto, por código");
        System.out.println("3 - Procurar por um produto, por nome");
        System.out.println("4 - Iniciar novo pedido");
        System.out.println("5 - Fechar pedido");
        System.out.println("6 - Repeticoes de um produto em um pedido");
        System.out.println("0 - Sair");
        return lerOpcao("Digite sua opção: ", Integer.class);
    }
    
    /**
     * Lê os dados de um arquivo-texto e retorna uma lista de produtos. Arquivo-texto no formato
     * N  (quantidade de produtos) <br/>
     * tipo;descrição;preçoDeCusto;margemDeLucro;[dataDeValidade] <br/>
     * Deve haver uma linha para cada um dos produtos. Retorna uma lista vazia em caso de problemas com o arquivo.
     * @param nomeArquivoDados Nome do arquivo de dados a ser aberto.
     * @return Uma lista com os produtos carregados, ou vazia em caso de problemas de leitura.
     */
    static Lista<Produto> lerProdutos(String nomeArquivoDados) {
    	
    	Scanner arquivo = null;
    	int numProdutos;
    	String linha;
    	Produto produto;
    	Lista<Produto> produtosCadastrados;
    	
    	try {
    		arquivo = new Scanner(new File(nomeArquivoDados), Charset.forName("UTF-8"));
    		
    		numProdutos = Integer.parseInt(arquivo.nextLine());
    		produtosCadastrados = new Lista<>();
    		
    		for (int i = 0; i < numProdutos; i++) {
    			linha = arquivo.nextLine();
    			produto = Produto.criarDoTexto(linha);
    			produtosCadastrados.inserir(produto);
    		}
    		quantosProdutos = numProdutos;
    		
    	} catch (IOException excecaoArquivo) {
    		produtosCadastrados = null;
    	} finally {
    		arquivo.close();
    	}
    	
    	return produtosCadastrados;
    }
    
    /** Localiza um produto no vetor de produtos cadastrados, a partir do código de produto informado pelo usuário, e o retorna. 
     *  Em caso de não encontrar o produto, retorna null */
    static Produto localizarProduto() {
        Produto produto = null;
    	
    	cabecalho();
    	System.out.println("Localizando um produto...");
        int idProduto = lerOpcao("Digite o identificador do produto desejado:", Integer.class);
        produto = produtosCadastrados.localizar((prod -> prod.hashCode() == idProduto));
        
        return produto;   
    }
    
    /** Localiza um produto na lista de produtos cadastrados, a partir do nome de produto informado pelo usuário, e o retorna. 
     *  A busca não é sensível ao caso. Em caso de não encontrar o produto, retorna null */
    static Produto localizarProdutoDescricao() {
        
    	Produto produtoProcurado, produto = null;
    	String descricao;
    	
    	System.out.println("Localizando um produto...");
    	System.out.print("Digite o nome ou a descrição do produto desejado: ");
        descricao = teclado.nextLine();
        produtoProcurado = new ProdutoNaoPerecivel(descricao, 0.01);
        produto = produtosCadastrados.localizar((prod -> prod.compareTo(produtoProcurado) == 0));
        
        return produto;
    }
    
    private static void mostrarProduto(Produto produto) {
    	
        cabecalho();
        String mensagem = "Dados inválidos para o produto!";
        
        if (produto != null){
            mensagem = String.format("Dados do produto:\n%s", produto);
        }
        
        System.out.println(mensagem);
    }
    
    /** Lista todos os produtos cadastrados, numerados, um por linha */
    static void listarTodosOsProdutos() {
    	
        cabecalho();
        System.out.println("\nPRODUTOS CADASTRADOS:");
        System.out.println(produtosCadastrados);
        
    }
    
    /** 
     * Inicia um novo pedido.
     * Permite ao usuário escolher e incluir produtos no pedido.
     * @return O novo pedido
     */
    public static Pedido iniciarPedido() {
    	
    	Pedido pedido = new Pedido();
    	Produto produto;
    	int numProdutos;
    	
    	listarTodosOsProdutos();
    	System.out.println("Incluindo produtos no pedido...");
    	numProdutos = lerOpcao("Quantos produtos serão incluídos no pedido?", Integer.class);
        for (int i = 0; i < numProdutos; i++) {
        	produto = localizarProdutoDescricao();
        	if (produto == null) {
        		System.out.println("Produto não encontrado");
        		i--;
        	} else {                
        		pedido.incluirProduto(produto);
                System.out.println("Incluído com sucesso.");
        	}
        }
    	
    	return pedido;
    }
    
    /**
     * Finaliza um pedido, momento no qual ele deve ser armazenado em uma lista de pedidos.
     * @param pedido O pedido que deve ser finalizado.
     */
    public static void finalizarPedido(Pedido pedido) {
    	String mensagem = "Pedido não foi aberto";
    	if (pedido != null) {
    		listaPedidos.inserir(pedido);
            mensagem = "Pedido finalizado e incluído na lista";
    	}
        System.out.println(mensagem);
    }
       
    
    /**
     * A partir da escolha de um pedido, exibe quantas vezes um determinado 
     * produto foi repetida naquele pedido. 
     * O pedido é escolhido pelo usuário a partir da posição da lista. O nome do 
     * produto a ser contado também é definido pelo usuário..
    */
    public static void repeticoesDeProdutoNoPedido() {
        cabecalho();
        String resposta = "Pedido não encontrado";
        int tamanhoLista = listaPedidos.tamanho();
        System.out.printf("A lista contém %d pedidos.\n", tamanhoLista);
        String mensagem = String.format("Digite a posição do pedido na lista (de 1 até %d):", tamanhoLista);
    	int N = lerOpcao(mensagem, Integer.class);
        try {
            Pedido qualPedido = listaPedidos.elementoNaPosicao(N-1);
            System.out.print("Informe o nome ou a descrição do produto desejado: ");
            String descricao = teclado.nextLine();
            Produto produtoAContar = new ProdutoNaoPerecivel(descricao, 0.1);
            int quantidade = qualPedido.repeticoes(produtoAContar);
            resposta = String.format("Repetições de %s no pedido %d: %d vezes\n", descricao, N, quantidade);
        } catch (IllegalStateException | IndexOutOfBoundsException e) {
                resposta = e.getMessage();
        }
        System.out.println(resposta);
    }
    
	public static void main(String[] args) {
		teclado = new Scanner(System.in, Charset.forName("UTF-8"));
        nomeArquivoDados = "produtos.txt";
        produtosCadastrados = lerProdutos(nomeArquivoDados);
        Pedido pedido = null;
        
        int opcao = -1;
      
        do{
            opcao = menu();
            switch (opcao) {
                case 1 -> listarTodosOsProdutos();
                case 2 -> mostrarProduto(localizarProduto());
                case 3 -> mostrarProduto(localizarProdutoDescricao());
                case 4 -> pedido = iniciarPedido();
                case 5 -> finalizarPedido(pedido);
                case 6 -> repeticoesDeProdutoNoPedido();
            }
            pausa();
        }while(opcao != 0);       

        teclado.close();    
    }
}
