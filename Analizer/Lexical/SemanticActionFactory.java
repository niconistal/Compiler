package Lexical;

import java.util.ArrayList;

/**
 * Factory pattern to create the different semantic actions
 * @author Inti
 *
 */
public class SemanticActionFactory {
	
	/**
	 * Retrieves the semantic actions instances given the names of them
	 * @param [actions] a concatenated string containing the names of each semantic action
	 * @return ArrayList<ISemanticAction>
	 */
	public static ArrayList<ISemanticAction> parseSemanticActions(String actions){
		ArrayList<ISemanticAction> parsedActions = new ArrayList<ISemanticAction>();
		String actionSeparator = " ";
		String[] stringActions = actions.split(actionSeparator);
		for(String ac : stringActions){
			ISemanticAction acAction;
			switch (ac){
				
				case "AS1" :	acAction = new TokenInitializer();
								break;
					
				case "AS2" : 	acAction = new CharacterAdder();
								break;
				
				case "AS3" : 	acAction = new SymbolTableHandler();
								break;
				
				case "AS4" : 	acAction = new RangeChecker();
								break;
					
				case "AS5" : 	acAction = new CharacterReturner();
								break;
				
				case "AS6" :	acAction = new CharacterTruncator();
								break;
						
				case "AS9" :	acAction = new SingleQuoteAdder();
								break;
							
				case "AS10" : 	acAction = new LineCounter();
								break;
				
				case "AS11" : 	acAction = new CharchainTokenSetter();
								break;
				
				case "AS12" : 	acAction = new IDTokenSetter();
								break;
				
				case "AS13" : 	acAction = new CteTokenSetter();
								break;
								
				case "AS14" : 	acAction = new LiteralTokenSetter();
								break;
								
				default    :	acAction = null;
			}
			parsedActions.add(acAction);
		}
		
		return parsedActions;
	}
}
