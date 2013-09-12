/**
 * 
 */
package Lexical;

import java.text.StringCharacterIterator;

/**
 * @author niconistal
 *
 */
public class CharacterTruncator implements ISemanticAction {

	public static int STRING_LONG = 15;
	@Override
	public void performAction(Token tok, StringCharacterIterator source,Integer line) {
		
		String truncated = tok.getTokenValue();
		if (truncated.length() > STRING_LONG){
			truncated = truncated.substring(0, STRING_LONG);
			tok.setTokenValue(truncated);
			//TODO notify error handler.
		}
		
	}
	
	@Override
	public String toString(){
		return "AS6";
	}

}
