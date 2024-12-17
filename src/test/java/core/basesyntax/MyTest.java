package core.basesyntax;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Feel free to remove this class and create your own.
 */
public class MyTest {
    private User user;
    private String login;
    private int loginLength;
    private String password;
    private int age;
    private int passwordLength;

    @BeforeEach
    void setUp() {
        user = new User();
        login = user.getLogin();
        password = user.getPassword();
        age = user.getAge();
        loginLength = user.getLogin().length();
        passwordLength = user.getPassword().length();
    }

    @Test
    void checkNotNullUser() {
        if (user == null) {
            throw new MyUncheckedException("User is null");
        }
    }

    @Test
    void loginIsNotNull() {
        assertNotNull(login);
    }

    @Test
    void loginLengthIsNotZero() {
        if (loginLength == 0) {
            throw new MyUncheckedException("Login is invalid");
        }
    }

    @Test
    void loginLengthIsMoreThan_6() {
        if (loginLength < 6) {
            throw new MyUncheckedException("Login should be bigger");
        }
    }

    @Test
    void passwordLengthIsNotZero() {
        if (passwordLength == 0) {
            throw new MyUncheckedException("Password is invalid");
        }
    }

    @Test
    void passwordIsNotNull() {
        assertNotNull(password);
    }

    @Test
    void passwordLengthIsMoreThan_6() {
        if (passwordLength < 6) {
            throw new MyUncheckedException("Password should be bigger");
        }
    }

    @Test
    void checkAgeOver_0() {
        assertTrue(age >= 0, "Age is invalid!");
    }

    @Test
    void userAgeIsUnder_18() {
        if (age < 18) {
            throw new MyUncheckedException("You are too young");
        }
    }
}
