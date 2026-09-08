package Web2.Tp1.repository.ProductoRepository;

import java.util.List;

import Web2.Tp1.dto.ProductoDataDto;

public interface ProductoRespository {
    public List<ProductoDataDto> GetProductosRepository();
    public ProductoDataDto GetProductoRespository(int id);

}
