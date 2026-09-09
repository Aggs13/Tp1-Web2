package Web2.Tp1.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import Web2.Tp1.dto.ListProductDto;

import Web2.Tp1.dto.ProductoRespuestaDto;

@Repository 
public class ProductoRespositoryImplement implements ProductoRespository{

	@Override
	public  List<ProductoRespuestaDto> GetProductosRepository() {
		RestClient restClient = RestClient.create();
		ListProductDto product = restClient.get().uri("https://dummyjson.com/products").retrieve().body(ListProductDto.class);
		return product.getProducts();
	}

	@Override
	public ProductoRespuestaDto GetProductoRespository(int id) {
		
		RestClient restClient = RestClient.create();
		ProductoRespuestaDto product = restClient.get().uri("https://dummyjson.com/products/{id}",id).retrieve().body(ProductoRespuestaDto.class);
		return  product;
		
	}

}
