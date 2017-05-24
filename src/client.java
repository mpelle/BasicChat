import java.io.*;
import java.net.*;
import javax.swing.*;   
import java.awt.*;   




class client extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static boolean connected; 
	static boolean logout; 
	static Socket cSocket; 
	static PrintWriter out; //
	static BufferedReader in;// 
	static userInput uinput; 
							 
	static readFromServer sinput; 
	static DefaultListModel<String> list; 

 	
 	
	void run()
	{
		
		setTitle("Java Chat - Disconnected");
		enter = false;
		connected = false;
		
		//Gui Stuff
		
		jScrollPane1 = new javax.swing.JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        inputText = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainText = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//not connected add to list
		list = new DefaultListModel<String>();
		list.addElement("Not Connected");
		
		
		nickList = new JList<String>(list);
        jMenuBar1 = new javax.swing.JMenuBar();
    
    	//menus 
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
		jMenuItem2 = new javax.swing.JMenuItem();
		jMenuItem3 = new javax.swing.JMenuItem();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        inputText.setColumns(20);
        inputText.setRows(5);
        jScrollPane1.setViewportView(inputText);
		

        sendButton.setText("Send");
        
        
        
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });
        

        inputText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputTextKeyReleased(evt);
            }
        });
        
    
        nickList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nickListMouseClicked(evt);
            }
        });
        

        inputText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputTextKeyReleased(evt);
            }
        });
        mainText.setColumns(20);
        mainText.setEditable(false);
        mainText.setRows(5);
        mainText.setLineWrap(true);
        inputText.setLineWrap(true);
        jScrollPane2.setViewportView(mainText);


		jScrollPane3.setViewportView(nickList);
	
        jMenu1.setText("Commands");
		jMenu2.setText("Help");
        jMenuItem1.setText("Connect");
        jMenuItem2.setText("Disconnect");
        jMenuItem3.setText("Usage");
		jMenuItem2.setEnabled(false);
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed3(evt);
            }
        });
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed2(evt);
            }
        });
        jMenu1.add(jMenuItem1);
		jMenu1.add(jMenuItem2);
		jMenu2.add(jMenuItem3);
		
		jMenuBar1.add(jMenu1);
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

		guiLayout();
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		mainText.setFont(new Font("Terminal", Font.PLAIN, 16));
	}
	
	void guiLayout()
	{
		        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sendButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        pack();
        
        
        setVisible(true);
	}
	


	private void nickListMouseClicked(java.awt.event.MouseEvent evt) //listen for mouse click on client name
	{

		if (connected && (!nickList.getSelectedValue().equals(nick)))
		{
		
			String msg =  JOptionPane.showInputDialog(null, "Send message to: ");
			if (msg != null)
			{
				
				send("PrivatePost " + msg + ", "+nickList.getSelectedValue());// if mouse clicked on name then private message box will pop up
			}
		
		 	System.out.println(nickList.getSelectedValue());
		}
	}
	
	static boolean enter; 
    private void inputTextKeyReleased(java.awt.event.KeyEvent evt) 
    {
    	
        if(evt.getKeyCode() == 10)
        {
           	if (enter)
    		{
    		
    			sendInput();
    			enter = false;
    		}
    		else
    		{
    			
    			enter = true;
    		}
        }
    }
    private void jMenuItem1ActionPerformed2(java.awt.event.ActionEvent evt) 
    {
    	
    	send("Logout");
		client.logout = true;
		System.exit(0);
    }
    

    private void jMenuItem1ActionPerformed3(java.awt.event.ActionEvent evt) 
    {
    	String s;
    	s = "Connect\nenter nickname\nSend messages to chat or to individual users\nDisconnect when done\n";
    	JOptionPane.showMessageDialog( this, s,"Usage", JOptionPane.INFORMATION_MESSAGE );
    }
    
    static String server;
	
	private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	
    	setTitle("Connecting ...");
    	logout = false;
		
		
		sinput = new readFromServer(this);
		
		uinput = new userInput();
		
		
		cSocket = null; //set everything to null while connecting
		out = null;
		in = null;
	
		boolean error;
		error = false; //if their is no error then continue
		
		//Default is localhost
		server = JOptionPane.showInputDialog("Enter server address: ", "localhost");
		try 
		{
			
			cSocket = new Socket(server,9999); //set up client socket that looks for server on port 9999
			//Add input/output streams to the socket
			out = new PrintWriter(cSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
			//Send the streams to the server
			uinput.start(); 
			sinput.start();
		}
		catch(UnknownHostException e)
		{
			//Cannot connect to server error
			JOptionPane.showMessageDialog( this, "cannot connect to server","ERROR", JOptionPane.ERROR_MESSAGE );
			System.out.println("Host Error" + e);
			setTitle("Java Chat - Cannot Connect Please Try another server");
			error = true;
		}
		catch (IOException e)
		{
			System.out.println("IOException" + e);
		}
		if (!error)
		{
		
       	 	nick = null;
       	 	nick = JOptionPane.showInputDialog(null, "NickName: ");
       	 	
       	 	
       	 	//Nicknames cannot contain ";"
       	 	while(nick.contains(";"))
       	 	{
       	 		nick = JOptionPane.showInputDialog(null, "nickname cannot contain \";\". try again");
       	 	}
       	 	
       	 	
       	 
       	 	send("Login: "+nick);
       	 	
       	 	//Gui menu
       	 	if (nick != null)
       	 	{
       	 		jMenuItem1.setEnabled(false);
       	 		jMenuItem2.setEnabled(true);       	 		
       	 	}			
		}
    }


    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) 
    {
		sendInput();	    
    }
	
	
	static void send(String msg)
	{
		out.println(msg);
	}
	
	static String read()
	{
		
		String s = null;
		try 
		{
			
			s = in.readLine();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		
		return s;
	}
	
	
	
	static String replace(String str, String pattern, String replace) 
	{
  	  	int s = 0;
  	  	int e = 0;
  	  	StringBuffer result = new StringBuffer();
    	while ((e = str.indexOf(pattern, s)) >= 0) 
    	{
    		result.append(str.substring(s, e));
       	    result.append(replace);
       	    s = e+pattern.length();
    	}
    	result.append(str.substring(s));
    	return result.toString();
    }
   
    void sendInput()
    {
    
    	if (!connected)
    	{
    		JOptionPane.showMessageDialog( this, "Not connected! Actions - Connect","Error", JOptionPane.ERROR_MESSAGE );
    		inputText.setText("");
    	}
    	
    	else if(inputText.getText().equals("") || inputText.getText().equals("\n") ||  inputText.getText()== null  )
    	{
    		inputText.setText("");
    	}
    	else
    	{
    	
    		  send("Post " + replace(inputText.getText(),"\n"," "));
    	      inputText.setText("");
    	}
    }
   
    public static String nick;
	    private javax.swing.JTextArea inputText;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    static public javax.swing.JMenuItem jMenuItem1;
    static public javax.swing.JMenuItem jMenuItem2;
    static public javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTextArea mainText;
    private javax.swing.JList<String> nickList;
    private javax.swing.JButton sendButton;

}