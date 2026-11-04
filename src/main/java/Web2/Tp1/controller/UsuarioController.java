package Web2.Tp1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Web2.Tp1.dto.ComunDto.RespuestasDto;
import Web2.Tp1.dto.UsuarioDto.NuevoUsuarioRequiredDto;
import Web2.Tp1.dto.UsuarioDto.UsuarioLoginRequiredDto;
import Web2.Tp1.service.UsuarioService.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth")
@Tag(name = "Autenticación")
public class UsuarioController {

  private final UsuarioService _usuarioService;

  public UsuarioController(UsuarioService usuarioService) {
    this._usuarioService = usuarioService;
  }

  @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario con rol USER por defecto. Devuelve 409 si el email ya existe.")
  @PostMapping("/registro")
  public ResponseEntity<RespuestasDto<String>> registrar(@Valid @RequestBody NuevoUsuarioRequiredDto usuario) {
    String resultado = _usuarioService.NuevoUsuario(usuario);
    RespuestasDto<String> respuesta = RespuestasDto.Respuesta(resultado, 201);
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }

  @Operation(summary = "Login de usuario", description = "Autentica al usuario y devuelve un token JWT.")
  @PostMapping("/login")
  public ResponseEntity<RespuestasDto<String>> login(@Valid @RequestBody UsuarioLoginRequiredDto usuario) {
    String token = _usuarioService.UsuarioLogin(usuario);
    RespuestasDto<String> respuesta = RespuestasDto.Respuesta("Login exitoso", token, 200);
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }
}
