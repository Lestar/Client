import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class fileEvents implements EditorEventListener
{
	public void fileNew(SimpleFrame frame) 
	{
		frame.setAction(false);
		frame.setIsFileNameSetted(false);
		frame.setTitle("Text Editor");
		JFileChooser chooser = new JFileChooser();
		
		if(frame.getIsChanged()) 
		{
			int selection = JOptionPane.showConfirmDialog(null,"Do you want save document?", "Warrning", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
			
			if(selection == JOptionPane.NO_OPTION)	
			{
				frame.getText().setText("");
				frame.setIsChanged(false);
				frame.setTitle("Text Editor");
			}
			
			if(selection == JOptionPane.YES_OPTION)
			{
				chooser.setCurrentDirectory(new File("."));
				
				int result = chooser.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					frame.fileName = chooser.getSelectedFile().getPath();
					PrintWriter out;
					try 
					{
						out = new PrintWriter(new FileWriter(frame.fileName));
						String str = frame.getText().getText();
						out.print(str);
						out.close();
					} 
					catch (IOException e) 
					{
						JOptionPane.showMessageDialog(frame, "Saving error");
					}
				}
				frame.getText().setText("");
				frame.setIsChanged(false);
				frame.setTitle("Text Editor");
			}
		}
		else
		{
			frame.getText().setText("");
		}
		frame.setAction(true);
	}
	public void fileOpen(SimpleFrame frame) 
	{
		frame.setAction(false);
		JFileChooser chooser = new JFileChooser();
		
		chooser.setCurrentDirectory(new File("."));
			
		if(frame.getIsChanged())
		{
			int selection = JOptionPane.showConfirmDialog(null,"Do you want save document?", "Warrning", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
			
			if(selection == JOptionPane.NO_OPTION)	
			{
				int result = chooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					frame.fileName = chooser.getSelectedFile().getPath();
					BufferedReader in;
					String buff;
					String str = "";
					try 
					{
						in = new BufferedReader(new FileReader(frame.fileName));
						while ((buff = in.readLine()) != null)
						{
							str += buff;
							str+= "\r\n";
						}
						frame.getText().setText(str);
					} 
					catch (FileNotFoundException e) 
					{
						JOptionPane.showMessageDialog(frame, "File not found");
					} 
					catch (IOException e) 
					{
						JOptionPane.showMessageDialog(frame, "Opening error");
					}
				}
			}
			if(selection == JOptionPane.YES_OPTION)
			{
				int result = chooser.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					String name = chooser.getSelectedFile().getPath();
					PrintWriter out;
					try 
					{
						out = new PrintWriter(new FileWriter(name));
						String str = frame.getText().getText();
						out.print(str);
						out.close();
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
					
				result = chooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					String name = chooser.getSelectedFile().getPath();
					BufferedReader in;
					String buff;
					String str = "";
					try 
					{
						in = new BufferedReader(new FileReader(name));
						while ((buff = in.readLine()) != null)
						{
							str += buff;
							str+= "\r\n";
						}
						frame.getText().setText(str);
					} 
					catch (FileNotFoundException e) 
					{
						JOptionPane.showMessageDialog(frame, "File not found");
					} 
					catch (IOException e) 
					{
						JOptionPane.showMessageDialog(frame, "Opening error");
					}
				}
			}
		}
		else
		{
			int result = chooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION)
			{
				String name = chooser.getSelectedFile().getPath();
				BufferedReader in;
				String buff;
				String str = "";
				try 
				{
					in = new BufferedReader(new FileReader(name));
					while ((buff = in.readLine()) != null)
					{
						str += buff;
						str+= "\r\n";
					}
					frame.getText().setText(str);
				} 
				catch (FileNotFoundException e) 
				{
					JOptionPane.showMessageDialog(frame, "File not found");
				} 
				catch (IOException e) 
				{
					JOptionPane.showMessageDialog(frame, "Opening error");
				}
			}
		}
		frame.setIsFileNameSetted(true);
		frame.setTitle("Text Editor" + frame.fileName);
		frame.setAction(true);
	}
	public void fileSave(SimpleFrame frame)
	{
		File tempFile = new File(frame.tempFileName);
		if(tempFile.exists()) tempFile.delete();
		if(!frame.getIsFileNameSetted())
		{
			JFileChooser chooser = new JFileChooser();
			
			chooser.setCurrentDirectory(new File("."));
				
			int result = chooser.showSaveDialog(null);
			if (result == JFileChooser.APPROVE_OPTION)
			{
				frame.fileName = chooser.getSelectedFile().getPath();
				PrintWriter out;
				try 
				{
					out = new PrintWriter(new FileWriter(frame.fileName));
					String str = frame.getText().getText();
					out.print(str);
					out.close();
					frame.setIsChanged(false);
				} 
				catch (IOException e) 
				{
					JOptionPane.showMessageDialog(frame, "Saving error");
				}
			}
		}
		else
		{
			PrintWriter out;
			try 
			{
				out = new PrintWriter(new FileWriter(frame.fileName));
				String str = frame.getText().getText();
				out.print(str);
				out.close();
			} 
			catch (IOException e) 
			{
				JOptionPane.showMessageDialog(frame, "Saving error");
			}
		}
		frame.setIsFileNameSetted(true);
		frame.setTitle("Text Editor - " + frame.fileName);
	}
	public void fileSaveAs(SimpleFrame frame) 
	{
		File tempFile = new File(frame.tempFileName);
		if(tempFile.exists()) tempFile.delete();
		JFileChooser chooser = new JFileChooser();
		
		chooser.setCurrentDirectory(new File("."));
			
		int result = chooser.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			frame.fileName = chooser.getSelectedFile().getPath();
			PrintWriter out;
			try 
			{
				out = new PrintWriter(new FileWriter(frame.fileName));
				String str = frame.getText().getText();
				out.print(str);
				out.close();
				frame.setIsChanged(false);
			} 
			catch (IOException e) 
			{
				JOptionPane.showMessageDialog(frame, "Saving error");
			}
		}
		frame.setIsFileNameSetted(true);
		frame.setTitle("Text Editor - " + frame.fileName);
	}
	public void printText(SimpleFrame frame)
	{
		try 
		{
			frame.getText().print();
		} 
		catch (PrinterException e) 
		{
			e.printStackTrace();
		}
	}
	public void exit(SimpleFrame frame) 
	{
		File tempFile = new File(frame.fileName);
		if(tempFile.exists()) tempFile.delete();
		JFileChooser chooser = new JFileChooser();
		
		chooser.setCurrentDirectory(new File("."));
			
		if(frame.getIsChanged()) 
		{
			int selection = JOptionPane.showConfirmDialog(null,"Do you want save document?", "Warrning", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
			
			if(selection == JOptionPane.NO_OPTION)	
			{
				try 
				{
					frame.getClient().close();
					frame.getPtr().Disconnect(frame.getProjName(), frame.getLogin());
				} 
				catch (RemoteException e1) 
				{
					JOptionPane.showMessageDialog(frame, "Disconect from Server");
					return;
				}
				System.exit(0);
			}
				
			if(selection == JOptionPane.YES_OPTION)
			{
				chooser.setCurrentDirectory(new File("."));
					
				int result = chooser.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					frame.fileName = chooser.getSelectedFile().getPath();
					PrintWriter out;
					try 
					{
						out = new PrintWriter(new FileWriter(frame.fileName));
						String str = frame.getText().getText();
						out.print(str);
						out.close();
					} 
					catch (IOException e) 
					{
						JOptionPane.showMessageDialog(frame, "Saving error");
					}
				}
				else
				{
					return;
				}
			}
		}
		else
		{
			try 
			{
				frame.getClient().close();
				frame.getPtr().Disconnect(frame.getProjName(), frame.getLogin());
			} 
			catch (RemoteException e1) 
			{
				JOptionPane.showMessageDialog(frame, "Disconect from Server");
				return;
			}
			System.exit(0);
		}
			
	}
}
