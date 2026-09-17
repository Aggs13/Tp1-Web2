package Web2.Tp1.service.ProductosService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import Web2.Tp1.client.DummyJsonProducto;
import Web2.Tp1.dto.ProductoRespuestaDto;
import Web2.Tp1.dto.RespuestasDto;
import Web2.Tp1.exception.RecursoNoEncontradoException;
import Web2.Tp1.exception.ServicioExternoException;
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

    } catch (HttpClientErrorException.NotFound e) {
      throw new RecursoNoEncontradoException("Producto " + id + " no encontrado");
    } catch (ResourceAccessException | HttpServerErrorException e) {
      throw new ServicioExternoException("DummyJSON no disponible", e);
    }
    
  }
}
