public class PaireChaineEntier {
    private String chaine;
    private int entier;

    public PaireChaineEntier(String chaine, int entier) {
        this.chaine = chaine;
        this.entier = entier;
    }

    public String getChaine() {
        return chaine;
    }

    public int getEntier() {
        return entier;
    }

    public void setChaine(String chaine) {
        this.chaine = chaine;
    }

    public void setEntier(int entier) {
        this.entier = entier;
    }

    @Override
    public String toString() {
        return "{chaine='" + chaine + "', entier=" + entier + "}";
    }
}