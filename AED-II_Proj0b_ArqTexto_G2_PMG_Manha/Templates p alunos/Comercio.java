import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Comercio {
    /** Para inclusão de novos produtos no vetor */
    static final int MAX_NOVOS_PRODUTOS = 10;

    /** Nome do arquivo de dados. O arquivo deve estar localizado na raiz do projeto */
    static String nomeArquivoDados;
    
    /** Scanner para leitura do teclado */
    static Scanner teclado;

    /** Vetor de produtos cadastrados. Sempre terá espaço para 10 novos produtos a cada execução */
    static Produto[] produtosCadastrados;

    /** Quantidade produtos cadastrados atualmente no vetor */
    static int quantosProdutos;

    /** Gera um efeito de pausa na CLI. Espera por um enter para continuar */
    static void pausa(){
        System.out.println("Digite enter para continuar...");
        teclado.nextLine();
    }

    /** Cabeçalho principal da CLI do sistema */
    static void cabecalho(){
        System.out.println("AEDII COMÉRCIO DE COISINHAS");
        System.out.println("===========================");
    }

    /** Imprime o menu principal, lê a opção do usuário e a retorna (int).
     * Perceba que poderia haver uma melhor modularização com a criação de uma classe Menu.
     * @return Um inteiro com a opção do usuário.
    */
    static int menu(){
        cabecalho();
        System.out.println("1 - Listar todos os produtos");
        System.out.println("2 - Procurar e listar um produto");
        System.out.println("3 - Cadastrar novo produto");
        System.out.println("0 - Sair");
        System.out.print("Digite sua opção: ");
        return Integer.parseInt(teclado.nextLine());
    }

    /**
     * Lê os dados de um arquivo texto e retorna um vetor de produtos. Arquivo no formato
     * N  (quantiade de produtos) <br/>
     * tipo; descrição;preçoDeCusto;margemDeLucro;[dataDeValidade] <br/>
     * Deve haver uma linha para cada um dos produtos. Retorna um vetor vazio em caso de problemas com o arquivo.
     * @param nomeArquivoDados Nome do arquivo de dados a ser aberto.
     * @return Um vetor com os produtos carregados, ou vazio em caso de problemas de leitura.
     */
    static Produto[] lerProdutos(String nomeArquivoDados) {
        Produto[] vetorProdutos = new Produto[MAX_NOVOS_PRODUTOS];
        quantosProdutos = 0;

        try (Scanner arquivo = new Scanner(new File(nomeArquivoDados), "UTF-8")) {
            int n = Integer.parseInt(arquivo.nextLine().trim());
            vetorProdutos = new Produto[n + MAX_NOVOS_PRODUTOS];

            for (int i = 0; i < n; i++) {
                if (arquivo.hasNextLine()) {
                    String linha = arquivo.nextLine();
                    Produto p = Produto.criarDoTexto(linha);
                    if (p != null) {
                        vetorProdutos[quantosProdutos++] = p;
                    }
                }
            }
        } 
        catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado. Criando novo cadastro vazio.");
            vetorProdutos = new Produto[MAX_NOVOS_PRODUTOS];
        }
        catch (Exception e) {
            System.out.println("Erro ao ler produtos: " + e.getMessage());
        }

        return vetorProdutos;
    }

    /** Lista todos os produtos cadastrados, numerados, um por linha */
    static void listarTodosOsProdutos(){
        cabecalho();
        System.out.println("\nPRODUTOS CADASTRADOS:");
        for (int i = 0; i < quantosProdutos; i++) {
            if(produtosCadastrados[i]!=null)
                System.out.println(String.format("%02d - %s", (i+1), produtosCadastrados[i].toString()));
        }
    }

    /** Localiza um produto no vetor de cadastrados, a partir do nome, e imprime seus dados. 
     *  A busca não é sensível ao caso.  Em caso de não encontrar o produto, imprime mensagem padrão */
    static void localizarProdutos(){
        System.out.print("Digite o nome do produto a procurar: ");
        String nome = teclado.nextLine().trim();

        boolean encontrado = false;
        for(int i=0; i<quantosProdutos; i++){
            if(produtosCadastrados[i]!=null && 
               produtosCadastrados[i].equals(new ProdutoNaoPerecivel(nome, 1.0))) {
                // crio um "mock" só para comparar pela descrição
                System.out.println("Produto encontrado: " + produtosCadastrados[i]);
                encontrado = true;
                break;
            }
        }

        if(!encontrado){
            System.out.println("Produto não encontrado.");
        }
    }

    /**
     * Rotina de cadastro de um novo produto: pergunta ao usuário o tipo do produto, lê os dados correspondentes,
     * cria o objeto adequado de acordo com o tipo, inclui no vetor. Este método pode ser feito com um nível muito 
     * melhor de modularização. As diversas fases da lógica poderiam ser encapsuladas em outros métodos. 
     * Uma sugestão de melhoria mais significativa poderia ser o uso de padrão Factory Method para criação dos objetos.
     */
    static void cadastrarProduto(){
        try {
            System.out.print("Digite o tipo do produto (1 = Não perecível, 2 = Perecível): ");
            int tipo = Integer.parseInt(teclado.nextLine());

            System.out.print("Descrição: ");
            String desc = teclado.nextLine();

            System.out.print("Preço de custo: ");
            double preco = Double.parseDouble(teclado.nextLine());

            System.out.print("Margem de lucro: ");
            double margem = Double.parseDouble(teclado.nextLine());

            Produto novo = null;

            if(tipo == 1){
                novo = new ProdutoNaoPerecivel(desc, preco, margem);
            }
            else if(tipo == 2){
                System.out.print("Data de validade (dd/MM/yyyy): ");
                String dataStr = teclado.nextLine();
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate validade = LocalDate.parse(dataStr, fmt);
                novo = new ProdutoPerecivel(desc, preco, margem, validade);
            }

            if(novo != null && quantosProdutos < produtosCadastrados.length){
                produtosCadastrados[quantosProdutos++] = novo;
                System.out.println("Produto cadastrado com sucesso!");
            }
            else {
                System.out.println("Não foi possível cadastrar o produto (vetor cheio?).");
            }
        }
        catch(Exception e){
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    /**
     * Salva os dados dos produtos cadastrados no arquivo csv informado. Sobrescreve todo o conteúdo do arquivo.
     * @param nomeArquivo Nome do arquivo a ser gravado.
     */
    public static void salvarProdutos(String nomeArquivo){
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write(quantosProdutos + "\n");
            for (int i=0; i<quantosProdutos; i++) {
                if(produtosCadastrados[i]!=null){
                    writer.write(produtosCadastrados[i].gerarDadosTexto() + "\n");
                }
            }
            System.out.println("Produtos salvos em arquivo com sucesso!");
        } 
        catch (IOException e) {
            System.out.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        teclado = new Scanner(System.in, Charset.forName("ISO-8859-2"));
        nomeArquivoDados = "dadosProdutos.csv";
        produtosCadastrados = lerProdutos(nomeArquivoDados);
        int opcao = -1;
        do{
            opcao = menu();
            switch (opcao) {
                case 1 -> listarTodosOsProdutos();
                case 2 -> localizarProdutos();
                case 3 -> cadastrarProduto();
            }
            pausa();
        }while(opcao !=0);       

        salvarProdutos(nomeArquivoDados);
        teclado.close();    
    }
}
