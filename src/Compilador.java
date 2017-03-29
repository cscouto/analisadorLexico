public class Compilador {
  public static void main (String args[]){
	  AnSintatico sintatico = new AnSintatico(args[0]);
	  sintatico.start();
  }
}
