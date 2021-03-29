package programa;

import jdk.jshell.execution.Util;

import java.util.Scanner;

public class Nota {
    private float np1, np2, substitutiva, exame;


    public Nota(float np1, float np2, float substitutiva, float exame) {
        this.np1 = np1;
        this.np2 = np2;
        this.substitutiva = substitutiva;
        this.exame = exame;
    }

    public static Nota novasNotas(){
        Scanner in = new Scanner(System.in);
        System.out.println(" *** Por favor, entre com as notas do aluno *** ");
        System.out.print("NP1: ");
        float np1 = formatarNota();
        if(checaNota(np1))
        System.out.print("NP2: ");
        float np2 = formatarNota();
        if(checaNota(np2))
        System.out.print("Substitutiva: ");
        float subs = formatarNota();
        if(checaNota(subs))
        System.out.print("Exame: ");
        float exame = formatarNota();
        checaNota(exame);

        return new Nota(np1, np2, subs, exame);
    }

    private static float formatarNota(){
        Scanner in = new Scanner(System.in);
        float nota;
        try{
            nota = Float.parseFloat(in.nextLine());
            if(nota >= 0.00 && nota <=10.00){
                return nota;
            }
        }catch(NumberFormatException e){
            System.out.println("Valor incorreto, tente novamente");
            Utilitario.pressioneEnter();
            Menu.runMenuPrincipal();
        }
        return -1;
    }

    private static boolean checaNota(float n){
        if(n == -1){
            System.out.println("Valor incorrrrrreto, tente novamente");
            Utilitario.pressioneEnter();
            Menu.runMenuPrincipal();
            return false;
        }else{
            return true;
        }
    }


    public float getNp1() {
        return np1;
    }

    public float getNp2() {
        return np2;
    }

    public float getSubstitutiva() {
        return substitutiva;
    }

    public float getExame() {
        return exame;
    }
}
