package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final User user = new User();
    private final String login = "slavik";
    private final int age = 18;
    private final String password = "Sukhov28";
    private static final int MIN_AGE = 18;
    private static final int MIN_PASS_LENGTH = 6;

    @BeforeEach
    void setUp() {
        user.setAge(age);
        user.setLogin(login);
        user.setPassword(password);
    }

    @Test
    void register_UserAgeNotOk_ThrowsException_Ok() {
        user.setAge(12);
        assertThrows(CustomException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserAge_Ok() {
        registrationService.register(user);
        assertTrue(user.getAge() >= MIN_AGE);
    }

    @Test
    void register_UserLoginNull_ThrowsException_Ok() {
        user.setLogin(null);
        assertThrows(CustomException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserLogin_NotNull() {
        String actualLogin = user.getLogin();
        assertNotNull(actualLogin);
        registrationService.register(user);
    }

    @Test
    void register_UserPasswordLength_Ok() {
        registrationService.register(user);
        assertTrue(user.getPassword().length() > MIN_PASS_LENGTH);
    }

    @Test
    void register_UserPasswordLength_ThrowsException_Ok() {
        user.setPassword("123");
        assertThrows(CustomException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserPasswordNull_ThrowsException_Ok() {
        user.setPassword(null);
        assertThrows(CustomException.class, () -> {
            registrationService.register(user);
        });
    }
}