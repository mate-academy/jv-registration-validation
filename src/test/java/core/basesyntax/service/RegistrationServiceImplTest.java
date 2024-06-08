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
        User first = new User("validLogin", "123456789", 25);
        User actual = service.register(first);
        User expected = first;
        assertEquals(expected, actual);

        User second = new User("validLogin2", "123456789", 35);
        actual = service.register(second);
        expected = second;
        assertEquals(expected, actual);

        User third = new User("validLogin3", "123456789", 30);
        actual = service.register(third);
        expected = third;
        assertEquals(expected, actual);
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_password_notOk() {
        User user = new User("IvanGun", "1", 25);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_age_notOk() {
        User user = new User("PeterPen", "123456", 16);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_login_notOk() {
        User user = new User("I", "123456", 25);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_twiceUser_notOk() {
        User firstUser = new User("validLogin", "123456789", 25);
        User actual = service.register(firstUser);
        User expected = firstUser;
        assertEquals(actual, expected);
        User secondUser = new User("validLogin", "987654321", 52);
        assertThrows(RegistrationException.class, () -> {
            service.register(secondUser);
        });
    }
}
