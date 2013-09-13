/**
 * 
 */
package Lexical;

import java.text.StringCharacterIterator;

/**
 * @author niconistal
 *
 */
public abstract class TokenSetterTemplate implements ISemanticAction {

	@Override
	public void performAction(Token tok, StringCharacterIterator source, Object line) {
		
		String tokenValue = resolveToken(tok);
		tok.setTokenValue(tokenValue);
		
	}
	
	public abstract String resolveToken(Token tok);
	

}