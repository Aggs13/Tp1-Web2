package Web2.Tp1.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class ProductResponseDto {

  private List<ProductoDataDto> products;
  private int total;
  private int skip;
  private int limit;

}
