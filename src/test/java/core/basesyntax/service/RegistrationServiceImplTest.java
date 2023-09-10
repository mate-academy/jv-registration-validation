package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
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
        Storage.PEOPLE.clear();
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
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_invalidLogin_notOk() {
        expectedUser.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(expectedUser);
        });
        expectedUser.setLogin("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(expectedUser);
        });
        expectedUser.setLogin("Abcde");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_invalidPassword_notOk() {
        expectedUser.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(expectedUser);
        });
        expectedUser.setPassword("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(expectedUser);
        });
        expectedUser.setPassword("abcde");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_invalidAge_notOk() {
        expectedUser.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(expectedUser);
        });

        expectedUser.setAge(17);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(expectedUser);
        });
        expectedUser.setAge(-1);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_validUser_notOk() {
        User actualUser = registrationService.register(expectedUser);
        assertEquals(expectedUser, actualUser);
        assertNotNull(actualUser.getId());
    }

    @Test
    void register_userIsPersoent_notOk() {
        registrationService.register(expectedUser);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(expectedUser);
        });
    }
}
