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
@Tag(name = "Autenticación", description = "Endpoints públicos de registro e inicio de sesión (emisión de JWT)")
public class UsuarioController {

  private final UsuarioService _usuarioService;

  public UsuarioController(UsuarioService usuarioService) {
    this._usuarioService = usuarioService;
  }

  @Operation(summary = "Registrar usuario", description = "Endpoint público. Registra un nuevo usuario con contraseña hasheada (BCrypt) y rol por defecto. Devuelve 201 Created o 409 Conflict si el email ya existe.")
  @PostMapping("/registro")
  public ResponseEntity<RespuestasDto<String>> registrar(@Valid @RequestBody NuevoUsuarioRequiredDto usuario) {
    String resultado = _usuarioService.NuevoUsuario(usuario);
    RespuestasDto<String> respuesta = RespuestasDto.Respuesta(resultado, resultado, 201);
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }

  @Operation(summary = "Login de usuario", description = "Endpoint público. Valida email y contraseña contra el hash guardado y devuelve un JWT firmado con el id y rol del usuario (200 OK), o 401 Unauthorized si las credenciales son inválidas.")
  @PostMapping("/login")
  public ResponseEntity<RespuestasDto<String>> login(@Valid @RequestBody UsuarioLoginRequiredDto usuario) {
    String token = _usuarioService.UsuarioLogin(usuario);
    RespuestasDto<String> respuesta = RespuestasDto.Respuesta("Login exitoso", token, 200);
    return ResponseEntity.status(respuesta.getEstado()).body(respuesta);
  }
}
