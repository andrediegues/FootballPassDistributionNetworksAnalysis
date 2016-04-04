package interpretation;

import java.util.LinkedList;

public class Team {
	String name;
	String country;
	Season season;
	Player player;
	boolean[][] players;
	LinkedList<Match> matches;
	int numberOfTeams = 0;
	
	Team(String n, String c){
		name = n;
		country = c;
		players = new boolean[player.numberOfPlayers][season.numberOfSeasons];
		numberOfTeams++;
	}
	
	public void addMatch(Match m){
		this.matches.add(m);
	}
}
