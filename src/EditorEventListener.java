public interface EditorEventListener 
{
	void fileNew(SimpleFrame frame);
	void fileOpen(SimpleFrame frame);
	void fileSave(SimpleFrame frame);
	void fileSaveAs(SimpleFrame frame);
	void exit(SimpleFrame frame);
}