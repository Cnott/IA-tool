package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Gui {

	public static void main(String[] args) throws IOException {
		JFrame f=new JFrame();
		f.setPreferredSize(new Dimension(1100,700));
		f.add(new ImpactJTable(),BorderLayout.SOUTH);
		f.setVisible(true);
		f.add(new FileSystemPanel(), BorderLayout.WEST);
		f.add(new JLabel("This is where dependency visualisation should be."), BorderLayout.EAST);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
	}

}
