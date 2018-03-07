package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import data.FileObject;

public class Parser {
	
	public static List<String> executeParse(String searchedFiles) throws FileNotFoundException{
		ArrayList<FileObject> coupledList=new ArrayList<FileObject>();
		parseCSCoupling(searchedFiles, coupledList);
		for(FileObject f : coupledList) {
			f.print();
		}
		
		return null;
	}
	
	public static void  parseCSCoupling(String searchedFile,List<FileObject> coupledList) throws FileNotFoundException{
		//Adding the search object to the list since it's connected to itself.
		coupledList.add(new FileObject(searchedFile, searchedFile,"0"));
		
		//Fetching from coupling.csv inside DataFiles folder
		String fileName=Paths.get(".").toAbsolutePath().normalize().toString();
		fileName+=File.separator+"DataFiles"+File.separator+""
				+"coupling.csv";
		File selectedFile = new File(fileName);
		System.out.println(selectedFile.getAbsolutePath());
		Scanner scanner = new Scanner(selectedFile);
		//reading each line of the csv file one line at a time
		while (scanner.hasNext()) {
			List<String> line =CSVUtils.parseLine(scanner.nextLine());
			String name[]=line.get(0).split("/");
			String fileToAdd="";
			String coupledFileToAdd="";
			String degOfCoup="";
			//Looking if the searched file matches any entry in the left column
			if(name[name.length-1].matches(searchedFile)) {
				fileToAdd=(name[name.length-1]);
				name=line.get(1).split("/");
				coupledFileToAdd=(name[name.length-1]);
				degOfCoup=(line.get(2));
			}
			//Looking if the searched file matches any entry in the right column
			name=line.get(1).split("/");
			if(name[name.length-1].matches(searchedFile)) {
				fileToAdd=(name[name.length-1]);
				name=line.get(0).split("/");
				coupledFileToAdd=(name[name.length-1]);
				degOfCoup=(line.get(2));
			}
			//If we found a match we add it to the list
			if(!fileToAdd.matches("")) {
				coupledList.add(new FileObject(fileToAdd,coupledFileToAdd,degOfCoup));
			}
		}
		scanner.close();
	}
}
