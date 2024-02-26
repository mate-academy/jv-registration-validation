package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService service;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_validCase_Ok() {
        final User firstUser = new User("firstUser", "123456", 22);
        final User secondUser = new User("secondUser", "password", 18);
        User firstActual = service.register(firstUser);
        assertEquals(firstUser, firstActual);
        User secondActual = service.register(secondUser);
        assertEquals(secondUser, secondActual);
        assertEquals(2, Storage.people.size());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> service.register(null));
    }

    @Test
    void register_ageLessMinAge_notOk() {
        final User user = new User("firstUser", "myPassword", 16);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_passwordLessMinLength_notOk() {
        final User user = new User("firstUser", "small", 19);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        final User user = new User("firstUser", null, 21);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        final User user = new User("firstUser", "password", null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        final User user = new User(null, "validPassword", 19);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        final User user = new User("firstUser", "validPassword", -1);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_loginLessMinLength_notOk() {
        final User user = new User("small", "validPassword", 18);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_sameLogin_notOk() {
        final User user = new User("firstUser", "123456", 22);
        final User sameUser = new User("firstUser", "validPassword", 23);
        User actual = service.register(user);
        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
        assertThrows(RegistrationException.class, () -> service.register(sameUser));
    }
}
