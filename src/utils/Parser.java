package utils;

import java.util.ArrayList;
import java.util.List;

public class Parser {
	
	public static List<String> executeParse(List<String> fileNames){
		
		return fileNames;
	}
	
	public static List<String> parseCSCoupling(String fileName, List<String> input){
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
