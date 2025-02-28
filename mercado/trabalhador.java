public class trabalhador extends cliente {
    private String profissao;

    public trabalhador(String nome, int idade, String profissao) {
        super(nome, idade);
        this.profissao = profissao;
    }

    public String getProfissao() {
        return profissao;
    }

    @Override
    public String toString() {
        return super.toString() + ", Profissão: " + profissao;
    }
}
