import java.util.ArrayList;

public class UtilitairePaireChaineEntier {


    public static int indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int i = 0;

        while (i < listePaires.size() && !listePaires.get(i).getChaine().equals(chaine)) {
            i++;
        }

        if(i == listePaires.size()) {
            return -1;
        }
        return i;
    }

    public static int entierPourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int i = 0;
        while (i < listePaires.size() && !listePaires.get(i).getChaine().equals(chaine)) {
            i++;
        }

        if(i < listePaires.size()) {
            return listePaires.get(i).getEntier();
        }
        return -1; // la chaine n'a pas étais trouvé
    }

    // retourne la chaine associé au plus grand entier de listePaires
    public static String chaineMax(ArrayList<PaireChaineEntier> listePaires) {
        PaireChaineEntier max = listePaires.get(0);

        for (int i = 1; i < listePaires.size(); i++) {
            PaireChaineEntier currentPaire = listePaires.get(i);
            if(max.getEntier() < currentPaire.getEntier()) {
                max = currentPaire;
            }
        }

        return max.getChaine();
    }


    public static float moyenne(ArrayList<PaireChaineEntier> listePaires) {
        int acc = 0;
        for (int i = 0; i < listePaires.size(); i++) {
            acc = acc + listePaires.get(i).getEntier();
        }
        return Math.round(acc / listePaires.size());
    }

}
