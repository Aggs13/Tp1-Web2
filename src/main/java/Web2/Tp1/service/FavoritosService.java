package Web2.Tp1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import Web2.Tp1.dto.FavoritosEntradaDto;
import Web2.Tp1.dto.FavoritosSalidaDto;
import Web2.Tp1.dto.ProductoRespuestaDto;
import Web2.Tp1.model.Favorito;
import Web2.Tp1.repository.FavoritosRepository.FavoritosRepository;

@Service 
public class FavoritosService {
  

  private final FavoritosRepository _favoritosRepository;

  public FavoritosService(FavoritosRepository f){
    this._favoritosRepository = f;
  }

  public List<FavoritosSalidaDto> getFavoritosService(){

    List<Favorito> fav = _favoritosRepository.GetListFavoritosRepository();
    List<ProductoRespuestaDto> productos = _favoritosRepository.GetProductosFavoritosRepository();
    
    List<FavoritosSalidaDto> salidaFav = fav.stream().map(f -> { // Para cada f se ejecuta este bloque de codigo rodeado por {}

      // Se filtran los productos para obtener uno por id
      ProductoRespuestaDto producto = productos.stream().filter(p -> p.getId() == f.getIdProducto()).findFirst().orElse(null);  

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
