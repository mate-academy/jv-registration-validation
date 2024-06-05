package core.basesyntax;

import core.basesyntax.exeptions.InvalidUserException;
import core.basesyntax.model.User;
import core.basesyntax.validators.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

public class UserValidationTest {
    private final String DEFAULT_LOGIN = "Login";
    private final String DEFAULT_PASSWORD = "Password";
    private final long DEFAULT_ID = 1L;
    private final int DEFAULT_AGE = 18;

    @Test
    void validateUser_nullableUser_NotOk() {
        User userWithNullProperties = new User();
        Assertions.assertThrows(InvalidUserException.class, () -> {
            new UserValidator(null).validateUser();
        });
        Assertions.assertThrows(InvalidUserException.class, () -> {
            new UserValidator(userWithNullProperties).validateUser();
        });

    }

    @Test
    void validateUser_nullableProperties_NotOk() {
        List<User> usersWithNullableProperties = List.of(
                new User(null, "Login", "", 3),
                new User(1L, null, "pass", 40),
                new User(3141L, "hello", null, 12),
                new User(1L, "mate", "strongPass", null)
        );
        for (User userWithInvalidProperties : usersWithNullableProperties) {
            Assertions.assertThrows(InvalidUserException.class, () -> {
                new UserValidator(userWithInvalidProperties).validateUser();
            });
        }
    }

    @Test
    void validateUser_validUser_Ok() throws InvalidUserException {
        User user = new User(DEFAULT_ID, DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        new UserValidator(user).validateUser();
    }

    @Test
    void compare_compareWorksCorrectly_Ok() {
        User user1 = new User(DEFAULT_ID, DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        Assertions.assertEquals(user1, user1);
        User user2 = new User(DEFAULT_ID, DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        Assertions.assertEquals(user1, user2);
        Assertions.assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void compare_notEqualsCompare_NotOk() {
        User user1 = new User(DEFAULT_ID, DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        Assertions.assertNotEquals(user1, null);
        User user2 = new User(30L, "HelloWorld", "SuperPassword", 99);
        Assertions.assertNotEquals(user1, user2);
    }


    @Test
    void setters_settersWorksFine_Ok() {
        User user = new User(DEFAULT_ID, DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        long newId = 100L;
        String newLogin = "Coca-cola";
        String newPassword = "EngineCapute123";
        int newAge = 44;
        user.setId(newId);
        Assertions.assertEquals(user.getId(), newId);
        user.setLogin(newLogin);
        Assertions.assertEquals(user.getLogin(), newLogin);
        user.setPassword(newPassword);
        Assertions.assertEquals(user.getPassword(), newPassword);
        user.setAge(newAge);
        Assertions.assertEquals(user.getAge(), newAge);
    }
}
