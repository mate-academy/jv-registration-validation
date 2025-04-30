package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int INVALID_AGE = 16;
    private static final int VALID_AGE = 18;
    private static final String INVALID_PASSWORD = "qwer";
    private static final String VALID_PASSWORD = "qwerty";
    private static final String VALID_LOGIN = "nameEx";
    private static final String INVALID_LOGIN = "name";

    private User user;
    private StorageDao storage = new StorageDaoImpl();
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(VALID_AGE);
        user.setPassword(VALID_PASSWORD);
        user.setLogin(VALID_LOGIN);
        Storage.people.clear();
    }

    @Test
    public void register_AddToStorage_Ok() {
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser, "The registered user should match the valid user");
        assertEquals(user, storage.get(user.getLogin()),
                "The registered user should be in the storage");
    }

    @Test
    public void register_invalidAge_notOk() {
        user.setAge(INVALID_AGE);
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid age: must be at least 18 years old.",
                exception.getMessage(), "Incorrect validation error message");
    }

    @Test
    public void register_invalidLogin_notOk() {
        user.setLogin(INVALID_LOGIN);
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid login: must be at least 6 characters long.",
                exception.getMessage(), "Incorrect validation error message");
    }

    @Test
    public void register_nullLogin_notOk() {
        user.setLogin(null);
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid login: cannot be null or empty.",
                exception.getMessage(), "Incorrect validation error message");
    }

    @Test
    public void register_invalidPassword_notOk() {
        user.setPassword(INVALID_PASSWORD);
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid password: must be at least 6 characters long.",
                exception.getMessage(), "Incorrect validation error message");
    }

    @Test
    public void register_nullPassword_notOk() {
        user.setPassword(null);
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid password: cannot be null or empty.",
                exception.getMessage(), "Incorrect validation error message");
    }

    @Test
    public void register_sameLogin_notOk() {
        storage.add(user);
        User newUser = new User();
        newUser.setLogin(VALID_LOGIN);
        newUser.setPassword("password");
        newUser.setAge(VALID_AGE);

        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(newUser));
        assertEquals("Invalid login: this login is already exists",
                exception.getMessage(), "Incorrect validation error message");
    }
}
