package Web2.Tp1.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class FavoritosEntradaDto {

  private int idProducto;
  private int notaPersonal;
  private LocalDate fechaAgregado;

  public FavoritosEntradaDto(int idProducto, int notaPersonal, LocalDate fechaAgregado){

    this.idProducto = idProducto; 
    this.notaPersonal = notaPersonal;
    this.fechaAgregado = fechaAgregado;
  }

}
