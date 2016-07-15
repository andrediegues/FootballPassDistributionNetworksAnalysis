package interpretation;

public class Match { 
	Team home;
	Team away;
	Season season;
	MatchScore score;
	MatchDate date;
	Player[] homePositionsToPlayer;
	Player[] awayPositionsToPlayer;
	int[][] homeAdjacencyMatrix;
	int[][] awayAdjacencyMatrix;
	int numberOfPassesHomeTeam = 0;
	int numberOfPassesAwayTeam = 0;
	
	
	static int totalMatches = 0;
	
	public Match(Team h, Team a, MatchScore score, MatchDate d, Player[] hptp, Player[] aptp, int[][] ham, int[][] aam){
		home = h;
		away = a;
		this.score = score;
		date = d;
		homePositionsToPlayer = hptp;
		awayPositionsToPlayer = aptp;
		homeAdjacencyMatrix = ham;
		awayAdjacencyMatrix = aam;
	}
	
	public String toString(){
		return date + "\n" + home + " vs " + away + " -> " + score.homeScore + "-" + score.awayScore + "\n";
	}
	
	public void readAllPasses(int[][] matrix, Player[] players){
		int passes = 0;
		for(int i = 0; i < players.length; i++){
			for(int j = 0; j < players.length; j++){
				if(numberOfPassesHomeTeam == 0){
					passes += matrix[i][j];
				}
				else{
					numberOfPassesAwayTeam += matrix[i][j];
				}
				players[i].addPass(players[j], matrix[i][j]);
			}
		}
		if(passes > 0)
			numberOfPassesHomeTeam = passes;
	}
}

class MatchDate {
	String weekday;
	int day;
	String month;
	int year;
	 
	MatchDate(String wd, int d, String m, int y){
		weekday = wd;
		day = d;
		month = m;
		year = y;
	}
	
	public String toString(){
		return weekday + ", " + day + " " + month + " " + year; 
	}
}

class MatchScore {
	int homeScore;
	int awayScore;
	static int totalGoalsScored = 0;
	
	MatchScore(int h, int a){
		homeScore = h;
		awayScore = a;
	}
	
	public String toString(){
		return homeScore + " - " + awayScore; 
	}
}

