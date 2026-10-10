package Web2.Tp1.dto.ListaDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListaSalidaDto {

  private long id;
  private String nombre;

  public ListaSalidaDto() {}

  public ListaSalidaDto(long id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }
}
