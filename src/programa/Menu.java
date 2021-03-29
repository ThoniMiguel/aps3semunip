package programa;

import jdk.jshell.execution.Util;

import java.util.Scanner;

public class Menu {    //classe utilitaria responsavel por montar o menu.
    private static boolean exitMenuPrincipal;
    private static boolean exitMenuAlunos;
    private static boolean exitMenuCursos;

    public static void printHeader() {
        System.out.println("+==================================================+");
        System.out.println("|                                                  |");
        System.out.println("|   Bem-Vindo ao Sistema da Universidade Amazônia  |");
        System.out.println("|                                                  |");
        System.out.println("+==================================================+");
    }

    //menu principal
    public static void menuPrincipal() {
        System.out.println("\n  ***    MENU PRINCIPAL   ***  ");
        System.out.println("Por favor, escolha uma opção:");
        System.out.println("1 - Alunos");
        System.out.println("2 - Cursos");
        System.out.println("3 - Incluir novo rendimento");
        System.out.println("0 - Sair");

    }

    public static void runMenuPrincipal() {
        printHeader();
        int choice = 0;
        while (!exitMenuPrincipal) {
            menuPrincipal();
            choice = getEscolha(3);
            realizarAcaoMenuPrincipal(choice);
        }
    }

    private static void realizarAcaoMenuPrincipal(int choice) {
        switch (choice) {
            case 0:
                System.out.println("Obrigado por utilizar o sistema!");
                System.out.println("Encerrando...");
                exitMenuPrincipal = true;
                break;
            case 1:
                if (exitMenuAlunos) {
                    exitMenuAlunos = false;
                    runMenuAlunos();
                } else {
                    runMenuAlunos();
                }
                break;
            case 2:
                if (exitMenuCursos) {
                    exitMenuCursos = false;
                    runMenuCursos();
                } else {
                    runMenuCursos();
                }
                break;
            case 3:
                Rendimento.selecionar();
                Utilitario.pressioneEnter();
                break;
        }
    }
    //fim menu principal

    //menu alunos
    public static void menuAlunos() {
        System.out.println("\n  ***    MENU ALUNOS   ***  ");
        System.out.println("Por favor, escolha uma opção:");
        System.out.println("1 - Listar todos os alunos");
        System.out.println("2 - Listar histórico dos alunos");
        System.out.println("3 - Incluir novo aluno");
        System.out.println("0 - Voltar");
    }

    public static void runMenuAlunos() {
        int choice;
        while (!exitMenuAlunos) {
            menuAlunos();
            choice = getEscolha(3);
            realizarAcaoMenuAlunos(choice);
        }
    }

    private static void realizarAcaoMenuAlunos(int choice) {

        switch (choice) {
            case 0:
                System.out.println("Voltando...");
                exitMenuAlunos = true;
                break;
            case 1:
                Aluno.listarAlunos();
                Utilitario.pressioneEnter();
                break;
            case 2:
                Aluno.listarHistorico();
                Utilitario.pressioneEnter();
                break;
            case 3:
                Aluno.incluirAluno();
                Utilitario.pressioneEnter();
                break;
        }
    }
    //fim menu alunos

    //menu cursos
    public static void menuCursos() {
        System.out.println("\n  ***    MENU CURSOS   ***  ");
        System.out.println("Por favor, escolha uma opção:");
        System.out.println("1 - Listar todos os cursos");
        System.out.println("2 - Listar relatório de rendimento dos cursos");
        System.out.println("3 - Incluir novo curso");
        System.out.println("0 - Voltar");
    }

    public static void runMenuCursos() {
        int choice = 0;
        while (!exitMenuCursos) {
            menuCursos();
            choice = getEscolha(3);
            realizarAcaoMenuCursos(choice);
        }
    }

    private static void realizarAcaoMenuCursos(int choice) {
        switch (choice) {
            case 0:
                System.out.println("Voltando...");
                exitMenuCursos = true;
                break;
            case 1:
                Curso.listarCursos();
                Utilitario.pressioneEnter();
                break;
            case 2:
                Curso.selecionarCurso();
                Utilitario.pressioneEnter();
                break;
            case 3:
                Curso.incluirNovoCurso();
                Utilitario.pressioneEnter();
                break;
        }
    }
    //fim menu cursos

    public static int getEscolha(int esc) {
        int escolha = -1;
        Scanner op = new Scanner(System.in);
        while (escolha < 0 || escolha > esc) {
            try {
                System.out.print("\nOpção: ");
                escolha = Integer.parseInt(op.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("Seleção inválida, por favor, tente novamente!");
            }
            if (escolha > esc) {
                System.out.println("Opção nao existente, tente novamente!");
            }
        }
        return escolha;
    }


}
