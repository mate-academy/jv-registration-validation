package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user.setLogin("userName");
        user.setPassword("adaswwgwef");
        user.setAge(33);
    }

    @Test
    void register_validUser_Ok() {
        User actual = registrationService.register(user);
        User expected = user;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_nullUser_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_validAge_Ok() {
        user.setAge(20);
        User actual = registrationService.register(user);
        User expected = user;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_NotOk() {
        user.setAge(-25);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_NotOk() {
        user.setAge(13);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validLogin_Ok() {
        user.setLogin("Name");
        User actual = registrationService.register(user);
        User expected = user;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin("");
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validPassword_Ok() {
        user.setPassword("asddfdvsv");
        User actual = registrationService.register(user);
        User expected = user;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_NotOk() {
        user.setPassword("12345");
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}