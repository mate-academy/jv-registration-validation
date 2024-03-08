package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "ValidLogin";
    private static final String SHORT_LOGIN = "abc";
    private static final String VALID_PASSWORD = "Password1!";
    private static final String INVALID_PASSWORD_NO_LOWERCASE = "PASSWORD1!";
    private static final String INVALID_PASSWORD_NO_UPPERCASE = "password1!";
    private static final String INVALID_PASSWORD_NO_DIGITS = "Password!";
    private static final String INVALID_PASSWORD_NO_SPECIAL_SYMBOL = "Password1";
    private static final String INVALID_PASSWORD_SHORT = "Pas1!";
    private static final int VALID_AGE = 18;
    private static final int NON_VALID_AGE = 17;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    public static void setup() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    public void drop() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(UserValidationException.class,
                () -> registrationService.register(null), "User cannot be null");
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, VALID_PASSWORD, VALID_AGE);
        assertThrows(UserValidationException.class,
                () -> registrationService.register(user), "Login cannot be null");
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User(VALID_LOGIN, null, VALID_AGE);
        assertThrows(UserPasswordValidationException.class,
                () -> registrationService.register(user), "Password cannot be null");
    }

    @Test
    public void testValidUserRegistration_ok() {
        User validUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User returnedUser = registrationService.register(validUser);
        assertEquals(validUser, returnedUser,
                "Valid user should be registered successfully");
        assertEquals(Storage.people.get(0), validUser,
                "User is not stored at Storage");
    }

    @Test
    public void testRegistrationWithNullUser_notOk() {
        assertThrows(UserValidationException.class, () -> registrationService.register(null),
                "Registration with null user should fail");
    }

    @Test
    public void testRegistrationWithExistingLogin_notOk() {
        User newUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        Storage.people.add(newUser);
        assertThrows(UserValidationException.class, () -> registrationService.register(newUser),
                "Cannot register user with existing login");
    }

    @Test
    public void testRegistrationWithUnderageUser_notOk() {
        User underageUser = new User(VALID_LOGIN, VALID_PASSWORD, NON_VALID_AGE);
        assertThrows(UserValidationException.class,
                () -> registrationService.register(underageUser),
                "Underage users should not be registered");
    }

    @Test
    public void testRegistrationWithInvalidLogin_notOk() {
        User userWithShortLogin = new User(SHORT_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(UserValidationException.class,
                () -> registrationService.register(userWithShortLogin),
                "Invalid login should not be accepted");
    }

    @Test
    public void testShortPassword_notOk() {
        User user = new User(VALID_LOGIN, INVALID_PASSWORD_SHORT, VALID_AGE);
        assertThrows(UserPasswordValidationException.class,
                () -> registrationService.register(user), "Password too short");
    }

    @Test
    public void testPasswordWithoutDigit_notOk() {
        User user = new User(VALID_LOGIN, INVALID_PASSWORD_NO_DIGITS, VALID_AGE);
        assertThrows(UserPasswordValidationException.class,
                () -> registrationService.register(user),
                "Password must contain at least one digit");
    }

    @Test
    public void testPasswordWithoutUppercase_notOk() {
        User user = new User(VALID_LOGIN, INVALID_PASSWORD_NO_UPPERCASE, VALID_AGE);
        assertThrows(UserPasswordValidationException.class,
                () -> registrationService.register(user),
                "Password must contain at least one uppercase letter");
    }

    @Test
    public void testPasswordWithoutLowercase_notOk() {
        User user = new User(VALID_LOGIN, INVALID_PASSWORD_NO_LOWERCASE, VALID_AGE);
        assertThrows(UserPasswordValidationException.class,
                () -> registrationService.register(user),
                "Password must contain at least one lowercase letter");
    }

    @Test
    public void testPasswordWithoutSpecialSymbol_notOk() {
        User user = new User(VALID_LOGIN, INVALID_PASSWORD_NO_SPECIAL_SYMBOL, VALID_AGE);
        assertThrows(UserPasswordValidationException.class,
                () -> registrationService.register(user),
                "Password must contain at least one special symbol");
    }
}
