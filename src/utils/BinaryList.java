package utils;

import java.util.ArrayList;

public class BinaryList {
	
	private ArrayList<String> list = new ArrayList<String>();
	
	public void add(String str) {
		if(list.contains(str)) {
			list.remove(str);
		} else {
			list.add(str);
		}
	}
	
	public ArrayList<String> getList() {
		return list;
	}

}
