package Web2.Tp1.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Web2.Tp1.dto.ComunDto.RespuestasDto;
import Web2.Tp1.dto.FavoritoDto.FavoritosSalidaDto;
import Web2.Tp1.dto.ListaDto.ListaEntradaDto;
import Web2.Tp1.dto.ListaDto.ListaSalidaDto;
import Web2.Tp1.dto.ListaDto.MoverFavoritosDto;
import Web2.Tp1.service.ListaService.ListaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/listas")
@Tag(name = "Listas")
public class ListaController {

  private final ListaService _listaService;

  public ListaController(ListaService listaService) {
    this._listaService = listaService;
  }

  @Operation(summary = "Crear lista", description = "Crea una lista de favoritos, devuelve 201")
  @PostMapping()
  public ResponseEntity<RespuestasDto<ListaSalidaDto>> crearLista(@Valid @RequestBody ListaEntradaDto lista) {
    RespuestasDto<ListaSalidaDto> respuesta = _listaService.crearLista(lista);
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }

  @Operation(summary = "Listar listas", description = "Devuelve todas las listas, devuelve 200")
  @GetMapping()
  public ResponseEntity<RespuestasDto<List<ListaSalidaDto>>> listarListas() {
    RespuestasDto<List<ListaSalidaDto>> respuesta = _listaService.listarListas();
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }

  @Operation(summary = "Obtener una lista", description = "Busca una lista por su id, devuelve 200")
  @GetMapping("/{id}")
  public ResponseEntity<RespuestasDto<ListaSalidaDto>> obtenerLista(@PathVariable long id) {
    RespuestasDto<ListaSalidaDto> respuesta = _listaService.obtenerLista(id);
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }

  @Operation(summary = "Favoritos de una lista", description = "Devuelve los favoritos que pertenecen a la lista, devuelve 200")
  @GetMapping("/{id}/favoritos")
  public ResponseEntity<RespuestasDto<List<FavoritosSalidaDto>>> favoritosDeLista(@PathVariable long id) {
    RespuestasDto<List<FavoritosSalidaDto>> respuesta = _listaService.favoritosDeLista(id);
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }

  @Operation(summary = "Eliminar una lista vacía", description = "Elimina por id solo si no tiene favoritos, devuelve 204. Si tiene, 409")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarLista(@PathVariable long id) {
    _listaService.eliminarLista(id);
    return ResponseEntity.noContent().build();
  }

  
  @Operation(summary = "Mover favoritos", description = "Mueve todos los favoritos de la lista origen a la destino y elimina la origen, devuelve 200")
  @PostMapping("/{origenId}/mover-favoritos")
  public ResponseEntity<RespuestasDto<List<FavoritosSalidaDto>>> moverFavoritos(@PathVariable long origenId, @Valid @RequestBody MoverFavoritosDto dto) {
    RespuestasDto<List<FavoritosSalidaDto>> respuesta = _listaService.moverFavoritos(origenId, dto.getDestinoId());
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }
}
