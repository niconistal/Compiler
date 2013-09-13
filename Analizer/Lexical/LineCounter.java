package Lexical;

import java.text.StringCharacterIterator;
/**
 * 
 * @author niconistal
 *
 *
 *	AS10
 *	Adds 1 to the line
 *
 *
 */
public class LineCounter implements ISemanticAction {

	@Override
	public void performAction(Token tok, StringCharacterIterator source, int[] line) {
		
		line[0]++;
		
	}
	
	@Override
	public String toString(){
		return "AS10";
	}

}
