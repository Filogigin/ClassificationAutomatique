import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Categorie {

    private String nom; // le nom de la catégorie p.ex : sport, politique,...
    private ArrayList<PaireChaineEntier> lexique; // le lexique de la catégorie

    // constructeur
    public Categorie(String nom) {
        this.nom = nom;
        this.lexique = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<PaireChaineEntier> getLexique() {
        return lexique;
    }

    private static ArrayList<PaireChaineEntier> triBulles(ArrayList<PaireChaineEntier> v) {
        int j;
        int i = 0;
        while (i < v.size()-1) {
            j = v.size()-1;
            while (j > i) {
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

    public void initLexique(String nomFichier) {
        try {
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);

            // tant qu'il y a une ligne
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();

                // récupère les valeurs et les met dans des variables
                String currentWord = ligne.substring(0, ligne.indexOf(':'));
                int currentEntier = Integer.parseInt(ligne.substring(ligne.indexOf(':') + 1).trim());

                PaireChaineEntier currentPaire = new PaireChaineEntier(currentWord, currentEntier);
                lexique.add(currentPaire);
            }
            // trie
            System.out.println(lexique);
            lexique = triBulles(lexique);
            System.out.println(lexique);

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // calcul du score d'une dépêche pour la catégorie
    public int score(Depeche d) {
        int score = 0;
        for (String mot : d.getMots()) {
            score += UtilitairePaireChaineEntier.entierPourChaine(lexique, mot);
        }
        return score;
    }
}