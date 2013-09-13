import Lexical.ErrorHandler;
import Lexical.LexicalAnalizer;
import Lexical.SymbolTable;
import Sintactic.Parser;

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
		Parser parser = new Parser();
		parser.run();
		System.out.println(SymbolTable.getInstance().toString());
		System.out.println(ErrorHandler.getInstance().toString());
		
	}

}
