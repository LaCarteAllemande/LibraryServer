package mediatheque;

import java.util.Map;
import java.util.TimerTask;

public class MajBannissement extends TimerTask {
	private Map<Integer, Abonne> abonnes;
	
	public MajBannissement(Map<Integer, Abonne> a) {
		abonnes =a;
	}

	@Override
	public void run() {
		//met à jour les bannissements tous les jours
		for (Abonne a : abonnes.values()) {
			a.bannir(-1);
		}

	}

}
