package Web2.Tp1.dto.FavoritoDto;

import java.time.LocalDate;

import Web2.Tp1.dto.ProductoDto.ProductoRespuestaDto;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class FavoritosSalidaDto {

  private int id;
  ProductoRespuestaDto productoFavorito;
  private String notaPersonal;
  private LocalDate fechaAgregado;
  private int listaId;
  


  public FavoritosSalidaDto(){}



}
