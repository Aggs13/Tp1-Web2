package Web2.Tp1.repository.ProductoRepository;

import java.util.List;

import Web2.Tp1.dto.ProductoRespuestaDto;


public interface ProductoRespository {
    public List<ProductoRespuestaDto> GetProductosRepository();
    public ProductoRespuestaDto GetProductoRespository(int id);

}
