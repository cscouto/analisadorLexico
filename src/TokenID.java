
public enum TokenID {
	NUM_INT(1),
	NUM_FLOAT(2),
	LITERAL(3),
	REL_OP(4),
	ADDSUB_OP(5),
	LOGIC_VALUE(12),
	LOGIC_OP(13),
	TYPE(14),
	PROGRAM(15),
	END_PROG(16),
	BEGIN(17),
	END(18),
	IF(19),
	THEN(20),
	ELSE(21),
	FOR(22),
	WHILE(23),
	DECLARE(24),
	TO(25),
	EOF(26);
	
	private int valor;
	
	TokenID(int valor){
		this.valor = valor;
	}
}
