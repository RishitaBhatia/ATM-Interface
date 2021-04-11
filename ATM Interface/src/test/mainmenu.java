package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class mainmenu extends JFrame implements ActionListener
{
	public static void main(String[]args)
	{
	mainmenu panel = new mainmenu();
	panel.setSize(330,300);
	panel.setVisible(true);
	panel.setResizable(false);
	panel.setLocation(400,250);
	}
	
	JButton btnBals = new JButton("Bal.Enquiry");
	JButton btnDep = new JButton("Deposit");
	JButton btnWid = new JButton("Withdraw");
	JButton btntrans = new JButton("Transfer");
	JButton btnLogOut = new JButton("Log Out");
	
	Connection conn;
	Statement st;
	PreparedStatement ps;
	
    public mainmenu() 
    {
		super("ATM");
	
		JPanel pane = new JPanel();
		pane.setLayout(null);
		
		btnBals.setBounds(40,60,100,25);
		pane.add(btnBals);
		btnBals.addActionListener(this);
		//btnBals.setForeground(Color.yellow);
		//btnBals.setBackground(Color.black);
		
		btntrans.setBounds(40,100,100,25);
		pane.add(btntrans);
		btntrans.addActionListener(this);	
		
		btnDep.setBounds(170,60,100,25);
		pane.add(btnDep);
		btnDep.addActionListener(this);

		btnWid.setBounds(170,100,100,25);
		pane.add(btnWid);
		btnWid.addActionListener(this);	
		
		btnLogOut.setBounds(115,150,80,25);
		pane.add(btnLogOut);
		btnLogOut.addActionListener(this);
		
		setContentPane(pane);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Select Transaction"));
		
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

		Object source = e.getSource();
		if(source == btnBals)
		{			
		    BalanceTransaction log=new BalanceTransaction();
            log.setLocation(400,250);
			log.setSize(300,200);
			log.setTitle("Balance Enquiry");
			log.setResizable(false);
			log.setVisible(true);
			dispose();
		}
		if(source == btnDep)
		{
		    DepositTransaction log=new DepositTransaction();
            log.setLocation(400,250);
			log.setSize(300,200);
			log.setResizable(false);
			log.setVisible(true);
			dispose();
		}
		if(source == btnWid)
		{
			WithdrawTransaction log=new WithdrawTransaction();
			log.setLocation(400,250);
			log.setSize(300,200);
			log.setResizable(false);
			log.setVisible(true);
            dispose();
		}	
	    if(source == btntrans)
	    {
			TransferTransaction log=new TransferTransaction();
	        log.setLocation(400,250);
			log.setSize(600,300);
			log.setResizable(false);
			log.setVisible(true);
			dispose();		
		}
		if(source == btnLogOut)
		{
			int n=JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?","Exit",JOptionPane.YES_NO_OPTION);
			if(n==JOptionPane.YES_OPTION)
			{
				login log=new login();
                log.setLocation(400,250);
				log.setSize(300,200);
				log.setTitle("Log-In");
				log.setResizable(false);
				log.setVisible(true);
				dispose();
			}
		}	
    }
}
