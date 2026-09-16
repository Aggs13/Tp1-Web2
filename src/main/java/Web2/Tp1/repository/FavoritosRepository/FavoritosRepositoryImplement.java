package Web2.Tp1.repository.FavoritosRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import Web2.Tp1.client.DummyJsonProducto;
import Web2.Tp1.client.DummyJsonProductosResponse;
import Web2.Tp1.dto.ProductoRespuestaDto;
import Web2.Tp1.model.Favorito;

@Repository 
public class FavoritosRepositoryImplement implements FavoritosRepository{

  private List<Favorito> listaFavoritos = new ArrayList<>();
  private RestClient restClient = RestClient.create();

    public FavoritosRepositoryImplement() {
      listaFavoritos.add(new Favorito(1,1 ,4,LocalDate.now()));
      listaFavoritos.add(new Favorito(2,2 ,3,LocalDate.now()));
      listaFavoritos.add(new Favorito(3,3 ,4,LocalDate.now()));

    }

  @Override
  public List<DummyJsonProducto> GetProductosFavoritosRepository() {
		DummyJsonProductosResponse response = restClient.get().uri("https://dummyjson.com/products").retrieve().body(DummyJsonProductosResponse.class);
  
    return response.products().stream()
      .filter(p -> listaFavoritos.stream().anyMatch(f -> f.getIdProducto() == p.id()))
      .toList();

  }


  @Override
  public void PostProductoFavorito(Favorito favorito) {
    listaFavoritos.add(favorito);
    
  }

  @Override
  public void EliminarFavorito(int idProducto) {
    listaFavoritos.removeIf(p -> p.getIdProducto() == idProducto);
  }

  @Override
  public ProductoRespuestaDto ObtenerUnFavorito(int idProducto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'ObtenerUnFavorito'");
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
