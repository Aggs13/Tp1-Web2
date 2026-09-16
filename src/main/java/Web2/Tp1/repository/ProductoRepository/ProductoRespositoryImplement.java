package Web2.Tp1.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import Web2.Tp1.client.DummyJsonProducto;
import Web2.Tp1.client.DummyJsonProductosResponse;


@Repository 
public class ProductoRespositoryImplement implements ProductoRespository{

	@Override
	public  List<DummyJsonProducto > GetProductosRepository() {
		RestClient restClient = RestClient.create();
		DummyJsonProductosResponse  response = restClient.get().uri("https://dummyjson.com/products").retrieve().body(DummyJsonProductosResponse.class);
		return response.products();
	}

	@Override
	public DummyJsonProducto  GetProductoRespository(int id) {
		
		RestClient restClient = RestClient.create();
		DummyJsonProducto product = restClient.get().uri("https://dummyjson.com/products/{id}",id).retrieve().body(DummyJsonProducto .class);
		return  product;
		
	}

}
