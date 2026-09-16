package Web2.Tp1.service.ProductosService;

import java.util.List;

import Web2.Tp1.dto.ProductoRespuestaDto;
import Web2.Tp1.dto.RespuestasDto;

public interface ProductoService {
  RespuestasDto<List<ProductoRespuestaDto>> GetProductosService();
  RespuestasDto<ProductoRespuestaDto> GetProductoService(int id);
}
