package core.basesyntax.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void setId_Ok() {
        Long id = 1L;
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    void setLogin_Ok() {
        String login = "user123";
        user.setLogin(login);
        assertEquals(login, user.getLogin());
    }

    @Test
    void setPassword_Ok() {
        String password = "password123";
        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }

    @Test
    void setAge_Ok() {
        Integer age = 30;
        user.setAge(age);
        assertEquals(age, user.getAge());
    }

    @Test
    void testEqualsAndHashCode_Ok() {
        User user1 = new User();
        user1.setId(1L);
        user1.setLogin("user123");
        user1.setPassword("password123");
        user1.setAge(30);

        User user2 = new User();
        user2.setId(1L);
        user2.setLogin("user123");
        user2.setPassword("password123");
        user2.setAge(30);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }
}

