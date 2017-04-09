//Tiago Henrique Do Couto
//Caique Souza

public class Token {
	private TokenID tokenCode; 
	private String lexema; 
	private long lin; 
	private long col;
	
	public Token (TokenID tokenCode, String lexema, long lin, long col){
		this.tokenCode 	= tokenCode;
		this.lexema 	= lexema;
		this.lin 		= lin;
		this.col    	= col;
	}
	public Token(){
		
	}
	
	public void setCol(long col) {
		this.col = col;
	}
	
	public void setLin(long lin) {
		this.lin = lin;
	}
	
	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	
	
	public void setTokenCode(TokenID tokenCode) {
		this.tokenCode = tokenCode;
	}
	
	public long getCol() {
		return col;
	}
	
	public String getLexema() {
		return lexema;
	}
	
	public long getLin() {
		return lin;
	}
	
	public TokenID getTokenCode() {
		return tokenCode;
	}

	public void print() {
		System.out.println(this.lexema);
	}
	
}
