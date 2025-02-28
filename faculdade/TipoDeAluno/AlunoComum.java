public class AlunoComum extends Aluno {
    public AlunoComum(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo: Aluno Comum";
    }
}

