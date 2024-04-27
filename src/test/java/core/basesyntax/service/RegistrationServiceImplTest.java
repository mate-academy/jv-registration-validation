package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterAll;
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
        user.setLogin("loogiin");
        user.setPassword("password");
        user.setAge(20);
    }

    @Test
    void register_validUser_ok() {
        assertEquals(registrationService.register(user), user);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_login_notOk() {
        user.setLogin("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        user.setLogin("s");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        user.setLogin("5char");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_6CharsLogin_ok() {
        user.setLogin("6chars");
        assertEquals(registrationService.register(user), user);
    }

    @Test
    void register_moreThan6CharsLogin_ok() {
        user.setLogin("someMoreChars");
        assertEquals(registrationService.register(user), user);
    }

    @Test
    void register_passwors_notOk() {
        user.setPassword("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        user.setPassword("s");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        user.setPassword("5char");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_6CharsPassword_ok() {
        user.setPassword("6chars");
        assertEquals(registrationService.register(user), user);
    }

    @Test
    void register_moreThan6CharsPassword_ok() {
        user.setPassword("someMoreChars");
        assertEquals(registrationService.register(user), user);
    }

    @Test
    void register_age_notOk() {
        user.setAge(10);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        user.setAge(-19);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        user.setAge(17);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_18Age_ok() {
        user.setAge(18);
        assertEquals(registrationService.register(user), user);
    }

    @Test
    void register_greaterThan18Age_ok() {
        user.setAge(28);
        assertEquals(registrationService.register(user), user);
    }

    @Test
    void register_nullUser_notOk() {
        User nullUser = null;
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(nullUser);
        });
    }

    @Test
    void register_Exists_notOk() {
        User sameUser = user;
        Storage.people.add(user);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(sameUser);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @AfterAll
    static void afterAll() {
        registrationService = new RegistrationServiceImpl();
    }
}
