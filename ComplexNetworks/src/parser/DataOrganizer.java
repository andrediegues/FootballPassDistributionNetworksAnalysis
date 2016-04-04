package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class DataOrganizer {
	public static void main(String[] args) throws IOException{
		File file = new File("/home/andre/Documents/Uefa/2015763_tpd.txt");
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String line = br.readLine();
	}
	
}
