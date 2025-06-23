public class Cliente {
    private String documento;
    private String nome;
    private Lista<Pedido> pedidos;

    public Cliente(String documento, String nome) {
        this.documento = documento;
        this.nome = nome;
        this.pedidos = new Lista<>();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public void adicionarPedido(Pedido p) {
        if (p != null) {
            pedidos.inserir(p);
        }
    }

    public double totalGasto() {
        double total = 0.0;
        for (int i = 0; i < pedidos.tamanho(); i++) {
            Pedido pedido = pedidos.elementoNaPosicao(i);
            total += pedido.valorFinal(); // Correção aqui!
        }
        return total;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cliente)) return false;
        Cliente outro = (Cliente) obj;
        return this.documento.equals(outro.documento);
    }

    @Override
    public int hashCode() {
        return documento.hashCode();
    }

    @Override
    public String toString() {
        return nome + " (" + documento + ")";
    }
}
