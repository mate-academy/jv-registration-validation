package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void registerUser_validData_noExistingUser_success() {
        User existingUser = createUser("john_doe", "password", 25);
        User newUser = createUser("jane_doe", "newpassword", 20);

        Assertions.assertNotEquals(existingUser, newUser);
    }

    @Test
    void registerUser_shortLogin_exceptionThrown() {
        User user = createUser("abc", "password", 25);

        Assertions.assertThrows(InvalidDataException.class, () -> {
            validateUser(user);
        });
    }

    @Test
    void registerUser_shortPassword_exceptionThrown() {
        User user = createUser("john_doe", "abc", 25);

        Assertions.assertThrows(InvalidDataException.class, () -> {
            validateUser(user);
        });
    }

    @Test
    void registerUser_underageUser_exceptionThrown() {
        User user = createUser("john_doe", "password", 17);

        Assertions.assertThrows(InvalidDataException.class, () -> {
            validateUser(user);
        });
    }

    @Test
    void registerUser_nullLogin_exceptionThrown() {
        User user = createUser (null, "password", 25);

        Assertions.assertThrows(InvalidDataException.class, () -> {
            validateUser(user);
        });
    }

    @Test
    void registerUser_nullPassword_exceptionThrow() {
        User user = createUser("john_doe", null, 25);

        Assertions.assertThrows(InvalidDataException.class, () -> {
            validateUser(user);
        });
    }

    @Test
    void registerUser_negativeAge_exceptionThrown() {
        User user = createUser("john_doe", "password", -25);

        Assertions.assertThrows(InvalidDataException.class, () -> {
            validateUser(user);
        });
    }

    @Test
    void registerUser_upperAgeLimit_validData_success() {
        User user = createUser("john_doe","password",150);

        Assertions.assertDoesNotThrow(() -> {
            validateUser(user);
        });
    }

    private User createUser(String login, String password, int age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }

    private void validateUser(User user) {
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new InvalidDataException("Invalid login. Minimum length is 6");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new InvalidDataException("Invalid password. Minimum length is 6");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new InvalidDataException("Invalid age. Minimum age is 18");
        }
    }

    private static class InvalidDataException extends RuntimeException {
        public InvalidDataException(String message) {
            super(message);
        }
    }
}
