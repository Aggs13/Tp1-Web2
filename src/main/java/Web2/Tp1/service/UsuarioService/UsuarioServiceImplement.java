package Web2.Tp1.service.UsuarioService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import Web2.Tp1.dto.UsuarioDto.NuevoUsuarioRequiredDto;
import Web2.Tp1.dto.UsuarioDto.UsuarioLoginRequiredDto;
import Web2.Tp1.dto.UsuarioDto.UsuarioTokenDto;
import Web2.Tp1.exception.ConflictoException;
import Web2.Tp1.model.Usuario;
import Web2.Tp1.repository.UsuarioRepository.UsuarioRepository;

@Service
public class UsuarioServiceImplement implements UsuarioService{

  private final UsuarioRepository _UsuarioRepository;

  public UsuarioServiceImplement(UsuarioRepository r){
    this._UsuarioRepository = r;
  }

  @Override
  public String UsuarioLogin(UsuarioLoginRequiredDto usuario) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'UsuarioLogin'");
  }

  @Override
  public String NuevoUsuario(NuevoUsuarioRequiredDto usuario) {

    boolean existe = _UsuarioRepository.existePorEmail(usuario.getEmail());
    if(existe) throw new ConflictoException("El email ya esta registrado");
    
    String hasPass = HashPass(usuario.pass);

    Usuario nuevoUsuario = new Usuario();
    nuevoUsuario.setRol("USUARIO");
    nuevoUsuario.setEmail(usuario.getEmail());
    nuevoUsuario.setPasswordHash(hasPass);

    _UsuarioRepository.guardar(nuevoUsuario);

    return "Se creo el usuario correctamente";

    
  }
  

  private String HashPass(String pass){
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(pass);
  }

  private String GenerarToken(UsuarioTokenDto usuarioTokenDto){
    return "";
  }

}
