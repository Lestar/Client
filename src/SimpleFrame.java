import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SimpleFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private int scr_w;
	private int scr_h;
	
	private boolean isFileNameSetted = false;
	String fileName = "temp.tmp";
	String tempFileName = "temp.tmp";
	String temp = null;
	int caretTextPosition = 0;
	SimpleFrame SF;
	private ActionsHistory ptr;
	private String projName;
	private String login;
	
	private JScrollPane scrollText;
	private JScrollPane scrollChat;
	private JScrollPane scrollMessage;
	private boolean isChanged = false;
	private Timer autosave;
	private String docText = "";
	private SimpleFrame mainFrame;
	private boolean Action = true;
	private ActionsHistory list;
	private UpdateText UpdateLink;
	private TextClient client;
	private JTextPane text;
	private JTextPane ChatFrame;
	private JTextPane MessageFrame; 
	private JSplitPane Upper;
	private JSplitPane Downer;
		
	SimpleFrame(final ActionsHistory list, final String projName, final String login)
    {
		mainFrame = this;
		setProjName(projName);
		setPtr(list);
		SF = this;
		this.list = list;
		this.setLogin(login);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen = kit.getScreenSize();
		scr_w = screen.width;
		scr_h = screen.height;
		setSize(scr_w/2,scr_h/2);
		setTitle("Text Editor");
		
		addWindowListener(new WindowListener()
		{
			public void windowActivated(WindowEvent e){}
	        public void windowClosed(WindowEvent e){} 
			public void windowClosing(WindowEvent e) 
			{
				new fileEvents().exit(SF); 
			}
			public void windowDeactivated(WindowEvent e){} 
			public void windowDeiconified(WindowEvent e){} 
			public void windowIconified(WindowEvent e){} 
			public void windowOpened(WindowEvent e){} 
		});
		addComponentListener(new ComponentListener() 
		{
			public void componentShown(ComponentEvent arg0){}
			public void componentResized(ComponentEvent arg0)
			{
				if(mainFrame.getExtendedState() == JFrame.MAXIMIZED_BOTH)
				{
					Upper.setDividerLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 300);
					Downer.setDividerLocation(Toolkit.getDefaultToolkit().getScreenSize().height - 200);
					mainFrame.repaint();
				}
			}
			public void componentMoved(ComponentEvent arg0){}
			public void componentHidden(ComponentEvent arg0){}
		});
		mainMenuBar menuBar = new mainMenuBar(this);
		setJMenuBar(menuBar.getMenuBar());
		
		final EditorKeyEventListener listener = new keyEvents(this);
		final DocListener doc = new docChange();
		
		ChatFrame = new JTextPane();
		ChatFrame.setEditable(false);
		ChatFrame.setText("");
		MessageFrame = new JTextPane();
		text = new JTextPane();
		scrollText = new JScrollPane(getText());
		scrollText.createHorizontalScrollBar();
		scrollText.createVerticalScrollBar();
		scrollChat = new JScrollPane(ChatFrame);
		scrollMessage = new JScrollPane(MessageFrame);
		scrollMessage.setPreferredSize(new Dimension(scrollMessage.getMaximumSize().width, 300));
		MessageFrame.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
					if(MessageFrame.getText().length() > 0)
					{
						client.userInput = login + " : " + MessageFrame.getText();
						client.run();
						MessageFrame.setText(null);
					}
				}
			}
			public void keyReleased(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
						MessageFrame.setText(null);
				}
			}
			public void keyTyped(KeyEvent arg0) 
			{
				
			}
		});
		Upper = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollText, scrollChat);
		Upper.setContinuousLayout(true);
		Upper.setDividerLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 300);
		Downer = new JSplitPane(JSplitPane.VERTICAL_SPLIT, Upper, scrollMessage);
		Downer.setDividerLocation(Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 150);
		add(Downer, BorderLayout.CENTER);
		
		try 
		{
			String IP = list.ServerIP();
			int PORT = list.ServerPort();
			client = new TextClient(IP, PORT, ChatFrame);		
		} 
		catch (RemoteException e2) 
		{
			JOptionPane.showMessageDialog(SF, "Disconnect from server");
			this.dispose();
			LoginFrame log = new LoginFrame();
			log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			log.setVisible(true);
		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(SF, "Disconnect from server");
			this.dispose();
			LoginFrame log = new LoginFrame();
			log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			log.setVisible(true);
		}				
		
		try 
		{
			UpdateLink = new EditorPane(this);
			list.addDocList(UpdateLink);
			list.addUserList(getLogin());
			text.setText(list.setStr());
			docText = list.setStr();
		} 
		catch (RemoteException e2) 
		{
			JOptionPane.showMessageDialog(SF, "Disconnect from server");
			this.dispose();
			LoginFrame log = new LoginFrame();
			log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			log.setVisible(true);
		}
			
		text.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0) 
			{
				listener.Pressed(arg0);
			}
			public void keyReleased(KeyEvent arg0) 
			{
				listener.Released(arg0);
			}
			public void keyTyped(KeyEvent arg0) 
			{
				listener.Typed(arg0);
			}
		});
		text.getDocument().addDocumentListener(new DocumentListener()
		{
			public void changedUpdate(DocumentEvent e) 
			{
				if(Action)
				doc.changedUpdate(e, mainFrame);
			}

			public void insertUpdate(DocumentEvent e) 
			{
				if(Action)
				doc.insertUpdate(e, mainFrame);
			}

			public void removeUpdate(DocumentEvent e) 
			{
				if(Action)
				doc.removeUpdate(e, mainFrame);
			}
		});
		autosave = new Timer(5 * 1000, new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(isChanged)
				{
					PrintWriter out;
					try 
					{
						if(getIsFileNameSetted())
						{
							out = new PrintWriter(new FileWriter(fileName));
						}
						else 
						{
							out = new PrintWriter(new FileWriter(tempFileName));
						}
						String str = getText().getText();
						out.print(str);
						out.close();
					} 
					catch (IOException e1) 
					{
						JOptionPane.showMessageDialog(SF, "Saving error");
					}
				}
			}
		});
		autosave.start();
		final SimpleFrame link = this;
		text.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent event) 
			{
				new popupMenu(link, event);
			}
		});		
    }
	public String getProjName() 
	{
		return projName;
	}
	public void setProjName(String name) 
	{
		this.projName = name;
	}
	public boolean getIsFileNameSetted() 
	{
		return isFileNameSetted;
	}
	public void setIsFileNameSetted(boolean isFileNameSetted) 
	{
		this.isFileNameSetted = isFileNameSetted;
	}
	public ActionsHistory getPtr() 
	{
		return ptr;
	}
	public void setPtr(ActionsHistory ptr) 
	{
		this.ptr = ptr;
	}
	public boolean getIsChanged()
	{
		return isChanged;
	}
	public void setIsChanged(boolean b)
	{
		isChanged = b;
	}
	public JEditorPane getText() 
	{
		return text;
	}
	public void setText(String text)
	{
		this.setText(text);
	}
	public ActionsHistory getList()
	{
		return list;
	}
	public String getDocText() 
	{
		return docText;
	}
	public void setDocText(String docText) 
	{
		this.docText = docText;
	}
	public void setAction(boolean b)
	{
		Action = b;
	}
	public boolean getAction()
	{
		return Action;
	}
	public UpdateText getUpdateLink() 
	{
		return UpdateLink;
	}
	public void setUpdateLink(UpdateText updateLink) 
	{
		UpdateLink = updateLink;
	}
	public String getLogin()
	{
		return login;
	}
	public void setLogin(String login) 
	{
		this.login = login;
	}
	public TextClient getClient() 
	{
		return client;
	}
	public void setClient(TextClient client) 
	{
		this.client = client;
	}
}