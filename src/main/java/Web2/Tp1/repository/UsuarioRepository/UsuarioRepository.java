package Web2.Tp1.repository.UsuarioRepository;

import org.springframework.stereotype.Repository;

import Web2.Tp1.model.Usuario;

@Repository 
public interface UsuarioRepository{

  String guardar(Usuario usuario);
  Usuario buscarPorEmail(String email);
  boolean existePorEmail(String email);
  
} 