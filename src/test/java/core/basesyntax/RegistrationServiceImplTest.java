package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int DEFAULT_AGE = 18;
    private static final int CHRIST_AGE = 33;
    private static final int LOW_AGE = 15;
    private static final int NEGATIVE_AGE = -1;
    private static final int ALTERNATIVE_AGE = 32;
    private static final String DEFAULT_PASS = "pas5word";
    private static final String SHORT_PASS = "5hort";
    private static final String ALTERNATIVE_PASS = "Pa5sw0rd";
    private static final String DEFAULT_LOGIN = "log1nLogin";
    private static final String SHORT_LOGIN = "short";
    private static RegistrationService registrationService;
    private User defaultValidUser;

    @BeforeAll
    static void createNewRegistrationServiceImpl() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        defaultValidUser = new User(DEFAULT_LOGIN, DEFAULT_PASS, DEFAULT_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_christAgeUser_ok() {
        User actual = defaultValidUser;
        actual.setAge(CHRIST_AGE);
        assertEquals(defaultValidUser, actual);
    }

    @Test
     void register_nullAgeUser_notOk() {
        defaultValidUser.setAge(null);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(defaultValidUser);
        }, "Expected " + InvalidUserException.class.getName() + " for null age!");
    }

    @Test
    void register_lowAgeUser_notOk() {
        defaultValidUser.setAge(LOW_AGE);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(defaultValidUser);
        }, "Expected " + InvalidUserException.class.getName() + " for low age!");
    }

    @Test
    void register_negativeAgeUser_notOk() {
        defaultValidUser.setAge(NEGATIVE_AGE);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(defaultValidUser);
        }, "Expected " + InvalidUserException.class.getName() + " for negative age!");
    }

    @Test
    void register_defaultValidUser_ok() {
        User actual = registrationService.register(defaultValidUser);
        assertEquals(defaultValidUser, actual);
    }

    @Test
    void register_alreadyExistLoginUser_notOk() {
        User actual = registrationService.register(defaultValidUser);
        assertEquals(defaultValidUser, actual);
        User clone = new User(actual.getLogin(), ALTERNATIVE_PASS, ALTERNATIVE_AGE);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(clone);
        }, "Expected " + InvalidUserException.class.getName() + "for already exist login!");
    }

    @Test
    void register_nullUser_notOk() {
        User actual = null;
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(actual);
        }, "Expected " + InvalidUserException.class + " for null user!");
    }

    @Test
    void register_nullLoginUser_notOk() {
        defaultValidUser.setLogin(null);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(defaultValidUser);
        }, "Expected " + InvalidUserException.class.getName() + " for null login!");
    }

    @Test
    void register_shortLoginUser_notOk() {
        defaultValidUser.setLogin(SHORT_LOGIN);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(defaultValidUser);
        }, "Expected " + InvalidUserException.class.getName() + " for short login!");
    }

    @Test
    void register_nullPassUser_notOk() {
        defaultValidUser.setPassword(null);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(defaultValidUser);
        }, "Expected " + InvalidUserException.class.getName() + " for null password!");
    }

    @Test
    void register_shortPassUser_notOk() {
        defaultValidUser.setPassword(SHORT_PASS);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(defaultValidUser);
        }, "Expected " + InvalidUserException.class.getName() + " for short password!");
    }
}
