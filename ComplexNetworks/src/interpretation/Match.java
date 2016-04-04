package interpretation;

public class Match {
	Team home;
	Team away;
	Season season;
	int scoreHome;
	int scoreAway;
	Player[] homePositionsToPlayer;
	Player[] awayPositionsToPlayer;
	char[][] homeAdjacencyMatrix;
	char[][] awayAdjacencyMatrix;
	int numberOfMatches = 0;
}
