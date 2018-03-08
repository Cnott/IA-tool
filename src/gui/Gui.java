package gui;

import java.io.IOException;

import javax.swing.JFrame;

public class Gui {

	public static void main(String[] args) throws IOException {
		JFrame f=new JFrame();
		f.add(new ImpactJTable());
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
	}

}
