package interpretation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GetData {
	public static void main(String[] args) throws IOException {
		int n_min = 2013;
		int n_max = 2017;
		int m_min = -2;
		int m_max = 14;
		File file;
		File file2;
		String line;
		BufferedReader br;
		for(int i = n_min; i < n_max; i++){
			Season s = new Season(i);
			for(int j = m_min; j < m_max; j++){
				if(j == 0) continue;
				file = new File("/home/andre/workspace/IIC/Uefa/games" + i + j +".txt");
				br = new BufferedReader(new FileReader(file));
				while((line = br.readLine()) != null){
					file2 = new File("/home/andre/workspace/IIC/ParsedFiles/" + line);
					collectData(file2, s);
				}
				
			}
			parseToTableNotation(s);
			parseToGML(s);
		}
	}

	
	private static void collectData(File file, Season s) throws FileNotFoundException {
		Scanner in = new Scanner(file);
		
		Match match;
		if(!in.hasNext()){
			in.close();
			return;
		}
		MatchDate date = new MatchDate(in.next(), in.nextInt(), in.next(), in.nextInt());
		in.nextLine();
		Team ht = null;
		Team at = null;
		String homeTeamName = in.nextLine();
		String awayTeamName = in.nextLine();
		boolean homeTeamExists = false;
		boolean awayTeamExists = false;
		if(Character.isDigit(homeTeamName.charAt(0)) || Character.isDigit(awayTeamName.charAt(0))){
			in.close();
			return;
		}
		for(int i = 0; i < s.teams.size(); i++){
			if(s.teams.get(i).name.equals(homeTeamName)){
				ht = s.teams.get(i);
				homeTeamExists = true;
			}
			if(s.teams.get(i).name.equals(awayTeamName)){
				at = s.teams.get(i);
				awayTeamExists = true;
			}
		}
		if(!homeTeamExists){
			ht = new Team(homeTeamName, s);
			s.addTeamToSeason(ht);
		}
		if(!awayTeamExists){
			at = new Team(awayTeamName, s);
			s.addTeamToSeason(at);
		}
		
		MatchScore matchScore;
		int homeScore = in.nextInt();
		int awayScore = in.nextInt();
		matchScore = new MatchScore(homeScore, awayScore);
	
		//home team
		
		int numberOfPlayersHomeTeam = in.nextInt();
		int[][] matrixOfPassesHomeTeam = new int[numberOfPlayersHomeTeam][numberOfPlayersHomeTeam];
		for(int i = 0; i < numberOfPlayersHomeTeam; i++){
			for(int j = 0; j < numberOfPlayersHomeTeam; j++){
				matrixOfPassesHomeTeam[i][j] = in.nextInt();
			}
		}
		int[] homeTeamNumberOfPlayer = new int[numberOfPlayersHomeTeam];
		for(int i = 0; i < numberOfPlayersHomeTeam; i++){
			homeTeamNumberOfPlayer[i] = in.nextInt();
		}
		int[] homeTeamTimePlayed = new int[numberOfPlayersHomeTeam];
		for(int i = 0; i < numberOfPlayersHomeTeam; i++){
			StringBuilder timePlayed = new StringBuilder(in.next());
			for(int j = 0; j < timePlayed.length(); j++){
				if(timePlayed.charAt(j) == '\'' || timePlayed.charAt(j) == '\"'){
					timePlayed.setCharAt(j, ' ');
				}
			}
			Scanner scanner = new Scanner(timePlayed.toString());
			int seconds = scanner.nextInt();
			if(scanner.hasNext()){
				seconds = seconds * 60 + scanner.nextInt();
			}
			homeTeamTimePlayed[i] = seconds;
			scanner.close();
		}

		
		//away team
		
		int numberOfPlayersAwayTeam = in.nextInt();
		int[][] matrixOfPassesAwayTeam = new int[numberOfPlayersAwayTeam][numberOfPlayersAwayTeam];
		for(int i = 0; i < numberOfPlayersAwayTeam; i++){
			for(int j = 0; j < numberOfPlayersAwayTeam; j++){
				matrixOfPassesAwayTeam[i][j] = in.nextInt();
			}
		}
		
		int[] awayTeamNumberOfPlayer = new int[numberOfPlayersAwayTeam];
		for(int i = 0; i < numberOfPlayersAwayTeam; i++){
			awayTeamNumberOfPlayer[i] = in.nextInt();
		}

		int[] awayTeamTimePlayed = new int[numberOfPlayersAwayTeam];
		for(int i = 0; i < numberOfPlayersAwayTeam; i++){
			StringBuilder timePlayed = new StringBuilder(in.next());
			for(int j = 0; j < timePlayed.length(); j++){
				if(timePlayed.charAt(j) == '\'' || timePlayed.charAt(j) == '\"'){
					timePlayed.setCharAt(j, ' ');
				}
			}
			Scanner scanner = new Scanner(timePlayed.toString());
			int seconds = scanner.nextInt();
			if(scanner.hasNext()){
				seconds = seconds * 60 + scanner.nextInt();
			}
			awayTeamTimePlayed[i] = seconds;
			scanner.close();
		}
		
		in.nextLine();
		String[] homeTeamPlayersName = new String[numberOfPlayersHomeTeam];
		String name = in.nextLine();
		if(Character.isDigit(name.charAt(0))){
			for(int i = 0; i < numberOfPlayersHomeTeam; i++){
				homeTeamPlayersName[i] = in.nextLine();
			}
		}
		else{
			homeTeamPlayersName[0] = name;
			for(int i = 1; i < numberOfPlayersHomeTeam; i++){
				homeTeamPlayersName[i] = in.nextLine();
			}
		}
		
		String[] awayTeamPlayersName = new String[numberOfPlayersAwayTeam];
		name = in.nextLine();
		if(Character.isDigit(name.charAt(0))){
			for(int i = 0; i < numberOfPlayersAwayTeam; i++){
				awayTeamPlayersName[i] = in.nextLine();
			}
		}
		else{
			awayTeamPlayersName[0] = name;
			for(int i = 1; i < numberOfPlayersAwayTeam; i++){
				awayTeamPlayersName[i] = in.nextLine();
			}
		}
		
		Player[] homeTeamPlayers = new Player[numberOfPlayersHomeTeam];
		Player[] awayTeamPlayers = new Player[numberOfPlayersAwayTeam];
		
		for(int i = 0; i < numberOfPlayersHomeTeam; i++){
			boolean exists = false;
			for(int j = 0; j < ht.players.size(); j++){
				if(homeTeamPlayersName[i].equals(ht.players.get(j).name)){
					ht.players.get(j).totalTimePlayed += homeTeamTimePlayed[i];
					homeTeamPlayers[i] = ht.players.get(j);
					exists = true;
				}
			}
			if(!exists){
				homeTeamPlayers[i] = new Player(ht, s, homeTeamNumberOfPlayer[i], homeTeamPlayersName[i], homeTeamTimePlayed[i]);
				ht.players.addLast(homeTeamPlayers[i]);
			}
		}
		for(int i = 0; i < numberOfPlayersAwayTeam; i++){
			boolean exists = false;
			for(int j = 0; j < at.players.size(); j++){
				if(awayTeamPlayersName[i].equals(at.players.get(j).name)){
					at.players.get(j).totalTimePlayed += awayTeamTimePlayed[i];
					awayTeamPlayers[i] = at.players.get(j);
					exists = true;
				}
			}
			if(!exists){
				awayTeamPlayers[i] = new Player(at, s, awayTeamNumberOfPlayer[i], awayTeamPlayersName[i], awayTeamTimePlayed[i]);
				at.players.addLast(awayTeamPlayers[i]);
			}
		}
		match = new Match(ht, at, matchScore, date, homeTeamPlayers, awayTeamPlayers, matrixOfPassesHomeTeam, matrixOfPassesAwayTeam);
		match.readAllPasses(matrixOfPassesHomeTeam, homeTeamPlayers);
		match.readAllPasses(matrixOfPassesAwayTeam, awayTeamPlayers);
		ht.matches.addLast(match);
		at.matches.addLast(match);
		in.close();
	}
	private static void parseToGML(Season s) throws IOException {
		//criar para cada equipa um grafo de passes da season s
		Season current = s;
		while(!current.teams.isEmpty()){
			Team t = current.teams.remove();
			File file = new File("/home/andre/workspace/IIC/GML/" + t.name + current.year + ".gml");
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			createGMLCode(bw, t);
			bw.close();
		}		
	}

	private static void createGMLCode(BufferedWriter bw, Team t) throws IOException {
		bw.write("graph [ directed 1 label \"" + t.name + " - " + t.season.year + "\"");
		
		for(int i = 0; i < t.players.size(); i++){
			Player p = t.players.get(i);
			bw.write(" node [ id " + p.teamNumber + " name \"" + p.name + "\" ]");
		}
		while(!t.players.isEmpty()){
			Player p = t.players.remove();
			while(!p.passes.isEmpty()){
				Pass pass = p.passes.remove();
				if(pass.numberOfPasses != 0)
					bw.write(" edge [ source " + pass.origin.teamNumber + " target " + pass.destiny.teamNumber + " weight " + pass.numberOfPasses + " ]");
			}
		}
		bw.write("]");
	}

	private static void parseToTableNotation(Season s) throws IOException {
		//criar para cada equipa um ficheiro com os nos e outro com as arestas na season s
		Season current = s;
		while(!current.teams.isEmpty()){	
			Team t = current.teams.remove();
			File nodes = new File("/home/andre/workspace/IIC/GephyNotation/Nodes/" + t.name + current.year + "Nodes.txt");
			File edges = new File("/home/andre/workspace/IIC/GephyNotation/Edges/" + t.name + current.year + "Edges.txt");
			FileWriter fwNodes = new FileWriter(nodes.getAbsoluteFile());
			FileWriter fwEdges = new FileWriter(edges);
			BufferedWriter bwNodes = new BufferedWriter(fwNodes);
			bwNodes.write("TeamNumber,Name");
			bwNodes.append('\n');
			BufferedWriter bwEdges = new BufferedWriter(fwEdges);
			bwEdges.write("Source,Target,Weight");
			bwEdges.append('\n');
			while(!t.players.isEmpty()){
				Player p = t.players.remove();
				bwNodes.write(p.teamNumber + ",\"" + p.name+"\"");
				bwNodes.append('\n');
				while(!p.passes.isEmpty()){
					Pass pass = p.passes.remove();
					if(pass.numberOfPasses != 0){
						bwEdges.write("\"" + pass.origin.teamNumber + "\",\"" + pass.destiny.teamNumber + "\"," + pass.numberOfPasses);
						bwEdges.append('\n');
					}
				}
			}
			bwNodes.close();
			bwEdges.close();
		}
	}

}
