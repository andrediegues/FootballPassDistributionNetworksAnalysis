package interpretation;

import java.util.LinkedList;

public class Player {
	int id = -1;
	Team team;
	int teamNumber;
	String name;
	int totalTimePlayed = 0;
	Season season;
	LinkedList<Pass> passes;
	//boolean[][] playedFor = new boolean[Team.numberOfTeams][Season.numberOfSeasons];
	static int totalPlayers = 0;
	
	Player(Team t, Season s, int tn, String n, int ttp){
		team = t;
		teamNumber = tn;
		name = n;
		totalTimePlayed += ttp;
		season = s;
		passes = new LinkedList<Pass>();
		//playedFor[t.id][s.index] = true;
	}
	
	public String toString(){
		return name + " " + teamNumber + " " + totalTimePlayed + " " + team.name;
	}
	
	public void addPass(Player p, int numberOfPassesBetween){
		boolean alreadyHaveConnection = false;
		for(int i = 0; i < passes.size(); i++){
			Pass pass = passes.get(i); 
			if(this.name.equals(pass.origin.name) && p.name.equals(pass.destiny.name)){
				alreadyHaveConnection = true;
				pass.numberOfPasses += numberOfPassesBetween;
			}
		}
		if(!alreadyHaveConnection){
			passes.add(new Pass(this, p, numberOfPassesBetween));
		}
	}
}
