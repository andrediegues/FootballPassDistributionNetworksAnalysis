package interpretation;

import java.util.LinkedList;

public class Season {
	int year;
	int index;
	LinkedList<Team> teams;
	LinkedList<Player> players;
	public static int totalSeasons = 0;
	
	public Season(int y){
		year = y;
		teams = new LinkedList<Team>();
		players = new LinkedList<Player>();
		index = year - 2013;
	}
	
	public void addTeamToSeason(Team t){
		if(teams.isEmpty()){
			teams.addFirst(t);
		}
		else if(!teams.contains(t)){
			teams.addFirst(t);
		}
	}
	public void addPlayerToSeason(Player p){
		players.addFirst(p);
	}
	
	public String toString(){
		return year + "\n";
	}
}
