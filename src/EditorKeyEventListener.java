import java.awt.event.KeyEvent;

public interface EditorKeyEventListener
{
	void Pressed(KeyEvent event);
	void Typed(KeyEvent event);
	void Released(KeyEvent event);
}