package Web2.Tp1.repository.UsuarioRepository;

import org.springframework.stereotype.Repository;
import Web2.Tp1.model.Usuario;

@Repository
public class UsuarioRepositoryAdapter implements UsuarioRepository{


  private final UsuarioJpaRepository _repository;

  public UsuarioRepositoryAdapter(UsuarioJpaRepository r){
    this._repository = r;
  }

  @Override
  public String guardar(Usuario usuario) {
    UsuarioEntity usuarioEntity = new UsuarioEntity();

    usuarioEntity.setEmail(usuario.getEmail());
    usuarioEntity.setRol(usuario.getRol());
    usuarioEntity.setPasswordHash(usuario.getPasswordHash());
    _repository.save(usuarioEntity);
    return "Usuario creado!!";

  }

  @Override
  public Usuario buscarPorEmail(String email) {

    return _repository.findAll().stream()
    .filter(e -> e.getEmail().equalsIgnoreCase(email))
    .findFirst()
    .map(e -> {

      Usuario usuario = new Usuario();
      usuario.setId(e.getId());
      usuario.setEmail(e.getEmail());
      usuario.setPasswordHash(e.getPasswordHash());
      usuario.setRol(e.getRol());
      return usuario;

    }).orElse(null);
  }

  @Override
  public boolean existePorEmail(String email) {
    return _repository.findAll().stream()
    .anyMatch(e -> e.getEmail().equalsIgnoreCase(email));
  }


}
