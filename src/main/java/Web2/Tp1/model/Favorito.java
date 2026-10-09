package Web2.Tp1.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class Favorito {
  
  private int id;
  private int idProducto;
  private String notaPersonal;
  private LocalDate fechaAgregado;
  private int listaId;

  public Favorito() {}

}
