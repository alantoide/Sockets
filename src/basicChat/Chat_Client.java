package basicChat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Chat_Client implements Runnable{

	Socket sock;
	Scanner input;
	Scanner send = new Scanner(System.in);
	PrintWriter out;

//-----------------------------------------------------------------------------------------
	//constructor
	public Chat_Client(Socket x){
		this.sock = x;                                             
	}

//-----------------------------------------------------------------------------------------

	public void run(){
		try{
			try{
				input = new Scanner(sock.getInputStream());
				out = new PrintWriter(sock.getOutputStream());
				out.flush();
				CheckStream();
			}
			finally{
				sock.close();
			}			
		}
		catch(Exception x){
			System.out.println(x);
		}		
	}

//-----------------------------------------------------------------------------------------

	public void Disconnect() throws IOException{
		
		out.println(Chat_Client_GUI.userName + " has disconnected.");
		out.flush();
		//i have to call method "is connected" from "chat server return" before closing socket
		sock.close();
		JOptionPane.showMessageDialog(null, "You disconnected!");
		System.exit(0);
	}
	
//-----------------------------------------------------------------------------------------

	public void CheckStream(){
		
		while(true){
			Receive();
		}
	}
	
//-----------------------------------------------------------------------------------------

	public void Receive(){
		
		if(input.hasNext()){
			String message = input.nextLine();
			
			if(message.contains("##??!!")){
				String temp1 = message.substring(6);
				temp1 = temp1.replace("[","");
				temp1 = temp1.replace("]","");
				
				String[] currentUsers = temp1.split(", ");
				
				Chat_Client_GUI.jl_online.setListData(currentUsers);
			}
			else{
				Chat_Client_GUI.ta_conversation.append(message + "\n");
			}
		}
	}
	
//-----------------------------------------------------------------------------------------
	
	public void Send(String x){
		
		out.println(Chat_Client_GUI.userName + ": " + x);
		out.flush();
		Chat_Client_GUI.tf_message.setText("");
	}

//-----------------------------------------------------------------------------------------	
}
                       