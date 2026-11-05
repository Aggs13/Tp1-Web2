package Web2.Tp1.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Health", description = "Verificación de estado del servidor (Endpoint público)")
public class HealthController {

  @Operation(summary = "Health Check", description = "Endpoint público que devuelve 200 OK si el servicio está activo.")
  @GetMapping("/health")
  public ResponseEntity<Map<String, String>> health() {
    return ResponseEntity.ok(Map.of("status", "UP"));
  }
}
