import java.awt.event.KeyEvent;

public class keyEvents implements EditorKeyEventListener
{
	protected SimpleFrame frame;
	keyEvents(SimpleFrame mFrame)
	{
		frame = mFrame;
	}
	public void Pressed(KeyEvent event)
	{
		if(event.getKeyCode() == KeyEvent.VK_N && event.isControlDown())
		{
			new fileEvents().fileNew(frame);
		}
		if(event.getKeyCode() == KeyEvent.VK_O && event.isControlDown())
		{
			new fileEvents().fileOpen(frame);
		}
		if(event.getKeyCode() == KeyEvent.VK_S && event.isControlDown())
		{
			new fileEvents().fileSave(frame);
		}
		if(event.getKeyCode() == KeyEvent.VK_S && event.isControlDown() && event.isShiftDown())
		{
			new fileEvents().fileSaveAs(frame);
		}
		if(event.getKeyCode() == KeyEvent.VK_P && event.isControlDown())
		{
			new fileEvents().printText(frame);
		}
		if(event.getKeyCode() == KeyEvent.VK_Z && event.isControlDown())
		{
			frame.setAction(false);
			new keyActions().undo(frame);
			frame.setAction(true);
		}
		if(event.getKeyCode() == KeyEvent.VK_Y && event.isControlDown())
		{
			frame.setAction(false);
			new keyActions().redo(frame);
			frame.setAction(true);
		}
		
	}
	public void Typed(KeyEvent event)
	{
		
	}
	public void Released(KeyEvent event)
	{
		
	}
}
