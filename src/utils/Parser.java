package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import data.FileObject;

public class Parser {

	public static List<String> executeParse(List<String> searchedFiles) throws FileNotFoundException {
		ArrayList<FileObject> coupledList = new ArrayList<FileObject>();
		for (String searchFile : searchedFiles) {
			parseCSCoupling(searchFile, coupledList);
			for (FileObject f : coupledList) {
				String searchedFile = f.getCoupledFileName().split("\\.")[0];
				parseCSCycloCompAndLongMeth(searchedFile, f);
				parseCodeCover(searchedFile, f);
				parseSOC(f.getCoupledFileName(), f);
				f.print();
			}
		}
		return null;
	}

	private static void parseCSCoupling(String searchedFile, List<FileObject> coupledList)
			throws FileNotFoundException {
		// Adding the search object to the list since it's connected to itself.
		coupledList.add(new FileObject(searchedFile, searchedFile, "100%"));

		// Fetching from coupling.csv inside DataFiles folder
		String fileName = Paths.get(".").toAbsolutePath().normalize().toString();
		fileName += File.separator + "DataFiles" + File.separator + "" + "coupling.csv";
		File selectedFile = new File(fileName);
		Scanner scanner = new Scanner(selectedFile);
		// reading each line of the csv file one line at a time
		while (scanner.hasNext()) {
			List<String> line = CSVUtils.parseLine(scanner.nextLine());
			String name[] = line.get(0).split("/");
			String fileToAdd = "";
			String coupledFileToAdd = "";
			String degOfCoup = "";
			// Looking if the searched file matches any entry in the left column
			if (name[name.length - 1].matches(searchedFile)) {
				fileToAdd = (name[name.length - 1]);
				name = line.get(1).split("/");
				coupledFileToAdd = (name[name.length - 1]);
				degOfCoup = (line.get(2))+"%";
			}
			// Looking if the searched file matches any entry in the right column
			name = line.get(1).split("/");
			if (name[name.length - 1].matches(searchedFile)) {
				fileToAdd = (name[name.length - 1]);
				name = line.get(0).split("/");
				coupledFileToAdd = (name[name.length - 1]);
				degOfCoup = (line.get(2))+"%";
			}
			// If we found a match we add it to the list
			if (!fileToAdd.matches("")) {
				coupledList.add(new FileObject(fileToAdd, coupledFileToAdd, degOfCoup));
			}
		}
		scanner.close();
	}

	private static void parseCSCycloCompAndLongMeth(String fileName, FileObject obj) throws FileNotFoundException {
		String filePath = Paths.get(".").toAbsolutePath().normalize().toString();
		filePath += File.separator + "DataFiles" + File.separator + "" + fileName + ".csv";
		File selectedFile = new File(filePath);
		Scanner scanner = new Scanner(selectedFile);
		int sum = 0;
		int maxFunctionSize=0;
		scanner.nextLine();
		while (scanner.hasNext()) {
			List<String> line = CSVUtils.parseLine(scanner.nextLine());
			sum += Integer.valueOf(line.get(3));
			maxFunctionSize=Math.max(maxFunctionSize, Integer.valueOf(line.get(2)));
		}
		scanner.close();
		obj.setCycloComplex(String.valueOf(sum));
		obj.setLongMethod(String.valueOf(maxFunctionSize));
	}

	private static void parseCodeCover(String fileName, FileObject obj) throws FileNotFoundException {
		String filePath = Paths.get(".").toAbsolutePath().normalize().toString();
		filePath += File.separator + "DataFiles" + File.separator + "" + "coverage.csv";
		File selectedFile = new File(filePath);
		Scanner scanner = new Scanner(selectedFile);
		int covSum = 0;
		int totalSum = 0;
		scanner.nextLine();
		while (scanner.hasNext()) {
			List<String> line = CSVUtils.parseLine(scanner.nextLine());
			String file = line.get(2);
			if (file.contains("\\.")) {
				file = file.split("\\.")[0];
			}
			if (file.matches(fileName)) {
				covSum += Integer.valueOf(line.get(4));
				totalSum += Integer.valueOf(line.get(4)) + Integer.valueOf(line.get(3));
			}
		}
		scanner.close();
		obj.setCodeCover(String.valueOf(100 * covSum / totalSum) + "%");
	}

	private static void parseSOC(String fileName, FileObject obj) throws FileNotFoundException {
		String filePath = Paths.get(".").toAbsolutePath().normalize().toString();
		filePath += File.separator + "DataFiles" + File.separator + "" + "soc.csv";
		File selectedFile = new File(filePath);
		Scanner scanner = new Scanner(selectedFile);
		int sum = 0;
		// scanner.nextLine();
		while (scanner.hasNext()) {
			List<String> line = CSVUtils.parseLine(scanner.nextLine());
			String file[] = line.get(0).split("\\/");
			if (file[file.length - 1].matches(fileName)) {
				sum += Integer.valueOf(line.get(1));
			}
		}
		scanner.close();
		obj.setSumOfCoupl(String.valueOf(sum));
	}

}
