/**
 * 
 */
package Lexical;

import java.text.StringCharacterIterator;

/**
 * @author niconistal
 *
 *
 *	AS1
 *	Initialize the token
 *
 */
public class TokenInitializer implements ISemanticAction{
	
	
	public void performAction(Token tok, StringCharacterIterator source,int[] line) {

		tok = new Token();
		tok.setLine(line[0]);
	}
	
	@Override
	public String toString(){
		return "AS1";
	}
	
}
