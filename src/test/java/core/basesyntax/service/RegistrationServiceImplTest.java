package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User testUser;
    private static final String VALID_LOGIN_1 = "1ValidLogin123";
    private static final String VALID_LOGIN_2 = "2ValidLogin123";
    private static final String VALID_LOGIN_3 = "3ValidLogin123";
    private static final String VALID_LOGIN_4 = "4ValidLogin123";
    private static final String VALID_LOGIN_5 = "5ValidLogin123";
    private static final String VALID_LOGIN_6 = "6ValidLogin123";
    private static final String VALID_LOGIN_7 = "7ValidLogin123";
    private static final String VALID_LOGIN_8 = "8ValidLogin123";
    private static final String VALID_LOGIN_9 = "9ValidLogin123";
    private static final String VALID_LOGIN_10 = "10ValidLogin123";
    private static final String INVALID_LOGIN_WITH_SPACE = "123j fyu";
    private static final String INVALID_LOGIN_WITH_METACHARACTER = "123j$fyu";
    private static final String VALID_PASSWORD = "validPassword123$";
    private static final String INVALID_PASSWORD_ONLY_DIGITS = "1234567";
    private static final String INVALID_PASSWORD_SHORT = "Aa@45";
    private static final Integer VALID_AGE = 25;
    private static final Integer MINIMUM_AGE_VALUE = 18;
    private static final Integer AGE_LESS_THAN_MINIMUM = 17;
    private static final Integer AGE_NEGATIVE_VALUE = -1;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setPassword(VALID_PASSWORD);
        testUser.setAge(VALID_AGE);
    }

    @Test
    void register_nullUser_NotOk() {
        testUser.setLogin(VALID_LOGIN_1);
        testUser = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nulLogin_NotOk() {
        testUser.setLogin(VALID_LOGIN_2);
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullPassword_NotOk() {
        testUser.setLogin(VALID_LOGIN_3);
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullAge_NotOk() {
        testUser.setLogin(VALID_LOGIN_4);
        testUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_negativeAge_NotOk() {
        testUser.setLogin(VALID_LOGIN_5);
        testUser.setAge(AGE_NEGATIVE_VALUE);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ageLessThanMinValue_NotOk() {
        testUser.setLogin(VALID_LOGIN_6);
        testUser.setAge(AGE_LESS_THAN_MINIMUM);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_passwordLengthLessThanMinLength_NotOk() {
        testUser.setLogin(VALID_LOGIN_7);
        testUser.setPassword(INVALID_PASSWORD_SHORT);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_passwordNotContainsLettersDigitsSpecChars_NotOk() {
        testUser.setLogin(VALID_LOGIN_8);
        testUser.setPassword(INVALID_PASSWORD_ONLY_DIGITS);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_loginContainsSpace_NotOk() {
        testUser.setLogin(INVALID_LOGIN_WITH_SPACE);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_loginContainsMetacharacter_NotOk() {
        testUser.setLogin(INVALID_LOGIN_WITH_METACHARACTER);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ageEqualsMinValue_Ok() {
        testUser.setLogin(VALID_LOGIN_9);
        testUser.setAge(MINIMUM_AGE_VALUE);
        assertEquals(testUser, registrationService.register(testUser));
    }

    @Test
    void register_userValidated_Ok() {
        testUser.setLogin(VALID_LOGIN_10);
        assertEquals(testUser, registrationService.register(testUser));
    }
}
