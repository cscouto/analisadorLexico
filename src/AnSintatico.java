//Tiago Henrique Do Couto
//Caique Souza

public class AnSintatico {
	private AnLexico lexico;
	private Token bufferToken;
	private TabelaPreditiva tabelaPreditiva;
	Token t;

	public AnSintatico(String filename) {
		lexico = new AnLexico(filename);
		tabelaPreditiva = TabelaPreditiva.getInstance();
	}

	public void start() {
		processaS();
		// TabSymbols.getInstance().printTable();
		 ErrorHandler.getInstance().printErros();
	}
	

	// program id term BLOCO end_prog term
	private void processaS() {
		t = lexico.nextToken();
		if (t.getTokenCode() == TokenID.PROGRAM) {
			t = lexico.nextToken();
			if (t.getTokenCode() == TokenID.ID) {
				t = lexico.nextToken();
				if (t.getTokenCode() == TokenID.TERM) {
					processaBLOCO();
					if (bufferToken != null) {
						t = bufferToken;
						bufferToken = null;
					} else {
						t = lexico.nextToken();
					}
					if (t.getTokenCode() == TokenID.END_PROG) {
						t = lexico.nextToken();
						if (t.getTokenCode() == TokenID.TERM) {
						} else {
							trataErros(t.getLexema(), "TERM was expected :", t.getLin(), t.getCol());
						}
					} else {
						trataErros(t.getLexema(), "END_PROG was expected :", t.getLin(), t.getCol());
					}
				} else {
					trataErros(t.getLexema(), "TERM was expected :", t.getLin(), t.getCol());
				}
			} else {
				trataErros(t.getLexema(), "ID was expected :", t.getLin(), t.getCol());
			}
		} else {
			trataErros(t.getLexema(), "PROGRAM was expected :", t.getLin(), t.getCol());
		}
	}

	// begin CMDS end
	// CMD
	private void processaBLOCO() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.BEGIN) {
			processaCMDS();
			if (bufferToken != null) {
				t = bufferToken;
				bufferToken = null;
			} else {
				t = lexico.nextToken();
			}
			if (t.getTokenCode() == TokenID.END) {
			} else {
				trataErros(t.getLexema(), "END was expected :", t.getLin(), t.getCol());
			}
		} else {
			bufferToken = t;
			processaCMD();
		}
	}

	// DECL | COND | REP | ATRIB
	private void processaCMD() {
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
			if (!t.isDeclared()){
				trataErros(t.getLexema(), "A ID NAO foi declarada anteriormente: ", t.getLin(), t.getCol());
			}
			processaATRIB();
			break;
		case IF:
			processaCOND();
			break;
		default:
			trataErros(t.getLexema(), "TOKEN was unexpected :", t.getLin(), t.getCol());
			break;
		}
	}

	// if l_par EXP_L r_par then BLOCO CNDB
	private void processaCOND() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.IF) {
			t = lexico.nextToken();
			if (t.getTokenCode() == TokenID.L_PAR) {
				processaEXP_L();
				if (bufferToken != null) {
					t = bufferToken;
					bufferToken = null;
				} else {
					t = lexico.nextToken();
				}
				if (t.getTokenCode() == TokenID.R_PAR) {
					t = lexico.nextToken();
					if (t.getTokenCode() == TokenID.THEN) {
						processaBLOCO();
						processaCNDB();
					} else {
						trataErros(t.getLexema(), "THEN was expected :", t.getLin(), t.getCol());
					}
				} else {
					trataErros(t.getLexema(), ") was expected :", t.getLin(), t.getCol());
				}
			} else {
				trataErros(t.getLexema(), "( was expected :", t.getLin(), t.getCol());
			}
		} else {
			trataErros(t.getLexema(), "IF was expected :", t.getLin(), t.getCol());
		}
	}

	// while id for else BLOCO if declare end end_prog
	//end end_prog declare if id for while else

	private void processaCNDB() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		switch (t.getTokenCode()) {
		case ELSE:
			processaBLOCO();
			break;
		case WHILE:
			bufferToken = t;
			break;
		case ID:
			bufferToken = t;
			break;
		case FOR:
			bufferToken = t;
			break;
		case IF:
			bufferToken = t;
			break;
		case DECLARE:
			bufferToken = t;
			break;
		case END:
			bufferToken = t;
			break;
		case END_PROG:
			bufferToken = t;
			break;
		default:
			trataErros(t.getLexema(), "TOKEN was unexpected :", t.getLin(), t.getCol());
			break;
		}
	}

	// id attrib_op ATRIB2 term
	private void processaATRIB() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.ID) {
			if (!t.isDeclared()){
				trataErros(t.getLexema(), "A ID NAO foi declarada anteriormente: ", t.getLin(), t.getCol());
			}
			t = lexico.nextToken();
			if (t.getTokenCode() == TokenID.ATTRIB_OP) {
				processaATRIB2();
				if (bufferToken != null) {
					t = bufferToken;
					bufferToken = null;
				} else {
					t = lexico.nextToken();
				}
				if (t.getTokenCode() == TokenID.TERM) {
				} else {
					trataErros(t.getLexema(), "TERM was expected :", t.getLin(), t.getCol());
				}
			} else {
				trataErros(t.getLexema(), "OPERATOR was expected :", t.getLin(), t.getCol());
			}
		} else {
			trataErros(t.getLexema(), "ID was expected :", t.getLin(), t.getCol());
		}
	}

	// l_par EXP_N r_par //id ATR_ID //num_float ATR_VN //num_int ATR_VN
	// //literal
	private void processaATRIB2() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		switch (t.getTokenCode()) {
		case L_PAR:
			processaEXP_N();
			if (bufferToken != null) {
				t = bufferToken;
				bufferToken = null;
			} else {
				t = lexico.nextToken();
			}
			if (t.getTokenCode() == TokenID.R_PAR) {
				processaEXP_N2();
			} else {
				trataErros(t.getLexema(), ") was expected :", t.getLin(), t.getCol());
			}
			break;
		case ID:
			if (!t.isDeclared()){
				trataErros(t.getLexema(), "A ID NAO foi declarada anteriormente: ", t.getLin(), t.getCol());
			}
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
			break;
		case LOGIC_VALUE:
			break;
		default:
			trataErros(t.getLexema(), "TOKEN was unexpected :", t.getLin(), t.getCol());
			break;
		}
	}

	//
	private void processaATR_VN() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		switch (t.getTokenCode()) {
		case R_PAR:
			bufferToken = t;
			processaEXP_N2();
			break;
		case WHILE:
			bufferToken = t;
			processaEXP_N2();
			break;
		case TO:
			bufferToken = t;
			processaEXP_N2();
			break;
		case ID:
			bufferToken = t;
			processaEXP_N2();
			break;
		case FOR:
			bufferToken = t;
			processaEXP_N2();
			break;
		case MULTDIV_OP:
			bufferToken = t;
			processaEXP_N2();
			break;
		case ADDSUB_OP:
			bufferToken = t;
			processaEXP_N2();
			break;
		case REL_OP:
			bufferToken = t;
			processaEXP_N2();
			break;
		case TERM:
			bufferToken = t;
			processaEXP_N2();
			break;
		case IF:
			bufferToken = t;
			processaEXP_N2();
			break;
		case DECLARE:
			bufferToken = t;
			processaEXP_N2();
			break;
		case BEGIN:
			bufferToken = t;
			processaEXP_N2();
			break;
		default:
			trataErros(t.getLexema(), "TOKEN was unexpected :", t.getLin(), t.getCol());
			break;
		}
		if (t.getTokenCode() == TokenID.REL_OP) {
			bufferToken = t;
			processaAT_VN2();
		} else if (t.getTokenCode() == TokenID.TERM) {
			bufferToken = t;
			processaAT_VN2();
		}
		
	}

	// rel_op EXP_N | term
	private void processaAT_VN2() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.REL_OP) {
			processaEXP_N();
		} else if (t.getTokenCode() == TokenID.TERM) {
			bufferToken = t;
		} else {
			trataErros(t.getLexema(), "TERM was expected :", t.getLin(), t.getCol());
		}
	}

	private void processaEXP_N2() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		switch (t.getTokenCode()) {
		case R_PAR:
			bufferToken = t;
			break;
		case WHILE:
			bufferToken = t;
			break;
		case TO:
			bufferToken = t;
			break;
		case ID:
			bufferToken = t;
			break;
		case FOR:
			bufferToken = t;
			break;
		case MULTDIV_OP:
			processaEXP_N();
			break;
		case ADDSUB_OP:
			processaEXP_N();
			break;
		case REL_OP:
			bufferToken = t;
			break;
		case LOGIC_OP:
			bufferToken = t;
			break;
		case TERM:
			bufferToken = t;
			break;
		case IF:
			bufferToken = t;
			break;
		case DECLARE:
			bufferToken = t;
			break;
		case BEGIN:
			bufferToken = t;
			break;
		default:
			trataErros(t.getLexema(), "TOKEN was unexpected :", t.getLin(), t.getCol());
			break;
		}
	}

	// num_int | num_float
	private void processaVAL_N() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.NUM_INT) {

		} else if (t.getTokenCode() == TokenID.NUM_FLOAT) {

		} else {
			trataErros(t.getLexema(), "FLOAT or INT was expected :", t.getLin(), t.getCol());
		}
	}

	// expL2
	private void processaATR_ID() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.TERM){
			bufferToken = t;
		}else if (t.getTokenCode() == TokenID.REL_OP){
			bufferToken = t;
			processaEXP_L2();
		} else if (t.getTokenCode() == TokenID.R_PAR) {
			bufferToken = t;
			processaEXP_L2();
		} else if (t.getTokenCode() == TokenID.LOGIC_OP) {
			bufferToken = t;
			processaEXP_L2();
		} else if (t.getTokenCode() == TokenID.TERM) {
			bufferToken = t;
			processaEXP_L2();
		}else{
			trataErros(t.getLexema(), "TOKEN was unexpected", t.getLin(), t.getCol());
		}
		
	}

	// rel_op EXP_N | EXP_L3
	private void processaEXP_L2() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.REL_OP) {
			processaEXP_N();
			processaEXP_L3();
		} else if (t.getTokenCode() == TokenID.R_PAR) {
			bufferToken = t;
			processaEXP_L3();
		} else if (t.getTokenCode() == TokenID.LOGIC_OP) {
			bufferToken = t;
			processaEXP_L3();
		} else if (t.getTokenCode() == TokenID.TERM) {
			bufferToken = t;
			processaEXP_L3();
		}else{
			trataErros(t.getLexema(), "TOKEN was unexpected", t.getLin(), t.getCol());
		}
	}

	// r_par | logic_op EXP_L | term
	private void processaEXP_L3() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.R_PAR) {
			bufferToken = t;
		} else if (t.getTokenCode() == TokenID.LOGIC_OP) {
			processaEXP_L();
		} else if (t.getTokenCode() == TokenID.TERM) {
			bufferToken = t;
		} else {
			trataErros(t.getLexema(), "TERM was expected :", t.getLin(), t.getCol());
		}
	}

	// l_par EXP_N r_par | id | num_float EXP_N2 | num_int EXP_N2
	private void processaEXP_N() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		switch (t.getTokenCode()) {
		case L_PAR:

			processaEXP_N();
			if (bufferToken != null) {
				t = bufferToken;
				bufferToken = null;
			} else {
				t = lexico.nextToken();
			}
			if (t.getTokenCode() == TokenID.R_PAR) {
				processaEXP_N2();
			} else {
				trataErros(t.getLexema(), ") was expected :", t.getLin(), t.getCol());
			}
			break;
		case ID:
			if (!t.isDeclared()){
				trataErros(t.getLexema(), "A ID NAO foi declarada anteriormente: ", t.getLin(), t.getCol());
			}
			processaEXP_N2();
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
			trataErros(t.getLexema(), "TOKEN was unexpected :", t.getLin(), t.getCol());
			break;
		}
	}

	// declare id type term
	private void processaDECL() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.DECLARE) {

			t = lexico.nextToken();
			if (t.getTokenCode() == TokenID.ID) {
				if (t.isDeclared()){
					trataErros(t.getLexema(), "A ID ja foi declarada anteriormente: ", t.getLin(), t.getCol());
				}else{
					t.setIsDeclared(true);
				}
				t = lexico.nextToken();
				if (t.getTokenCode() == TokenID.TYPE) {

					t = lexico.nextToken();
					if (t.getTokenCode() == TokenID.TERM) {

					} else {
						trataErros(t.getLexema(), "TERM was expected :", t.getLin(), t.getCol());
					}
				} else {
					trataErros(t.getLexema(), "TYPE was expected :", t.getLin(), t.getCol());
				}
			} else {
				trataErros(t.getLexema(), "ID was expected :", t.getLin(), t.getCol());
			}
		} else {
			trataErros(t.getLexema(), "DECLARE was expected :", t.getLin(), t.getCol());
		}
	}

	private void processaREP() {
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
			trataErros(t.getLexema(), "WHILE was expected :", t.getLin(), t.getCol());
		}
	}

	// while l_par EXP_L r_par BLOCO
	private void processaREPW() {
		if (bufferToken == null) {
			t = lexico.nextToken();
		} else {
			t = bufferToken;
			bufferToken = null;
		}
		if (t.getTokenCode() == TokenID.WHILE) {
			t = lexico.nextToken();
			if (t.getTokenCode() == TokenID.L_PAR) {
				processaEXP_L();
				if (bufferToken != null) {
					t = bufferToken;
					bufferToken = null;
				} else {
					t = lexico.nextToken();
				}
				if (t.getTokenCode() == TokenID.R_PAR) {
					processaBLOCO();
				} else {
					trataErros(t.getLexema(), ") was expected :", t.getLin(), t.getCol());
				}
			} else {
				trataErros(t.getLexema(), "( was expected :", t.getLin(), t.getCol());
			}
		} else {
			trataErros(t.getLexema(), "WHILE was expected :", t.getLin(), t.getCol());
		}
	}

	// id EXP_L2 | num_float EXP_N2 rel_op EXP_N | num_int EXP_N2 rel_op EXP_N |
	// logic_val EXP_L2 | l_par EXP_N r_par
	private void processaEXP_L() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		switch (t.getTokenCode()) {
		case ID:
			if (!t.isDeclared()){
				trataErros(t.getLexema(), "A ID NAO foi declarada anteriormente: ", t.getLin(), t.getCol());
			}
			processaEXP_L2();
			break;
		case NUM_FLOAT:
			bufferToken = t;
			processaVAL_N();
			processaEXP_N2();
			if (bufferToken != null) {
				t = bufferToken;
				bufferToken = null;
			} else {
				t = lexico.nextToken();
			};
			if (t.getTokenCode() == TokenID.REL_OP) {

				processaEXP_N();
			} else {
				trataErros(t.getLexema(), "REL_OP was expected :", t.getLin(), t.getCol());	
			}
			break;
		case NUM_INT:
			bufferToken = t;
			processaVAL_N();
			processaEXP_N2();
			if (bufferToken != null) {
				t = bufferToken;
				bufferToken = null;
			} else {
				t = lexico.nextToken();
			}
			if (t.getTokenCode() == TokenID.REL_OP) {

				processaEXP_N();
			} else {
				trataErros(t.getLexema(), "REL_OP was expected :", t.getLin(), t.getCol());
			}
			break;
		case LOGIC_VALUE:

			processaEXP_L2();
			break;
		case L_PAR:

			processaEXP_N();
			if (bufferToken != null) {
				t = bufferToken;
				bufferToken = null;
			} else {
				t = lexico.nextToken();
			}
			if (t.getTokenCode() == TokenID.R_PAR) {
			} else {
				trataErros(t.getLexema(), ") was expected :", t.getLin(), t.getCol());
			}
			break;
		default:
			trataErros(t.getLexema(), "TOKEN was unexpected :", t.getLin(), t.getCol());
			break;
		}
	}

	// for id attrib_op EXP_N to EXP_N BLOCO
	private void processaREPF() {
		if (bufferToken == null) {
			t = lexico.nextToken();
		} else {
			t = bufferToken;
			bufferToken = null;
		}
		if (t.getTokenCode() == TokenID.FOR) {

			t = lexico.nextToken();
			if (t.getTokenCode() == TokenID.ID) {
				if (!t.isDeclared()){
					trataErros(t.getLexema(), "A ID NAO foi declarada anteriormente: ", t.getLin(), t.getCol());
				}
				t = lexico.nextToken();
				if (t.getTokenCode() == TokenID.ATTRIB_OP) {

					processaEXP_N();
					if (bufferToken != null) {
						t = bufferToken;
						bufferToken = null;
					} else {
						t = lexico.nextToken();
					}
					if (t.getTokenCode() == TokenID.TO) {

						processaEXP_N();
						processaBLOCO();
					} else {
						trataErros(t.getLexema(), "TO was expected :", t.getLin(), t.getCol());
					}
				} else {
					trataErros(t.getLexema(), "OPERATOR was expected :", t.getLin(), t.getCol());
				}
			} else {
				trataErros(t.getLexema(), "ID was expected :", t.getLin(), t.getCol());
			}
		} else {
			trataErros(t.getLexema(), "FOR was expected :", t.getLin(), t.getCol());
		}
	}

	// while l_par EXP_L r_par BLOCO CMDS | id IDFLW | for id attrib_op EXP_N to
	// EXP_N BLOCO CMDS | else |if IFLLW | declare DCFLW |end | end_prog
	private void processaCMDS() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		switch (t.getTokenCode()) {
		case DECLARE:
			processaDCFLW();
			break;
		case IF:
			processaIFFLW();
			processaCMDS();
			break;
		case WHILE:

			bufferToken = t;
			processaREPW();
			processaCMDS();
			break;
		case FOR:

			bufferToken = t;
			processaREPF();
			processaCMDS();
			break;
		case ID:
			if (!t.isDeclared()){
				trataErros(t.getLexema(), "A ID NAO foi declarada anteriormente: ", t.getLin(), t.getCol());
			}
			processaIDFLW();
			break;
		case END:
			bufferToken = t;
			break;
		default:
			trataErros(t.getLexema(), "TOKEN was unexpected :", t.getLin(), t.getCol());
			break;
		}
	}

	// attrib_op ATRIB2 term CMDS
	private void processaIDFLW() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.ATTRIB_OP) {

			processaATRIB2();
			if (bufferToken != null) {
				t = bufferToken;
				bufferToken = null;
			} else {
				t = lexico.nextToken();
			}
			if (t.getTokenCode() == TokenID.TERM) {
				processaCMDS();
			} else {
				trataErros(t.getLexema(), "TERM was expected :", t.getLin(), t.getCol());
			}
		} else {
			trataErros(t.getLexema(), "OPERATOR was expected :", t.getLin(), t.getCol());
		}
	}

	// l_par EXP_L r_par then BLOCO CMDS
	private void processaIFFLW() {
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.L_PAR) {
			processaEXP_L();
			if (bufferToken != null) {
				t = bufferToken;
				bufferToken = null;
			} else {
				t = lexico.nextToken();
			}
			if (t.getTokenCode() == TokenID.R_PAR) {
				t = lexico.nextToken();
				if (t.getTokenCode() == TokenID.THEN) {
					processaBLOCO();
					processaCNDB();
				} else {
					trataErros(t.getLexema(), "THEN was expected :", t.getLin(), t.getCol());
				}
			} else {
				trataErros(t.getLexema(), ") was expected :", t.getLin(), t.getCol());
			}
		} else {
			trataErros(t.getLexema(), "( was unexpected :", t.getLin(), t.getCol());
		}
	}

	// id type term CMDS
	private void processaDCFLW() {
		
		if (bufferToken != null) {
			t = bufferToken;
			bufferToken = null;
		} else {
			t = lexico.nextToken();
		}
		if (t.getTokenCode() == TokenID.ID) {
			if (t.isDeclared()){
				trataErros(t.getLexema(), "A ID ja foi declarada anteriormente: ", t.getLin(), t.getCol());
			}else{
				t.setIsDeclared(true);
			}
			t = lexico.nextToken();
			if (t.getTokenCode() == TokenID.TYPE) {
				t = lexico.nextToken();
				if (t.getTokenCode() == TokenID.TERM) {
					processaCMDS();
				} else {
					trataErros(t.getLexema(), "TERM was unexpected :", t.getLin(), t.getCol());
				}
			} else {
				trataErros(t.getLexema(), "TYPE was unexpected :", t.getLin(), t.getCol());
			}
		} else {
			trataErros(t.getLexema(), "ID was unexpected :", t.getLin(), t.getCol());
		}
	}
	
	private void trataErros(String dados, String erro, long lin, long col) {
		StringBuilder erroB = new StringBuilder();
		erroB.append(erro);
		erroB.append(dados);
		erroB.append(" linha: ");
		erroB.append(lin);
		erroB.append(" col: ");
		erroB.append(col);
		ErrorHandler.getInstance().addErro(erroB.toString());
	}
	
}
