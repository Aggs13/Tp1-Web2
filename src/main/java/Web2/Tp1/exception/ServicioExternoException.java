package Web2.Tp1.exception;

public class ServicioExternoException extends RuntimeException{
  public ServicioExternoException(String mensaje, Throwable causa) {
    super(mensaje, causa);
  }
}
