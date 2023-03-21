package tasks;

import java.util.Map;
import java.util.TimerTask;

import mediatheque.Abonne;

public class MajBannissement extends TimerTask {
	private Map<Integer, Abonne> abonnes;
	
	public MajBannissement(Map<Integer, Abonne> a) {
		abonnes =a;
	}

	@Override
	public void run() {
		for (Abonne a : abonnes.values()) {
			a.bannir(-1);
		}

	}

}
