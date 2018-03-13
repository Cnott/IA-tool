package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;

import utils.BinaryList;
import utils.CSVUtils;
import utils.Parser;

public class DependencyButton extends JButton implements ActionListener {
	private BinaryList impactSet;
	ImpactJTable impactTable;
	
	public DependencyButton(BinaryList impactSet, ImpactJTable impactTable) {
		super("Show dependencies");
		this.impactSet=impactSet;
		this.impactTable=impactTable;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ArrayList<String> listToSearch=impactSet.getList();
		
		try {
			Parser.executeParse(listToSearch);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			CSVUtils.readResFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			impactTable.update();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
