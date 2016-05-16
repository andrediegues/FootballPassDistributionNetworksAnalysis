package interpretation;

public class Season {
	int year;
	int index;
	static int numberOfSeasons = 0;
	
	Season(int y){
		year = y;
		index = year - 2013;
		numberOfSeasons++;
	}
}
