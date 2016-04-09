package interpretation;

public class Season {
	int year;
	static int numberOfSeasons = 0;
	
	Season(int y){
		year = y;
		numberOfSeasons++;
	}
}
