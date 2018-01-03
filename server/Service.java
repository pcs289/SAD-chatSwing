import java.lang.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class Service implements Runnable{
    MySocket ms;
    String currentNick;
    Server serv;

    public Service(MySocket s, String nick, Server serv) {
        this.ms = s;
        this.currentNick = nick;
        this.serv = serv;
    }

    public void broadcastMessage(String text){
        Set<String> keySet = this.serv.llista.keySet();
        for (String nick : keySet){

            PrintWriter outbroadcast = new PrintWriter(this.serv.llista.get(nick).getOutputStream(), true);
            outbroadcast.println(text);
        }
    }

    public void getList(){
        PrintWriter out = new PrintWriter(this.ms.getOutputStream());
        for(String name: this.serv.llista.keySet()){
            System.out.println("Getting list from " + this.currentNick + "'s thread, checking " + name + " from server");
            if(!name.equals(this.currentNick)){
                System.out.println("Retrieved name "+ name+ " from " + this.currentNick + "'s thread");
                out.print("USR: ADD " + name);
            }
        }
    }

    @Override
    public void run() {
        try {
            getList();
            BufferedReader in = new BufferedReader(new InputStreamReader(this.ms.getInputStream()));
            while (true) {
                String line = in.readLine();
                //User has disconnected
                if (line == null) {
                    this.serv.llista.remove(this.currentNick);
                    System.out.println("Client with nickname: "+ this.currentNick +" connection finished");
                    this.broadcastMessage("USR: DEL " + this.currentNick);
                    break;
                }
                this.broadcastMessage("MSG: " + this.currentNick + " " + line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}