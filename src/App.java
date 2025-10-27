import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Scanner;

public class App {

    /** Nome do arquivo de dados. O arquivo deve estar localizado na raiz do projeto */
    static String nomeArquivoDados;

    /** Scanner para leitura de dados do teclado */
    static Scanner teclado;

    /** Vetor de produtos cadastrados */
    static Produto[] produtosCadastrados;

    /** Quantidade de produtos cadastrados atualmente no vetor */
    static int quantosProdutos = 0;

    /** Fila de pedidos — por ordem de chegada */
    static Fila<Pedido> filaPedidos = new Fila<>();

    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("Digite enter para continuar...");
        teclado.nextLine();
    }

    static void cabecalho() {
        System.out.println("AEDs II COMÉRCIO DE COISINHAS");
        System.out.println("=============================");
    }

    static <T extends Number> T lerOpcao(String mensagem, Class<T> classe) {
        T valor;
        System.out.println(mensagem);
        try {
            valor = classe.getConstructor(String.class).newInstance(teclado.nextLine());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            return null;
        }
        return valor;
    }

    static int menu() {
        cabecalho();
        System.out.println("1 - Listar todos os produtos");
        System.out.println("2 - Procurar por um produto, por código");
        System.out.println("3 - Procurar por um produto, por nome");
        System.out.println("4 - Iniciar novo pedido");
        System.out.println("5 - Fechar pedido");
        System.out.println("6 - Listar produtos dos pedidos (por ordem de chegada)");
        System.out.println("7 - Exibir valor médio dos N primeiros pedidos");
        System.out.println("8 - Exibir primeiros pedidos com valor total acima de um valor");
        System.out.println("9 - Exibir primeiros pedidos que contenham um determinado produto");
        System.out.println("0 - Sair");
        System.out.print("Digite sua opção: ");
        return Integer.parseInt(teclado.nextLine());
    }

    static Produto[] lerProdutos(String nomeArquivoDados) {
        Scanner arquivo = null;
        int numProdutos;
        String linha;
        Produto produto;
        Produto[] produtosCadastrados;

        try {
            arquivo = new Scanner(new File(nomeArquivoDados), Charset.forName("UTF-8"));
            numProdutos = Integer.parseInt(arquivo.nextLine());
            produtosCadastrados = new Produto[numProdutos];

            for (int i = 0; i < numProdutos; i++) {
                linha = arquivo.nextLine();
                produto = Produto.criarDoTexto(linha);
                produtosCadastrados[i] = produto;
            }
            quantosProdutos = numProdutos;

        } catch (IOException excecaoArquivo) {
            produtosCadastrados = null;
        } finally {
            if (arquivo != null) arquivo.close();
        }

        return produtosCadastrados;
    }

    static Produto localizarProduto() {
        Produto produto = null;
        Boolean localizado = false;

        cabecalho();
        System.out.println("Localizando um produto...");
        int idProduto = lerOpcao("Digite o código identificador do produto desejado: ", Integer.class);
        for (int i = 0; (i < quantosProdutos && !localizado); i++) {
            if (produtosCadastrados[i].hashCode() == idProduto) {
                produto = produtosCadastrados[i];
                localizado = true;
            }
        }
        return produto;
    }

    static Produto localizarProdutoDescricao() {
        Produto produto = null;
        Boolean localizado = false;
        String descricao;

        cabecalho();
        System.out.println("Localizando um produto...");
        System.out.println("Digite o nome ou a descrição do produto desejado:");
        descricao = teclado.nextLine();
        for (int i = 0; (i < quantosProdutos && !localizado); i++) {
            if (produtosCadastrados[i].descricao.equalsIgnoreCase(descricao)) {
                produto = produtosCadastrados[i];
                localizado = true;
            }
        }
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

    static void listarTodosOsProdutos() {
        cabecalho();
        System.out.println("\nPRODUTOS CADASTRADOS:");
        for (int i = 0; i < quantosProdutos; i++) {
            System.out.println(String.format("%02d - %s", (i + 1), produtosCadastrados[i].toString()));
        }
    }

    public static Pedido iniciarPedido() {
        int formaPagamento = lerOpcao("Digite a forma de pagamento do pedido, sendo 1 para pagamento à vista e 2 para pagamento a prazo", Integer.class);
        Pedido pedido = new Pedido(LocalDate.now(), formaPagamento);
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
            }
        }
        return pedido;
    }

    public static void finalizarPedido(Pedido pedido) {
        if (pedido != null) {
            filaPedidos.inserir(pedido);
            System.out.println("Pedido finalizado e armazenado por ordem de chegada!");
        } else {
            System.out.println("Nenhum pedido para finalizar.");
        }
    }

    public static void listarProdutosPedidosRecentes() {
        if (filaPedidos.isEmpty()) {
            System.out.println("Nenhum pedido na fila.");
            return;
        }

        System.out.println("Listando pedidos por ordem de chegada:\n");
        Fila<Pedido> auxiliar = new Fila<>();

        while (!filaPedidos.isEmpty()) {
            Pedido p = filaPedidos.remover();
            auxiliar.inserir(p);
            System.out.println(p);
            System.out.println("------------------------------\n");
        }

        while (!auxiliar.isEmpty()) {
            filaPedidos.inserir(auxiliar.remover());
        }
    }

    public static void main(String[] args) {
        teclado = new Scanner(System.in, Charset.forName("UTF-8"));
        nomeArquivoDados = "produtos.txt";
        produtosCadastrados = lerProdutos(nomeArquivoDados);

        Pedido pedido = null;
        int opcao = -1;

        do {
            opcao = menu();
            switch (opcao) {
                case 1 -> listarTodosOsProdutos();
                case 2 -> mostrarProduto(localizarProduto());
                case 3 -> mostrarProduto(localizarProdutoDescricao());
                case 4 -> pedido = iniciarPedido();
                case 5 -> finalizarPedido(pedido);
                case 6 -> listarProdutosPedidosRecentes();

                case 7 -> {
                    int n = lerOpcao("Digite quantos primeiros pedidos deseja considerar:", Integer.class);
                    if (n > 0) {
                        double media = filaPedidos.calcularValorMedio(p -> p.valorFinal(), n);
                        System.out.printf("Valor médio dos %d primeiros pedidos: R$ %.2f%n", n, media);
                    } else {
                        System.out.println("Quantidade inválida.");
                    }
                }

                case 8 -> {
                    int n = lerOpcao("Quantos primeiros pedidos considerar?", Integer.class);
                    double limite = lerOpcao("Valor mínimo do pedido:", Double.class);
                    Fila<Pedido> filtrados = filaPedidos.filtrar(p -> p.valorFinal() > limite, n);

                    System.out.println("Pedidos com valor acima de R$ " + limite + ":");
                    while (!filtrados.vazia()) {
                        System.out.println(filtrados.remover());
                        System.out.println("------------------------------");
                    }
                }

                case 9 -> {
                    System.out.println("Digite o nome do produto:");
                    String nomeProd = teclado.nextLine();
                    int n = lerOpcao("Quantos primeiros pedidos considerar?", Integer.class);

                    Fila<Pedido> filtrados = filaPedidos.filtrar(p -> {
                        for (Produto prod : p.getProdutos()) {
                            if (prod != null && prod.descricao.equalsIgnoreCase(nomeProd)) {
                                return true;
                            }
                        }
                        return false;
                    }, n);

                    System.out.println("Pedidos contendo o produto '" + nomeProd + "':");
                    while (!filtrados.vazia()) {
                        System.out.println(filtrados.remover());
                        System.out.println("------------------------------");
                    }
                }
            }
            pausa();
        } while (opcao != 0);

        teclado.close();
    }
}
