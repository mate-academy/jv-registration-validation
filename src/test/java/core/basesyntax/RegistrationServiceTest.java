package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exeption.UserNullException;
import core.basesyntax.exeption.age.InvalidAgeException;
import core.basesyntax.exeption.login.InvalidLoginCharacterException;
import core.basesyntax.exeption.login.InvalidLoginLengthException;
import core.basesyntax.exeption.login.LoginAlreadyExistsException;
import core.basesyntax.exeption.login.NullLoginException;
import core.basesyntax.exeption.password.InvalidPasswordCharacterException;
import core.basesyntax.exeption.password.InvalidPasswordLengthException;
import core.basesyntax.exeption.password.NullPasswordException;
import core.basesyntax.exeption.password.SequentialPatternException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final String VALID_PASSWORD = "mAtE3sT7_&%$a8";
    private static final String VALID_LOGIN = "Bob1985";
    private static final int VALID_USER_AGE = 65;
    private static final int BELOW_THRESHOLD_AGE = 15;
    private static final int ABOVE_THRESHOLD_AGE = 150;
    private static final String LOGIN_WITH_INCORRECT_LENGTH = "bob75superBOB135bestOF7the7BEST";
    private static final String PASSWORD_WITH_INCORRECT_LENGTH = "1";
    private static final String LOGIN_WITH_ILLEGAL_CHARACTERS = "%Big$$$Super->Boss";
    private static final String PASSWORD_WITH_ILLEGAL_CHARACTERS = "太阳天空地球海森林海洋";
    private static final String PASSWORD_WITH_INCREASING_SYMBOLS = "safari1234dog";
    private static final String PASSWORD_WITH_DECREASING_SYMBOLS = "bibaqponboba";
    private static final String PASSWORD_WITH_REPEATED_CHARACTERS = "dog$$$$cat";
    private static final String DUPLICATE_LOGIN = "bestOfTheBest";
    private static final RegistrationServiceImpl service = new RegistrationServiceImpl();
    private final User userWithValidParameters = new User();

    @BeforeAll
    public static void addValidUser() {
        User user = new User();
        user.setAge(VALID_USER_AGE);
        user.setLogin(DUPLICATE_LOGIN);
        user.setPassword(VALID_PASSWORD);
        service.register(user);

    }

    @BeforeEach
    public void initializingValidUserFields() {
        userWithValidParameters.setPassword(VALID_PASSWORD);
        userWithValidParameters.setLogin(VALID_LOGIN);
        userWithValidParameters.setAge(VALID_USER_AGE);
    }

    @Test
    public void register_userNullExceptionThrown() {
        User user = null;
        assertThrows(UserNullException.class, () -> service.register(user));
    }

    @Test
    public void register_invalidAgeExceptionThrown_lowerLimit() {
        userWithValidParameters.setAge(BELOW_THRESHOLD_AGE);
        assertThrows(InvalidAgeException.class,
                () -> service.register(userWithValidParameters));
    }

    @Test
    public void register_invalidAgeExceptionThrown_upperLimit() {
        userWithValidParameters.setAge(ABOVE_THRESHOLD_AGE);
        assertThrows(InvalidAgeException.class,
                () -> service.register(userWithValidParameters));
    }

    @Test
    public void register_nullLoginExceptionThrown() {
        userWithValidParameters.setLogin(null);
        assertThrows(NullLoginException.class,
                () -> service.register(userWithValidParameters));
    }

    @Test
    public void register_invalidLoginLengthExceptionThrown() {
        userWithValidParameters.setLogin(LOGIN_WITH_INCORRECT_LENGTH);
        assertThrows(InvalidLoginLengthException.class,
                () -> service.register(userWithValidParameters));
    }

    @Test
    public void register_loginAlreadyExistsExceptionThrown() {
        userWithValidParameters.setLogin(DUPLICATE_LOGIN);
        assertThrows(LoginAlreadyExistsException.class,
                () -> service.register(userWithValidParameters));
    }

    @Test
    public void register_invalidLoginCharacterExceptionThrown() {
        userWithValidParameters.setLogin(LOGIN_WITH_ILLEGAL_CHARACTERS);
        assertThrows(InvalidLoginCharacterException.class,
                () -> service.register(userWithValidParameters));
    }

    @Test
    public void register_nullPasswordExceptionThrown() {
        userWithValidParameters.setPassword(null);
        assertThrows(NullPasswordException.class,
                () -> service.register(userWithValidParameters));
    }

    @Test
    public void register_invalidPasswordLengthExceptionThrown() {
        userWithValidParameters.setPassword(PASSWORD_WITH_INCORRECT_LENGTH);
        assertThrows(InvalidPasswordLengthException.class,
                () -> service.register(userWithValidParameters));
    }

    @Test
    public void register_invalidPasswordCharacterExceptionThrown() {
        userWithValidParameters.setPassword(PASSWORD_WITH_ILLEGAL_CHARACTERS);
        assertThrows(InvalidPasswordCharacterException.class,
                () -> service.register(userWithValidParameters));
    }

    @Test
    public void register_sequentialPatternExceptionThrown_IncreasingSymbols() {
        userWithValidParameters.setPassword(PASSWORD_WITH_INCREASING_SYMBOLS);
        assertThrows(SequentialPatternException.class,
                () -> service.register(userWithValidParameters));
    }

    @Test
    public void register_sequentialPatternExceptionThrown_DecreaseSequentially() {
        userWithValidParameters.setPassword(PASSWORD_WITH_DECREASING_SYMBOLS);
        assertThrows(SequentialPatternException.class,
                () -> service.register(userWithValidParameters));
    }

    @Test
    public void register_sequentialPatternExceptionThrown_RepeatedCharacters() {
        userWithValidParameters.setPassword(PASSWORD_WITH_REPEATED_CHARACTERS);
        assertThrows(SequentialPatternException.class,
                () -> service.register(userWithValidParameters));
    }
}
