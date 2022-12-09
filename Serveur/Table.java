package relation;
import java.io.*;
public class Table implements Serializable{
    String Nom;
    Object[][] Tableau;
    String[] NomCol;

//Getter setter
    public String getNom() {
        return Nom;
    }
    public void setNom(String nom) {
        Nom = nom;
    }
    public Object[][] getTableau() {
        return Tableau;
    }
    public void setTableau(Object[][] tableau) {
        Tableau = tableau;
    }
    public String[] getNomCol() {
        return NomCol;
    }
    public void setNomCol(String[] nomCol) {
        NomCol = nomCol;
    }
//Constructeur
    public Table(String nom, Object[][] tableau,String[] nomCol) {
        Nom = nom;
        Tableau = tableau;
        NomCol = nomCol;
    }
    public Table(String nom, Object[][] tableau) {
        Nom = nom;
        Tableau = tableau;
    }
    public Table(){}
}
