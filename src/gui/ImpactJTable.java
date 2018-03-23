package gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.csv.CSVRecord;

import utils.CSVUtils;

public class ImpactJTable extends JTable {
	private static final String[] COL_NAMES = { "File", "Coupled File", "Degree of Coupling", "Cyclomatic Complexity",
			"Test Coverage", "Sum of Coupling", "Long Method", "Many Parameters\r\n" };

	private DefaultTableModel model;

	public ImpactJTable() throws IOException {
		model = new DefaultTableModel(COL_NAMES, 0);
		List<CSVRecord> list = CSVUtils.readResFile();
		for (CSVRecord row : list) {
			Vector<String> rowToAppend = new Vector<String>();
			for (String entry : row) {
				rowToAppend.add(entry);
			}
			model.addRow(rowToAppend);
		}
		model.addColumn("Remove");
		setModel(model);
		centerText();
		// Dont know if this is needed
		// setPreferredSize(new Dimension(1100, 150));

		Action delete = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				ImpactJTable table = (ImpactJTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				model.removeRow(modelRow);
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(this, delete, model.getColumnCount() - 1);
		buttonColumn.setMnemonic(KeyEvent.VK_D);

		setVisible(true);
	}

	private void centerText() {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.LEFT);
		for (int i = 0; i < getColumnCount(); i++) {
			getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}

	public void update() throws IOException {
		model = new DefaultTableModel(COL_NAMES, 0);
		List<CSVRecord> list = CSVUtils.readResFile();
		for (CSVRecord row : list) {
			Vector<String> rowToAppend = new Vector<String>();
			for (String entry : row) {
				rowToAppend.add(entry);
			}
			model.addRow(rowToAppend);
		}
		model.addColumn("Remove");
		setModel(model);
		centerText();
		Action delete = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				ImpactJTable table = (ImpactJTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				model.removeRow(modelRow);
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(this, delete, model.getColumnCount() - 1);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
		

	}

	public DefaultTableModel getModel() {
		return model;
	}

	public ArrayList<String> getDependencyReport() {
		ArrayList<String> list = new ArrayList<String>();
		int row = model.getRowCount(); 
		//int col = model.getColumnCount();
		System.out.println(row);
		for(int i = 0; i < row; i++) {
			list.add(model.getValueAt(i, 0) + "," + model.getValueAt(i, 1));
		}
		return list;
	}

}
