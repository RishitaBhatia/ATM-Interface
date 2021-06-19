package test;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class BalanceTransaction extends JFrame implements ActionListener
{
	JPasswordField txtuser=new JPasswordField(25);
	JTextField txtpass=new JTextField(25);
	JLabel lbluser=new JLabel("Pin Number: ");
	JLabel lblpass=new JLabel("Balance: ");
	JButton btnOk=new JButton("Back to Menu");
	JButton btnRegister=new JButton("Bal.Enquire");
	
	Connection conn;
	//ResultSet rs;
	Statement st;
	
	public BalanceTransaction()
	{
		JPanel pane=new JPanel();
		pane.setLayout(null);
		
		//Adding components to frame
		pane.add(txtuser);
		pane.add(txtpass);
		pane.add(lbluser);
		pane.add(lblpass);	
		pane.add(btnOk);
		pane.add(btnRegister);
		
		//Setting location components
		lbluser.setBounds(10,20,80,25);
		lblpass.setBounds(10,50,80,25);
		txtuser.setBounds(100,20,165,25);
		txtpass.setBounds(100,50,165,25);
		btnRegister.setBounds(25,90,100,25);
		btnOk.setBounds(135,90,125,25);
	
		//Adding actionlistener to buttons
		btnOk.addActionListener(this);
		btnRegister.addActionListener(this);
		
		txtpass.setEditable(false);
		btnOk.setToolTipText("Click To Back to Menu");

		setContentPane(pane);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		//connection
				
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","rishita","honey");
		}
		catch(ClassNotFoundException e)  
		{
 			System.err.println("Failed to load driver");
 			e.printStackTrace();
 	    }
		catch(SQLException e){
 			System.err.println("Unable to connect");
 			e.printStackTrace();
 	    }
	}
	
	public void actionPerformed(ActionEvent e)
	{
		Object source=e.getSource();
		if(source==btnOk)
		{
			mainmenu panel = new mainmenu();
			panel.setSize(330,300);
			panel.setVisible(true);
			panel.setResizable(false);
			panel.setLocation(400,250);
			dispose();				
		}
		else if(source==btnRegister)
		{
			try
			{
				st= conn.createStatement();	
				ResultSet rs=st.executeQuery("SELECT * FROM atm WHERE password ='"+txtuser.getText()+"'");
				while(rs.next())
				{
				    txtpass.setText(rs.getString(9));
					JOptionPane.showMessageDialog(null,"Record Found.","ATM System",JOptionPane.INFORMATION_MESSAGE);
				}
					st.close();
			}
			catch(SQLException s)
			{
				System.out.println("No record found!\n\n\n");
				System.out.println("SQL Error" + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
			}
			catch(Exception x)
			{
				System.out.println("Error" + x.toString()+" " + x.getMessage());
			}
			
		}
	}
	
	public static void main(String[]args)
	{
		BalanceTransaction log=new BalanceTransaction();
		log.setLocation(400,250);
		log.setSize(300,200);
		log.setTitle("Balance Inquiry");
		log.setResizable(false);
		log.setVisible(true);
	}
}
