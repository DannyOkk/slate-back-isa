package com.slate.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slate.backend.dto.AuthResponse;
import com.slate.backend.dto.ChangePasswordRequest;
import com.slate.backend.dto.LoginRequest;
import com.slate.backend.dto.RegisterRequest;
import com.slate.backend.dto.UpdateProfileRequest;
import com.slate.backend.dto.UserProfileResponse;
import com.slate.backend.exception.GlobalExceptionHandler;
import com.slate.backend.service.AuthService;
import com.slate.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private AuthService authService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("POST /api/auth/register - Retorna 201 Created y token JWT ante registro exitoso")
    void register_success_returns201AndToken() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .name("Comerciante")
                .email("nuevo@slate.com")
                .password("password123")
                .phone("123456789")
                .build();

        AuthResponse authResponse = AuthResponse.builder()
                .id(1L)
                .name("Comerciante")
                .email("nuevo@slate.com")
                .token("jwt.token.de.prueba")
                .build();

        when(authService.register(any())).thenReturn(authResponse);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("jwt.token.de.prueba"))
                .andExpect(jsonPath("$.email").value("nuevo@slate.com"));
    }

    @Test
    @DisplayName("POST /api/auth/register - Retorna 409 Conflict si el email ya existe")
    void register_duplicateEmail_returns409() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .name("Comerciante")
                .email("existente@slate.com")
                .password("password123")
                .phone("123456789")
                .build();

        when(authService.register(any())).thenThrow(new IllegalArgumentException("El email ya está registrado"));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("POST /api/auth/login - Retorna 200 OK y token JWT con credenciales válidas")
    void login_validCredentials_returns200AndToken() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .email("valido@slate.com")
                .password("password123")
                .build();

        AuthResponse authResponse = AuthResponse.builder()
                .id(1L)
                .name("Comerciante")
                .email("valido@slate.com")
                .token("jwt.token.valido")
                .build();

        when(authService.login(any())).thenReturn(authResponse);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt.token.valido"))
                .andExpect(jsonPath("$.email").value("valido@slate.com"));
    }

    @Test
    @DisplayName("POST /api/auth/login - Retorna 401 Unauthorized con credenciales incorrectas")
    void login_invalidCredentials_returns401() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .email("usuario@slate.com")
                .password("passwordErroneo")
                .build();

        when(authService.login(any())).thenThrow(new BadCredentialsException("Credenciales inválidas"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/auth/validate - Retorna 200 OK y datos de perfil ante token válido")
    void validateToken_valid_returns200AndProfile() throws Exception {
        when(userService.getCurrentUserProfile()).thenReturn(
                UserProfileResponse.builder()
                        .id(1L)
                        .name("Usuario Verificado")
                        .email("verificado@slate.com")
                        .phone("5551234")
                        .build());

        mockMvc.perform(get("/api/auth/validate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Usuario Verificado"))
                .andExpect(jsonPath("$.email").value("verificado@slate.com"));
    }

    @Test
    @DisplayName("PUT /api/auth/profile - Actualiza el perfil correctamente")
    void updateProfile_returnsSafeResponseWithoutPassword() throws Exception {
        when(userService.updateCurrentUserProfile(any())).thenReturn(
                UserProfileResponse.builder()
                        .id(1L)
                        .name("Nuevo nombre")
                        .email("nuevo@email.com")
                        .phone("123")
                        .build());

        mockMvc.perform(put("/api/auth/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                java.util.Map.of("name", "Nuevo nombre", "email", "nuevo@email.com", "phone", "123"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nuevo nombre"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    @DisplayName("GET /api/auth/profile - Retorna datos del perfil actual")
    void getProfile_returnsSafeResponseWithoutPassword() throws Exception {
        when(userService.getCurrentUserProfile()).thenReturn(
                UserProfileResponse.builder()
                        .id(1L)
                        .name("Nombre actual")
                        .email("actual@email.com")
                        .phone("123")
                        .build());

        mockMvc.perform(get("/api/auth/profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nombre actual"))
                .andExpect(jsonPath("$.email").value("actual@email.com"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    @DisplayName("PUT /api/auth/profile - Rechaza nombre en blanco")
    void updateProfile_rejectsBlankName() throws Exception {
        mockMvc.perform(put("/api/auth/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"email\":\"valid@email.com\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/auth/profile - Rechaza formato de email inválido")
    void updateProfile_rejectsInvalidEmail() throws Exception {
        mockMvc.perform(put("/api/auth/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nombre\",\"email\":\"invalid\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/auth/password - Permite cambio de contraseña")
    void changePassword_returnsNoContent() throws Exception {
        mockMvc.perform(put("/api/auth/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"currentPassword\":\"actual\",\"newPassword\":\"nueva123\"}"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    @DisplayName("PUT /api/auth/password - Rechaza contraseña menor a 6 caracteres")
    void changePassword_rejectsNewPasswordShorterThanSixCharacters() throws Exception {
        mockMvc.perform(put("/api/auth/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"currentPassword\":\"actual\",\"newPassword\":\"12345\"}"))
                .andExpect(status().isBadRequest());
    }
}
