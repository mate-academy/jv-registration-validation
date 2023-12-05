package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.exceptions.RegistrationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String INVALID_PASSWORD = "1234";
    private static final String INVALID_LOGIN = "user";
    private static final int INVALID_AGE = 17;
    private static int loginCount = 0;
    private static RegistrationService registrationService;
    private static User validUser;
    private static StorageDaoImpl storageDao;
    private String mockLogin;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        mockLogin = "login" + loginCount++;
        User user = new User();
        user.setAge(19);
        user.setLogin(mockLogin);
        user.setPassword("12341234");
        validUser = user;
    }

    @Test
    void register_addValidUser_Ok() {
        User newUser = validUser;
        User expected = registrationService.register(newUser);
        assertEquals(expected, newUser);
    }

    @Test
    void register_userWasAdded_Ok() {
        User newUser = validUser;
        registrationService.register(newUser);
        User actual = storageDao.get(newUser.getLogin());
        assertEquals(newUser, actual);
    }

    @Test
    void register_addUserWithSameEmail_NotOk() {
        registrationService.register(validUser);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_userPasswordLessThanMinValidLength_NotOk() {
        User newUser = validUser;
        newUser.setPassword(INVALID_PASSWORD);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_userLoginLessThanMinValidLength_NotOk() {
        User newUser = validUser;
        newUser.setLogin(INVALID_LOGIN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_userAgeLessThanMinValidAge_NotOk() {
        User newUser = validUser;
        newUser.setAge(INVALID_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_userAgeIsLessThanMinAgeValue_NotOk() {
        User newUser = validUser;
        newUser.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_userPasswordIsNull_NotOk() {
        User newUser = validUser;
        newUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_userLoginIsNull_NotOk() {
        User newUser = validUser;
        newUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }
}
