package semanticActions;

import java.text.StringCharacterIterator;

import Lexical.Error;
import Lexical.ErrorHandler;
import Lexical.Token;
/**
 * 
 * @author niconistal
 *
 *
 *	AS4
 *	Checks if the constant expression fits in the range.
 *	If not, the number gets modified and the ErrorHandler gets notified.
 *
 */
public class RangeChecker implements ISemanticAction {

	private static long MIN_RANGE = 0L;
	private static long MAX_RANGE = 4294967295L;
	
	@Override
	public void performAction(Token tok, StringCharacterIterator source,int[] line) {
		Long tokenValue = Long.valueOf(tok.getLiteralValue());
		boolean belowRange = tokenValue < MIN_RANGE;
		boolean aboveRange = tokenValue > MAX_RANGE;
		if(belowRange){
			tokenValue = MIN_RANGE;
		} else if(aboveRange) {
			tokenValue = MAX_RANGE;
		}
		if(belowRange || aboveRange) {
			tok.setLiteralValue(tokenValue.toString());
			//throw error
			Error error = new Error(Error.TYPE_WARNING, "The constant "+tok.getLiteralValue()+" is out of bounds.", line[0]);
			ErrorHandler.getInstance().addError(error);
		}
		
	}
	
	@Override
	public String toString(){
		return "AS4";
	}

}
