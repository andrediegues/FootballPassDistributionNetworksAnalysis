package interpretation;

import java.util.LinkedList;

public class Season {
	int year;
	//int index;
	//static int numberOfSeasons = 0;

	LinkedList<Team> teams;
	
	Season(int y){
		year = y;
		teams = new LinkedList<Team>();
		//index = year - 2013;
		//numberOfSeasons++;
	}
	
	public void addTeamToSeason(Team t){
		if(teams.isEmpty()){
			//System.out.println("entrei");
			teams.addFirst(t);
			//System.out.println("|| " + teams.size() + " " + t.toString());
		}
		else if(!teams.contains(t)){
			teams.addFirst(t);
			//System.out.println("|| " + teams.size() + " " + t.toString());
		}
	}
	
	public String toString(){
		return year + "\n";
	}
}
