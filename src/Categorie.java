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