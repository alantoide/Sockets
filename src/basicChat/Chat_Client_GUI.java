package basicChat;

import javax.swing.*;
import java.io.PrintWriter;
import java.net.*;

public class Chat_Client_GUI {

	private static Chat_Client chatClient;
	public static String userName = "Anonymous";
	
	//globals for main window
	public static JFrame mainWindow = new JFrame();
	private static JButton b_about = new JButton();
	private static JButton b_connect = new JButton();
	private static JButton b_disconnect = new JButton();
	private static JButton b_help = new JButton();
	private static JButton b_send = new JButton();
	private static JLabel l_message = new JLabel("Message: ");
	public static JTextField tf_message = new JTextField(20);
	private static JLabel l_conversation = new JLabel();
	public static JTextArea ta_conversation = new JTextArea();
	private static JScrollPane sp_conversation = new JScrollPane();
	private static JLabel l_online = new JLabel();
	public static JList jl_online = new JList();
	private static JScrollPane sp_online = new JScrollPane();
	private static JLabel l_loggedInAs = new JLabel();
	private static JLabel l_loggedInAsBox = new JLabel();
	
	//globals for login window
	public static JFrame logInWindow = new JFrame();
	public static JTextField tf_userNameBox = new JTextField(20);
	private static JButton b_enter = new JButton("ENTER");
	private static JLabel l_enterUserName = new JLabel("Enter username: ");
	private static JPanel p_login = new JPanel();
	
//----------------------------------------------------------------------
	//main method
	public static void main(String args[]){
		BuildMainWindow();
		Initialize();
	}
	
//----------------------------------------------------------------------
	//build a new chat_client instance and try to connect 
	public static void Connect(){
		try{
			final int port = 2024; //same port as from server
			Socket sock = new Socket (InetAddress.getByName("2003:86:2d2a:1200:7cf5:c4cb:bf9:fbbe"), port); //Server IP in IPv6 format
			System.out.println("You're connected");
			
			chatClient = new Chat_Client(sock);
			
			//send name to add to online list
			PrintWriter out = new PrintWriter(sock.getOutputStream());
			out.println(userName);
			out.flush();
			
			Thread x = new Thread(chatClient);
			x.start();
		}
		catch(Exception x){
			System.out.println(x);
			JOptionPane.showMessageDialog(null, "Server not responding");
			System.exit(0);
		}
	}

//----------------------------------------------------------------------	
	//method to enable/disable buttons at program start
	public static void Initialize(){
		b_send.setEnabled(false);
		b_disconnect.setEnabled(false);
		b_connect.setEnabled(true);
	}

//----------------------------------------------------------------------	
	//method to build a log in window (it calls action listener method)
	public static void BuildLogInWindow(){
		
		logInWindow.setTitle("What's your name?");
		logInWindow.setSize(400,100);
		logInWindow.setLocation(250,200);
		logInWindow.setResizable(false);
		p_login = new JPanel();
		p_login.add(l_enterUserName);
		p_login.add(tf_userNameBox);
		p_login.add(b_enter);
		logInWindow.add(p_login);
		Login_Action();
		logInWindow.setVisible(true);
	}
//----------------------------------------------------------------------	
	//method to build a main window (it calls configure and action listener methods)
	public static void BuildMainWindow(){
		
		mainWindow.setTitle(userName + "'s chat box");		
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setSize(450,500);
		mainWindow.setLocation(220, 180);
		mainWindow.setResizable(false);
		ConfigureMainWindow();
		Main_Window_Action();
		mainWindow.setVisible(true);
	}


//----------------------------------------------------------------------	
	//method to configure main window elements
	public static void ConfigureMainWindow(){
		
		mainWindow.setBackground(new java.awt.Color(255,255,255));
		mainWindow.setSize(500, 320);
		mainWindow.getContentPane().setLayout(null);
		
		b_send.setText("SEND");
		mainWindow.getContentPane().add(b_send);
		b_send.setBounds(250,40,81,25);
		
		b_disconnect.setText("DISCONNECT");
		mainWindow.getContentPane().add(b_disconnect);
		b_disconnect.setBounds(10,40,110,25);
		
		b_connect.setText("CONNECT");
		b_connect.setToolTipText("");
		mainWindow.getContentPane().add(b_connect);
		b_connect.setBounds(130,40,110,25);
		
		b_help.setText("HELP");
		mainWindow.getContentPane().add(b_help);
		b_help.setBounds(420,40,70,25);
		
		b_about.setText("ABOUT");
		mainWindow.getContentPane().add(b_about);
		b_about.setBounds(340,40,75,25);
		
		l_message.setText("Message:");
		mainWindow.getContentPane().add(l_message);
		l_message.setBounds(10,10,60,20);
		
		tf_message.requestFocus();
		mainWindow.getContentPane().add(tf_message);
		tf_message.setBounds(70,4,260,30);
		
		l_conversation.setHorizontalAlignment(SwingConstants.CENTER);
		l_conversation.setText("Conversation");
		mainWindow.getContentPane().add(l_conversation);
		l_conversation.setBounds(100,70,140,16);
		
		ta_conversation.setColumns(20);
		ta_conversation.setFont(new java.awt.Font("Tahoma", 0, 12));
		ta_conversation.setForeground(new java.awt.Color(0,0,255));
		ta_conversation.setLineWrap(true);
		ta_conversation.setRows(5);
		ta_conversation.setEditable(false);
		
		sp_conversation.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp_conversation.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp_conversation.setViewportView(ta_conversation);
		mainWindow.getContentPane().add(sp_conversation);
		sp_conversation.setBounds(10,90,330,180);
		
		l_online.setHorizontalAlignment(SwingConstants.CENTER);
		l_online.setToolTipText("");
		l_online.setText("Currently online");
		mainWindow.getContentPane().add(l_online);
		l_online.setBounds(350,70,130,16);
		
		jl_online.setForeground(new java.awt.Color(0,0,255));
		
		sp_online.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp_online.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp_online.setViewportView(jl_online);
		mainWindow.getContentPane().add(sp_online);
		sp_online.setBounds(350,90,130,180);
		
		l_loggedInAs.setHorizontalAlignment(SwingConstants.CENTER);
		l_loggedInAs.setText("Currently logged in as");
		mainWindow.getContentPane().add(l_loggedInAs);
		l_loggedInAs.setBounds(340,0,140,15);
		
		l_loggedInAsBox.setHorizontalAlignment(SwingConstants.CENTER);
		l_loggedInAsBox.setFont(new java.awt.Font("Tahoma", 0, 12));
		l_loggedInAsBox.setForeground(new java.awt.Color(0,0,255));
		l_loggedInAsBox.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0,0,0)));
		mainWindow.getContentPane().add(l_loggedInAsBox);
		l_loggedInAsBox.setBounds(340,17,150,20);
		
	}
	
//---------------------------------------------------------------------------------------------
	//action listener for login window button
	public static void Login_Action(){
		
		b_enter.addActionListener(
				new java.awt.event.ActionListener(){
					public void actionPerformed(java.awt.event.ActionEvent evt){
						Action_b_enter();
					}
				}
		);
	}
	
//------------------------------------------------------------------------------------------
	//method to finish login and change settings 
	public static void Action_b_enter(){
		
		if(!tf_userNameBox.getText().equals("")){
			userName = tf_userNameBox.getText().trim();
			l_loggedInAsBox.setText(userName);
			Chat_Server.CurrentUsers.add(userName);
			mainWindow.setTitle(userName + "'s chat box");
			logInWindow.setVisible(false);
			b_send.setEnabled(true);
			b_disconnect.setEnabled(true);
			b_connect.setEnabled(false);
			Connect();
		}
		else{
			JOptionPane.showMessageDialog(null, "Please enter a name!");
		}
	}

	
	
//-----------------------------------------------------------------------------------------
	//action listener for main window buttons
	public static void Main_Window_Action(){
		
		b_send.addActionListener(
			new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt){
					Action_b_send();
				}
			}
		);
		
		b_disconnect.addActionListener(
			new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt){
					Action_b_disconnect();
				}
			}
		);
		
		b_connect.addActionListener(
			new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt){
					BuildLogInWindow();
				}
			}
		);
		
		b_help.addActionListener(
			new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt){
					Action_b_help();
				}
			}
		);
		
		b_about.addActionListener(
			new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt){
					Action_b_about();
				}
			}
		);		
	}

//-----------------------------------------------------------------------------------------
	//method for "send" button (to send written message)
	public static void Action_b_send(){
		
		if(!tf_message.getText().equals("")){
			chatClient.Send(tf_message.getText());
			tf_message.requestFocus();
		}
	}
	
//-----------------------------------------------------------------------------------------
	//method for "disconnect" button
	public static void Action_b_disconnect(){
		
		try{
			chatClient.Disconnect();
		}
		catch(Exception y){
			y.printStackTrace();
		}
	}
	
//-----------------------------------------------------------------------------------------
	//method for "help" button
	public static void Action_b_help(){
		
		JOptionPane.showMessageDialog(null, "You don't need help!");
	}

//-----------------------------------------------------------------------------------------
	//method for "about" button
	public static void Action_b_about(){
			
		JOptionPane.showMessageDialog(null, "Multi-client chat program\nVersion 1.1 - 2017\nStefan Brunner");
	}	

}

