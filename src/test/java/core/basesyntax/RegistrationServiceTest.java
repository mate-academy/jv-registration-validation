package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.InvalidUserDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final int VALID_AGE = 19;
    private static final String VALID_LOGIN_1 = "misterBean";
    private static final String VALID_LOGIN_2 = "nagibator223";
    private static final String VALID_PASSWORD = "smallgreencar";
    private static final String EMPTY_STRING = "";
    private static final int INVALID_AGE = 12;
    private static final String INVALID_LOGIN = "bean";
    private static final String INVALID_PASSWORD = "mycar";
    private static final int CRITICAL_AGE = -12478;
    private static final String CRITICAL_LOGIN_OR_PASSWORD = null;
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private final StorageDaoImpl storageDao = new StorageDaoImpl();
    private final User testUser = new User();

    private void changeUserFields(User currentUser, int age, String login, String password) {
        currentUser.setAge(age);
        currentUser.setLogin(login);
        currentUser.setPassword(password);
    }

    @Nested
    class FoundUserInDatabaseTest {
        private final User userInDatabase = new User();

        @Test
        void foundUserInDatabase_UserNotFound_False() {
            changeUserFields(userInDatabase, VALID_AGE, VALID_LOGIN_1, VALID_PASSWORD);
            storageDao.add(userInDatabase);
            changeUserFields(testUser, VALID_AGE, VALID_LOGIN_2, VALID_PASSWORD);
            assertFalse(registrationService.foundUserInDatabase(testUser));
        }

        @Test
        void foundUserInDatabase_UserFound_True() {
            changeUserFields(userInDatabase, VALID_AGE, VALID_LOGIN_2, VALID_PASSWORD);
            storageDao.add(userInDatabase);
            changeUserFields(testUser, VALID_AGE, VALID_LOGIN_1, VALID_PASSWORD);
            assertTrue(registrationService.foundUserInDatabase(testUser));
        }
    }

    @Nested
    class RegisterTest {

        @Test
        void register_userIsAddedToDatabase_True() throws InvalidUserDataException {
            changeUserFields(testUser, VALID_AGE, VALID_LOGIN_1, VALID_PASSWORD);
            assertEquals(registrationService.register(testUser), testUser);
        }

        @Test
        void register_userHasCriticalFields_Throws() {
            changeUserFields(testUser, INVALID_AGE, CRITICAL_LOGIN_OR_PASSWORD,
                    CRITICAL_LOGIN_OR_PASSWORD);
            assertThrows(InvalidUserDataException.class,
                    () -> registrationService.register(testUser));
        }

        @Test
        void register_userIsNotValid_Throws() {
            changeUserFields(testUser, INVALID_AGE, VALID_LOGIN_1, VALID_PASSWORD);
            assertThrows(InvalidUserDataException.class,
                    () -> registrationService.register(testUser));
            changeUserFields(testUser, VALID_AGE, INVALID_LOGIN, VALID_PASSWORD);
            assertThrows(InvalidUserDataException.class,
                    () -> registrationService.register(testUser));
            changeUserFields(testUser, VALID_AGE, VALID_LOGIN_1, INVALID_PASSWORD);
            assertThrows(InvalidUserDataException.class,
                    () -> registrationService.register(testUser));
        }

        @Test
        void register_userIsValid_NotThrows() {
            changeUserFields(testUser, VALID_AGE, VALID_LOGIN_1, VALID_PASSWORD);
            assertDoesNotThrow(() -> registrationService.register(testUser));
        }
    }

    @Nested
    class UserValidatorTest {
        @Test
        void userValidator_userIsNull_Throws() {
            assertThrows(InvalidUserDataException.class,
                    () -> registrationService.userValidator(null));
        }

        @Test
        void userValidator_userIsNotNull_NotThrows() {
            changeUserFields(testUser, VALID_AGE, VALID_LOGIN_1, VALID_PASSWORD);
            assertDoesNotThrow(() -> registrationService.userValidator(testUser));
        }

        @Test
        void userValidator_userHaveInvalidAge_Throws() {
            changeUserFields(testUser, INVALID_AGE, VALID_LOGIN_1, VALID_PASSWORD);
            assertThrows(InvalidUserDataException.class,
                    () -> registrationService.userValidator(testUser));
        }

        @Test
        void userValidator_userHaveCriticalAge_Throws() {
            changeUserFields(testUser, CRITICAL_AGE, VALID_LOGIN_1, VALID_PASSWORD);
            assertThrows(InvalidUserDataException.class,
                    () -> registrationService.userValidator(testUser));
        }

        @Test
        void userValidator_userHaveValidAge_NotThrows() {
            changeUserFields(testUser, VALID_AGE, VALID_LOGIN_1, VALID_PASSWORD);
            assertDoesNotThrow(() -> registrationService.userValidator(testUser));
        }

        @Test
        void userValidator_userHaveInvalidLogin_Throws() {
            changeUserFields(testUser, VALID_AGE, INVALID_LOGIN, VALID_PASSWORD);
            assertThrows(InvalidUserDataException.class,
                    () -> registrationService.userValidator(testUser));
        }

        @Test
        void userValidator_userHaveEmptyLogin_Throws() {
            changeUserFields(testUser, VALID_AGE, EMPTY_STRING, VALID_PASSWORD);
            assertThrows(InvalidUserDataException.class,
                    () -> registrationService.userValidator(testUser));
        }

        @Test
        void userValidator_userHaveValidLogin_NotThrows() {
            changeUserFields(testUser, VALID_AGE, VALID_LOGIN_1, VALID_PASSWORD);
            assertDoesNotThrow(() -> registrationService.userValidator(testUser));
        }

        @Test
        void userValidator_userHaveInvalidPassword_Throws() {
            changeUserFields(testUser, VALID_AGE, VALID_LOGIN_1, INVALID_PASSWORD);
            assertThrows(InvalidUserDataException.class,
                    () -> registrationService.userValidator(testUser));
        }

        @Test
        void userValidator_userHaveEmptyPassword_Throws() {
            changeUserFields(testUser, VALID_AGE, VALID_LOGIN_1, EMPTY_STRING);
            assertThrows(InvalidUserDataException.class,
                    () -> registrationService.userValidator(testUser));
        }

        @Test
        void userValidator_userHaveValidPassword_NotThrows() {
            changeUserFields(testUser, VALID_AGE, VALID_LOGIN_1, VALID_PASSWORD);
            assertDoesNotThrow(() -> registrationService.userValidator(testUser));
        }

        @Test
        void userValidator_userHaveNullLogin_Throws() {
            changeUserFields(testUser, VALID_AGE, CRITICAL_LOGIN_OR_PASSWORD, VALID_PASSWORD);
            assertThrows(InvalidUserDataException.class,
                    () -> registrationService.userValidator(testUser));
        }

        @Test
        void userValidator_userHaveNullPassword_Throws() {
            changeUserFields(testUser, VALID_AGE, VALID_LOGIN_1, CRITICAL_LOGIN_OR_PASSWORD);
            assertThrows(InvalidUserDataException.class,
                    () -> registrationService.userValidator(testUser));
        }
    }
}
