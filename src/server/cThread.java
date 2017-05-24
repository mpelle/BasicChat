package server;
import java.net.*;
import java.io.*;
import java.util.*;



public class cThread extends Thread
{
	
	String nicname;      
	Boolean connected;
	Socket socket;
	PrintWriter out; //I/O
	BufferedReader in;
	Socket clientSocket;

	cThread(Socket s)
	{
		super("cThread");
		
		connected = false;
		
		nicname = "";
		
		
		//constructor
		clientSocket = s;
		try 
		{
			//I/O out and in streams from client socket
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	public boolean equals(cThread c)
	{
		return (c.nicname.equals(this.nicname));
	}
	
	
	synchronized void send(String message)
	{
			out.println(message);	
	}
	

	void listen()//Method to listen for the beginning of the message sent by the client
	{
		try 
		{
			//read message
			//print message
while (true) //while their is a message check if the message starts with login or logout
			{	   			
	       	    String msg = in.readLine(); //read the line of the message
	       		System.out.println(msg);	//print the message
	        	//if message contains login login
	   if (msg.startsWith("Login")) //if string msg starts with login then login the msg
	        	{
					login(msg);
	        	}
	        	//logout
	     else if (msg.equals("Logout"))
	        	{
	        		//logout
	        		//remove from server.clients
	        		
	        		if (connected)       //If the client is not connected then print ok and close the socket connection
	        		{	
	        			connected = false;
	        			server.clients.indexOf(this);
	        			server.clients.remove(this);
						sendList();
	        			out.println("OK");
	        			out.close();
	        			in.close();
	        			clientSocket.close(); //End socket 
	        			return;
	        		}
	        		//Not logged in
	        		else
	        		{
	        			send("Not Logged in !!");
	        		}
	        		
	        	}
	        	//If message starts with post 
	        	
	     else if (msg.startsWith("Post "))
	        	{
	        		//post message to chat
	        		for (int i = 0; i < server.clients.size() ; i ++) //loop to check for clients
	        		{
	        			cThread t = (cThread)server.clients.get(i); //get the number of clients
	        			if (t.connected) 
	        			{
	        				t.send("Recieve "+ nicname+": " +msg.substring(5, msg.length()));
	        			}
	        		}
	        	}
	        	
	        	//Private post
	        	else if (msg.startsWith("PrivatePost ")) //If the message is a private message to a user it will start with the words PrivatePost
	        	{
	        		//post message to specific user
	        		StringTokenizer st = new StringTokenizer(msg.substring(12,msg.length()),", ");   	
	        		String message = st.nextToken();	        		
	        		String to = st.nextToken();
	        		
	        		//Send message to user
					boolean success = false;
	        		
	        		
	      for (int i = 0; i < server.clients.size() ; i ++) 
	        		{
	        			cThread cli = (cThread)server.clients.get(i);
	        			if (cli.nicname.equals(to))
	        			{
	        				cli.send("PrivateRecieve "+ nicname+": " + message);
	        				success = true;
	        				break;
	        			}
	        		}
	        			 
	        	
	        		if (!success)
	        		{
	        			send("Error:could not send to nick");
	        		}       		
	        	}
	        
	        
	        	else
	        	{
	        		send(msg);
	        	}
	   		}
		}
		catch (SocketException e)
		{
			
				    if (connected)
	        		{
	        			try 
	        			{        			
	        				connected = false;
	        				server.clients.indexOf(this);
	        				server.clients.remove(this);
							sendList();
	       		 			out.close();
	       		 			in.close();
	        				clientSocket.close();
	        				return;
	       	 			}
	        			catch (Exception d)
	        			{
	        				return;
	        			}
	        		}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	

	public void run() 
	{
		listen();
	}
	
	boolean login(String msg)
	{
			
	    if (connected)
	    {
	    	out.println("You are All ready Connected!");
	    	return true;
	    }
	    
		boolean exists = false;
		System.out.println("Login" + msg.substring(5, msg.length()));
	    
	    for (int i = 0;i<server.clients.size();i++)
	    {
	    	if (server.clients.get(i) != null)
	        {
	        	
				System.out.println(msg.substring(7, msg.length()));
				cThread temp = (cThread)server.clients.get(i);
	        	if ((temp.nicname).equals(msg.substring(7, msg.length())))
	        	{
					exists = true;
	        		break;
	        	}

	        }
		}
		
		if (exists)
		{
			out.println("NewNick");
		}
		else
		{
			connected = true;		
			nicname = msg.substring(7,msg.length());
	        sendList();
		}
	    return true;
	}
	
	
	void sendList() //method for sending the client list
	{
		
		String list = "";
		System.out.println(server.clients.size());
		if (server.clients.size() == 0)
		{
			return;
		}
	
	    for (int i = 0;i<server.clients.size();i++)
	    {
	    	cThread temp = (cThread)server.clients.get(i); 
	    	if (server.clients.get(i) != null)
	        {
	        		if (connected)
	        		{
	        			list =temp.nicname + "," + list  ;
	        		}
	        }
		}
		
		list = "List " +list.substring(0,list.length() -1) +";";
	    //Send List to all 
	    
	    for (int i = 0; i < server.clients.size() ; i ++)
	    {
	    	cThread thrd = (cThread)server.clients.get(i);
	    	if (thrd.connected)
	    	{
	    		thrd.send(list);
	    	}
	    }
	}
	
	
	static String replace(String str, String pattern, String replace) 
	{
  	  	int z = 0;
  	  	int x = 0;
  	  	StringBuffer result = new StringBuffer();
    	while ((x = str.indexOf(pattern, z)) >= 0) 
    	{
    		result.append(str.substring(z, x));
       	    result.append(replace);
       	    z = x+pattern.length();
    	}
    	result.append(str.substring(z));
    	return result.toString();
    }
}