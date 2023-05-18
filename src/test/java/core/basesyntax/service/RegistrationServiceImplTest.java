package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User(RegistrationServiceTestConstants.OVER_MIN_LOGIN,
                RegistrationServiceTestConstants.OVER_MIN_PASSWORD,
                RegistrationServiceTestConstants.OVER_MIN_AGE);
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
    void register_userShortLogin_notOk() {
        user.setLogin(RegistrationServiceTestConstants.SHORT_LOGIN);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userEmptyLogin_notOk() {
        user.setLogin(RegistrationServiceTestConstants.EMPTY_LOGIN);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithSpaceLogin_notOk() {
        user.setLogin(RegistrationServiceTestConstants.LOGIN_WITH_SPACE);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userMinLogin_ok() {
        user.setLogin(RegistrationServiceTestConstants.MIN_LOGIN);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userOverMinLogin_ok() {
        user.setLogin(RegistrationServiceTestConstants.OVER_MIN_LOGIN);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userNullPassword_notOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            user.setPassword(null);
            registrationService.register(user);
        });
    }

    @Test
    void register_userShortPassword_notOk() {
        user.setPassword(RegistrationServiceTestConstants.SHORT_PASSWORD);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userEmptyPassword_notOk() {
        user.setPassword(RegistrationServiceTestConstants.EMPTY_PASSWORD);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithSpacePassword_notOk() {
        user.setPassword(RegistrationServiceTestConstants.PASSWORD_WITH_SPACE);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userMinPassword_ok() {
        user.setPassword(RegistrationServiceTestConstants.MIN_PASSWORD);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userOverMinPassword_ok() {
        user.setPassword(RegistrationServiceTestConstants.OVER_MIN_PASSWORD);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userNullAge_notOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            user.setAge(null);
            registrationService.register(user);
        });
    }

    @Test
    void register_userNotEnoughAge_notOk() {
        user.setAge(RegistrationServiceTestConstants.NOT_ENOUGH_AGE);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userNegativeAge_notOk() {
        user.setAge(RegistrationServiceTestConstants.NEGATIVE_AGE);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userMinAge_ok() {
        user.setAge(RegistrationServiceTestConstants.MIN_AGE);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userOverMinAge_ok() {
        user.setAge(RegistrationServiceTestConstants.OVER_MIN_AGE);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }
}
