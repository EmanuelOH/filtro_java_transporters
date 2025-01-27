package com.irwi.transporters.securityTest;

import com.irwi.transporters.domain.entities.UserEntity;
import com.irwi.transporters.domain.enums.Roles;
import com.irwi.transporters.infrastructure.helpers.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // Carga el contexto completo de Spring
@AutoConfigureMockMvc // Habilita la capacidad de realizar peticiones HTTP Mock
@DisplayName("Pruebas de Seguridad") // Nombre para las pruebas de seguridad
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc se usa para hacer peticiones y simular interacciones con los controladores

    @Autowired
    private JwtUtil jwtUtil; // Utilidad para trabajar con JWT

    private static final String PUBLIC_ENDPOINT = "/swagger-ui/index.html"; // Endpoint público de prueba
    private static final String ADMIN_ENDPOINT = "/api/carriers/loads"; // Endpoint de administrador
    private static final String TRANSPORTER_ENDPOINT = "/api/carriers/loads"; // Endpoint de transportista

    // Test para verificar que los endpoints públicos son accesibles sin autenticación
    @Test
    @DisplayName("Debe permitir acceso publico a los endpoints publicos")
    void shouldAllowPublicAccessToPublicEndPoints() throws Exception {
        mockMvc.perform(get(PUBLIC_ENDPOINT)) // Realiza una solicitud GET al endpoint público
                .andExpect(status().isOk()); // Espera un código 200 OK en la respuesta
    }

    // Test para verificar que los endpoints de admin deniegan el acceso a usuarios no autenticados
    @Test
    @DisplayName("Debe denegar acceso a los endpoints de admin a usuarios no autenticados")
    void shouldDenyAccessToUnauthenticatedUsers() throws Exception {
        mockMvc.perform(get(ADMIN_ENDPOINT)) // Realiza una solicitud GET al endpoint de administrador
                .andExpect(status().isUnauthorized()); // Espera un código 401 Unauthorized si no está autenticado
    }

    // Test para verificar que un administrador autenticado puede acceder a los endpoints de administrador
    @Test
    @DisplayName("Debe permitir acceso a los endpoints de admin con JWT adecuado")
    void shouldAllowAdminAccessToAdminEndPoints() throws Exception {
        UserEntity adminUser = new UserEntity();
        adminUser.setId(1L);
        adminUser.setUsername("admin");
        adminUser.setRole(Roles.valueOf("ADMIN")); // Asignamos el rol de ADMIN al usuario

        // Generamos un JWT válido para el admin
        String adminToken = "Bearer " + jwtUtil.generateToken(adminUser);

        mockMvc.perform(get(ADMIN_ENDPOINT)
                        .header("Authorization", adminToken)) // Enviamos la solicitud con el header Authorization
                .andExpect(status().isOk()); // Espera un código 200 OK si el JWT es válido
    }

    // Test para verificar que los endpoints de transportista deniegan el acceso sin JWT adecuado
    @Test
    @DisplayName("Debe denegar acceso a los endpoints de transporter sin JWT adecuado")
    void shouldDenyAccessToTransporterEndPointsWithoutValidToken() throws Exception {
        mockMvc.perform(get(TRANSPORTER_ENDPOINT)) // Solicita el endpoint de transportista sin autorización
                .andExpect(status().isUnauthorized()); // Espera un código 401 Unauthorized si no se proporciona un JWT
    }

    // Test para verificar que un transportista autenticado puede acceder a los endpoints de transportista
    @Test
    @DisplayName("Debe permitir acceso a los endpoints de transporter con JWT adecuado")
    void shouldAllowTransporterAccessToTransporterEndPoints() throws Exception {
        UserEntity transporterUser = new UserEntity();
        transporterUser.setId(1L);
        transporterUser.setUsername("transporter");
        transporterUser.setRole(Roles.valueOf("TRANSPORTER")); // Asignamos el rol de TRANSPORTER al usuario

        // Generamos un JWT válido para el transportista
        String transporterToken = "Bearer " + jwtUtil.generateToken(transporterUser);

        mockMvc.perform(get(TRANSPORTER_ENDPOINT)
                        .header("Authorization", transporterToken)) // Enviamos el token en el header Authorization
                .andExpect(status().isOk()); // Espera un código 200 OK si el JWT es válido
    }
}
