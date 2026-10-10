package Web2.Tp1.service.ListaService;

import java.util.List;

import Web2.Tp1.dto.FavoritoDto.FavoritosSalidaDto;
import Web2.Tp1.dto.ListaDto.ListaEntradaDto;
import Web2.Tp1.dto.ListaDto.ListaSalidaDto;
import Web2.Tp1.dto.ComunDto.RespuestasDto;

public interface ListaService {

  RespuestasDto<ListaSalidaDto> crearLista(ListaEntradaDto lista);

  RespuestasDto<List<ListaSalidaDto>> listarListas();

  RespuestasDto<ListaSalidaDto> obtenerLista(long id);

  RespuestasDto<List<FavoritosSalidaDto>> favoritosDeLista(long id);

  void eliminarLista(long id);
}
