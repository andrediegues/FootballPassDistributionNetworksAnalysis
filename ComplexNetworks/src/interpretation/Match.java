package interpretation;

public class Match {
	Team home;
	Team away;
	Season season;
	MatchScore score;
	MatchDate date;
	Player[] homePositionsToPlayer;
	Player[] awayPositionsToPlayer;
	char[][] homeAdjacencyMatrix;
	char[][] awayAdjacencyMatrix;
	static int numberOfMatches = 0;
	
	public Match(Team h, Team a, Season season, MatchScore score, MatchDate d, Player[] hptp, Player[] aptp, char[][] ham, char[][] aam){
		home = h;
		away = a;
		this.season = season;
		this.score = score;
		date = d;
		homePositionsToPlayer = hptp;
		awayPositionsToPlayer = aptp;
		homeAdjacencyMatrix = ham;
		awayAdjacencyMatrix = aam;
		numberOfMatches++;
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
}

class MatchScore {
	int homeScore;
	int awayScore;
	
	MatchScore(String s){
		homeScore = s.charAt(0);
		awayScore = s.charAt(4);
	}	
}

