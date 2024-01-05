package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.AuthenticationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void justAddingFitUsers_Ok() {
        registrationService.register(new User("vlad222r", "afsgggwa", 25));
        registrationService.register(new User("bob222", "fjjsafj", 18));
        registrationService.register(new User("alice222", "viadkc", 65));
    }

    @Test
    void checkNullUser_notOk() {
        assertThrows(AuthenticationException.class,() -> {
            registrationService.register(null);
        });
    }

    @Test
    void loginIsNull_notOk() {
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(new User(null, "abcdred", 18));
        });
    }

    @Test
    void passwordIsNull_notOk() {
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(new User("vlad777r", null, 25));
        });
    }

    @Test
    void ageIsNull_notOk() {
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(new User("vlad777r@gmail.com", "abcdred", null));
        });
    }

    @Test
    void notEnglishLogin_notOk() {
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(new User("влад_25997", "afffsg", 26));
        });
    }

    @Test
    void notEnglishPassword_notOk() {
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(new User("vlad777r", "влад777ff", 30));
        });
    }

    @Test
    void emptyLogin_notOk() {
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(new User("", "fstttadc", 45));
        });
    }

    @Test
    void emptyPassword_notOk() {
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(new User("vlad777r", "", 45));
        });
    }

    @Test
    void addTheSameUser_notOk() {
        User bob = new User("bobBobenko", "bobenko27bobenko", 27);
        registrationService.register(bob);
        User bobCopy = new User("bobBobenko", "bobenko27bobenko", 27);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(bobCopy);
        });
    }

    @Test
    void getShortLogin_notOk() {
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(new User("VBond", "abschg", 25));
        });
    }

    @Test
    void getShortPassword_notOk() {
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(new User("VdBondarev", "vlad", 25));
        });
    }

    @Test
    void ageIsLessThan0_notOk() {
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(new User("VBondarev", "vfaskfkk", -100));
        });
    }

    @Test
    void ageIsBiggerThanAllowed_notOk() {
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(new User("VBondarev", "vfaskfkk", 150));
        });
    }
}
