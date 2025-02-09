import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DisciplinaService disciplinaService = new DisciplinaService(scanner);

        while (true) {
            System.out.println("\n=== Sistema de Gestão de Provas ===");
            System.out.println("1. Criar arquivo com gabarito de uma disciplina");
            System.out.println("2. Gerar resultado de uma disciplina");
            System.out.println("3. Entrar com gabarito dos alunos");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> disciplinaService.criarArquivoGabarito();
                case 2 -> disciplinaService.gerarResultadoDisciplina();
                case 3 -> disciplinaService.entrarGabaritoAlunos();
                case 4 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}