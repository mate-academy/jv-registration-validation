package core.basesyntax;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void register_userNotNull_ok() {
        assertNotNull(user, "User is invalid!");
    }

    @Test
    void register_nullLogin_notOk() {
        assertNotNull(login, "Login should not be null");
    }

    @Test
    void login_length_notZero_check() {
        assertThrows(MyUncheckedException.class, () -> {
            if (loginLength < 6) {
                throw new MyUncheckedException("Login should be bigger!");
            }
        });
    }

    @Test
    void register_shortLogin_notOk() {
        assertThrows(MyUncheckedException.class, () -> {
            if (loginLength < 6) {
                throw new MyUncheckedException("Login should be bigger!");
            }
        });
    }

    @Test
    void password_length_notZero_check() {
        assertThrows(MyUncheckedException.class, () -> {
            if (passwordLength == 0) {
                throw new MyUncheckedException("Password should be bigger!");
            }
        });
    }

    @Test
    void register_nullPassword_notOk() {
        assertNotNull(password, "Password should not be null");
    }

    @Test
    void register_shortPassword_notOk() {
        assertThrows(MyUncheckedException.class, () -> {
            if (passwordLength < 6) {
                throw new MyUncheckedException("Password should be bigger!");
            }
        });
    }

    @Test
    void age_under_0_check() {
        assertThrows(MyUncheckedException.class, () -> {
            if (age < 0) {
                throw new MyUncheckedException("Age should be bigger!");
            }
        });
    }

    @Test
    void age_under_18_check() {
        assertThrows(MyUncheckedException.class, () -> {
            if (age < 18) {
                throw new MyUncheckedException("You must be at least 18 to register!");
            }
        });
    }
}
