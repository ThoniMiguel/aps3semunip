package programa;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Utilitario {
    public static void pressioneEnter() {
        System.out.println("\nPressione ENTER para continuar...");
        try {
            System.in.read();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public static String retornaCapitalizado(String nome){
        if(nome.isBlank()){
            return "";
        }else {
            nome = nome.toLowerCase();
            String output = Arrays.stream(nome.split("\\s+")).map(t -> t.substring(0, 1).toUpperCase() + t.substring(1)).collect(Collectors.joining(" "));
            return output;
        }
    }

}
