/**
 * 
 */
package semanticActions;

import Lexical.Token;

/**
 * @author niconistal
 *
 *
 *
 *	AS11
 *	Sets the type of the token value of the token to CHARCHAIN
 *
 *
 */
public class CharchainTokenSetter extends TokenSetterTemplate {

	@Override
	public String resolveToken(Token tok) {
		
		return "CHARCHAIN";
	}
	
	public String toString(){
		return "AS11";
	}

}
