package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationServiceImpl register;

    @BeforeEach
    void setUp() {
        register = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void wrongAge_notOk() {
        User user = new User();
        user.setAge(15);
        user.setLogin("dasersix");
        user.setPassword("1234565");
        assertThrows(InvalidDataException.class, () -> register.register(user));
    }

    @Test
    void minusAge_notOk() {
        User user = new User();
        user.setAge(-5);
        user.setLogin("dasersix");
        user.setPassword("1234565");
        assertThrows(InvalidDataException.class, () -> register.register(user));
    }

    @Test
    void edgeAge_notOk() {
        User user = new User();
        user.setAge(17);
        user.setLogin("dasersix");
        user.setPassword("1234565");
        assertThrows(InvalidDataException.class, () -> register.register(user));
    }

    @Test
    void exactAge_Ok() {
        User user = new User();
        user.setAge(18);
        user.setLogin("dasersix");
        user.setPassword("12345665");
        User result = register.register(user);
        assertNotNull(result);
        assertEquals(18, result.getAge());
    }

    @Test
    void wrongPassword_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin("dasersix");
        user.setPassword("1234");
        assertThrows(InvalidDataException.class, () -> register.register(user));
    }

    @Test
    void edgePassword_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin("dasersix");
        user.setPassword("12345");
        assertThrows(InvalidDataException.class, () -> register.register(user));
    }

    @Test
    void exactPassword_Ok() {
        User user = new User();
        user.setAge(19);
        user.setLogin("dasersix");
        user.setPassword("123456");
        User result = register.register(user);
        assertNotNull(result);
        assertEquals("123456", result.getPassword());
    }

    @Test
    void wrongLogin_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin("dase");
        user.setPassword("12345678");
        assertThrows(InvalidDataException.class, () -> register.register(user));
    }

    @Test
    void edgeLogin_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin("daser");
        user.setPassword("12345678");
        assertThrows(InvalidDataException.class, () -> register.register(user));
    }

    @Test
    void exactLogin_Ok() {
        User user = new User();
        user.setAge(19);
        user.setLogin("dasers");
        user.setPassword("12345678");
        User result = register.register(user);
        assertNotNull(result);
        assertEquals("dasers", result.getLogin());
    }

    @Test
    void emptyUser_notOk() {
        User user = new User();
        assertThrows(InvalidDataException.class, () -> register.register(user));
    }

    @Test
    void userExists_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin("dasersix");
        user.setPassword("12345678");
        Storage.people.add(user);
        assertThrows(InvalidDataException.class, () -> register.register(user));
    }

    @Test
    void nullUser_notOk() {
        User user = null;
        assertThrows(InvalidDataException.class, () -> register.register(user));
    }

    @Test
    void nullPassword_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin("12345678");
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> register.register(user));
    }

    @Test
    void nullLogin_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin(null);
        user.setPassword("dasersix");
        assertThrows(InvalidDataException.class, () -> register.register(user));
    }

    @Test
    void nullAge_notOk() {
        User user = new User();
        user.setAge(null);
        user.setLogin("dasersix");
        user.setPassword("12345678");
        assertThrows(InvalidDataException.class, () -> register.register(user));
    }
}
