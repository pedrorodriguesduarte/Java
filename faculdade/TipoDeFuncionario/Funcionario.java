public class Funcionario {
    private String nome;
    private int idade;
    private String cargo;

    public Funcionario(String nome, int idade, String cargo) {
        this.nome = nome;
        this.idade = idade;
        this.cargo = cargo;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getCargo() {
        return cargo;
    }

    @Override
    public String toString() {
        return "Funcionario: " + nome + ", Cargo: " + cargo + ", Idade: " + idade;
    }
}

