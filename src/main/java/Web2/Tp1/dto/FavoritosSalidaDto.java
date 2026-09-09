package Web2.Tp1.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class FavoritosSalidaDto {

  ProductoRespuestaDto productoFavorito;
  private double notaPersonal;
  private LocalDate fechaAgregado;


  public FavoritosSalidaDto(){}

  public FavoritosSalidaDto(int notaPersonal, LocalDate fechaAgregado,ProductoRespuestaDto producto){

    this.productoFavorito = producto;
    this.notaPersonal = notaPersonal;
    this.fechaAgregado = fechaAgregado;
  }

}
