import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class PassFrame extends JFrame
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
	private Box hbox3;
	private Box vbox;
	private Container contentPane;
	private int scr_w;
	private int scr_h;
	private OpenProjFrame opf;
	private PassFrame pf;
	
	PassFrame(OpenProjFrame f)
	{
		setTitle("Set project password...");
		pf = this;
		opf = f;
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen = kit.getScreenSize();
		scr_w = screen.width;
		scr_h = screen.height;
		this.setLocation(scr_w/2 - 150, scr_h/2 - 100);
		setTitle("Login...");
		setSize(300, 200);
		
		loggin = new JLabel("Password");
		loginTextField = new JTextField(13);
		loginTextField.setMaximumSize(loginTextField.getPreferredSize());
		loginTextField.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) Ok();
			}
			public void keyReleased(KeyEvent arg0){}

			public void keyTyped(KeyEvent arg0){}
		});
		hbox1 = Box.createHorizontalBox();
		hbox1.add(Box.createHorizontalStrut(20));
		hbox1.add(loggin);
	    hbox1.add(Box.createHorizontalStrut(57));
	    hbox1.add(loginTextField);
	    hbox1.setAlignmentX(Component.LEFT_ALIGNMENT);
	    
	    pass = new JLabel("Confirm password");
	    passTextField = new JTextField(13);
	    passTextField.setMaximumSize(passTextField.getPreferredSize());
	    passTextField.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)Ok();
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
				Ok();
			}
	    });
	    buttonOk.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) Ok();
			}
			public void keyReleased(KeyEvent arg0){}

			public void keyTyped(KeyEvent arg0){}
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
			public void keyPressed(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) System.exit(0);
			}
			public void keyReleased(KeyEvent arg0){}

			public void keyTyped(KeyEvent arg0){}
		});
	    hbox3 = Box.createHorizontalBox();
	    hbox3.add(Box.createHorizontalStrut(80));
	    hbox3.add(buttonOk);
	    hbox3.add(Box.createHorizontalStrut(10));
	    hbox3.add(buttonCancel);
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
		if(loginTextField.getText().equals(passTextField.getText()))
		{
			opf.setPass(loginTextField.getText());
			pf.dispose();
			opf.dispose();
			ActionsHistory ptr = null;
			try 
			{
				ptr = opf.getList().NewThread(opf.getLogin(), loginTextField.getText(), opf.getProjectName());
			} 
			catch (RemoteException e1) 
			{
				JOptionPane.showMessageDialog(this, "Disconnect from server");
			}
			final SimpleFrame frame = new SimpleFrame(ptr, opf.getProjectName(), opf.getLogin());
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true); 
		}
		else
		{
			JOptionPane.showMessageDialog(pf, "Password do not match");
			opf.dispose();
			pf.dispose();
			LoginFrame log = new LoginFrame();
			log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			log.setVisible(true);
		}
	}
}
