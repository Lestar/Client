import java.rmi.RemoteException;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;

public class docChange implements DocListener
{
	public void changedUpdate(DocumentEvent e, SimpleFrame text)
	{
		
	}
	public void insertUpdate(DocumentEvent e, SimpleFrame text)
	{
		text.setAction(false);
		try 
		{
			text.setDocText(text.getText().getText());
			try 
			{
				String str = e.getDocument().getText(e.getOffset(), e.getLength());
				int position = e.getOffset();
				ActionsHistoryImpl.action type = ActionsHistoryImpl.action.ADD;
				ActionData data = new ActionData(text.getText().getCaretPosition(),text.getText().getText(), false);
				data = text.getList().newPutString(str,position,type, data, text.getLogin());
				text.setDocText(data.getPanelText());
				text.setIsChanged(true);
			} 
			catch (RemoteException e1) 
			{
				JOptionPane.showMessageDialog(text, "Disconect from server");
			}
			finally
			{
				text.setAction(true);
			}
		} 
		catch (BadLocationException e1) 
		{
			JOptionPane.showMessageDialog(text, "Error! Update your software");
		}
		finally
		{
			text.setAction(true);
		}
		
	}
	public void removeUpdate(DocumentEvent e, SimpleFrame text)
	{
		if(e.getLength() == 1)
		{
			try 
			{
				String str = text.getDocText().substring(e.getOffset(), e.getOffset() + e.getLength());
				int position = e.getOffset();
				ActionsHistoryImpl.action type = ActionsHistoryImpl.action.SUB;
				ActionData data = new ActionData(text.getText().getCaretPosition(),text.getText().getText(), false);
				data = text.getList().newPutString(str, position, type, data, text.getLogin());
				text.setDocText(data.getPanelText());
				text.setIsChanged(true);
			} 
			catch (RemoteException e1) 
			{
				JOptionPane.showMessageDialog(text, "Disconect from server");
			}
			text.setDocText(text.getText().getText());
		}
		else
		{
			try 
			{
				String str = text.getDocText().substring(e.getOffset(), e.getOffset() + e.getLength());
				int position = e.getOffset();
				ActionsHistoryImpl.action type = ActionsHistoryImpl.action.SUB;
				ActionData data = new ActionData(text.getText().getCaretPosition(),text.getText().getText(), false);
				data = text.getList().newPutString(str, position, type, data, text.getLogin());
				text.setDocText(data.getPanelText());
				text.setIsChanged(true);
			} 
			catch (RemoteException e1) 
			{
				JOptionPane.showMessageDialog(text, "Disconect from server");
			}
		}
	}
}
