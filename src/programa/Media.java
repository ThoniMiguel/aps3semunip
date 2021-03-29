package programa;

public class Media {
    public static final double MEDIA_GRAD = 7.0;
    public static final double MEDIA_POS_GRAD = 5.0;

    public static double calcMediaInicial(double np1, double np2, double notaRep) {
        //checar se a notaRep é maior q np1 ou np2
        if (notaRep > np1 || notaRep > np2) {
            if (np1 > np2) {
                np2 = notaRep;
            } else if (np2 > np1) {
                np1 = notaRep;
            } else if (np1 == np2) {
                np1 = notaRep;
            }
        }
        return (np1 + np2) / 2;
    }

    public static double calcMediaFinal(double mediaInicial, double notaExam, String nivelCurso) {
        double media = (mediaInicial + notaExam) / 2;
        if(nivelCurso.equalsIgnoreCase("POS")) { //calculo da media com nota do exame para POS_graduacao
            if (media >= 5.0) {
                media = 5.0;
            }
            return media;
        }
        else{ //media com nota do exame para graduacao
            return media;
        }
    }
    public static String calcMedia(double np1, double np2, double notaRep, double notaExam, String nivelCurso){
        double mediaInicial = calcMediaInicial(np1, np2, notaRep);
        double mediaFinal = calcMediaFinal(mediaInicial, notaExam, nivelCurso);
        if(nivelCurso.equalsIgnoreCase("pos")){
            return getString(mediaInicial, mediaFinal, MEDIA_POS_GRAD);
        }else{
            return getString(mediaInicial, mediaFinal, MEDIA_GRAD);
        }
    }

    private static String getString(double mediaInicial, double mediaFinal, double mediaPosGrad) {
        if (mediaInicial >= mediaPosGrad) {
            Aluno.aprovado = true;
            return String.format("%.2f", mediaInicial);
        } else if (mediaFinal >= 5.0) {
            Aluno.aprovado = true;
            return String.format("%.2f", mediaFinal);
        } else {
            Aluno.aprovado = false;
            return String.format("%.2f", mediaFinal);
        }
    }


}
