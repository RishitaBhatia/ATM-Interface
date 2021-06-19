package test;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class TransferTransaction extends JFrame implements ActionListener
{
	JPasswordField txtuser=new JPasswordField(25);
	JTextField txtpass=new JTextField(25);
	JTextField txtwid=new JTextField(25);
	
	JPasswordField txtuser2=new JPasswordField(25);
	JTextField txtpass2=new JTextField(25);
	JTextField txtwid2=new JTextField(25);
	
	JLabel lbluser=new JLabel("Pin Number: ");
	JLabel lblpass=new JLabel("Transfer: ");
	JLabel lblwid=new JLabel("Balanced: ");
	
	JLabel lbluser2=new JLabel("Pin Number: ");
	JLabel lblpass2=new JLabel("Balance: ");
	JLabel lblwid2=new JLabel("Balance: ");
	
	JButton btnOk=new JButton("Back to Menu");
	JButton btnRegister=new JButton("Transfer");
	JButton btnRegister2=new JButton("Balance");
	
	JLabel lblyour = new JLabel("Your Account");
	JLabel lblReciever = new JLabel("Reciever Account");
	
	Connection cn;
	//ResultSet rs;
	Statement st;
	PreparedStatement ps;
	
	public TransferTransaction()
	{
		super("Transfer Fund Transaction");
		JPanel pane=new JPanel();
		pane.setLayout(null);
		
		//----Adding Components into your Frame
		pane.add(txtuser);
 		pane.add(txtpass);
 		pane.add(txtwid);
		pane.add(lbluser);
 		pane.add(lblpass);
 		pane.add(lblwid);	
 		pane.add(txtuser2);
 		pane.add(txtpass2);
 		pane.add(txtwid2);
		pane.add(lbluser2);
 		pane.add(lblpass2);
 		pane.add(lblwid2);	
		pane.add(btnOk);
		pane.add(btnRegister);
		pane.add(btnRegister2);
		pane.add(lblyour);
		pane.add(lblReciever);
		
		//-----Setting the location of the components
		lbluser.setBounds(30,50,80,25);
 		lblpass.setBounds(30,80,80,25);
 		lblwid.setBounds(600,80,80,20);
		txtuser.setBounds(120,50,165,25);
 		txtpass.setBounds(600,80,100,20);
 		txtwid.setBounds(120,80,165,25);
 		
 		lbluser2.setBounds(300,50,80,25);
 		lblpass2.setBounds(300,80,80,25);
 		lblwid2.setBounds(670,80,80,20);
		txtuser2.setBounds(390,50,165,25);
 		txtpass2.setBounds(650,80,100,20);
 		txtwid2.setBounds(390,80,165,25);
 		
 		lblyour.setBounds(160,20,140,20);
 		lblReciever.setBounds(410,20,140,20);
 		
		btnOk.setBounds(250,160,165,25);
		btnRegister.setBounds(120,120,165,25);
		btnRegister2.setBounds(390,120,165,25);
		
		//-----Adding the an actionlistener to the buttons
		btnOk.addActionListener(this);
		btnRegister.addActionListener(this);
		btnRegister2.addActionListener(this);
		txtpass.setEditable(false);
		txtpass2.setEditable(false);
		txtwid2.setEditable(false);
		
		setContentPane(pane);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		btnRegister.setToolTipText("Click First then Enter Your Money to Transfer");
		btnRegister2.setToolTipText("Click To display your balanced");
		btnOk.setToolTipText("Click To Back to Menu");
			
		lblyour.setForeground(Color.red);
		lblReciever.setForeground(Color.red);
		
		//connection
				
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			cn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","rishita","honey");
		}
		catch(ClassNotFoundException e)  
		{
 			System.err.println("Failed to load driver");
 			e.printStackTrace();
 	
 		}catch(SQLException e){
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
			try{
				try{
				st= cn.createStatement();	
					ResultSet rs=st.executeQuery("SELECT * FROM atm WHERE Password ='"+txtuser.getText()+"'");
					
						while(rs.next()){
						txtpass.setText(rs.getString(9));
							int a = Integer.parseInt(txtpass.getText());
							int b = Integer.parseInt(txtwid.getText());
							
							if(a<b)
							{
								JOptionPane.showMessageDialog(null,"Insufficient fund","ATM",JOptionPane.INFORMATION_MESSAGE);
								txtwid.setText("");
							}
							else
							{
								if(b<10)
								{
									JOptionPane.showMessageDialog(null,"The Minimum cash you can Transfer is 10","ATM",JOptionPane.INFORMATION_MESSAGE);
								
								}
								else
								{
							      int sum = a-b;
							      txtpass.setText((sum+""));
							//txtwid.setText("");
						JOptionPane.showMessageDialog(null,"You Transfer "+b,"ATM",JOptionPane.INFORMATION_MESSAGE);
				ps = cn.prepareStatement("UPDATE atm SET Password = '" + txtuser.getText() + "',ct = '" + sum +  "'WHERE Password = '" + txtuser.getText() + "'");
		
				ps.executeUpdate();
				txtuser.requestFocus(true);
							}
							}
						}
					st.close();
				
				}catch(NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(null,"Enter now the amount to Transfer","ATM",JOptionPane.INFORMATION_MESSAGE);
							
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
			
				
			
		}else if(source == btnRegister2){
						try{
				try{
				st= cn.createStatement();	
					ResultSet rs2=st.executeQuery("SELECT * FROM atm WHERE Password ='"+txtuser2.getText()+"'");
					
						while(rs2.next()){
						txtpass2.setText(rs2.getString(9));
							int c = Integer.parseInt(txtpass2.getText());
							int d = Integer.parseInt(txtwid.getText());
							int sum = c+d;
							txtwid2.setText((sum+""));
							txtpass.setText("");
							txtwid.setText("");
				//		JOptionPane.showMessageDialog(null,"You Transfer "+d,"ATM",JOptionPane.INFORMATION_MESSAGE);
				ps = cn.prepareStatement("UPDATE atm SET Password = '" + txtuser2.getText() + "',ct = '" + sum +  "'WHERE Password = '" + txtuser2.getText() + "'");
				
				ps.executeUpdate();
				txtuser2.requestFocus(true);
							}
					st.close();
				
				}catch(NumberFormatException nfe){
								JOptionPane.showMessageDialog(null,"Enter now the amount to Transfer","ATM",JOptionPane.INFORMATION_MESSAGE);
							
							}
				
				}catch(SQLException s){
					System.out.println("No record found!\n\n\n");
					System.out.println("SQL Error" + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
									}
				
				catch(Exception x){
					System.out.println("Error" + x.toString()+" " + x.getMessage());
								}
			
			
		}
	}
	
	public static void main(String[]args){
		TransferTransaction log=new TransferTransaction();
		
		log.setLocation(400,250);
		log.setSize(600,300);
		log.setResizable(false);
		log.setVisible(true);
		
	
	}
}