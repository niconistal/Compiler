package Lexical;

import java.text.StringCharacterIterator;
/**
 * 
 * @author niconistal
 *
 *	
 *	AS9
 *	Adds a single quote to the token literal and notifies the ErrorHandler of this.
 *
 *
 */
public class SingleQuoteAdder implements ISemanticAction {

	@Override
	public void performAction(Token tok, StringCharacterIterator source,Object line) {
		tok.addCharacter('\'');
		
		//Notify the error handler
		
		Error error = new Error( Error.TYPE_WARNING, " Found a quote mark unclosed", (Integer)line);
		ErrorHandler.getInstance().addError(error);
		
	}
	
	@Override
	public String toString(){
		return "AS9";
	}
	
}
