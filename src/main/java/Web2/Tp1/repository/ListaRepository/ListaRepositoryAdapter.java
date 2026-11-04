package Web2.Tp1.repository.ListaRepository;

import java.util.List;

import Web2.Tp1.model.Lista;
import Web2.Tp1.repository.UsuarioRepository.UsuarioJpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public class ListaRepositoryAdapter implements ListaRepository{

  private final ListaJpaRepository repository;
  private final UsuarioJpaRepository usuarioRepository;

  public ListaRepositoryAdapter(ListaJpaRepository r,UsuarioJpaRepository u){
    this.repository = r;
    this.usuarioRepository = u;
  }

  @Override
  public Lista GuardarLista(Lista lista) {
    ListaEntity entity = new ListaEntity();

    entity.setNombre(lista.getNombre());
    entity.setUsuario(usuarioRepository.getReferenceById(lista.getUsuarioId()));


    ListaEntity guardada = repository.save(entity);

    Lista salida = new Lista();
    salida.setId(guardada.getId());
    salida.setNombre(guardada.getNombre());
    salida.setUsuarioId(guardada.getUsuario().getId());
    return salida;
  }

  @Override
  public Lista BuscarListaPorId(long id) {

  
    return repository.findById(id).map(l -> {
      
      Lista list = new Lista();

      if (l.getUsuario() != null) list.setUsuarioId(l.getUsuario().getId());
      list.setId(l.getId());
      list.setNombre(l.getNombre());

      return list;
    }).orElse(null);
  }

  @Override
  public Lista BuscarListaPorNombre(String nombre) {
    
    return repository.findAll().stream()
      .filter(l -> l.getNombre().equalsIgnoreCase(nombre))
      .findFirst()
      .map(l -> {
        Lista lista = new Lista();

        if (l.getUsuario() != null) lista.setUsuarioId(l.getUsuario().getId());
        lista.setId(l.getId());
        lista.setNombre(l.getNombre());

        return lista;
      }).orElse(null);
  }

  @Override
  public List<Lista> GetListListasRepository() {
    
    return repository.findAll().stream().map(l -> {
      Lista list = new Lista();

      if (l.getUsuario() != null) list.setUsuarioId(l.getUsuario().getId());
      list.setId(l.getId());
      list.setNombre(l.getNombre());

      return list;
    }).toList();
      
  }

  @Override
  public void ActualizarLista(Lista lista) {
    ListaEntity entity = repository.findById(lista.getId()).orElse(null);
    entity.setNombre(lista.getNombre());
    repository.save(entity);
  }

  @Override
  public void EliminarLista(long id) {
    repository.deleteById(id);
  }

  

}
