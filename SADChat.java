import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.*;

class SADChat{

    static InetAddress chatURL;
    static int chatPort = 12345;

    static String      appName     = "SAD Chat";

   

    public static void main(String[] args) {

          try{
                    chatURL = InetAddress.getByName("127.0.0.1");

                    new SuperGui(chatURL, chatPort, appName);

                    
                }catch(Exception e){}
    }

    
}