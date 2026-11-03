package Web2.Tp1.model;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class Usuario {
  private Long id;
  private String email;
  private String passwordHash;
  private String rol;
}
