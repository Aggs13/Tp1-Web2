package Web2.Tp1.dto;


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

  @Positive (message = "El id debe ser mayor a 0")
  @NotNull 
  private Integer id;

  @Positive(message = "El id del producto debe ser mayor a 0")
  @NotNull 
  private Integer idProducto;

  @NotBlank(message = "la nota personal es obligatoria") 
  @Size(max = 200,message = "Maximo 200 caracteres")
  private String notaPersonal;

  public FavoritosEntradaDto(Integer id ,Integer idProducto, String notaPersonal){
    this.id = id;
    this.idProducto = idProducto; 
    this.notaPersonal = notaPersonal;

  }

}
