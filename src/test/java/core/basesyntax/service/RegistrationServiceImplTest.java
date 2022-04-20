package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
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
    void register_userGetAgeIsOK() {
        user.setAge(19);
        user.setPassword("123456");
        user.setLogin("pasis");
        User actual = registrationService.register(user);
        assertEquals(user.getAge(), actual.getAge());
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
    void register_userGetPasswordIsOK() {
        user.setPassword("1234567");
        user.setAge(18);
        user.setLogin("pasis");
        User actual = registrationService.register(user);
        assertEquals(user.getPassword(), actual.getPassword());
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
    void register_usergetloginisNotOk() {
        user.setLogin("abc");
        User user2 = new User();
        user2.setLogin("abc");
        user2.setAge(19);
        user2.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_usergetloginIsOk() {
        user.setLogin("ghjkl");
        user.setAge(19);
        user.setPassword("123456");
        assertEquals(user, registrationService.register(user));
    }
}
