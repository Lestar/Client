import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class RegFrame extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JLabel loggin;
	private JTextField loginTextField;
	private Box hbox1;
	private JLabel pass;
	private JTextField passTextField;
	private Box hbox2;
	private JLabel passConf;
	private JTextField passConfTextField;
	private Box hbox3;
	private JButton buttonOk;
	private JButton buttonCancel;
	private Box hbox4;
	private Box vbox;
	private int scr_w;
	private int scr_h;
	private Container contentPane;	
	LoginFrame frame;
	final RegFrame rf;
	LoginFrame lf;
	private ConnectMethods link;
	
	RegFrame(final LoginFrame f)
	{
		setTitle("Registrate...");
		this.lf = f;
		f.setList();
		link = f.getList();
		rf = this;
		rf.addWindowListener(new WindowListener()
		{
			public void windowActivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			public void windowClosing(WindowEvent arg0)
			{
				frame.setVisible(true);
	    		rf.dispose();
			}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowOpened(WindowEvent arg0) {}
		});
		frame = f;
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
		hbox1.add(loggin);
	    hbox1.add(Box.createHorizontalStrut(10));
	    hbox1.add(loginTextField);
	    hbox1.add(Box.createHorizontalStrut(20));
	    hbox1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
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
	    hbox2.add(pass);
	    hbox2.add(Box.createHorizontalStrut(10));
	    hbox2.add(passTextField);
	    hbox2.add(Box.createHorizontalStrut(20));
	    hbox2.setAlignmentX(Component.RIGHT_ALIGNMENT);
	    
	    passConf = new JLabel("Confirm password:");
	    passConfTextField = new JTextField(13);
	    passConfTextField.setMaximumSize(passConfTextField.getPreferredSize());
	    passConfTextField.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)	Ok();
			}
			public void keyReleased(KeyEvent arg0){}

			public void keyTyped(KeyEvent arg0){}
		});
	    
	    hbox3 = Box.createHorizontalBox();
	    hbox3.add(passConf);
	    hbox3.add(Box.createHorizontalStrut(10));
	    hbox3.add(passConfTextField);
	    hbox3.add(Box.createHorizontalStrut(20));
	    hbox3.setAlignmentX(Component.RIGHT_ALIGNMENT);
	    
	    buttonOk = new JButton("Ok");
	    buttonOk.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e) 
			{
				Ok();
			}
	    });
	    buttonCancel = new JButton("Cancel");
	    buttonCancel.addActionListener(new ActionListener()
	    {
	    	public void actionPerformed(ActionEvent arg0) 
	    	{
	    		frame.setVisible(true);
	    		rf.dispose();
			}
	    	
	    });
	
	    hbox4 = Box.createHorizontalBox();
	    hbox4.add(buttonOk);
	    hbox4.add(Box.createHorizontalStrut(30));
	    hbox4.add(buttonCancel);
	    hbox4.add(Box.createHorizontalStrut(70));
	    hbox4.setAlignmentX(Component.RIGHT_ALIGNMENT);
	    
	    vbox = Box.createVerticalBox();
	    vbox.add(hbox1);
	    vbox.add(hbox2);
	    vbox.add(hbox3);
	    vbox.add(hbox4);

	    contentPane = getContentPane();
	    contentPane.add(vbox, BorderLayout.CENTER);
	    this.setResizable(false);
	}
	
	private void Ok()
	{
		if(loginTextField.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(rf, "Enter login");
			return;
		}
		if(loginTextField.getText().length() < 4)
		{
			JOptionPane.showMessageDialog(rf, "Login is to short, it should be longer then 3 characters");
			return;
		}
		if(passTextField.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(rf, "Enter password");
			return;
		}
		if(passTextField.getText().length() < 5)
		{
			JOptionPane.showMessageDialog(rf, "Password is to short, it should be longer then 6 characters");
			return;
		}
		if(passConfTextField.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(rf, "Confirm password");
			return;
		}
		if(!passConfTextField.getText().equals(passTextField.getText()))
		{
			JOptionPane.showMessageDialog(rf, "Passwords do not match");
			return;
		}
		try 
		{
			if(!link.Registrate(loginTextField.getText(), passTextField.getText()))
			{
				JOptionPane.showMessageDialog(rf, "Login is already used");
				return;
			}
			else
			{
				JOptionPane.showMessageDialog(rf, "Registration completed successful");
				frame.setVisible(true);
				rf.dispose();
			}
		} 
		catch (RemoteException e1) 
		{
			JOptionPane.showMessageDialog(rf, "No connection with server");
			rf.dispose();
			lf.setVisible(true);
		}
	}
}
