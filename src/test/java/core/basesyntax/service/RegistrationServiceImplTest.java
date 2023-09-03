package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void tearDown() {
        Storage.USERS.clear();
    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        expectedUser = new User();
        expectedUser.setLogin("MyName");
        expectedUser.setPassword("MyPassword");
        expectedUser.setAge(18);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_invalidLogin_notOk() {
        expectedUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expectedUser);
        });
        expectedUser.setLogin("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expectedUser);
        });
        expectedUser.setLogin("Abcde");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_invalidPassword_notOk() {
        expectedUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expectedUser);
        });
        expectedUser.setPassword("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expectedUser);
        });
        expectedUser.setPassword("asdfg");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_invalidAge_notOk() {
        expectedUser.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expectedUser);
        });
        expectedUser.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expectedUser);
        });
        expectedUser.setAge(-1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_validUser_Ok() {
        User actualUser = registrationService.register(expectedUser);
        assertEquals(expectedUser, actualUser);
        assertNotNull(actualUser.getId());
    }

    @Test
    void register_userIsPresent_notOk() {
        registrationService.register(expectedUser);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expectedUser);
        });
    }
}
