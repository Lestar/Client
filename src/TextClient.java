import java.net.Socket;
import java.io.*;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;


public class TextClient implements Runnable
{
    final Socket s;  
    final BufferedReader socketReader; 
    final BufferedWriter socketWriter; 
    public String userInput; 
    JEditorPane chat;

    public TextClient(String host, int port, JEditorPane chat) throws IOException 
    {
    	this.chat = chat;
        s = new Socket(host, port); 
        socketReader = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
        socketWriter = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
        userInput = null;
        new Thread(new Receiver(chat)).start();
    }
    public void run()
    {
    	if (s.isClosed()) 
        {
    		close(); 
        } 
    	else 
    	{
            try 
            {
              	socketWriter.write(userInput);
                socketWriter.write("\n"); 
                socketWriter.flush(); 
            } 
            catch (IOException e) 
            {
            	JOptionPane.showMessageDialog(chat, "Disconnect from server");
            }
        }
    	userInput = null;
    }
    public synchronized void close() 
    {
        if (!s.isClosed()) 
        { 
            try
            {
                s.close();
            } 
            catch (IOException ignored) 
            {
                ignored.printStackTrace();
            }
        }
    }

    private class Receiver implements Runnable
    {
    	JEditorPane chat;
        public Receiver(JEditorPane chat) 
        {
			this.chat = chat;
		}

		public void run() 
        {
            while (!s.isClosed()) 
            { 
                String line = null;
                try 
                {
                    line = socketReader.readLine(); 
                } 
                catch (IOException e) 
                { 
                    if ("Socket closed".equals(e.getMessage())) 
                    {
                        break;
                    }
                    JOptionPane.showMessageDialog(chat, this, "Connection lost", 0);
                    close();
                }

                if (line == null) 
                {  
                	JOptionPane.showMessageDialog(chat, this, "Server has closed connection", 0);
                    close(); 
                } 
                else 
                { 
                	chat.setText(chat.getText() + line + "\n");
                }
            }
        }
    }
}

