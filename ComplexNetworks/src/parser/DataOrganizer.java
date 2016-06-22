package parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class DataOrganizer {
	public static void main(String[] args) throws IOException{
		/*int nMin = 2013;
		int nMax = 2017;
		int mMin = -2;
		int mMax = 14;
		for(int i = nMin; i < nMax; i++){
			for(int j = mMin; j < mMax; j++){
				if(j == 0) continue;
				getFilesPath(i, j);
			}	
		}*/
		getFilesPath(2013,1);
	}
	public static void getFilesPath(int i, int j) throws IOException{
	//	String filename = "/home/andre/workspace/IIC/Uefa/games" + i + j + ".txt";
		String filename = "/home/andre/workspace/IIC/ParsedFiles/failedteams.txt";
		File f = new File(filename);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String s;// = "2009499_tpd.txt";
		while((s = br.readLine()) != null){
			String path = "/home/andre/workspace/IIC/Uefa/" + s;
			//String path = "/home/andre/workspace/IIC/Uefa/2009499_tpd.txt";
			System.out.println("parsing " + s);
			parse(path, s);
		}
		br.close();
	}
	//to do
	public static void parse(String filePath, String filename) throws IOException{
		File file = new File(filePath);
		//File file2 = new File("/home/andre/workspace/IIC/ParsedFiles/" + filename);
		File file2 = new File("/home/andre/workspace/IIC/ParsedFiles/2011870_tpd.txt");
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
		String[] timePlayedH = {""};
		String[] timePlayedA = {""};
		String[] playerNameH = {""};
		String[] playerNameA = {""};
		int index = 0;
		int index2 = 0;
		int indexT = 0;
		int indexP = 0;
		int nrH = 0;
		int count = 0;
		LinkedList<String> matrix = new LinkedList<String>();
		while( (line = br.readLine()) != null){
			if(line.isEmpty())
				continue;
			//System.out.println("# " + line);
			if(isWeekDay(line) && isMonth(line)){

				//System.out.println("#date " + line);
				n = 0;
				String weekday = "";
				weekday = getWeekDay(line);
				int i = line.indexOf(weekday);
				//escrever data para novo ficheiro a partir do index i
				writeToParsedFile(line.substring(i), bw);
			}
			else if(isTeams(line)){
				//System.out.println("#teams " + line);
				//escrever para o novo ficheiro a funcao getmatchTeams
				n = 0;
				writeToParsedFile(getTeams(line), bw);
			}
			else if(isScore(line)){
				//System.out.println("#score " + line);
				//escrever o retorno da funcao getScore para o novo ficheiro
				n = 0;
				writeToParsedFile(getScore(line),bw);
			}
			else if(isTeamValues(line)){
				//System.out.println("# matriz" + line);
				//write team values to new file
				b = true;
				matrix.add(line);
			}
			else if(!isTeamValues(line) && !matrix.isEmpty()){
				//System.out.println("# no matriz" + line);
				Integer size = matrix.size();
				bw.append(size.toString());
				if(nrH == 0)
					nrH = size;
				else m = size;
				bw.append('\n');
				int[][] teamMatrix = new int[size][size];
				for(int i = 0; i < size; i++){
					String s = matrix.removeFirst();
					Scanner in = new Scanner(s);
					for(int j = 0; j < size; j++) {
						if (i == j) continue;
						String aux = in.next();
						if(aux.equals("-"))
							teamMatrix[i][j] = 0;
						else
							teamMatrix[i][j] = Integer.parseInt(aux);
					}
					in.close();
				}	
				 
				for(int i = 0; i < size; i++){
					for(int j = 0; j < size; j++)
						bw.append(teamMatrix[i][j] + " ");
					bw.append('\n');				
				}
				writeToParsedFile(line, bw);
			}
			else if(isNumeric(line) && b){
				n = 0;
				//System.out.println("# " + line);
				if(!hometeam || index != 0){
					hometeam = true;
					playerNumbersH = new int[nrH];
					if(index < nrH - 1){
						playerNumbersH[index] = Integer.parseInt(line);
						writeToParsedFile(line, bw);
						index++;
					}
					if(index == nrH - 1){ 
						index = 0;	
						b = false;
					}
				}
				else{
					//System.out.println("## " + line);
					playerNumbersA = new int[m];
					if(index2 < m - 1){
						playerNumbersA[index2] = Integer.parseInt(line);
						writeToParsedFile(line, bw);
						index2++;
					}
					if(index2 == m){
						index2 = 0;
						b = false;
						hometeam = false;
					}
				}
			}
			else if(line.contains("\"")){
				n = 0;	
				//System.out.println("# " + line);
				timePlayedH = new String[nrH];
				timePlayedA = new String[m];
				if(indexT < nrH){
					timePlayedH[indexT] = line;					
					writeToParsedFile(line, bw);
					//System.out.println("#" + indexT + " " + timePlayedH[indexT]);
					indexT++;
				}
				else{
					writeToParsedFile(line, bw);
					//System.out.println("* " + indexP + " "+ timePlayedA[indexP]);
					indexP++;
				}
				
			}
			else if(line.equals("From")){
				n = 0;
				count++;
			}
			else if(count == 2){
				n = 0;
				playerNameH = new String[nrH];
				playerNameA = new String[m];
				if(index < nrH)
					writeToParsedFile(line, bw);
				else if(index2 < m)	{		
					writeToParsedFile(line, bw);
				}
			}
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
				if(!matrix.isEmpty())
					line = matrix.removeFirst();
				else continue;
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
		Scanner in = new Scanner(s);
		String c;
		int i = 0;
		while(in.hasNext()){
			c = in.next();

			//System.out.println(c);
			i++;
			if(!isNumeric(c) && !c.equals("-")){
				return false;
			}
		}
		if(i > 9 && i < 14){
			//System.out.println("entrei " + s);
			return true;
		}
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
			//System.out.println("|" + s + "|" + line + "|" + s.contains(line));
			if(s.contains(line) && (s.charAt(0) == line.charAt(0) && !home)){
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
			if(s.contains(line) && s.indexOf(line) == 0){
				home = line;
				System.out.println("|" + s + "|" + home + "|");

			}
			else if(s.contains(line)){
				away = line;
				System.out.println("|" + s + "|" + away + "|");
			}
		}
		br.close();
		return home + "\n" + away;
	}
	
	public static boolean isScore(String s){
		//System.out.println("isScore |"+ s + "|");
		if(s.length() == 5 && Character.isDigit(s.charAt(0)) && Character.isDigit(s.charAt(4)) ){
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
		if(s.contains("Monday"))
			return "Monday";
		else if(s.contains("Tuesday"))
			return "Tuesday";
		else if(s.contains("Wednesday"))
			return "Wednesday";
		else if(s.contains("Thursday"))
			return "Thursday";
		else if(s.contains("Friday"))
			return "Friday";
		else if(s.contains("Saturday"))
			return "Saturday";
		else if(s.contains("Sunday"))
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
	public static boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	}
	
}
