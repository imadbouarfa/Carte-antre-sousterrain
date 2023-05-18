import java.util.Scanner;
/**
 * Cette classe représente un programme générant la carte d’un antre sousterrain
 *
 * @author Imad Bouarfa
 * Code permanent: BOUI24039303
 * Courriel: bouarfa.imad@courrier.uqam.ca
 * Cours: INF2120-010
 * @version 2023-02-17
 */

public class Principal {


    public static void main(String[] args) {

        Scanner clavier = new Scanner(System.in);
        System.out.print("Veuillez saisir le nom du fichier: ");
        String file = clavier.nextLine();

        Carte carte = new Carte(file);

        carte.dessiner();



    }
}
