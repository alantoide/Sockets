package basicServerClient;

import java.io.*;
import java.net.*;

public class BasicClient {

	//***************************************************
	//**Please run first the BasicServer main method!!!**
	//***************************************************
	
	public static void main(String[] args) throws Exception {
		BasicClient client = new BasicClient();
		client.run();
	}

	public void run() throws Exception{
		
		//connects with "localhost" (can be also an IP) and port 444 already created in server
		Socket sock = new Socket	("localhost", 1024);
		
		//send a message
		PrintStream ps = new PrintStream(sock.getOutputStream());
		ps.println("Hello from Client");
		
		//get a message
		InputStreamReader ir = new InputStreamReader(sock.getInputStream());
		BufferedReader br = new BufferedReader(ir);
		String message = br.readLine();
		System.out.println(message);		
	};
}
