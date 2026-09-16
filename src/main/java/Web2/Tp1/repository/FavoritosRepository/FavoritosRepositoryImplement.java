package Web2.Tp1.repository.FavoritosRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import Web2.Tp1.client.DummyJsonProducto;
import Web2.Tp1.client.DummyJsonProductosResponse;
import Web2.Tp1.dto.FavoritosEntradaDto;
import Web2.Tp1.model.Favorito;

@Repository 
public class FavoritosRepositoryImplement implements FavoritosRepository{

  private List<Favorito> listaFavoritos = new ArrayList<>();

    public FavoritosRepositoryImplement() {
      listaFavoritos.add(new Favorito(1,1 ,4,LocalDate.now()));
      listaFavoritos.add(new Favorito(2,2 ,3,LocalDate.now()));
      listaFavoritos.add(new Favorito(3,3 ,4,LocalDate.now()));

    }

  @Override
  public List<DummyJsonProducto> GetProductosFavoritosRepository() {
    RestClient restClient = RestClient.create();
		DummyJsonProductosResponse response = restClient.get().uri("https://dummyjson.com/products").retrieve().body(DummyJsonProductosResponse.class);
  
    return response.products().stream()
      .filter(p -> listaFavoritos.stream().anyMatch(f -> f.getIdProducto() == p.id()))
      .toList();

  }


  @Override
  public boolean PostProductoFavorito(FavoritosEntradaDto favorito) {
    RestClient restClient = RestClient.create();
    DummyJsonProductosResponse response = restClient.get().uri("https://dummyjson.com/products").retrieve().body(DummyJsonProductosResponse.class);

    DummyJsonProducto product = response.products().stream().filter(p -> p.id() == favorito.getIdProducto())
    .findFirst()
    .orElse(null);

    if(product == null) return false;

    Favorito newFav = new Favorito
    (
      listaFavoritos.getLast().getId() + 1, 
      favorito.getIdProducto(),
      favorito.getNotaPersonal(), 
      favorito.getFechaAgregado()
    );

    listaFavoritos.add(newFav);
    return true;
    
  }

  // Nesesario para el service
  @Override 
  public List<Favorito> GetListFavoritosRepository() {
    return listaFavoritos;
}
  
}
