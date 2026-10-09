package Web2.Tp1.service.ListaService;

import java.util.List;

import Web2.Tp1.dto.FavoritosSalidaDto;
import Web2.Tp1.dto.RespuestasDto;
import Web2.Tp1.model.Lista;

public interface ListaService {

  RespuestasDto<Lista> crearLista(Lista lista);

  RespuestasDto<List<Lista>> listarListas();

  RespuestasDto<Lista> obtenerLista(long id);

  RespuestasDto<List<FavoritosSalidaDto>> favoritosDeLista(long id);

  void eliminarLista(long id);
}
