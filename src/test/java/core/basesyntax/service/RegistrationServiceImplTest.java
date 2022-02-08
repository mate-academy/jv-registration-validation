package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    void register_isAgeCorrect_NotOk() {
        user.setAge(-5);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setAge(102);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsCorrect_Ok() {
        User user = new User();
        user.setAge(22);
        user.setPassword("svknsdkv");
        user.setLogin("First user");
        assertEquals(user, registrationService.register(user));
        user = new User();
        user.setAge(88);
        user.setPassword("svknsdkv");
        user.setLogin("Second user");
        assertEquals(user, registrationService.register(user));
        user = new User();
        user.setAge(18);
        user.setPassword("svknsdkv");
        user.setLogin("Third user");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_passwordIsIncorrect_NotOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setPassword("123");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsCorrect_Ok() {
        user.setAge(22);
        user.setPassword("1234567");
        user.setLogin("First user");
        assertEquals(user, registrationService.register(user));
        user = new User();
        user.setAge(22);
        user.setLogin("Second user");
        user.setPassword("svknsdkv");
        assertEquals(user, registrationService.register(user));
        user = new User();
        user.setAge(22);
        user.setLogin("Third user");
        user.setPassword("123vsn");
        assertEquals(user, registrationService.register(user));
        user = new User();
        user.setAge(22);
        user.setLogin("Fourth user");
        user.setPassword("123$%^&5dsvs&**67");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_addTwoSameUsers_NotOk() {
        user.setAge(30);
        user.setPassword("sdmvonss");
        user.setLogin("First user");
        registrationService.register(user);
        assertNull(registrationService.register(user));
    }

    @Test
    void register_addDifferentUsers_Ok() {
        user.setAge(30);
        user.setPassword("sdmvonss");
        user.setLogin("First user");
        assertEquals(user, registrationService.register(user));
        User secondUser = new User();
        secondUser.setAge(35);
        secondUser.setPassword("12h1mfv&");
        secondUser.setLogin("Second user");
        assertEquals(secondUser, registrationService.register(secondUser));
        User thirdUser = new User();
        thirdUser.setAge(18);
        thirdUser.setPassword("$%^&*&G1");
        thirdUser.setLogin("Third user");
        assertEquals(thirdUser, registrationService.register(thirdUser));
        User fourthUser = new User();
        fourthUser.setAge(100);
        fourthUser.setPassword("vn3e89r38*");
        fourthUser.setLogin("Fourth user");
        assertEquals(fourthUser, registrationService.register(fourthUser));
    }

    @Test
    void register_userIsNull_Exception() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsIncorrect_Ok() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
