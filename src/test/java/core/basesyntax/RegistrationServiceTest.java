package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.LoginAlreadyExistsException;
import core.basesyntax.exceptions.NotEnoughAgeException;
import core.basesyntax.exceptions.NotEnoughSizeException;
import core.basesyntax.exceptions.UserIsNullException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final User EXISTING_USER = new User("BobAlison", "password", 25);
    private static final User NEW_USER_WITH_EXISTING_LOGIN = new User(
            "BobAlison", "password", 30);
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private static final User NULL_USER = null;
    private static final User VALID_USER = new User("NickAlison", "password", 65);
    private static final User USER_WITH_NULL_AGE = new User("BobAlison", "password", null);
    private static final User USER_WITH_NULL_PASSWORD = new User("BobAlison", null, 25);
    private static final User USER_WITH_NULL_LOGIN = new User(null, "password", 25);
    private static final User USER_WITH_SHORT_LOGIN = new User("abc", "password", 25);
    private static final User USER_WITH_SHORT_PASSWORD = new User("BobAlison", "abc", 25);
    private static final User USER_BELOW_MIN_AGE = new User("BobAlison", "password", 16);
    private static final User USER_WITH_NEGATIVE_AGE = new User("NickAlison", "password", -16);
    private RegistrationService registrationService;

    @BeforeEach
    public void setup() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void testRegister_ValidUser_ReturnsRegisteredUser() {
        User registeredUser = registrationService.register(VALID_USER);
        assertNotNull(registeredUser);
        assertEquals(VALID_USER, registeredUser);
    }

    @Test
    public void testRegister_NullUser_ThrowsUserIsNullException() {
        UserIsNullException userIsNullException = assertThrows(
                UserIsNullException.class, () -> registrationService.register(NULL_USER));
        assertEquals("Can't register user, because user is null", userIsNullException.getMessage());
    }

    @Test
    public void testRegister_NullAge_ThrowsNotEnoughAgeException() {
        NotEnoughAgeException notEnoughAgeException = assertThrows(
                NotEnoughAgeException.class, () -> registrationService.register(
                        USER_WITH_NULL_AGE));
        assertEquals("The age of the user can't be null", notEnoughAgeException.getMessage());
    }

    @Test
    public void testRegister_NegativeAge_ThrowsNotEnoughAgeException() {
        NotEnoughAgeException notEnoughAgeException = assertThrows(
                NotEnoughAgeException.class, () -> registrationService.register(
                USER_WITH_NEGATIVE_AGE));
        assertEquals("The age of the user must be at least " + MIN_AGE,
                notEnoughAgeException.getMessage());
    }

    @Test
    public void testRegister_NullPassword_ThrowsNotEnoughSizeException() {
        NotEnoughSizeException notEnoughSizeException = assertThrows(
                NotEnoughSizeException.class, () -> registrationService.register(
                USER_WITH_NULL_PASSWORD));
        assertEquals("The password can't be null", notEnoughSizeException.getMessage());
    }

    @Test
    public void testRegister_NullLogin_ThrowsNotEnoughSizeException() {
        NotEnoughSizeException notEnoughSizeException = assertThrows(
                NotEnoughSizeException.class, () -> registrationService.register(
                USER_WITH_NULL_LOGIN));
        assertEquals("The login can't be null", notEnoughSizeException.getMessage());
    }

    @Test
    public void testRegister_LoginTooShort_ThrowsNotEnoughSizeException() {
        NotEnoughSizeException notEnoughSizeException = assertThrows(
                NotEnoughSizeException.class, () -> registrationService.register(
                USER_WITH_SHORT_LOGIN));
        assertEquals("The length of the login must be at least " + MIN_LENGTH,
                notEnoughSizeException.getMessage());
    }

    @Test
    public void testRegister_PasswordTooShort_ThrowsNotEnoughSizeException() {
        NotEnoughSizeException notEnoughSizeException = assertThrows(
                NotEnoughSizeException.class, () -> registrationService.register(
                USER_WITH_SHORT_PASSWORD));
        assertEquals("The length of the password must be at least " + MIN_LENGTH,
                notEnoughSizeException.getMessage());
    }

    @Test
    public void testRegister_AgeBelowMinimum_ThrowsNotEnoughAgeException() {
        NotEnoughAgeException notEnoughAgeException = assertThrows(
                NotEnoughAgeException.class, () -> registrationService.register(
                USER_BELOW_MIN_AGE));
        assertEquals("The age of the user must be at least " + MIN_AGE,
                notEnoughAgeException.getMessage());
    }

    @Test
    public void testRegister_ExistingLogin_ThrowsLoginAlreadyExistsException() {
        Storage.people.add(EXISTING_USER);
        LoginAlreadyExistsException loginAlreadyExistsException = assertThrows(
                LoginAlreadyExistsException.class, () -> registrationService.register(
                NEW_USER_WITH_EXISTING_LOGIN));
        assertEquals("User already exists with login " + NEW_USER_WITH_EXISTING_LOGIN.getLogin(),
                loginAlreadyExistsException.getMessage());
    }
}
