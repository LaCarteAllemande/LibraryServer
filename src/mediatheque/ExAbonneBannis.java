package mediatheque;

public class ExAbonneBannis extends Exception{
	private int duree;
	public ExAbonneBannis(int duree) {
		this.duree= duree;
	}
	
	public int duree() {
		return duree;
	}
}
