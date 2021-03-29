package programa;

import java.io.*;
import java.util.*;

public class Curso {
    private static final String filePath = "./BancoDeDados/cursos.csv";
    private String nome, nivel;
    private int ano;

    //construtor
    public Curso(String nome, String nivel, int ano) {
        this.nome = nome;
        this.nivel = nivel;
        this.ano = ano;
    }

    //metodo para listar todos os cursos
    public static void listarCursos() {
        List<Curso> listaCursos;
        listaCursos = retornaListaCursos();
        System.out.println("\n *** CURSOS *** ");
        for (Curso c : listaCursos) {
            System.out.println("Nome: " + c.getNome() + " | Nivel: " + c.getNivel() + " | Ano: " + c.getAno());
        }
    }

    //metodo que retorna uma lista com todos os cursos
    public static List<Curso> retornaListaCursos() {
        List<Curso> listaCursos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linhas = br.readLine();
            while (linhas != null) {
                String[] vetorCursos = linhas.split(";");
                listaCursos.add(new Curso(vetorCursos[0], vetorCursos[1], Integer.parseInt(vetorCursos[2])));
                linhas = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return listaCursos;
    }

    //metodo que retorna o nome de um arquivo CSV no formato NOME_NIVEL_ANO.csv
    private static List<String> retornaNome_Nivel_AnoCSV() {
        List<Curso> listaCursos = retornaListaCursos();
        List<String> nomeDosArquivos = new ArrayList<>();
        for (Curso c : listaCursos) {
            nomeDosArquivos.add(c.getNome() + "_" + c.getNivel() + "_" + c.getAno() + ".csv");
        }
        return nomeDosArquivos;
    }

    //retorna o caminho de todos os cursos cadastrados no arquivo cursos.csv
    private static String[] retornaCaminhoCursos() {
        String path = "./BancoDeDados";
        List<String> nomeDosArquivos = retornaNome_Nivel_AnoCSV();
        String[] caminhoArquivosCursos = new String[nomeDosArquivos.size()];
        for (int i = 0; i < nomeDosArquivos.size(); i++) {
            caminhoArquivosCursos[i] = path + "/" + nomeDosArquivos.get(i);
        }
        return caminhoArquivosCursos;
    }

    //busca o curso por id do aluno e ja retorna para o metodo na classe Aluno
    public static void lerCadaCurso(String id) {
        String[] caminhoArquivoCursos = retornaCaminhoCursos();
        String[] vetorUtilitario;
        String filePath;
        for (int i = 0; i < caminhoArquivoCursos.length; i++) {
            filePath = caminhoArquivoCursos[i];
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String linhas = br.readLine();
                while (linhas != null) {
                    vetorUtilitario = linhas.split(";");
                    if (vetorUtilitario[0].equals(id)) {  //se o id é igual ao ID requerido, todas as informacoes dentro do arquivo CSV serao passadas para o metedo abaixo;
                        String[] vetorTemp = {filePath, vetorUtilitario[0], vetorUtilitario[1], vetorUtilitario[2], vetorUtilitario[3], vetorUtilitario[4]};
                        Aluno.procuraAlunosPorCurso(vetorTemp);
                    }
                    linhas = br.readLine();
                }
            } catch (IOException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    public static String devolveCurso(String caminhoDoCurso) {
        String[] stringTemp;
        stringTemp = caminhoDoCurso.split("/");
        String[] nomeDoCurso = stringTemp[2].split("\\.");
        return nomeDoCurso[0];
    }

    public static String devolveNivelDoCurso(String nomeCurso) {
        String[] stringTemp = nomeCurso.split("_");
        return stringTemp[1];
    }

    //metodo responsavel por escolher o curso mediante a um ID informado pelo usuario
    public static void selecionarCurso() {
        Scanner in = new Scanner(System.in);
        List<Curso> cursoList = retornaListaCursos();
        Map<Integer, Curso> mapDeCursos = new HashMap<>();
        int i = 1;
        for (Curso c : cursoList) {
            System.out.println("ID Curso: " + i + " | Nome: " + c.getNome() + " | Nivel: " + c.getNivel() + " | Ano: " + c.getAno());
            mapDeCursos.put(i, c);
            i++;
        }
        int escolha = 0;

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
                listaCursoSelecionado(cursoTemp.getNome(), cursoTemp.getNivel(), cursoTemp.getAno());

            }
        } catch (NumberFormatException e) {
            System.out.println("Selecao invalida, tente novamente...");
        }

    }

    //metodo que lista o curso selecionado
    private static void listaCursoSelecionado(String nomeCurso, String nivelCurso, int anoCurso) {
        String cursoCSV = "./BancoDeDados/" + nomeCurso + "_" + nivelCurso + "_" + anoCurso + ".csv";
        try (BufferedReader br = new BufferedReader(new FileReader(cursoCSV))) {
            String linhas = br.readLine();
            while (linhas != null) {
                String[] temp = linhas.split(";");
                String[] infoCursos = {cursoCSV, temp[0], temp[1], temp[2], temp[3], temp[4]};
                Aluno.procuraAlunosPorCurso(infoCursos);
                linhas = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    //meteodo responsavel por receber e tratar informações sobre novos cursos, e chamar o metodo responsavel por gravar
    //estas informaçoes no arquivo cursos.csv
    public static void incluirNovoCurso() {
        Scanner in = new Scanner(System.in);
        System.out.print("Nome do curso a ser incluido: ");
        String nomeCurso = in.nextLine();
        if (nomeCurso.isBlank() || nomeCurso.length() > 23) {
            System.out.println("Nome inválido, tente novamente...");
        } else {
            nomeCurso = formatarNomeCurso(nomeCurso);
            System.out.println("Escolha o nível do curso: ");
            String nivelCurso = escolherNivelCurso();
            int anoCurso;
            if (nivelCurso.isBlank()) {
                System.out.println("Nivel invalido, tente novamente");
            } else {
                System.out.print("Escolha o ano do curso no formato YYYY: ");
                try {
                    anoCurso = Integer.parseInt(in.nextLine());

                } catch (NumberFormatException e) {
                    System.out.println("Ano inválido, tente novamente");
                    return;
                }
                if (checaAno(anoCurso)) {
                    if (!verificaExistenciaCurso(nomeCurso, nivelCurso, anoCurso)) {
                        gravarCursoNoCSV(nomeCurso, nivelCurso, anoCurso);
                    } else {
                        System.out.println("Curso ja existente! ");
                    }
                } else {
                    System.out.println("Ano inválido, tente novamente...");
                }
            }

        }
    }

    //metodo que recebe os dados do novo curso, e grava eles no arquivo cursos.csv
    private static void gravarCursoNoCSV(String nomeCurso, String nivelCurso, int anoCurso) {
        String separator = ";";
        String anoCursoString = String.valueOf(anoCurso);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(nomeCurso);
            bw.write(separator);
            bw.write(nivelCurso);
            bw.write(separator);
            bw.write(anoCursoString);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        geraArquivoDoCurso(nomeCurso, nivelCurso, anoCurso);
    }

    //responsavel por gerar o novo arquivo CSV do curso gravado
    private static void geraArquivoDoCurso(String nomeCurso, String nivelCurso, int anoCurso) {
        String nomeArquivo = nomeCurso + "_" + nivelCurso + "_" + anoCurso + ".csv";
        String filePath = "./BancoDeDados";
        File file;
        List<String> arquivos = retornaNome_Nivel_AnoCSV();
        for (String arquivo : arquivos) {
            if (!new File(filePath + "/" + nomeArquivo).exists()) {
                //se nao existe, cria um arquivo csv novo
                try {
                    new File(filePath + "/" + nomeArquivo).createNewFile();
                    System.out.println("Novo arquivo " + nomeArquivo + " criado com sucesso!");
                } catch (IOException e) {
                    System.out.println("ERROR: " + e.getMessage());
                }
            }
        }
    }

    private static boolean checaAno(int ano) {
        if (String.valueOf(ano).length() == 4) {
            return true;
        }
        return false;
    }

    private static String formatarNomeCurso(String nome) {
        nome = nome.replaceAll(" ", "");
        nome = nome.toUpperCase();
        return nome;
    }

    private static String escolherNivelCurso() {
        Scanner in = new Scanner(System.in);
        System.out.println("1 - Graduacao");
        System.out.println("2 - Pos Graduacao");
        System.out.print("Opcao: ");
        int i = in.nextInt();
        if (i == 1) {
            return "graduacao".toUpperCase();
        } else if (i == 2) {
            return "pos_graduacao".toUpperCase();
        } else if (i > 2) {
            System.out.println("Opção não existente, tente novamente...");
        }
        return "";
    }

    private static boolean verificaExistenciaCurso(String nome, String nivel, int ano) {
        List<Curso> cursos = retornaListaCursos();
        for (Curso c : cursos) {
            if ((c.getNome().equalsIgnoreCase(nome)) && (c.getNivel().equalsIgnoreCase(nivel)) && (c.getAno() == ano)) {
                return true;
            }
        }
        return false;
    }

    //getters
    public String getNome() {
        return nome;
    }

    public String getNivel() {
        return nivel;
    }

    public int getAno() {
        return ano;
    }
}
