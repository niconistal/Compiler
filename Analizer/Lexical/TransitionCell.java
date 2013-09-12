/**
 * 
 */
package Lexical;

import java.util.ArrayList;

/**
 * @author niconistal
 *
 */
public class TransitionCell {

	protected int nextState;
	protected ArrayList<ISemanticAction> actions;
	
	public TransitionCell(){
		actions = new ArrayList<ISemanticAction>();
	}
	public TransitionCell(int nextState, ArrayList<ISemanticAction> actions){
		this.nextState = nextState;
		this.actions = actions;
	}
	
	public void setNextState(int nextState){
		this.nextState = nextState;
	}
	
	public void addAction(ISemanticAction action){
		actions.add(action);
	}
	
	public int getNextState(){
		return this.nextState;
	}
	
	public ArrayList<ISemanticAction> getActions(){
		return this.actions;
	}
	
	public String toString(){
		String returnString = "";
		Integer state = new Integer(this.nextState);
		returnString += state.toString();
		returnString += " - ";
		returnString += actions.toString();
		
		return returnString;
	}
}
