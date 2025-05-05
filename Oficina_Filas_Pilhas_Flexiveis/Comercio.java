import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Comercio {
    static final int MAX_NOVOS_PRODUTOS = 10; // Capacidade adicional para novos produtos

    static String nomeArquivoDados;
    static Scanner teclado;

    static Produto[] produtosCadastrados;
    static int quantosProdutos;

    static final int MAX_PEDIDOS = 10;
    static Fila<Pedido> pedidosCadastrados;
    static int quantPedidos;

    static Pilha<Produto> produtosPedidosRecentes; // Pilha de produtos dos pedidos recentes

    // Aguarda o usuário pressionar Enter
    static void pausa() {
        System.out.println("Digite enter para continuar...");
        teclado.nextLine();
    }

    // Exibe o cabeçalho padrão do sistema
    static void cabecalho() {
        System.out.println("AEDII COMÉRCIO DE COISINHAS");
        System.out.println("===========================");
    }

    // Exibe o menu principal e retorna a opção escolhida
    static int menu() {
        cabecalho();
        System.out.println("1 - Listar todos os produtos");
        System.out.println("2 - Procurar e imprimir os dados de um produto");
        System.out.println("3 - Cadastrar novo produto");
        System.out.println("4 - Iniciar novo pedido");
        System.out.println("5 - Fechar pedido");
        System.out.println("6 - Listar produtos dos pedidos recentes");
        System.out.println("0 - Sair");
        System.out.print("Digite sua opção: ");
        return Integer.parseInt(teclado.nextLine());
    }

    // Lê os produtos de um arquivo e os armazena no vetor
    static Produto[] lerProdutos(String nomeArquivoDados) {
        try {
            Scanner arquivo = new Scanner(new File(nomeArquivoDados));
            int quantidade = Integer.parseInt(arquivo.nextLine());
            produtosCadastrados = new Produto[quantidade + MAX_NOVOS_PRODUTOS];

            for (int i = 0; i < quantidade; i++) {
                String linha = arquivo.nextLine();
                produtosCadastrados[i] = Produto.criarDoTexto(linha);
            }

            quantosProdutos = quantidade;
            arquivo.close();
            return produtosCadastrados;
        } catch (IOException exception) {
            return null;
        }
    }

    // Localiza e imprime os dados de um produto pelo nome
    static void localizarProdutos() {
        cabecalho();
        System.out.print("Digite o nome do produto a localizar: ");
        String desc = teclado.nextLine();
        Produto busca = new ProdutoNaoPerecivel(desc, 1);
        Produto resultado = null;

        for (int i = 0; i < quantosProdutos; i++) {
            if (produtosCadastrados[i].equals(busca))
                resultado = produtosCadastrados[i];
        }

        if (resultado != null)
            System.out.println(resultado);
        else
            System.out.println("Produto não encontrado.");
    }

    // Lista todos os produtos cadastrados
    public static void listarTodosOsProdutos() {
        cabecalho();
        System.out.println("\nPRODUTOS CADASTRADOS:");
        for (int i = 0; i < produtosCadastrados.length; i++) {
            if (produtosCadastrados[i] != null)
                System.out.println(String.format("%02d - %s", (i + 1), produtosCadastrados[i].toString()));
        }
    }

    // Cadastra um novo produto (perecível ou não)
    public static void cadastrarProduto() {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int tipo;
        double precoCusto;
        double margemLucro;
        String desc;
        LocalDate dataValidade;

        cabecalho();
        System.out.println("Cadastro de novo produto:");
        System.out.println("1 - Não perecível (padrão)");
        System.out.println("2 - Perecível");
        System.out.print("Digite o tipo desejado: ");
        tipo = Integer.parseInt(teclado.nextLine());
        if (tipo != 2)
            tipo = 1;

        System.out.print("\nDescrição do produto: ");
        desc = teclado.nextLine();
        System.out.print("Preço de custo: R$ ");
        precoCusto = Double.parseDouble(teclado.nextLine());
        System.out.print("Margem de lucro: ");
        margemLucro = Double.parseDouble(teclado.nextLine());

        if (tipo == 2) {
            System.out.print("Data de validade (dd/mm/aaaa): ");
            dataValidade = LocalDate.parse(teclado.nextLine(), formatoData);
            produtosCadastrados[quantosProdutos] = new ProdutoPerecivel(desc, precoCusto, margemLucro, dataValidade);
        } else {
            produtosCadastrados[quantosProdutos] = new ProdutoNaoPerecivel(desc, precoCusto, margemLucro);
        }

        quantosProdutos++;
        System.out.println(desc + " cadastrado com sucesso. Total de " + quantosProdutos + " produtos cadastrados");
    }

    // Salva os produtos cadastrados em arquivo
    public static void salvarProdutos(String nomeArquivo) {
        try {
            FileWriter arquivoSaida = new FileWriter(nomeArquivo, Charset.forName("UTF-8"));
            arquivoSaida.append(quantosProdutos + "\n");
            for (Produto produto : produtosCadastrados) {
                if (produto != null)
                    arquivoSaida.append(produto.gerarDadosTexto() + "\n");
            }
            arquivoSaida.close();
            System.out.println("Arquivo " + nomeArquivo + " salvo.");
        } catch (IOException e) {
            System.out.println("Problemas no arquivo " + nomeArquivo + ". Tente novamente");
        }
    }

    // Inicia um novo pedido, permitindo a escolha de produtos
    public static Pedido iniciarPedido() {
        Pedido novo = new Pedido();
        int indexProd;
        int qtdeProdutosNoPedido;

        while (true) {
            System.out.println("Informe o código do produto desejado ou 0 para sair:");
            listarTodosOsProdutos();
            System.out.print("Código: ");
            indexProd = Integer.parseInt(teclado.nextLine());

            if (indexProd == 0) break;

            if (indexProd > 0 && indexProd <= produtosCadastrados.length && produtosCadastrados[indexProd - 1] != null) {
                qtdeProdutosNoPedido = novo.incluirProduto(produtosCadastrados[indexProd - 1]);
                System.out.println("Produto " + indexProd + " incluído no pedido.");
                if (qtdeProdutosNoPedido == MAX_NOVOS_PRODUTOS) {
                    System.out.println("Atingida a quantidade máxima de produtos por pedido.");
                    break;
                }
            } else {
                System.out.println("Código de produto inválido.");
            }
        }

        return novo;
    }

    // Finaliza um pedido, armazena na fila e empilha os produtos na pilha
    public static void finalizarPedido(Pedido pedido) {
        if (pedido != null) {
            pedidosCadastrados.enfileirar(pedido);
            quantPedidos++;

            for (Produto p : pedido.getProdutos()) {
                if (p != null)
                    produtosPedidosRecentes.empilhar(p);
            }

            System.out.println("Pedido finalizado com sucesso.");
        } else {
            System.out.println("Pedido vazio.");
        }
    }

    // Salva os pedidos no arquivo
    public static void salvarPedidos(String nomeArquivo) {
        try {
            FileWriter arquivoSaida = new FileWriter(nomeArquivo, Charset.forName("UTF-8"));
            arquivoSaida.append(quantPedidos + "\n");

            while (!pedidosCadastrados.vazia())
                arquivoSaida.append(pedidosCadastrados.desenfileirar().resumo() + "\n");

            arquivoSaida.close();
            System.out.println("Arquivo " + nomeArquivo + " salvo.");
        } catch (IOException e) {
            System.out.println("Problemas no arquivo " + nomeArquivo + ". Tente novamente");
        }
    }

    // Lista os produtos dos pedidos recentes (subPilha com no máximo 10 elementos)
    public static void listarProdutosRecentes() {
        cabecalho();
        Pilha<Produto> ultimosProdutos = produtosPedidosRecentes.subPilha(10);
        int i = 1;

        System.out.println("Produtos mais recentes (últimos pedidos):");

        while (!ultimosProdutos.vazia()) {
            System.out.println((i++) + " - " + ultimosProdutos.desempilhar());
        }
    }

    // Método principal
    public static void main(String[] args) {
        teclado = new Scanner(System.in, Charset.forName("UTF-8"));
        nomeArquivoDados = "produtos.txt";
        String nomeArquivoPedidos = "dadosPedidos.csv";

        produtosCadastrados = lerProdutos(nomeArquivoDados);
        pedidosCadastrados = new Fila<>();
        produtosPedidosRecentes = new Pilha<>();

        Pedido pedido = null;
        int opcao;

        do {
            opcao = menu();
            switch (opcao) {
                case 1 -> listarTodosOsProdutos();
                case 2 -> localizarProdutos();
                case 3 -> cadastrarProduto();
                case 4 -> pedido = iniciarPedido();
                case 5 -> {
                    finalizarPedido(pedido);
                    pedido = null;
                }
                case 6 -> listarProdutosRecentes();
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida.");
            }
            if (opcao != 0) pausa();
        } while (opcao != 0);

        salvarProdutos(nomeArquivoDados);
        salvarPedidos(nomeArquivoPedidos);
        teclado.close();
    }
}
