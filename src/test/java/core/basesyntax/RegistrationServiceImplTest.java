package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService service;

    @BeforeEach
    void setUp() {
        service = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    private User getCorrectUser() {
        User user = new User();
        user.setAge(20);
        user.setPassword("123456");
        user.setLogin("validLogin");
        return user;
    }

    @Test
    void register_validUser_ok() {
        User user = getCorrectUser();
        User result = service.register(user);
        assertEquals(user, result);
        assertEquals(1, Storage.people.size());
        assertEquals(user, Storage.people.get(0));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> service.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = getCorrectUser();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_loginLength5_notOk() {
        User user = getCorrectUser();
        user.setLogin("abcde");
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_loginLength6_ok() {
        User user = getCorrectUser();
        user.setLogin("abcdef");
        User result = service.register(user);
        assertEquals(user, result);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_nullPassword_notOk() {
        User user = getCorrectUser();
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_passwordLength5_notOk() {
        User user = getCorrectUser();
        user.setPassword("abcde");
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_passwordLength6_ok() {
        User user = getCorrectUser();
        user.setPassword("abcdef");
        User result = service.register(user);
        assertEquals(user, result);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_nullAge_notOk() {
        User user = getCorrectUser();
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = getCorrectUser();
        user.setAge(-1);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_age17_notOk() {
        User user = getCorrectUser();
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_age18_ok() {
        User user = getCorrectUser();
        user.setAge(18);
        User result = service.register(user);
        assertEquals(user, result);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_duplicateLogin_notOk() {
        User firstUser = getCorrectUser();
        Storage.people.add(firstUser);
        User sameLoginUser = new User();
        sameLoginUser.setAge(25);
        sameLoginUser.setPassword("password123");
        sameLoginUser.setLogin(firstUser.getLogin());
        assertThrows(RegistrationException.class, () -> service.register(sameLoginUser));
    }
}
