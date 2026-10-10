package Web2.Tp1.dto.ListaDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MoverFavoritosDto {

  @NotNull(message = "El id de la lista destino es obligatorio")
  @Positive(message = "El id de la lista destino debe ser mayor a 0")
  private Long destinoId;

  public MoverFavoritosDto(Long destinoId) {
    this.destinoId = destinoId;
  }
}
