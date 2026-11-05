package Web2.Tp1.service.UsuarioService;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import Web2.Tp1.dto.UsuarioDto.NuevoUsuarioRequiredDto;
import Web2.Tp1.model.Usuario;
import Web2.Tp1.repository.UsuarioRepository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplementTest {

  @Mock UsuarioRepository repository;
  @InjectMocks UsuarioServiceImplement service;

  @Test
  void nuevoUsuario_contraseña_noGuardaEnTextoPlano() {
    var dto = new NuevoUsuarioRequiredDto();
    dto.setEmail("a@b.com");
    dto.setPass("123456");

    service.NuevoUsuario(dto);

    var captor = ArgumentCaptor.forClass(Usuario.class);
    verify(repository).guardar(captor.capture());
    assertNotEquals("123456", captor.getValue().getPasswordHash());
  }
}
