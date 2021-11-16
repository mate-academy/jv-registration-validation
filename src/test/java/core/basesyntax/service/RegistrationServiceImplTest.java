package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user.setLogin("goodLogin");
        user.setPassword("goodPassword");
        user.setAge(18);
    }

    @AfterAll
    static void afterAll() {
        Storage.people.clear();
    }

    @Test
    void registration_validPassword_Ok() {
        user.setPassword("goodPassword");
        User actual = registrationService.register(user);
        User expected = user;
        assertEquals(expected, actual);
    }

    @Test
    void registration_validLogin_Ok() {
        user.setLogin("niceLogin");
        User actual = registrationService.register(user);
        User expected = user;
        assertEquals(expected, actual);
    }

    @Test
    void registration_nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_emptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_passwordLength_NotOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_ageVerification_NotOk() {
        user.setAge(10);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_ageIsNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_validAge_Ok() {
        user.setAge(18);
        User actual = registrationService.register(user);
        User expected = user;
        assertEquals(expected, actual);
    }

    @Test
    void registration_validUser_Ok() {
        User actual = registrationService.register(user);
        User expected = user;
        assertEquals(expected, actual);
    }
}
