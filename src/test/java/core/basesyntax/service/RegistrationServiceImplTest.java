package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final int NEGATIVE_AGE = -10;
    public static final int ZERO_AGE = 0;
    public static final int BELOW_MINIMUM_AGE = 17;
    public static final int MINIMUM_AGE = 18;
    public static final int OVER_MINIMUM_AGE = 21;
    public static final int EXPECTED_TWO = 2;
    public static final int EXPECTED_FOUR = 4;
    public static final int FIRST = 0;
    public static final String LOGIN_YURI = "mylogin";
    public static final String LOGIN_DASHA = "DZlogin";
    public static final String LOGIN_NAZAR = "nazar2005";
    public static final String CORRECT_LOGIN = "qwerty";
    public static final String INCORRECT_LOGIN = "log";
    public static final String CORRECT_LOGIN_SIX_LETTERS = "mlogin";
    public static final String CORRECT_PASSWORD = "qwertyu";
    public static final String CORRECT_PASSWORD_SIX_LETTERS = "123456";
    public static final String INCORRECT_PASSWORD = "qwer";
    public static final String EMPTY_LINE = "";

    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        people.clear();
    }

    @Test
    void register_nullLoginValue_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(
                new User(null, CORRECT_PASSWORD, MINIMUM_AGE)));
    }

    @Test
    void register_NullPasswordValue_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(
                new User(LOGIN_YURI, null, MINIMUM_AGE)));
    }

    @Test
    void register_NullAgeValue_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(
                new User(LOGIN_YURI, CORRECT_PASSWORD, null)));
    }

    @Test
    void register_correctEge_Ok() {
        registrationService.register(new User(LOGIN_YURI, CORRECT_PASSWORD, MINIMUM_AGE));
        registrationService.register(new User(LOGIN_DASHA, CORRECT_PASSWORD, OVER_MINIMUM_AGE));
        assertTrue(people.contains(new User(LOGIN_YURI, CORRECT_PASSWORD, MINIMUM_AGE)));
        assertTrue(people.contains(new User(LOGIN_DASHA, CORRECT_PASSWORD, OVER_MINIMUM_AGE)));
        assertEquals(EXPECTED_TWO, people.size());
    }

    @Test
    void register_negativeAge_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(
                new User(CORRECT_LOGIN, CORRECT_PASSWORD, NEGATIVE_AGE)));
    }

    @Test
    void register_zeroAge_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(
                new User(CORRECT_LOGIN, CORRECT_PASSWORD, ZERO_AGE)));
    }

    @Test
    void register_belowMinimumAge_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(
                new User(CORRECT_LOGIN, CORRECT_PASSWORD, BELOW_MINIMUM_AGE)));
    }

    @Test
    void register_correctLogin_Ok() {
        registrationService.register(new User(LOGIN_YURI, CORRECT_PASSWORD, MINIMUM_AGE));
        registrationService.register(new User(LOGIN_DASHA, CORRECT_PASSWORD, MINIMUM_AGE));
        registrationService.register(new User(LOGIN_NAZAR, CORRECT_PASSWORD, MINIMUM_AGE));
        registrationService.register(new User(
                CORRECT_LOGIN_SIX_LETTERS, CORRECT_PASSWORD, MINIMUM_AGE));
        assertTrue(people.contains(new User(LOGIN_YURI, CORRECT_PASSWORD, MINIMUM_AGE)));
        assertTrue(people.contains(new User(LOGIN_DASHA, CORRECT_PASSWORD, MINIMUM_AGE)));
        assertTrue(people.contains(new User(LOGIN_NAZAR, CORRECT_PASSWORD, MINIMUM_AGE)));
        assertTrue(people.contains(new User(
                CORRECT_LOGIN_SIX_LETTERS, CORRECT_PASSWORD, MINIMUM_AGE)));
        assertEquals(EXPECTED_FOUR, people.size());
    }

    @Test
    void register_incorrectLogin_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(
                new User(INCORRECT_LOGIN, CORRECT_PASSWORD, MINIMUM_AGE)));
    }

    @Test
    void register_incorrectLoginEmptyLine_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(
                new User(EMPTY_LINE, CORRECT_PASSWORD, MINIMUM_AGE)));
    }

    @Test
    void register_correctPassword_Ok() {
        registrationService.register(new User(LOGIN_YURI, CORRECT_PASSWORD, MINIMUM_AGE));
        registrationService.register(new User(
                LOGIN_DASHA, CORRECT_PASSWORD_SIX_LETTERS, MINIMUM_AGE));
        assertTrue(people.contains(new User(LOGIN_YURI, CORRECT_PASSWORD, MINIMUM_AGE)));
        assertTrue(people.contains(new User(
                LOGIN_DASHA, CORRECT_PASSWORD_SIX_LETTERS, MINIMUM_AGE)));
        assertEquals(EXPECTED_TWO, people.size());
    }

    @Test
    void register_incorrectPassword_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(
                new User(CORRECT_LOGIN, INCORRECT_PASSWORD, MINIMUM_AGE)));
    }

    @Test
    void register_incorrectPasswordEmptyLine_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(
                new User(CORRECT_LOGIN, EMPTY_LINE, MINIMUM_AGE)));
    }

    @Test
    void register_userAlreadyExists_NotOk() {
        people.add(new User(LOGIN_DASHA, CORRECT_PASSWORD, MINIMUM_AGE));
        assertThrows(RegistrationException.class, () -> registrationService.register(
                new User(LOGIN_DASHA, CORRECT_PASSWORD, MINIMUM_AGE)));
    }

    @Test
    void register_addCorrectUser_Ok() {
        User actual = registrationService.register(new User(
                LOGIN_YURI,CORRECT_PASSWORD_SIX_LETTERS,MINIMUM_AGE));
        User expected = people.get(FIRST);
        assertEquals(actual,expected);
    }
}
