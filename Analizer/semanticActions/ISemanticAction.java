/**
 * 
 */
package semanticActions;

import java.text.StringCharacterIterator;

import Lexical.Token;

/**
 * @author niconistal
 * This interface represents the entire family of semantic
 * actions.
 */
public interface ISemanticAction {

	public abstract void performAction(Token tok, StringCharacterIterator source,int[] line);
	
}