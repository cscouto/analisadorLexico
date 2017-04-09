//Tiago Henrique Do Couto
//Caique Souza
import java.util.ArrayList;

public class ErrorHandler {
	private static ErrorHandler instance = new ErrorHandler();
	private ArrayList<String> erros = new ArrayList<>();
	public static ErrorHandler getInstance() {
		return instance;
	}
	
	public void printErros(){
		System.out.println("\n-----Relatorio Erros-----");
		for (String erro: erros){
			System.out.println(erro);
		}
	}
	
	public void addErro(String erro){
		erros.add(erro);
	}
}
