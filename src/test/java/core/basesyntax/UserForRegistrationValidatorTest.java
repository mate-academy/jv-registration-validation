package core.basesyntax;

import core.basesyntax.exeptions.InvalidUserException;
import core.basesyntax.model.User;
import core.basesyntax.validators.UserForRegistrationValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UserForRegistrationValidatorTest {
    private static final String DEFAULT_CORRECT_PASSWORD = "SuperPassword123@";
    private static final String DEFAULT_LOGIN = "HelloMates";
    private static final int DEFAULT_AGE = 18;
    private static final long DEFAULT_ID = 100L;

    @Test
    void validateUser_checkValidPasswords_Ok() throws InvalidUserException {
        String passwordByPattern1 = "HelloWorld!28";
        String passwordByPattern2 = "4141&JavaCode";
        User user = createDefaultUser();
        user.setPassword(passwordByPattern1);
        new UserForRegistrationValidator(user).validateUser();
        user.setPassword(passwordByPattern2);
        new UserForRegistrationValidator(user).validateUser();
    }

    @Test
    void validateUser_invalidPassword_NotOk() {
        List<String> incorrectPasswords = List.of("",
                "123",
                "Hello64",
                "!nowaYaaaaa",
                "HelloMates!!!",
                "Yel6low12",
                "123!#$%^111",
                "VeryLongPasswordWithA1otofS!ignes"
        );
        User user = createDefaultUser();
        for (String incorrectPassword : incorrectPasswords) {
            Assertions.assertThrows(InvalidUserException.class, () -> {
                user.setPassword(incorrectPassword);
                new UserForRegistrationValidator(user).validateUser();
            });
        }
    }

    @Test
    void validateUser_underMinimumAge_NotOk() {
        List<User> userList = List.of(
                new User(DEFAULT_ID, DEFAULT_LOGIN, DEFAULT_CORRECT_PASSWORD, 25),
                new User(DEFAULT_ID, DEFAULT_LOGIN, DEFAULT_CORRECT_PASSWORD, 40),
                new User(DEFAULT_ID, DEFAULT_LOGIN, DEFAULT_CORRECT_PASSWORD, 15),
                new User(DEFAULT_ID, DEFAULT_LOGIN, DEFAULT_CORRECT_PASSWORD, 88),
                new User(DEFAULT_ID, DEFAULT_LOGIN, DEFAULT_CORRECT_PASSWORD, 64),
                new User(DEFAULT_ID, DEFAULT_LOGIN, DEFAULT_CORRECT_PASSWORD, 3)
        );
        int testMinimumAge = 18;
        Assertions.assertThrows(InvalidUserException.class, () -> {
            for (User user : userList) {
                new UserForRegistrationValidator(user, testMinimumAge).validateUser();
            }
        });
    }

    @Test
    void validateUser_negativeAge_NotOk() {
        User user = createDefaultUser();
        user.setAge(-2);
        Assertions.assertThrows(InvalidUserException.class, () ->
                new UserForRegistrationValidator(user).validateUser());
    }

    private User createDefaultUser() {
        return new User(DEFAULT_ID, DEFAULT_LOGIN, DEFAULT_CORRECT_PASSWORD, DEFAULT_AGE);
    }
}
