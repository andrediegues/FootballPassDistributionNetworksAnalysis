package interpretation;
 
import java.util.LinkedList;

public class Team {
	int id = -1;
	String name;
	String country;
	Season season;
	//boolean[][] players = new boolean[Player.numberOfPlayers][Season.numberOfSeasons];
	LinkedList<Match> matches;
	LinkedList<Player> players;
	
	Team(String n, Season s){
		id++;
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
	public int getID(){
		return this.id;
	}
	public String toString(){
		return "ID: " + id + "\nName: " + name + "\nSeason: " + season + "\nNo. Players: " + players.size() + "\nNo. Matches: " + matches.size();
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

