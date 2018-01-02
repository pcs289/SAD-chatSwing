import java.lang.*;
import java.util.*;
import java.io.*;
import java.net.*;

class Client extends Observable implements Runnable{
	
    private MySocket ms;
	private BufferedReader in;
    private PrintWriter out;

    Gui gui;
    
    public Client(InetAddress ip, int port){
        try{
            this.ms = new MySocket(ip, port);
            System.out.println("Connexio establerta");
            in = new BufferedReader(new InputStreamReader(this.ms.getInputStream()));
            out = new PrintWriter(this.ms.getOutputStream(), true);
            new Thread(this).start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            while(true){
                String read = in.readLine();
                if(read==null) break;

                setChanged();
                notifyObservers(read);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void writeToSocket(String linia){
    	// Input Thread
    	try{
                out.println(linia);

        }catch(Exception e){
            e.printStackTrace();
        }        
    }
}	