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
            String currentWord;
            int currentEntier;

            // tant qu'il y a une ligne
            while (scanner.hasNextLine()) {
                int i = 0;
                String ligne = scanner.nextLine();

                while (i < ligne.length() && ligne.charAt(i) != ':') {
                    i++;
                }

                // récupère les valeurs et les met dans des variables
                currentWord = ligne.substring(0, i);
                currentEntier = Integer.parseInt(ligne.substring(i + 1).trim());

                PaireChaineEntier currentPaire = new PaireChaineEntier(currentWord, currentEntier);
                lexique.add(currentPaire);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // calcul du score d'une dépêche pour la catégorie
    public int score(Depeche d) {
        int score = 0;
        for (int i = 0; i < d.getMots().size(); i++) {
            String mot = d.getMots().get(i);
            for (int j = 0; j < lexique.size(); j++) {
                if (lexique.get(j).getChaine().equals(mot)) {
                    score += lexique.get(j).getEntier();
                }
            }
        }
        return score;
    }
}