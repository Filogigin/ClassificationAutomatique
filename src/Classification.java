import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.Object;
import java.util.regex.Pattern;

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
        /*
        * idée: faire une liste dans une liste [[categorie, conteur], [categorie, conteur]]
        * round les moyenne
        * */
        //long startTime = System.currentTimeMillis();
        try {
            FileWriter file = new FileWriter(nomFichier);

            // Compteurs pour les catégories dans les depeches
            ArrayList<Integer> nbCategorieDepecheReel = new ArrayList<>();

            // Initialise nbCategorieDepecheReel à 0 pour le nombre de categories
            for (int i = 0; i < categories.size(); i++) {
                Categorie categorie = categories.get(i);
                int acc = 0;

                for (int j = 0; j < depeches.size(); j++) {
                    Depeche depeche = depeches.get(j);

                    if (depeche.getCategorie().toLowerCase().equals(categorie.getNom())) {
                        acc++;
                    }
                }
                nbCategorieDepecheReel.add(acc);
            }

            // Compteurs pour les catégories détectées
            ArrayList<Integer> nbCategorieDepeche = new ArrayList<>();

            // Initialise nbCategorieDepeche à 0 pour le nombre de categories
            for (int i = 0; i < categories.size(); i++) {
                nbCategorieDepeche.add(0);
            }

            // Parcours des dépêches pour calcul des scores
            for (int i = 0; i < depeches.size(); i++) {
                Depeche depeche = depeches.get(i); // Récupérer la dépêche à l'indice d
                String meilleureCategorie = "NON TROUVÉ";
                int maxScore = 0; // On commence à 0, car un score négatif ou nul n'est pas valide
                boolean categorieTrouvee = false;

                // Parcours des catégories pour trouver la meilleure
                for (int j = 0; j < categories.size(); j++) {
                    Categorie categorie = categories.get(j);
                    int score = categorie.score(depeche);

                    // Si un meilleur score est trouvé, on met à jour
                    if (score > maxScore) {
                        maxScore = score;
                        meilleureCategorie = categorie.getNom();
                        categorieTrouvee = true;
                    }
                }

                // Si aucune catégorie n'a de score valide, garder "NON TROUVÉ"
                if (!categorieTrouvee) {
                    file.write(depeche.getId() + ": " + "NON TROUVÉ" + "\n");
                } else {
                    // Sinon, incrémenter le compteur pour la catégorie trouvée
                    int k = 0;
                    while (k < categories.size() && !meilleureCategorie.equalsIgnoreCase(categories.get(k).getNom())) {
                        k++;
                    }
                    if (k < categories.size()) { // Vérifier que l'indice est valide
                        nbCategorieDepeche.set(k, nbCategorieDepeche.get(k) + 1);
                    }
                    file.write(depeche.getId() + ": " + meilleureCategorie.toUpperCase() + "\n");
                }
            }

            float accMoyenne = 0f;
            // Écriture des statistiques dans le fichier
            for (int i = 0; i < categories.size(); i++) {
                float nbTrouve = nbCategorieDepeche.get(i);
                float nbReel = nbCategorieDepecheReel.get(i);

                accMoyenne += nbTrouve;

                file.write(categories.get(i).getNom().toUpperCase() + ": " + ((nbTrouve/nbReel)*100) + "%\n");
            }
            file.write("MOYENNE: " + (accMoyenne/categories.size()) + "%");

            // Fermeture du fichier
            file.close();
            System.out.println("Les catégories des dépêches ont été écrites avec succès dans " + nomFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        long endTime = System.currentTimeMillis();
        System.out.println("votre saisie a été réalisée en : " + (endTime-startTime) + "ms");
        */
    }

    // Fonction pour vérifier si une chaîne est un nombre
    private static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str); // Vérifie si la chaîne peut être convertie en un nombre
            return true; // Si ça marche, c'est un nombre
        } catch (NumberFormatException e) {
            return false; // Sinon, ce n'est pas un nombre
        }
    }

    public static ArrayList<PaireChaineEntier> initDico(ArrayList<Depeche> depeches, String categorie) {
        ArrayList<String> words = new ArrayList<>();
        ArrayList<PaireChaineEntier> dictionnaire = new ArrayList<>();

        ArrayList<String> banwords = new ArrayList<>(Arrays.asList
                ("le", "la", "et", "de", "les", "un", "une", "des", "en", "du", "au",
                        "aux", "à", "pour", "par", "sur", "dans", "avec", "comme", "est",
                        "ce", "cette", "son", "sa", "ses", "qui", "que", "qu", "l", "d",
                        "aujourd", "hui" ,"ont", "était", "ils", "elle", "elles", "il", "on",
                        "ne", "pas", "plus", "ou", "a", "y", "sont", "été", "fait", "mais",
                        "se", "leur", "lui", "nous", "vous", "vos", "deux", "entre", "aussi",
                        "c", "n", "ainsi", "peuvent", "ceux", "quelles", "quels", "quel", "dont", "ces", "dès"));

        for (Depeche depech : depeches) {
            if (depech.getCategorie().equalsIgnoreCase(categorie)) {
                for (int j = 0; j < depech.getMots().size(); j++) {
                    String word = depech.getMots().get(j);
                    if (!words.contains(word)) {
                        words.add(word);
                    }
                }
            }
        }

        for (int i = 0; i < words.size(); i++) {
            if (!banwords.contains(words.get(i)) & !isNumeric(words.get(i))) {
                dictionnaire.add(new PaireChaineEntier(words.get(i), 0));
            }
        }
        for (int i = 0; i < dictionnaire.size(); i++){
            System.out.println(dictionnaire.get(i) + "\n");
        }
        return dictionnaire;
    }


    public static void calculScores(ArrayList<Depeche> depeches, String categorie, ArrayList<PaireChaineEntier> dictionnaire) {

    }

    public static int poidsPourScore(int score) {
        if (score <= 0) {
            return 0; // entre 0 et -infini
        } else if (score <= 5) {
            return 1; // entre 1 et 5
        } else if (score <= 10) {
            return 2; // entre 6 et 10
        }
        return 3; // entre 11 et +infini

    }

    public static void generationLexique(ArrayList<Depeche> depeches, String categorie, String nomFichier) {

    }

    public static void main(String[] args) {

        //long startTime = System.currentTimeMillis();

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

        /*
        long endTime = System.currentTimeMillis();
        System.out.println("votre saisie a été réalisée en : " + (endTime-startTime) + "ms");
        */

        ArrayList<PaireChaineEntier> dico = initDico(depeches, "sport");
        calculScores(depeches, "sport", dico);

        Pattern pattern = Pattern.compile(".*[^0-9].*");



    }


}