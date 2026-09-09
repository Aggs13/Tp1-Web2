package Web2.Tp1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
// Clase para responder 
public class RespuestasDto<T> {
  
  private String mensaje; // mensaje que muestra la respuesta
  private T datos; // los datos que se mandan al front
  private int estado; // el estado de la respuesta (200,404,500) 

  public RespuestasDto(String mensaje, T datos, int estado) {
    this.mensaje = mensaje;
    this.datos = datos;
    this.estado = estado;
  }

  public static <T> RespuestasDto<T> Respuesta(String mensaje, T datos, int estado){ 
    return new RespuestasDto<>(mensaje,datos,estado);
  }

}
