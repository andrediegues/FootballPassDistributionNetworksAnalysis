package interpretation;
 
import java.util.LinkedList;

public class Team {
	String name;
	String country;
	boolean[][] players;
	LinkedList<Match> matches;
	static int numberOfTeams = 0;
	
	Team(String n){
		name = n;
		country = getCountry(n);
		players = new boolean[Player.numberOfPlayers][Season.numberOfSeasons];
		matches = new LinkedList<Match>();
		numberOfTeams++;
	}
	
	public void addMatch(Match m){
		this.matches.add(m);
	}
	
	//falta implementar
	public String getCountry(String team){
		return null;
	}
}
enum Country{
	Italy,
	England,
	Spain,
	Portugal,
	Germany,
	Russia, 
	Turkey, 
	France,
	Scotland,
	Austria,
	Switzerland,
	Sweden,
	Belgium, 
	Netherlands,
	Cyprus,
	Kazakistan,
	FaroeIslands,
	Belarus,
	NorthernIreland,
	Georgia,
	Croatia,
	Ireland,
	Ukraine,
	Andorra,
	Luxembourg,
	SanMarino,
	Malta,
	Finland,
	Poland,
	Estonia,
	Bulgaria,
	Israel,
	Slovenia,
	Denmark,
	Moldova,
	Norway,
	Greece,
	Serbia,
	CzechRepublic,
	Armenia,
	Montenegro,
	BosniaHerzegovina,
	Albania,
	Romania,
	Iceland,
	Wales,
	Macedonia;
}

