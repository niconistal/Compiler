/**
 * 
 */
package Lexical;

/**
 * @author niconistal
 *
 */
public class Token {
	
	protected String preToken = "";
	protected int line;
	
	public Token(){
		line = -1;
	}
	
	public void setLine(int line){
		this.line = line;
	}
	
	public void addCharacter(char c){
		preToken += c;
	}
	public void setTokenValue(String value){
		this.preToken = value;
	}
	
	public String getTokenValue(){
		return this.preToken;
	}
	public String toString(){
		return this.preToken;
	}
}
