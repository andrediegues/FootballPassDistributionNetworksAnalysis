package interpretation;

import java.util.LinkedList;

public class Season {
	int year;
	//int index;
	//static int numberOfSeasons = 0;

	LinkedList<Team> teams;
	
	Season(int y){
		year = y;
		//index = year - 2013;
		//numberOfSeasons++;
	}
	
	public void addTeamToSeason(Team t){
		if(!teams.contains(t)){
			teams.addFirst(t);
		}
	}
	
	public String toString(){
		return year + "\n";
	}
}
