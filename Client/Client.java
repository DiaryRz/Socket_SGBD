package client;
import java.io.*;
import java.net.*;
import java.util.*;
import relation.*;
import exception.*;
/*
 * www.codeurjava.com
 */
public class Client {

    public void client() throws Exception {
    
    final Socket clientSocket;
    final BufferedReader in;
    final PrintWriter wr;
    final Scanner sc = new Scanner(System.in);
    
    
        try {
        
            clientSocket = new Socket("localhost",5000);
            
            //flux pour envoyer
            wr = new PrintWriter(clientSocket.getOutputStream());
            //flux pour recevoir
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ObjectInputStream in2 = new ObjectInputStream(clientSocket.getInputStream());
            

            String msg;
            
                while(true){
                    try
                    {
                        msg = sc.nextLine();
                        wr.println(msg);
                        wr.flush();

                    
                        Object ta = in2.readObject();
        
                        if(ta.getClass().getSimpleName().equals("String") == false)
                        {
                            Table t = (Table)ta;

                            for(int j = 0 ; j < t.getNomCol().length ; j++)
                            {
                                System.out.print("|"+t.getNomCol()[j]+"\t"+"\t");
                            }
                            System.out.println();
                            for(int j = 0 ; j < t.getNomCol().length ; j++)
                            {
                                System.out.print("---------------");
                            }
                            System.out.println();

                            for(int i = 0 ; i < t.getTableau().length  ; i++)
                            {
                                for(int j= 0 ; j < t.getTableau()[0].length ; j++)
                                {
                                    System.out.print("|"+t.getTableau()[i][j]+"\t"+"\t");
                                }
                                System.out.print("\n");
                            }
                        }
                        else if(ta.getClass().getSimpleName().equals("String"))
                        {
                            System.out.println(String.valueOf(ta));
                        }
                       
                    }catch(Exception e ){System.out.println(e);}
                }
                
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

