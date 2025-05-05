package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private final User user = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_OK() {
        User expected = new User();
        expected.setLogin("Mike1984");
        expected.setPassword("483asd~@djs");
        expected.setAge(19);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setAge(19);
        user.setPassword("483asd~@djs");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setLogin("Mike1984");
        user.setAge(19);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_NotOk() {
        user.setLogin("Mike1984");
        user.setPassword("483asd~@djs");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_NotOk() {
        user.setLogin("Mike1984");
        user.setPassword("483asd~@djs");
        user.setAge(10);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidPassword_NotOk() {
        user.setLogin("Mike1984");
        user.setPassword("483as");
        user.setAge(19);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidLogin_NotOk() {
        user.setLogin("~Bilbo42~");
        user.setPassword("483asd~@djs");
        user.setAge(19);
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
