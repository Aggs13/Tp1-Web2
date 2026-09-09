package Web2.Tp1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
// Formato de respuesta que se quiere mostrar
public class ProductoRespuestaDto {

  private int id;
  private String title;
  private double price;

  public ProductoRespuestaDto(){}

  public ProductoRespuestaDto(int id,String title,double price){
    this.id = id;
    this.title = title;
    this.price = price;
  }

}
