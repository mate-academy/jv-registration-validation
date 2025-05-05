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
        registrationService.register(userToRegistrate);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userToRegistrate));
    }

    @Test
    void register_userNormalCase_Ok() {
        User secondUserToRegistrate = new User();
        secondUserToRegistrate.setAge(25);
        secondUserToRegistrate.setId((long) 456);
        secondUserToRegistrate.setLogin("test2");
        secondUserToRegistrate.setPassword("password");
        User expected = secondUserToRegistrate;
        assertEquals(expected, registrationService.register(secondUserToRegistrate));
    }

    @Test
    void register_userPasswordToShort_notOk() {
        userToRegistrate.setAge(20);
        userToRegistrate.setId((long) 123);
        userToRegistrate.setLogin("test");
        userToRegistrate.setPassword("short");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userToRegistrate));
    }
}
