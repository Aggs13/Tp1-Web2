package Web2.Tp1.service.FavoritosService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import Web2.Tp1.client.DummyJsonProducto;
import Web2.Tp1.dto.FavoritosEntradaDto;
import Web2.Tp1.dto.FavoritosSalidaDto;
import Web2.Tp1.dto.ProductoRespuestaDto;
import Web2.Tp1.dto.RespuestasDto;
import Web2.Tp1.model.Favorito;
import Web2.Tp1.repository.FavoritosRepository.FavoritosRepository;
import Web2.Tp1.repository.ProductoRepository.ProductoRespository;

@Service 
public class FavoritosServiceImplement implements FavoritosService{
  

  private final FavoritosRepository _favoritosRepository;
  private final ProductoRespository _productoRespository;
  public FavoritosServiceImplement(FavoritosRepository f,ProductoRespository p){
    this._favoritosRepository = f;
    this._productoRespository = p;
  }



  // Obtiene la lista de favoritos 
  @Override 
  public RespuestasDto<List<FavoritosSalidaDto>> getFavoritosService(){

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
        f.getId(),
        f.getNotaPersonal(),
        f.getFechaAgregado(),
        producto
      );
    }).toList();

    return RespuestasDto.Respuesta("Mostrando productos favoritos",salidaFav, 200);

  }

  // Agregar un nuevo producto a favoritos
  @Override 
  public RespuestasDto<FavoritosSalidaDto> postFavoritoService(FavoritosEntradaDto favorito){
    DummyJsonProducto producto;

    // busca el producto por el repository
    try {
      producto = _productoRespository.GetProductoRespository(favorito.getIdProducto());
    } catch (HttpClientErrorException e) {
      
      return RespuestasDto.Respuesta("No es encontro el producto", null, 404);

    }catch(Exception e){

      return RespuestasDto.Respuesta("No se pudo agregar el producto ERROR: " + e.getMessage(), null, 500);
    }


    // revisa que el producto no este ya en la lista favoritos
    boolean existe = _favoritosRepository.GetListFavoritosRepository().stream().anyMatch(f -> f.getIdProducto() == favorito.getIdProducto());
    if(existe) return RespuestasDto.Respuesta("El producto ya esta en favoritos", null, 409);

    // genera el id a partir del ultimo agregado
    int idFav = _favoritosRepository.GetListFavoritosRepository().stream().mapToInt(f -> f.getId()).max().orElse(0 ) + 1;

    // se crea el objeto Favorito y se le pasa al repository
    Favorito fav = new Favorito(idFav, producto.id().intValue(), favorito.getNotaPersonal(), LocalDate.now());
    _favoritosRepository.PostProductoFavorito(fav);
    

    // Mapeo producto agregado a favoritos para mostrar en el body
    ProductoRespuestaDto productoRespuestaDto = new ProductoRespuestaDto(producto.id().intValue(),producto.title(),producto.price());
    FavoritosSalidaDto favoritosSalidaDto = new FavoritosSalidaDto(
      fav.getId(),
      fav.getNotaPersonal(),
      fav.getFechaAgregado(),
      productoRespuestaDto

    );
    return RespuestasDto.Respuesta("Se agrego el producto correctamente",favoritosSalidaDto, 201);

    
  }


  @Override
  public RespuestasDto<ProductoRespuestaDto> eliminarFavoritoService(int idFav) {
    try {
      Favorito fav = _favoritosRepository.GetListFavoritosRepository().stream().filter(f -> f.getId() == idFav).findFirst().orElse(null);
      if(fav == null)  return  RespuestasDto.Respuesta("No se encotro el producto favorito",null, 404);
    
      DummyJsonProducto dummyJsonProducto = _productoRespository.GetProductoRespository(fav.getIdProducto());
      _favoritosRepository.EliminarFavorito(idFav);

      ProductoRespuestaDto producto = new ProductoRespuestaDto(
        dummyJsonProducto.id().intValue(),
        dummyJsonProducto.title(),
        dummyJsonProducto.price()
      );

      return  RespuestasDto.Respuesta("Se elimino el producto: " + producto.getTitle()+ "De favoritos",producto , 204);

    } catch (HttpClientErrorException e) {

      return  RespuestasDto.Respuesta("No se encotro el producto a eliminar",null , 404);
      
    }catch(Exception e){
      return  RespuestasDto.Respuesta("Error al intentar eliminar: " + e.getMessage(),null , 500);
    }
  }



  @Override
  public RespuestasDto<FavoritosSalidaDto> obtenerUnicoFavorito(int idFav) {
    try {
      
      Favorito fav = _favoritosRepository.GetListFavoritosRepository().stream().filter(f -> f.getId() == idFav).findFirst().orElse(null);
      if(fav == null) return RespuestasDto.Respuesta("No se encontro el producto favorito", null, 404);

      DummyJsonProducto dummyJsonProducto = _productoRespository.GetProductoRespository(fav.getIdProducto());
      
      ProductoRespuestaDto producto = new ProductoRespuestaDto(
  
        dummyJsonProducto.id().intValue(),
        dummyJsonProducto.title(),
        dummyJsonProducto.price()
      );
  
      FavoritosSalidaDto favSalida = new FavoritosSalidaDto(
        fav.getId(),
        fav.getNotaPersonal(),
        fav.getFechaAgregado(),
        producto

      );

      return RespuestasDto.Respuesta("Unico producto favorito", favSalida, 200);

    } catch (HttpClientErrorException e) {

      return RespuestasDto.Respuesta("Error al buscar favorito", null, e.getStatusCode().value());
      
    }catch(Exception e){
      return RespuestasDto.Respuesta("Interno en el servidor", null, 500);
    }
  }



  @Override
  public RespuestasDto<FavoritosSalidaDto> EditarFavorito(FavoritosEntradaDto fav) {
    try {
      
      Favorito favorito = _favoritosRepository.GetListFavoritosRepository().stream()
      .filter(f -> f.getId() == fav.getId()).map(f -> new Favorito(fav.getId(),  fav.getIdProducto(), fav.getNotaPersonal(), LocalDate.now()))
      .findFirst()
      .orElse(null);
      if(favorito == null) return RespuestasDto.Respuesta("No se encotro el favorito", null, 404);

      _favoritosRepository.ActualizarFavorito(favorito);

      DummyJsonProducto dummyJsonProducto = _productoRespository.GetProductoRespository(favorito.getIdProducto());
      ProductoRespuestaDto producto = new ProductoRespuestaDto(
        dummyJsonProducto.id().intValue(),
        dummyJsonProducto.title(),
        dummyJsonProducto.price()
      );

      FavoritosSalidaDto favoritosSalida = new FavoritosSalidaDto(
        favorito.getId(),
        favorito.getNotaPersonal(),
        favorito.getFechaAgregado(),
        producto
      );

      return RespuestasDto.Respuesta("Se modifico el favorito", favoritosSalida, 200);

      
    } catch (HttpClientErrorException e) {
      return RespuestasDto.Respuesta("Error "+ e.getMessage(), null, e.getStatusCode().value());
    }
  }

}
