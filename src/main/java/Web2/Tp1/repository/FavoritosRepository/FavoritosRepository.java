package Web2.Tp1.repository.FavoritosRepository;

import java.util.List;

import Web2.Tp1.client.DummyJsonProducto;
import Web2.Tp1.dto.FavoritosEntradaDto;
import Web2.Tp1.model.Favorito;



public interface FavoritosRepository {
  public List<DummyJsonProducto> GetProductosFavoritosRepository();
  public boolean PostProductoFavorito(FavoritosEntradaDto favorito);
  public List<Favorito> GetListFavoritosRepository();

  
} 