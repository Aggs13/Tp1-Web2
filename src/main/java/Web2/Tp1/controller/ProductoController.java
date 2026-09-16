package Web2.Tp1.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Web2.Tp1.dto.ProductoRespuestaDto;
import Web2.Tp1.dto.RespuestasDto;
import Web2.Tp1.service.ProductosService.ProductoService;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/productos")
public class ProductoController {
  
 private final ProductoService _productoService;
 public ProductoController(ProductoService p){
  this._productoService = p;
 } 

  @GetMapping("/ObtenerProductos")
  public RespuestasDto<List<ProductoRespuestaDto>> ObtenerProductos(){
    return _productoService.GetProductosService();
  }

  @GetMapping("/ObtenerPorId")
  public RespuestasDto<ProductoRespuestaDto> ObtenerPorId(@RequestParam int id) {
    return _productoService.GetProductoService(id);
  }
  

}