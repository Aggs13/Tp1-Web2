package Web2.Tp1.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ListaControllerIT {

  @Container
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @DynamicPropertySource
  static void props(DynamicPropertyRegistry r) {
    r.add("spring.datasource.url", postgres::getJdbcUrl);
    r.add("spring.datasource.username", postgres::getUsername);
    r.add("spring.datasource.password", postgres::getPassword);
  }

  @Autowired MockMvc mockMvc;
  @Autowired JdbcTemplate jdbc;
  @Autowired PasswordEncoder encoder;

  Long user1Id;
  Long listaDeUser1Id;

  @BeforeEach
  void setUp() {
    jdbc.execute("DELETE FROM favoritos; DELETE FROM lista; DELETE FROM usuarios;");
    String hash = encoder.encode("1234");
    user1Id = jdbc.queryForObject(
        "INSERT INTO usuarios (email, password_hash, rol) VALUES ('u1@mail.com', ?, 'USER') RETURNING id",
        Long.class, hash);
    jdbc.update("INSERT INTO usuarios (email, password_hash, rol) VALUES ('u2@mail.com', ?, 'USER')", hash);
    jdbc.update("INSERT INTO usuarios (email, password_hash, rol) VALUES ('admin@mail.com', ?, 'ADMIN')", hash);
    listaDeUser1Id = jdbc.queryForObject(
        "INSERT INTO lista (nombre, usuario_id) VALUES ('Lista U1', ?) RETURNING id",
        Long.class, user1Id);
  }

  private String login(String email) throws Exception {
    String res = mockMvc.perform(post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"email\":\"" + email + "\",\"pass\":\"1234\"}"))
        .andReturn().getResponse().getContentAsString();
    return JsonPath.read(res, "$.datos");
  }

  @Test
  void login_credencialesValidas_devuelve200ConToken() throws Exception {
    mockMvc.perform(post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"email\":\"u1@mail.com\",\"pass\":\"1234\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.datos").isNotEmpty());
  }

  @Test
  void login_credencialesInvalidas_devuelve401() throws Exception {
    mockMvc.perform(post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"email\":\"u1@mail.com\",\"pass\":\"mal\"}"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void obtener_listaPropia_devuelve200() throws Exception {
    mockMvc.perform(get("/api/listas/" + listaDeUser1Id)
        .header("Authorization", "Bearer " + login("u1@mail.com")))
        .andExpect(status().isOk());
  }

  @Test
  void obtener_listaAjena_devuelve403() throws Exception {
    mockMvc.perform(get("/api/listas/" + listaDeUser1Id)
        .header("Authorization", "Bearer " + login("u2@mail.com")))
        .andExpect(status().isForbidden());
  }

  @Test
  void listar_sinToken_devuelve401() throws Exception {
    mockMvc.perform(get("/api/listas"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void obtener_adminListaAjena_devuelve200() throws Exception {
    mockMvc.perform(get("/api/listas/" + listaDeUser1Id)
        .header("Authorization", "Bearer " + login("admin@mail.com")))
        .andExpect(status().isNotFound());
  }
}
