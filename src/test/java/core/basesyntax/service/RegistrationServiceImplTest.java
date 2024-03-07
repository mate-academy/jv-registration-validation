package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User expectedUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        expectedUser = new User();
        expectedUser.setLogin("UserLogin");
        expectedUser.setPassword("UserPassword");
        expectedUser.setAge(18);
    }

    @Test
    void register_validUser_Ok() {
        User actualUser = registrationService.register(expectedUser);
        assertEquals(expectedUser, actualUser);
        assertNotNull(actualUser.getId());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_dublicatedUser_notOk() {
        User userDouble = new User();
        userDouble.setLogin(expectedUser.getLogin());
        Storage.people.add(userDouble);
        assertThrows(RegistrationException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_invalidLogin_notOk() {
        expectedUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(expectedUser));
        expectedUser.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(expectedUser));
        expectedUser.setLogin("lll");
        assertThrows(RegistrationException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_invalidPassword_notOk() {
        expectedUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(expectedUser));
        expectedUser.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(expectedUser));
        expectedUser.setPassword("ppp");
        assertThrows(RegistrationException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_invalidAge_notOk() {
        expectedUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(expectedUser));
        expectedUser.setAge(15);
        assertThrows(RegistrationException.class, () -> registrationService.register(expectedUser));
        expectedUser.setAge(-15);
        assertThrows(RegistrationException.class, () -> registrationService.register(expectedUser));
    }
}
