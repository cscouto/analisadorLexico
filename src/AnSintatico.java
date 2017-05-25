//Tiago Henrique Do Couto
//Caique Souza
public class AnSintatico {
	private AnLexico lexico;
	
	public AnSintatico(String filename) {
		lexico = new AnLexico(filename);
	}

	public void start() {
		Token t = lexico.nextToken();
		while (t == null){
			t = lexico.nextToken();
		}
		while (t.getTokenCode() != TokenID.EOF){
			t.print();
			if (t.getTokenCode() == TokenID.PROGRAM){
				//TODO valida token
				t = lexico.nextToken();
				if (t.getTokenCode() == TokenID.ID){
					//TODO valida token
					t = lexico.nextToken();
					if (t.getTokenCode() == TokenID.TERM){
						//TODO valida token
						processaBLOCO();
						t = lexico.nextToken();
						if (t.getTokenCode() == TokenID.END_PROG){
							//TODO valida token
							t = lexico.nextToken();
							if (t.getTokenCode() == TokenID.TERM){
								//TODO valida token
							}else{
								//TODO tratar erro
							}
						}else{
							//TODO tratar erro
						}
					}else{
						//TODO tratar erro
					}
				}else{
					//TODO tratar erro
				}
			}else{
				//TODO tratar erro
			}
			
			while (t == null){
				t = lexico.nextToken();
			}
		}
		TabSymbols.getInstance().printTable();
		
		ErrorHandler.getInstance().printErros();
	}

	private void processaBLOCO() {
		Token t = lexico.nextToken();
		if (t.getTokenCode() == TokenID.BEGIN){
			//TODO valida token
			processaCMDS();
			t = lexico.nextToken();
			if (t.getTokenCode() == TokenID.END){
				//TODO valida token
			}else{
				//TODO tratar erro
			}
		}else{
			processaCMD();
		}
	}

	private void processaCMD() {
		Token t = lexico.nextToken();
		
	}

	private void processaCMDS() {
		Token t = lexico.nextToken();
		if (t.getTokenCode() == TokenID.DECLARE){
			//TODO valida toke
		}else if (t.getTokenCode() == TokenID.IF){
			
		}else if (t.getTokenCode() == TokenID.ID){
			
		}
	}
	
}
