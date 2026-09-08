package Web2.Tp1.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class Producto {

  private int id;
  private String title;
  private String description;
  private String category;
  private double price;
  public int stock;
  List<String> tags;
  
}
