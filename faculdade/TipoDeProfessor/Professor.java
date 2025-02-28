public class Professor {
    private String nome;
    private int idade;
    private String materia;

    public Professor(String nome, int idade, String materia) {
        this.nome = nome;
        this.idade = idade;
        this.materia = materia;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getMateria() {
        return materia;
    }

    @Override
    public String toString() {
        return "Professor: " + nome + ", Idade: " + idade + ", Matéria: " + materia;
    }
}
