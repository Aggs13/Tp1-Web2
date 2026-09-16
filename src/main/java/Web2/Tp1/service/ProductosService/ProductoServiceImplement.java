package Web2.Tp1.service.ProductosService;
import java.util.List;
import org.springframework.stereotype.Service;

import Web2.Tp1.client.DummyJsonProducto;
import Web2.Tp1.dto.ProductoRespuestaDto;
import Web2.Tp1.dto.RespuestasDto;
import Web2.Tp1.repository.ProductoRepository.ProductoRespository;

@Service 
public class ProductoServiceImplement implements ProductoService{
  private final ProductoRespository productoRespository;

  public ProductoServiceImplement(ProductoRespository p){
    this.productoRespository = p; 
  }

  public RespuestasDto<List<ProductoRespuestaDto>> GetProductosService(){
    
    List<ProductoRespuestaDto> list = productoRespository.GetProductosRepository().stream()
    .map(p -> new ProductoRespuestaDto(
    p.id().intValue(),
    p.title(),
    p.price()
    )).toList();
    
    if(list.isEmpty()) return RespuestasDto.Respuesta("No hay datos para mostrar", null, 404);
    return RespuestasDto.Respuesta("Mostrando datos", list, 200);

  }


  public RespuestasDto<ProductoRespuestaDto> GetProductoService(int id){

    try {

      DummyJsonProducto p = productoRespository.GetProductoRespository(id);
      ProductoRespuestaDto dto = new ProductoRespuestaDto(
        p.id().intValue(),
        p.title(),
        p.price()
      );

        return RespuestasDto.Respuesta(
        "Mostrando producto: " + dto.getTitle(),
        dto,
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
