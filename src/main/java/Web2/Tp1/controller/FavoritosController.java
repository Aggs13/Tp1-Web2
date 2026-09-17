package Web2.Tp1.controller;

import org.springframework.web.bind.annotation.RestController;

import Web2.Tp1.dto.FavoritosEntradaDto;
import Web2.Tp1.dto.FavoritosSalidaDto;
import Web2.Tp1.dto.ProductoRespuestaDto;
import Web2.Tp1.dto.RespuestasDto;
import Web2.Tp1.service.FavoritosService.FavoritosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController 
@RequestMapping("api/favoritos")
@Tag(name="Favoritos")
public class FavoritosController {
  
  private final FavoritosService _favoritosService;
  public FavoritosController(FavoritosService f){
    this._favoritosService = f;
  }

  @Operation (summary = "Listar favoritos",description = "Devuelve toda la lista de favoritos con sus productos")
  @GetMapping()
  public ResponseEntity<RespuestasDto<List<FavoritosSalidaDto>>> GetFavoritos(){

    RespuestasDto<List<FavoritosSalidaDto>> respuesta = _favoritosService.getFavoritosService();
    return  ResponseEntity.status(respuesta.getEstado()).body(respuesta);

  }
  @Operation(summary = "Agregar favorito",description = "Agrega un producto de DummyJSON a favoritos, devuelve 201")
  @PostMapping()
  public ResponseEntity<RespuestasDto<FavoritosSalidaDto>> postFavorito(@Valid @RequestBody FavoritosEntradaDto fav) {
      
    RespuestasDto<FavoritosSalidaDto> respuesta = _favoritosService.postFavoritoService(fav);
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }

  @Operation(summary = "Obtener un favorito", description = "Busca un favorito por su id propio")
  @GetMapping("/{id}")
  public ResponseEntity<RespuestasDto<FavoritosSalidaDto>> GetUnicoFavorito(@PathVariable int id) {
    RespuestasDto<FavoritosSalidaDto> respuesta = _favoritosService.obtenerUnicoFavorito(id);
    return  ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }
  
  @Operation(summary = "Eliminar favorito", description = "Elimina por id, devuelve 204")
  @DeleteMapping("/{id}")
  public ResponseEntity<RespuestasDto<ProductoRespuestaDto>> deleteFavorito(@PathVariable int id){
    RespuestasDto<ProductoRespuestaDto> respuesta = _favoritosService.eliminarFavoritoService(id); // <- Devuelve body para mostrar el producto que se elimino de favoritos
    return  ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }

 @Operation(summary = "Editar favorito", description = "Actualiza el favorito sobreescribiendo con los datos(body) que se le pasan desde el endpoint")
  @PutMapping("/{id}")
  public ResponseEntity<RespuestasDto<FavoritosSalidaDto>> editarFavorito(@PathVariable int id, @Valid @RequestBody FavoritosEntradaDto fav) {
    RespuestasDto<FavoritosSalidaDto> respuesta = _favoritosService.EditarFavorito(id, fav);
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }
  

}
