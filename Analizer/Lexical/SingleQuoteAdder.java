package Lexical;

import java.text.StringCharacterIterator;

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
