package programa;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Aluno {
    private static final String filePath = "./BancoDeDados/alunos.csv";
    public static boolean aprovado;
    //metodo para listar todos os alunos
    public static void listarAlunos() {
        Map<String, String> mapDeAlunos;
        mapDeAlunos = retornaMapDeAlunos();
        System.out.println("\n  ***    ALUNOS   ***  ");
        for (String key : mapDeAlunos.keySet()) {
            System.out.println("ID: " + key + " | Nome: " + mapDeAlunos.get(key));
        }
    }

    //metodo que retorna um Map de todos os alunos
    protected static Map<String, String> retornaMapDeAlunos() {
        Map<String, String> mapDeAlunos = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linhas = br.readLine();
            while (linhas != null) {
                String[] vetorDeAlunos = linhas.split(";");
                mapDeAlunos.put(vetorDeAlunos[0], vetorDeAlunos[1]);
                linhas = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return mapDeAlunos;
    }


    //retorna o nome do aluno baseado no ID
    private static String buscaAlunoPorId(String id) {
        return retornaMapDeAlunos().get(id);
    }

    //retorna um boolean caso o aluno exista ou nao baseado no id
    public static boolean verificaExistenciaAluno(String id) {
        return retornaMapDeAlunos().containsKey(id);
    }

    //metodo que lista o historico do aluno selecionado
    public static void listarHistorico() {
        Scanner in = new Scanner(System.in);
        System.out.println("Selecione o aluno: ");
        listarAlunos();
        System.out.print("Entre com o ID selecionado: ");
        String idSelecionado = in.nextLine();
        idSelecionado = idSelecionado.replaceAll(" ", "");
        idSelecionado = idSelecionado.toLowerCase();

        //verifica existencia do aluno
        if(verificaExistenciaAluno(idSelecionado)){
            Curso.lerCadaCurso(idSelecionado);
        }else{
            System.out.println("Aluno não encontrado, tente novamente...");
            listarHistorico();
        }
    }
    //é chamado dentro do metodo Curso.lerCadaCurso();
    //é responsavel por buscar o aluno requerido por ID em cada curso, e retornar +
    //com todas as informações dele;
    public static void procuraAlunosPorCurso(String[] alunosEncontrados) {
        String nomeDoCurso = Curso.devolveCurso(alunosEncontrados[0]);
        String nivelDoCurso = Curso.devolveNivelDoCurso(nomeDoCurso);
        String idDoAluno = alunosEncontrados[1];
        String nomeDoAluno = buscaAlunoPorId(idDoAluno);
        double notaNp1 = Double.parseDouble(alunosEncontrados[2]);
        double notaNp2 = Double.parseDouble(alunosEncontrados[3]);
        double notaRep = Double.parseDouble(alunosEncontrados[4]);
        double notaExam = Double.parseDouble(alunosEncontrados[5]);
        String notaMedia;
        String situacao;
        notaMedia = Media.calcMedia(notaNp1, notaNp2, notaRep, notaExam, nivelDoCurso);
        if (aprovado) {
            situacao = "APROVADO";
        } else {
            situacao = "REPROVADO";
        }
        System.out.println("Aluno: " + nomeDoAluno + " | Curso: " + nomeDoCurso + " | Nota NP1: " + notaNp1 + " | Nota NP2: " + notaNp2 + " | Nota Reposição: " + notaRep + " | Nota Exame: " + notaExam + " | Média: " + notaMedia + " | Situação: " + situacao);
    }
    //inclusao de alunos, verifica existencia do ID, o tamanho, e a formatacao padrao
    public static void incluirAluno() {
        Scanner in = new Scanner(System.in);
        System.out.println("Menu de inclusao de novo aluno");
        System.out.println("\nAlunos ja existentes: ");
        listarAlunos();
        System.out.println();
        System.out.print("Id unico do novo aluno: ");
        String idAluno = in.nextLine();
        idAluno = idAluno.replaceAll(" ", "");
        idAluno = idAluno.toLowerCase();
        if(!verificaIdAluno(idAluno)){
            System.out.println("\nId inválido, tente novamente...");
            incluirAluno();
        }else {
            System.out.print("\nNome do novo aluno: ");
            String nomeAluno = in.nextLine();
            nomeAluno = Utilitario.retornaCapitalizado(nomeAluno);
            if(!verificaNomeAluno(nomeAluno)){
                System.out.println("\nNome inválido, tente novamente...");
                incluirAluno();
            }else {
                System.out.println("\nAluno a ser incluso: ");
                System.out.println("Id: " + idAluno + " " + " Nome: " + nomeAluno);
                gravarAlunoNoCSV(idAluno, nomeAluno);
            }
        }
    }

    private static void gravarAlunoNoCSV(String idAluno, String nomeAluno){
        String separacao = ";";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,true))){
            bw.write(idAluno);
            bw.write(separacao);
            bw.write(nomeAluno);
            bw.newLine();
        }catch(IOException e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    //
    private static boolean verificaNomeAluno(String nomeAluno) {
        return nomeAluno.length() <= 20 && !nomeAluno.isBlank();
    }

    private static boolean verificaIdAluno(String idAluno) {
        return (idAluno.length() == 3) && !verificaExistenciaAluno(idAluno);
    }
}
