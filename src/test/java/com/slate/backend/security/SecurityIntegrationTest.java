package com.slate.backend.security;

import com.slate.backend.entity.User;
import com.slate.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class SecurityIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;
    private String validToken;
    private final String testEmail = "seguridad@slate.com";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        if (!userRepository.existsByEmail(testEmail)) {
            User user = User.builder()
                    .name("Usuario Seguridad")
                    .email(testEmail)
                    .password(passwordEncoder.encode("passwordSeguro123"))
                    .phone("123456789")
                    .build();
            userRepository.save(user);
        }
        validToken = jwtService.generateToken(testEmail);
    }

    @Test
    @DisplayName("Endpoint público: /actuator/health debe responder 200 sin autenticación")
    void publicEndpoint_health_accessibleWithoutAuth() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Endpoint público: /api/auth/login debe ser accesible sin cabecera Authorization")
    void publicEndpoint_login_accessibleWithoutAuth() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"inexistente@slate.com\",\"password\":\"wrong\"}"))
                .andExpect(status().isUnauthorized()); // Responde 401 por credenciales, NO 403 por falta de token
    }

    @Test
    @DisplayName("Endpoint protegido: /api/clients debe rechazar peticiones sin token (403 Forbidden)")
    void protectedEndpoint_clients_rejectedWithoutToken() throws Exception {
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Endpoint protegido: /api/auth/profile debe rechazar peticiones sin token (403 Forbidden)")
    void protectedEndpoint_profile_rejectedWithoutToken() throws Exception {
        mockMvc.perform(get("/api/auth/profile"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Endpoint protegido: /api/auth/validate debe aceptar peticiones con token JWT válido (200 OK)")
    void protectedEndpoint_validate_acceptedWithValidToken() throws Exception {
        mockMvc.perform(get("/api/auth/validate")
                        .header("Authorization", "Bearer " + validToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Endpoint protegido: /api/auth/validate debe rechazar peticiones con token falso o manipulado (403 Forbidden)")
    void protectedEndpoint_validate_rejectedWithInvalidToken() throws Exception {
        String invalidToken = validToken + "tampered";
        mockMvc.perform(get("/api/auth/validate")
                        .header("Authorization", "Bearer " + invalidToken))
                .andExpect(status().isForbidden());
    }
}
