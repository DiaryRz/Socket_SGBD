package main;
import server.*;
import fonction.*;
import relation.*;

public class MainServer{
    public static void main(String[] args)
    {
        try{
            Server appel = new Server();
            appel.Serveur();

        }catch(Exception e){System.out.println(e);}
        
    }
}