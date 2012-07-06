import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


public class OpenProjFrame extends JFrame 
{
	private static final long serialVersionUID = 1L;
	
	private int scr_w;
	private int scr_h;
	private JLabel name;
	private JList<String> ProjList;
	private JLabel ProjName;
	private JTextField NameTextField;
	private JButton buttonNew;
	private JButton buttonOpen;
	private JButton buttonCancel;
	private Box vbox1;
	private Box vbox2;
	private Box hbox;
	private Container contentPane;
	private OpenProjFrame opf;
	private String pass;
	private String login;
	private ConnectMethods list;
	private String ProjectName;
	
	OpenProjFrame(final ConnectMethods list, final String login)
	{
		setTitle("Choose project...");
		this.setList(list);
		this.setLogin(login);
		opf = this;
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen = kit.getScreenSize();
		
		scr_w = screen.width;
		scr_h = screen.height;
		this.setLocation(scr_w/2 - 150, scr_h/2 - 100);
		setTitle("Login...");
		setSize(350, 300);
		
		ArrayList<String> projList = null;
		try 
		{
			projList = list.getProjectList();
		} 
		catch (RemoteException e2) 
		{
			JOptionPane.showMessageDialog(buttonNew, "Disconnect from server");
		}
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for(int i = 0; i < projList.size(); i++)
		{
			listModel.addElement(projList.get(i));
		}
		if(listModel.size() == 0)
		{
			listModel.addElement("<no avalible projects>");
		}
		name = new JLabel("Open project");
		name.setAlignmentX(LEFT_ALIGNMENT);
		ProjList = new JList<String>(listModel);
		ProjList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ProjList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		ProjList.setAlignmentX(LEFT_ALIGNMENT);
		ProjList.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent arg0) 
			{
				if(arg0.getClickCount() == 2)
				{
					Open();
				}
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		});
		ProjName = new JLabel("New project");
	    ProjName.setAlignmentX(LEFT_ALIGNMENT);
	    NameTextField = new JTextField(13);
	    NameTextField.setAlignmentX(LEFT_ALIGNMENT);
	    NameTextField.setMaximumSize(NameTextField.getPreferredSize());
	    NameTextField.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) New();
			}
			public void keyReleased(KeyEvent arg0){}

			public void keyTyped(KeyEvent arg0){}
		});
	       
	    buttonNew = new JButton("New");
	    buttonNew.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e) 
			{
				New();
			}
	    });
	    buttonNew.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
					New();
				}
			}
			public void keyReleased(KeyEvent arg0){}
			public void keyTyped(KeyEvent arg0){}
		});
	    buttonOpen = new JButton("Open");
	    buttonOpen.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e) 
			{
				Open();
			}
	    });
	    buttonOpen.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
					Open();
				}
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
	    
	    Dimension buttonSize = buttonCancel.getPreferredSize();
	    buttonNew.setPreferredSize(buttonSize);
	    buttonNew.setMaximumSize(buttonSize);
	    buttonNew.setMinimumSize(buttonSize);
	    buttonOpen.setPreferredSize(buttonSize);
	    buttonOpen.setMaximumSize(buttonSize);
	    buttonOpen.setMinimumSize(buttonSize);
	    buttonCancel.setPreferredSize(buttonSize);
	    buttonCancel.setMaximumSize(buttonSize);
	    buttonCancel.setMinimumSize(buttonSize);
	    
	    Dimension listSize = new Dimension(350 - buttonSize.width - 40 ,200);
	    ProjList.setPreferredSize(listSize);
	    ProjList.setMaximumSize(listSize);
	    ProjList.setMinimumSize(listSize);
	    
	    vbox1 = Box.createVerticalBox();
		vbox1.add(Box.createVerticalStrut(5));
		vbox1.add(name);
		vbox1.add(Box.createVerticalStrut(2));
	    vbox1.add(ProjList);
	    vbox1.add(Box.createVerticalStrut(5));
	    vbox1.add(ProjName);
	    vbox1.add(Box.createVerticalStrut(2));
	    vbox1.add(NameTextField);
	    vbox1.add(Box.createGlue());
	    	    
	    vbox2 = Box.createVerticalBox();
	    vbox2.add(Box.createVerticalStrut(20));
	    vbox2.add(buttonNew);
	    vbox2.add(Box.createVerticalStrut(5));
	    vbox2.add(buttonOpen);
	    vbox2.add(Box.createVerticalStrut(5));
	    vbox2.add(buttonCancel);
	    vbox2.add(Box.createGlue());
	    vbox2.add(Box.createVerticalStrut(5));

	    hbox = Box.createHorizontalBox();
	    hbox.add(Box.createHorizontalStrut(5));
	    hbox.add(vbox1);
	    hbox.add(Box.createHorizontalGlue());
	    hbox.add(vbox2);
	    hbox.add(Box.createHorizontalStrut(5));
	    hbox.setAlignmentX(RIGHT_ALIGNMENT);
	    
	    contentPane = getContentPane();
	    contentPane.add(hbox, BorderLayout.CENTER);
	    this.setResizable(false);
	}
	
	public String getPass() 
	{
		return pass;
	}
	public void setPass(String pass) 
	{
		this.pass = pass;
	}
	public String getLogin() 
	{
		return login;
	}
	public void setLogin(String login) 
	{
		this.login = login;
	}
	public ConnectMethods getList() 
	{
		return list;
	}
	public void setList(ConnectMethods list)
	{
		this.list = list;
	}
	public String getProjectName()
	{
		return ProjectName;
	}
	public void setProjectName(String projectName) 
	{
		ProjectName = projectName;
	}

	private void New()
	{
		try 
		{
			for(int i = 0; i < list.getProjectList().size(); i++)
			{
				StringTokenizer st = new StringTokenizer(list.getProjectList().get(i), " : ");
				String str = st.nextToken();
				if(str.equals(NameTextField.getText()))
				{
					JOptionPane.showMessageDialog(buttonNew, "Project with such name is already exists");
					return;
				}
			}
		} 
		catch (RemoteException e1) 
		{
			JOptionPane.showMessageDialog(buttonNew, "Disconect from server");
			this.dispose();
			LoginFrame log = new LoginFrame();
			log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			log.setVisible(true);
		}
		
		if(NameTextField.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(buttonNew, "Enter projact name");
			return;
		}
		setProjectName(NameTextField.getText());
		PassFrame PF = new PassFrame(opf);
		PF.setDefaultCloseOperation(EXIT_ON_CLOSE);
		PF.setVisible(true);
	}
	private void Open()
	{
		int index = ProjList.getSelectedIndex();
		ConfPassFrame CPF = new ConfPassFrame(opf, index);
		CPF.setDefaultCloseOperation(EXIT_ON_CLOSE);
		CPF.setVisible(true);
	}
}
