import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;

class SuperGui{

	Gui gui;

	Client client;

	public SuperGui(InetAddress chatURL, int chatPort, String appName){

		this.client = new Client(chatURL, chatPort);
		this.gui = new Gui(appName);

		this.client.addObserver(this.gui);
	}
   	
   	
	public void update(Observable c, String linia){
        System.out.print(linia);
    }
}