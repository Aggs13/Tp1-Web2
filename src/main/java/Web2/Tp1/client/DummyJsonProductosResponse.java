package Web2.Tp1.client;

import java.util.List;

public record DummyJsonProductosResponse(
  List<DummyJsonProducto> products,
  int total,
  int skip,
  int limit
) {
}
