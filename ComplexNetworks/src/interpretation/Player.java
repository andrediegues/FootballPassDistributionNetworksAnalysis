package interpretation;

public class Player {
	Team team;
	String name;
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
	
	Player(Team t, String n, int tn, int tp, int lpc, int lpa, int mpc, int mpa, int spc, int spa, int tpc, int tpa, int a){
		team = t;
		name = n;
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
	}
	
}
