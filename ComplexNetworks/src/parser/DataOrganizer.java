package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class DataOrganizer {
	public static void main(String[] args) throws IOException{
		int nMin = 2013;
		int nMax = 2017;
		int mMin = -2;
		int mMax = 14;
		for(int i = nMin; i < nMax; i++){
			for(int j = mMin; j < mMax; j++){
				if(j == 0) continue;
				getFilesPath(i, j);
			}
		}
	}
	public static void getFilesPath(int i, int j) throws IOException{
		String path = "/home/andre/workspace/IIC/Uefa/games" + i + j + ".txt";
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while((line = br.readLine()) != null){
			parse(path + "/" + line);
		}
		br.close();
	}
	//to do
	public static void parse(String filePath){
		File file = new File(filePath);
		
	}
	
	public String getMatchTeams(String s) throws IOException{
		File file = new File("/home/andre/IIC/Uefa/Teams.txt");
		String home;
		String away;
		home = away = null;
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while((line = br.readLine()) != null){
			if(s.contains(line) && s.charAt(0) == line.charAt(0)){
				home = line;
			}
			else if(s.contains(line)){
				away = line;
			}
		}
		br.close();
		return home + "\n" + away;
	}
	
	public boolean isScore(String s){
		if(Character.isDigit(s.charAt(0)) && Character.isDigit(s.charAt(4)) && s.length() == 4){
			return true;
		}
		return false;
	}
	
	public boolean isWeekDay(String s){
		return (s.contains("Monday")  || 
				s.contains("Tuesday") || 
				s.contains("Wednesday") || 
				s.contains("Thursday") || 
				s.contains("Friday") || 
				s.contains("Saturday") ||
				s.contains("Sunday"));
	}
	
	public boolean isMonth(String s){
		return (s.contains("January") || 
				s.contains("February") || 
				s.contains("March") || 
				s.contains("April") || 
				s.contains("May") || 
				s.contains("June") || 
				s.contains("July") || 
				s.contains("August") || 
				s.contains("September") ||
				s.contains("October") ||
				s.contains("November") ||
				s.contains("December"));		
	}
}
