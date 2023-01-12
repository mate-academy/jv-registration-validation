package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Viktoria");
        user.setAge(23);
        user.setPassword("qwerty");
    }

    @Test
    void register_user_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(ValidationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_containsLogin_notOk() {
        registrationService.register(user);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_notAdultAge_notOk() {
        user.setAge(10);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("qwert");
        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
