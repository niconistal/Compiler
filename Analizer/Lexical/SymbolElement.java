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
	protected String use;
	protected String varType;
	
	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public String getVarType() {
		return varType;
	}

	public void setVarType(String varType) {
		this.varType = varType;
	}

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
