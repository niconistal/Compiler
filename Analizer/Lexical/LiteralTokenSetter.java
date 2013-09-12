/**
 * 
 */
package Lexical;

/**
 * @author niconistal
 *
 */
public class LiteralTokenSetter extends TokenSetterTemplate {

	@Override
	public String resolveToken(Token tok) {
		
		return tok.getLiteralValue();
		
	}

	
}
