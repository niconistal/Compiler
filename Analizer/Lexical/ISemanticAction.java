/**
 * 
 */
package Lexical;

import java.text.StringCharacterIterator;

/**
 * @author niconistal
 * This interface represents the entire family of semantic
 * actions.
 */
public interface ISemanticAction {

	public abstract void performAction(Token tok, StringCharacterIterator source,int[] line);
	
}