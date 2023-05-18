import java.util.HashMap;
import java.awt.*;

public class Piece extends Element{

    private Mur[] murs;

    public Piece(int base, int hauteur){
        super(base,hauteur);
    }
    public Piece(int x, int y, int base, int hauteur) {
        super(x, y, base, hauteur);
        this.murs = getMurs();
    }

    public Mur[] getMurs() {
        Mur[] m = new Mur[4];
        m[0]= new Mur(getPosition().x, getPosition().y-1, getBase(), 1); //Mur au nord de la pièce
        m[1]= new Mur(getPosition().x+getBase(), getPosition().y, 1, getHauteur()); //Mur à l'est de la pièce
        m[2]= new Mur(getPosition().x, getPosition().y + getHauteur() , getBase(), 1); //Mur au sud de la pièce
        m[3]= new Mur(getPosition().x-1, getPosition().y, 1, getHauteur()); //Mur à l'ouest de la pièce
        this.murs = m;
        return m;
    }
    public Mur[] murs() {
        return this.murs;
    }


    public boolean enConflit(Piece piece){
        int x1 = this.getX();
        int y1 = this.getY();
        int base1 = this.getBase();
        int hauteur1 = this.getHauteur();

        int x2 = piece.getX();
        int y2 = piece.getY();
        int base2 = piece.getBase();
        int hauteur2 = piece.getHauteur();

        if((y2 <= y1 && y1 <= y2+hauteur2) || (y1 <= y2 && y2 <= y1+hauteur1)){
            if((x2 <= x1 && x1 <= x2+base2) || (x1 <= x2 && x2 <= x1+base1)){
                return true;
            }
        }
        return false;
    }


    public boolean horsCarte(String[][] m) {
        if (this.getX()>=m.length || this.getY()>=m[0].length){
            return true;
        }
        return false;
    }
}
