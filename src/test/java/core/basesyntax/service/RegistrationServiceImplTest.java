package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        User user1 = new User();
        user1.setLogin("admin");
        user1.setPassword("adminPassword");
        user1.setAge(20);
        user1.setId(0L);
        Storage.people.add(user1);
        User user2 = new User();
        user2.setLogin("someLogin");
        user2.setPassword("somePassword");
        user2.setAge(18);
        user2.setId(1L);
        Storage.people.add(user2);
        User user3 = new User();
        user3.setLogin("randomLogin");
        user3.setPassword("randomPassword");
        user3.setAge(43);
        user3.setId(2L);
        Storage.people.add(user3);
    }

    @Test
    void register_NotContainsUserLoginInStorage_Ok() {
        User expected = new User();
        expected.setLogin("myLogin");
        expected.setPassword("myPassword");
        expected.setAge(23);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_ContainsUserLoginInStorage_NotOk() {
        User user = new User();
        user.setLogin("admin");
        user.setPassword("adminPassword");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserAge_Ok() {
        User expected = new User();
        expected.setLogin("testForAgeOk");
        expected.setPassword("testForAgeOk");
        expected.setAge(23);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_UserAge_NotOk() {
        User user = new User();
        user.setLogin("testForAgeNotOk");
        user.setPassword("testForAgeNotOk");
        user.setAge(10);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserPasswordLength_Ok() {
        User expected = new User();
        expected.setLogin("testForPasswordLengthOk");
        expected.setPassword("testForPasswordLengthOk");
        expected.setAge(23);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_UserPasswordLength_NotOk() {
        User user = new User();
        user.setLogin("testForPasswordLengthNotOk");
        user.setPassword("NotOk");
        user.setAge(56);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullLogin_NotOk() {
        User user = new User();
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullPassword_NotOk() {
        User user = new User();
        user.setLogin("login");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void resister_NullAge_NotOk() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullUserArgument_NotOk() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }
}
