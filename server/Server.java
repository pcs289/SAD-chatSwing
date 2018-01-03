import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server{

    ConcurrentHashMap<String, MySocket> llista;

	public Server(int port){
        try {
            this.llista = new ConcurrentHashMap<String, MySocket>();
            MyServerSocket mss = new MyServerSocket(port);
            while (true) {
                MySocket ms = mss.accept();
                String actualNick = this.requestName(ms); 
                this.llista.put(actualNick, ms);
                System.out.println("Client with nickname: "+ actualNick +" connection started");
                System.out.println(this.llista);
                advertList(actualNick);
                new Thread(new Service(ms, actualNick, this)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void advertList(String actualNick){
        for(String nick: this.llista.keySet()){
            if(!nick.equals(actualNick)){
                System.out.println("Adverting nickname: " + actualNick);
                broadcastMessage("USR: ADD " + actualNick);
            }
        }
    }

    public void broadcastMessage(String text){
        Set<String> keySet = this.llista.keySet();
        for (String nick : keySet){

            PrintWriter outbroadcast = new PrintWriter(this.llista.get(nick).getOutputStream(), true);
            outbroadcast.println(text);
        }
    }

    public String requestName(MySocket s){
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        Boolean check = true;
        String acceptedNick = null;
        Set<String> keys = this.llista.keySet();
        
        do{
            try{
                acceptedNick = in.readLine();
            }catch(Exception e){
                e.printStackTrace();
            }
            for(String name : keys){
                if(name.equals(acceptedNick)){
                    out.println("0");
                    check = false;
                }else{check = true;}
            }
        }while(!check);     
        out.println("1");
        return acceptedNick;
    }
    
}

