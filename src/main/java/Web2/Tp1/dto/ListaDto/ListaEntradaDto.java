package Web2.Tp1.dto.ListaDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ListaEntradaDto {

  @NotBlank(message = "el nombre de la lista es obligatorio")
  @Size(max = 100, message = "Maximo 100 caracteres")
  private String nombre;

  public ListaEntradaDto(String nombre) {
    this.nombre = nombre;
  }
}
