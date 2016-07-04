package interpretation;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
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
		Season[] allSeasons = new Season[n_max - n_min];
		for(int i = n_min; i < n_max; i++){
			Season s = new Season(i);
			allSeasons[i - n_min] = s;
			Season.totalSeasons++;
			for(int j = m_min; j < m_max; j++){
				if(j == 0) continue;
				file = new File("/home/andre/workspace/IIC/Uefa/games" + i + j +".txt");
				br = new BufferedReader(new FileReader(file));
				while((line = br.readLine()) != null){
					file2 = new File("/home/andre/workspace/IIC/ParsedFiles/" + line);
					collectData(file2, s, allSeasons);
				}
				
			}
			allSeasons[s.index] = s;
			parseToTableNotation(s);
			parseToGML(s);
		}
		createTranfersFile(allSeasons);
		System.out.println("nr.Seasons: " + Season.totalSeasons + ", nr.Teams: " + Team.totalTeams + ", nr.Players: " + Player.totalPlayers);
	}
	private static void createTranfersFile(Season[] allSeasons) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("/home/andre/workspace/IIC/tranfers.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write("ID\tName\t2013\t2014\t2015\t2016");
		bw.append('\n');
		boolean[] alreadySeen = new boolean[Player.totalPlayers];
		for(int i = 0; i < allSeasons.length; i++){
			Season s = allSeasons[i];
			for(int j = 0; j < s.players.size(); j++){
				Player p = s.players.get(j);
				if(alreadySeen[p.id] == false){
					alreadySeen[p.id] = true;
					bw.write(p.id + "\t" + p.name + "\t");
					for(int k = 0; k < allSeasons.length; k++){
						bw.write(p.find(allSeasons[k]) + "\t");
					}
					bw.append('\n');
				}
			}
		}
		bw.close();
	}
	private static void collectData(File file, Season s, Season[] allSeasons) throws FileNotFoundException {
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
		if(homeTeamName.contains("Zenit")){
				homeTeamName = "FC Zenit";
		}
		if(awayTeamName.contains("Zenit")){
				awayTeamName = "FC Zenit";
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
			for(int i = s.index - 1; i >= 0 && ht.id == -1; i--){
				Season previousSeason = allSeasons[i];
				for(int j = 0; j < previousSeason.teams.size(); j++){
					Team t = previousSeason.teams.get(j);
					if(t.name.equals(ht.name)){
						ht.id = t.id;
					}
				}
			}
			if(ht.id == -1){
				ht.id = Team.totalTeams++;
			}
			s.addTeamToSeason(ht);
		}
		if(!awayTeamExists){
			at = new Team(awayTeamName, s);
			for(int i = s.index - 1; i >= 0 && at.id == -1; i--){
				Season previousSeason = allSeasons[i];
				for(int j = 0; j < previousSeason.teams.size(); j++){
					Team t = previousSeason.teams.get(j);
					if(t.name.equals(at.name)){
						at.id = t.id;
					}
				}
			}
			if(at.id == -1){
				at.id = Team.totalTeams++;
			}
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
				for(int k = s.index - 1; k >= 0 && homeTeamPlayers[i].id == -1; k--){
					Season previousSeason = allSeasons[k];
					for(int l = 0; l < previousSeason.teams.size(); l++){
						Team t = previousSeason.teams.get(l);
						for(int q = 0; q < t.players.size(); q++){
							Player player = t.players.get(q);
							if(player.name.equals(homeTeamPlayers[i].name)){
								homeTeamPlayers[i].id = player.id;
							}
						}
					}
				}
				if(homeTeamPlayers[i].id == -1)
					homeTeamPlayers[i].id = Player.totalPlayers++;
				ht.players.addLast(homeTeamPlayers[i]);
				s.addPlayerToSeason(homeTeamPlayers[i]);
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
				for(int k = s.index - 1; k >= 0 && awayTeamPlayers[i].id == -1; k--){
					Season previousSeason = allSeasons[k];
					for(int l = 0; l < previousSeason.teams.size(); l++){
						Team t = previousSeason.teams.get(l);
						for(int q = 0; q < t.players.size(); q++){
							Player player = t.players.get(q);
							if(player.name.equals(awayTeamPlayers[i].name)){
								awayTeamPlayers[i].id = player.id;
							}
						}
					}
				}
				if(awayTeamPlayers[i].id == -1)
					awayTeamPlayers[i].id = Player.totalPlayers++;
				at.players.addLast(awayTeamPlayers[i]);
				s.addPlayerToSeason(awayTeamPlayers[i]);
			}
		}
		match = new Match(ht, at, matchScore, date, homeTeamPlayers, awayTeamPlayers, matrixOfPassesHomeTeam, matrixOfPassesAwayTeam);
		Match.totalMatches++;
		match.readAllPasses(matrixOfPassesHomeTeam, homeTeamPlayers);
		match.readAllPasses(matrixOfPassesAwayTeam, awayTeamPlayers);
		ht.matches.addLast(match);
		at.matches.addLast(match);
		in.close();
	}
	
	private static void parseToGML(Season s) throws IOException {
		//criar para cada equipa um grafo de passes da season s
		Season current = s;
		for(int i = 0; i < current.teams.size(); i++){
			Team t = current.teams.get(i);
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
			bw.write(" node [ id " + p.id + " name \"" + p.name + "\" ]");
		}
		for(int i = 0; i < t.players.size(); i++){
			Player p = t.players.get(i);
			for(int j = 0; j < p.passes.size(); j++){
				Pass pass = p.passes.get(j);
				if(pass.numberOfPasses != 0)
					bw.write(" edge [ source " + pass.origin.id + " target " + pass.destiny.id + " weight " + (pass.numberOfPasses/t.matches.size() + 1) + " ]");
			}
		}
		bw.write("]");
	}

	private static void parseToTableNotation(Season s) throws IOException {
		//criar para cada equipa um ficheiro com os nos e outro com as arestas na season s
		Season current = s;
		for(int i = 0; i < current.teams.size(); i++){	
			Team t = current.teams.get(i);
			File nodes = new File("/home/andre/workspace/IIC/GephyNotation/Nodes/" + t.name + current.year + "Nodes.txt");
			File edges = new File("/home/andre/workspace/IIC/GephyNotation/Edges/" + t.name + current.year + "Edges.txt");
			FileWriter fwNodes = new FileWriter(nodes.getAbsoluteFile());
			FileWriter fwEdges = new FileWriter(edges);
			BufferedWriter bwNodes = new BufferedWriter(fwNodes);
			bwNodes.write("Id,TeamNumber,Name");
			bwNodes.append('\n');
			BufferedWriter bwEdges = new BufferedWriter(fwEdges);
			bwEdges.write("Source,Target,Weight");
			bwEdges.append('\n');
			for(int j = 0; j < t.players.size(); j++){
				Player p = t.players.get(j);
				bwNodes.write(p.id + "," + p.teamNumber + ",\"" + p.name+"\"");
				bwNodes.append('\n');
				for(int k = 0; k < p.passes.size(); k++){
					Pass pass = p.passes.get(k);
					if(pass.numberOfPasses != 0){
						bwEdges.write("\"" + pass.origin.id + "\",\"" + pass.destiny.id + "\"," + (pass.numberOfPasses/t.matches.size() + 1));
						bwEdges.append('\n');
					}
				}
			}
			bwNodes.close();
			bwEdges.close();
		}
	}

}
