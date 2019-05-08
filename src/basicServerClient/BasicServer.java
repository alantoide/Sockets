package basicServerClient;

import java.io.*;
import java.net.*;

public class BasicServer {

	public static void main(String[] args) throws Exception {
		//create a new instance
		BasicServer server = new BasicServer();
		server.run();
	}
	
	public void run() throws Exception{
		
		//create a new port
		ServerSocket srvSock = new ServerSocket (1024); //1024 is just a example port (under port 1024 reserved to standard services)
		Socket sock = srvSock.accept();
		
		//get a message
		InputStreamReader ir = new InputStreamReader(sock.getInputStream());
		BufferedReader br = new BufferedReader(ir);		
		String message = br.readLine();
		System.out.println(message);

		//send a confirmation message
		if(message != null){
			PrintStream ps = new PrintStream(sock.getOutputStream());
			ps.println("Message received!");
		}
	}

}
