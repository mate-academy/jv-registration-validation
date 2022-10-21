package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static final Integer VALID_AGE = 18;
    private static final Integer INVALID_AGE = 17;
    private static final String VALID_LOGIN = "B";
    private static final String INVALID_LOGIN = "";
    private static final String VALID_PASSWORD = "password";
    private static final String INVALID_PASSWORD = "passw";
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();
    private final User validUser = new User();

    @BeforeEach
    void setUp() {
        validUser.setAge(VALID_AGE);
        validUser.setLogin(VALID_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_loginIsNull_notOk() {
        User userWithNullLogin = validUser;
        userWithNullLogin.setLogin(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userWithNullLogin));
    }

    @Test
    void register_passwordIsNull_notOk() {
        User userWithNullPassword = validUser;
        userWithNullPassword.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userWithNullPassword));
    }

    @Test
    void register_ageIsNull_notOk() {
        User userWithNullAge = validUser;
        userWithNullAge.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithNullAge));
    }

    @Test
    void register_validUser_ok() {
        String expected = registrationService.register(validUser).getLogin();
        String actual = storageDao.get(validUser.getLogin()).getLogin();
        assertEquals(expected, actual);
    }

    @Test
    void register_userWithWrongPassword_notOk() {
        User userWithWrongPassword = validUser;
        userWithWrongPassword.setPassword(INVALID_PASSWORD);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userWithWrongPassword));
    }

    @Test
    void register_userWithWrongAge_notOk() {
        User userWithWrongAge = validUser;
        userWithWrongAge.setAge(INVALID_AGE);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userWithWrongAge));
    }

    @Test
    void register_userWithExistingLogin_notOk() {
        Storage.people.add(validUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_userWithInvalidLogin_notOk() {
        User userWithWrongLogin = validUser;
        userWithWrongLogin.setLogin(INVALID_LOGIN);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userWithWrongLogin));
    }
}
