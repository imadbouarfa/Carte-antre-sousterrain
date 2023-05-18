import java.util.Scanner;

public class Principal {


    public static void main(String[] args) {

        Scanner clavier = new Scanner(System.in);
        System.out.print("Veuillez saisir le nom du fichier: ");
        String file = clavier.nextLine();

        Carte carte = new Carte(file);

        carte.dessiner();



    }
}
