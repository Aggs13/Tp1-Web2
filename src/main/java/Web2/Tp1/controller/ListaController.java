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
@Tag(name = "Listas", description = "Gestión de listas de favoritos (Requiere autenticación Bearer JWT)")
public class ListaController {

  private final ListaService _listaService;

  public ListaController(ListaService listaService) {
    this._listaService = listaService;
  }

  @Operation(summary = "Crear lista", description = "Requiere autenticación JWT. Crea una lista asociada al usuario autenticado, devuelve 201 (o 409 si ya tiene una con ese nombre)")
  @PostMapping()
  public ResponseEntity<RespuestasDto<ListaSalidaDto>> crearLista(@Valid @RequestBody ListaEntradaDto lista) {
    RespuestasDto<ListaSalidaDto> respuesta = _listaService.crearLista(lista);
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }

  @Operation(summary = "Listar listas", description = "Requiere autenticación JWT. Devuelve las listas del usuario autenticado (o todas si es ADMIN), devuelve 200")
  @GetMapping()
  public ResponseEntity<RespuestasDto<List<ListaSalidaDto>>> listarListas() {
    RespuestasDto<List<ListaSalidaDto>> respuesta = _listaService.listarListas();
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }

  @Operation(summary = "Obtener una lista", description = "Requiere autenticación JWT. Busca una lista por su id, devuelve 200 (403 si pertenece a otro usuario y no es ADMIN, 404 si no existe)")
  @GetMapping("/{id}")
  public ResponseEntity<RespuestasDto<ListaSalidaDto>> obtenerLista(@PathVariable long id) {
    RespuestasDto<ListaSalidaDto> respuesta = _listaService.obtenerLista(id);
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }

  @Operation(summary = "Favoritos de una lista", description = "Requiere autenticación JWT. Devuelve los favoritos de la lista si pertenece al usuario autenticado o es ADMIN, devuelve 200 (403 si es ajena)")
  @GetMapping("/{id}/favoritos")
  public ResponseEntity<RespuestasDto<List<FavoritosSalidaDto>>> favoritosDeLista(@PathVariable long id) {
    RespuestasDto<List<FavoritosSalidaDto>> respuesta = _listaService.favoritosDeLista(id);
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }

  @Operation(summary = "Eliminar una lista vacía", description = "Requiere autenticación JWT. Elimina por id solo si pertenece al usuario (o ADMIN) y no tiene favoritos, devuelve 204. Si es ajena 403, si tiene favoritos 409")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarLista(@PathVariable long id) {
    _listaService.eliminarLista(id);
    return ResponseEntity.noContent().build();
  }

  
  @Operation(summary = "Mover favoritos", description = "Requiere autenticación JWT. Mueve todos los favoritos de la lista origen a la destino (ambas deben pertenecer al usuario o ser ADMIN) y elimina la origen, devuelve 200")
  @PostMapping("/{origenId}/mover-favoritos")
  public ResponseEntity<RespuestasDto<List<FavoritosSalidaDto>>> moverFavoritos(@PathVariable long origenId, @Valid @RequestBody MoverFavoritosDto dto) {
    RespuestasDto<List<FavoritosSalidaDto>> respuesta = _listaService.moverFavoritos(origenId, dto.getDestinoId());
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }
}
