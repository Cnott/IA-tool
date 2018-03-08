package parsing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import utils.CSVUtils;
import utils.Parser;

public class CplxParsing {

	public CplxParsing() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		ArrayList<String> listToSearch=new ArrayList<String>();
		listToSearch.add("GitBridge.java");
		Parser.executeParse(listToSearch);
		CSVUtils.readResFile();
	}
}
