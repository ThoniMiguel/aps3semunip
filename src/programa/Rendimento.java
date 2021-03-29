package programa;

import java.io.*;
import java.util.*;

public class Rendimento {
    private static String path = "./BancoDeDados/";

    public static void selecionar() {
        String[] cursoSelecionado = selecionarCurso();
        if (cursoSelecionado == null) {
            System.out.println("Curso nao encontrado, tente novamente");
            return;
        }

        String[] alunoSelecionado = selecionarAluno();
        if (alunoSelecionado == null) {
            System.out.println("Aluno nao encontrado, tente novamente...");
            return;
        }
        String curso = String.join("_", cursoSelecionado);
        curso = curso + ".csv";
        if (checarExisteAluno(curso, alunoSelecionado[0])) {
            System.out.println("Aluno ja matriculado nesse curso!");
            System.exit(1);
        } else {
            float[] notas = informarNotas();
            String[] notasString = new String[notas.length];
            for (int i = 0; i < notas.length; i++) {
                notasString[i] = String.format("%.2f", notas[i]);
            }
            escreverNoCSV(curso, alunoSelecionado, notasString);
        }
    }

    private static String[] selecionarCurso() {
        Scanner in = new Scanner(System.in);
        List<Curso> cursoList = Curso.retornaListaCursos();
        Map<Integer, Curso> mapDeCursos = new HashMap<>();
        int i = 1;
        for (Curso c : cursoList) {
            System.out.println("ID Curso: " + i + " | Nome: " + c.getNome() + " | Nivel: " + c.getNivel() + " | Ano: " + c.getAno());
            mapDeCursos.put(i, c);
            i++;
        }
        int escolha = 0;
        String[] curso = new String[3];
        //try-catch para nao ter erros com a entrada de Strings no lugar de Integer
        //e dentro tem um if-else para nao permitir a entrada de dados maiores ou menores do que as existentes
        try {
            System.out.print("Selecionar o curso por meio do ID informado: ");
            escolha = Integer.parseInt(in.nextLine());
            if (escolha >= i || escolha <= 0) {
                System.out.println("Curso inexistente, tente novamente...");
            } else {
                Curso cursoTemp = mapDeCursos.get(escolha);
                System.out.println("\nCurso selecionado:");
                System.out.println("Nome: " + cursoTemp.getNome() + " | Nivel: " + cursoTemp.getNivel() + " | Ano: " + cursoTemp.getAno());
                System.out.println();
                curso = new String[]{cursoTemp.getNome(), cursoTemp.getNivel(), String.valueOf(cursoTemp.getAno())};
            }
        } catch (NumberFormatException e) {
            System.out.println("Selecao invalida, tente novamente...");
        }
        if (escolha > 0 && escolha <= i) {
            return curso;
        } else {
            return null;
        }
    }

    private static String[] selecionarAluno() {
        Scanner in = new Scanner(System.in);
        System.out.println("Selecione o aluno: ");
        Map<String, String> alunos = Aluno.retornaMapDeAlunos();
        String[] alunoSelecionado = new String[2];
        Aluno.listarAlunos();
        System.out.print("Entre com o ID selecionado: ");
        String idSelecionado = in.nextLine();
        idSelecionado = idSelecionado.replaceAll(" ", "");
        idSelecionado = idSelecionado.toLowerCase();

        //verifica existencia do aluno
        if (Aluno.verificaExistenciaAluno(idSelecionado)) {
            alunoSelecionado[0] = idSelecionado;
            alunoSelecionado[1] = alunos.get(idSelecionado);
            return alunoSelecionado;
        } else {
            return null;
        }
    }

    private static float[] informarNotas() {
        Nota nota = Nota.novasNotas();
        return new float[]{nota.getNp1(), nota.getNp2(), nota.getSubstitutiva(), nota.getExame()};
    }

    private static void escreverNoCSV(String curso, String[] aluno, String[] notas) {
        String csvPath = path + curso;
        String separacao = ";";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(csvPath, true))){
            bw.write(aluno[0]);
            bw.write(separacao);
            bw.write(notas[0]);
            bw.write(separacao);
            bw.write(notas[1]);
            bw.write(separacao);
            bw.write(notas[2]);
            bw.write(separacao);
            bw.write(notas[3]);
            bw.newLine();
        }catch(IOException e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static boolean checarExisteAluno(String curso, String idAluno) {
        String csvPath = path + curso;
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String linhas = br.readLine();
            while (linhas != null) {
                String[] temp = linhas.split(";");
                if (temp[0].equals(idAluno)) {
                    return true;
                }
                linhas = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return false;
    }
}