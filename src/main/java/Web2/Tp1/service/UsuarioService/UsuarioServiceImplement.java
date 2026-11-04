package Web2.Tp1.service.UsuarioService;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import Web2.Tp1.dto.UsuarioDto.NuevoUsuarioRequiredDto;
import Web2.Tp1.dto.UsuarioDto.UsuarioLoginRequiredDto;
import Web2.Tp1.dto.UsuarioDto.UsuarioTokenDto;
import Web2.Tp1.exception.ConflictoException;
import Web2.Tp1.exception.NoAutorizadoException;
import Web2.Tp1.model.Usuario;
import Web2.Tp1.repository.UsuarioRepository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Service
public class UsuarioServiceImplement implements UsuarioService{

  private final UsuarioRepository _UsuarioRepository;

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration}")
  private long jwtVencimiento;


  public UsuarioServiceImplement(UsuarioRepository r){
    this._UsuarioRepository = r;
  }

  @Override
  public String UsuarioLogin(UsuarioLoginRequiredDto usuarioLogin) {
    Usuario usuarioVerificar = _UsuarioRepository.buscarPorEmail(usuarioLogin.getEmail());

    if(usuarioVerificar == null) throw new NoAutorizadoException("El usuario con email " + usuarioLogin.email + " no existe");

    boolean verificarPass = VerificarPass(usuarioLogin.getPass(), usuarioVerificar.getPasswordHash());
    if(!verificarPass) throw new NoAutorizadoException ("La ingresada contraseña es incorrecta ");

    
    UsuarioTokenDto usuarioToken = new UsuarioTokenDto();

    usuarioToken.setId(usuarioVerificar.getId());
    usuarioToken.setRol(usuarioVerificar.getRol());

    return GenerarToken(usuarioToken);

  }

  @Override
  public String NuevoUsuario(NuevoUsuarioRequiredDto usuario) {

    boolean existe = _UsuarioRepository.existePorEmail(usuario.getEmail());
    if(existe) throw new ConflictoException("El email ya esta registrado");
    
    String hasPass = HashPass(usuario.pass);

    Usuario nuevoUsuario = new Usuario();
    nuevoUsuario.setRol("USER");
    nuevoUsuario.setEmail(usuario.getEmail());
    nuevoUsuario.setPasswordHash(hasPass);

    _UsuarioRepository.guardar(nuevoUsuario);

    return "Se creo el usuario correctamente";

    
  }
  

  private String HashPass(String pass){
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(pass);
  }

  private Boolean VerificarPass(String passIngresada,String PassGuardada){

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.matches(passIngresada, PassGuardada);

  }

  private String GenerarToken(UsuarioTokenDto usuarioTokenDto){

    // 1. Convertimos el string secreto en una clave criptográfica (SecretKey)
    SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

    // 2. Armamos el token con el email (subject), el rol (claim), fecha de emisión y expiración
    return Jwts.builder()
      .claim("id", usuarioTokenDto.getId())               // Datos (Id, rol)
      .claim("rol", usuarioTokenDto.getRol())              
      .issuedAt(new Date())                                // Cuándo se creó (iat)
      .expiration(new Date(System.currentTimeMillis() + jwtVencimiento)) // Cuándo vence (exp)
      .signWith(key)                                       // Lo firmamos con nuestra clave secreta
      .compact();                                          // Genera el String final "eyJhbGci..."

  }

}
