package Web2.Tp1.service;
import java.util.List;
import org.springframework.stereotype.Service;
import Web2.Tp1.dto.ProductoRespuestaDto;
import Web2.Tp1.dto.RespuestasDto;
import Web2.Tp1.repository.ProductoRepository.ProductoRespository;

@Service 
public class ProductoService {
  private final ProductoRespository productoRespository;

  public ProductoService(ProductoRespository p){
    this.productoRespository = p; 
  }

  public RespuestasDto<List<ProductoRespuestaDto>> GetProductosService(){
    
    List<ProductoRespuestaDto> list = productoRespository.GetProductosRepository().stream()
    .map(p -> new ProductoRespuestaDto(
    p.getId(),
    p.getTitle(),
    p.getPrice()
    )).toList();
    
    if(list.isEmpty()) return RespuestasDto.Respuesta("No hay datos para mostrar", null, 404);
    return RespuestasDto.Respuesta("Mostrando datos", list, 200);

  }

  public RespuestasDto<ProductoRespuestaDto> GetProductoService(int id){

    try {

      ProductoRespuestaDto p =productoRespository.GetProductoRespository(id);

      return RespuestasDto.Respuesta(
        "Mostrando producto: " + p.getTitle(),
        p,
        200
      );

    } catch (Exception e) {

      return RespuestasDto.Respuesta(
        "No se encontró el producto ingresado",
        null,
        404
      );
    }
    
  }
}
