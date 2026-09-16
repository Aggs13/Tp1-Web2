package Web2.Tp1.service.FavoritosService;

import java.util.List;

import org.springframework.stereotype.Service;

import Web2.Tp1.client.DummyJsonProducto;
import Web2.Tp1.dto.FavoritosEntradaDto;
import Web2.Tp1.dto.FavoritosSalidaDto;
import Web2.Tp1.dto.ProductoRespuestaDto;
import Web2.Tp1.model.Favorito;
import Web2.Tp1.repository.FavoritosRepository.FavoritosRepository;

@Service 
public class FavoritosServiceImplement implements FavoritosService{
  

  private final FavoritosRepository _favoritosRepository;

  public FavoritosServiceImplement(FavoritosRepository f){
    this._favoritosRepository = f;
  }

  public List<FavoritosSalidaDto> getFavoritosService(){

    List<Favorito> fav = _favoritosRepository.GetListFavoritosRepository();
    List<DummyJsonProducto> productos = _favoritosRepository.GetProductosFavoritosRepository();
    
    List<FavoritosSalidaDto> salidaFav = fav.stream().map(f -> { // Para cada favorito se ejecuta este bloque de codigo rodeado por {}

      // Se filtran los productos para obtener uno por id
    ProductoRespuestaDto producto = productos.stream()
        .filter(p -> p.id() == f.getIdProducto())
        .findFirst()
        .map(p -> new ProductoRespuestaDto(
          p.id().intValue(),
          p.title(),         
          p.price()          
        ))
        .orElse(null);
      // se retorna el objeto con su producto y atrubutos
      return new FavoritosSalidaDto(
        f.getNotaPersonal(),
        f.getFechaAgregado(),
        producto
      );
    }).toList();

    return salidaFav;

  }

  public String postFavoritoService(FavoritosEntradaDto fav){
    boolean agregado = _favoritosRepository.PostProductoFavorito(fav);
    
    if(!agregado) return "No se pudo agregar el producto";
    return "Se agrego el producto a favoritos";
    
  }

}
