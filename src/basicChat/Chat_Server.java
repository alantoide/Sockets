package basicChat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Chat_Server {

	public static ArrayList<Socket> ConnectionArray = new ArrayList<Socket>();
	public static ArrayList<String> CurrentUsers = new ArrayList<String>();
	
	
	public static void main(String[] args) throws IOException{
		
		try{
			final int port = 2024; //use one port for each instance
			ServerSocket server = new ServerSocket(port);
			System.out.println("Waiting for clients...");
			
			//loop to keep server port running and looking for new users/messages
			while(true){
				Socket sock = server.accept();
				ConnectionArray.add(sock);
				
				System.out.println("Client connected from: " + sock.getLocalAddress().getHostName());
				
				addUserName(sock);
				
				Chat_Server_Return chat = new Chat_Server_Return(sock);
				Thread x = new Thread(chat);
				x.start();		
			}
		}
		catch(Exception x) {
			System.out.println(x);
		}
		
	}


	public static void addUserName(Socket x) throws IOException {
		Scanner input = new Scanner(x.getInputStream());
		String UserName = input.nextLine();
		CurrentUsers.add(UserName);
		
		for(int i=0; i<Chat_Server.ConnectionArray.size(); i++){
			Socket tempSock = (Socket) Chat_Server.ConnectionArray.get(i);
			PrintWriter out = new PrintWriter(tempSock.getOutputStream());
			out.println("##??!!" + CurrentUsers);
			out.flush();
		}
		
	}

}
