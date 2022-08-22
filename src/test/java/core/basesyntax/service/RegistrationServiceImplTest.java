package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.db.Storage;
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
    void registration_userValid_ok() {
        assertEquals(0, Storage.people.size());
        user.setLogin("example1");
        user.setPassword("123456");
        user.setAge(18);
        registrationService.register(user);
        int actual = Storage.people.size();
        assertEquals(1,actual);
    }

    @Test
    void registration_userNullLogin_notOk() {
        user.setLogin(null);
        user.setPassword("123456");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_userWithEmptyLogin_notOk() {
        user.setLogin("");
        user.setPassword("123456");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_userInvalidPassword_notOk() {
        user.setLogin("example2");
        user.setPassword("12345");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_userWithNullPassword_notOk() {
        user.setLogin("example3");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_userWithEmptyPassword_notOk() {
        user.setLogin("example4");
        user.setPassword("");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_userInvalidAge_notOk() {
        user.setLogin("example5");
        user.setPassword("123456");
        user.setAge(16);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_userWithNullAge_notOk() {
        user.setLogin("example6");
        user.setPassword("123456");
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_userWithSameLogins_notOk() {
        user.setLogin("example7");
        user.setPassword("123456");
        user.setAge(20);
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_userNull_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

}
