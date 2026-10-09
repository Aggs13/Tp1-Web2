package Web2.Tp1.service.ListaService;

import java.util.List;

import org.springframework.stereotype.Service;

import Web2.Tp1.dto.FavoritosSalidaDto;
import Web2.Tp1.dto.RespuestasDto;
import Web2.Tp1.model.Lista;

@Service 
public class ListaServiceImplement implements ListaService{

  @Override
  public RespuestasDto<Lista> crearLista(Lista lista) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'crearLista'");
  }

  @Override
  public RespuestasDto<List<Lista>> listarListas() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'listarListas'");
  }

  @Override
  public RespuestasDto<Lista> obtenerLista(long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'obtenerLista'");
  }

  @Override
  public RespuestasDto<List<FavoritosSalidaDto>> favoritosDeLista(long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'favoritosDeLista'");
  }

  @Override
  public void eliminarLista(long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'eliminarLista'");
  }
  
}
