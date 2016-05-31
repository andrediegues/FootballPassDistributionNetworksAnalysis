package interpretation;

public class Player {
	//int id = -1;
	Team team;
	int teamNumber;
	String name;
	int totalTimePlayed = 0;
	//boolean[][] playedFor = new boolean[Team.numberOfTeams][Season.numberOfSeasons];
	//static int numberOfPlayers = 0;
	
	Player(Team t, Season s, int tn, String n, int ttp){
		//id++;
		team = t;
		teamNumber = tn;
		name = n;
		totalTimePlayed += ttp;
		//playedFor[t.id][s.index] = true;
		//numberOfPlayers++;
	}
	
	public String toString(){
		return name + " " + teamNumber + " " + totalTimePlayed + " " + team.name;
	}
}
