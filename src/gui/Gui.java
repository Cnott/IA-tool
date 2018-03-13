package gui;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui {

	public static void main(String[] args) throws IOException {
		JFrame f=new JFrame();
		f.add(new ImpactJTable(),BorderLayout.SOUTH);
		f.setVisible(true);
		f.add(new FileSystemPanel(), BorderLayout.WEST);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
	}

}
