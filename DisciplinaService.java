import java.io.IOException;
import java.util.*;

public class DisciplinaService {
    private Scanner scanner;

    public DisciplinaService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void criarArquivoGabarito() {
        while (true) {
            System.out.print("Digite o nome da disciplina (ou 'sair' para voltar ao menu): ");
            String disciplina = scanner.nextLine();
            if (disciplina.equalsIgnoreCase("sair")) {
                break;
            }

            System.out.print("Digite o gabarito da disciplina: ");
            String gabarito = scanner.nextLine();

            try {
                GerenciadorArquivo.salvarGabarito("data/" + disciplina + "_gabarito.txt", gabarito);
                System.out.println("Arquivo de gabarito criado com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
            }
        }
    }

    public void gerarResultadoDisciplina() {
        System.out.print("Digite o caminho do arquivo de respostas dos alunos: ");
        String caminhoAlunos = scanner.nextLine();
        System.out.print("Digite o caminho do arquivo de gabarito: ");
        String caminhoGabarito = scanner.nextLine();

        try {
            List<Aluno> alunos = GerenciadorArquivo.lerAlunos(caminhoAlunos);
            String gabarito = GerenciadorArquivo.lerGabarito(caminhoGabarito);

            for (Aluno aluno : alunos) {
                int pontuacao = calcularPontuacao(aluno.getRespostas(), gabarito);
                aluno.setPontuacao(pontuacao);
            }

            alunos.sort(Comparator.comparing(Aluno::getNome));
            GerenciadorArquivo.salvarArquivo("data/resultado_alfabetico.txt", alunos, null, true);

            alunos.sort((a1, a2) -> Integer.compare(a2.getPontuacao(), a1.getPontuacao()));
            double media = alunos.stream().mapToInt(Aluno::getPontuacao).average().orElse(0.0);
            GerenciadorArquivo.salvarArquivo("data/resultado_notas.txt", alunos, media, true);

            System.out.println("Resultados gerados com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao processar os arquivos: " + e.getMessage());
        }
    }

    private int calcularPontuacao(String respostas, String gabarito) {
        if (respostas.length() != gabarito.length()) {
            System.out.println("Erro: O comprimento das respostas do aluno não corresponde ao comprimento do gabarito.");
            return 0;
        }

        if (respostas.equals("VVVVVVVVVV") || respostas.equals("FFFFFFFFFF")) {
            return 0;
        }

        int pontuacao = 0;
        for (int i = 0; i < gabarito.length(); i++) {
            if (respostas.charAt(i) == gabarito.charAt(i)) {
                pontuacao++;
            }
        }
        return pontuacao;
    }

    public void entrarGabaritoAlunos() {
        while (true) {
            System.out.print("Digite o nome da disciplina (ou 'sair' para voltar ao menu): ");
            String disciplina = scanner.nextLine();
            if (disciplina.equalsIgnoreCase("sair")) {
                break;
            }

            List<Aluno> alunos = new ArrayList<>();

            while (true) {
                System.out.print("Digite o gabarito e o nome do aluno (ou 'sair' para finalizar): ");
                String entrada = scanner.nextLine();
                if (entrada.equalsIgnoreCase("sair")) {
                    break;
                }
                String[] partes = entrada.split(" ");
                if (partes.length < 2) {
                    System.out.println("Entrada inválida. Tente novamente.");
                    continue;
                }
                String respostas = partes[0];
                String nome = partes[1];
                alunos.add(new Aluno(nome, respostas));
            }

            try {
                GerenciadorArquivo.salvarArquivo("data/" + disciplina + ".txt", alunos, null, false);
                System.out.println("Arquivo de gabaritos dos alunos criado com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
            }
        }
    }
}