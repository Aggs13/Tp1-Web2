package Web2.Tp1.service.FavoritosService;

import java.util.List;

import Web2.Tp1.dto.FavoritosEntradaDto;
import Web2.Tp1.dto.FavoritosSalidaDto;

public interface FavoritosService {
  List<FavoritosSalidaDto> getFavoritosService();
   String postFavoritoService(FavoritosEntradaDto fav);
}
