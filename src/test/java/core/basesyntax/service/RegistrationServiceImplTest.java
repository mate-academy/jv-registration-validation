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
    private static RegistrationServiceImpl registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_userIsOK() {
        user.setAge(19);
        user.setPassword("123456");
        user.setLogin("pasis");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_userGetAgeIsNotOK() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userGetAgeIsNull() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userGetPasswordIsNotOK() {
        user.setAge(18);
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_usergetpasswordIsNull() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_usergetloginIsNull() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_theSameLogin_notOk() {
        user.setLogin("marina");
        User user2 = new User();
        user2.setAge(19);
        user2.setLogin("marina");
        user2.setPassword("passsword");
        registrationService.register(user2);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
