package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

 public class register extends JFrame implements ActionListener
 {
	public static void main(String[]args)
	{
	register panel = new register();
	panel.setSize(370,400);
	panel.setVisible(true);
	panel.setResizable(false);
	panel.setLocation(400,250);
	}
	
	Font f1 = new Font("", Font.BOLD, 10);
	JLabel lblUser = new JLabel("User Name ",JLabel.RIGHT);	
	JLabel lblFName = new JLabel("First Name ",JLabel.RIGHT);
	JLabel lblVPass = new JLabel("Verify Pin Number ",JLabel.RIGHT);
	JLabel lblLName = new JLabel("Last Name ",JLabel.RIGHT);
	JLabel lblPass = new JLabel("Pin Number ",JLabel.RIGHT);
	JLabel lblBday = new JLabel("B-Day ",JLabel.RIGHT);
	JLabel lblCash = new JLabel("Cash Tender ",JLabel.RIGHT);
	JLabel lblday = new JLabel("Day");
	JLabel lblMonth = new JLabel("Month");
	JLabel lblyer = new JLabel("Year");
	
	JTextField txtUser = new JTextField(20);
	JTextField txtFName= new JTextField(20);
	JTextField txtLName = new JTextField(20);
	JPasswordField txtPass= new JPasswordField(20);
	JPasswordField txtVPass = new JPasswordField(20);
	JTextField txtday = new JTextField(20);
	JTextField txtmonth = new JTextField(20);
	JTextField txtyear = new JTextField(20);
	JTextField txtCash = new JTextField(20);
	
	JButton btnCret = new JButton("Register");
	
	Connection conn;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;
	PreparedStatement ps1;

    public void clear()
    {
		txtUser.setText("");
		txtFName.setText("");
		txtPass.setText("");
		txtLName.setText("");
		txtVPass.setText("");
	}
	
	public register() 
	{
		super("Register");
	
		JPanel pane = new JPanel();
		pane.setLayout(null);
		
		lblyer.setFont(f1);
		lblday.setFont(f1);
		lblMonth.setFont(f1);
		lblUser.setBounds(5,30,120,25);
		pane.add(lblUser);
		txtUser.setBounds(145,30,150,25);
		pane.add(txtUser);
		
		lblFName.setBounds(5,65,120,25);
		pane.add(lblFName);
		txtFName.setBounds(145,65,150,25);
		pane.add(txtFName);
		
		lblLName.setBounds(5,100,120,25); 
		pane.add(lblLName);
		txtLName.setBounds(145,100,150,25); 
		pane.add(txtLName);
		
		lblPass.setBounds(5,135,120,25);
		pane.add(lblPass);
		txtVPass.setBounds(145,135,150,25);
		pane.add(txtVPass);
		
		lblVPass.setBounds(5,170,120,25);
		pane.add(lblVPass);
		txtPass.setBounds(145,170,150,25); 
		pane.add(txtPass);	
		
		lblBday.setBounds(5,220,120,25);
		pane.add(lblBday);	
		txtday.setBounds(145,220,50,25);
		pane.add(txtday);	
		txtmonth.setBounds(195,220,50,25);
		pane.add(txtmonth);
		txtyear.setBounds(245,220,50,25);
		pane.add(txtyear);
		lblday.setBounds(155,200,70,20);
		pane.add(lblday);
		lblMonth.setBounds(205,200,70,20);
		pane.add(lblMonth);
		lblyer.setBounds(255,200,70,20);
		pane.add(lblyer);
	
			
		lblCash.setBounds(5,255,120,25);
		pane.add(lblCash);
		txtCash.setBounds(145,255,150,25);
		pane.add(txtCash);
			
		btnCret.setBounds(100,290,165,25);
		pane.add(btnCret);
		btnCret.addActionListener(this);
		
		setContentPane(pane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
 		catch(SQLException e)
		{
 			System.err.println("Unable to connect");
 			e.printStackTrace();
 		}
	}

	public void actionPerformed(ActionEvent e)
	{

		Object source = e.getSource();
	    if(source == btnCret)
	    {
			String suser = 	txtUser.getText();
			String sname =	txtFName.getText();
			String spass = 	txtPass.getText();
			String slname =	txtLName.getText();
			String svpass = txtVPass.getText();

		
	        if((suser.length()==0 || sname.length()==0 || spass.length()==0 || slname.length()==0 || svpass.length()==0 ))
	        {
    			JOptionPane.showMessageDialog(null,"Some Fields are empty","WARNING",JOptionPane.WARNING_MESSAGE);
    		}
            else
            {
    		    if(spass.equals(svpass))
    		    {
    			   boolean xx = false;
    			   try
    			   {
    				   st= conn.createStatement();	
    				   rs=st.executeQuery("SELECT * FROM atm WHERE Password = '" + spass + "'");
					   while(rs.next())
					   {
							String spass2 =rs.getString(4);
							xx = true;
							JOptionPane.showMessageDialog(null,"Pincode already used","Information",JOptionPane.INFORMATION_MESSAGE);
					   }	
					   st.close();
					
					   if(xx==false)
					   {
							try
							{
	                            st= conn.createStatement();
							    ps=conn.prepareStatement("INSERT INTO atm " + " (username,fname,lname,password,vpassword,bday,bmonth,byear,ct) " + " VALUES(?,?,?,?,?,?,?,?,?)");		
								ps.setString(1,txtUser.getText());	
								ps.setString(2,txtFName.getText());
								ps.setString(3,txtLName.getText());
								ps.setString(4,txtPass.getText());
								ps.setString(5,txtVPass.getText());
								ps.setString(6,txtday.getText());
								ps.setString(7,txtmonth.getText());
								ps.setString(8,txtyear.getText());
								ps.setString(9,txtCash.getText());
				                ps.executeUpdate();
				
							    JOptionPane.showMessageDialog(null,"Your New Account has been successfully Created.","ATM",JOptionPane.INFORMATION_MESSAGE);
								txtUser.requestFocus(true);
								st.close();
								clear();
				
								login log=new login();
								log.setLocation(400,250);
								log.setSize(300,200);
								log.setTitle("Log-In");
								log.setResizable(false);
								log.setVisible(true);
								dispose();
				           }
				//}

				           catch(SQLException sqlEx)
				           {
				               JOptionPane.showMessageDialog(null,"General error","ATM",JOptionPane.INFORMATION_MESSAGE);
				           }
						}
    			   }
				   catch(SQLException s)
    			   {
					   System.out.println("SQL Error" + s.toString() + " " + s.getErrorCode() + " " + s.getSQLState());
				   }
				   catch(Exception x)
    			   {
					   System.out.println("Error" + x.toString()+" " + x.getMessage());
				   }
    		  }
			  else
			  {
				  JOptionPane.showMessageDialog(null,"Verify your password.","ATM",JOptionPane.INFORMATION_MESSAGE);
			  }
		}	
					
	}
}
}
 
						
		
		
					
					
  
 
 
