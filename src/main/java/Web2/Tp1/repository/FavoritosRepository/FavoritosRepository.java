package Web2.Tp1.repository.FavoritosRepository;

import java.util.List;

import Web2.Tp1.client.DummyJsonProducto;
import Web2.Tp1.model.Favorito;



public interface FavoritosRepository {
  public List<DummyJsonProducto> GetProductosFavoritosRepository();
  public void PostProductoFavorito(Favorito favorito);
  // public FavoritosSalidaDto ObtenerUnFavorito(ProductoRespuestaDto idProducto);
  public void ActualizarFavorito(Favorito fav);
  public void EliminarFavorito(int idFav);
  
  public List<Favorito> GetListFavoritosRepository(); // se usa para luego mapear al producto, se guarda lo necesario para relacionar con productos
} 