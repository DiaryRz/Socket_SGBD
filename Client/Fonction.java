package fonction;
import relation.*;
import java.io.*;
import java.lang.reflect.*;
import java.lang.Object.*;
import java.util.*;
import exception.*;
public class Fonction {

//Inserer dqns un fichier
    public void Fichier(Object Nom ) throws Exception
    {
        Field[] att = Nom.getClass().getDeclaredFields();
        String[] Name = new String[att.length];
        for(int i = 0 ; i < att.length ; i++)
        {
            Name[i] = Nom.getClass().getDeclaredFields()[i].getName();
        }
        Method mt = Nom.getClass().getMethod("get"+Name[0]);
        String name = (String) mt.invoke(Nom);

        File casa=new File(name+".txt");
        try(BufferedWriter ecrivain=new BufferedWriter(new FileWriter(casa,true)))
        {
            Method mth = Nom.getClass().getMethod("get"+Name[1]);
            Object[][] Tab= (Object[][])mth.invoke(Nom);
            int LimI = Tab.length;
            int LimJ = Tab[0].length;
            String[][] Donnes = new String[LimI][LimJ];

            Method methode =  Nom.getClass().getMethod("get"+Name[2]);
            Object[] DonnesColonne=  (Object[])methode.invoke(Nom);
            String[] colonne = new String[LimJ];

//Ecrire Nom et Nom de colonne
            ecrivain.write(name);
            ecrivain.write("\n");
            for(int h = 0 ; h < LimJ ; h++)
            {
                colonne[h] = String.valueOf(DonnesColonne[h]);
                ecrivain.write(colonne[h]);
                if(h != LimJ-1)
                {
                    ecrivain.write("--");
                }
            }
            ecrivain.write("\n");
            for(int i = 0 ; i < LimI ; i++)
            {
                for(int j = 0 ; j < LimJ ; j++)
                {
                    Donnes[i][j] = String.valueOf(Tab[i][j]);
                    ecrivain.write(Donnes[i][j]);
                    if(j == LimJ-1)
                    {
                        ecrivain.write("\n");
                    }
                    else{ecrivain.write(",");}
                }
            }
        } catch(Exception e ){System.out.println(e); }
    }
//Nombre de ligne
    public int nbLigne(String Nom) throws Exception
    {
        int nb = 0 ;
        Table aretourner = new Table();
        File casa=new File(Nom+".txt");
        try(BufferedReader lecteur=new BufferedReader(new FileReader(casa)))
        {
            while((lecteur.readLine()) != null)
            {
                nb++;
            }
        } catch(IOException err){err.printStackTrace();}
        return nb;
    }

//Lire ce qu'il y a dans le ficiher
    public Table Recuperation(String Nom) throws Exception
    {
        int Ligne = this.nbLigne(Nom);
        Table aretourner = new Table();
        File casa=new File(Nom+".txt");
        try(BufferedReader lecteur=new BufferedReader(new FileReader(casa)))
        {
            String[] text = new String[Ligne];
            for(int i = 0 ; i < text.length ; i++)
            {
                text[i] = lecteur.readLine();
            }
            String NomClass = text[0];
            String[] NomCol = text[1].split("--");
            int tailleTab = Ligne-2;
            String[][] Tableau = new String[tailleTab][NomCol.length];
            for(int j = 2 ; j < Ligne ; j++)
            {
                Tableau[j-2] = text[j].split(",");
            }

            aretourner = new Table(NomClass , Tableau , NomCol);
            
        }  catch(IOException err){err.printStackTrace();}

        return aretourner;
    }
//Select 
    public Table Select(String Nom) throws Exception
    {
        Table t = this.Recuperation(Nom);
        return t;
    }
//Union
    public Table Union(String r1,String r2) throws Exception
    {
        String Nom = "Union";
        Table union = new Table();
        Table t1 = this.Recuperation(r1);
        Table t2 = this.Recuperation(r2);
        String[] Colonne= t1.getNomCol();
        Object[][] tableau1= t1.getTableau();
        Object[][] tableau2= t2.getTableau();
        int LigneTotal = tableau1.length + tableau2.length;
        int nbColonne = tableau1[0].length;
        Object[][] total = new Object[LigneTotal][nbColonne];
        if(tableau1[0].length == tableau2[0].length)
        {
            for(int i = 0 ; i < tableau1.length ; i++)
            {
                for(int h = 0 ; h < tableau2.length ; h++)
                {
                    for(int j = 0 ; j < nbColonne ; j++)
                    {
                        total[i][j] = tableau1[i][j];
                        if(i==tableau1.length-1)
                        {
                            total[h+tableau1.length][j] = tableau2[h][j];
                        }
                    }
                }
            }
        } else {
            throw new Exception("Colonne incompatible");
        }
        union = new Table(Nom, total , Colonne);
        return union;
    }

///-----------------------------------------------Difference------------------------------------------------------------------------
//Somme String
    public String SommeString(String[] unite)
    {
        String somme = new String();
        for(int i = 0 ; i < unite.length ; i++)
        {
            somme = somme + unite[i];
        }
        return somme;
    }

//taille Difference chacun
    public int LigneDiff(String r1,String r2) throws Exception
    {
        Table t1 = this.Recuperation(r1);
        Table t2 = this.Recuperation(r2);
        Object[][] table1 = t1.getTableau();
        Object[][] table2 = t2.getTableau();
        String[] somme1 = new String[table1.length];
        String[] somme2 = new String[table2.length];
        int taille = 0 ;
        
        for(int i = 0 ; i < table1.length ; i++)
        {
            somme1[i] = this.SommeString((String[])table1[i]);
            for(int j = 0 ; j < table2.length ; j++)
            {
                somme2[j] = this.SommeString((String[])table2[j]);
                if(somme1[i].equals(somme2[j]) == false)
                {
                    taille++;
                }
            }
        }
        return taille;
    }

//taille Difference final
public int LigneDiffFinal(String r1,String r2) throws Exception
    {
        Table t1 = this.Recuperation(r1);
        Table t2 = this.Recuperation(r2);
        Object[][] table1 = t1.getTableau();
        Object[][] table2 = t2.getTableau();
        String[] somme1 = new String[table1.length];
        String[] somme2 = new String[table2.length];
        int[] tailleAvantFinal = new int[this.LigneDiff(r1, r2)];
        int taille = 0;
        
        for(int i = 0 ; i < table1.length ; i++)
        {
            somme1[i] = this.SommeString((String[])table1[i]);
            for(int j = 0 ; j < table2.length ; j++)
            {
                somme2[j] = this.SommeString((String[])table2[j]);
                if(somme1[i].equals(somme2[j]) == false)
                {
                    tailleAvantFinal[i] = tailleAvantFinal[i] + 1 ;
                    if(tailleAvantFinal[i] == table2.length)
                    {
                        taille++;
                    }
                }
            }
        }
        return taille;
    }

//indice des difference
    public int[] PreDifference(String r1,String r2) throws Exception
    {
        Table t1 = this.Recuperation(r1);
        Table t2 = this.Recuperation(r2);
        Object[][] table1 = t1.getTableau();
        Object[][] table2 = t2.getTableau();
        String[] somme1 = new String[table1.length];
        String[] somme2 = new String[table2.length];
        int taille = this.LigneDiff(r1, r2);
        int[] Compte = new int[taille];
        int tailleFinal = this.LigneDiffFinal(r1, r2);
        Table GranT = new Table();
        int[] indiceGranT = new int[tailleFinal];
        int m = 0;
        
        for(int i = 0 ; i < table1.length ; i++)
        {
            somme1[i] = this.SommeString((String[])table1[i]);
            for(int j = 0 ; j < table2.length ; j++)
            {
                somme2[j] = this.SommeString((String[])table2[j]);
                if(somme1[i].equals(somme2[j]) == false)
                {
                    Compte[i] = Compte[i] +1;
                    if(Compte[i] == table2.length)
                    {
                        indiceGranT[m] = i;
                        m++;
                    }
                }
            }
        }
        return indiceGranT;
    }

//Difference
    public Table Difference(String r1,String r2) throws Exception
    {
        Table t1 = this.Recuperation(r1);
        Table t2 = this.Recuperation(r2);
        Object[][] table1 = t1.getTableau();
        Object[][] table2 = t2.getTableau();
        String[] somme1 = new String[table1.length];
        String[] somme2 = new String[table2.length];
        int[] indice = this.PreDifference(r1,r2);
        int taille = this.LigneDiffFinal(r1,r2);
        Table GranT = new Table();
        Object[][] GrandTable = new Object[taille][table1[0].length];
        String NomTable = t1.getNom();
        String[] Attribut = t1.getNomCol();
        int m = 0;
        if(table1[0].length == table2[0].length)
        {
            for(int i = 0 ; i < indice.length ; i++)
            {
                for(int j = 0 ; j < GrandTable[0].length ; j++)
                {
                    GrandTable[i][j] = table1[indice[i]][j];
                }
            }
        } else {
            throw new Exception("colonne incompatible");
        }
        
        GranT = new Table(NomTable , GrandTable , Attribut);
        return GranT;
    }

// -----------------------------------------------Condition-----------------------------------------------
//Ligne 
    public int LigneCondition(String r,String att,String Condition) throws Exception
    {
        Table t = this.Recuperation(r);
        Object[][] tableau = t.getTableau();
        String[] attribut = t.getNomCol();
        int taille = 0;
        for(int i = 0 ; i < tableau.length ; i++)
        {
            for(int j = 0 ; j < tableau[0].length ; j++)
            {
                if(attribut[j].equals(att))
                {
                    if(tableau[i][j].equals(Condition))
                    {
                        taille++;
                    }
                }
            }
        }
        return taille;
    }

//Condition
    public int[] PreCondition(String r,String att,String Condition) throws Exception
    {
        Table t = this.Recuperation(r);
        Object[][] tableau = t.getTableau();
        String[] attribut = t.getNomCol();
        String Nom = t.getNom();
        int taille = this.LigneCondition(r,att,Condition);
        int[] indice = new int[taille];
        int m = 0;
        Table aretourner = new Table();
        for(int i = 0 ; i < tableau.length ; i++)
        {
            for(int j = 0 ; j < tableau[0].length ; j++)
            {
                if(attribut[j].equals(att))
                {
                    if(tableau[i][j].equals(Condition))
                    {
                        indice[m] = i;
                        m++;
                    }
                }
            }
        }
        return indice;
    }

    public Table Condition(String r,String att,String Condition) throws Exception
    {
        Table aretourner = new Table();
        Table t = this.Recuperation(r);
        Object[][] tableau = t.getTableau();
        String[] attribut = t.getNomCol();
        String Nom = t.getNom();
        int taille = this.LigneCondition(r,att,Condition);
        int[] indiceDeI = this.PreCondition(r, att, Condition);
        Object[][] TableauFinal = new Object[taille][tableau[0].length];
        for(int i = 0 ; i < TableauFinal.length ; i++)
        {
            for(int j = 0 ; j < TableauFinal[0].length ; j++)
            {
                TableauFinal[i][j] = tableau[indiceDeI[i]][j];
            }
        }
        aretourner = new Table(Nom,TableauFinal,attribut);
        return aretourner;
    }
//-----------------------------------Produit cartesienne-------------------------------------------------------
    public Table ProduitCartesienne(String r1,String r2) throws Exception
    {
        Table t1 = this.Recuperation(r1);
        Table t2 = this.Recuperation(r2);
        Object[][] table1 = t1.getTableau();
        Object[][] table2 = t2.getTableau();
        String[] att = t1.getNomCol();
        String Nom = "Cartesienne";
        int ligne = table1.length * table2.length;
        int colonne = table1[0].length + table2[0].length;
        Object[][] TableauFinal = new Object[ligne][colonne];
        int i=0;
        Table GranT = new Table();
        for(int j = 0 ; j < table1.length ; j++)
        {
            for(int m = 0 ; m <table2.length ; m++)
            {
                int k = 0 ;
                for(int l = 0 ; l < table1[0].length ; l++)
                {
                    TableauFinal[i][k] = table1[j][l];
                    k++;
                }
                for(int l = 0 ; l < table2[0].length ; l++)
                {
                    TableauFinal[i][k] = table2[m][l];
                    k++;
                }
                i++;
            }
        }
        GranT = new Table(Nom,TableauFinal,att);
        return GranT;
    }

//---------------------------------Projection-------------------------------------------------
//taille de la colonne de Projection
    public int tailleProjection(String r,String Selection) throws Exception
    {
        Table t = this.Recuperation(r);
        String[] NomAtt = t.getNomCol();
        String[] ListASelectionner = Selection.split(",");
        int taille = 0;
        for(int i = 0 ; i < NomAtt.length ; i++)
        {
            for(int j = 0 ; j < ListASelectionner.length ; j++)
            {
                if(NomAtt[i].equals(ListASelectionner[j]))
                {
                    taille++;
                }
            }
        }
        return taille;
    }

//Projection
    public Table Projection(String r,String Selection) throws Exception
    {
        Table t = this.Recuperation(r);
        Object[][] tableau = t.getTableau();
        String[] NomAtt = t.getNomCol();
        String Nom = "Projection"+t.getNom();
        String[] ListASelectionner = Selection.split(",");
        int taille = this.tailleProjection(r,Selection);
        Object[][] GrandTable = new Object[tableau.length][taille];
        Table GranT = new Table();
        for(int m = 0 ; m < tableau.length ; m++)
        {
            int k = 0;
           for(int i = 0 ; i < NomAtt.length ; i++)
            {
                for(int j = 0 ; j < ListASelectionner.length ; j++)
                {
                    if(NomAtt[i].equals(ListASelectionner[j]))
                    {
                        GrandTable[m][k] = tableau[m][j];
                        k++;
                    }
                }
            } 
        }
        GranT = new Table(Nom , GrandTable , NomAtt);
        return GranT;
    }
//----------------------------------------------Jointure------------------------------------------------------
    public int indiceJoin(String r1,String key1) throws Exception
    {
        Table t1 = this.Recuperation(r1);
        Object[][] table1 = t1.getTableau();
        String[] att1 = t1.getNomCol();
        int j1 = 0 ;
        for(int i = 0 ; i < att1.length ; i++)
        {
            if(att1[i].equals(key1))
            {
                j1 = i;
            }
        }
        return j1;
    }

//Doublons
    public boolean Doublons(String r , String att) throws Exception
    {
        Table t = this.Recuperation(r);
        Object[][] table1 = t.getTableau();
        int j = this.indiceJoin(r, att);
        int compte = 0 ;
        boolean reponse = false;
        for(int i = 0 ; i < table1.length ; i++)
        {
            for(int m = 0 ; m < table1.length ; m++)
            {
                if(table1[m][j].equals(table1[i][j]))
                {
                    compte++;
                }
                if(compte > table1.length)
                {
                    reponse = true;
                }
            }
        }
        return reponse;
    }

//Jointure
    public Table Jointure(String r1,String r2,String key1 , String key2) throws Exception
    {
        Table t1 = this.Recuperation(r1);
        Table t2 = this.Recuperation(r2);
        Object[][] table1 = t1.getTableau();
        Object[][] table2 = t2.getTableau();
        String[] att1 = t1.getNomCol();
        String[] att2 = t2.getNomCol();
        String Nom = "Jointure";
        int j1 = this.indiceJoin(r1, key1);
        int j2 = this.indiceJoin(r2, key2);
        int colone = table1[0].length+table2[0].length;
        Object[][] GranTable = new Object[table1.length][colone];
        Table Join = new Table();
        boolean verify = this.Doublons(r2, key2);
        String[] att = new String[2];
        if(verify == false)
        {
            for(int i = 0 ; i < table1.length ; i++)
            {
                for(int j = 0 ; j < table2.length ; j++)
                {
                    int k = 0;
                    int l=0;
                    int m = 0;
                    for(int h = 0 ; h < colone ; h++)
                    {
                        if(table1[i][j1].equals(table2[j][j2]))
                        {
                            if(h < table1[0].length)
                            {
                                GranTable[i][h] = table1[i][k];   
                                k++;     
                            }
                            else
                            {
                                GranTable[i][h] = table2[j][l];
                                l++;
                            }
                        }
                        else{
                            if(m < table1[0].length)
                            {
                                GranTable[i][h] = table1[i][m];
                                m++;
                            }
                        }
                    }
                }
            }
            Join = new Table(Nom, GranTable,att);
        }
        else{throw new Exception("duplicate key");}

        return Join;
    }

//Cartesienne amin'ny alalan'ny tableau
    public Table ProduitCartesienneDiv(Object[][] table1,Object[][] table2) throws Exception
    {
        String Nom = "Cartesienne";
        String[] NomCol = new String[2];
            NomCol[0] = "table1";
            NomCol[1] = "table2";
        int ligne = table1.length * table2.length;
        int colonne = table1[0].length + table2[0].length;
        Object[][] TableauFinal = new Object[ligne][colonne];
        int i=0;
        Table GranT = new Table();
        for(int j = 0 ; j < table1.length ; j++)
        {
            for(int m = 0 ; m <table2.length ; m++)
            {
                int k = 0 ;
                for(int l = 0 ; l < table1[0].length ; l++)
                {
                    TableauFinal[i][k] = table1[j][l];
                    k++;
                }
                for(int l = 0 ; l < table2[0].length ; l++)
                {
                    TableauFinal[i][k] = table2[m][l];
                    k++;
                }
                i++;
            }
        }
        GranT = new Table(Nom,TableauFinal,NomCol);
        return GranT;
    }



//Division
    public Table Division(String r1,String r2,String Col1, String Col2,String r3) throws Exception
    {
        Table t1 = this.Recuperation(r1);
        Table t2 = this.Recuperation(r2);
        Object[][] table1 = t1.getTableau();
        Object[][] table2 = t2.getTableau();
        String[] att1 = t1.getNomCol();
        String[] att2 = t2.getNomCol();
        Object[][] Project1 = this.Projection(r1,Col1).getTableau();
        Object[][] Project2 = this.Projection(r2,Col2).getTableau();
        Table Cartesienne = this.ProduitCartesienneDiv(Project1,Project2);
        Cartesienne.setNom("Division");
        this.Fichier(Cartesienne);
        Table div = this.Difference(Cartesienne.getNom(),r3);
   
        return div;
    }

//------------------------------------------------Creer table---------------------------------------------------------------    
    public void Create(String Nom,String att) throws Exception
    {
        File casa=new File(Nom+".txt");
        String[] ValAtt = att.split(",");
        if(casa.exists() == false)
        {
            try(BufferedWriter ecrivain=new BufferedWriter(new FileWriter(casa,true)))
            {
                ecrivain.write(Nom);
                ecrivain.write("\n");
                
                for(int i = 0 ; i < ValAtt.length ; i++)
                {
                    ecrivain.write((String)ValAtt[i]);
                    if(i != ValAtt.length-1)
                    {
                        ecrivain.write("--");
                    }
                }
                ecrivain.write("\n");
            }
        }else{throw new Exception("Cette table existe deja"); }
    }

//--------------------------------------------------Completer Donner-------------------------------------------------------
    public void Insert(String Nom,String Val) throws Exception
    {
        File casa=new File(Nom+".txt");
        String[] Valeur = Val.split(",");
        Table t = this.Recuperation(Nom);
        
        if(casa.exists() && Valeur.length == t.getNomCol().length)
        {
            System.out.println(t.getNomCol().length+"------"+Valeur.length);
            try(BufferedWriter ecrivain=new BufferedWriter(new FileWriter(casa,true)))
            {   
                for(int i = 0 ; i < Valeur.length ; i++)
                {
                    ecrivain.write((String)Valeur[i]);
                    if(i != Valeur.length-1)
                    {
                        ecrivain.write(",");
                    }
                }
                ecrivain.write("\n");
            }
        }
        else{
            throw new Exception("Nombre de valeur trop grand");
        }
    }

//-----------------------------------------Requette-------------------------------------------------------------------------
    public Table Requette(String req) throws Exception
    {
        String[] reqUnite = req.split(" ");
        Table GrandTable = new Table();
//select
        if(reqUnite[0].equals("Select") && reqUnite[1].equals("*") && reqUnite[2].equals("from") && reqUnite.length==4)
        {
            GrandTable = this.Select(reqUnite[3]);
        }
        else if(reqUnite[0].equals("Select") &&  reqUnite[1].equals("*") && reqUnite[2].equals("from") && reqUnite[4].equals("where") && reqUnite[6].equals("="))
        {
            GrandTable = this.Condition(reqUnite[3],reqUnite[5],reqUnite[7]);
        }
         else if(reqUnite[0].equals("Select") &&  reqUnite[1].equals("*") && reqUnite[2].equals("from") &&  reqUnite[4].equals("join") &&  reqUnite[6].equals("on") &&  reqUnite[8].equals("="))
        {
            GrandTable = this.Jointure(reqUnite[3],reqUnite[5],reqUnite[7],reqUnite[9]);
        }
        // else if(reqUnite[2].equals("dans"))
        // {
        //     GrandTable = this.Projection(reqUnite[3],reqUnite[1]);
        // }
//Union
        else if(reqUnite[0].equals("Union") && reqUnite[1].equals("of") && reqUnite[3].equals("and"))
        {
            GrandTable = this.Union(reqUnite[2],reqUnite[4]);
        }
//Produit cartesienne
        else if(reqUnite[0].equals("Produit") && reqUnite[1].equals("of") && reqUnite[3].equals("and"))
        {
            GrandTable = this.ProduitCartesienne(reqUnite[2],reqUnite[4]);
        }
//Difference
        else if(reqUnite[0].equals("Difference") && reqUnite[1].equals("of") && reqUnite[3].equals("and") )
        {
            GrandTable = this.Difference(reqUnite[2],reqUnite[4]);
        }
        else if(reqUnite[0].equals("Division") && reqUnite[2].equals("par") && reqUnite[5].equals("par"))
        {
            GrandTable = this.Division(reqUnite[3],reqUnite[4],reqUnite[6],reqUnite[7],reqUnite[1]);
        }
        else if(reqUnite[0].equals("Create") && reqUnite.length == 5)
        {
            this.Create(reqUnite[1],reqUnite[3]);
        }
        else if(reqUnite[0].equals("Insert") && reqUnite.length == 5)
        {
            this.Insert(reqUnite[1],reqUnite[3]);
        }
        else{
            throw  new RequetteException("Erreur") ;
        }
        return GrandTable;
    }
}
