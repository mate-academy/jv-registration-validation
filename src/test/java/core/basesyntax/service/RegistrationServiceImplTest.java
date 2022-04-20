package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void set() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(18);
        user.setLogin("yarikClownBoy");
        user.setPassword("1337228");
    }

    @AfterEach
    void storageClose() {
        Storage.people.clear();
    }

    @Test
    void passwordLength_notOk() {
        user.setPassword("smal");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordLength_ok() {
        user.setPassword("thisLogin");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void userAgeIs_notOk() {
        user.setAge(10);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userAgeIs_ok() {
        user.setAge(30);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void userAgeIsNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userLoginIs_notOk() {
        User secondBoy = new User();
        secondBoy.setLogin("yarikClownBoy");
        secondBoy.setPassword("7654321");
        secondBoy.setAge(19);
        registrationService.register(secondBoy);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userLoginIs_ok() {
        user.setLogin("yarikNotClownBoy");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void userLoginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
