package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private static final String VALID_EDGE_LOGIN = "valLog";
    private static final String VALID_EDGE_PASSWORD = "valPas";
    private static final int VALID_EDGE_AGE = 18;
    private static final String TOO_SHORT_LOGIN = "abcde";
    private static final String TOO_SHORT_PASSWORD = "12345";
    private static final int TOO_YOUNG_AGE = 17;
    private static RegistrationService registrationService;
    private User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setAge(VALID_EDGE_AGE);
        validUser.setLogin(VALID_EDGE_LOGIN);
        validUser.setPassword(VALID_EDGE_PASSWORD);
    }

    @Test
    void register_validUser_ok() {
        User actualUser = registrationService.register(validUser);
        User expectUser = validUser;
        Assertions.assertEquals(expectUser, actualUser);
    }

    @Test
    void register_existUser_notOk() {
        User sameLoginUser = new User();
        sameLoginUser.setLogin(VALID_EDGE_LOGIN);
        validUser.setLogin(VALID_EDGE_LOGIN);
        Storage.people.add(sameLoginUser);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(validUser),
                "There is shouldn't add a user with already exist login in the storage. Login: "
                + VALID_EDGE_LOGIN);
    }

    @Test
    void register_nullUser_notOk() {
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(null),
                "There is shouldn't to pass null User");
    }

    @Test
    void register_nullLogin_notOk() {
        User nullLoginUser = validUser;
        nullLoginUser.setLogin(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(nullLoginUser),
                "There is shouldn't to pass null login");
    }

    @Test
    void register_shortLogin_notOk() {
        User shortLoginUser = validUser;
        shortLoginUser.setLogin(TOO_SHORT_LOGIN);
        int actual = TOO_SHORT_LOGIN.length();
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(shortLoginUser),
                "Login should be least " + MIN_LOGIN_LENGTH + "characters, but: "
                + actual);
    }

    @Test
    void register_nullPassword_notOk() {
        User nullPasswordUser = validUser;
        nullPasswordUser.setPassword(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(nullPasswordUser),
                "There is shouldn't to pass null password");
    }

    @Test
    void register_shortPassword_notOk() {
        User shortPasswordUser = validUser;
        shortPasswordUser.setPassword(TOO_SHORT_PASSWORD);
        int actual = TOO_SHORT_PASSWORD.length();
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(shortPasswordUser),
                "Password should be least " + MIN_PASSWORD_LENGTH + "characters, but: "
                + actual);
    }

    @Test
    void register_nullAge_notOk() {
        User nullAgeUser = validUser;
        nullAgeUser.setAge(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(nullAgeUser),
                "There is shouldn't to pass null age");
    }

    @Test
    void register_youngAge_notOk() {
        User youngUser = validUser;
        youngUser.setAge(TOO_YOUNG_AGE);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(youngUser),
                "Age should be least " + MIN_USER_AGE + ", but: " + TOO_YOUNG_AGE);
    }
}
