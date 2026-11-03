package Web2.Tp1.repository.UsuarioRepository;

import Web2.Tp1.model.Usuario;

public interface UsuarioRepository {
  
  Usuario guardar(Usuario usuario);
  Usuario buscarPorEmail(String email);
  boolean existePorEmail(String email);
  
} 