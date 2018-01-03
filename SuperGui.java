import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

class SuperGui{


	public SuperGui(InetAddress chatURL, int chatPort, String appName){

		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        
		        new Gui(appName, chatURL, chatPort);

		    }
		});
		
	}
 
}