import java.util.ArrayList;

public class UtilitairePaireChaineEntier {


    public static int indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        return 0;

    }

    public static int entierPourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int i = 0;
        while (i < listePaires.size() && listePaires.get(i).getChaine().equals(chaine)) {
            i++;
        }

        if(i < listePaires.size()) {
            return listePaires.get(i).getEntier();
        }
        return -1; // la chaine n'a pas étais trouvé
    }

    public static String chaineMax(ArrayList<PaireChaineEntier> listePaires) {
        return "SPORT";
    }


    public static float moyenne(ArrayList<PaireChaineEntier> listePaires) {
        return 0;
    }

}
