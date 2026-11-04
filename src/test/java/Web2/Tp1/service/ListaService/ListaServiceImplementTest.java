package Web2.Tp1.service.ListaService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import Web2.Tp1.dto.ListaDto.ListaEntradaDto;
import Web2.Tp1.model.Lista;
import Web2.Tp1.repository.FavoritosRepository.FavoritosRepository;
import Web2.Tp1.repository.ListaRepository.ListaRepository;
import Web2.Tp1.repository.ProductoRepository.ProductoRespository;

@ExtendWith(MockitoExtension.class)
class ListaServiceImplementTest {

  @Mock ListaRepository repository;
  @Mock FavoritosRepository favoritosRepository;
  @Mock ProductoRespository productoRespository;
  @InjectMocks ListaServiceImplement service;

  @Test
  void crearLista_usuarioAutenticado_asociaAlUsuario() {
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(10L, null, List.of()));
    Lista guardada = new Lista();
    guardada.setId(1L);
    when(repository.GuardarLista(any())).thenReturn(guardada);

    service.crearLista(new ListaEntradaDto("Mi Lista"));

    var captor = ArgumentCaptor.forClass(Lista.class);
    verify(repository).GuardarLista(captor.capture());
    assertEquals(10L, captor.getValue().getUsuarioId());
  }
}
