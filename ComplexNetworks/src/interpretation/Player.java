package interpretation;

public class Player {
	int id = 0;
	Team team;
	String name;
	boolean[][] playedFor;
	int teamNumber;
	int timePlayed;
	int longPassesCompleted;
	int longPassesAttempted;
	int mediumPassesCompleted;
	int mediumPassesAttempted;
	int shortPassesCompleted;
	int shortPassesAttempted;
	int totalPassesCompleted;
	int totalPassesAttempted;
	int accuracy;
	static int numberOfPlayers = 0;
	
	Player(Team t, String n, Season s, int tn, int tp, int lpc, int lpa, int mpc, int mpa, int spc, int spa, int tpc, int tpa, int a){
		id++;
		team = t;
		name = n;
		playedFor = new boolean[Team.numberOfTeams][Season.numberOfSeasons];
		teamNumber = tn;
		timePlayed = tp;
		longPassesCompleted = lpc;
		longPassesAttempted = lpa;
		mediumPassesCompleted = mpc;
		mediumPassesAttempted = mpa;
		shortPassesCompleted = spc;
		shortPassesAttempted = spa;
		totalPassesCompleted = tpc;
		totalPassesAttempted = tpa;
		accuracy = a;
		numberOfPlayers++;
	}
}
