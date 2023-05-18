package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static final String CORRECT_LOGIN = "Volodymyr";
    private static final String NOT_CORRECT_LOGIN = "vova";
    private static final String CORRECT_PASSWORD = "123456";
    private static final String NOT_CORRECT_PASSWORD = "123";
    private static final String EMPTY_PASSWORD = "";
    private static final int CORRECT_AGE = 19;
    private static final int NOT_CORRECT_AGE = 17;
    private static final int NEGATIVE_AGE = -1;

    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();

    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User(CORRECT_LOGIN, CORRECT_PASSWORD, CORRECT_AGE);
    }

    @Test
    void register_existingUser_notOk() {
        Storage.people.add(user);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userNull_notOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_userNullLogin_notOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            user.setLogin(null);
            registrationService.register(user);
        });
    }

    @Test
    void register_userNotCorrectLogin_notOk() {
        user.setLogin(NOT_CORRECT_LOGIN);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userNullPassword_notOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            user.setPassword(null);
            registrationService.register(user);
        });
    }

    @Test
    void register_userCorrectPassword_ok() {
        user.setPassword(CORRECT_PASSWORD);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userNotCorrectPassword_notOk() {
        user.setPassword(NOT_CORRECT_PASSWORD);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userEmptyPassword_notOk() {
        user.setPassword(EMPTY_PASSWORD);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userNotEnoughAge_notOk() {
        user.setAge(NOT_CORRECT_AGE);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userNegativeAge_notOk() {
        user.setAge(NEGATIVE_AGE);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
