package Web2.Tp1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Web2.Tp1.service.ListaService.ListaService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/listas")
@Tag(name = "Listas")
public class ListaController {

  private final ListaService _listaService;

  public ListaController(ListaService listaService) {
    this._listaService = listaService;
  }
}
