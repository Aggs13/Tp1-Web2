package Web2.Tp1.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import Web2.Tp1.dto.ListProductDto;
import Web2.Tp1.dto.ProductoDataDto;

@Repository 
public class ProductoRespositoryImplement implements ProductoRespository{

	@Override
	public  List<ProductoDataDto> GetProductosRepository() {
		RestClient restClient = RestClient.create();
		ListProductDto product = restClient.get().uri("https://dummyjson.com/products").retrieve().body(ListProductDto.class);
		return product.getProducts();
	}

	@Override
	public ProductoDataDto GetProductoRespository(int id) {

		RestClient restClient = RestClient.create();
		ProductoDataDto product = restClient.get().uri("https://dummyjson.com/products/{id}",id).retrieve().body(ProductoDataDto.class);
		return  product;

	}

}
