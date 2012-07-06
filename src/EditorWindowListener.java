import java.awt.event.WindowEvent;

public interface EditorWindowListener
{
	void Activated(WindowEvent e) ;
	void Closed(WindowEvent e) ;
	void Closing(WindowEvent e) ;
	void Deactivated(WindowEvent e) ;
	void Deiconified(WindowEvent e) ;
	void Iconified(WindowEvent e) ;
	void Opened(WindowEvent e) ;
}