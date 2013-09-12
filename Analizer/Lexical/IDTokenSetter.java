/**
 * 
 */
package Lexical;
/**
 * 
 * @author niconistal
 *
 *	AS12
 *	Checks if the identifier is a reserved word or if its an identifier.
 *
 */
public class IDTokenSetter extends TokenSetterTemplate {

	@Override
	public String resolveToken(Token tok) {
		//if (SymbolTable.getInstance().)
		return "ID";
		
	}
	
	public String toString(){
		
		return "AS";
	}

}
