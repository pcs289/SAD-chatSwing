import java.lang.*;
import java.io.*;
import java.net.*;

public class Client implements Runnable{
	
    private MySocket ms;
	private BufferedReader in, br_ky;
    private PrintWriter out;
    
    public Client(InetAddress ip, int port){
        try{
            this.ms = new MySocket(ip, port);
            System.out.println("Connexio establerta");
            //this.ms.connect();
            in = new BufferedReader(new InputStreamReader(this.ms.getInputStream()));
            out = new PrintWriter(this.ms.getOutputStream(),true);
            br_ky = new BufferedReader(new InputStreamReader(System.in));
            new Thread(this).start();
            this.writeToSocket();
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
                System.out.println(read);
            }

            this.ms.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void writeToSocket(){
    	// Input Thread
    	try{
            String linia;
            while ((linia = br_ky.readLine()) != null){
                out.println(linia);
            }   

            this.ms.close();

        }catch(Exception e){
            e.printStackTrace();
        }        
    }
}	