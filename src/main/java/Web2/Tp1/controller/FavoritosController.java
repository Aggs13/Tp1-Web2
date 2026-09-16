package Web2.Tp1.controller;

import org.springframework.web.bind.annotation.RestController;

import Web2.Tp1.dto.FavoritosEntradaDto;
import Web2.Tp1.dto.FavoritosSalidaDto;
import Web2.Tp1.dto.RespuestasDto;
import Web2.Tp1.service.FavoritosService.FavoritosService;
import ch.qos.logback.core.joran.action.Action;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("api/favoritos")
public class FavoritosController {
  
  private final FavoritosService _favoritosService;
  public FavoritosController(FavoritosService f){
    this._favoritosService = f;
  }

  @GetMapping()
  public ResponseEntity<RespuestasDto<List<FavoritosSalidaDto>>> GetFavoritos(){

    RespuestasDto<List<FavoritosSalidaDto>> respuesta = _favoritosService.getFavoritosService();
    return  ResponseEntity.status(respuesta.getEstado()).body(respuesta);

  }

  @PostMapping()
  public ResponseEntity<RespuestasDto<FavoritosSalidaDto>> postFavorito(@RequestBody FavoritosEntradaDto fav) {
      
    RespuestasDto<FavoritosSalidaDto> respuesta = _favoritosService.postFavoritoService(fav);
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }
  

}
