import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        try {
            FileWriter file = new FileWriter(nomFichier);

            ArrayList<Integer> nbCategorieDepeche = new ArrayList<>();
            ArrayList<Integer> nbCategorieDepecheReel = new ArrayList<>();

            // initialise nbCategorieDepeche avec des 0 pour l'incrementer
            for (int i = 0; i < categories.size(); i++) {
                nbCategorieDepeche.add(0);
            }

            for (Categorie categorie : categories) {
                int acc = 0;

                for (Depeche depeche : depeches) {
                    if (depeche.getCategorie().toLowerCase().equals(categorie.getNom())) {
                        acc++;
                    }
                }
                nbCategorieDepecheReel.add(acc);
            }

            // parcours des dépêches pour calcul des scores
            for (Depeche depeche : depeches) {
                String meilleureCategorie = categories.get(1).getNom();
                int maxScore = categories.get(1).score(depeche);

                // calcul des scores pour chaque catégorie
                for (Categorie categorie : categories) {
                    int score = categorie.score(depeche); // Calcul du score pour cette catégorie
                    if (score > maxScore) {
                        maxScore = score;
                        meilleureCategorie = categorie.getNom();
                    }
                }
                file.write(depeche.getId() + ": " + meilleureCategorie.toUpperCase() + "\n");

                int i = 0;
                while (i < categories.size()) {
                    if (meilleureCategorie.toLowerCase().equals(categories.get(i).getNom())) {
                        nbCategorieDepeche.set(i, nbCategorieDepeche.get(i) + 1);
                    }
                    i++;
                }
            }

            // calcul pourcentage et moyenne
            int accTrouver = 0;
            int accReel = 0;
            for (int i = 0; i < categories.size(); i++) {
                float nbTrouver = nbCategorieDepeche.get(i);
                float nbReel = nbCategorieDepecheReel.get(i);

                accTrouver += nbCategorieDepecheReel.get(i);
                accReel += nbCategorieDepeche.get(i);

                file.write(categories.get(i).getNom().toUpperCase() + ": " + ((nbTrouver/nbReel)*100) + "%\n");
            }
            file.write("MOYENNE: " + ((accTrouver/accReel)*100) + "%\n");

            file.close();
            System.out.println("votre saisie a été écrite avec succès dans " + nomFichier);

        } catch (IOException e) {
            e.printStackTrace();
        }
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

        //System.out.println("--> " + categoriePolitque.getLexique()); // renvoie la categories politique et pas culture


        System.out.println(sport);
        System.out.println(sciences);
        System.out.println(economie);
        System.out.println(politique);
        System.out.println(culture);

        /*
        System.out.println(UtilitairePaireChaineEntier.chaineMax(sport));
        System.out.println(UtilitairePaireChaineEntier.chaineMax(sciences));
        System.out.println(UtilitairePaireChaineEntier.chaineMax(economie));
        System.out.println(UtilitairePaireChaineEntier.chaineMax(politique));
        System.out.println(UtilitairePaireChaineEntier.chaineMax(culture));

        Depeche d = new Depeche("393", "13/09/2024", "POLITIQUE", "Emmanuel Macron propose d'instaurer une fête nationale du sport tous les 14 septembre. Le président de la République espère ainsi \" réenclencher, pour la rentrée, la pratique du sport au quotidien \". Il apporte par ailleurs son soutien à la décision de la maire de Paris Anne Hidalgo de ne pas retirer les anneaux olympiques de la tour Eiffel.");
        System.out.println(categorieSport.score(d));
*/
        ArrayList<Categorie> listCategories = new ArrayList<>(Arrays.asList(categorieSport, categorieSciences, categoriePolitque, categorieEconomie, categorieCulture));

        ArrayList<Depeche> listDepeches = new ArrayList<>();
        for (int i = 0; i < depeches.size(); i++) {
            listDepeches.add(depeches.get(i));
        }
        classementDepeches(listDepeches, listCategories, "hassoul");

    }


}

