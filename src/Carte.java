import java.awt.*;
import java.io.*;
import java.util.*;
import java.io.File;
import java.util.List;

public class Carte {
    private String[][] matrice;

    private ArrayList<Mur> mursLibres;

    private ArrayList<Piece> piecesLibres;

    private ArrayList<Piece> piecesSurCarte;

    private int echec;

    public Carte(String fileName) {
        this.echec = 0;
        this.mursLibres = new ArrayList<>();
        this.piecesLibres = new ArrayList<>();
        this.piecesSurCarte = new ArrayList<>();
        this.matrice = remplireMatrice(fileName);
    }

    private String[][] remplireMatrice(String fileName) {
        String[][] m = null;
        try {
            Scanner scanner = new Scanner(new File(fileName));

            m = remplireMatriceAvecCasesPleines(scanner);

            placerPieceCentrale(m, scanner);

            lirePiecesLibres(scanner);

            while (echec<100 && piecesLibres.size()>0){
                placerPieceAuHasard(m);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        return m;

    }

    private void placerPieceAuHasard(String[][] m) {
        int indexPieceSurCarte = new Random().nextInt(piecesSurCarte.size());
        Piece pieceSurCarte = piecesSurCarte.get(indexPieceSurCarte);
        int indexMur = new Random().nextInt(pieceSurCarte.murs().length);
        Mur mur = pieceSurCarte.murs()[indexMur];

        int indexPiece = new Random().nextInt(piecesLibres.size());
        Piece piece = this.piecesLibres.get(indexPiece);

        Point porteMur = new Point(new Random().nextInt(mur.getBase()), new Random().nextInt(mur.getHauteur()));
        Point portePiece = new Point(new Random().nextInt(piece.getBase()), new Random().nextInt(piece.getHauteur()));

        int xPorte=-1;
        int yPorte=-1;

        if (mur.getBase()>1){
            xPorte = mur.getX() + porteMur.x;
            yPorte = mur.getY();
        } else {
            xPorte = mur.getX();
            yPorte = mur.getY() + porteMur.y;
        }

        int xPiece = xPorte-portePiece.x;
        int yPiece = yPorte-portePiece.y;


        piece.setPosition(new Point(xPiece, yPiece));
        piece.getMurs();

        while(piece.enConflit(pieceSurCarte)){
            deplacer(mur,piece,pieceSurCarte);
        }

        //Verifier si la piece à ajouter n'est pas au dessus d'une autre meme apres le deplacement
        boolean bienPlace = pieceEstBienPlace(piece);

        if (!piece.horsCarte(m) && bienPlace){
            piecesSurCarte.add(piece);
            piecesLibres.remove(piece);
            mursLibres.addAll(List.of(piece.getMurs()));
            mursLibres.remove(mur);
            updateMatrice(m, xPorte, yPorte, mur);
        } else {
            echec++;
        }


    }

    private boolean pieceEstBienPlace(Piece piece) {
        boolean bienPlace = true;
        for (Piece p : piecesSurCarte){
            if(piece.enConflit(p)){
                bienPlace  = false;
            }
        }
        return bienPlace;
    }

    private void deplacer(Mur mur, Piece piece, Piece p) {
        if(mur.getBase()==1){//mur vertical ==> déplacement à gauche ou à droite
            if(piece.getX()< p.getX()){ //piece a gauche de p
                piece.deplacerGauche();
            } else {
                piece.deplacerDroit();
            }
        } else { //mur horizental ==> déplacement en haut ou en bas
            if (piece.getY()< p.getY()){ //piece au dessus de p
                piece.deplacerHaut();
            } else { //piece au dessous de p
                piece.deplacerBas();
            }
        }
    }

    private void updateMatrice(String[][] m, int xPorte, int yPorte, Mur mur) {
        for (Piece piece : piecesSurCarte){
            for (int i=piece.getX(); i<piece.getX()+piece.getBase(); i++){
                for (int j=piece.getY(); j<piece.getY()+piece.getHauteur(); j++){
                    m[i][j] = ".";
                }
            }
        }

        if (mur.getBase()==1){ //mur vertical donc porte |
            m[xPorte][yPorte] = "|";
        } else { //mur horizental donc porte -
            m[xPorte][yPorte] = "-";
        }

    }

    private void lirePiecesLibres(Scanner scanner) {
        while (scanner.hasNext()){
            int base = scanner.nextInt();
            int hauteur = scanner.nextInt();
            Piece piece = new Piece(base, hauteur);
            this.piecesLibres.add(piece);
        }
    }

    private String[][] remplireMatriceAvecCasesPleines(Scanner scanner) {
        String[][] m;
        int base = scanner.nextInt();
        int hauteur = scanner.nextInt();
        m = new String[base][hauteur];
        for (int i=0; i<base; i++){
            for (int j=0; j<hauteur; j++){
                m[i][j] = "O";
            }
        }
        return m;
    }

    private void placerPieceCentrale(String[][] m, Scanner scanner) {
        int basePC = scanner.nextInt();
        int hauteurPC = scanner.nextInt();
        int x = (m.length-basePC)/2;
        int y = (m[0].length-hauteurPC)/2;

        Piece pieceCentrale = new Piece(x, y, basePC, hauteurPC);

        for (int i=pieceCentrale.getX(); i<pieceCentrale.getX()+pieceCentrale.getBase(); i++){
            for (int j=pieceCentrale.getY(); j<pieceCentrale.getY()+pieceCentrale.getHauteur(); j++){
                m[i][j] = ".";
            }
        }
        piecesSurCarte.add(pieceCentrale);

        Mur[] mursPieceCentrale = pieceCentrale.getMurs();
        for (Mur mur : mursPieceCentrale){
            mursLibres.add(mur);
        }

    }

    public void dessiner() {
        for (int j = 0; j < matrice[0].length; j++) {
            for (int i = 0; i < matrice.length; i++) {
                System.out.print(matrice[i][j]);
            }
            System.out.println();
        }
    }
}
