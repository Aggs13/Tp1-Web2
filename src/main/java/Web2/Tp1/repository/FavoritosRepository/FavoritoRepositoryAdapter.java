package Web2.Tp1.repository.FavoritosRepository;

import java.util.List;

import Web2.Tp1.client.DummyJsonProducto;
import Web2.Tp1.model.Favorito;

public class FavoritoRepositoryAdapter implements FavoritosRepository{

  @Override
  public List<DummyJsonProducto> GetProductosFavoritosRepository() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'GetProductosFavoritosRepository'");
  }

  @Override
  public void PostProductoFavorito(Favorito favorito) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'PostProductoFavorito'");
  }

  @Override
  public void ActualizarFavorito(Favorito fav) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'ActualizarFavorito'");
  }

  @Override
  public void EliminarFavorito(int idFav) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'EliminarFavorito'");
  }

  @Override
  public List<Favorito> GetListFavoritosRepository() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'GetListFavoritosRepository'");
  }
  
}
