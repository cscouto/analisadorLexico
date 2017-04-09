//Tiago Henrique Do Couto
//Caique Souza
public class AnSintatico {
	private AnLexico lexico;
	
	public AnSintatico(String filename) {
		lexico = new AnLexico(filename);
	}

	public void start() {
		Token t = lexico.nextToken();
		
		while (t.getTokenCode() != TokenID.EOF){
			t.print();
			t = lexico.nextToken();
		}
		TabSymbols.getInstance().printTable();
		
		ErrorHandler.getInstance().printErros();
	}
	
}
