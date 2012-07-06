import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class LoginFrame extends JFrame 
{
	private static final long serialVersionUID = 1L;
	
	private JLabel loggin;
	private JTextField loginTextField;
	private Box hbox1;
	private JLabel pass;
	private JTextField passTextField;
	private Box hbox2;
	private JButton buttonOk;
	private JButton buttonCancel;
	private JButton buttonRegistrate;
	private Box hbox3;
	private Box vbox;
	private Container contentPane;
	private int scr_w;
	private int scr_h;
	private ConnectMethods list;
	final LoginFrame f = this;
	
	LoginFrame()
	{
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen = kit.getScreenSize();
		scr_w = screen.width;
		scr_h = screen.height;
		this.setLocation(scr_w/2 - 150, scr_h/2 - 100);
		setTitle("Login...");
		setSize(300, 200);
	
		loggin = new JLabel("Loggin:");
		loginTextField = new JTextField(13);
		loginTextField.setMaximumSize(loginTextField.getPreferredSize());
		loginTextField.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)	Ok();
			}
			public void keyReleased(KeyEvent arg0){}

			public void keyTyped(KeyEvent arg0){}
		});
		hbox1 = Box.createHorizontalBox();
		hbox1.add(Box.createHorizontalStrut(20));
		hbox1.add(loggin);
	    hbox1.add(Box.createHorizontalStrut(29));
	    hbox1.add(loginTextField);
	    hbox1.setAlignmentX(Component.LEFT_ALIGNMENT);
	    
	    pass = new JLabel("Password:");
	    passTextField = new JTextField(13);
	    passTextField.setMaximumSize(passTextField.getPreferredSize());
	    passTextField.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)	Ok();
			}
			public void keyReleased(KeyEvent arg0){}

			public void keyTyped(KeyEvent arg0){}
		});

	    hbox2 = Box.createHorizontalBox();
	    hbox2.add(Box.createHorizontalStrut(20));
	    hbox2.add(pass);
	    hbox2.add(Box.createHorizontalStrut(10));
	    hbox2.add(passTextField);
	    hbox2.setAlignmentX(Component.LEFT_ALIGNMENT);
	   
	    buttonOk = new JButton("Ok");
	    buttonOk.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e) 
			{
				f.Ok();
			}
	    });
	    buttonOk.addKeyListener(new KeyListener()
	    {
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)	Ok();
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
	    	
	    });
	    buttonCancel = new JButton("Cancel");
	    buttonCancel.addActionListener(new ActionListener()
	    {
	    	public void actionPerformed(ActionEvent arg0) 
	    	{
				System.exit(0);
			}
	    	
	    });
	    buttonCancel.addKeyListener(new KeyListener()
	    {
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)	System.exit(0);
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
	    	
	    });
	    buttonRegistrate = new JButton("Registrate");
	    buttonRegistrate.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent arg0) 
			{
				f.setVisible(false);
				RegFrame reg = new RegFrame(f);
				reg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				reg.setVisible(true);
			}
	    	
	    });
	    buttonRegistrate.addKeyListener(new KeyListener()
	    {
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					f.setVisible(false);
					RegFrame reg = new RegFrame(f);
					reg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					reg.setVisible(true);
				}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
	    	
	    });
	    
	    hbox3 = Box.createHorizontalBox();
	    hbox3.add(Box.createHorizontalStrut(30));
	    hbox3.add(buttonOk);
	    hbox3.add(Box.createHorizontalStrut(10));
	    hbox3.add(buttonCancel);
	    hbox3.add(Box.createHorizontalStrut(10));
	    hbox3.add(buttonRegistrate);
	    hbox3.setAlignmentX(Component.LEFT_ALIGNMENT);
	    

	    vbox = Box.createVerticalBox();
	    vbox.add(hbox1);
	    vbox.add(hbox2);
	    vbox.add(hbox3);

	    contentPane = getContentPane();
	    contentPane.add(vbox, BorderLayout.CENTER);
	    this.setResizable(false);
	}
	
	private void Ok()
	{
		if(list == null)
		{
			setList();
		}
		if(loginTextField.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(f, "Enter login");
			return;
		}
		if(passTextField.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(f, "Enter password");
			return;
		}
		
		try {
			ConnectMethods.LogInfo info = list.Authenticate(loginTextField.getText(), passTextField.getText());
			if(info == ConnectMethods.LogInfo.INCORRECT_LOGGIN)
			{
				JOptionPane.showMessageDialog(f, "Login was not found in database, check your login");
				passTextField.setText("");
				loginTextField.setText("");
			}
			if(info == ConnectMethods.LogInfo.INCORRECT_PASS)
			{
				JOptionPane.showMessageDialog(f, "Incorrecn password, check your password");
				passTextField.setText("");
			}
			if(info == ConnectMethods.LogInfo.ALREADY_LOGGED)
			{
				JOptionPane.showMessageDialog(f, "User is already logged");
				passTextField.setText("");
			}
			if(info == ConnectMethods.LogInfo.CORRECT_DATA)
			{
				f.dispose();
				OpenProjFrame OPFrame = new OpenProjFrame(list, loginTextField.getText());
				OPFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				OPFrame.setVisible(true);
				
			}
		} 
		catch (RemoteException e1) 
		{
			JOptionPane.showMessageDialog(f, "Disconnect from server");
			return;
		}
		catch (NullPointerException e1)
		{
			this.dispose();
			LoginFrame log = new LoginFrame();
			log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			log.setVisible(true);
		}
	}

	public ConnectMethods getList()
	{
		return list;
	}
	public void setList()
	{
		System.setProperty("java.security.policy", "client.policy");
	    System.setSecurityManager(new RMISecurityManager());
		
		try 
		{
	        String url = "rmi://192.168.1.2/history";
	        list = (ConnectMethods)Naming.lookup(url);
	        
		} 
		catch (MalformedURLException e1) 
		{
			JOptionPane.showMessageDialog(f, "No legal protocol could be found");
			return;
		} 
		catch (RemoteException e1) 
		{
			JOptionPane.showMessageDialog(f, "Can`t connect to server");
			return;
		} 
		catch (NotBoundException e1) 
		{
			JOptionPane.showMessageDialog(f, "Name bind error");
			return;
		} 
	}
}
