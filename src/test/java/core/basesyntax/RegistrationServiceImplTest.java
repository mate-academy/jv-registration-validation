package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {

    private RegistrationServiceImpl service;

    private static final String VALID_LOGIN = "validUser";
    private static final String VALID_PASSWORD = "password";
    private static final int VALID_AGE = 20;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);

        User result = service.register(user);

        assertNotNull(result);
        assertNotNull(Storage.people.stream()
                .filter(u -> u.getLogin().equals(VALID_LOGIN))
                .findFirst()
                .orElse(null));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(null));
    }

    @Test
    void register_existingLogin_notOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        Storage.people.add(user);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User("usr", VALID_PASSWORD, VALID_AGE);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_loginBoundary6_ok() {
        User user = new User("123456", VALID_PASSWORD, VALID_AGE);

        service.register(user);

        assertNotNull(Storage.people.stream()
                .filter(u -> u.getLogin().equals("123456"))
                .findFirst()
                .orElse(null));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User(VALID_LOGIN, "123", VALID_AGE);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_ageUnder18_notOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, 17);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_ageBoundary18_ok() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, 18);

        service.register(user);

        assertNotNull(Storage.people.stream()
                .filter(u -> u.getLogin().equals(VALID_LOGIN))
                .findFirst()
                .orElse(null));
    }

    @Test
    void register_edgePassword5_notOk() {
        User user = new User(VALID_LOGIN, "12345", VALID_AGE);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_edgePassword6_ok() {
        User user = new User(VALID_LOGIN, "123456", VALID_AGE);

        service.register(user);

        assertNotNull(Storage.people.stream()
                .filter(u -> u.getLogin().equals(VALID_LOGIN))
                .findFirst()
                .orElse(null));
    }
}