package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import utils.CSVUtils;

public class PrintReportButton extends JButton implements ActionListener {

	private ImpactJTable impactTable;
	private JFrame frame;

	public PrintReportButton(JFrame frame, ImpactJTable impactTable) {
		super("Create Report");
		this.impactTable = impactTable;
		this.frame = frame;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] options = { "Cancel", "Include Paramets", "Only impacted classes" };
		int choice = JOptionPane.showOptionDialog(frame, "Do you want a simple or detailed report?", "Report Dialog",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.DEFAULT_OPTION, null, options, null);

		try {
			if (choice == 2) {
				CSVUtils.writeReport(impactTable.getDependencyReport(2));
				JOptionPane.showMessageDialog(frame,
					    "Report created in result folder.");
			}
			if (choice == 1) {
				CSVUtils.writeReport(impactTable.getDependencyReport(impactTable.getModel().getColumnCount()-1));
				JOptionPane.showMessageDialog(frame,
					    "Report created in result folder.");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
