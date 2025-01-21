import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ExempleEcritureFichier
{

    public static void main(String[] args) {


        try {
            FileWriter file = new FileWriter("fichier-sortie.txt");
            file.write("chaine saisie :\n");
            file.write("hello"+"\n");
            file.close();
            System.out.println("votre saisie a été écrite avec succès dans fichier-sortie.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
