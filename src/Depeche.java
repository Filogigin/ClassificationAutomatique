import java.util.ArrayList;

public class Depeche {

    private String id;
    private String date;
    private String categorie;
    private String contenu;
    private ArrayList<String> mots;


    public Depeche(String id, String date, String categorie, String contenu) {
        this.id = id;
        this.date = date;
        this.categorie = categorie;
        this.contenu = contenu;
        this.mots = decoupeEnMots(contenu);
    }


    private ArrayList<String> decoupeEnMots(String contenu) {

        // Peut être créer un tableau comme initDico afin d'opti le temps passer ?

        String chaine = contenu.toLowerCase();
        chaine = chaine.replace('\n', ' ');
        chaine = chaine.replace('-', ' ');
        chaine = chaine.replace('\'', ' ');
        chaine = chaine.replace('.', ' ');
        chaine = chaine.replace(',', ' ');
        chaine = chaine.replace('\'', ' ');
        chaine = chaine.replace('\"', ' ');
        chaine = chaine.replace('(', ' ');
        chaine = chaine.replace(')', ' ');
        chaine = chaine.replace(':', ' ');
        chaine = chaine.replace(';', ' ');
        chaine = chaine.replace('%', ' ');
        chaine = chaine.replace('[', ' ');
        chaine = chaine.replace(']', ' ');
        chaine = chaine.replace('/', ' ');
        chaine = chaine.replace('ᵉ', ' ');
        chaine = chaine.replace('#', ' ');
        chaine = chaine.replace('…', ' ');
        chaine = chaine.replace('-', ' ');
        chaine = chaine.replace('–', ' ');
        chaine = chaine.replace('_', ' ');
        chaine = chaine.replace('+', ' ');
        chaine = chaine.replace('*', ' ');
        chaine = chaine.replace('!', ' ');
        chaine = chaine.replace('?', ' ');
        chaine = chaine.replace('&', ' ');
        chaine = chaine.replace('0', ' ');
        chaine = chaine.replace('1', ' ');
        chaine = chaine.replace('2', ' ');
        chaine = chaine.replace('3', ' ');
        chaine = chaine.replace('4', ' ');
        chaine = chaine.replace('5', ' ');
        chaine = chaine.replace('6', ' ');
        chaine = chaine.replace('7', ' ');
        chaine = chaine.replace('8', ' ');
        chaine = chaine.replace('9', ' ');
        String[] tabchaine = chaine.split(" ");
        ArrayList<String> resultat = new ArrayList<String>();
        for (int i = 0; i < tabchaine.length; i++) {
            if (!tabchaine[i].equals("")) {
                resultat.add(tabchaine[i]);
            }
        }
        return resultat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public ArrayList<String> getMots() {
        return mots;
    }

    public void setMots(ArrayList<String> mots) {
        this.mots = mots;
    }

    public void afficher() {
        System.out.println("---------------------");
        System.out.println("depêche " + id);
        System.out.println("date:" + date);
        System.out.println("catégorie:" + categorie);
        System.out.println(contenu);
        System.out.println("---------------------");
    }

}