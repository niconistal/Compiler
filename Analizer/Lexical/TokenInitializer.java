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
	
	
	public void performAction(Token tok, StringCharacterIterator source,Object line) {

		tok = new Token();
		tok.setLine(((Integer)line).intValue());
	}
	
	@Override
	public String toString(){
		return "AS1";
	}
	
}
