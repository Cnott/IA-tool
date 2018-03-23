package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

import utils.CSVUtils;

public class PrintReportButton extends JButton implements ActionListener{

	private ImpactJTable impactTable;

	public PrintReportButton(ImpactJTable impactTable) {
		super("Create Report");
		this.impactTable=impactTable;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			CSVUtils.writeReport(impactTable.getDependencyReport());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
