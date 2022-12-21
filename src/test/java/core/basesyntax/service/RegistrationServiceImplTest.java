package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    static final int MIN_AGE = 18;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_AgeLessThan18_NotOk() {
        User testUser = new User();
        testUser.setAge(10);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_AgeIsNull_NotOk() {
        User expectedUser = new User();
        expectedUser.setAge(null);
        expectedUser.setPassword("HelloWorld");
        expectedUser.setLogin("Bob@gmail.com");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_AgeMoreThan18_Ok() {
        User expectedUser = new User();
        expectedUser.setAge(20);
        expectedUser.setPassword("HelloWorld");
        expectedUser.setLogin("Alice@ukr.net");
        User actualUser = registrationService.register(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_AgeIs18_Ok() {
        User expectedUser = new User();
        expectedUser.setAge(MIN_AGE);
        expectedUser.setPassword("HelloWorld");
        expectedUser.setLogin("Jhon@gmail.com");
        User actualUser = registrationService.register(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_NegativeAge_NotOk() {
        User expectedUser = new User();
        expectedUser.setAge(-8);
        expectedUser.setPassword("HelloWorld");
        expectedUser.setLogin("Jane@gmail.com");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_UserWasAdded_Ok() {
        User kile = new User();
        kile.setAge(22);
        kile.setLogin("kile@gmail.com");
        kile.setPassword("weirdKon20");
        registrationService.register(kile);
        assertEquals(1, Storage.people.size());
        assertTrue(Storage.people.contains(kile));
    }

    @Test
    void register_PasswordIsNull_NotOk() {
        User testUser = new User();
        testUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_PasswordLessThanMinLength_NotOk() {
        User expectedUser = new User();
        expectedUser.setAge(30);
        expectedUser.setPassword("yeah");
        expectedUser.setLogin("Daniel@yahoo.com");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_PasswordMoreThanMinLength_Ok() {
        User expectedUser = new User();
        expectedUser.setAge(28);
        expectedUser.setPassword("HelloWorld");
        expectedUser.setLogin("Georg@gmail.com");
        User actualUser = registrationService.register(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_PasswordIsMinLength_Ok() {
        User expectedUser = new User();
        expectedUser.setAge(19);
        expectedUser.setPassword("Hello!");
        expectedUser.setLogin("Melanie@yahoo.com");
        User actualUser = registrationService.register(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_LoginIsNull_NotOk() {
        User expectedUser = new User();
        expectedUser.setAge(23);
        expectedUser.setPassword("Hello!");
        expectedUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_UserWithLoginNotExistsYet_Ok() {
        User expectedUser = new User();
        expectedUser.setAge(25);
        expectedUser.setPassword("Hello!");
        expectedUser.setLogin("Maya@ukr.net");
        assertFalse(Storage.people.contains(expectedUser));
    }

    @Test
    void register_UserWithLoginAlreadyExists_NotOk() {
        User expectedUser = new User();
        expectedUser.setAge(19);
        expectedUser.setPassword("Hello!");
        expectedUser.setLogin("David@yahoo.com");
        registrationService.register(expectedUser);
        assertTrue(Storage.people.contains(expectedUser));
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_UserIsNull_NotOk() {
        User expectedUser = null;
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(expectedUser);
        });
    }
}
