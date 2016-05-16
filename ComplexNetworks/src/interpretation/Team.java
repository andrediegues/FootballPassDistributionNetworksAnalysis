package interpretation;
 
import java.util.LinkedList;

public class Team {
	String name;
	String country;
	boolean[][] players = new boolean[Player.numberOfPlayers][Season.numberOfSeasons];
	LinkedList<Match> matches;
	int id = -1;
	static int numberOfTeams = 0;
	
	Team(String n){
		id++;
		numberOfTeams++;
		name = n;
		country = getCountry(n);
		matches = new LinkedList<Match>();
	}
	
	public void addTeamPlayerInSeason(Player p, Season s){
		players[p.id][s.index] = true;
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

