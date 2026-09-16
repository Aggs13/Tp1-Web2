package Web2.Tp1.client;

public record DummyJsonProducto(
    Long id,
    String title,
    String description,
    String category,
    String brand,
    double price,
    double discountPercentage,
    int stock,
    double rating,
    String thumbnail
) {
}
