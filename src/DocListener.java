import javax.swing.event.DocumentEvent;

public interface DocListener
{
	void changedUpdate(DocumentEvent e, SimpleFrame text);
	void insertUpdate(DocumentEvent e, SimpleFrame text);
	void removeUpdate(DocumentEvent e, SimpleFrame text);
}