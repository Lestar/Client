import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;


public class keyActions implements ClipboardOwner
{
	public void undo(SimpleFrame panelText)
	{
		try 
		{
			String login = panelText.getLogin();
			ActionData data = new ActionData(panelText.getText().getCaretPosition(),panelText.getText().getText(), false);
			data = panelText.getList().newGetString(data, login);
			panelText.getText().setText(data.getPanelText());
			panelText.getText().setCaretPosition(data.getCaretPosition());
			panelText.setIsChanged(data.getChanged());
		} 
		catch (RemoteException e) 
		{
			JOptionPane.showMessageDialog(panelText, "Disconnect from server");
		}
	}
	public void redo(SimpleFrame panelText)
	{
		try 
		{
			String login = panelText.getLogin();
			ActionData data = new ActionData(panelText.getText().getCaretPosition(),panelText.getText().getText(), false);
			data = panelText.getList().newRestoreString(data, login);
			panelText.getText().setText(data.getPanelText());
			panelText.getText().setCaretPosition(data.getCaretPosition());
			panelText.setIsChanged(data.getChanged());
		} 
		catch (RemoteException e) 
		{
			JOptionPane.showMessageDialog(panelText, "Disconnect from server");
		}
	}
	public void copy(SimpleFrame panelText)
	{
		if(panelText.getText().getSelectedText() != null)
		{
			StringSelection stringSelection = new StringSelection( panelText.getText().getSelectedText() );
		    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		    clipboard.setContents(stringSelection,  this );
		}
	}
	public void cut(SimpleFrame panelText)
	{
		panelText.setAction(false);
		int cp;
		if (panelText.getText().getSelectedText() != null)
		{
			copy(panelText);
			cp = panelText.getText().getSelectionStart();
			try 
			{
				String text = panelText.getText().getSelectedText();
				int position = panelText.getText().getSelectionStart();
				ActionsHistoryImpl.action type = ActionsHistoryImpl.action.SUB;
				ActionData data = new ActionData(panelText.getText().getCaretPosition(),panelText.getText().getText(), false);
				String login = panelText.getLogin();
				data = panelText.getList().newPutString(text, position, type, data, login);
				panelText.getText().setText(data.getPanelText());
				panelText.getText().setCaretPosition(data.getCaretPosition());
				panelText.setIsChanged(data.getChanged());
			} 
			catch (RemoteException e) 
			{
				JOptionPane.showMessageDialog(panelText, "Disconnect from server");
			}
			if(panelText.getText().getSelectionStart() == 0)
			{
				int begin = panelText.getText().getSelectedText().length();
				int finish = panelText.getText().getText().length();
				String text = panelText.getText().getText().substring(begin, finish);
				panelText.getText().setText(text);
				panelText.getText().setCaretPosition(cp);
				return;
			}
			if(panelText.getText().getSelectionStart() > 0 && panelText.getText().getSelectionStart() < panelText.getText().getText().length())
			{
				int begin1 = 0;
				int finish1 = panelText.getText().getSelectionStart();
				String text1 = panelText.getText().getText().substring(begin1, finish1); 
				int begin2 = panelText.getText().getSelectionStart() + panelText.getText().getSelectedText().length();
				int finish2 = panelText.getText().getText().length();
				String text2 = panelText.getText().getText().substring(begin2, finish2);
				panelText.getText().setText(text1 + text2);
				panelText.getText().setCaretPosition(cp);
				return;
			}
			if(panelText.getText().getSelectionStart() == panelText.getText().getText().length())
			{
				int begin = 0;
				int finish = panelText.getText().getSelectionStart();
				String text = panelText.getText().getText().substring(begin, finish);
				panelText.getText().setText(text);
				panelText.getText().setCaretPosition(cp);
				return;
			}
		}
		panelText.setAction(true);
	}
	public void paste(SimpleFrame panelText)
	{
		panelText.setAction(false);
		String clipboardText = null;
        Transferable trans = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
         
        if (trans != null && trans.isDataFlavorSupported(DataFlavor.stringFlavor)) 
        {
             try 
             {
				clipboardText = (String) trans.getTransferData(DataFlavor.stringFlavor);
				int position = panelText.getText().getCaretPosition();
                ActionsHistoryImpl.action type = ActionsHistoryImpl.action.ADD;
                ActionData data = new ActionData(panelText.getText().getCaretPosition(),panelText.getText().getText(), false);
                String login = panelText.getLogin();
                data = panelText.getList().newPutString(clipboardText, position, type, data, login);
                panelText.getText().setText(data.getPanelText());
                panelText.getText().setCaretPosition(data.getCaretPosition());
				panelText.setIsChanged(data.getChanged());
			} 
            catch (UnsupportedFlavorException e) 
            {
            	JOptionPane.showMessageDialog(panelText, "Pasting error");
			} 
            catch (IOException e) 
            {
            	JOptionPane.showMessageDialog(panelText, "Disconect from server");
			}
         }
                
        if(panelText.getText().getCaretPosition() == 0)
		{
        	panelText.getText().setText(clipboardText + panelText.getText().getText());
        	panelText.setAction(true);
        	return;
		}
		if(panelText.getText().getCaretPosition() > 0 && panelText.getText().getCaretPosition() < panelText.getText().getText().length())
		{
			int begin1 = 0;
			int finish1 = panelText.getText().getCaretPosition();
			String text1 = panelText.getText().getText().substring(begin1, finish1);
			int begin2 = panelText.getText().getCaretPosition();
			int finish2 =panelText.getText().getText().length();
			String text2 = panelText.getText().getText().substring(begin2, finish2);
			panelText.getText().setText(text1 + clipboardText + text2);
			panelText.setAction(true);
			return;
		}
		if(panelText.getText().getCaretPosition() == panelText.getText().getText().length())
		{
			panelText.getText().setText(panelText.getText().getText() + clipboardText);
			panelText.setAction(true);
			return;
		}
	}
	public void delete(SimpleFrame panelText)
	{
		if(panelText.getText().getSelectedText() != null)
		{
			panelText.setAction(false);
			int begin1 = 0;
			int finish1 = panelText.getText().getSelectionStart();
			String text1 = panelText.getText().getText().substring(begin1, finish1);
			int begin2 = panelText.getText().getSelectionEnd();
			int finish2 = panelText.getText().getText().length();
			String text2 = panelText.getText().getText().substring(begin2, finish2);
			panelText.getText().setText(text1 + text2);
			try 
			{
				String text = panelText.getText().getSelectedText();
				int position = panelText.getText().getSelectionStart();
				ActionsHistoryImpl.action type = ActionsHistoryImpl.action.SUB;
				ActionData data = new ActionData(panelText.getText().getCaretPosition(),panelText.getText().getText(), false);
				String login = panelText.getLogin();
				data = panelText.getList().newPutString(text, position, type, data, login);
				panelText.getText().setText(data.getPanelText());
				panelText.getText().setCaretPosition(data.getCaretPosition());
				panelText.setIsChanged(data.getChanged());

			} 
			catch (RemoteException e) 
			{
				JOptionPane.showMessageDialog(panelText, "Disconect from server");
			}
			panelText.setAction(true);
		}
	}
	public void backspace(SimpleFrame panelText)
	{
		if(panelText.getText().getSelectedText() != null)
		{
			panelText.setAction(false);
			int begin1 = 0;
			int finish1 = panelText.getText().getSelectionStart();
			String text1 = panelText.getText().getText().substring(begin1, finish1);
			int begin2 = panelText.getText().getSelectionEnd();
			int finish2 = panelText.getText().getText().length();
			String text2 = panelText.getText().getText().substring(begin2, finish2);
			panelText.getText().setText(text1 + text2);
			try 
			{
				String text = panelText.getText().getSelectedText();
				int position = panelText.getText().getSelectionStart();
				ActionsHistoryImpl.action type = ActionsHistoryImpl.action.SUB;
				ActionData data = new ActionData(panelText.getText().getCaretPosition(),panelText.getText().getText(), false);
				String login = panelText.getLogin();
				data = panelText.getList().newPutString(text, position, type, data, login);
				panelText.getText().setText(data.getPanelText());
				panelText.getText().setCaretPosition(data.getCaretPosition());
				panelText.setIsChanged(data.getChanged());

			} 
			catch (RemoteException e) 
			{
				JOptionPane.showMessageDialog(panelText, "Disconect from server");
			}
			panelText.setAction(true);
		}
	}
	
	public void lostOwnership(Clipboard arg0, Transferable arg1) {}
}