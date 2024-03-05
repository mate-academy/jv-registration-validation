package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int DEFAULT_AGE = 18;
    private static final long DEFAULT_ID = 123456789L;
    private static final String DEFAULT_LOGIN = "user_login";
    private static final String DEFAULT_PASSWORD = "user_password";

    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDaoImpl storageDao = new StorageDaoImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(DEFAULT_AGE);
        user.setId(DEFAULT_ID);
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        Storage.people.clear();
    }

    @Test
    void register_checkUser_ok() {
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(actual, user);
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAlreadyExists_notOk() {
        registrationService.register(user);
        User userCopy = user;
        assertThrows(InvalidDataException.class, () -> registrationService.register(userCopy));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userLoginLength_notOk() {
        user.setLogin("login");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setLogin("log");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setLogin("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordLength_notOk() {
        user.setPassword("12345");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setPassword("1234");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setPassword("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAge_notOk() {
        user.setAge(17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setAge(9);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setAge(3);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));

        user.setAge(-11);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setAge(-2378);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));

        user.setAge(374);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setAge(123);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }
}
