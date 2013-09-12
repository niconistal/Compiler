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
	public void performAction(Token tok, StringCharacterIterator source,Integer line) {
		tok.addCharacter('\'');
		
	}
	
	@Override
	public String toString(){
		return "AS9";
	}
	
}
