package server;

import java.io.*;
import java.net.*;
import java.util.*;




class server
{
	
	static Vector<cThread> clients;

	static Socket clientSocket;
	
	public static void main(String args[])
	{
		
		clients = new Vector<cThread>();
		
		clientSocket = null;
		
		//server socket
		ServerSocket serverSocket = null;
		
		try
		{
			//sERVER PORT :9999
			serverSocket = new ServerSocket(9999);
		}
		catch(IOException e)
		{
			System.out.println("IO "+e);
		}
		
		
		while (true)
		{
			try
			{
				
				clientSocket = serverSocket.accept();
				
				cThread s = new cThread(clientSocket);
				
				clients.add(s);
				s.start();
			}
			catch (IOException e)
			{
				System.out.println("IOaccept "+e);
				
			}
		}	
	}
}