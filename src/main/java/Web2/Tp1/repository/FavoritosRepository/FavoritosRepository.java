package Web2.Tp1.repository.FavoritosRepository;

import java.util.List;

import Web2.Tp1.model.Favorito;



public interface FavoritosRepository {
  
  public Favorito BuscarFavoritoPorId(int id);
  public void PostProductoFavorito(Favorito favorito);
  public void ActualizarFavorito(Favorito fav);
  public void EliminarFavorito(int idFav);
  public List<FavoritoEntity> BuscarPorIdLista(int id);
  public List<Favorito> GetListFavoritosRepository();
} 