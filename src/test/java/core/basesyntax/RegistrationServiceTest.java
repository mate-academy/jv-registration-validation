package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private User user;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("testPassword");
        user.setAge(22);
        User registeredUser = registrationService.register(user);
        Assertions.assertNotNull(registeredUser);
        Assertions.assertEquals(user.getLogin(), registeredUser.getLogin());
    }

    @Test
    void register_nullUser_notOk() {
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_loginNull_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("testPassword");
        user.setAge(22);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordNull_notOk() {
        User user = new User();
        user.setLogin("testLogin");
        user.setPassword(null);
        user.setAge(22);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordLess6_notOk() {
        User user = new User();
        user.setLogin("testLogin");
        user.setPassword("less");
        user.setAge(22);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginLess6_notOk() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("testPassword");
        user.setAge(22);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageUnder18_notOk() {
        User user = new User();
        user.setLogin("testLogin");
        user.setPassword("testPassword");
        user.setAge(17);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageNull_notOk() {
        User user = new User();
        user.setLogin("testLogin");
        user.setPassword("testPassword");
        user.setAge(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginExist_notOk() {
        User user = new User();
        user.setLogin("testLogin");
        user.setPassword("testPassword");
        user.setAge(18);
        registrationService.register(user);

        User duplicatedUser = new User();
        duplicatedUser.setLogin("testLogin");
        duplicatedUser.setPassword("Password");
        duplicatedUser.setAge(19);

        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(duplicatedUser));
    }
}
