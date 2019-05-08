package basicChat;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Chat_Server_Return implements Runnable{

	
	 Socket sock;
	 private Scanner input;
	 private PrintWriter out;
	 String message = "";
//------------------------------------------------------------------------------------------------	 
	 //constructor
	 public Chat_Server_Return(Socket x){
		 this.sock = x;
	 }

//------------------------------------------------------------------------------------------------	 
	//if disconnected, remove from list and advise it (actually doesn't work)
	public void CheckConnection() throws IOException{
		 System.out.println("hey outside");		 
		 if(!sock.isConnected()){
			 System.out.println("hey inside");	
			 //remove socket from ConnectionArray list
			 for(int i=0; i<Chat_Server.ConnectionArray.size(); i++){
				 if(Chat_Server.ConnectionArray.get(i) == sock){
					 Chat_Server.ConnectionArray.remove(i);
				 }
			 }
			 //to advise user and server from status
			 for(int i=0; i<Chat_Server.ConnectionArray.size(); i++){
				Socket tempSock = (Socket) Chat_Server.ConnectionArray.get(i);
				PrintWriter tempOut = new PrintWriter(tempSock.getOutputStream());
				tempOut.println(tempSock.getLocalAddress().getHostName() + " disconnected!"); //to users
				tempOut.flush();
				System.out.println(tempSock.getLocalAddress().getHostName() + "disconnected!"); //to server
			 }
		 }
		 
	 }
//------------------------------------------------------------------------------------------------
	//keep port running and looking for new messages
	public void run(){
		try{
			try{
				input = new Scanner(sock.getInputStream());
				out = new PrintWriter(sock.getOutputStream());
				
				while(true){
					//to check if some user got offline
					CheckConnection(); 

					//server becomes every message and print it 
					if(!input.hasNext()){
						return;
					}
					message = input.nextLine();					
					System.out.println("Client said: " + message);

					//send arrived message to chat
					for(int i=0; i<Chat_Server.ConnectionArray.size(); i++){
						//Note: If necessary take CAST below out. I added it to make it compile
						Socket tempSock = (Socket) Chat_Server.ConnectionArray.get(i);
						PrintWriter tempOut = new PrintWriter(tempSock.getOutputStream());
						tempOut.println(message);
						tempOut.flush();
						System.out.println("Sent to: " + tempSock.getLocalAddress().getHostName());
					}
				}			
			}
			finally{
				sock.close();
			}			
		}
		catch(Exception x){
			System.out.println(x);
		}
	}
//------------------------------------------------------------------------------------------------	 
}
