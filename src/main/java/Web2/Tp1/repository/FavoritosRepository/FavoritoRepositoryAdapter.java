package Web2.Tp1.repository.FavoritosRepository;
import java.util.List;
import org.springframework.stereotype.Repository;
import Web2.Tp1.exception.RecursoNoEncontradoException;
import Web2.Tp1.model.Favorito;

@Repository 
public class FavoritoRepositoryAdapter implements FavoritosRepository{

  private final FavoritoJpaRepository repository;
  
  public FavoritoRepositoryAdapter(FavoritoJpaRepository r){
    this.repository = r;
  }

  @Override
  public void PostProductoFavorito(Favorito favorito) {

    FavoritoEntity f = new FavoritoEntity();
    f.setProductoId(favorito.getIdProducto());
    f.setNota(favorito.getNotaPersonal());
    f.setFechaAlta(favorito.getFechaAgregado());
    repository.save(f);

  }

  @Override
  public void ActualizarFavorito(Favorito fav) {

    FavoritoEntity fEntity = repository.findById((long)fav.getId())
      .orElseThrow(() -> new RecursoNoEncontradoException("Favorito con id " + fav.getId() + " no encontrado"));
    fEntity.setProductoId(fav.getIdProducto());
    fEntity.setNota(fav.getNotaPersonal());
    repository.save(fEntity);
  }

  @Override
  public void EliminarFavorito(int idFav) {
    repository.deleteById((long)idFav);
  }

  @Override
  public List<Favorito> GetListFavoritosRepository() {

    List<Favorito> listFav = repository.findAll().stream().map(f -> {

      Favorito fav = new Favorito();
      fav.setId(f.getId().intValue());
      fav.setIdProducto(f.getProductoId());
      fav.setListaId(0);
      fav.setFechaAgregado(f.getFechaAlta());
      return fav;

    }).toList();
    
    return listFav;
  }

  @Override
  public Favorito BuscarFavoritoPorId(int id) {   return repository
   .findById((long) id)
   .map(f -> {

      Favorito fav = new Favorito();
      fav.setId(f.getId().intValue());
      fav.setIdProducto(f.getProductoId());
      fav.setNotaPersonal(f.getNota());
      fav.setListaId(0);
      fav.setFechaAgregado(f.getFechaAlta());
      return fav;
    }).orElse(null);
  }

  @Override
  public List<FavoritoEntity> BuscarPorIdLista(int id) {
    // TODO: implementar con repository.findByListaId + mapeo a dominio
    throw new UnsupportedOperationException("Unimplemented method 'BuscarPorIdLista'");
  }

  
}
