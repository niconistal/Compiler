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
	protected String literal = "";
	protected int line;
	
	public Token(){
		line = -1;
	}
	public Token(int i){
		this.line = i;
	}
	
	public void setLine(int line){
		this.line = line;
	}
	
	public int getLine(){
		return this.line;
	}
	
	public void addCharacter(char c){
		literal += c;
	}
	public void setTokenValue(String value){
		this.preToken = value;
	}
	public void setLiteralValue(String literal){
		this.literal = literal;
	}
	
	public String getTokenValue(){
		return this.preToken;
	}
	
	public String getLiteralValue(){
		return this.literal;
	}
	
	public String toString(){
		return "TOKEN -> Literal: "+this.literal+" Token: "+this.preToken+" Line: "+this.getLine();
	}
}
