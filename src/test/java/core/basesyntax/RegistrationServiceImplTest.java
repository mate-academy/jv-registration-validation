package core.basesyntax;

import core.basesyntax.exception.RegistrationDataException;
import core.basesyntax.model.User;
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
    }

    @Test
    void register_nullUser_notOk() {
        Assertions.assertThrows(RegistrationDataException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullUserLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("validPassword");
        user.setAge(20);
        Assertions.assertThrows(RegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUserPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword(null);
        user.setAge(20);
        Assertions.assertThrows(RegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUserAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(null);
        Assertions.assertThrows(RegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(17);
        Assertions.assertThrows(RegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("validPassword");
        user.setAge(20);
        Assertions.assertThrows(RegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_edgeCaseLoginLength_ok() {
        User user = new User();
        user.setLogin("length");
        user.setPassword("validPassword");
        user.setAge(20);
        Assertions.assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("short");
        user.setAge(20);
        Assertions.assertThrows(RegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_edgeCasePasswordLength_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("length");
        user.setAge(20);
        Assertions.assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_existingLogin_notOk() {
        User user = new User();
        user.setLogin("existingLogin");
        user.setPassword("validPassword");
        user.setAge(20);
        registrationService.register(user);

        User anotherUser = new User();
        anotherUser.setLogin("existingLogin");
        anotherUser.setPassword("anotherPassword");
        anotherUser.setAge(22);
        Assertions.assertThrows(RegistrationDataException.class, () -> {
            registrationService.register(anotherUser);
        });
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("newLogin");
        user.setPassword("newPassword");
        user.setAge(25);
        Assertions.assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_validUser_userAddedToStorage() {
        User user = new User();
        user.setLogin("uniqueLogin");
        user.setPassword("uniquePassword");
        user.setAge(25);
        User registeredUser = registrationService.register(user);

        Assertions.assertEquals(user.getLogin(), registeredUser.getLogin());
        Assertions.assertEquals(user.getPassword(), registeredUser.getPassword());
        Assertions.assertEquals(user.getAge(), registeredUser.getAge());
    }
}
