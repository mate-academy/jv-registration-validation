package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static final Long MIN_ID_LENGTH = 1L;
    private static final Long MAX_ID_LENGTH = 200L;
    private static final String LOGIN_OK = "asdfghjklfgsdcv";
    private static final String LOGIN_WITH_SPACES = "asdfg hjklfg sdcv";
    private static final String MIN_PASSWORD_LENGTH = "o1t2t3";
    private static final String MAX_PASSWORD_LENGTH = "qwertyuiop[]#$%^&asdfghj";
    private static final String WITHOUT_SPECIAL_SYMBOLS = "password78909876";
    private static final int MIN_AGE = 18;
    private static final int NEGATIVE_AGE = -18;
    private static final int MAX_AGE = 110;
    private static final String MIN_LOGIN_LENGTH = "abcdef";
    private static final String LESS_THAN_MIN_LOGIN_LENGHT = "abcde";
    private static final String EMPTY_PASSWORD = "";
    private static final int SMALLER_THAN_MIN_AGE = 17;

    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(MIN_ID_LENGTH);
        user.setLogin(LOGIN_OK);
        user.setPassword(MIN_PASSWORD_LENGTH);
        user.setAge(MIN_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registerUser_NullId_notOk() {
        user.setId(null);
        try {
            registrationService.validateUserId(user.getId());
        } catch (RegistrationException e) {
            assertEquals("Minimum expected ID length is: " + MIN_ID_LENGTH + "", e.getMessage());
        }
    }

    @Test
    void registerUser_InvalidId_LessThanMinLength_notOk() {
        user.setId(MIN_ID_LENGTH - 1);
        try {
            registrationService.validateUserId(user.getId());
        } catch (RegistrationException e) {
            assertEquals("Invalid User ID Length", e.getMessage());
        }
    }

    @Test
    void registerUser_InvalidId_GreaterThanMaxLength_notOk() {
        user.setId(MAX_ID_LENGTH + 1);
        try {
            registrationService.validateUserId(user.getId());
        } catch (RegistrationException e) {
            assertEquals("Invalid User ID length", e.getMessage());
        }
    }

    @Test
    void registerUser_NullLogin_notOk() {
        user.setLogin(null);
        try {
            registrationService.validateUserLogin(user.getLogin());
        } catch (RegistrationException e) {
            assertEquals("User Login cannot be null", e.getMessage());
        }
    }

    @Test
    void registerUser_InvalidLoginFormat_LessThanMinLength_notOk() {
        user.setLogin(LESS_THAN_MIN_LOGIN_LENGHT);
        try {
            registrationService.validateUserLogin(user.getLogin());
        } catch (RegistrationException e) {
            assertEquals("User Login lengths less than 6 Symbols", e.getMessage());
        }
    }

    @Test
    void registerUser_InvalidLoginFormat_LoginWithSpaces_notOk() {
        user.setLogin(LOGIN_WITH_SPACES);
        try {
            registrationService.validateUserLogin(user.getLogin());
        } catch (RegistrationException e) {
            assertEquals("User Login cannot contain spaces", e.getMessage());
        }
    }

    @Test
    void registerUser_NullPassword_notOk() {
        user.setPassword(null);
        try {
            registrationService.validateUserPassword(user.getPassword());
        } catch (RegistrationException e) {
            assertEquals("Password cannot be null.", e.getMessage());
        }
    }

    @Test
    void registerUser_EmptyPassword_notOk() {
        user.setPassword(EMPTY_PASSWORD);
        try {
            registrationService.validateUserPassword(user.getPassword());
        } catch (RegistrationException e) {
            assertEquals("Password cannot be empty.", e.getMessage());
        }
    }

    @Test
    void registerUser_InvalidPasswordFormat_LessThanMinLength_notOk() {
        user.setPassword(MIN_PASSWORD_LENGTH.substring(0, 3));
        try {
            registrationService.validateUserPassword(user.getPassword());
        } catch (RegistrationException e) {
            assertEquals("Password is shorter than the min length.", e.getMessage());
        }
    }

    @Test
    void registerUser_InvalidPasswordFormat_MoreThanMaxLength_notOk() {
        user.setPassword(MAX_PASSWORD_LENGTH + 1);
        try {
            registrationService.validateUserPassword(user.getPassword());
        } catch (RegistrationException e) {
            assertEquals("Password is bigger than the max length.", e.getMessage());
        }
    }

    @Test
    void registerUser_InvalidPasswordFormat_WithoutSpecialSymbols_notOk() {
        user.setPassword(WITHOUT_SPECIAL_SYMBOLS);
        try {
            registrationService.validateUserPassword(user.getPassword());
        } catch (RegistrationException e) {
            assertEquals("Your password must contain at least one of the following special symbols:"
                    + " @, #, $, %, ^, &.", e.getMessage());
        }
    }

    @Test
    void registerUser_NullAge_notOk() {
        user.setAge(null);
        try {
            registrationService.validateUserAge(user.getAge());
        } catch (RegistrationException e) {
            assertEquals("Invalid age range ", e.getMessage());
        }
    }

    @Test
    void registerUser_InvalidAge_NegativeAge_notOk() {
        user.setAge(NEGATIVE_AGE);
        try {
            registrationService.validateUserAge(user.getAge());
        } catch (RegistrationException e) {
            assertEquals("Invalid User age range", e.getMessage());
        }
    }

    @Test
    void registerUser_InvalidAge_YoungerThanMinAge_notOk() {
        user.setAge(SMALLER_THAN_MIN_AGE);
        try {
            registrationService.validateUserAge(user.getAge());
        } catch (RegistrationException e) {
            assertEquals("Invalid User age range", e.getMessage());
        }
    }

    @Test
    void registerUser_InvalidAge_OlderThanMaxAge_notOk() {
        user.setAge(MAX_AGE + 1);
        try {
            registrationService.validateUserAge(user.getAge());
        } catch (RegistrationException e) {
            assertEquals("Invalid age", e.getMessage());
        }
    }

    @Test
    void registerUser_With_Min_ValidData_ok() {
        user.setId(MIN_ID_LENGTH);
        user.setLogin(MIN_LOGIN_LENGTH);
        user.setPassword(MIN_PASSWORD_LENGTH);
        user.setAge(MIN_AGE);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void registerUser_With_Max_ValidData_ok() {
        user.setId(MAX_ID_LENGTH);
        user.setLogin(LOGIN_OK);
        user.setPassword(MAX_PASSWORD_LENGTH);
        user.setAge(MAX_AGE);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void registerUser_ValidData_Ok() {
        assertDoesNotThrow(() -> registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void registerUser_MinIdLength_Ok() {
        user.setId(MIN_ID_LENGTH);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void registerUser_MaxIdLength_Ok() {
        user.setId(MAX_ID_LENGTH);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void registerUser_MinLoginLength_Ok() {
        user.setLogin(MIN_LOGIN_LENGTH);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void registerUser_MinPasswordLength_Ok() {
        user.setPassword(MIN_PASSWORD_LENGTH);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void registerUser_MaxPasswordLength_Ok() {
        user.setPassword(MAX_PASSWORD_LENGTH);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void registerUser_MinAge_Ok() {
        user.setAge(MIN_AGE);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void registerUser_MaxAge_Ok() {
        user.setAge(MAX_AGE);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }
}
