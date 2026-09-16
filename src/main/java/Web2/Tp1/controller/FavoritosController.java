package Web2.Tp1.controller;

import org.springframework.web.bind.annotation.RestController;

import Web2.Tp1.dto.FavoritosEntradaDto;
import Web2.Tp1.dto.FavoritosSalidaDto;
import Web2.Tp1.dto.RespuestasDto;
import Web2.Tp1.service.FavoritosService.FavoritosService;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
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

  @GetMapping("Get/")
  public RespuestasDto<List<FavoritosSalidaDto>> GetFavoritos(){
    List<FavoritosSalidaDto> list = _favoritosService.getFavoritosService();
    return  RespuestasDto.Respuesta("Mostrando productos favoritos", list, 200);
  }

  @PostMapping("Post/")
  public RespuestasDto<String> postMethodName(@RequestBody FavoritosEntradaDto fav) {
      
    String mensaje = _favoritosService.postFavoritoService(fav);
    return RespuestasDto.Respuesta(mensaje, null, 200);
  }
  

}
