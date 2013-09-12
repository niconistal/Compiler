/**
 * 
 */
package Lexical;

import java.text.StringCharacterIterator;

/**
 * @author niconistal
 * 
 * 
 * 
 * 	AS6
 * 	Checks the length of the chain, if it exceeds a predefined value it gets truncated.
 * 	If the chain was truncated, the ErrorHandler is notified.
 *
 */
public class CharacterTruncator implements ISemanticAction {

	public static int STRING_LONG = 15;
	@Override
	public void performAction(Token tok, StringCharacterIterator source,Integer line) {
		
		String truncated = tok.getLiteralValue();
		if (truncated.length() > STRING_LONG){
			truncated = truncated.substring(0, STRING_LONG);
			tok.setLiteralValue(truncated);
			//TODO notify error handler.
		}
		
	}
	
	@Override
	public String toString(){
		return "AS6";
	}

}
