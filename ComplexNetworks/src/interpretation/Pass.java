package interpretation;

public class Pass {
	Player origin;
	Player destiny;
	int numberOfPasses;
	
	Pass(Player o, Player d, int np){
		origin = o;
		destiny = d;
		numberOfPasses = np;
	}
}
