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
	
	
	public void performAction(Token tok, StringCharacterIterator source,Integer line) {
		// TODO Auto-generated method stub
		tok = new Token();
		tok.setLine(line.intValue());
	}
	
	@Override
	public String toString(){
		return "AS1";
	}
	
}
