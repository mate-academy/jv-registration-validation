package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_DEFAULT_LOGIN = "user@gmail.com";
    private static final String VALID_DEFAULT_PASSWORD = "user1234";
    private static final int VALID_AGE = 25;
    private static final String INVALID_LOGIN_LENGTH = "user";
    private static final String INVALID_LOGIN_START_WITH_NUMBERS = "1234@gmail.com";
    private static final String INVALID_EMPTY_LOGIN = "";
    private static final String INVALID_PASSWORD_LENGTH = "pass";
    private static final String INVALID_EMPTY_PASSWORD = "";
    private static final int INVALID_USER_AGE = 16;

    private RegistrationService registrationService;
    private User user;
    private StorageDaoImpl storageDao;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
        user.setAge(VALID_AGE);
        user.setLogin(VALID_DEFAULT_LOGIN);
        user.setPassword(VALID_DEFAULT_PASSWORD);
    }

    @Test
    void register_nullValueForUser_notOk() {
        Assertions.assertThrows(RegistrationException.class, 
                () -> registrationService.register(null));
    }

    @Test
    void register_validUser_ok() {
        Assertions.assertNotNull(storageDao.add(user));
    }

    @Test
    void register_userDoesAlreadyExist_notOk() {
        storageDao.add(user);
        Assertions.assertThrows(RegistrationException.class,
                 () -> registrationService.register(user));
    }

    @Test
    void register_invalidLoginLength_notOk() {
        user.setLogin(INVALID_LOGIN_LENGTH);
        Assertions.assertThrows(RegistrationException.class, 
                () -> registrationService.register(user));
    }

    @Test
    void register_userLoginIsEmpty_notOk() {
        user.setLogin(INVALID_EMPTY_LOGIN);
        Assertions.assertThrows(RegistrationException.class, 
                () -> registrationService.register(user));
    }

    @Test
    void register_userLoginStartWithNumber_notOk() {
        user.setLogin(INVALID_LOGIN_START_WITH_NUMBERS);
        Assertions.assertThrows(RegistrationException.class, 
                () -> registrationService.register(user));
    }

    @Test
    void register_userLoginIsNull_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(RegistrationException.class, 
                () -> registrationService.register(user));
    }

    @Test
    void register_invalidPasswordLength_notOk() {
        user.setPassword(INVALID_PASSWORD_LENGTH);
        Assertions.assertThrows(RegistrationException.class, 
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        user.setPassword(INVALID_EMPTY_PASSWORD);
        if (user.getLogin().isEmpty()) {
            Assertions.assertThrows(RegistrationException.class,
                    () -> registrationService.register(user));
        }

    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_notOk() {
        user.setAge(INVALID_USER_AGE);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }
}
