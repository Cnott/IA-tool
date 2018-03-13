package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.BinaryList;

public class Gui {
	
	public static void main(String[] args) throws IOException {
		JFrame f=new JFrame();
		f.setPreferredSize(new Dimension(1100,700));
		ImpactJTable impactTable=new ImpactJTable();
		f.add(impactTable,BorderLayout.SOUTH);
		f.setVisible(true);

		BinaryList impactSet=new BinaryList();
		JPanel leftTopPanel=new JPanel();
		leftTopPanel.setLayout(new BoxLayout(leftTopPanel,BoxLayout.Y_AXIS));
		leftTopPanel.add(new FileSystemPanel(impactSet),BorderLayout.CENTER);
		leftTopPanel.add(new DependencyButton(impactSet,impactTable));
		
		f.add(leftTopPanel, BorderLayout.WEST);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
	}

}
