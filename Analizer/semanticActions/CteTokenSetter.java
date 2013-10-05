/**
 * 
 */
package semanticActions;

import Lexical.Token;

/**
 * @author niconistal
 *
 *
 *	AS13
 *	Sets the value of the token to CTE
 *
 *
 */
public class CteTokenSetter extends TokenSetterTemplate {

	@Override
	public String resolveToken(Token tok) {
		

		return "CTE";
	}
	
	public String toString(){
		
		return "AS13";
	}

}
