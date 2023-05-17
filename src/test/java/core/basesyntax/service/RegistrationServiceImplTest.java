package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User(RegistrationServiceTestConstants.OVER_MIN_LOGIN,
                RegistrationServiceTestConstants.OVER_MIN_PASSWORD,
                RegistrationServiceTestConstants.OVER_MIN_AGE);
    }

    @Test
    void userIsAlreadyRegistered_NotOk() {
        storageDao.add(user);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullUser_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void nullLogin_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            user.setLogin(null);
            registrationService.register(user);
        });
    }

    @Test
    void shortLogin_NotOk() {
        user.setLogin(RegistrationServiceTestConstants.SHORT_LOGIN);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void emptyLogin_NotOk() {
        user.setLogin(RegistrationServiceTestConstants.EMPTY_LOGIN);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginWithSpace_NotOk() {
        user.setLogin(RegistrationServiceTestConstants.LOGIN_WITH_SPACE);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void minLogin_Ok() {
        user.setLogin(RegistrationServiceTestConstants.MIN_LOGIN);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void overMinLogin_Ok() {
        user.setLogin(RegistrationServiceTestConstants.OVER_MIN_LOGIN);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void nullPassword_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            user.setPassword(null);
            registrationService.register(user);
        });
    }

    @Test
    void shortPassword_NotOk() {
        user.setPassword(RegistrationServiceTestConstants.SHORT_PASSWORD);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void emptyPassword_NotOk() {
        user.setPassword(RegistrationServiceTestConstants.EMPTY_PASSWORD);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordWithSpace_NotOk() {
        user.setPassword(RegistrationServiceTestConstants.PASSWORD_WITH_SPACE);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void minPassword_Ok() {
        user.setPassword(RegistrationServiceTestConstants.MIN_PASSWORD);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void overMinPassword_Ok() {
        user.setPassword(RegistrationServiceTestConstants.OVER_MIN_PASSWORD);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void nullAge_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            user.setAge(null);
            registrationService.register(user);
        });
    }

    @Test
    void notEnoughAge_NotOk() {
        user.setAge(RegistrationServiceTestConstants.NOT_ENOUGH_AGE);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void negativeAge_NotOk() {
        user.setAge(RegistrationServiceTestConstants.NEGATIVE_AGE);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void minAge_Ok() {
        user.setAge(RegistrationServiceTestConstants.MIN_AGE);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void overMinAge_Ok() {
        user.setAge(RegistrationServiceTestConstants.OVER_MIN_AGE);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    private static class RegistrationServiceTestConstants {
        private static final String OVER_MIN_LOGIN = "roberto";
        private static final String SHORT_LOGIN = "bob";
        private static final String MIN_LOGIN = "morris";
        private static final String LOGIN_WITH_SPACE = "gary  ";
        private static final String EMPTY_LOGIN = "";
        private static final int OVER_MIN_AGE = 22;
        private static final int MIN_AGE = 18;
        private static final int NOT_ENOUGH_AGE = 12;
        private static final int NEGATIVE_AGE = -10;
        private static final String OVER_MIN_PASSWORD = "robinson134";
        private static final String SHORT_PASSWORD = "984";
        private static final String MIN_PASSWORD = "123456";
        private static final String PASSWORD_WITH_SPACE = "robi   ";
        private static final String EMPTY_PASSWORD = "";
    }
}
