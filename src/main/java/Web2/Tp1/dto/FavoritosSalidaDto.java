package Web2.Tp1.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class FavoritosSalidaDto {

  private int id;
  ProductoRespuestaDto productoFavorito;
  private String notaPersonal;
  private LocalDate fechaAgregado;


  public FavoritosSalidaDto(){}

  public FavoritosSalidaDto(int id,String notaPersonal, LocalDate fechaAgregado,ProductoRespuestaDto producto){
    this.id = id;
    this.productoFavorito = producto;
    this.notaPersonal = notaPersonal;
    this.fechaAgregado = fechaAgregado;
  }

}
