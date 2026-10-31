package com.slate.backend.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private final String testSecret = "claveSecretaDePruebasConMasDeTreintaYDosBytes123456789";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", testSecret);
        ReflectionTestUtils.setField(jwtService, "expirationMs", 3600000L); // 1 hora
    }

    @Test
    @DisplayName("Debe generar un token JWT y extraer el email correctamente")
    void generateToken_andExtractEmail_success() {
        String email = "comerciante@slate.com";
        String token = jwtService.generateToken(email);

        assertNotNull(token);
        assertFalse(token.isBlank());

        String extractedEmail = jwtService.extractEmail(token);
        assertEquals(email, extractedEmail);
    }

    @Test
    @DisplayName("isTokenValid debe retornar true si el email coincide y el token está vigente")
    void isTokenValid_validToken_returnsTrue() {
        String email = "usuario@slate.com";
        String token = jwtService.generateToken(email);

        boolean isValid = jwtService.isTokenValid(token, email);
        assertTrue(isValid);
    }

    @Test
    @DisplayName("isTokenValid debe retornar false si el email no coincide con el subject del token")
    void isTokenValid_differentEmail_returnsFalse() {
        String emailToken = "usuario1@slate.com";
        String emailCheck = "usuario2@slate.com";
        String token = jwtService.generateToken(emailToken);

        boolean isValid = jwtService.isTokenValid(token, emailCheck);
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Debe lanzar ExpiredJwtException si el token ya expiró")
    void isTokenValid_expiredToken_throwsExpiredJwtException() {
        // Configuramos expiración en el pasado (-10 segundos)
        ReflectionTestUtils.setField(jwtService, "expirationMs", -10000L);

        String email = "expirado@slate.com";
        String expiredToken = jwtService.generateToken(email);

        assertThrows(ExpiredJwtException.class, () -> jwtService.isTokenValid(expiredToken, email));
    }

    @Test
    @DisplayName("Debe fallar con SignatureException si el token fue firmado con otra clave")
    void validateToken_invalidSignature_throwsSignatureException() {
        String token = jwtService.generateToken("victima@slate.com");

        // Creamos otro servicio con clave distinta para verificar el fallo de firma
        JwtService attackerService = new JwtService();
        ReflectionTestUtils.setField(attackerService, "secret", "otraClaveDiferenteCompletamenteInvalida1234567890123");
        ReflectionTestUtils.setField(attackerService, "expirationMs", 3600000L);

        assertThrows(SignatureException.class, () -> attackerService.extractEmail(token));
    }
}

