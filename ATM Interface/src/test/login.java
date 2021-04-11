package test;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class login extends JFrame implements ActionListener
{
	JTextField txtuser=new JTextField(25);
	JPasswordField txtpass=new JPasswordField(25);
	JLabel lbluser=new JLabel("Username: ");
	JLabel lblpass=new JLabel("Pin Number: ");
	JButton btnOk=new JButton("Login");
	JButton btnRegister=new JButton("Register");

	Connection conn;
	//ResultSet rs;
	Statement st;
	PreparedStatement ps;
	
	public login()
	{
		JPanel pane=new JPanel();
		pane.setLayout(null);
		
		//Adding Components to Frame
		pane.add(txtuser);
		pane.add(txtpass);
		pane.add(lbluser);
		pane.add(lblpass);	
		pane.add(btnOk);
		pane.add(btnRegister);
		//pane.add(btnBlock);
		
		//Setting location of components
		lbluser.setBounds(10,20,80,25);
		lblpass.setBounds(10,50,80,25);
		txtuser.setBounds(100,20,165,25);
		txtpass.setBounds(100,50,165,25);
		btnOk.setBounds(50,90,85,25);
		btnRegister.setBounds(145,90,85,25);
		//btnBlock.setBounds(55,90,150,20);
		
		//Adding actionlistener to buttons
		btnOk.addActionListener(this);
		btnRegister.addActionListener(this);

		setContentPane(pane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//connection
				
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RISHITA","honey");
		}
		catch(ClassNotFoundException e)  
		{
 			System.err.println("Failed to load driver");
 			e.printStackTrace();
 	    }
		catch(SQLException e)
		{
 			System.err.println("Unable to connect");
 			e.printStackTrace();
 		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		Object source=e.getSource();
		if(source==btnOk)
		{
			try
			{
				String str1=txtuser.getText();
				String str2=txtpass.getText();
				if((str1.length()==0 || str2.length()==0))
				{
    				JOptionPane.showMessageDialog(null,"Connot be Process","WARNING",JOptionPane.WARNING_MESSAGE);
    			}
    			else
    			{
					st=conn.createStatement();
					String strUser="";
					String strPass="";
					
					ResultSet rs=st.executeQuery("SELECT * FROM atm WHERE UserName='"+str1+"'");
					while(rs.next())
					{
						strUser=rs.getString(1);
						strPass=rs.getString(4);
					}
				
		            st.close();
		
				    if(strUser.equals(str1))
				    {
				    	if(strPass.equals(str2))
				    	{
							JOptionPane.showMessageDialog(null,"Welcome "+txtuser.getText(),"Welcome",JOptionPane.INFORMATION_MESSAGE);
							
							mainmenu panel = new mainmenu();
							panel.setSize(330,300);
							panel.setVisible(true);
							panel.setResizable(false);
							panel.setLocation(400,250);
							dispose();
						}
				    	else
				    	{
						JOptionPane.showMessageDialog(null,"Username found but the password is incorrect!","Security Pass",JOptionPane.WARNING_MESSAGE);
						txtpass.requestFocus(true);
					    }
				     }
				     else
				     {
				    	 JOptionPane.showMessageDialog(null,"Username is incorrect!","Security Pass",JOptionPane.WARNING_MESSAGE);
				    	 txtuser.requestFocus(true);
				     }
    			}	
			}
			catch(SQLException w)
			{}
		}
		else if(source==btnRegister)
		{
			register panel = new register();
			panel.setSize(370,400);
			panel.setVisible(true);
			panel.setResizable(false);
			panel.setLocation(400,250);
			dispose();
		}
	}
	
	public static void main(String[]args)
	{
		login log=new login();
	    log.setLocation(400,250);
		log.setSize(300,200);
		log.setTitle("Log-In");
		log.setResizable(false);
		log.setVisible(true);
	}
}
