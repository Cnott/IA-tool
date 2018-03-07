package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {
	
	public static List<String> executeParse(String selectedFiles) throws FileNotFoundException{
		String fileName=Paths.get(".").toAbsolutePath().normalize().toString();
		fileName+=File.separator+"DataFiles"+File.separator+""
				+"coupling.csv";
		File selectedFile = new File(fileName);
		System.out.println(selectedFile.getAbsolutePath());

		Scanner scanner = new Scanner(selectedFile);
		System.out.println( selectedFiles+","+ selectedFiles+ ",0");
		while (scanner.hasNext()) {
			List<String> line = parseCSCoupling(selectedFiles, CSVUtils.parseLine(scanner.nextLine()));
			if (line != null) {
				for (String s : line) {
					System.out.print(s);
					System.out.print(",");
				}
				System.out.println();
			}
		}
		scanner.close();
		
		
		return null;
	}
	
	public static List<String> parseCSCoupling(String fileName,List<String> input){
		
		List<String> list=new ArrayList<String>();
		String name[]=input.get(0).split("/");
		if(name[name.length-1].matches(fileName)) {
			list.add(name[name.length-1]);
			name=input.get(1).split("/");
			list.add(name[name.length-1]);
			list.add(input.get(2));
		}
		name=input.get(1).split("/");
		if(name[name.length-1].matches(fileName)) {
			list.add(name[name.length-1]);
			name=input.get(0).split("/");
			list.add(name[name.length-1]);
			list.add(input.get(2));
		}
		return list.size() > 0 ? list:null;
	}
}
