package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserTest {
    private static final User userWithId = new User();
    private static final User userWithLogin = new User();
    private static final User userWithPassword = new User();
    private static final User userWithAge = new User();

    @BeforeAll
    public static void setupTestUsers() {
        userWithId.setId(0L);
        userWithLogin.setLogin("admin");
        userWithPassword.setPassword("password");
        userWithAge.setAge(20);
    }

    @Test
    public void equals_sameIdUser_true() {
        User user = new User();
        user.setId(0L);
        assertEquals(userWithId,
                user,
                "Users with same ID should be equal");
    }

    @Test
    public void equals_differentIdUser_false() {
        User user = new User();
        user.setId(1L);
        assertNotEquals(userWithId,
                user,
                "Users with different IDs should not be equal");
    }

    @Test
    public void equals_sameLoginUser_true() {
        User user = new User();
        user.setLogin("admin");
        assertEquals(userWithLogin,
                user,
                "Users with same login should be equal");
    }

    @Test
    public void equals_differentLoginUser_false() {
        User user = new User();
        user.setLogin("employee");
        assertNotEquals(userWithLogin,
                user,
                "Users with different logins should not be equal");
    }

    @Test
    public void equals_samePasswordUser_true() {
        User user = new User();
        user.setPassword("password");
        assertEquals(userWithPassword,
                user,
                "Users with same password should be equal");
    }

    @Test
    public void equals_differentPasswordUser_false() {
        User user = new User();
        user.setPassword("drowssap");
        assertNotEquals(userWithPassword,
                user,
                "Users with different passwords should not be equal");
    }

    @Test
    public void equals_sameAgeUser_true() {
        User user = new User();
        user.setAge(20);
        assertEquals(userWithAge,
                user,
                "Users with same age should be equal");
    }

    @Test
    public void equals_differentAgeUser_false() {
        User user = new User();
        user.setAge(35);
        assertNotEquals(userWithAge,
                user,
                "Users with different ages should not be equal");
    }

    @Test
    public void equals_sameUser_true() {
        User user = userWithId;
        assertEquals(userWithId,
                user,
                "Same user should be equal to itself");
    }

    @Test
    public void equals_null_false() {
        assertNotEquals(userWithId,
                null,
                "User should not be equal to null");
    }

    @Test
    public void equals_otherClass_false() {
        Long id = 0L;
        assertNotEquals(userWithId,
                id,
                "User should not be equal to an other class object");
    }

    @Test
    public void hashCode_equalUser_equal() {
        User user = new User();
        user.setId(0L);
        assertEquals(userWithId.hashCode(),
                user.hashCode(),
                "Equal users should have the same hash code");
    }
}
