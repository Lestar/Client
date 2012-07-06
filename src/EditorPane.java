import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JEditorPane;

class EditorPane extends UnicastRemoteObject implements UpdateText, Serializable
{
	private static final long serialVersionUID = 1L;
	private JEditorPane text;
	private SimpleFrame textArea;
	protected EditorPane(SimpleFrame textPanel) throws RemoteException
	{
		textArea = textPanel;
		this.text = textPanel.getText();
	}

	public void UpdateData(String text, int position, int lenght) throws RemoteException 
	{
		int pos = textArea.getText().getCaretPosition();
		textArea.setAction(false);
		this.text.setText(text);
		textArea.setAction(true);
		textArea.setDocText(text);
		if(text.length() < pos)
		{
			textArea.getText().setCaretPosition(text.length());
			return;
		}
		if(position > pos)
		{
			textArea.getText().setCaretPosition(pos);
		}
		else
		{
			textArea.getText().setCaretPosition(pos + lenght);
		}
	}
	public int Ping(int data) throws RemoteException
	{
		return data;
	}
}
