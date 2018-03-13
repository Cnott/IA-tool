package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import data.FileObject;

public class CSVUtils {

	private static final char DEFAULT_SEPARATOR = ',';
	private static final char DEFAULT_QUOTE = '"';

	public CSVUtils() {

	}

	public static List<String> parseLine(String cvsLine) {
		return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
	}

	public static List<String> parseLine(String cvsLine, char separators) {
		return parseLine(cvsLine, separators, DEFAULT_QUOTE);
	}

	public static List<String> parseLine(String cvsLine, char separators, char customQuote) {
		List<String> result = new ArrayList<>();

		// if empty, return!
		if (cvsLine == null && cvsLine.isEmpty()) {
			return result;
		}

		if (customQuote == ' ') {
			customQuote = DEFAULT_QUOTE;
		}

		if (separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}

		StringBuffer curVal = new StringBuffer();
		boolean inQuotes = false;
		boolean startCollectChar = false;
		boolean doubleQuotesInColumn = false;

		char[] chars = cvsLine.toCharArray();

		for (char ch : chars) {

			if (inQuotes) {
				startCollectChar = true;
				if (ch == customQuote) {
					inQuotes = false;
					doubleQuotesInColumn = false;
				} else {

					// Fixed : allow "" in custom quote enclosed
					if (ch == '\"') {
						if (!doubleQuotesInColumn) {
							curVal.append(ch);
							doubleQuotesInColumn = true;
						}
					} else {
						curVal.append(ch);
					}
				}
			} else {
				if (ch == customQuote) {
					inQuotes = true;
					// Fixed : allow "" in empty quote enclosed
					if (chars[0] != '"' && customQuote == '\"') {
						curVal.append('"');
					}
					// double quotes in column will hit this!
					if (startCollectChar) {
						curVal.append('"');
					}

				} else if (ch == separators) {
					result.add(curVal.toString());
					curVal = new StringBuffer();
					startCollectChar = false;
				} else if (ch == '\r') {
					// ignore LF characters
					continue;
				} else if (ch == '\n') {
					// the end, break!
					break;
				} else {
					curVal.append(ch);
				}
			}

		}

		result.add(curVal.toString());

		return result;
	}

	public static void write(ArrayList<FileObject> list) throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("result" + File.separator + "res.csv"));
		writer.append("file,coupledFile,doc,cyclomatic complexity,code coverage,soc,long method,manyParameters\n");
		for (FileObject f : list) {
			writer.append(f.toString() + "\n");
		}
		writer.close();
	}
	
	public static void writeIAList(ArrayList<String> list) throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("result" + File.separator + "IAset.csv"));
		for (String f : list) {
			writer.append(f + "\n");
		}
		writer.close();
	}
	
	
	public static List<CSVRecord> readResFile() throws IOException {
		String csvFileInput = Paths.get(".").toAbsolutePath().normalize().toString();
		csvFileInput += File.separator + "result" + File.separator + "" + "res.csv";
		
		File f = new File(csvFileInput);
		if(!(f.exists() && !f.isDirectory())) { 
			PrintWriter writer = new PrintWriter(csvFileInput, "UTF-8");
		}
		
		
		Reader in = new FileReader( csvFileInput );
	    CSVParser parser = new CSVParser( in, CSVFormat.DEFAULT );
	    List<CSVRecord> list = parser.getRecords();
	    return list;
	}

}
