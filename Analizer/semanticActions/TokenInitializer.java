/**
 * 
 */
package semanticActions;

import java.text.StringCharacterIterator;

import Lexical.Token;

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

		
	}
	
	@Override
	public String toString(){
		return "AS1";
	}
	
}
