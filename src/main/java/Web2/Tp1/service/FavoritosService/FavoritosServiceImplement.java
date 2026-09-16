package Web2.Tp1.service.FavoritosService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import Web2.Tp1.client.DummyJsonProducto;
import Web2.Tp1.client.DummyJsonProductosResponse;
import Web2.Tp1.dto.FavoritosEntradaDto;
import Web2.Tp1.dto.FavoritosSalidaDto;
import Web2.Tp1.dto.ProductoRespuestaDto;
import Web2.Tp1.model.Favorito;
import Web2.Tp1.repository.FavoritosRepository.FavoritosRepository;
import Web2.Tp1.repository.ProductoRepository.ProductoRespository;
import Web2.Tp1.service.ProductosService.ProductoService;

@Service 
public class FavoritosServiceImplement implements FavoritosService{
  

  private final FavoritosRepository _favoritosRepository;
  private final ProductoRespository _productoRespository;
  public FavoritosServiceImplement(FavoritosRepository f,ProductoRespository p){
    this._favoritosRepository = f;
    this._productoRespository = p;
  }

  @Override 
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

  @Override 
  public String postFavoritoService(FavoritosEntradaDto favorito){
    DummyJsonProducto producto;

    // busca el producto por el repository
    try {
      producto = _productoRespository.GetProductoRespository(favorito.getIdProducto());
    } catch (Exception e) {
      return "No se pudo agregar el producto ERROR: " + e.getMessage();
    }

    if(producto == null) return  "El producto no existe";

    // revisa que el producto no este ya en la lista favoritos
    boolean existe = _favoritosRepository.GetListFavoritosRepository().stream().anyMatch(f -> f.getIdProducto() == favorito.getIdProducto());
    if(existe) return  "El producto ya esta en favoritos";

    // genera el id a partir del ultimo agregado
    int idFav = _favoritosRepository.GetListFavoritosRepository().stream().mapToInt(f -> f.getId()).max().orElse(0 ) + 1;

    // se crea el objeto Favorito y se le pasa 
    Favorito fav = new Favorito(idFav, producto.id().intValue(), favorito.getNotaPersonal(), LocalDate.now());
    _favoritosRepository.PostProductoFavorito(fav);
    
    return "Se agrego el producto correctamente";

    
  }

}
