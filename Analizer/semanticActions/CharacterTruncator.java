/**
 * 
 */
package semanticActions;

import java.text.StringCharacterIterator;

import Lexical.Error;
import Lexical.ErrorHandler;
import Lexical.Token;

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
	public void performAction(Token tok, StringCharacterIterator source,int[] line) {
		
		String truncated = tok.getLiteralValue();
		if (truncated.length() > STRING_LONG){
			
			//The chain got truncated
			truncated = truncated.substring(0, STRING_LONG);
			
			//Error Handler Notification
			Error error = new Error (Error.TYPE_WARNING, "The chain "+tok.getLiteralValue()+" got truncated to "+truncated, line[0]);
			ErrorHandler.getInstance().addError(error);
			
			tok.setLiteralValue(truncated);
			
		}
		
	}
	
	@Override
	public String toString(){
		return "AS6";
	}

}
