package Web2.Tp1.exception;

public class NoAutorizadoException  extends RuntimeException{
  public NoAutorizadoException (String mensaje){
    super(mensaje);
  }
}
