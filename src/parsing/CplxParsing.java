package parsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import utils.CSVUtils;

public class CplxParsing {

	public CplxParsing() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws FileNotFoundException {

		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);
		//int returnValue = jfc.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			//System.out.println(selectedFile.getAbsolutePath());

			Scanner scanner = new Scanner(selectedFile);
			while (scanner.hasNext()) {
				List<String> line = CSVUtils.parseLine(scanner.nextLine());

				for (String s : line) {
					System.out.print(s);
					System.out.print(" , ");
				}
				System.out.println();
			}
			scanner.close();
		}
	}
}
