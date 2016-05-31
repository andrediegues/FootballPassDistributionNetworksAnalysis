package interpretation;
 
import java.util.LinkedList;

public class Team {
	String name;
	String country;
	Season season;
	//boolean[][] players = new boolean[Player.numberOfPlayers][Season.numberOfSeasons];
	LinkedList<Match> matches;
	LinkedList<Player> players;
	
	Team(String n, Season s){
		name = n;
		//country = getCountry(n);
		season = s;
		matches = new LinkedList<Match>();
		players = new LinkedList<Player>();
	}
	
	public void addTeamPlayerInSeason(Player p, Season s){
		//players[p.id][s.index] = true;
		if(season == s && !players.contains(p)){
			players.addFirst(p);
		}
	}
	
	public void addMatch(Match m, Season s){
		if(season == s){
			matches.addFirst(m);
		}
	}
	
	//falta implementar
	public String getCountry(String team){
		return null;
	}
	public String toString(){
		return name + " " + season + "\n";
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

