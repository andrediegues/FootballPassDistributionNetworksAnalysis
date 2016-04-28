package parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

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
		String filename = "/home/andre/workspace/IIC/Uefa/games" + i + j + ".txt"; 
		File f = new File(filename);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String s;
		while((s = br.readLine()) != null){
			String path = "/home/andre/workspace/IIC/Uefa/" + s;
			parse(path, s);
		}
		br.close();
	}
	//to do
	public static void parse(String filePath, String filename) throws IOException{
		File file = new File(filePath);
		File file2 = new File("/home/andre/workspace/IIC/ParsedFiles/" + filename);
		BufferedReader br = new BufferedReader(new FileReader(file));
		FileWriter fw = new FileWriter(file2.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		String line;
		int n = 0;
		int max = 0;
		int m = 0;
		boolean b = false;
		boolean hometeam = false;
		int[] playerNumbersH = {0};
		int[] playerNumbersA = {0};
		int[] timePlayedH = {0};
		int[] timePlayedA = {0};
		String[] playerNameH = {""};
		String[] playerNameA = {""};
		int index = 0;
		int index2 = 0;
		int nrH = 0;
		int count = 0;
		LinkedList<String> matrix = new LinkedList<String>();
		while( (line = br.readLine()) != null){
			if(line.equals(""));
			if(isWeekDay(line) && isMonth(line)){
				n = 0;
				String weekday = "";
				weekday = getWeekDay(line);
				int i = line.indexOf(weekday);
				//escrever data para novo ficheiro a partir do index i
				writeToParsedFile(line.substring(i), bw);
			}
			else if(isTeams(line)){
				//escrever para o novo ficheiro a funcao getmatchTeams
				n = 0;
				writeToParsedFile(getTeams(line), bw);
			}
			else if(isScore(line)){
				//escrever o retorno da funcao getScore para o novo ficheiro
				n = 0;
				writeToParsedFile(getScore(line),bw);
			}
			else if(isTeamValues(line)){
				//write team values to new file
				b = true;
				if(n != max){
					//write max and teamMatrix in new file
					m = max;
					int[][] teamMatrix = buildTeam(matrix, max);
					writeToParsedFile(max, bw);
					writeToParsedFile(teamMatrix, max, bw);
					max = n = 0;
				}
				n++;
				if(max < n) max = n;
				matrix.add(line);
			}
			else if(Character.isDigit(line.charAt(0)) && b){
				if(!hometeam || index != 0){
					hometeam = true;
					playerNumbersH = new int[m];
					if(index < m){
						playerNumbersH[index] = (int) line.charAt(0);
						index++;
					}
					if(index == m){ 
						index = 0;	
						b = false;
					}
				}
				else{
					playerNumbersA = new int[m];
					if(index2 < m){
						playerNumbersA[index2] = (int) line.charAt(0);
						index2++;
					}
					if(index2 == m){
						index2 = 0;
						b = false;
						hometeam = false;
					}
				}
			}
			else if(line.contains("'") && line.contains("\"")){
				if(!hometeam || index != 0){
					hometeam = true;
					timePlayedH = new int[m];
					if(index < m){
						timePlayedH[index] = (int) line.charAt(0);
						index++;
					}
					if(index == m){ 
						index = 0;
						nrH = m;
					}
				}
				else{
					timePlayedA = new int[m];
					if(index2 < m){
						timePlayedA[index2] = (int) line.charAt(0);
						index2++;
					}
					if(index2 == m){
						index2 = 0;
						hometeam = false;
					}
				}
			}
			else if(line.equals("From"))
				count++;
			else if(count == 2){
				playerNameH = new String[nrH];
				playerNameA = new String[m];
				if(index < nrH)
					playerNameH[index] = line;
				else if(index2 < m)
					playerNameA[index2] = line;
			}
		}
		writeToParsedFile(nrH, bw);
		for(int i = 0; i < nrH; i++){
			bw.write(playerNumbersH[i] + " ");
			bw.write(playerNameH[i] + " ");
			writeToParsedFile(timePlayedH[i], bw);
		}
		writeToParsedFile(m, bw);
		for(int i = 0; i < m; i++){
			bw.write(playerNumbersA[i] + " ");
			bw.write(playerNameA[i] + " ");
			writeToParsedFile(timePlayedA[i], bw);
		}
		br.close();
		bw.close();
	}
	
	private static void writeToParsedFile(String s, BufferedWriter bw) throws IOException{
		bw.write(s);
		bw.append('\n');
	}
	private static void writeToParsedFile(int n, BufferedWriter bw) throws IOException{
		bw.write(n);
		bw.append('\n');
	}
	private static void writeToParsedFile(int[][] tm, int n, BufferedWriter bw) throws IOException{
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				bw.write(tm[i][j]);
				bw.append(' ');
			}
			bw.append('\n');
		}
	}
	private static int[][] buildTeam(LinkedList<String> matrix, int n) {
		String line;
		int[][] teamMatrix = new int[n][n];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				line = matrix.removeFirst();
				for(int k = 0; k < line.length(); k++){
					if(line.charAt(k) == '-' || i == j)
						teamMatrix[i][j] = 0;
					else if(line.charAt(k) == ' ')
						continue;
					else
					teamMatrix[i][j] = line.charAt(k); 
				}
			}
		}
		return teamMatrix;
	}
	public static boolean isTeamValues(String s){
		if(s.contains("-"))
			return true;
		return false;
	}
	
	public static boolean isTeams(String s) throws IOException{
		File file = new File("/home/andre/workspace/IIC/Uefa/Teams.txt");
		boolean home;
		boolean away;
		home = away = false;
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while((line = br.readLine()) != null){
			if(s.contains(line) && (s.charAt(0) == line.charAt(0))){
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
		File file = new File("/home/andre/workspace/IIC/Uefa/Teams.txt");
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
		return "";
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
		return "";
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
