package Web2.Tp1.repository.FavoritosRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import Web2.Tp1.client.DummyJsonProducto;
import Web2.Tp1.model.Favorito;
import Web2.Tp1.repository.ProductoRepository.ProductoRespository;

@Repository 
public class FavoritosRepositoryImplement implements FavoritosRepository{

  private List<Favorito> listaFavoritos = new ArrayList<>(
    List.of(
      new Favorito(1, 43, "Gran producto!", LocalDate.now()),
      new Favorito(2, 21,"Acepatable por el precio", LocalDate.now()),
      new Favorito(3, 54, "Buena calidad", LocalDate.now())
    )
  );

  private final ProductoRespository _productoRespository;
  public FavoritosRepositoryImplement(ProductoRespository p){
    _productoRespository = p;
  }
  

  @Override
  public List<DummyJsonProducto> GetProductosFavoritosRepository() {
		List<DummyJsonProducto> dummyJsonProductosResponse = _productoRespository.GetProductosRepository();
  
    return dummyJsonProductosResponse.stream()
      .filter(p -> listaFavoritos.stream().anyMatch(f -> f.getIdProducto() == p.id()))
      .toList();

  }


  @Override
  public void PostProductoFavorito(Favorito favorito) {
    listaFavoritos.add(favorito);
    
  }

  @Override
  public void EliminarFavorito(int idFav) {
    listaFavoritos.removeIf(f -> f.getId() == idFav);
  }


  @Override
  public void ActualizarFavorito(int idProducto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'ActualizarFavorito'");
  }
  

  // Nesesario para el service
  @Override 
  public List<Favorito> GetListFavoritosRepository() {
    return listaFavoritos;
  }
}
