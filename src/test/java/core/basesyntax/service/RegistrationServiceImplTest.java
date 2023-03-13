package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.InvalidUserException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int DEFAULT_AGE = 28;
    private static final String DEFAULT_LOGIN = "Ivan Ivanov";
    private static final String DEFAULT_PASSWORD = "123TT_nn123";
    private static RegistrationService service;
    private User defaultValidUser;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        defaultValidUser = new User();
        defaultValidUser.setAge(DEFAULT_AGE);
        defaultValidUser.setLogin(DEFAULT_LOGIN);
        defaultValidUser.setPassword(DEFAULT_PASSWORD);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_christAgeUser_ok() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User actual = registrationService.register(defaultValidUser);
        assertEquals(defaultValidUser, actual);
    }

    @Test
    void register_nullAgeUser_notOk() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User actual = defaultValidUser;
        actual.setAge(null); //для замены
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(actual);
        }, "Expected " + InvalidUserException.class + " age user! = null");
    }

    @Test
    void register_lowAgeUser_notOk() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User actual = defaultValidUser;
        actual.setAge(2); //для замены
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(actual);
        }, "Expected " + InvalidUserException.class + " a little age user!");
    }

    @Test
    void register_negativeAgeUser_notOk() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User actual = defaultValidUser;
        actual.setAge(-12); //для замены
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(actual);
        }, "Expected " + InvalidUserException.class + " negative age user!");
    }

    @Test
    void register_alreadyExistLoginUser_notOk() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User actual = defaultValidUser;
        actual.setLogin("Ivan Ivanov"); //для замены
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(actual);
            registrationService.register(actual);
        }, "Expected " + InvalidUserException.class + " twice login user!");
    }

    @Test
    void register_nullLoginUser_notOk() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User actual = defaultValidUser;
        actual.setLogin(null); //для замены
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(actual);
        }, "Expected " + InvalidUserException.class + " null login user!");
    }

    @Test
    void register_shortLoginUser_notOk() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User actual = defaultValidUser;
        actual.setLogin("i"); //для замены
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(actual);
        }, "Expected " + InvalidUserException.class + " short login user!");
    }

    @Test
    void register_nullPassUser_notOk() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User actual = defaultValidUser;
        actual.setPassword(null); //для замены
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(actual);
        }, "Expected " + InvalidUserException.class + " null Password user!");
    }

    @Test
    void register_shortPassUser_notOk() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User actual = defaultValidUser;
        actual.setPassword("ab"); //для замены
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(actual);
        }, "Expected " + InvalidUserException.class + " short Password user!");
    }

    @Test
    void register_defaultValidUser_ok() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User actual = registrationService.register(defaultValidUser);
        assertEquals(defaultValidUser, actual);
    }

    @Test
    void register_nullUser_notOk() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User actual = null;
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(actual);
        }, "Expected " + InvalidUserException.class + " for null user!");
    }
}