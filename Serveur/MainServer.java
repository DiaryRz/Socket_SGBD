package main;
import server.*;
import fonction.*;

public class MainServer{
    public static void main(String[] args)
    {
        try{
            Server appel = new Server();
            appel.Serveur();

            Fonction appelFonction = new Fonction();
            appelFonction.Insert("t1","tt,nn");
        }catch(Exception e){System.out.println(e);}
        
    }
}