package Web2.Tp1.repository.ProductoRepository;

import java.util.List;

import Web2.Tp1.client.DummyJsonProducto;



public interface ProductoRespository {
    public List<DummyJsonProducto> GetProductosRepository();
    public DummyJsonProducto  GetProductoRespository(int id);

}
