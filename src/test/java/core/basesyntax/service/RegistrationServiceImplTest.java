package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void validUser_Ok() {
        User user = new User();
        user.setLogin("validLoginOk");
        user.setPassword("12345678");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void userWithSameLoginInStorage_NotOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("12345678");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void loginHasMoreSixCharacters_Ok() {
        User user = new User();
        user.setLogin("validLoginOk");
        user.setPassword("12345678");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void loginHasLessSixCharacters_NotOk() {
        User user = new User();
        user.setLogin("valid");
        user.setPassword("12345678");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void loginNull_NotOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("12345678");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordHasMoreSixCharacters_Ok() {
        User user = new User();
        user.setLogin("validLoginOk");
        user.setPassword("12345678");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void passwordHasLessSixCharacters_NotOk() {
        User user = new User();
        user.setLogin("validLoginOk");
        user.setPassword("12345");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordNull_NotOk() {
        User user = new User();
        user.setLogin("validLoginOk");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void userAgeIsUpper18_Ok() {
        User user = new User();
        user.setLogin("validLoginOk");
        user.setPassword("123456789");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void userAgeIsLess18_NotOk() {
        User user = new User();
        user.setLogin("validLoginOk");
        user.setPassword("12345678");
        user.setAge(15);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void userAgeNull_NotOk() {
        User user = new User();
        user.setLogin("validLoginOk");
        user.setPassword("12345678");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
