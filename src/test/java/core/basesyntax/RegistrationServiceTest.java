package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {

    private static final String VALID_LOGIN = "user12345";
    private static final String VALID_PASSWORD = "qwerty123";
    private static final int VALID_AGE = 20;

    private static final String NULL_LOGIN = null;
    private static final String SHORT_LOGIN = "user";
    private static final String NULL_PASSWORD = null;
    private static final String SHORT_PASSWORD = "pass";
    private static final int NEGATIVE_AGE = -25;
    private static final Integer NULL_AGE = null;
    private static final int UNDERAGE = 17;

    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    public void register_validUser_registersSuccessfully() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        String failureMessage = "User registration failed";

        User registeredUser = registrationService.register(user);

        Assertions.assertEquals(user, registeredUser, failureMessage);
        Assertions.assertEquals(registeredUser, storageDao.get(registeredUser.getLogin()),
                "User not found in storage after registration");
    }

    @Test
    public void register_nullUser_throwsInvalidUserException() {
        String failureMessage = "Expected to throw InvalidUserException for null user";
        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(null), failureMessage);
    }

    @Test
    public void register_nullLogin_throwsInvalidUserException() {
        User user = createUser(null, VALID_PASSWORD, VALID_AGE);
        String failureMessage = "Expected to throw InvalidUserException for null login";

        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), failureMessage);
    }

    @Test
    public void register_shortLogin_throwsInvalidUserException() {
        User user = createUser(SHORT_LOGIN, VALID_PASSWORD, VALID_AGE);
        String failureMessage = "Expected to throw InvalidUserException for short login";

        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), failureMessage);
    }

    @Test
    public void register_nullPassword_throwsInvalidUserException() {
        User user = createUser(VALID_LOGIN, null, VALID_AGE);
        String failureMessage = "Expected to throw InvalidUserException for null password";

        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), failureMessage);
    }

    @Test
    public void register_shortPassword_throwsInvalidUserException() {
        User user = createUser(VALID_LOGIN, SHORT_PASSWORD, VALID_AGE);
        String failureMessage = "Expected to throw InvalidUserException for short password";

        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), failureMessage);
    }

    @Test
    public void register_negativeAge_throwsInvalidUserException() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, NEGATIVE_AGE);
        String failureMessage = "Expected to throw InvalidUserException for negative age";

        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), failureMessage);
    }

    @Test
    public void register_nullAge_throwsInvalidUserException() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, null);
        String failureMessage = "Expected to throw InvalidUserException for null age";

        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), failureMessage);
    }

    @Test
    public void register_under18Age_throwsInvalidUserException() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, UNDERAGE);
        String failureMessage = "Expected to throw InvalidUserException for under 18 age";

        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), failureMessage);
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
