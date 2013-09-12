/**
 * 
 */
package Lexical;

/**
 * @author niconistal
 *
 */
public class SymbolElement {

	protected String type;
	protected Object value;
	
	public SymbolElement(String type, Object value){
		
		this.type = type;
		this.value = value;
		
	}
	
	public SymbolElement(){
		type = "N/A";
		value = null;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void setValue(Object value){
		this.value = value;
	}
	
	public String getType(){
		return this.type;
	}
	
	public Object getValue(){
		return this.value;
	}
	
	public String toString(){
		String val;
		if (value == null){
			val = "N/A";
		}else{
			val = value.toString();
		}
		return "Type: "+type+" Value: "+val;
	}
}
