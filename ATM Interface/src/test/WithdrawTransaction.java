package test;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class WithdrawTransaction extends JFrame implements ActionListener
{
	JPasswordField txtuser=new JPasswordField(25);
	JTextField txtpass=new JTextField(25);
	JTextField txtwid=new JTextField(25);
	JLabel lbluser=new JLabel("Pin Number: ");
	JLabel lblpass=new JLabel("Amount: ");
	JLabel lblwid=new JLabel("Balanced: ");
	JButton btnOk=new JButton("Back to Menu");
	JButton btnRegister=new JButton("Withdraw");
	
    Connection conn;
	//ResultSet rs;
	Statement st;
	PreparedStatement ps;
	public WithdrawTransaction()
	{
		super("Withdraw Transaction");
		
		JPanel pane=new JPanel();
		pane.setLayout(null);
		
		//Adding Components to Frame
		pane.add(txtuser);
 		pane.add(txtpass);
 		pane.add(txtwid);
		pane.add(lbluser);
 		pane.add(lblpass);
 		pane.add(lblwid);	
		pane.add(btnOk);
		pane.add(btnRegister);
		
		//Setting location of components
		lbluser.setBounds(10,20,80,25);
 		lblpass.setBounds(10,50,80,20);
 		lblwid.setBounds(600,60,80,20);
		txtuser.setBounds(100,20,165,25);
 		txtpass.setBounds(600,60,100,20);
 		txtwid.setBounds(100,50,165,25);
 		btnOk.setBounds(135,90,125,25);
		btnRegister.setBounds(25,90,100,25);
		
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
			mainmenu panel = new mainmenu();
			panel.setSize(330,300);
			panel.setVisible(true);
			panel.setResizable(false);
			panel.setLocation(400,250);
			dispose();
        }
		else if(source==btnRegister)
		{
		//	btnRegister.setText("Withdraw");
			
			try
			{
				try
				{
					st= conn.createStatement();	
					ResultSet rs=st.executeQuery("SELECT * FROM atm WHERE Password ='"+txtuser.getText()+"'");
						while(rs.next())
						{
							txtpass.setText(rs.getString(9));
							int a = Integer.parseInt(txtpass.getText());
							int b = Integer.parseInt(txtwid.getText());
							if(a<b)
							{
						    JOptionPane.showMessageDialog(null,"Transaction cannot process","ATM",JOptionPane.INFORMATION_MESSAGE);
							txtwid.setText("");
							//}else{
							//	JOptionPane.showMessageDialog(null,"Transaction c s","ATM",JOptionPane.INFORMATION_MESSAGE);
							//}
							}
							else
							{
								if(b<10)
								{
									JOptionPane.showMessageDialog(null,"The Minimum cash you can withdraw is 10","ATM",JOptionPane.INFORMATION_MESSAGE);
								}
								else
								{
								    int sum = a-b;
								
									txtpass.setText((sum+""));
									txtwid.setText("");
									JOptionPane.showMessageDialog(null,"You WithDraw "+b,"ATM",JOptionPane.INFORMATION_MESSAGE);
										
									ps = conn.prepareStatement("UPDATE atm SET Password = '" + txtuser.getText() + "',ct = '" + sum +  "'WHERE Password = '" + txtuser.getText() + "'");
									ps.executeUpdate();
									txtuser.requestFocus(true);
							    }
							}
						}
					st.close();
					}
				    catch(NumberFormatException nfe)
				    {
					    JOptionPane.showMessageDialog(null,"Enter now the amount to withdraw","ATM",JOptionPane.INFORMATION_MESSAGE);
					}
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
		WithdrawTransaction log=new WithdrawTransaction();
		log.setLocation(400,250);
		log.setSize(300,200);
		log.setResizable(false);
		log.setVisible(true);
	}
}
