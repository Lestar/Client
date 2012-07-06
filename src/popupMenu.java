import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;


public class popupMenu extends JPopupMenu
{
	private static final long serialVersionUID = 1L;
	private JPopupMenu popupMenu;
	private JMenuItem undo;
	private JMenuItem redo;
	private JMenuItem copy;
	private JMenuItem cut;
	private JMenuItem paste;
	private JMenuItem selectAll;
	@SuppressWarnings("null")
	popupMenu(final SimpleFrame text, MouseEvent event)
	{
		popupMenu = new JPopupMenu();
		undo = new JMenuItem("Undo");
		redo = new JMenuItem("Redo");
		copy = new JMenuItem("Copy");
		cut = new JMenuItem("Cut");
		paste = new JMenuItem("Paste");
		selectAll = new JMenuItem("Select All");
		
		undo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new keyActions().undo(text);				
			}
			
		});
		redo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new keyActions().redo(text);				
			}
			
		});
		copy.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new keyActions().copy(text);				
			}
			
		});
		cut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new keyActions().cut(text);				
			}
			
		});
		paste.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new keyActions().paste(text);				
			}
			
		});
		selectAll.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				text.getText().setSelectionStart(0);
				text.getText().setSelectionEnd(text.getText().getText().length());				
			}
			
		});
		
		popupMenu.add(undo);
		popupMenu.add(redo);
		popupMenu.add(copy);
		popupMenu.add(cut);
		popupMenu.add(paste);
		popupMenu.add(selectAll);
		if (SwingUtilities.isRightMouseButton(event))
		{
			Transferable trans = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
			if (trans == null && !trans.isDataFlavorSupported(DataFlavor.stringFlavor)) 
			{
				paste.setEnabled(false);
			}
			else
			{
				paste.setEnabled(true);
			}
			
			try {
				if(text.getList().isEmpty())
				{
					undo.setEnabled(false);
				}
				else
				{
					undo.setEnabled(true);
				}
			} 
			catch (RemoteException e1) 
			{
				e1.printStackTrace();
			}
			
			try 
			{
				if(text.getList().isEmpty() || (!text.getList().isEmpty() && text.getList().getIndexAction() == text.getList().getSize()-1))
				{
					redo.setEnabled(false);
				}
				else
				{
					redo.setEnabled(true);
				}
			} 
			catch (RemoteException e1) 
			{
				e1.printStackTrace();
			}
			
			if(text.getText().getSelectedText() == null)
			{
				copy.setEnabled(false);
				cut.setEnabled(false);
			}
			else
			{
				copy.setEnabled(true);
				cut.setEnabled(true);
			}
			popupMenu.show(text.getText(), event.getX(), event.getY());
		}
	}
}
