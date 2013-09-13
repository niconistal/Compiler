/**
 * 
 */
package Lexical;

/**
 * @author niconistal
 *
 *
 *	AS14
 *	Sets the value of the token with his literal value in upper case.
 */
public class LiteralTokenSetter extends TokenSetterTemplate {

	@Override
	public String resolveToken(Token tok) {
		
		return tok.getLiteralValue().toUpperCase();
		
	}
	
	public String toString(){
		return "AS14";
	}

	
}
