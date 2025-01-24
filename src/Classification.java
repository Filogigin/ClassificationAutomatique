import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Classification {

    public static int compteurComparaisons = 0; // pour les algorithmes outillés

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

            // compteurs pour les categories dans les depeches
            ArrayList<Integer> nbCategorieDepecheReel = new ArrayList<>();

            // initialise nbCategorieDepecheReel avec des 0 pour le nombre de categories
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

            // compteur pour les categories detectées
            ArrayList<Integer> nbCategorieDepeche = new ArrayList<>();

            // Initialise nbCategorieDepeche a 0 pour le nombre de categories
            for (int i = 0; i < categories.size(); i++) {
                nbCategorieDepeche.add(0);
            }

            // parcours des depeches pour le calcul des scores
            for (int i = 0; i < depeches.size(); i++) {
                Depeche depeche = depeches.get(i); // recuperer la dépêche à l'indice i
                String meilleureCategorie = "NON TROUVÉ";
                int maxScore = 0; // on commence à 0, car un score négatif ou nul n'est pas valide
                boolean categorieTrouvee = false;

                // parcours des catégories pour trouver la meilleure
                for (int j = 0; j < categories.size(); j++) {
                    Categorie categorie = categories.get(j);
                    int score = categorie.score(depeche);

                    // si un meilleur score est trouvé, on met à jour
                    if (score > maxScore) {
                        maxScore = score;
                        meilleureCategorie = categorie.getNom();
                        categorieTrouvee = true;
                    }
                }

                // si aucune catégorie n'a de score valide on garde "NON TROUVÉ"
                if (!categorieTrouvee) {
                    file.write(depeche.getId() + ": " + "NON TROUVÉ\n");
                } else {
                    // Sinon, incrémenter le compteur pour la catégorie trouvée
                    int k = 0;
                    while (k < categories.size() && !meilleureCategorie.equalsIgnoreCase(categories.get(k).getNom())) {
                        k++;
                    }
                    if (k < categories.size()) { // verifier que l'indice est valide
                        nbCategorieDepeche.set(k, nbCategorieDepeche.get(k) + 1);
                    }
                    file.write(depeche.getId() + ": " + meilleureCategorie.toUpperCase() + "\n");
                }
            }

            float accMoyenne = 0f;
            // écriture des statistiques dans le fichier
            for (int i = 0; i < categories.size(); i++) {
                float nbTrouve = nbCategorieDepeche.get(i);
                float nbReel = nbCategorieDepecheReel.get(i);

                accMoyenne += nbTrouve;

                file.write(categories.get(i).getNom().toUpperCase() + ": " + String.format("%.1f", ((nbTrouve/nbReel)*100)) + "%\n");
            }
            file.write("MOYENNE: " + String.format("%.1f", (accMoyenne/categories.size())) + "%");

            file.close();
            System.out.println("Les catégories des dépêches ont été écrites avec succès dans " + nomFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<PaireChaineEntier> triBulles(ArrayList<PaireChaineEntier> v) {
        int j;
        int i = 0;
        while (i < v.size()-1) {
            j = v.size()-1;
            while (j > i) {
                //compteurComparaisons++;
                if (v.get(j).getChaine().compareToIgnoreCase(v.get(j-1).getChaine()) < 0) {
                    PaireChaineEntier temporaire = v.get(j);
                    v.set(j, v.get(j-1));
                    v.set(j-1, temporaire);
                }
                j = j - 1;
            }
            i = i + 1;
        }
        return v;
    }


    public static int rechIndDico(ArrayList<PaireChaineEntier> dictionnaire, String chaine) {
        // Vérification des préconditions
        if (dictionnaire == null || dictionnaire.isEmpty()) {
            return -1;
        }

        // Vérifie si la chaîne est hors des bornes alphabétiques
        //compteurComparaisons++;
        if (dictionnaire.get(dictionnaire.size() - 1).getChaine().compareToIgnoreCase(chaine) < 0) {
            return -1;
        }

        int inf = 0;
        int sup = dictionnaire.size() - 1;

        while (inf < sup) {
            int milieu = (inf + sup) / 2;

            //compteurComparaisons++; // On compare une fois ici
            if (dictionnaire.get(milieu).getChaine().compareToIgnoreCase(chaine) >= 0) {
                sup = milieu; // Réduit la borne supérieure
            } else {
                inf = milieu + 1; // Augmente la borne inférieure
            }
        }

        //compteurComparaisons++; // Comparaison finale pour vérifier la correspondance exacte
        if (dictionnaire.get(sup).getChaine().compareToIgnoreCase(chaine) == 0) {
            return sup;
        }
        return -1;
    }

    public static ArrayList<PaireChaineEntier> initDico(ArrayList<Depeche> depeches, String categorie) {
        ArrayList<String> words = new ArrayList<>();
        ArrayList<PaireChaineEntier> dictionnaire = new ArrayList<>();
        ArrayList<String> banWords = new ArrayList<>(Arrays.asList(
                "le", "la", "et", "de", "les", "un", "une", "des", "en", "du", "au",
                "aux", "à", "pour", "par", "sur", "dans", "avec", "comme", "est",
                "ce", "cette", "son", "sa", "ses", "qui", "que", "qu", "l", "d",
                "aujourd", "hui", "ont", "était", "ils", "elle", "elles", "il", "on",
                "ne", "pas", "plus", "ou", "a", "y", "sont", "été","mais",
                "se", "leur", "lui", "nous", "vous", "vos", "deux", "entre", "aussi",
                "c", "n","peuvent", "ceux","quels", "quel", "dont", "ces", "dès",
                "même", "tous", "toutes", "tout","faut", "avant", "après", "bien", "mal",
                "aucun", "aucune", "chaque", "autre", "autres", "jamais", "toujours", "quelque",
                "quelques", "sans", "certain", "certaine", "certains", "certaines","leurs",
                "mon", "ma", "mes", "notre", "nos", "votre","ceci", "cela",
                "lors", "lorsque", "donc", "car", "si", "quand", "où","très", "beaucoup",
                "peu", "plusieurs", "moins","trop", "autant", "encore", "seulement", "souvent",
                "déjà", "ensuite", "puis", "alors", "tandis", "quoi", "chez", "hors",
                "quelle", "quelles","tant", "tantôt", "autour", "vers", "devant",
                "derrière", "dessous", "dessus", "loin", "près", "là", "ici", "partout", "nulle part",
                "dedans", "dehors","presque","malgré", "selon",
                "environ", "parmi", "depuis", "pendant","ailleurs", "or", "ni", "je",
                "tu", "doit", "peut", "veut", "vient", "fait", "va", "suis", "es", "sera", "seront", "avait",
                "avaient", "allé", "allée", "allés", "vont", "viens", "venu","venus","venez","dit","dire","pris",
                "prend","prendre","vu","voir","crois","croire","semblez", "semble","parle",
                "parler", "parlent", "écoute", "écouter", "regarde", "regarder", "regardez","auparavant",
                "quelqu'un", "personne", "rien", "chose", "manière", "façon", "moment", "endroit", "travail", "vie",
                "jour", "nuit"
        ));
        for (int i = 0; i < depeches.size(); i++) {
            // si la depeche courrante est egale à la categorie, execute
            if (depeches.get(i).getCategorie().equalsIgnoreCase(categorie)) {
                // parcours les mot de la ligne courante
                for (int j = 0; j < depeches.get(i).getMots().size(); j++) {
                    String word = depeches.get(i).getMots().get(j);
                    // si le mot n'est pas contenu dans les mots deja ajouter alors on l'ajoute (si il n'est pas en double)
                    if (!words.contains(word)) {
                        words.add(word);
                    }
                }
            }
        }

        // permet d'ajouter les mots unique et pas banni
        for (int i = 0; i < words.size(); i++) {
            // si le mot n'est pas contenu dans banwords et qu'il n'est pas un nombre on l'ajoute au dictionnaires
            if (!banWords.contains(words.get(i))) {
                dictionnaire.add(new PaireChaineEntier(words.get(i), 0));
            }
        }
        return dictionnaire;
    }


    public static void calculScores(ArrayList<Depeche> depeches, String categorie, ArrayList<PaireChaineEntier> dictionnaire) {
        triBulles(dictionnaire);

        for (Depeche depeche : depeches) {
            ArrayList<String> mots = depeche.getMots(); // Découpe le texte en mots
            for (String mot : mots) {
                mot = mot.toLowerCase(); // Normalisation des mots
                int indice = rechIndDico(dictionnaire, mot); // Recherche dichotomique
                //compteurComparaisons++;
                if (indice != -1) { // Si le mot est trouvé dans le dictionnaire
                    PaireChaineEntier paire = dictionnaire.get(indice);
                    //compteurComparaisons++;
                    if (depeche.getCategorie().equalsIgnoreCase(categorie)) {
                        paire.setEntier(paire.getEntier() + 1); // Incrémenter le score si dans la catégorie
                    } else {
                        paire.setEntier(paire.getEntier() - 1); // Décrémenter le score sinon
                    }
                }
            }
        }
/*
        for (Depeche depeche : depeches) {
            ArrayList<String> mots = depeche.getMots(); // Découpe le texte en mots

            for (String mot : mots) {
                mot = mot.toLowerCase(); // Normalisation des mots
                for (PaireChaineEntier paire : dictionnaire) {
                    //compteurComparaisons++;
                    if (paire.getChaine().equals(mot)) {
                        //compteurComparaisons++;
                        if (depeche.getCategorie().equalsIgnoreCase(categorie)) {
                            paire.setEntier(paire.getEntier() + 1); // Incrémenter le score si dans la catégorie
                        } else {
                            paire.setEntier(paire.getEntier() - 1); // Décrémenter le score sinon
                        }
                    }
                }
            }
        }
*/
    }


    public static int poidsPourScore(int score) {
        if (score >= 2 && score <= 5) {
            return 1; // score compris entre 1 et 5
        } else if (score > 5 && score <= 10) {
            return 2; // score compris entre 6 et 10
        } else if (score > 10) {
            return 3; // score supérieur à 10
        }
        return 0;  // score inferieur à 1
    }


    public static void generationLexique(ArrayList<Depeche> depeches, String categorie, String nomFichier) {
        try {
            FileWriter file = new FileWriter(nomFichier);
            ArrayList<PaireChaineEntier> dictionnaire = initDico(depeches, categorie); // initialise le dictionnaire
            calculScores(depeches, categorie, dictionnaire); // calcul les scores des mots du dictionnaire

            for (PaireChaineEntier paire : dictionnaire) {
                String mot = paire.getChaine();
                int score = paire.getEntier();
                int poids = poidsPourScore(score);

                if (poids != 0) {
                    file.write(mot + ": " + poids + "\n");
                }
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Scanner lecteur = new Scanner(System.in);

        //Chargement des dépêches en mémoire
        System.out.println("chargement des dépêches");
        ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");
        ArrayList<Depeche> test = lectureDepeches("./test.txt");

        // création des categories et des lexiques (un lexique est une liste de mots qui sont assosié a des valeurs)
        Categorie categorieSport = new Categorie("sport");
        categorieSport.initLexique("./sport.txt");
        ArrayList<PaireChaineEntier> lexiqueSport = categorieSport.getLexique();

        Categorie categorieSciences = new Categorie("sciences");
        categorieSciences.initLexique("./sciences.txt");
        ArrayList<PaireChaineEntier> lexiqueSciences = categorieSciences.getLexique();

        Categorie categorieEconomie = new Categorie("economie");
        categorieEconomie.initLexique("./economie.txt");
        ArrayList<PaireChaineEntier> lexiqueEconomie = categorieEconomie.getLexique();

        Categorie categoriePolitque = new Categorie("politique");
        categoriePolitque.initLexique("./politique.txt");
        ArrayList<PaireChaineEntier> lexiquePolitique = categoriePolitque.getLexique();

        Categorie categorieCulture = new Categorie("culture");
        categorieCulture.initLexique("./culture.txt");
        ArrayList<PaireChaineEntier> lexiqueCulture = categorieCulture.getLexique();

        System.out.println("Lexique sport: "+ lexiqueSport);

/*
        // test dans le lexique pour rechercher la valeur d'un mot
        System.out.print("\nSaisir un mot a rechercher dans le lexique sport: ");
        String word = lecteur.nextLine();
        int valWord = UtilitairePaireChaineEntier.entierPourChaine(lexiqueSport, word);
        System.out.println(word + " a une valeur de " + valWord);
*/

        // affichage du score de la depeche 20
        Depeche depecheTest = depeches.get(19);
        System.out.println("\nCalcul des score sur la dépeche n°" + depecheTest.getId());
        depecheTest.afficher();



        ArrayList<Categorie> categories = new ArrayList<>(Arrays.asList(categorieSport, categorieSciences, categoriePolitque, categorieEconomie, categorieCulture));

        // remplir le vecteur des scores pour chaque categorie
        ArrayList<PaireChaineEntier> vScores = new ArrayList<>();
        for (Categorie categorie : categories) {
            int score = categorie.score(depecheTest); // calcul du score pour la dépeche dans la catégorie courante
            vScores.add(new PaireChaineEntier(categorie.getNom(), score));
            System.out.println("Score sur le lexique " + categorie.getNom() + ": " + score);
        }

        // Trouver la catégorie ayant le score maximal pour la dépêche testée
        String categorieMax = UtilitairePaireChaineEntier.chaineMax(vScores);
        System.out.println("\nLa catégorie avec le score maximal pour la dépeche n°" + depecheTest.getId() + " est " + categorieMax);


        // Création d'une liste contenant les noms des catégories
        ArrayList<String> nomCategories = new ArrayList<>(Arrays.asList("sport", "sciences", "politique", "economie", "culture"));

        // Génération des lexiques pour chaque catégorie
        for (int i = 0; i < nomCategories.size(); i++) {
            generationLexique(depeches, nomCategories.get(i), nomCategories.get(i) + ".txt");
        }

        // classement des dépeches en fonction des scores calculés pour chaque catégorie
        classementDepeches(test, categories, "resultat.txt");

        long endTime = System.currentTimeMillis();
        System.out.println("\nVotre programme a été réalisée en : " + (endTime-startTime) + "ms");

        //System.out.println("Il y a eu " + compteurComparaisons + " comparaisons !");

    }
}