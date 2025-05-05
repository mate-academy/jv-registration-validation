package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User user = createValidUser();
        User registeredUser = registrationService.register(user);
        Assertions.assertNotNull(registeredUser);
        Assertions.assertEquals(user.getLogin(), registeredUser.getLogin());
        Assertions.assertEquals(1, Storage.people.size());
    }

    @Test
    void register_nullUser_notOk() {
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = createValidUser();
        user.setLogin("short");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = createValidUser();
        user.setPassword("12345");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = createValidUser();
        user.setLogin(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = createValidUser();
        user.setPassword(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = createValidUser();
        user.setAge(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_underageUser_notOk() {
        User user = createValidUser();
        user.setAge(17);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_existingLogin_notOk() {
        User user = createValidUser();
        registrationService.register(user);
        User duplicateUser = createValidUser();
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(duplicateUser));
    }

    @Test
    void register_edgeCasePasswordLength_notOk() {
        User user = createValidUser();
        user.setPassword("12345");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_edgeCasePasswordLength_ok() {
        User user = createValidUser();
        user.setPassword("123456");
        Assertions.assertDoesNotThrow(() -> registrationService.register(user));
    }

    private User createValidUser() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(20);
        return user;
    }
}
