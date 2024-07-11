package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private RegistrationService service;
    private final User firstUser = new User("validLogin1", "123456789", 25);
    private final User secondUser = new User("validLogin2", "abcdefghqwerty", 35);
    private final User thirdUser = new User("validLogin3", "qwerty12345", 30);

    @BeforeEach
    void init() {
        Storage.people.clear();
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User actual = service.register(firstUser);
        assertEquals(firstUser, actual);
    }

    @Test
    void register_validUsers_ok() {
        service.register(firstUser);
        service.register(secondUser);
        service.register(thirdUser);
        assertTrue(Storage.people.size() == 3);
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User("validLogin", null, 25);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User("IvanGun", "1", 25);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_noAge_notOk() {
        User user = new User("PeterPen", "123456", 0);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User("PeterPen", "123456", -20);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_youngAge_notOk() {
        User user = new User("PeterPen", "123456", 16);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, "123456", 25);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User("I", "123456", 25);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_loginAlreadyExist_notOk() {
        User user = new User("validLogin", "123456789", 25);
        Storage.people.add(user);
        User newUser = new User("validLogin", "987654321", 52);
        assertThrows(RegistrationException.class, () -> {
            service.register(newUser);
        });
    }
}
