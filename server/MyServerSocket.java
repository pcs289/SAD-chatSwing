import java.lang.*;
import java.io.*;
import java.net.*;

class MyServerSocket{

	ServerSocket s;
	
	//Socket I/O
	PrintWriter out;
	BufferedReader in;

	public MyServerSocket(int port){
		try{
			this.s = new ServerSocket(port);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public MySocket accept(){
		MySocket sock = null;
		try{
			Socket sc = this.s.accept();
			this.out = new PrintWriter(sc.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			sock = new MySocket(sc);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sock;
	}

}