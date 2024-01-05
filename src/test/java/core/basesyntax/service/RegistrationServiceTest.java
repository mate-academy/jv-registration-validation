package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.fail;

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
        try {
            registrationService.register(new User("vlad222r", "afsgggwa", 25));
            registrationService.register(new User("bob222", "fjjsafj", 18));
            registrationService.register(new User("alice222", "viadkc", 65));
        } catch (AuthenticationException e) {
            fail("Can't add user");
        }
    }

    @Test
    void checkNullUser_notOk() {
        try {
            registrationService.register(null);
        } catch (AuthenticationException e) {
            return;
        }
        fail("User is not made");
    }

    @Test
    void loginIsNull_notOk() {
        try {
            registrationService.register(new User(null, "abcdred", 18));
        } catch (AuthenticationException e) {
            return;
        }
        fail("User should filled login");
    }

    @Test
    void passwordIsNull_notOk() {
        try {
            registrationService.register(new User("vlad777r", null, 25));
        } catch (AuthenticationException e) {
            return;
        }
        fail("User should filled password");
    }

    @Test
    void ageIsNull_notOk() {
        try {
            registrationService.register(new User("vlad777r@gmail.com", "abcdred", null));
        } catch (AuthenticationException e) {
            return;
        }
        fail("User should filled age");
    }

    @Test
    void notEnglishLogin_notOk() {
        try {
            registrationService.register(new User("влад_25997", "afffsg", 26));
        } catch (AuthenticationException e) {
            return;
        }
        fail("Login should have english letters only");
    }

    @Test
    void notEnglishPassword_notOk() {
        try {
            registrationService.register(new User("vlad777r", "влад777ff", 30));
        } catch (AuthenticationException e) {
            return;
        }
        fail("Password should have english letters only");
    }

    @Test
    void emptyLogin_notOk() {
        try {
            registrationService.register(new User("", "fstttadc", 45));
        } catch (AuthenticationException e) {
            return;
        }
        fail("login can't be empty");
    }

    @Test
    void emptyPassword_notOk() {
        try {
            registrationService.register(new User("vlad777r", "", 45));
        } catch (AuthenticationException e) {
            return;
        }
        fail("password can't be empty");
    }

    @Test
    void addTheSameUser_notOk() {
        try {
            User bob = new User("bobBobenko", "bobenko27bobenko", 27);
            registrationService.register(bob);
            User bobCopy = new User("bobBobenko", "bobenko27bobenko", 27);
            registrationService.register(bobCopy);
        } catch (AuthenticationException e) {
            return;
        }
        fail("User already exists");
    }

    @Test
    void getShortLogin_notOk() {
        try {
            registrationService.register(new User("VBond", "abschg", 25));
        } catch (AuthenticationException e) {
            return;
        }
        fail("User's login should contain more than 5 letters");
    }

    @Test
    void getShortPassword_notOk() {
        try {
            registrationService.register(new User("VdBondarev", "vlad", 25));
        } catch (AuthenticationException e) {
            return;
        }
        fail("User's password should contain more than 5 letters");
    }

    @Test
    void ageIsLessThan0_notOk() {
        try {
            registrationService.register(new User("VBondarev", "vfaskfkk", -100));
        } catch (AuthenticationException e) {
            return;
        }
        fail("User's age can't be 0 or less");
    }

    @Test
    void ageIsBiggerThanAllowed_notOk() {
        try {
            registrationService.register(new User("VBondarev", "vfaskfkk", 150));
        } catch (AuthenticationException e) {
            return;
        }
        fail("User's age can't be 100 or bigger");
    }
}
