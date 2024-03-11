package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    static final StorageDao storage = new StorageDaoImpl();
    private static final String VALID_LOGIN = "username";
    private static final String VALID_PASSWORD = "password123";
    private static final int VALID_AGE = 20;
    private static User user;

    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(VALID_AGE);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        Storage.people.clear();
    }

    @Test
    void register_UserOk_notNull() throws ValidationException {
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_UserOkBackFromDb_notNull() throws ValidationException {
        registrationService.register(user);
        User retrivedUser = storage.get(VALID_LOGIN);
        assertNotNull(retrivedUser);
        assertEquals(user, retrivedUser, "Users not equal");
    }

    @Test
    void register_UserAlreadyExists_Exception() {
        User existingUser = new User();
        existingUser.setAge(VALID_AGE);
        existingUser.setLogin("existingUser");
        existingUser.setPassword(VALID_PASSWORD);
        storage.add(existingUser);
        user.setLogin("existingUser");
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals("We already have such user - existingUser", exception.getMessage());
    }

    @Test
    void register_UserIsNull_Exception() throws ValidationException {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(null));
        assertEquals("User is null ", exception.getMessage());
    }

    @Test
    void register_UserLoginIsNull_Exception() throws ValidationException {
        user.setLogin(null);
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals("Login is null", exception.getMessage());
    }

    @Test
    void register_UserAgeIsNull_Exception() throws ValidationException {
        user.setAge(null);
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals("Age is null", exception.getMessage());
    }

    @Test
    void register_UserPasswordIsNull_Exception() throws ValidationException {
        user.setPassword(null);
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals("Password is null", exception.getMessage());
    }

    @Test
    void register_LoginShort_Exception() {
        user.setLogin("user");
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals("Login shorter than 6 symbols - user", exception.getMessage());
    }

    @Test
    void register_PasswordShort_Exception() {
        user.setPassword("pass");
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals("Password shorter than 6 symbols - pass", exception.getMessage());
    }

    @Test
    void register_AgeIncorrect_Exception() {
        user.setAge(17);
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals("Age is incorrect - 17", exception.getMessage());
    }
}
