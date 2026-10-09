package Web2.Tp1.repository.ListaRepository;

import java.util.List;

import Web2.Tp1.model.Lista;

public interface ListaRepository {

  public Lista GuardarLista(Lista lista);
  public Lista BuscarListaPorId(long id);
  public List<Lista> GetListListasRepository();
  public void ActualizarLista(Lista lista);
  public void EliminarLista(long id);

}
