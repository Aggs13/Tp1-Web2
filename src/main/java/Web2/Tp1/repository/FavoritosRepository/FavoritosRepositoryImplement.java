package Web2.Tp1.repository.FavoritosRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import Web2.Tp1.dto.FavoritosEntradaDto;
import Web2.Tp1.dto.ListProductDto;
import Web2.Tp1.dto.ProductoRespuestaDto;
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
  public List<ProductoRespuestaDto> GetProductosFavoritosRepository() {
    RestClient restClient = RestClient.create();
		List<ProductoRespuestaDto> product = restClient.get().uri("https://dummyjson.com/products").retrieve().body(ListProductDto.class)
    .getProducts()
    .stream()
    .filter(p -> listaFavoritos.stream().anyMatch(f -> f.getIdProducto() == p.getId())).toList();
    return  product;
  }

  @Override
  public boolean PostProductoFavorito(FavoritosEntradaDto favorito) {
    

    Favorito newFav = new Favorito
    (
      312, 
      favorito.getId(),
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
