package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static User userToRegistrate;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeAll
    static void beforeAll() {
        userToRegistrate = new User();
    }

    @Test
    void register_nullAge_notOk() {
        userToRegistrate.setAge(null);
        userToRegistrate.setId((long) 123);
        userToRegistrate.setLogin("test");
        userToRegistrate.setPassword("password");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userToRegistrate));
    }

    @Test
    void register_nullId_notOk() {
        userToRegistrate.setAge(20);
        userToRegistrate.setId(null);
        userToRegistrate.setLogin("test");
        userToRegistrate.setPassword("password");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userToRegistrate));
    }

    @Test
    void register_nullLogin_notOk() {
        userToRegistrate.setAge(20);
        userToRegistrate.setId((long) 123);
        userToRegistrate.setLogin(null);
        userToRegistrate.setPassword("password");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userToRegistrate));
    }

    @Test
    void register_nullPassword_notOk() {
        userToRegistrate.setAge(20);
        userToRegistrate.setId((long) 123);
        userToRegistrate.setLogin("test");
        userToRegistrate.setPassword(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userToRegistrate));
    }

    @Test
    void register_userAgeLeastMinAge_notOk() {
        userToRegistrate.setAge(17);
        userToRegistrate.setId((long) 123);
        userToRegistrate.setLogin("test");
        userToRegistrate.setPassword("password");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userToRegistrate));
    }

    @Test
    void register_userLoginAlreadyExists_notOk() {
        userToRegistrate.setAge(20);
        userToRegistrate.setId((long) 123);
        userToRegistrate.setLogin("test");
        userToRegistrate.setPassword("password");
        User actual = userToRegistrate;
        assertEquals(actual, registrationService.register(userToRegistrate));
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userToRegistrate));
    }
}
