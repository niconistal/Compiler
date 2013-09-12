/**
 * 
 */
package Lexical;

/**
 * @author niconistal
 *
 */
public class CteTokenSetter extends TokenSetterTemplate {

	@Override
	public String resolveToken(Token tok) {
		

		return "CTE";
	}
	
	public String toString(){
		
		return "AS";
	}

}
