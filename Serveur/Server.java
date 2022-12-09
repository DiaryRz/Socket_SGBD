package server;
import java.io.*;
import java.net.*;
import java.util.*;
import fonction.*;
import relation.*;
import exception.*;

public class Server {
 
    public void Serveur() throws Exception {
    
        try
        {
             ServerSocket serveurSocket ;
             Socket clientSocket ;
             BufferedReader in;
             PrintWriter out;
             Scanner sc=new Scanner(System.in);
        
            serveurSocket = new ServerSocket(5000);
            clientSocket = serveurSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
            Fonction appelFonction = new Fonction();
            ObjectOutputStream obj = new ObjectOutputStream(clientSocket.getOutputStream());

            Table tab = new Table();
            while(true)
            {
                try
                {
                    String msg = in.readLine();
                    tab = appelFonction.Requette(msg);
                    obj.writeObject(tab);
                    obj.flush();
                } catch(Exception RequetteException){
                    System.out.println(RequetteException);
                    obj.writeObject(String.valueOf(RequetteException));
                    obj.flush();
                }
            
            }
        } catch (Exception e){System.out.println(e);}   
    }
}