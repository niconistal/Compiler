package Lexical;

import java.text.StringCharacterIterator;

public class RangeChecker implements ISemanticAction {

	private static long MIN_RANGE = 0L;
	private static long MAX_RANGE = 4294967295L;
	
	@Override
	public void performAction(Token tok, StringCharacterIterator source,Integer line) {
		Long tokenValue = Long.valueOf(tok.getTokenValue());
		boolean belowRange = tokenValue < MIN_RANGE;
		boolean aboveRange = tokenValue > MAX_RANGE;
		if(belowRange){
			tokenValue = MIN_RANGE;
		} else if(aboveRange) {
			tokenValue = MAX_RANGE;
		}
		if(belowRange || aboveRange) {
			tok.setTokenValue(tokenValue.toString());
			//throw error
		}
		
	}
	
	@Override
	public String toString(){
		return "AS4";
	}

}
