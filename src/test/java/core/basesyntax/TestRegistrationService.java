package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class TestRegistrationService {
    private static final String DEF_LOGIN = "qweqwe";
    private static final String DEF_PASS = "qweqwe";
    private static final int DEF_AGE = 18;

    private static final String SHORT_PASS = "qweqw";
    private static final String SHORT_LOG = "qweqw";

    private static final int LOW_AGE = 17;
    private static final int MINUS_AGE = -1;

    private User correctUser;
    private User nullUser;
    private User nullLogUser;
    private User nullPassUser;
    private User nullAgeUser;
    private User shortLogUser;
    private User shortPassUser;
    private User lowAgeUser;
    private RegistrationService registrationService;

    private static User createUser(String login, String password, Integer age) {
        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setAge(age);
        return newUser;
    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        correctUser = createUser(DEF_LOGIN, DEF_PASS, DEF_AGE);
        nullUser = null;
        nullLogUser = createUser(null, DEF_PASS, DEF_AGE);
        nullPassUser = createUser(DEF_LOGIN, null, DEF_AGE);
        nullAgeUser = createUser(DEF_LOGIN, DEF_PASS, null);
        shortLogUser = createUser(DEF_LOGIN, SHORT_PASS, DEF_AGE);
        shortPassUser = createUser(SHORT_LOG, DEF_PASS, DEF_AGE);
        lowAgeUser = createUser(DEF_LOGIN, DEF_PASS, LOW_AGE);
    }

    @Test
    void register_User_Ok() {
        User actual = registrationService.register(correctUser);
        User expected = correctUser;
        assertEquals(actual.getLogin(), expected.getLogin());
        assertEquals(actual.getPassword(), expected.getPassword());
        assertEquals(actual.getAge(), expected.getAge());
        assertNotNull(actual.getId());
    }

    @Test
    void register_SameLogin_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(correctUser));
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(nullUser));
    }

    @Test
    void register_NullFieldsUser_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(nullLogUser));
        assertThrows(RegistrationException.class, () -> registrationService.register(nullPassUser));
        assertThrows(RegistrationException.class, () -> registrationService.register(nullAgeUser));
    }

    @Test
    void register_ShortLogUser_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(shortLogUser));
    }

    @Test
    void register_ShortPassUser_NotOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(shortPassUser));
    }

    @Test
    void register_LowAgeUser_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(lowAgeUser));
    }
}
