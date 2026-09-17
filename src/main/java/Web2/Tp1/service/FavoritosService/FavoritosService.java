package Web2.Tp1.service.FavoritosService;

import java.util.List;

import Web2.Tp1.dto.FavoritosEntradaDto;
import Web2.Tp1.dto.FavoritosSalidaDto;
import Web2.Tp1.dto.ProductoRespuestaDto;
import Web2.Tp1.dto.RespuestasDto;

public interface FavoritosService {
  RespuestasDto<List<FavoritosSalidaDto>> getFavoritosService();
  RespuestasDto<FavoritosSalidaDto> postFavoritoService(FavoritosEntradaDto fav);
  RespuestasDto<ProductoRespuestaDto> eliminarFavoritoService(int idProducto);
  RespuestasDto<FavoritosSalidaDto> obtenerUnicoFavorito(int idProducto);
}
