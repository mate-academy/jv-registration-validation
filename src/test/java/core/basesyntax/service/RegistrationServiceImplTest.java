package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String INVALID_LOGIN = "user";
    private static final String VALID_LOGIN = "user1986";
    private static final String INVALID_PASSWORD = "pass";
    private static final String VALID_PASSWORD = "password";
    private static final String EXIST_LOGIN = "bob1989";
    private static final int INVALID_AGE = 15;
    private static final int VALID_AGE = 25;
    private StorageDao storageDao = new StorageDaoImpl();
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User();
        user.setLogin(EXIST_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @Test
    void register_UserIsValid() {
        user.setLogin(VALID_LOGIN);
        user.setAge(VALID_AGE);
        user.setPassword(VALID_PASSWORD);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Registering user with valid data should not throw an exception");

    }

    @Test
    void register_OutputUserIsEqualtoInputUser_IsOk() {
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual, "Registered user should be equal to the original user");

    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));

    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));

    }

    @Test
    void register_ageLess18_notOk() {
        user.setAge(INVALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));

    }

    @Test
    void register_PasswordLengthLess6Chars_notOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginLengthLess6Chars_notOk() {
        user.setLogin(INVALID_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));

    }

    @Test
    void register_ExistUser_notOk() {
        User bob = new User();
        bob.setLogin(EXIST_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(bob));
    }
}
