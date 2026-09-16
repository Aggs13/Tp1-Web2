package Web2.Tp1.repository.FavoritosRepository;

import java.util.List;

import Web2.Tp1.client.DummyJsonProducto;
import Web2.Tp1.dto.ProductoRespuestaDto;
import Web2.Tp1.model.Favorito;



public interface FavoritosRepository {
  public List<DummyJsonProducto> GetProductosFavoritosRepository();
  public void PostProductoFavorito(Favorito favorito);
  public ProductoRespuestaDto ObtenerUnFavorito(int idProducto);
  public void ActualizarFavorito(int idProducto);
  public void EliminarFavorito(int idProducto);
  
  public List<Favorito> GetListFavoritosRepository(); // se usa para luego mapear al producto, se guarda lo necesario para relacionar con productos
} 