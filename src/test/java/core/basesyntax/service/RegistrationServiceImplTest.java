package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    public static final Long DEF_ID = 19L;
    public static final String DEF_LOGIN = "asdf@gmail.com";
    public static final String DEF_PASSWORD = "qweasd";
    public static final Integer DEF_AGE = 18;
    static User expected;
    static RegistrationServiceImpl registrationService;
    User actual;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        expected = new User(DEF_ID, DEF_LOGIN, DEF_PASSWORD, DEF_AGE);
        User userExist = new User(1234L, "asdasd@gmail.com", "1234567", 65);
        registrationService.register(userExist);
    }

    @Test
    void dataIsTrue_Ok() {
        actual = new User(DEF_ID, DEF_LOGIN, DEF_PASSWORD, DEF_AGE);
        assertEquals(expected, actual);
    }

    @Test
    void isNull_NotOK() {
        actual = new User(DEF_ID, null, DEF_PASSWORD, DEF_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void ageTooSmall_NotOk() {
        actual = new User(DEF_ID, DEF_LOGIN, DEF_PASSWORD, 17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void ageTooBig_NotOK() {
        actual = new User(DEF_ID, DEF_LOGIN, DEF_PASSWORD, 101);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void passwordTooSmall_NotOk() {
        actual = new User(DEF_ID, DEF_LOGIN, "qweas", DEF_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void passwordTooBig_NotOK() {
        actual = new User(DEF_ID, DEF_LOGIN, "qweasdzxcrtyfghvbnu", DEF_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void loginExist_NotOK() {
        actual = new User(DEF_ID, "asdasd@gmail.com", DEF_PASSWORD, DEF_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(actual);
        });
    }
}
