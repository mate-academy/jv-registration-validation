package core.basesyntax.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private static final int MAX_AGE = 100;
    private static final long MAX_ID = Long.MAX_VALUE;
    private static final int MAX_STRING_LENGTH = 20;
    private static final int MIN_AGE = 18;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void idIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> user.setId(null));
    }

    @Test
    void passwordIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> user.setPassword(null));
    }

    @Test
    void loginIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> user.setLogin(null));
    }

    @Test
    void ageIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> user.setAge(null));
    }

    @Test
    void loginIsEmpty_NotOk() {
        user.setLogin("");
        assertTrue(!user.getLogin().isEmpty(), "login can't be null");
    }

    @Test
    void passwordIsEmpty_NotOk() {
        user.setLogin("");
        assertTrue(!user.getPassword().isEmpty(), "password can't be null");
    }

    @Test
    void ageIsTooBig_NotOk() {
        user.setAge(130);
        assertTrue(user.getAge() < MAX_AGE, "user is too old");
    }

    @Test
    void idIsOutOfBound() {
        user.setId(Long.MAX_VALUE);
        assertTrue(user.getId() < MAX_ID, "Id is too big");
    }

    @Test
    void passwordIsTooLong() {
        user.setPassword("qwertryiuopiupupoiupiuads");
        assertTrue(user.getPassword().length()
                < MAX_STRING_LENGTH, "password is too long");
    }

    @Test
    void loginIsTooLong() {
        user.setLogin("qwertryiuopiupupoiupiadsau");
        assertTrue(user.getLogin().length() < MAX_STRING_LENGTH,
                "login is too long");
    }

    @Test
    void ageIsNegative_NotOk() {
        user.setAge(-5);
        assertTrue(user.getAge() > 0, "age can't be negative");
    }

    @Test
    void idIsNegative_NotOk() {
        user.setId(-5l);
        assertTrue(user.getId() > 0, "id can't be negative");
    }

    @Test
    void setWrongAge_NotOk() {
        user.setAge(16);
        assertTrue(user.getAge() < MIN_AGE,
                "user can't be younger than " + MIN_AGE);
    }

    @Test
    void userEquals_Ok() {
        User user2 = new User();
        user.setLogin("Jack");
        user.setPassword("qwertyu");
        user.setAge(21);
        user2.setLogin("Jack");
        user2.setPassword("qwertyu");
        user2.setAge(21);
        assertEquals(user, user2,
                "" + user + " must be equals " + user2);
    }

    @Test
    void userEqualsNull_NotOk() {
        assertNotEquals(user, null,
                "User can't be equals null");
    }

    @Test
    void userEqualsItself_Ok() {
        assertEquals(user, user,
                "User must equals himself");
    }

    @Test
    void userHashcode_Ok() {
        User user2 = new User();
        user.setLogin("Jack");
        user.setPassword("qwertyu");
        user.setAge(21);
        user.setId(455L);
        user2.setLogin("Jack");
        user2.setPassword("qwertyu");
        user2.setAge(21);
        user2.setId(455L);
        assertEquals(user.hashCode(), user2.hashCode(),
                "" + user + " hashcode must be equal to hashcode " + user2);
    }

    @Test
    void getAge_Ok() {
        user.setAge(22);
        assertEquals(user.getAge(), 22, "Age must be 22" );
    }

    @Test
    void getId_Ok() {
        user.setId(4567L);
        assertEquals(user.getId(), 4567L, "ID must be 4567");
    }

    @Test
    void getPassword_Ok() {
        user.setPassword("qwertyu");
        assertEquals(user.getPassword(), "qwertyu",
                "Password must be \"qwertyu\"");
    }

    @Test
    void getLogin_Ok() {
        user.setLogin("Jack");
        assertEquals(user.getLogin(), "Jack",
                "Password must be \"Jack\"");
    }
}
