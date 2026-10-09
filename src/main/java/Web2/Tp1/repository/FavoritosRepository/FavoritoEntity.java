package Web2.Tp1.repository.FavoritosRepository;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "favoritos")
@Getter @Setter 
public class FavoritoEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "producto_id",nullable = false)
  private Integer productoId;

  @Column(name = "nota",nullable = false)
  private String nota; 

  @Column(name = "fecha_alta",nullable = false)
  private LocalDate fechaAlta;
}
