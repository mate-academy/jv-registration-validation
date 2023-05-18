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
    void register_existingUser_NotOk() {
        Storage.people.add(user);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userNull_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_userNullLogin_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            user.setLogin(null);
            registrationService.register(user);
        });
    }

    @Test
    void register_userShortLogin_NotOk() {
        user.setLogin(RegistrationServiceTestConstants.SHORT_LOGIN);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userEmptyLogin_NotOk() {
        user.setLogin(RegistrationServiceTestConstants.EMPTY_LOGIN);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithSpaceLogin_NotOk() {
        user.setLogin(RegistrationServiceTestConstants.LOGIN_WITH_SPACE);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userMinLogin_Ok() {
        user.setLogin(RegistrationServiceTestConstants.MIN_LOGIN);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userOverMinLogin_Ok() {
        user.setLogin(RegistrationServiceTestConstants.OVER_MIN_LOGIN);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userNullPassword_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            user.setPassword(null);
            registrationService.register(user);
        });
    }

    @Test
    void register_userShortPassword_NotOk() {
        user.setPassword(RegistrationServiceTestConstants.SHORT_PASSWORD);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userEmptyPassword_NotOk() {
        user.setPassword(RegistrationServiceTestConstants.EMPTY_PASSWORD);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithSpacePassword_NotOk() {
        user.setPassword(RegistrationServiceTestConstants.PASSWORD_WITH_SPACE);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userMinPassword_Ok() {
        user.setPassword(RegistrationServiceTestConstants.MIN_PASSWORD);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userOverMinPassword_Ok() {
        user.setPassword(RegistrationServiceTestConstants.OVER_MIN_PASSWORD);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userNullAge_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            user.setAge(null);
            registrationService.register(user);
        });
    }

    @Test
    void register_userNotEnoughAge_NotOk() {
        user.setAge(RegistrationServiceTestConstants.NOT_ENOUGH_AGE);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userNegativeAge_NotOk() {
        user.setAge(RegistrationServiceTestConstants.NEGATIVE_AGE);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userMinAge_Ok() {
        user.setAge(RegistrationServiceTestConstants.MIN_AGE);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userOverMinAge_Ok() {
        user.setAge(RegistrationServiceTestConstants.OVER_MIN_AGE);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }
}
