package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;
    private String login = "slavik";
    private int age = 18;
    private final String password = "Sukhov28";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(age++);
        user.setLogin(login);
        user.setPassword(password);
    }
    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_UserExistsAlready_NotOk() {
        User user1 = registrationService.register(user);
        Assertions.assertThrows(RegisterException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_UserAge_Ok() {
        user.setAge(19);
        User user1 = registrationService.register(user);
        Assertions.assertTrue(user1.getAge() >= MIN_AGE);
    }

    @Test
    void register_UserAgeLessThan18_NotOk() {
        user.setAge(12);
        Assertions.assertThrows(RegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserLoginNull_NotOk() {
        user.setLogin(null);
        Assertions.assertThrows(RegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserLogin_NotNull() {
        User user1 = registrationService.register(user);
        Assertions.assertNotNull(user1.getLogin());
    }

    @Test
    void register_UserPasswordLength_Ok() {
        User user1 = registrationService.register(user);
        Assertions.assertTrue(user1.getPassword().length() >= MIN_PASSWORD_LENGTH);
    }

    @Test
    void register_UserPasswordLength_ThrowsException_Ok() {
        user.setPassword("123");
        Assertions.assertThrows(RegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserPasswordNull_ThrowsException_Ok() {
        user.setPassword(null);
        Assertions.assertThrows(RegisterException.class, () -> {
            registrationService.register(user);
        });
    }
}
