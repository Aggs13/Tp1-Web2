package Web2.Tp1.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Web2.Tp1.dto.ProductoRespuestaDto;
import Web2.Tp1.dto.RespuestasDto;
import Web2.Tp1.service.ProductosService.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("api/productos")
@Tag(name="Productos")
public class ProductoController {
  
 private final ProductoService _productoService;
 public ProductoController(ProductoService p){
  this._productoService = p;
 } 

 @Operation(summary = "Obtiene lista de productos", description = "Lista todos los productos de DummyJsonProducto y los mapea en ProductoRespuestaDto")
  @GetMapping()
  public ResponseEntity<RespuestasDto<List<ProductoRespuestaDto>>> ObtenerProductos(){
    RespuestasDto<List<ProductoRespuestaDto>> respuesta = _productoService.GetProductosService();
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }

  @Operation(summary = "Selecciona un producto", description = "filtra los productos de DummyJsonProducto por id y lo mapea a ProductoRespuestaDto")
  @GetMapping("{id}")
  public ResponseEntity<RespuestasDto<ProductoRespuestaDto>> ObtenerPorId(@PathVariable  int id) {
    RespuestasDto<ProductoRespuestaDto> respuesta = _productoService.GetProductoService(id);
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }
  

}