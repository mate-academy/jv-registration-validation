package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.exception.RegistrationExceptionMessage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_USER_LOGIN = "testLogin";
    private static final String VALID_USER_PASSWORD = "testPassword";
    private static final int VALID_USER_AGE = 18;
    private static final String TEST_FAILED_EXPECTED_MESSAGE_FALSE =
            "Test failed, expected exception message: ";
    private static final String TEST_FAILED_EXPECTED_USER_FALSE =
            "Test failed to compare actual user data with expected user data.";
    private static final String TEST_FAILED_EXPECTED_USER_ID_FALSE =
            "Test failed to check user.id, expected that user.id will be greater than 0.";
    private RegistrationService registrationService;
    private User testUser;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        testUser = new User();
        testUser.setLogin(VALID_USER_LOGIN);
        testUser.setPassword(VALID_USER_PASSWORD);
        testUser.setAge(VALID_USER_AGE);
    }

    @Test
    void register_nullUser_notOk() {
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
        assertEquals(RegistrationExceptionMessage.USER_CAN_NOT_BE_NULL, exception.getMessage(),
                TEST_FAILED_EXPECTED_MESSAGE_FALSE
                        + RegistrationExceptionMessage.USER_CAN_NOT_BE_NULL
                        + " but actual " + exception.getMessage()
        );
    }

    @Test
    void register_validUser_ok() {
        User actual = registrationService.register(testUser);
        assertNotNull(actual.getId(), TEST_FAILED_EXPECTED_USER_ID_FALSE);
        assertEquals(testUser, actual, TEST_FAILED_EXPECTED_USER_FALSE);
    }

    @Test
    void register_duplicateUser_notOk() {
        Storage.people.add(testUser);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(RegistrationExceptionMessage.USER_EXISTS_MESSAGE, exception.getMessage(),
                TEST_FAILED_EXPECTED_MESSAGE_FALSE
                        + RegistrationExceptionMessage.USER_EXISTS_MESSAGE
                        + " but actual " + exception.getMessage()
        );
    }

    @Test
    void register_userWithNullLogin_notOk() {
        testUser.setLogin(null);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE, exception.getMessage(),
                TEST_FAILED_EXPECTED_MESSAGE_FALSE
                        + RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE
                        + " but actual " + exception.getMessage()
        );
    }

    @Test
    void register_userWith_emptyLogin_notOk() {
        String login = "";
        testUser.setLogin(login);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE, exception.getMessage(),
                TEST_FAILED_EXPECTED_MESSAGE_FALSE
                        + RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE
                        + " but actual " + exception.getMessage()
        );
    }

    @Test
    void register_userWithLengthIsLessThanMinLogin_notOk() {
        String login = "login";
        testUser.setLogin(login);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE, exception.getMessage(),
                TEST_FAILED_EXPECTED_MESSAGE_FALSE
                        + RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE
                        + " but actual " + exception.getMessage()
        );
    }

    @Test
    void register_userWithSpecialCharLogin_notOk() {
        String[] logins = new String[]{"user@name", "?username", "user name", " user   name"};
        for (String login : logins) {
            testUser.setLogin(login);
            RegistrationException exception = assertThrows(RegistrationException.class, () -> {
                registrationService.register(testUser);
            });
            assertEquals(RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE, exception.getMessage(),
                    TEST_FAILED_EXPECTED_MESSAGE_FALSE
                            + RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE
                            + " but actual " + exception.getMessage()
            );
        }
    }

    @Test
    void register_userWithNullPassword_notOk() {
        testUser.setPassword(null);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE, exception.getMessage(),
                TEST_FAILED_EXPECTED_MESSAGE_FALSE
                        + RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE
                        + " but actual " + exception.getMessage()
        );
    }

    @Test
    void register_userWithEmptyPassword_notOk() {
        String password = "";
        testUser.setPassword(password);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE,
                exception.getMessage(),
                TEST_FAILED_EXPECTED_MESSAGE_FALSE
                        + RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE
                        + " but actual " + exception.getMessage()
        );
    }

    @Test
    void register_userWithLengthIsLessThanMinPassword_notOk() {
        String password = "pass";
        testUser.setPassword(password);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE,
                exception.getMessage(),
                TEST_FAILED_EXPECTED_MESSAGE_FALSE
                        + RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE
                        + " but actual " + exception.getMessage()
        );
    }

    @Test
    void register_userWithSpecialChaPassword_notOk() {
        String[] passwords = new String[]{"pass@word", "?password", " password"};
        for (String password : passwords) {
            testUser.setPassword(password);
            RegistrationException exception = assertThrows(RegistrationException.class, () -> {
                registrationService.register(testUser);
            });
            assertEquals(RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE,
                    exception.getMessage(),
                    TEST_FAILED_EXPECTED_MESSAGE_FALSE
                            + RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE
                            + " but actual " + exception.getMessage()
            );
        }
    }

    @Test
    void register_userWithNullAge_notOk() {
        testUser.setAge(null);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_AGE_MESSAGE, exception.getMessage(),
                TEST_FAILED_EXPECTED_MESSAGE_FALSE
                        + RegistrationExceptionMessage.WRONG_AGE_MESSAGE
                        + " but actual " + exception.getMessage()
        );
    }

    @Test
    void register_userWithIsLessThanMinAge_notOk() {
        int age = 17;
        testUser.setAge(age);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_AGE_MESSAGE, exception.getMessage(),
                TEST_FAILED_EXPECTED_MESSAGE_FALSE
                        + RegistrationExceptionMessage.WRONG_AGE_MESSAGE
                        + " but actual " + exception.getMessage()
        );
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
