import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Classification {


    private static ArrayList<Depeche> lectureDepeches(String nomFichier) {
        //creation d'un tableau de dépêches
        ArrayList<Depeche> depeches = new ArrayList<>();
        try {
            // lecture du fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String id = ligne.substring(3);
                ligne = scanner.nextLine();
                String date = ligne.substring(3);
                ligne = scanner.nextLine();
                String categorie = ligne.substring(3);
                ligne = scanner.nextLine();
                String lignes = ligne.substring(3);
                while (scanner.hasNextLine() && !ligne.equals("")) {
                    ligne = scanner.nextLine();
                    if (!ligne.equals("")) {
                        lignes = lignes + '\n' + ligne;
                    }
                }
                Depeche uneDepeche = new Depeche(id, date, categorie, lignes);
                depeches.add(uneDepeche);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return depeches;
    }


    public static void classementDepeches(ArrayList<Depeche> depeches, ArrayList<Categorie> categories, String nomFichier) {
    }


    public static ArrayList<PaireChaineEntier> initDico(ArrayList<Depeche> depeches, String categorie) {
        ArrayList<PaireChaineEntier> resultat = new ArrayList<>();
        return resultat;

    }

    public static void calculScores(ArrayList<Depeche> depeches, String categorie, ArrayList<PaireChaineEntier> dictionnaire) {
    }

    public static int poidsPourScore(int score) {
        return 0;
    }

    public static void generationLexique(ArrayList<Depeche> depeches, String categorie, String nomFichier) {

    }

    public static void main(String[] args) {

        //Chargement des dépêches en mémoire
        System.out.println("chargement des dépêches");
        ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");

        /*
        for (int i = 0; i < depeches.size(); i++) {
            depeches.get(i).afficher();
        }
        */

        Scanner lecteur = new Scanner(System.in);

        /*
        System.out.print("Saisir un mot a rechercher: ");
        String word = lecteur.nextLine();
        int valWord = UtilitairePaireChaineEntier.entierPourChaine(lexique, word);

        if (valWord == -1) {
            System.out.println(word + " n'a pas été trouvé");
        } else {
            System.out.println(word + " a une valeur de " + valWord);
        }
        */


        Categorie categorieSport = new Categorie("sport");
        categorieSport.initLexique("./sport.txt");
        ArrayList<PaireChaineEntier> sport = categorieSport.getLexique();

        Categorie categorieSciences = new Categorie("sciences");
        categorieSciences.initLexique("./sciences.txt");
        ArrayList<PaireChaineEntier> sciences = categorieSciences.getLexique();

        Categorie categorieEconomie = new Categorie("economie");
        categorieEconomie.initLexique("./economie.txt");
        ArrayList<PaireChaineEntier> economie = categorieEconomie.getLexique();

        Categorie categoriePolitque = new Categorie("politique");
        categoriePolitque.initLexique("./politique.txt");
        ArrayList<PaireChaineEntier> politique = categoriePolitque.getLexique();

        Categorie categorieCulture = new Categorie("culture");
        categorieCulture.initLexique("./culture.txt");
        ArrayList<PaireChaineEntier> culture = categorieCulture.getLexique();

        System.out.println("--> " + categoriePolitque.getLexique()); // renvoie la categories politique et pas culture


        System.out.println(sport);
        System.out.println(sciences);
        System.out.println(economie);
        System.out.println(politique);
        System.out.println(culture);

        System.out.println(UtilitairePaireChaineEntier.chaineMax(sport));
        System.out.println(UtilitairePaireChaineEntier.chaineMax(sciences));
        System.out.println(UtilitairePaireChaineEntier.chaineMax(economie));
        System.out.println(UtilitairePaireChaineEntier.chaineMax(politique));
        System.out.println(UtilitairePaireChaineEntier.chaineMax(culture));


        //ArrayList<Depeche> depeches;



    }


}

