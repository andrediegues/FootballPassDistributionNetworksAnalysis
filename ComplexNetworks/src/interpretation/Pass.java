package interpretation;

public class Pass {
	Player origin;
	Player destiny;
	int numberOfPasses = 0;
	
	Pass(Player o, Player d, int np){
		origin = o;
		destiny = d;
		numberOfPasses += np;
	}
}
