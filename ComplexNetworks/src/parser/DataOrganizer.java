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
		String filename = "games" + i + j + ".txt"; 
		String path = "/home/andre/workspace/IIC/Uefa/" + filename;
		parse(path, filename);
		
	}
	//to do
	public static void parse(String filePath, String filename) throws IOException{
		File file = new File(filePath);
		File file2 = new File("/home/andre/workspace/IIC/ParsedFiles/" + filename);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while( (line = br.readLine()) != null){
			if(isWeekDay(line) && isMonth(line)){
				String weekday = getWeekDay(line);
				int i = line.indexOf(weekday);
				//escrever data para novo ficheiro a partir do index i
			}
			else if(isTeams(line))
				//escrever para o novo ficheiro a funcao getmatchTeams
				getTeams(line);
			else if(isScore(line)){
				//escrever o retorno da funcao getScore para o novo ficheiro
				getScore(line);
			}/*
			else if(isTeamValues(line)){
				//write team values to new file
				getTeamValues(line);
			}*/
		}
		br.close();
	}
	
	public static boolean isTeams(String s) throws IOException{
		File file = new File("/home/andre/IIC/Uefa/Teams.txt");
		boolean home;
		boolean away;
		home = away = false;
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while((line = br.readLine()) != null){
			if(s.contains(line) && s.charAt(0) == line.charAt(0)){
				home = true;
			}
			else if(s.contains(line)){
				away = true;
			}
		}
		br.close();
		return home && away;
	}
	
	public static String getTeams(String s) throws IOException{
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
	
	public static boolean isScore(String s){
		if(Character.isDigit(s.charAt(0)) && Character.isDigit(s.charAt(4)) && s.length() == 4){
			return true;
		}
		return false;
	}
	
	public static String getScore(String s){
		char c1 = s.charAt(0);
		char c2 = s.charAt(4);
		if(Character.isDigit(c1) && Character.isDigit(c2))
			return c1 + "\n" + c2;
		return null;
	}
	
	public static boolean isWeekDay(String s){
		return (s.contains("Monday")  || 
				s.contains("Tuesday") || 
				s.contains("Wednesday") || 
				s.contains("Thursday") || 
				s.contains("Friday") || 
				s.contains("Saturday") ||
				s.contains("Sunday"));
	}
	public static String getWeekDay(String s){
		if(s.equals("Monday"))
			return "Monday";
		else if(s.equals("Tuesday"))
			return "Tuesday";
		else if(s.equals("Wednesday"))
			return "Wednesday";
		else if(s.equals("Thursday"))
			return "Thursday";
		else if(s.equals("Friday"))
			return "Friday";
		else if(s.equals("Saturday"))
			return "Saturday";
		else if(s.equals("Sunday"))
			return "Sunday";
		return null;
	}
	
	public static boolean isMonth(String s){
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
