package interpretation;

import java.util.LinkedList;

public class Team {
	String name;
	LinkedList<Player> player;
	
	Team(String n){
		name = n;
	}
	
	public void addPlayer(Player p){
		this.player.add(p);
	}
}
