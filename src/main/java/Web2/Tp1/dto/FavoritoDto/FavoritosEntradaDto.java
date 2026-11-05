package Web2.Tp1.dto.FavoritoDto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor
public class FavoritosEntradaDto {

  @Positive(message = "El id del producto debe ser mayor a 0")
  @NotNull(message = "El id del producto es obligatorio")
  private Integer idProducto;

  @NotBlank(message = "la nota personal es obligatoria") 
  @Size(max = 200,message = "Maximo 200 caracteres")
  private String notaPersonal;

  @NotNull(message = "El id de la lista es obligatorio")
  @Positive(message = "El id de la lista debe ser mayor a 0")
  private Integer listaId;

  public FavoritosEntradaDto(Integer idProducto, String notaPersonal){
    this.idProducto = idProducto; 
    this.notaPersonal = notaPersonal;

  }

}
