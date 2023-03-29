
package utilitaire;

public class Utilitaire {
	private static final char NEW_LINE= '\n';
	private static final char ENCRYPTED_LINE = '#';
	public static String decrypt(String message) {
		return message.replace(ENCRYPTED_LINE, NEW_LINE);
	}
	
	public static String encrypt(String message) {
		return message.replace(NEW_LINE, ENCRYPTED_LINE);
	}
}
