package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static final String INVALID_LOGIN = "user";
    private static final String VALID_LOGIN = "user1986";
    private static final String INVALID_PASSWORD = "pass";
    private static final String VALID_PASSWORD = "password";
    private static final String EXIST_LOGIN = "bob1989";
    private static final int INVALID_AGE = 15;
    private static final int VALID_AGE = 25;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        User bob = new User();
        bob.setLogin(EXIST_LOGIN);
        storageDao.add(bob);
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_nullUser_notOk() {
        try {
            registrationService.register(null);
        } catch (RegistrationException e) {
            return;
        }
        fail("If user is null RegistrationException should be.");
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
        user.setLogin(EXIST_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserIsValid() {
        user.setLogin(VALID_LOGIN);
        user.setAge(VALID_AGE);
        user.setPassword(VALID_PASSWORD);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Registering user with valid data should not throw an exception");

    }
}
