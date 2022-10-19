package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User validUser = new User();
    private static final Integer validAge = 18;
    private static final Integer invalidAge = 17;
    private static final String validLogin = "B";
    private static final String invalidLogin = "";
    private static final String validPassword = "password";
    private static final String invalidPassword = "passw";
    private static final RegistrationService registrationService = new RegistrationServiceImpl();
    private static final StorageDao storageDao = new StorageDaoImpl();

    @BeforeAll
    static void beforeAll() {
        validUser.setAge(validAge);
        validUser.setLogin(validLogin);
        validUser.setPassword(validPassword);
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_loginIsNull_notOk() {
        User userWithNullLogin = new User();
        userWithNullLogin.setAge(validAge);
        userWithNullLogin.setLogin(null);
        userWithNullLogin.setPassword(validPassword);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithNullLogin);
        });
    }

    @Test
    void register_passwordIsNull_notOk() {
        User userWithNullPassword = new User();
        userWithNullPassword.setAge(validAge);
        userWithNullPassword.setLogin(validLogin);
        userWithNullPassword.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithNullPassword);
        });
    }

    @Test
    void register_ageIsNull_notOk() {
        User userWithNullAge = new User();
        userWithNullAge.setAge(null);
        userWithNullAge.setLogin(validLogin);
        userWithNullAge.setPassword(validPassword);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithNullAge);
        });
    }

    @Test
    void register_validUser_Ok() {
        String expected = registrationService.register(validUser).getLogin();
        String actual = storageDao.get(validUser.getLogin()).getLogin();
        assertEquals(expected, actual);
    }

    @Test
    void register_userWithWrongPassword_notOk() {
        User userWithWrongPassword = new User();
        userWithWrongPassword.setAge(validAge);
        userWithWrongPassword.setLogin(validLogin);
        userWithWrongPassword.setPassword(invalidPassword);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithWrongPassword);
        });
    }

    @Test
    void register_userWithWrongAge_notOk() {
        User userWithWrongAge = new User();
        userWithWrongAge.setAge(invalidAge);
        userWithWrongAge.setLogin(validLogin);
        userWithWrongAge.setPassword(validPassword);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithWrongAge);
        });
    }

    @Test
    void register_userWithExistingLogin_notOk() {
        registrationService.register(validUser);
        User otherUser = new User();
        otherUser.setAge(validAge);
        otherUser.setLogin(validLogin);
        otherUser.setPassword(validPassword);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(otherUser);
        });
    }

    @Test
    void register_userWithInvalidLogin_notOk() {
        User userWithWrongLogin = new User();
        userWithWrongLogin.setAge(validAge);
        userWithWrongLogin.setLogin(invalidLogin);
        userWithWrongLogin.setPassword(validPassword);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithWrongLogin);
        });
    }
}
