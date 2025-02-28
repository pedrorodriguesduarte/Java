public class AlunoBolsista extends Aluno {
    private double valorBolsa;

    public AlunoBolsista(String nome, int idade, double valorBolsa) {
        super(nome, idade);
        this.valorBolsa = valorBolsa;
    }

    public double getValorBolsa() {
        return valorBolsa;
    }

    @Override
    public String toString() {
        return super.toString() + ", Valor da Bolsa: R$ " + valorBolsa;
    }
}
