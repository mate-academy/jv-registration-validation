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
    private static RegistrationService registrationService;
    private User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setAge(18);
        validUser.setLogin("valLog");
        validUser.setPassword("valPas");
    }

    @Test
    void test_register_validUser_ok() {
        User actualUser = registrationService.register(validUser);
        User expectUser = validUser;
        Assertions.assertEquals(expectUser, actualUser);
    }

    @Test
    void test_register_existUser_notOk() {
        String login = "ValidLogin";
        User sameLoginUser = new User();
        sameLoginUser.setLogin(login);
        validUser.setLogin(login);
        Storage.people.add(sameLoginUser);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(validUser),
                "There is shouldn't add a user with already exist login in the storage. Login: "
                + login);
    }

    @Test
    void test_register_nullUser_notOk() {
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(null),
                "There is shouldn't to pass null User");
    }

    @Test
    void test_register_nullLogin_notOk() {
        User nullLoginUser = validUser;
        nullLoginUser.setLogin(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(nullLoginUser),
                "There is shouldn't to pass null login");
    }

    @Test
    void test_register_shortLogin_notOk() {
        User shortLoginUser = validUser;
        String login = "abcde";
        shortLoginUser.setLogin(login);
        int actual = login.length();
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(shortLoginUser),
                "Login should be least " + MIN_LOGIN_LENGTH + "characters, but: "
                + actual);
    }

    @Test
    void test_register_nullPassword_notOk() {
        User nullPasswordUser = validUser;
        nullPasswordUser.setPassword(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(nullPasswordUser),
                "There is shouldn't to pass null password");
    }

    @Test
    void test_register_shortPassword_notOk() {
        User shortPasswordUser = validUser;
        String password = "12345";
        shortPasswordUser.setPassword(password);
        int actual = password.length();
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(shortPasswordUser),
                "Password should be least " + MIN_PASSWORD_LENGTH + "characters, but: "
                + actual);
    }

    @Test
    void test_register_nullAge_notOk() {
        User nullAgeUser = validUser;
        nullAgeUser.setAge(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(nullAgeUser),
                "There is shouldn't to pass null age");
    }

    @Test
    void test_register_youngAge_notOk() {
        int actual = 17;
        User youngUser = validUser;
        youngUser.setAge(actual);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(youngUser),
                "Age should be least " + MIN_USER_AGE + ", but: " + actual);
    }
}
