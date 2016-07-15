package interpretation;
 
import java.util.LinkedList;

public class Team {
	static int id = -1;
	String name;
	Season season;
	LinkedList<Match> matches;
	LinkedList<Player> players;
	static int totalTeams = 0;
	
	Team(String n, Season s){
		name = n;
		season = s;
		matches = new LinkedList<Match>();
		players = new LinkedList<Player>();
	}
	
	public void addTeamPlayerInSeason(Player p, Season s){
		if(season == s && !players.contains(p)){
			players.addFirst(p);
		}
	}
	
	public void addMatch(Match m, Season s){
		if(season == s){
			matches.addFirst(m);
		}
	}
	
	public String toString(){
		return "Name: " + name + "\nSeason: " + season + "\nNo. Players: " + players.size() + "\nNo. Matches: " + matches.size();
	}
}
