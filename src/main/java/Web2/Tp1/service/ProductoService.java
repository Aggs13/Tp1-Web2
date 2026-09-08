package Web2.Tp1.service;
import java.util.List;
import org.springframework.stereotype.Service;
import Web2.Tp1.dto.ProductoDataDto;
import Web2.Tp1.repository.ProductoRepository.ProductoRespository;

@Service 
public class ProductoService {
  private final ProductoRespository productoRespository;

  public ProductoService(ProductoRespository p){
    this.productoRespository = p; 
  }

  public List<ProductoDataDto> GetProductosService(){
    return  productoRespository.GetProductosRepository();
  }

  public ProductoDataDto GetProductoService(int id){
    return  productoRespository.GetProductoRespository(id);
  }
}
