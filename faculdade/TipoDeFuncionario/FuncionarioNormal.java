public class FuncionarioNormal extends Funcionario {
    public FuncionarioNormal(String nome, int idade, String cargo) {
        super(nome, idade, cargo);
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo: Funcionario Normal";
    }
}
