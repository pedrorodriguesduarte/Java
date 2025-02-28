public class Main {
    public static void main(String[] args) {
        // Criando instâncias de Alunos
        AlunoBolsista alunoBolsista = new AlunoBolsista("Carlos Silva", 21, -1000.00);
        AlunoComum alunoComum = new AlunoComum("Ana Pereira", 22);

        // Criando instâncias de Funcionários
        FuncionarioNormal funcionarioNormal = new FuncionarioNormal("Roberto Costa", 30, "Assistente Administrativo");
        FuncionarioPCD funcionarioPCD = new FuncionarioPCD("Mariana Souza", 28, "Auxiliar de Escritório", "Mobilidade Reduzida");

        // Criando instâncias de Professores
        ProfessorEfetivado professorEfetivado = new ProfessorEfetivado("José Oliveira", 40, "Matemática");
        ProfessorTemporario professorTemporario = new ProfessorTemporario("Luciana Lima", 35, "Física", 12);

        // Exibindo os resultados
        System.out.println(alunoBolsista);
        System.out.println(alunoComum);
        System.out.println(funcionarioNormal);
        System.out.println(funcionarioPCD);
        System.out.println(professorEfetivado);
        System.out.println(professorTemporario);
    }
}
