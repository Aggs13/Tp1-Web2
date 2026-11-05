package Web2.Tp1.repository.UsuarioRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter

public class UsuarioEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "email", nullable = false, unique = true)
  private String email;
  @Column(name = "password_hash", nullable = false)
  private String passwordHash;
  @Column(name = "rol", nullable = false)
  private String rol;
  
}
