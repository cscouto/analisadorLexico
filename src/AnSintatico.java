//Tiago Henrique Do Couto
//Caique Souza
public class AnSintatico {
	private AnLexico lexico;
	private Token bufferToken;

	public AnSintatico(String filename) {
		lexico = new AnLexico(filename);
	}

	public void start() {
		processaS();
		// TabSymbols.getInstance().printTable();
		// ErrorHandler.getInstance().printErros();
	}

	//program id term BLOCO end_prog term
	private void processaS() {
		Token t = lexico.nextToken();
		if (t.getTokenCode() == TokenID.PROGRAM) {
			// TODO valida token
			t = lexico.nextToken();
			if (t.getTokenCode() == TokenID.ID) {
				// TODO valida token
				t = lexico.nextToken();
				if (t.getTokenCode() == TokenID.TERM) {
					// TODO valida token
					processaBLOCO();
					t = lexico.nextToken();
					if (t.getTokenCode() == TokenID.END_PROG) {
						// TODO valida token
						t = lexico.nextToken();
						if (t.getTokenCode() == TokenID.TERM) {
							// TODO valida token
						} else {
							// TODO tratar erro
						}
					} else {
						// TODO tratar erro
					}
				} else {
					// TODO tratar erro
				}
			} else {
				// TODO tratar erro
			}
		} else {
			// TODO tratar erro
		}
	}

	// begin CMDS end
	// CMD
	private void processaBLOCO() {
		Token t = lexico.nextToken();
		if (t.getTokenCode() == TokenID.BEGIN) {
			// TODO valida token
			processaCMDS();
			t = lexico.nextToken();
			if (t.getTokenCode() == TokenID.END) {
				// TODO valida token
			} else {
				// TODO tratar erro
			}
		} else {
			bufferToken = t;
			processaCMD();
		}
	}

	// DECL | COND | REP | ATRIB
	private void processaCMD() {
		Token t;
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		bufferToken = t;
		switch (t.getTokenCode()) {
		case WHILE:
			processaREP();
			break;
		case FOR:
			processaREP();
			break;
		case DECLARE:
			processaDECL();
			break;
		case ID:
			processaATRIB();
			break;
		case IF:
			processaCOND();
			break;
		default:
			//TODO ERROR
			break;
		}
	}

	// if l_par EXP_L r_par then BLOCO CNDB
	private void processaCOND() {
		Token t;
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.IF) {
			// TODO valida token
			t = lexico.nextToken();
			if (t.getTokenCode() == TokenID.L_PAR) {
				// TODO valida token
				processaEXP_L();
				t = lexico.nextToken();
				if (t.getTokenCode() == TokenID.R_PAR) {
					// TODO valida token
					t = lexico.nextToken();
					if (t.getTokenCode() == TokenID.THEN) {
						// TODO valida token
						processaBLOCO();
						processaCNDB();
					}else{
						//TODO error
					}
				}else{
					//TODO error
				}
			}else{
				//TODO error
			}
		}else{
			//TODO error
		}
	}
	
	//while	id 	for	else BLOCO	if	declare end	end_prog 
	private void processaCNDB() {
		Token t = lexico.nextToken();
		switch(t.getTokenCode()){
		case ELSE:
			//TODO valida token
			processaBLOCO();
			break;
		case WHILE:
			//TODO valida token
			break;
		case ID:
			//TODO valida token
			break;
		case FOR:
			//TODO valida token
			break;
		case IF:
			//TODO valida token
			break;
		case DECLARE:
			//TODO valida token
			break;
		case END:
			//TODO valida token
			break;
		case END_PROG:
			//TODO valida token
			break;
		default:
			//TODO error
			break;
		}

	}
	
	//id attrib_op ATRIB2 term
	private void processaATRIB() {
		Token t;
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.ID){
			//TODO valida token
			t = lexico.nextToken();
			if (t.getTokenCode() == TokenID.ATTRIB_OP){
				//TODO valida token;
				processaATRIB2();
				t = lexico.nextToken();
				if (t.getTokenCode() == TokenID.TERM){
					//TODO valida token
				}else{
					//TODO error
				}
			}else{
				//TODO error
			}
		}else{
			//TODO error
		}
	}
	
	//l_par EXP_N r_par //id ATR_ID  //num_float ATR_VN	 //num_int ATR_VN //literal
	private void processaATRIB2() {
		Token t =  lexico.nextToken();
		switch(t.getTokenCode()){
		case L_PAR:
			//TODO valida token
			processaEXP_N();
			t = lexico.nextToken();
			if (t.getTokenCode() == TokenID.R_PAR){
				//TODO valida token
			}else{
				//TODO error
			}
			break;
		case ID:
			//TODO valida token
			processaATR_ID();
			break;
		case NUM_FLOAT:
			bufferToken = t;
			processaVAL_N();
			processaATR_VN();
			break;
		case NUM_INT:
			bufferToken = t;
			processaVAL_N();
			processaATR_VN();
			break;
		case LITERAL:
			//TODO valida token
			break;
		default:
			//TODO ERROR
			break;
		}
	}

	//
	private void processaATR_VN() {
		processaEXP_N2();
		processaAT_VN2();
	}
	
	//rel_op EXP_N | term
	private void processaAT_VN2() {
		Token t = lexico.nextToken();
		if (t.getTokenCode() == TokenID.REL_OP){
			//TODO valida token
			processaEXP_N();
		}else if (t.getTokenCode() == TokenID.TERM){
			//TODO valida token
		}else{
			//TODO erro
		}
	}

	//r_par | while | to | id | for | multdiv_op EXP_N | addsub_op EXP_N | rel_op |	term | if |	declare | begin 
	private void processaEXP_N2() {
		Token t =  lexico.nextToken();
		switch(t.getTokenCode()){
			case R_PAR:
				//TODO valida token
				break;
			case WHILE:
				//TODO valida token
				break;
			case TO:
				//TODO valida token
				break;
			case ID:
				//TODO valida token
				break;
			case FOR:
				//TODO valida token
				break;
			case MULTDIV_OP:
				//TODO valida token
				bufferToken = t;
				processaEXP_N();
				break;
			case ADDSUB_OP:
				//TODO valida token
				bufferToken = t;
				processaEXP_N();
				break;
			case REL_OP:
				//TODO valida token
				break;
			case TERM:
				//TODO valida token
				break;
			case IF:
				//TODO valida token
				break;
			case DECLARE:
				//TODO valida token
				break;
			case BEGIN:
				//TODO valida token
				break;
			default:
				//TODO ERROR
				break;
		}
	}
	
	//num_int | num_float
	private void processaVAL_N() {
		Token t;
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.NUM_FLOAT){
			//TODO valida token
		}else if (t.getTokenCode() == TokenID.NUM_FLOAT){
			//TODO valida token
		}else{
			//TODO error
		}
	}
	
	//expL2
	private void processaATR_ID() {
		processaEXP_L2();
	}
	
	//rel_op EXP_N | EXP_L3
	private void processaEXP_L2() {
		Token t = lexico.nextToken();
		if (t.getTokenCode() == TokenID.REL_OP){
			//TODO valida token
			processaEXP_N();
		}else{
			bufferToken = t;
			processaEXP_L3();
		}
	}

	//r_par | logic_op EXP_L | term 
	private void processaEXP_L3() {
		Token t;
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.R_PAR){
			//TODO valida token
		}else if (t.getTokenCode() == TokenID.LOGIC_OP){
			//TODO valida token
			processaEXP_L();
		}else if (t.getTokenCode() == TokenID.TERM){
			//TODO valida token
		}else{
			//TODO error
		}
	}
	//l_par EXP_N r_par | id | num_float EXP_N2 | num_int EXP_N2
	private void processaEXP_N() {
		Token t;
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		switch(t.getTokenCode()){
			case L_PAR:
				//TODO valida token
				processaEXP_N();
				t = lexico.nextToken();
				if (t.getTokenCode()==TokenID.R_PAR){
					//TODO valida token
				}else{
					//TODO error
				}
				break;
			case ID:
				//TODO valida token
				break;
			case NUM_FLOAT:
				bufferToken = t;
				processaVAL_N();
				processaEXP_N2();
				break;
			case NUM_INT:
				bufferToken = t;
				processaVAL_N();
				processaEXP_N2();
				break;
			default:
				//TODO error
				break;
		}
	}

	//declare id type term 
	private void processaDECL() {
		Token t;
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.DECLARE){
			//TODO valida token
			t = lexico.nextToken();
			if (t.getTokenCode() == TokenID.ID){
				//TODO valida token
				t = lexico.nextToken();
				if (t.getTokenCode() == TokenID.TYPE){
					//TODO valida token
					t = lexico.nextToken();
					if (t.getTokenCode() == TokenID.TERM){
						//TODO valida token
					}else{
						//TODO error
					}
				}else{
					//TODO error
				}
			}else{
				//TODO error
			}
		}else{
			//TODO error
		}
	}
	private void processaREP() {
		Token t;
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.FOR) {
			bufferToken = t;
			processaREPF();
		} else if (t.getTokenCode() == TokenID.WHILE) {
			bufferToken = t;	
			processaREPW();
		} else {
			// TODO ERROR
		}
	}

	// while l_par EXP_L r_par BLOCO
	private void processaREPW() {
		Token t;
		if (bufferToken == null) {
			t = lexico.nextToken();
		} else {
			t = bufferToken;
			bufferToken = null;
		}
		if (t.getTokenCode() == TokenID.WHILE) {
			// TODO valida token
			t = lexico.nextToken();
			if (t.getTokenCode() == TokenID.L_PAR) {
				// TODO valida token
				processaEXP_L();
				t = lexico.nextToken();
				if (t.getTokenCode() == TokenID.R_PAR) {
					// TODO valida token
					processaBLOCO();
				} else {
					// TODO error
				}
			} else {
				// TODO error
			}
		} else {
			// TODO error
		}
	}

	//id EXP_L2 | num_float EXP_N2 rel_op EXP_N | num_int EXP_N2 rel_op EXP_N | logic_val EXP_L2 |  l_par EXP_N r_par 
	private void processaEXP_L() {
		Token t = lexico.nextToken();
		switch(t.getTokenCode()){
			case ID:
				//TODO valida token
				processaEXP_L2();
				break;
			case NUM_FLOAT:
				bufferToken = t;
				processaVAL_N();
				processaEXP_N2();
				t = lexico.nextToken();
				if (t.getTokenCode() == TokenID.REL_OP){
					//TODO valida token
					processaEXP_N();
				}else{
					//TODO error
				}
				break;
			case NUM_INT:
				bufferToken = t;
				processaVAL_N();
				processaEXP_N2();
				t = lexico.nextToken();
				if (t.getTokenCode() == TokenID.REL_OP){
					//TODO valida token
					processaEXP_N();
				}else{
					//TODO error
				}
				break;
			case LOGIC_VALUE:
				//TODO valida token 
				processaEXP_L2();
				break;
			case L_PAR:
				//TODO valida token
				processaEXP_N();
				t = lexico.nextToken();
				if (t.getTokenCode() == TokenID.R_PAR){
					//TODO valida dado
				}else{
					//TODO error
				}
				break;
			default:
				//TODO error
				break;
		}
	}
	
	//for id attrib_op EXP_N to EXP_N BLOCO
	private void processaREPF() {
		Token t;
		if (bufferToken == null) {
			t = lexico.nextToken();
		} else {
			t = bufferToken;
			bufferToken = null;
		}
	}

	private void processaCMDS() {
		Token t = lexico.nextToken();
		if (t.getTokenCode() == TokenID.DECLARE) {
			// TODO valida toke
		} else if (t.getTokenCode() == TokenID.IF) {

		} else if (t.getTokenCode() == TokenID.ID) {

		}
	}

}
