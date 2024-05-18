package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class UserTest {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int FIRST_USER_ID = 1;
    private static final int SECOND_USER_ID = 2;

    @Test
    public void test_equals_sameUser_successful() {
        User user1 = new User("user1", "password", MIN_AGE);
        User user2 = new User("user1", "password", MIN_AGE);

        assertEquals(user1, user2);
    }

    @Test
    public void test_equals_differentUsers_failed() {
        User user1 = new User("user1", "password", MIN_AGE);
        User user2 = new User("user2", "password", MIN_AGE);

        assertNotEquals(user1, user2);
    }

    @Test
    public void test_hashCode_equalUsers_equalHashCodes() {
        User user1 = new User("user1", "password", MIN_AGE);
        User user2 = new User("user1", "password", MIN_AGE);

        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void test_hashCode_differentUsers_differentHashCodes() {
        User user1 = new User("user1", "password", MIN_AGE);
        User user2 = new User("user2", "password", MIN_AGE);

        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void test_gettersAndSetters_successful() {
        User user = new User("user1", "password", MIN_AGE);
        user.setId(1L);

        assertEquals("user1", user.getLogin());
        assertEquals("password", user.getPassword());
        assertEquals(MIN_AGE, user.getAge());
        assertEquals(FIRST_USER_ID, user.getId());

        user.setLogin("newUser");
        user.setPassword("newPassword");
        user.setAge(MAX_AGE);
        user.setId(2L);

        assertEquals("newUser", user.getLogin());
        assertEquals("newPassword", user.getPassword());
        assertEquals(MAX_AGE, user.getAge());
        assertEquals(SECOND_USER_ID, user.getId());
    }
}
