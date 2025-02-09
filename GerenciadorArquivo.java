import java.io.*;
import java.util.*;

public class GerenciadorArquivo {

    public static List<Aluno> lerAlunos(String caminhoAlunos) throws IOException {
        File arquivo = new File(caminhoAlunos);
        List<Aluno> alunos = new ArrayList<>();

        if (!arquivo.exists()) {
            System.out.println("Arquivo de alunos não encontrado: " + caminhoAlunos);
            return alunos;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split("\t");
                if (partes.length == 2) {
                    alunos.add(new Aluno(partes[0], partes[1])); // [0] = nome, [1] = respostas
                }
            }
        }
        return alunos;
    }

    public static String lerGabarito(String caminhoGabarito) throws IOException {
        File arquivo = new File(caminhoGabarito);

        if (!arquivo.exists()) {
            throw new FileNotFoundException("O arquivo de gabarito não foi encontrado: " + caminhoGabarito);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            return reader.readLine().trim();
        }
    }

    public static void salvarArquivo(String caminho, List<Aluno> alunos, Double media, boolean incluirPontuacao) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            for (Aluno aluno : alunos) {
                if (incluirPontuacao) {
                    writer.write(aluno.getNome() + "\t" + aluno.getRespostas() + "\t" + aluno.getPontuacao());
                } else {
                    writer.write(aluno.getNome() + "\t" + aluno.getRespostas());
                }
                writer.newLine();
            }
            if (media != null) {
                writer.write("Média da turma: " + media);
            }
        }
    }

    public static void salvarGabarito(String caminho, String gabarito) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            writer.write(gabarito);
        }
    }
}