package interpretation;

import java.util.LinkedList;

public class Season {
	int year;
	int index;
	LinkedList<Team> teams;
	static int totalSeasons = 0;
	
	Season(int y){
		year = y;
		teams = new LinkedList<Team>();
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
	
	public String toString(){
		return year + "\n";
	}
}
