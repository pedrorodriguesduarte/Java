public class ProfessorTemporario extends Professor {
    private int duracaoContrato;

    public ProfessorTemporario(String nome, int idade, String materia, int duracaoContrato) {
        super(nome, idade, materia);
        this.duracaoContrato = duracaoContrato;
    }

    public int getDuracaoContrato() {
        return duracaoContrato;
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo: Professor Temporário, Duração do Contrato: " + duracaoContrato + " meses";
    }
}
