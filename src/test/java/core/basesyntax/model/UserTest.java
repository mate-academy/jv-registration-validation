package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserTest {
    private static final User userWithLogin = new User();
    private static final User userWithPassword = new User();
    private static final User userWithAge = new User();
    private static final User user = new User();

    @BeforeAll
    public static void setupTestUsers() {
        userWithLogin.setLogin("admin");
        userWithLogin.setPassword(null);
        userWithLogin.setAge(null);
        userWithPassword.setLogin(null);
        userWithPassword.setPassword("password");
        userWithPassword.setAge(null);
        userWithAge.setLogin(null);
        userWithAge.setPassword(null);
        userWithAge.setAge(20);
    }

    @Test
    public void equals_sameLogin_ok() {
        user.setLogin(userWithLogin.getLogin());
        user.setPassword(null);
        user.setAge(null);
        assertEquals(userWithLogin,
                user,
                "Users with same login should be equal");
    }

    @Test
    public void equals_differentLogin_ok() {
        user.setLogin(userWithLogin.getLogin() + "1");
        user.setPassword(null);
        user.setAge(null);
        assertNotEquals(userWithLogin,
                user,
                "Users with different logins should not be equal");
    }

    @Test
    public void equals_nullLogin_ok() {
        user.setLogin(null);
        user.setPassword(null);
        user.setAge(null);
        assertNotEquals(userWithLogin,
                user,
                "User with empty login should not be equal to a User with present login");
    }

    @Test
    public void equals_samePassword_ok() {
        user.setLogin(null);
        user.setPassword(userWithPassword.getPassword());
        user.setAge(null);
        assertEquals(userWithPassword,
                user,
                "Users with same password should be equal");
    }

    @Test
    public void equals_differentPassword_ok() {
        user.setLogin(null);
        user.setPassword(userWithPassword.getPassword() + "1");
        user.setAge(null);
        assertNotEquals(userWithPassword,
                user,
                "Users with different passwords should not be equal");
    }

    @Test
    public void equals_nullPassword_ok() {
        user.setLogin(null);
        user.setPassword(null);
        user.setAge(null);
        assertNotEquals(userWithPassword,
                user,
                "User with empty password should not be equal to a User with present password");
    }

    @Test
    public void equals_sameAge_ok() {
        user.setLogin(null);
        user.setPassword(null);
        user.setAge(userWithAge.getAge());
        assertEquals(userWithAge,
                user,
                "Users with same age should be equal");
    }

    @Test
    public void equals_differentAge_ok() {
        user.setLogin(null);
        user.setPassword(null);
        user.setAge(userWithAge.getAge() + 1);
        assertNotEquals(userWithAge,
                user,
                "Users with different ages should not be equal");
    }

    @Test
    public void equals_nullAge_ok() {
        user.setLogin(null);
        user.setPassword(null);
        user.setAge(null);
        assertNotEquals(userWithAge,
                user,
                "User with empty age should not be equal to a User with present age");
    }

    @Test
    public void equals_itself_ok() {
        User user = userWithLogin;
        assertEquals(userWithLogin,
                user,
                "Same user should be equal to itself");
    }

    @Test
    public void equals_null_ok() {
        assertNotEquals(userWithLogin,
                null,
                "User should not be equal to null");
    }

    @Test
    public void equals_differentClass_ok() {
        Long id = 0L;
        assertNotEquals(userWithLogin,
                id,
                "User should not be equal to an other class object");
    }

    @Test
    public void hashCode_equal_ok() {
        user.setLogin(userWithLogin.getLogin());
        user.setPassword(null);
        user.setAge(null);
        assertEquals(userWithLogin.hashCode(),
                user.hashCode(),
                "Equal users should have the same hash code");
    }
}
