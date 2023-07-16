package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertThrows(UserIsNullException.class, () -> registrationService.register(NULL_USER));
    }

    @Test
    public void testRegister_NullAge_ThrowsNotEnoughAgeException() {
        assertThrows(NotEnoughAgeException.class, () -> registrationService.register(
                USER_WITH_NULL_AGE));
    }

    @Test
    public void testRegister_NegativeAge_ThrowsNotEnoughAgeException() {
        assertThrows(NotEnoughAgeException.class, () -> registrationService.register(
                USER_WITH_NEGATIVE_AGE));
    }

    @Test
    public void testRegister_NullPassword_ThrowsNotEnoughSizeException() {
        assertThrows(NotEnoughSizeException.class, () -> registrationService.register(
                USER_WITH_NULL_PASSWORD));
    }

    @Test
    public void testRegister_NullLogin_ThrowsNotEnoughSizeException() {
        assertThrows(NotEnoughSizeException.class, () -> registrationService.register(
                USER_WITH_NULL_LOGIN));
    }

    @Test
    public void testRegister_LoginTooShort_ThrowsNotEnoughSizeException() {
        assertThrows(NotEnoughSizeException.class, () -> registrationService.register(
                USER_WITH_SHORT_LOGIN));
    }

    @Test
    public void testRegister_PasswordTooShort_ThrowsNotEnoughSizeException() {
        assertThrows(NotEnoughSizeException.class, () -> registrationService.register(
                USER_WITH_SHORT_PASSWORD));
    }

    @Test
    public void testRegister_AgeBelowMinimum_ThrowsNotEnoughAgeException() {
        assertThrows(NotEnoughAgeException.class, () -> registrationService.register(
                USER_BELOW_MIN_AGE));
    }

    @Test
    public void testRegister_ExistingLogin_ReturnsNull() {
        registrationService.register(EXISTING_USER);
        User registeredUser = registrationService.register(NEW_USER_WITH_EXISTING_LOGIN);
        assertNull(registeredUser);
    }
}
