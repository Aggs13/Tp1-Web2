package Web2.Tp1.service.FavoritosService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import Web2.Tp1.client.DummyJsonProducto;
import Web2.Tp1.dto.FavoritosEntradaDto;
import Web2.Tp1.dto.FavoritosSalidaDto;
import Web2.Tp1.dto.ProductoRespuestaDto;
import Web2.Tp1.dto.RespuestasDto;
import Web2.Tp1.exception.RecursoNoEncontradoException;
import Web2.Tp1.exception.ServicioExternoException;
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
    List<DummyJsonProducto> productos = _productoRespository.GetProductosRepository();

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
      
      producto = _productoRespository.GetProductoPorIdRespository(favorito.getIdProducto());

      // revisa que el producto no este ya en la lista favoritos
      boolean existe = _favoritosRepository.GetListFavoritosRepository().stream().anyMatch(f -> f.getIdProducto() == favorito.getIdProducto());
      if(existe) return RespuestasDto.Respuesta("El producto ya esta en favoritos", null, 409);

      // genera el id a partir del ultimo agregado


      // se crea el objeto Favorito y se le pasa al repository
      Favorito fav = new Favorito();
      fav.setNotaPersonal(favorito.getNotaPersonal());
      fav.setIdProducto(producto.id().intValue());
      fav.setListaId(favorito.getListaId());
      fav.setFechaAgregado(LocalDate.now());
      
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

    } catch (HttpClientErrorException.NotFound e) {
      throw new RecursoNoEncontradoException("Producto " + favorito.getIdProducto() + " no existe en DummyJSON");
    } catch (ResourceAccessException | HttpServerErrorException e) {
      throw new ServicioExternoException("DummyJSON no disponible", e);
    }

  }


  @Override
  public RespuestasDto<ProductoRespuestaDto> eliminarFavoritoService(int idFav) {
      Favorito fav = _favoritosRepository.GetListFavoritosRepository().stream().filter(f -> f.getId() == idFav).findFirst().orElse(null);
      if(fav == null) throw new RecursoNoEncontradoException("Favorito con id " + idFav + " no encontrado");
    
      DummyJsonProducto dummyJsonProducto;
      try {
        dummyJsonProducto = _productoRespository.GetProductoPorIdRespository(fav.getIdProducto());
      } catch (HttpClientErrorException.NotFound e) {
        throw new RecursoNoEncontradoException("Producto " + fav.getIdProducto() + " no existe en DummyJSON");
      } catch (ResourceAccessException | HttpServerErrorException e) {
        throw new ServicioExternoException("DummyJSON no disponible", e);
      }
      _favoritosRepository.EliminarFavorito(idFav);

      ProductoRespuestaDto producto = new ProductoRespuestaDto(
        dummyJsonProducto.id().intValue(),
        dummyJsonProducto.title(),
        dummyJsonProducto.price()
      );

      return  RespuestasDto.Respuesta("Se elimino el producto: " + producto.getTitle()+ "De favoritos",producto , 200);
  }



  @Override
  public RespuestasDto<FavoritosSalidaDto> obtenerUnicoFavorito(int idFav) {

      Favorito fav = _favoritosRepository.BuscarFavoritoPorId(idFav);
      if(fav == null) throw new RecursoNoEncontradoException("Favorito con id " + idFav + " no encontrado");

      DummyJsonProducto dummyJsonProducto;
      try {
        dummyJsonProducto = _productoRespository.GetProductoPorIdRespository(fav.getIdProducto());
      } catch (HttpClientErrorException.NotFound e) {
        throw new RecursoNoEncontradoException("Producto " + fav.getIdProducto() + " no existe en DummyJSON");
      } catch (ResourceAccessException | HttpServerErrorException e) {
        throw new ServicioExternoException("DummyJSON no disponible", e);
      }
      
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
  }



  @Override
  public RespuestasDto<FavoritosSalidaDto> EditarFavorito(int id, FavoritosEntradaDto favEdit) {
    
      Favorito favorito = _favoritosRepository.BuscarFavoritoPorId(id);
      if(favorito == null) throw new RecursoNoEncontradoException("Favorito con id " + id + " no encontrado");

      favorito.setIdProducto(favEdit.getIdProducto());
      favorito.setNotaPersonal(favEdit.getNotaPersonal());
      
      _favoritosRepository.ActualizarFavorito(favorito);

      DummyJsonProducto dummyJsonProducto;
      try {

        dummyJsonProducto = _productoRespository.GetProductoPorIdRespository(favorito.getIdProducto());

      } catch (HttpClientErrorException.NotFound e) {
        throw new RecursoNoEncontradoException("Producto " + favorito.getIdProducto() + " no existe en DummyJSON");
      } catch (ResourceAccessException | HttpServerErrorException e) {
        throw new ServicioExternoException("DummyJSON no disponible", e);
      }
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
  }

}
