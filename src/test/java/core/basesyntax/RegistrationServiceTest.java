package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
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
        user.setLogin("userLogin");
        user.setPassword("666666");
    }

    @AfterEach
    void storageClose() {
        Storage.people.clear();
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void passwordLength_5characters_notOk() {
        user.setPassword("5symb");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordLength_10characters_ok() {
        user.setPassword("10_symbols");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void userAgeIs17_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userAgeIs18_ok() {
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void userAgeIsNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userLoginAlreadyExist_notOk() {
        user.setLogin("Login");
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userLoginIsUnique_ok() {
        user.setLogin("Unique_Login");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void userLoginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
