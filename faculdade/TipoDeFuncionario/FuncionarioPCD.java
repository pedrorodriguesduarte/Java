public class FuncionarioPCD extends Funcionario {
    private String tipoDeficiencia;

    public FuncionarioPCD(String nome, int idade, String cargo, String tipoDeficiencia) {
        super(nome, idade, cargo);
        this.tipoDeficiencia = tipoDeficiencia;
    }

    public String getTipoDeficiencia() {
        return tipoDeficiencia;
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo de Deficiência: " + tipoDeficiencia;
    }
}
