package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setLogin("validLogin");
        validUser.setPassword("validPassword");
        validUser.setAge(20);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_Ok() {
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
        assertEquals(validUser, Storage.people.get(0));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        validUser.setLogin("12345");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_existingLogin_notOk() {
        Storage.people.add(validUser);
        User duplicateUser = new User();
        duplicateUser.setLogin("validLogin");
        duplicateUser.setPassword("anotherPass");
        duplicateUser.setAge(25);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(duplicateUser);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        validUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        validUser.setPassword("12345");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_nullAge_notOk() {
        validUser.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_underAge_notOk() {
        validUser.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }
}
