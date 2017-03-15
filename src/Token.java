
public class Token {
	private int tokenCode; 
	private String lexema; 
	private int lin; 
	private int col;
	
	public Token (int tokenCode, String lexema, int lin, int col){
		this.tokenCode 	= tokenCode;
		this.lexema 	= lexema;
		this.lin 		= lin;
		this.col    	= col;
	}
	
	public int getCol() {
		return col;
	}
	
	public String getLexema() {
		return lexema;
	}
	
	public int getLin() {
		return lin;
	}
	
	public int getTokenCode() {
		return tokenCode;
	}
	
}
