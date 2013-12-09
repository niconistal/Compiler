/**
 * 
 */
package semanticActions;

import java.text.StringCharacterIterator;

import Lexical.Error;
import Lexical.ErrorHandler;
import Lexical.Token;


public class StrangeCharacter implements ISemanticAction{

	public void performAction(Token tok, StringCharacterIterator source,int[] line) {
		

		
		//Error Handler Notification
		Error error = new Error (Error.TYPE_FATAL, "Strange character appeared ( "+source.current()+" ) ", line[0]);
		ErrorHandler.getInstance().addError(error);
		
	}
	
	public String toString(){
		return "AS19";
	}
}

