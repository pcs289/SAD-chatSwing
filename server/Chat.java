import java.lang.*;
import java.net.*;
import java.io.*;

class Chat{
	public static void main(String[] args){
		
		// Server: -s port

		if(args.length == 0 || args.length > 3){
			System.out.println("Usage:\r\nServer: java Chat -s Port\r\n");
		}else{
			String type = args[0];
			int port;
			InetAddress inetAddress;
			
			if(type.equals("-s") && args.length == 2){	//SERVER

				System.out.println("Server");
				port = Integer.parseInt(args[1]);
				System.out.println("Waiting for connection at port " + port);
				Server serv = new Server(port);
				
			}else{
				System.out.println("Usage:\r\nServer: java Chat -s Port\r\n");
			}
		}	
	}
}