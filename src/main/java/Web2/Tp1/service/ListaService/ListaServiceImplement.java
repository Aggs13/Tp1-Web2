package Web2.Tp1.service.ListaService;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import Web2.Tp1.dto.FavoritoDto.FavoritosSalidaDto;
import Web2.Tp1.dto.ListaDto.ListaEntradaDto;
import Web2.Tp1.dto.ListaDto.ListaSalidaDto;
import Web2.Tp1.dto.ProductoDto.ProductoRespuestaDto;
import Web2.Tp1.exception.AccesoDenegadoException;
import Web2.Tp1.exception.ConflictoException;
import Web2.Tp1.exception.RecursoNoEncontradoException;
import Web2.Tp1.model.Favorito;
import Web2.Tp1.model.Lista;
import Web2.Tp1.repository.FavoritosRepository.FavoritosRepository;
import Web2.Tp1.repository.ListaRepository.ListaRepository;
import Web2.Tp1.repository.ProductoRepository.ProductoRespository;
import jakarta.transaction.Transactional;
import Web2.Tp1.client.DummyJsonProducto;
import Web2.Tp1.dto.ComunDto.RespuestasDto;

@Service 
public class ListaServiceImplement implements ListaService{

  private final ListaRepository repository;
  private final FavoritosRepository favoritosRepository;
  private final ProductoRespository productoRespository;
  public ListaServiceImplement(ListaRepository r,FavoritosRepository f,ProductoRespository p){
    this.repository = r;
    this.favoritosRepository = f;
    this.productoRespository = p;
  }

  @Override
  public RespuestasDto<ListaSalidaDto> crearLista(ListaEntradaDto listaDto) {
    Long usuarioId = obtenerUsuarioIdActual();

    boolean existe = repository.GetListListasRepository().stream()
        .anyMatch(l -> usuarioId.equals(l.getUsuarioId()) && l.getNombre().equalsIgnoreCase(listaDto.getNombre()));
    if(existe) throw new ConflictoException("Ya existe una lista con ese nombre");

    Lista nuevaLista = new Lista();
    nuevaLista.setNombre(listaDto.getNombre());
    nuevaLista.setUsuarioId(usuarioId);
    Lista guardada = repository.GuardarLista(nuevaLista);

    ListaSalidaDto listaSalida = new ListaSalidaDto();
    listaSalida.setId(guardada.getId());
    listaSalida.setNombre(guardada.getNombre());

    return RespuestasDto.Respuesta("Se guardo la nueva lista", listaSalida, 201);
    
  }

  @Override
  public RespuestasDto<List<ListaSalidaDto>> listarListas() {
    Long usuarioId = obtenerUsuarioIdActual();
    boolean admin = esAdmin();

    List<ListaSalidaDto> listas = repository.GetListListasRepository().stream()
        .filter(l -> admin || usuarioId.equals(l.getUsuarioId()))
        .map(l -> {
          ListaSalidaDto listaSalidaDto = new ListaSalidaDto();
          listaSalidaDto.setNombre(l.getNombre());
          listaSalidaDto.setId(l.getId());
          return listaSalidaDto;
        }).toList();

    return RespuestasDto.Respuesta("Mostrando listas", listas, 200);
  } 

  @Override
  public RespuestasDto<ListaSalidaDto> obtenerLista(long id) {

    Lista lista = repository.BuscarListaPorId(id);
    if (lista == null) throw new RecursoNoEncontradoException("Lista con id " + id + " no encontrada");

    validarPropiedadLista(lista);

    ListaSalidaDto salida = new ListaSalidaDto();
    salida.setId(lista.getId());
    salida.setNombre(lista.getNombre());

    return RespuestasDto.Respuesta("Mostrando lista", salida, 200);
  }

  @Override
  public RespuestasDto<List<FavoritosSalidaDto>> favoritosDeLista(long id) {

    Lista lista = repository.BuscarListaPorId((int) id);
    if(lista == null) throw new RecursoNoEncontradoException("No se encontro la lista");

    validarPropiedadLista(lista);

    List<FavoritosSalidaDto> favoritosSalida = favoritosRepository.FiltrarPorLista((int)id).stream()
    .map(f -> {
      FavoritosSalidaDto fav = new FavoritosSalidaDto();
      fav.setListaId(f.getListaId());
      fav.setId(f.getId());
      fav.setFechaAgregado(f.getFechaAgregado());

      DummyJsonProducto ext = productoRespository.GetProductoPorIdRespository(f.getIdProducto());
      ProductoRespuestaDto producto = new ProductoRespuestaDto();
      producto.setId(ext.id().intValue());
      producto.setTitle(ext.title());
      producto.setPrice(ext.price());

      fav.setProductoFavorito(producto);

      fav.setNotaPersonal(f.getNotaPersonal());

      return fav;
    })
    .toList();

    return RespuestasDto.Respuesta("Mostrando Favoritos de lista", favoritosSalida, 200);
    
  }

  @Override
  public void eliminarLista(long id) {
    Lista lista = repository.BuscarListaPorId(id);
    if (lista == null) throw new RecursoNoEncontradoException("Lista con id " + id + " no encontrada");

    validarPropiedadLista(lista);

    boolean tieneFavoritos = !favoritosRepository.FiltrarPorLista((int) id).isEmpty();
    if (tieneFavoritos) throw new ConflictoException("No se puede eliminar la lista porque todavía tiene favoritos");

    repository.EliminarLista(id);
  }

  @Override
  @Transactional 
  public RespuestasDto<List<FavoritosSalidaDto>> moverFavoritos(long origenId,long destinoId) {

    Lista listaDestino = repository.BuscarListaPorId(destinoId);
    if(listaDestino == null) throw new RecursoNoEncontradoException("No se encontro la lista de destino");

    Lista listaOrigen = repository.BuscarListaPorId(origenId);
    if(listaOrigen == null) throw new RecursoNoEncontradoException("No se encontro la lista de origen");

    validarPropiedadLista(listaOrigen);
    validarPropiedadLista(listaDestino);

    List<FavoritosSalidaDto> favoritosMovidos = favoritosRepository.FiltrarPorLista((int) origenId).stream().map(f -> {

      Favorito moverFav = new Favorito();
      moverFav.setListaId((int)destinoId);
      moverFav.setId(f.getId());
      moverFav.setIdProducto(f.getIdProducto());
      moverFav.setNotaPersonal(f.getNotaPersonal());
      
      favoritosRepository.ActualizarFavorito(moverFav);

      FavoritosSalidaDto salida = new FavoritosSalidaDto();
      salida.setFechaAgregado(f.getFechaAgregado());
      salida.setId(f.getId());
      salida.setListaId((int) destinoId);
      salida.setNotaPersonal(f.getNotaPersonal());
      
      return salida;
      
    }).toList();

    
    repository.EliminarLista(origenId);

    return  RespuestasDto.Respuesta("Se movieron los productos de "+listaOrigen.getNombre()+ "a la lista" + listaDestino.getNombre() ,favoritosMovidos , 200);


  }

  private Long obtenerUsuarioIdActual() {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    return Long.valueOf(auth.getPrincipal().toString());
  }

  private boolean esAdmin() {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    return auth.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
  }

  private void validarPropiedadLista(Lista lista) {
    if (!esAdmin() && !obtenerUsuarioIdActual().equals(lista.getUsuarioId())) {
      throw new AccesoDenegadoException("No tenés permiso para acceder a esta lista");
    }
  }
  
}
