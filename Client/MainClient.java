package main;
import client.*;
public class MainClient{
    public static void main(String[] args)
    {
        try{
            Client appel = new Client();
            appel.client();
            //  Selectionner * dans t1 et t2 join Firstname = Firstname
            // Division t3 par t1 t2 par Firstname Firstname
        } catch(Exception e){System.out.println(e);}
        
    }
}