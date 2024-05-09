package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.exception.RegistrationExceptionMessage;
import core.basesyntax.model.User;
import core.basesyntax.service.interfaces.RegistrationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String LOGIN_USER = "userLogin";
    private static final String PASSWORD_USER = "password";
    private static final int AGE_USER = 19;
    private static final String EMPTY_LOGIN_USER = "";
    private static final String ONE_CHAR_LOGIN_USER = "a";
    private static final String TWO_CHAR_LOGIN_USER = "as";
    private static final String THREE_CHAR_LOGIN_USER = "asd";
    private static final String FOUR_CHAR_LOGIN_USER = "asdf";
    private static final String FIVE_CHAR_LOGIN_USER = "asdfg";
    private static final String SIX_CHAR_LOGIN_USER = "asdfgh";
    private static final String SEVEN_CHAR_LOGIN_USER = "asdfghj";
    private static final String EIGHT_CHAR_LOGIN_USER = "asdfghjk";
    private static final String START_WITH_SPACE_LOGIN_USER = " sdfghjk";
    private static final String EMPTY_PASSWORD_USER = "";
    private static final String ONE_CHAR_PASSWORD_USER = "a";
    private static final String TWO_CHAR_PASSWORD_USER = "a1";
    private static final String THREE_CHAR_PASSWORD_USER = "a23";
    private static final String FOUR_CHAR_PASSWORD_USER = "as44";
    private static final String FIVE_CHAR_PASSWORD_USER = "asdf5";
    private static final String SIX_CHAR_PASSWORD_USER = "a6dfgh";
    private static final String SEVEN_CHAR_PASSWORD_USER = "asd7ghj";
    private static final String EIGHT_CHAR_PASSWORD_USER = "8sdfghjk";
    private static final String START_WITH_SPACE_PASSWORD_USER = " 1dfghjk";
    private static final int EIGHTEEN_AGE_USER = 18;
    private static final int ZERO_AGE_USER = 0;
    private static final int TEN_AGE_USER = 10;
    private static final int FIFTEEN_AGE_USER = 15;
    private static final int SEVENTEEN_AGE_USER = 17;
    private static final int TWENTY_AGE_USER = 20;
    private static final int TWENTY_ONE_AGE_USER = 21;
    private static final int FIFTY_AGE_USER = 50;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(LOGIN_USER);
        user.setPassword(PASSWORD_USER);
        user.setAge(AGE_USER);
    }

    @Test
    void register_nullUser_notOk() {
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
        assertEquals(RegistrationExceptionMessage.USER_CAN_NOT_BE_NULL, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.USER_CAN_NOT_BE_NULL
        );
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.LOGIN_IS_NOT_NULL, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.LOGIN_IS_NOT_NULL
        );
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin(EMPTY_LOGIN_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE
        );
    }

    @Test
    void register_oneCharLogin_notOk() {
        user.setLogin(ONE_CHAR_LOGIN_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE
        );
    }

    @Test
    void register_twoCharLogin_notOk() {
        user.setLogin(TWO_CHAR_LOGIN_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE
        );
    }

    @Test
    void register_threeCharLogin_notOk() {
        user.setLogin(THREE_CHAR_LOGIN_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE
        );
    }

    @Test
    void register_fourCharLogin_notOk() {
        user.setLogin(FOUR_CHAR_LOGIN_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE
        );
    }

    @Test
    void register_fiveCharLogin_notOk() {
        user.setLogin(FIVE_CHAR_LOGIN_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE
        );
    }

    @Test
    void register_sixCharLogin_ok() {
        user.setLogin(SIX_CHAR_LOGIN_USER);
        User actual = registrationService.register(user);
        assertTrue(actual.getId() > 0,
                "Test failed to check user.id, expected that user.id will be greater than 0");
        assertEquals(user, actual,
                "Test failed to compare actual user data with expected user data");
    }

    @Test
    void register_sevenCharLogin_ok() {
        user.setLogin(SEVEN_CHAR_LOGIN_USER);
        User actual = registrationService.register(user);
        assertTrue(actual.getId() > 0,
                "Test failed to check user.id, expected that user.id will be greater than 0");
        assertEquals(user, actual,
                "Test failed to compare actual user data with expected user data");
    }

    @Test
    void register_eightCharLogin_ok() {
        user.setLogin(EIGHT_CHAR_LOGIN_USER);
        User actual = registrationService.register(user);
        assertTrue(actual.getId() > 0,
                "Test failed to check user.id, expected that user.id will be greater than 0");
        assertEquals(user, actual,
                "Test failed to compare actual user data with expected user data");
    }

    @Test
    void register_startWithSpaceLogin_notOk() {
        user.setLogin(START_WITH_SPACE_LOGIN_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE, exception.getMessage());
    }

    @Test
    void register_uniqLogin_ok() {
        User actual = registrationService.register(user);
        assertTrue(actual.getId() > 0,
                "Test failed to check user.id, expected that user.id will be greater than 0");
        assertEquals(user, actual,
                "Test failed to compare actual user data with expected user data");
    }

    @Test
    void register_loginNotUniq_notOk() {
        Storage.people.add(user);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.USER_EXISTS_MESSAGE, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.USER_EXISTS_MESSAGE
        );
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.PASSWORD_IS_NOT_NULL, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.PASSWORD_IS_NOT_NULL
        );
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword(EMPTY_PASSWORD_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE
        );
    }

    @Test
    void register_oneCharPassword_notOk() {
        user.setPassword(ONE_CHAR_PASSWORD_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE
        );
    }

    @Test
    void register_twoCharPassword_notOk() {
        user.setPassword(TWO_CHAR_PASSWORD_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE
        );
    }

    @Test
    void register_threeCharPassword_notOk() {
        user.setPassword(THREE_CHAR_PASSWORD_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE
        );
    }

    @Test
    void register_fourCharPassword_notOk() {
        user.setPassword(FOUR_CHAR_PASSWORD_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE
        );
    }

    @Test
    void register_fiveCharPassword_notOk() {
        user.setPassword(FIVE_CHAR_PASSWORD_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE
        );
    }

    @Test
    void register_sixCharPassword_ok() {
        user.setPassword(SIX_CHAR_PASSWORD_USER);
        User actual = registrationService.register(user);
        assertTrue(actual.getId() > 0,
                "Test failed to check user.id, expected that user.id will be greater than 0");
        assertEquals(user, actual,
                "Test failed to compare actual user data with expected user data");
    }

    @Test
    void register_sevenCharPassword_ok() {
        user.setPassword(SEVEN_CHAR_PASSWORD_USER);
        User actual = registrationService.register(user);
        assertTrue(actual.getId() > 0,
                "Test failed to check user.id, expected that user.id will be greater than 0");
        assertEquals(user, actual,
                "Test failed to compare actual user data with expected user data");
    }

    @Test
    void register_eightCharPassword_ok() {
        user.setPassword(EIGHT_CHAR_PASSWORD_USER);
        User actual = registrationService.register(user);
        assertTrue(actual.getId() > 0,
                "Test failed to check user.id, expected that user.id will be greater than 0");
        assertEquals(user, actual,
                "Test failed to compare actual user data with expected user data");
    }

    @Test
    void register_startWithSpacePassword_notOk() {
        user.setPassword(START_WITH_SPACE_PASSWORD_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE, exception.getMessage());
    }

    @Test
    void register_eighteenAge_Ok() {
        user.setAge(EIGHTEEN_AGE_USER);
        User actual = registrationService.register(user);
        assertEquals(user, actual,
                "Test failed to compare actual user data with expected user data");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.AGE_IS_NOT_NULL, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.AGE_IS_NOT_NULL
        );
    }

    @Test
    void register_zeroAge_notOk() {
        user.setAge(ZERO_AGE_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_AGE_MESSAGE, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.WRONG_AGE_MESSAGE
        );
    }

    @Test
    void register_tenAge_notOk() {
        user.setAge(TEN_AGE_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_AGE_MESSAGE, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.WRONG_AGE_MESSAGE
        );
    }

    @Test
    void register_fifteenAge_notOk() {
        user.setAge(FIFTEEN_AGE_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_AGE_MESSAGE, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.WRONG_AGE_MESSAGE
        );
    }

    @Test
    void register_seventeenAge_notOk() {
        user.setAge(SEVENTEEN_AGE_USER);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(RegistrationExceptionMessage.WRONG_AGE_MESSAGE, exception.getMessage(),
                "Test failed actual exception message: " + exception.getMessage()
                        + " not equal to " + RegistrationExceptionMessage.WRONG_AGE_MESSAGE
        );
    }

    @Test
    void register_twentyAge_ok() {
        user.setAge(TWENTY_AGE_USER);
        User actual = registrationService.register(user);
        assertEquals(user, actual,
                "Test failed to compare actual user data with expected user data");
    }

    @Test
    void register_twentyOneAge_ok() {
        user.setAge(TWENTY_ONE_AGE_USER);
        User actual = registrationService.register(user);
        assertEquals(user, actual,
                "Test failed to compare actual user data with expected user data");
    }

    @Test
    void register_fiftyAge_ok() {
        user.setAge(FIFTY_AGE_USER);
        User actual = registrationService.register(user);
        assertEquals(user, actual,
                "Test failed to compare actual user data with expected user data");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
