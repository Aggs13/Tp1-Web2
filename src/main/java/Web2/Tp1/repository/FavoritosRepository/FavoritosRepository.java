package Web2.Tp1.repository.FavoritosRepository;

import java.util.List;

import Web2.Tp1.dto.FavoritosEntradaDto;
import Web2.Tp1.dto.ProductoRespuestaDto;
import Web2.Tp1.model.Favorito;



public interface FavoritosRepository {
  public List<ProductoRespuestaDto> GetProductosFavoritosRepository();
  public boolean PostProductoFavorito(FavoritosEntradaDto favorito);
   List<Favorito> GetListFavoritosRepository();

  
} 