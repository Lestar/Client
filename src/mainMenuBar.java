import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class mainMenuBar extends JMenuBar
{
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem newFile;
	private JMenuItem openFile;
	private JMenuItem saveFile;
	private JMenuItem saveAsFile;
	private JMenuItem printText;
	private JMenuItem exit;
	private JMenu editMenu;
	private JMenuItem undo;
	private JMenuItem redo;
	private JMenuItem cut;
	private JMenuItem copy;
	private JMenuItem paste;
	private JMenuItem delete;
	private JMenuItem selectAll;
	mainMenuBar(final SimpleFrame frame)
	{
		menuBar = new JMenuBar();
		//---Menu "File"---
		fileMenu = new JMenu("File");
	    
	    newFile = new JMenuItem("New file                      Ctrl+N");
	    fileMenu.add(newFile);
	    openFile = new JMenuItem("Open file                     Ctrl+O");
	    fileMenu.add(openFile);
	    saveFile = new JMenuItem("Save file                     Ctrl+S");
	    fileMenu.add(saveFile);
	    saveAsFile = new JMenuItem("Save As file    Ctrl+Shift+S");
	    fileMenu.add(saveAsFile);
	    printText = new JMenuItem("Print     					 Ctrl+P");
	    fileMenu.add(printText);
	    exit = new JMenuItem("Exit");
	    fileMenu.add(exit);
	    //---Menu "Edit"---
	    editMenu = new JMenu("Edit");
	    
	    undo = new JMenuItem("Undo      Ctrl+Z");
	    editMenu.add(undo);
	    redo = new JMenuItem("Redo      Ctrl+Y");
	    editMenu.add(redo);
	    cut = new JMenuItem("Cut      Ctrl+X");
	    editMenu.add(cut);
	    copy = new JMenuItem("Copy      Ctrl+C");
	    editMenu.add(copy);
	    paste = new JMenuItem("Paste      Ctrl+V");
	    editMenu.add(paste);
	    delete = new JMenuItem("Delete      Delete");
	    editMenu.add(delete);
	    selectAll = new JMenuItem("Select All     Ctlr+A");
	    editMenu.add(selectAll);
	    	        
	    menuBar.add(fileMenu);
	    menuBar.add(editMenu);
	    
	    newFile.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e) 
			{
				new fileEvents().fileNew(frame);
				
			}
	    	
	    });
	    openFile.addActionListener(new ActionListener()
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		new fileEvents().fileOpen(frame);
			}
	    	
	    });
	    saveFile.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e) 
			{
				new fileEvents().fileSave(frame);
			}
	    	
	    });
	    saveAsFile.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e) 
			{
				new fileEvents().fileSaveAs(frame);
			}
	    	
	    });
	    printText.addActionListener(new ActionListener() 
	    {
			public void actionPerformed(ActionEvent arg0) 
			{
				new fileEvents().printText(frame);
			}
		});
	    exit.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e)
			{
				new fileEvents().exit(frame);				
			}
	    	
	    });
	    
	    undo.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e)
			{
				new keyActions().undo(frame);		
			}
	    	
	    });
	  	redo.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e)
			{
				new keyActions().redo(frame);				
			}
	    	
	    });
	  	cut.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e) 
			{
				new keyActions().cut(frame);
			}
	    });
	  	copy.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e)
			{
				new keyActions().copy(frame);
			}
	    	
	    });
 	  	paste.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e)
			{
				new keyActions().paste(frame);
			}
	    	
	    });
	  	delete.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e)
			{
				frame.temp = frame.getText().getSelectedText();
				frame.caretTextPosition = frame.getText().getCaretPosition() - 1;
				new keyActions().delete(frame);
			}
	    });
	  	selectAll.addActionListener(new ActionListener()
	  	{
	  		public void actionPerformed(ActionEvent e) 
	  		{
				frame.getText().setSelectionStart(0);
				frame.getText().setSelectionEnd(frame.getText().getText().length());
			}
	  		
	  	});
	}
	public JMenuBar getMenuBar()
	{
		return menuBar;
	}
}
