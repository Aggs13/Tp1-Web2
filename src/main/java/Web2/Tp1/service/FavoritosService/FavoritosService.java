package Web2.Tp1.service.FavoritosService;

import java.util.List;

import Web2.Tp1.dto.FavoritoDto.FavoritosEntradaDto;
import Web2.Tp1.dto.FavoritoDto.FavoritosSalidaDto;
import Web2.Tp1.dto.ProductoDto.ProductoRespuestaDto;
import Web2.Tp1.dto.ComunDto.RespuestasDto;

public interface FavoritosService {
  
  RespuestasDto<List<FavoritosSalidaDto>> getFavoritosService();
  RespuestasDto<FavoritosSalidaDto> postFavoritoService(FavoritosEntradaDto fav);
  RespuestasDto<ProductoRespuestaDto> eliminarFavoritoService(int idFav);
  RespuestasDto<FavoritosSalidaDto> obtenerUnicoFavorito(int idFav);
  RespuestasDto<FavoritosSalidaDto> EditarFavorito(int id, FavoritosEntradaDto fav);
}
