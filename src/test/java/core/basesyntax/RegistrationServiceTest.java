package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationServiceTest {
    private static final String OK_LOGIN_TEN_LENGTH = "boroda4436";
    private static final String NOT_OK_LOGIN_ZERO_LENGTH = "";
    private static final String NOT_OK_LOGIN_THREE_LENGTH = "123";
    private static final String NOT_OK_LOGIN_FIVE_LENGTH = "12345";
    private static final String NOT_OK_LOGIN_SIX_LENGTH = "123456";
    private static final String OK_PASSWORD_TEN_LENGTH = "boroda4436";
    private static final String OK_PASSWORD_EIGHT_LENGTH = "12345678";
    private static final String NOT_OK_PASSWORD_ZERO_LENGTH = "";
    private static final String NOT_OK_PASSWORD_THREE_LENGTH = "123";
    private static final String NOT_OK_PASSWORD_FIVE_LENGTH = "12345";
    private static final String NOT_OK_PASSWORD_SIX_LENGTH = "123456";
    private static final int OK_AGE_TWENTY_TWO = 22;
    private static final int NOT_OK_AGE_ZERO = 0;
    private static final int NOT_OK_AGE_SEVENTEEN = 17;
    private static final int OK_AGE_EIGHTEEN = 18;
    private static final int OK_AGE_HUNDRED = 100;
    private static RegistrationService registServImpl;
    private User expected;

    @BeforeAll
    public static void beforeAll() {
        registServImpl = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        expected = new User();
    }

    @AfterEach
    public void afterEach() {
        Storage.people.clear();
    }

    @Test
    public void register_userNullValue_notOk() {
        expected = null;
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(expected),
                "Registration should throw an exception when the user is null.");
    }

    @Test
    public void register_passwordNullValue_notOk() {
        expected.setLogin(OK_LOGIN_TEN_LENGTH);
        expected.setAge(OK_AGE_TWENTY_TWO);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(expected),
                "Registration should throw an exception when the password is null.");
    }

    @Test
    public void register_loginNullValue_notOk() {
        expected.setPassword(OK_PASSWORD_EIGHT_LENGTH);
        expected.setLogin(null);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(expected),
                "Registration should throw an exception when the login is null.");
    }

    @Test
    public void register_ageNullValue_notOk() {
        expected.setPassword(OK_PASSWORD_EIGHT_LENGTH);
        expected.setAge(null);
        expected.setLogin(OK_LOGIN_TEN_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(expected),
                "Registration should throw an exception when the age is null.");
    }

    @Test
    public void register_0_password_length_notOk() {
        expected.setPassword(NOT_OK_PASSWORD_ZERO_LENGTH);
        expected.setAge(OK_AGE_TWENTY_TWO);
        expected.setLogin(OK_LOGIN_TEN_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(expected),
                "Registration should throw an exception when the password length is 0.");
    }

    @Test
    public void register_3_password_length_notOk() {
        expected.setPassword(NOT_OK_PASSWORD_THREE_LENGTH);
        expected.setAge(OK_AGE_TWENTY_TWO);
        expected.setLogin(OK_LOGIN_TEN_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(expected),
                "Registration should throw an exception when the password length is 3.");
    }

    @Test
    public void register_5_password_length_notOk() {
        expected.setPassword(NOT_OK_PASSWORD_FIVE_LENGTH);
        expected.setAge(OK_AGE_TWENTY_TWO);
        expected.setLogin(OK_LOGIN_TEN_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(expected),
                "Registration should throw an exception when the password length is 5.");
    }

    @Test
    public void register_6_password_length_Ok_edge_case() {
        expected.setPassword(NOT_OK_PASSWORD_SIX_LENGTH);
        expected.setAge(OK_AGE_TWENTY_TWO);
        expected.setLogin(OK_LOGIN_TEN_LENGTH);
        User actual = registServImpl.register(expected);
        assertEquals(expected,
                actual,
                "Registration should succeed when the password length is 6.");
    }

    @Test
    public void register_8_password_length_Ok() {
        expected.setPassword(OK_PASSWORD_EIGHT_LENGTH);
        expected.setAge(OK_AGE_TWENTY_TWO);
        expected.setLogin(OK_LOGIN_TEN_LENGTH);
        User actual = registServImpl.register(expected);
        assertEquals(expected,
                actual,
                "Registration should succeed when the password length is 8.");
    }

    @Test
    public void register_0_login_length_notOk() {
        expected.setPassword(OK_PASSWORD_TEN_LENGTH);
        expected.setAge(OK_AGE_TWENTY_TWO);
        expected.setLogin(NOT_OK_LOGIN_ZERO_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(expected),
                "Registration should throw an exception when the login length is 0.");
    }

    @Test
    public void register_3_login_length_notOk() {
        expected.setPassword(OK_PASSWORD_TEN_LENGTH);
        expected.setAge(OK_AGE_TWENTY_TWO);
        expected.setLogin(NOT_OK_LOGIN_THREE_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(expected),
                "Registration should throw an exception when the login length is 3.");
    }

    @Test
    public void register_5_login_length_notOk() {
        expected.setPassword(OK_PASSWORD_TEN_LENGTH);
        expected.setAge(OK_AGE_TWENTY_TWO);
        expected.setLogin(NOT_OK_LOGIN_FIVE_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(expected),
                "Registration should throw an exception when the login length is 5.");
    }

    @Test
    public void register_6_login_length_Ok_edge_case() {
        expected.setPassword(OK_PASSWORD_TEN_LENGTH);
        expected.setAge(OK_AGE_TWENTY_TWO);
        expected.setLogin(NOT_OK_LOGIN_SIX_LENGTH);
        User actual = registServImpl.register(expected);
        assertEquals(expected,
                actual,
                "Registration should succeed when the login length is 6.");
    }

    @Test
    public void register_8_login_length_Ok() {
        expected.setPassword(OK_PASSWORD_TEN_LENGTH);
        expected.setAge(OK_AGE_TWENTY_TWO);
        expected.setLogin(OK_PASSWORD_EIGHT_LENGTH);
        User actual = registServImpl.register(expected);
        assertEquals(expected,
                actual,
                "Registration should succeed when the login length is 8.");
    }

    @Test
    public void register_user_is_already_registered_notOk() {
        expected.setPassword(OK_PASSWORD_TEN_LENGTH);
        expected.setAge(OK_AGE_TWENTY_TWO);
        expected.setLogin(OK_PASSWORD_EIGHT_LENGTH);
        registServImpl.register(expected);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(expected),
                "Registration should throw an exception when the user is already registered.");
    }

    @Test
    public void register_0_age_notOk() {
        expected.setLogin(OK_LOGIN_TEN_LENGTH);
        expected.setAge(NOT_OK_AGE_ZERO);
        expected.setPassword(OK_PASSWORD_EIGHT_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(expected),
                "Registration should throw an exception when the age is 0.");
    }

    @Test
    public void register_17_age_notOk() {
        expected.setLogin(OK_LOGIN_TEN_LENGTH);
        expected.setAge(NOT_OK_AGE_SEVENTEEN);
        expected.setPassword(OK_PASSWORD_EIGHT_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(expected),
                "Registration should throw an exception when the age is 17.");
    }

    @Test
    public void register_18_age_Ok_edge_case() {
        expected.setLogin(OK_LOGIN_TEN_LENGTH);
        expected.setAge(OK_AGE_EIGHTEEN);
        expected.setPassword(OK_PASSWORD_EIGHT_LENGTH);
        User actual = registServImpl.register(expected);
        assertEquals(expected,
                actual,
                "Registration should succeed when the age is 18.");
    }

    @Test
    public void register_100_age_Ok() {
        expected.setLogin(OK_LOGIN_TEN_LENGTH);
        expected.setAge(OK_AGE_HUNDRED);
        expected.setPassword(OK_PASSWORD_EIGHT_LENGTH);
        User actual = registServImpl.register(expected);
        assertEquals(expected,
                actual,
                "Registration should succeed when the age is 100.");
    }

    @Test
    public void user_equals_users_Ok() {
        User user1 = new User();
        user1.setLogin(OK_LOGIN_TEN_LENGTH);
        user1.setAge(OK_AGE_TWENTY_TWO);
        user1.setPassword(OK_PASSWORD_EIGHT_LENGTH);
        User user2 = new User();
        user2.setLogin(OK_LOGIN_TEN_LENGTH);
        user2.setAge(OK_AGE_TWENTY_TWO);
        user2.setPassword(OK_PASSWORD_EIGHT_LENGTH);
        assertTrue(user1.equals(user2),
                "User objects with the same attributes should be equal.");
    }
}
