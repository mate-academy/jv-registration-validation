package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASS_LENGTH = 6;
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

    @Test
    void register_UserExistsAlready_Ok() {
        User user1 = registrationService.register(user);
        Assertions.assertThrows(CustomException.class, () -> {
            registrationService.register(user1);
        });
        Storage.people.remove(user1);
    }

    @Test
    void register_UserAge_Ok() {
        user.setAge(19);
        User user1 = registrationService.register(user);
        Assertions.assertTrue(user1.getAge() > MIN_AGE);
        Storage.people.remove(user1);
    }

    @Test
    void register_UserAgeNotOk_ThrowsException_Ok() {
        user.setAge(12);
        Assertions.assertThrows(CustomException.class, () -> {
            registrationService.register(user);
        });
        Storage.people.remove(user);
    }

    @Test
    void register_UserLoginNull_ThrowsException_Ok() {
        user.setLogin(null);
        Assertions.assertThrows(CustomException.class, () -> {
            registrationService.register(user);
        });
        Storage.people.remove(user);
    }

    @Test
    void register_UserLogin_NotNull() {
        User user1 = registrationService.register(user);
        Assertions.assertNotNull(user1.getLogin());
        Storage.people.remove(user1);
    }

    @Test
    void register_UserPasswordLength_Ok() {
        User user1 = registrationService.register(user);
        Assertions.assertTrue(user1.getPassword().length() > MIN_PASS_LENGTH);
        Storage.people.remove(user1);
    }

    @Test
    void register_UserPasswordLength_ThrowsException_Ok() {
        user.setPassword("123");
        Assertions.assertThrows(CustomException.class, () -> {
            registrationService.register(user);
        });
        Storage.people.remove(user);
    }

    @Test
    void register_UserPasswordNull_ThrowsException_Ok() {
        user.setPassword(null);
        Assertions.assertThrows(CustomException.class, () -> {
            registrationService.register(user);
        });
        Storage.people.remove(user);
    }
}
