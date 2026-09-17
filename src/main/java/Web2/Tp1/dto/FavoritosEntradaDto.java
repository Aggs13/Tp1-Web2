package Web2.Tp1.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class FavoritosEntradaDto {
  @PositiveOrZero 
  
  private int idProducto;

  @NotBlank 
  private String notaPersonal;

  public FavoritosEntradaDto(int idProducto, String notaPersonal){

    this.idProducto = idProducto; 
    this.notaPersonal = notaPersonal;

  }

}
