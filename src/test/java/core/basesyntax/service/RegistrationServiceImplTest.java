package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    public static void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void sample() {
        user = new User();
        user.setLogin("login");
        user.setAge(54);
        user.setPassword("password");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    public void register_nullUser_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    public void register_validUser_ok() {
        Assertions.assertEquals(user, registrationService.register(user));
    }

    @Test
    public void register_existingUser() {
        registrationService.register(user);
        User newUser = new User();
        newUser.setLogin(user.getLogin());
        newUser.setPassword("123456");
        newUser.setAge(21);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService
                .register(newUser));
    }

    @Test
    public void register_nullLogin_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_emptyLogin_notOk() {
        user.setLogin("");
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullPassword_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_emptyPassword_notOk() {
        user.setPassword("");
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordIsValidLength_Ok() {
        user.setPassword("123456");
        Assertions.assertEquals(user, registrationService.register(user));
    }

    @Test
    public void register_shortPassword_notOk() {
        user.setPassword("12345");
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ageIsLessThanNecessery_notOk() {
        user.setAge(18);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_validAge_notOk() {
        user.setAge(17);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
