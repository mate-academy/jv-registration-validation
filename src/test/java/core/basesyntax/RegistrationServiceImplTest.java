package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final RegistrationService REGISTRATION = new RegistrationServiceImpl();
    private static final String EMPTY = "";
    private static final int CORRECT_AGE = 18;
    private static final int INCORRECT_AGE = 16;
    private static final String INCORRECT_LOGIN_WITH_WHITESPACE = "Log in";
    private static final String CORRECT_PASSWORD = "passWord6";
    private static final String INCORRECT_PASSWORD_SHORT = "short";
    private static final String INCORRECT_PASSWORD_WITH_WHITESPACE = "pass Word6";
    private static final String INCORRECT_PASSWORD_WITHOUT_DIGIT = "passWord";
    private static final String INCORRECT_PASSWORD_WITHOUT_UPPERCASE = "password6";

    private static final User FIRST_USER = new User();
    private static final User SECOND_USER = new User();

    @BeforeEach
    void setUp() {
        Storage.people.clear();

        FIRST_USER.setLogin("BestLogin");
        FIRST_USER.setPassword(CORRECT_PASSWORD);
        FIRST_USER.setAge(CORRECT_AGE);

        SECOND_USER.setLogin("GoodLogin");
        SECOND_USER.setPassword(CORRECT_PASSWORD);
        SECOND_USER.setAge(CORRECT_AGE);
    }

    @Test
    void register_additionUsers_ok() {
        boolean expect = FIRST_USER.equals(REGISTRATION.register(FIRST_USER));
        assertTrue(expect);
        expect = SECOND_USER.equals(REGISTRATION.register(SECOND_USER));
        assertTrue(expect);
    }

    @Test
    void register_checkIdIncrease_ok() {
        REGISTRATION.register(FIRST_USER);
        REGISTRATION.register(SECOND_USER);
        assertEquals(FIRST_USER.getId() + 1, (long) SECOND_USER.getId());
    }

    @Test
    void register_loginIsEmpty_notOk() {
        FIRST_USER.setLogin(EMPTY);
        assertThrows(RuntimeException.class,
                () -> REGISTRATION.register(FIRST_USER));
    }

    @Test
    void register_loginContainsWhitespaces_notOk() {
        FIRST_USER.setLogin(INCORRECT_LOGIN_WITH_WHITESPACE);
        assertThrows(RuntimeException.class,
                () -> REGISTRATION.register(FIRST_USER));
    }

    @Test
    void register_loginIsNull_notOk() {
        FIRST_USER.setLogin(null);
        assertThrows(NullPointerException.class,
                () -> REGISTRATION.register(FIRST_USER));
    }

    @Test
    void register_checkPasswordOnDigit_notOk() {
        FIRST_USER.setPassword(INCORRECT_PASSWORD_WITHOUT_DIGIT);
        assertThrows(RuntimeException.class,
                () -> REGISTRATION.register(FIRST_USER));
    }

    @Test
    void register_checkPasswordOnWhitespace_notOk() {
        FIRST_USER.setPassword(INCORRECT_PASSWORD_WITH_WHITESPACE);
        assertThrows(RuntimeException.class,
                () -> REGISTRATION.register(FIRST_USER));
    }

    @Test
    void register_checkPasswordOnUppercaseLetter_notOk() {
        FIRST_USER.setPassword(INCORRECT_PASSWORD_WITHOUT_UPPERCASE);
        assertThrows(RuntimeException.class,
                () -> REGISTRATION.register(FIRST_USER));
    }

    @Test
    void register_checkPasswordLength_notOk() {
        FIRST_USER.setPassword(INCORRECT_PASSWORD_SHORT);
        assertThrows(RuntimeException.class,
                () -> REGISTRATION.register(FIRST_USER));
    }

    @Test
    void register_passwordIsNull_notOk() {
        FIRST_USER.setPassword(null);
        assertThrows(NullPointerException.class,
                () -> REGISTRATION.register(FIRST_USER));
    }

    @Test
    void register_rangeOfAge_notOk() {
        FIRST_USER.setAge(INCORRECT_AGE);
        assertThrows(RuntimeException.class,
                () -> REGISTRATION.register(FIRST_USER));
    }

    @Test
    void register_ageIsNull_notOk() {
        FIRST_USER.setAge(null);
        assertThrows(NullPointerException.class,
                () -> REGISTRATION.register(FIRST_USER));
    }

    @Test
    void register_addingExistUser_notOk() {
        REGISTRATION.register(FIRST_USER);
        assertThrows(RuntimeException.class,
                () -> REGISTRATION.register(FIRST_USER));
    }
}
