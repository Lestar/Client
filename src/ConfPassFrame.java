import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
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


public class ConfPassFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private JLabel pass;
	private JTextField passTextField;
	private Box hbox1;
	private JButton buttonOk;
	private JButton buttonCancel;
	private Box hbox2;
	private Box vbox;
	private Container contentPane;
	private int scr_w;
	private int scr_h;
	private OpenProjFrame opf;
	private ConfPassFrame cpf;
	
	ConfPassFrame(OpenProjFrame f, final int index)
	{
		setTitle("Enter project password...");
		cpf = this;
		opf = f;
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen = kit.getScreenSize();
		scr_w = screen.width;
		scr_h = screen.height;
		this.setLocation(scr_w/2 - 150, scr_h/2 - 100);
		setTitle("Login...");
		setSize(300, 100);
		
		pass = new JLabel("Password");
		passTextField = new JTextField(13);
		passTextField.setMaximumSize(passTextField.getPreferredSize());
		passTextField.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) Ok(index, passTextField.getText());
			}
			public void keyReleased(KeyEvent arg0){}

			public void keyTyped(KeyEvent arg0){}
		});
		hbox1 = Box.createHorizontalBox();
		hbox1.add(Box.createHorizontalStrut(20));
		hbox1.add(pass);
	    hbox1.add(Box.createHorizontalStrut(29));
	    hbox1.add(passTextField);
	    hbox1.setAlignmentX(Component.LEFT_ALIGNMENT);
	    
	    buttonOk = new JButton("Ok");
	    buttonOk.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e) 
			{
				Ok(index, passTextField.getText());
			}
	    });
	    buttonOk.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) Ok(index, passTextField.getText());
			}
			public void keyReleased(KeyEvent arg0){}

			public void keyTyped(KeyEvent arg0){}
		});
	    buttonCancel = new JButton("Cancel");
	    buttonCancel.addActionListener(new ActionListener()
	    {
	    	public void actionPerformed(ActionEvent arg0) 
	    	{
				cpf.dispose();
			}
	    	
	    });
	    buttonCancel.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) cpf.dispose();
			}
			public void keyReleased(KeyEvent arg0){}

			public void keyTyped(KeyEvent arg0){}
		});
	    hbox2 = Box.createHorizontalBox();
	    hbox2.add(Box.createHorizontalStrut(30));
	    hbox2.add(buttonOk);
	    hbox2.add(Box.createHorizontalStrut(10));
	    hbox2.add(buttonCancel);
	    hbox2.setAlignmentX(Component.LEFT_ALIGNMENT);
	   
	    vbox = Box.createVerticalBox();
	    vbox.add(hbox1);
	    vbox.add(hbox2);
	    
	    contentPane = getContentPane();
	    contentPane.add(vbox, BorderLayout.CENTER);
	    this.setResizable(false);
	}
	
	private void Ok(int index, String pass)
	{
		
		try {
			ActionsHistory ptr = opf.getList().ConfirmPass(index, pass);
			if(ptr != null)
			{
				opf.setPass(passTextField.getText());
				cpf.dispose();
				opf.dispose();
				final SimpleFrame frame = new SimpleFrame(ptr, opf.getProjectName(), opf.getLogin());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true); 
			}
			else
			{
				JOptionPane.showMessageDialog(cpf, "Password do not match");
			}
		} 
		catch (HeadlessException e) 
		{
			JOptionPane.showMessageDialog(cpf, "Code that is dependent on a keyboard, display, or mouse \n is called in an environment that does not support \n a keyboard, display, or mouse");
			return;
		} 
		catch (RemoteException e) 
		{
			JOptionPane.showMessageDialog(buttonOk, "Disconect from server");
			this.dispose();
			opf.dispose();
			LoginFrame log = new LoginFrame();
			log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			log.setVisible(true);
		}
	}
}

