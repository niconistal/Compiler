/**
 * 
 */
package semanticActions;

import java.text.StringCharacterIterator;

import Lexical.Token;

/**
 * @author niconistal
 *
 */
public abstract class TokenSetterTemplate implements ISemanticAction {

	@Override
	public void performAction(Token tok, StringCharacterIterator source, int[] line) {
		
		String tokenValue = resolveToken(tok);
		tok.setTokenValue(tokenValue);
		
	}
	
	public abstract String resolveToken(Token tok);
	

}