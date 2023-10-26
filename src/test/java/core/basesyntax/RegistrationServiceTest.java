package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    private User user;

    @BeforeAll
    public static void beforeAll() {
        registServImpl = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @AfterEach
    public void afterEach() {
        Storage.people.clear();
    }

    @Test
    public void register_nullUser_shouldThrowException() {
        user = null;
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(user),
                "Registration should throw an exception when the user is null.");
    }

    @Test
    public void register_nullPassword_shouldThrowException() {
        user.setLogin(OK_LOGIN_TEN_LENGTH);
        user.setAge(OK_AGE_TWENTY_TWO);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(user),
                "Registration should throw an exception when the password is null.");
    }

    @Test
    public void register_nullLogin_shouldThrowException() {
        user.setPassword(OK_PASSWORD_EIGHT_LENGTH);
        user.setLogin(null);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(user),
                "Registration should throw an exception when the login is null.");
    }

    @Test
    public void register_nullAge_shouldThrowException() {
        user.setPassword(OK_PASSWORD_EIGHT_LENGTH);
        user.setAge(null);
        user.setLogin(OK_LOGIN_TEN_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(user),
                "Registration should throw an exception when the age is null.");
    }

    @Test
    public void register_passwordLengthZero_shouldThrowException() {
        user.setPassword(NOT_OK_PASSWORD_ZERO_LENGTH);
        user.setAge(OK_AGE_TWENTY_TWO);
        user.setLogin(OK_LOGIN_TEN_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(user),
                "Registration should throw an exception when the password length is 0.");
    }

    @Test
    public void register_PasswordLengthThree_ShouldThrowRegistrationException() {
        user.setPassword(NOT_OK_PASSWORD_THREE_LENGTH);
        user.setAge(OK_AGE_TWENTY_TWO);
        user.setLogin(OK_LOGIN_TEN_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(user),
                "Registration should throw an exception when the password length is 3.");
    }

    @Test
    public void register_PasswordLengthFive_ShouldThrowRegistrationException() {
        user.setPassword(NOT_OK_PASSWORD_FIVE_LENGTH);
        user.setAge(OK_AGE_TWENTY_TWO);
        user.setLogin(OK_LOGIN_TEN_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(user),
                "Registration should throw an exception when the password length is 5.");
    }

    @Test
    public void register_PasswordLengthSix_Ok_edge_case() {
        user.setPassword(NOT_OK_PASSWORD_SIX_LENGTH);
        user.setAge(OK_AGE_TWENTY_TWO);
        user.setLogin(OK_LOGIN_TEN_LENGTH);
        User actual = registServImpl.register(user);
        assertEquals(user,
                actual,
                "Registration should succeed when the password length is 6.");
    }

    @Test
    public void register_PasswordLengthEight_Ok() {
        user.setPassword(OK_PASSWORD_EIGHT_LENGTH);
        user.setAge(OK_AGE_TWENTY_TWO);
        user.setLogin(OK_LOGIN_TEN_LENGTH);
        User actual = registServImpl.register(user);
        assertEquals(user,
                actual,
                "Registration should succeed when the password length is 8.");
    }

    @Test
    public void register_LoginLengthZero_ShouldThrowRegistrationException() {
        user.setPassword(OK_PASSWORD_TEN_LENGTH);
        user.setAge(OK_AGE_TWENTY_TWO);
        user.setLogin(NOT_OK_LOGIN_ZERO_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(user),
                "Registration should throw an exception when the login length is 0.");
    }

    @Test
    public void register_LoginLengthThree_ShouldThrowRegistrationException() {
        user.setPassword(OK_PASSWORD_TEN_LENGTH);
        user.setAge(OK_AGE_TWENTY_TWO);
        user.setLogin(NOT_OK_LOGIN_THREE_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(user),
                "Registration should throw an exception when the login length is 3.");
    }

    @Test
    public void register_LoginLengthFive_ShouldThrowRegistrationException() {
        user.setPassword(OK_PASSWORD_TEN_LENGTH);
        user.setAge(OK_AGE_TWENTY_TWO);
        user.setLogin(NOT_OK_LOGIN_FIVE_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(user),
                "Registration should throw an exception when the login length is 5.");
    }

    @Test
    public void register_LoginLengthSix_Ok_edge_case() {
        user.setPassword(OK_PASSWORD_TEN_LENGTH);
        user.setAge(OK_AGE_TWENTY_TWO);
        user.setLogin(NOT_OK_LOGIN_SIX_LENGTH);
        User actual = registServImpl.register(user);
        assertEquals(user,
                actual,
                "Registration should succeed when the login length is 6.");
    }

    @Test
    public void register_LoginLengthEight_Ok() {
        user.setPassword(OK_PASSWORD_TEN_LENGTH);
        user.setAge(OK_AGE_TWENTY_TWO);
        user.setLogin(OK_PASSWORD_EIGHT_LENGTH);
        User actual = registServImpl.register(user);
        assertEquals(user,
                actual,
                "Registration should succeed when the login length is 8.");
    }

    @Test
    public void register_user_is_already_registered_notOk() {
        user.setPassword(OK_PASSWORD_TEN_LENGTH);
        user.setAge(OK_AGE_TWENTY_TWO);
        user.setLogin(OK_PASSWORD_EIGHT_LENGTH);
        Storage.people.add(user);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(user),
                "Registration should throw an exception when the user is already registered.");
    }

    @Test
    public void register_ageZero_shouldThrowException() {
        user.setLogin(OK_LOGIN_TEN_LENGTH);
        user.setAge(NOT_OK_AGE_ZERO);
        user.setPassword(OK_PASSWORD_EIGHT_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(user),
                "Registration should throw an exception when the age is 0.");
    }

    @Test
    public void register_ageSeventeen_shouldThrowException() {
        user.setLogin(OK_LOGIN_TEN_LENGTH);
        user.setAge(NOT_OK_AGE_SEVENTEEN);
        user.setPassword(OK_PASSWORD_EIGHT_LENGTH);
        assertThrows(RegistrationException.class,
                () -> registServImpl.register(user),
                "Registration should throw an exception when the age is 17.");
    }

    @Test
    public void register_ageEighteen_Ok_edge_case() {
        user.setLogin(OK_LOGIN_TEN_LENGTH);
        user.setAge(OK_AGE_EIGHTEEN);
        user.setPassword(OK_PASSWORD_EIGHT_LENGTH);
        User actual = registServImpl.register(user);
        assertEquals(user,
                actual,
                "Registration should succeed when the age is 18.");
    }

    @Test
    public void register_ageOneHundredOk() {
        user.setLogin(OK_LOGIN_TEN_LENGTH);
        user.setAge(OK_AGE_HUNDRED);
        user.setPassword(OK_PASSWORD_EIGHT_LENGTH);
        User actual = registServImpl.register(user);
        assertEquals(user,
                actual,
                "Registration should succeed when the age is 100.");
    }
}
