package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService service;

    @BeforeEach
    void init() {
        Storage.people.clear();
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_valid_user_ok() {
        User expected = new User("validLogin", "123456789", 25);
        User actual = service.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_bulkValidUsers_ok() {
        User firstUser = new User("validLogin", "123456789", 25);
        User actual = service.register(firstUser);
        assertEquals(firstUser, actual);

        User secondUser = new User("validLogin2", "123456789", 35);
        actual = service.register(secondUser);
        assertEquals(secondUser, actual);

        User thirdUser = new User("validLogin3", "123456789", 30);
        actual = service.register(thirdUser);
        assertEquals(thirdUser, actual);
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;
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
    void register_youngAge_notOk() {
        User user = new User("PeterPen", "123456", 16);
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
        User actual = service.register(user);
        assertEquals(actual, user);
        User newUser = new User("validLogin", "987654321", 52);
        assertThrows(RegistrationException.class, () -> {
            service.register(newUser);
        });
    }
}
