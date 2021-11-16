package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void validPassword_Ok() {
        user.setPassword("goodPassword");
        User actual = registrationService.register(user);
        User expected = user;
        assertEquals(expected, actual);
    }

    @Test
    void validLogin_Ok() {
        user.setLogin("niceLogin");
        User actual = registrationService.register(user);
        User expected = user;
        assertEquals(expected, actual);
    }

    @Test
    void nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void emptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordLength_NotOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void ageVerification_NotOk() {
        user.setAge(10);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void ageIsNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void validAge_Ok() {
        user.setAge(18);
        User actual = registrationService.register(user);
        User expected = user;
        assertEquals(expected, actual);
    }

    @Test
    void validUser_Ok() {
        User actual = registrationService.register(user);
        User expected = user;
        assertEquals(expected, actual);
    }
}
