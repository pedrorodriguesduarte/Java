public class ProfessorEfetivado extends Professor {
    public ProfessorEfetivado(String nome, int idade, String materia) {
        super(nome, idade, materia);
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo: Professor Efetivado";
    }
}
