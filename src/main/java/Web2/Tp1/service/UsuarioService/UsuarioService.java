package Web2.Tp1.service.UsuarioService;

import Web2.Tp1.dto.UsuarioDto.NuevoUsuarioRequiredDto;
import Web2.Tp1.dto.UsuarioDto.UsuarioLoginRequiredDto;

public interface UsuarioService {
  String UsuarioLogin(UsuarioLoginRequiredDto usuario);
  String NuevoUsuario(NuevoUsuarioRequiredDto usuario);
}
