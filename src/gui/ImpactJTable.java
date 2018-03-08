package gui;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.csv.CSVRecord;

import utils.CSVUtils;


public class ImpactJTable extends JTable {
		private static final String[] COL_NAMES = { "file","coupledFile","doc","cyclomatic complexity"
				 									,"code coverage","soc","long method","manyParameters\r\n"};
		
		public ImpactJTable() throws IOException {
			DefaultTableModel model=new DefaultTableModel(COL_NAMES,0);
			List<CSVRecord> list=CSVUtils.readResFile();
	        for( CSVRecord row : list ) {
	        	Vector<String> rowToAppend=new Vector<String>();
	            for( String entry : row ) {
	                rowToAppend.add(entry);
	            }
	            model.addRow(rowToAppend);
	        }
	        setModel(model);
	        
	        //Dont know if this is needed
	        setVisible(true);
		}
}
