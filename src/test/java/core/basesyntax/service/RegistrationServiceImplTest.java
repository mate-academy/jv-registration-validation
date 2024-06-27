package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final Long ID = 1L;
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final Integer NORMAL_AGE = 19;
    private static final Integer NEGATIVE_AGE = -1;
    private static final Integer AGE_LESS_THAN_18 = 14;
    private static RegistrationService service;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullFields_notOk() {
        User user = new User();
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_noValidLogin_notOk() {
        User user = new User();
        user.setAge(NORMAL_AGE);
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setAge(NORMAL_AGE);
        user.setLogin(null);
        user.setPassword(PASSWORD);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_loginMinimumLength_Ok() {
        User user = new User();
        user.setAge(NORMAL_AGE);
        user.setLogin("login1");
        user.setPassword("pass123");
        User register = service.register(user);
        assertEquals(user, register);
    }

    @Test
    void register_noValidPassword_notOk() {
        User user = new User();
        user.setAge(NORMAL_AGE);
        user.setLogin("goodlogin");
        user.setPassword("pass");
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setAge(NORMAL_AGE);
        user.setLogin("goodlogin");
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_passwordMinimumLength_Ok() {
        User user = new User();
        user.setAge(NORMAL_AGE);
        user.setLogin("goodlogin");
        user.setPassword("pass12");
        User register = service.register(user);
        assertEquals(user, register);
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setAge(NEGATIVE_AGE);
        user.setLogin("goodlogin");
        user.setPassword(PASSWORD);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_ageLessThan18_notOk() {
        User user = new User();
        user.setAge(AGE_LESS_THAN_18);
        user.setLogin("goodlogin");
        user.setPassword(PASSWORD);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setAge(null);
        user.setLogin("goodlogin");
        user.setPassword(PASSWORD);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_ValidData_ok() {
        User user = new User();
        user.setAge(NORMAL_AGE);
        user.setLogin("validlogin");
        user.setPassword(PASSWORD);
        User register = service.register(user);
        assertEquals(user, register);
    }

    @Test
    void register_ExistingUser_notOk() {
        User user = new User();
        user.setAge(NORMAL_AGE);
        user.setLogin("greatuser12");
        user.setPassword("secretpassword");
        Storage.people.add(user);
        User anotherUser = new User();
        anotherUser.setAge(NORMAL_AGE);
        anotherUser.setLogin("greatuser12");
        anotherUser.setPassword("password12");
        assertThrows(RegistrationException.class, () -> service.register(anotherUser));
    }
}
