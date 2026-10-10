package Web2.Tp1.repository.ListaRepository;

import java.util.List;

import Web2.Tp1.model.Lista;
import org.springframework.stereotype.Repository;

@Repository
public class ListaRepositoryAdapter implements ListaRepository{

  private final ListaJpaRepository repository;

  public ListaRepositoryAdapter(ListaJpaRepository r){
    this.repository = r;
  }

  @Override
  public Lista GuardarLista(Lista lista) {
    ListaEntity entity = new ListaEntity();
    entity.setNombre(lista.getNombre());
    ListaEntity guardada = repository.save(entity);

    Lista salida = new Lista();
    salida.setId(guardada.getId());
    salida.setNombre(guardada.getNombre());
    return salida;
  }

  @Override
  public Lista BuscarListaPorId(long id) {
    return repository.findById(id).map(l -> {
      Lista list = new Lista();
      list.setId(l.getId());
      list.setNombre(l.getNombre());

      return list;
    }).orElse(null);
  }

  @Override
  public Lista BuscarListaPorNombre(String nombre) {
    
    return repository.findAll().stream()
      .filter(e -> e.getNombre().equalsIgnoreCase(nombre))
      .findFirst()
      .map(e -> {
        Lista lista = new Lista();
        lista.setId(e.getId());
        lista.setNombre(e.getNombre());

        return lista;
      }).orElse(null);
  }

  @Override
  public List<Lista> GetListListasRepository() {
    
    return repository.findAll().stream().map(l -> {
      Lista list = new Lista();
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
