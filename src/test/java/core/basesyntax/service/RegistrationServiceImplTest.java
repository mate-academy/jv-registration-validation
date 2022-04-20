package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User();
    }

    @Test
    void registerUserIsOK() {
        user.setAge(19);
        user.setPassword("123456");
        user.setLogin("pasis");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void registerUserGetAgeIsNotOK() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserGetAgeIsNull() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserGetPasswordIsNotOK() {
        user.setAge(18);
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserGetPasswordIsNull() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserGetloginIsNull() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerTheSameLogin_NotOk() {
        user.setLogin("marina");
        User user2 = new User();
        user2.setAge(19);
        user2.setLogin("marina");
        user2.setPassword("passsword");
        registrationService.register(user2);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
