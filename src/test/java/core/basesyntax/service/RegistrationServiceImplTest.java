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
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));
        assertEquals(invalidDataException.getMessage(),
                "User cannot be NULL!");
    }

    @Test
    void register_loginAlreadyExists_notOk() {
        registrationService.register(user);
        User userCopy = user;
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class,
                () -> registrationService.register(userCopy));
        assertEquals(invalidDataException.getMessage(),
                "Login is already taken!");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));
        assertEquals(invalidDataException.getMessage(),
                "Login should not be NULL!");
    }

    @Test
    void register_loginLength_notOk() {
        user.setLogin("login");
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class, 
                () -> registrationService.register(user));
        assertEquals(invalidDataException.getMessage(),
                "Login must be at least 6 characters, but was 5!");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class, 
                () -> registrationService.register(user));
        assertEquals(invalidDataException.getMessage(),
                "The password should not be NULL!");
    }

    @Test
    void register_userPasswordLength_notOk() {
        user.setPassword("12345");
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));
        assertEquals(invalidDataException.getMessage(),
                "The password must be at least 6 characters, but was 5!");
    }

    @Test
    void register_notValidUserAge_notOk() {
        user.setAge(17);
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class, 
                () -> registrationService.register(user));
        assertEquals(invalidDataException.getMessage(), 
                "User age must be at least 18 years, but was 17!");
    }

    @Test
    void register_negativeUserAge_notOk() {
        user.setAge(-11);
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));
        assertEquals(invalidDataException.getMessage(),
                "User age should not be negative!");

    }

    @Test
    void register_exceedingMaxUserAge_notOk() {
        user.setAge(374);
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));
        assertEquals(invalidDataException.getMessage(),
                "User age should be smaller than 122 years, but was 374!");
    }
}
