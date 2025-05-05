package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {

    private static final String VALID_LOGIN = "user12345";
    private static final String VALID_PASSWORD = "qwerty123";
    private static final int VALID_AGE = 20;
    private static final String SHORT_LOGIN = "user";
    private static final String SHORT_PASSWORD = "pass";
    private static final int NEGATIVE_AGE = -25;
    private static final int UNDERAGE = 17;

    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    public void register_validUser_isOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);

        User registeredUser = registrationService.register(user);

        assertEquals(user, registeredUser, "User registration failed");
        assertEquals(registeredUser, storageDao.get(registeredUser.getLogin()),
                "User not found in storage after registration");
    }

    @Test
    public void register_nullUser_notOk() {

        assertThrows(InvalidUserException.class, () ->
                registrationService.register(null), "Expected to "
                + "throw InvalidUserException for null user");
    }

    @Test
    public void register_nullLogin_notOk() {
        User user = createUser(null, VALID_PASSWORD, VALID_AGE);

        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Expected to throw "
                + "InvalidUserException for null login");
    }

    @Test
    public void register_shortLogin_notOk() {
        User user = createUser(SHORT_LOGIN, VALID_PASSWORD, VALID_AGE);

        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Expected to throw "
                + "InvalidUserException for short login");
    }

    @Test
    public void register_nullPassword_notOk() {
        User user = createUser(VALID_LOGIN, null, VALID_AGE);

        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Expected to throw "
                + "InvalidUserException for null password");
    }

    @Test
    public void register_shortPassword_notOk() {
        User user = createUser(VALID_LOGIN, SHORT_PASSWORD, VALID_AGE);

        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Expected to throw "
                + "InvalidUserException for short password");
    }

    @Test
    public void register_negativeAge_notOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, NEGATIVE_AGE);

        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Expected to throw "
                + "InvalidUserException for negative age");
    }

    @Test
    public void register_nullAge_notOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, null);

        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Expected to throw "
                + "InvalidUserException for null age");
    }

    @Test
    public void register_under18Age_notOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, UNDERAGE);

        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Expected to throw "
                + "InvalidUserException for under 18 age");
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
