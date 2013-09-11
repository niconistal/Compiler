import java.io.IOException;

import Lexical.LexicalAnalizer;

/**
 * 
 */

/**
 * @author niconistal
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LexicalAnalizer lex = new LexicalAnalizer("test.txt");
		try {
			lex.initializeMatrix();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
