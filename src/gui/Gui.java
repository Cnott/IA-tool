package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import utils.BinaryList;

public class Gui {
	
	public static void main(String[] args) throws IOException {
		JFrame f=new JFrame("Impact Analysis and parameters indicating Technical Debt");
		f.setPreferredSize(new Dimension(1300,750));
		
		ImpactJTable impactTable = new ImpactJTable();
		JPanel impactPanel = new JPanel();
		
		impactPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JScrollPane scroll = new JScrollPane(impactTable);
		scroll.setPreferredSize(new Dimension(1300, 250));

		impactPanel.add(scroll);
		f.add(impactPanel, BorderLayout.SOUTH);
		
		BinaryList impactSet=new BinaryList();
		JPanel leftTopPanel=new JPanel();
		leftTopPanel.setLayout(new BoxLayout(leftTopPanel,BoxLayout.Y_AXIS));
		leftTopPanel.add(new FileSystemPanel(impactSet),BorderLayout.CENTER);
		leftTopPanel.add(new DependencyButton(impactSet,impactTable));
				
		
		f.add(leftTopPanel, BorderLayout.WEST);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
