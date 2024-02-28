package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "valid login";
    private static final String VALID_PASSWORD = "valid pass";
    private static final String INVALID_STRING = "short";
    private static final Integer VALID_AGE = 20;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        User validUser = new User();
        validUser.setLogin(VALID_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
        validUser.setAge(VALID_AGE);
        user = validUser;
    }

    @Test
    void register_ValidUser_Ok() {
        User actualUser = user;
        registrationService.register(user);
        StorageDaoImpl storageDao = new StorageDaoImpl();
        User expectedUser = storageDao.get(user.getLogin());
        assertEquals(expectedUser, actualUser, "User must be in storage but is not");
    }

    @Test
    void register_loginIsTaken_notOk() {
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin(INVALID_STRING);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword(INVALID_STRING);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user),
                "InvalidUserException must be thrown");
    }

    @Test
    void register_negativeAge_notOk() {
        int negativeAge = -20;
        user.setAge(negativeAge);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user),
                "InvalidUserException must be thrown");
    }

    @Test
    void register_ageOverMax_notOk() {
        int ageOverMax = 121;
        user.setAge(ageOverMax);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user),
                "InvalidUserException must be thrown");
    }

    @Test
    void register_ageUnderMin_notOk() {
        int ageUnderMin = 17;
        user.setAge(ageUnderMin);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user),
                "InvalidUserException must be thrown");
    }
}
