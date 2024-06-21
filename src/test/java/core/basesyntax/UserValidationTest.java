package core.basesyntax;

import core.basesyntax.exeptions.InvalidUserException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.validators.UserValidator;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserValidationTest {
    private static final String DEFAULT_LOGIN = "HelloMates";
    private static final String DEFAULT_VALID_PASSWORD = "SuperPassword123@";
    private static final int DEFAULT_AGE = 18;
    private static final long DEFAULT_ID = 100L;
    private static UserValidator userValidator;
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        userValidator = new UserValidator();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void validateUser_nullableUser_NotOk() {
        User userWithNullProperties = new User();
        Assertions.assertThrows(InvalidUserException.class, () ->
                userValidator.validateUser(null));
        Assertions.assertThrows(InvalidUserException.class, () ->
                userValidator.validateUser(userWithNullProperties));

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
            Assertions.assertThrows(InvalidUserException.class, () ->
                    userValidator.validateUser(userWithInvalidProperties));
        }
    }

    @Test
    void validateUser_validUser_Ok() {
        User user = new User(DEFAULT_ID, DEFAULT_LOGIN, DEFAULT_VALID_PASSWORD, DEFAULT_AGE);
        Assertions.assertDoesNotThrow(() -> userValidator.validateUser(user));
    }

    @Test
    void validateUser_checkValidPasswords_Ok() throws InvalidUserException {
        String passwordByPattern1 = "HelloWorld!28";
        String passwordByPattern2 = "4141&JavaCode";
        User user = createDefaultUser();
        user.setPassword(passwordByPattern1);
        userValidator.validateUser(user);
        user.setPassword(passwordByPattern2);
        userValidator.validateUser(user);
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
                userValidator.validateUser(user);
            });
        }
    }

    @Test
    void validateUser_negativeAge_NotOk() {
        User user = createDefaultUser();
        user.setAge(-2);
        Assertions.assertThrows(InvalidUserException.class, () ->
                userValidator.validateUser(user));
    }

    @Test
    void register_nullableUser_NotOk() {
        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_userWithNullableId_NotOk() {
        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(new User(null,
                        DEFAULT_LOGIN,
                        DEFAULT_VALID_PASSWORD,
                        DEFAULT_AGE)));
    }

    @Test
    void register_userWithNullableLogin_NotOk() {
        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(new User(DEFAULT_ID,
                        null,
                        DEFAULT_VALID_PASSWORD,
                        DEFAULT_AGE)));
    }

    @Test
    void register_userWithNullablePassword_NotOk() {
        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(new User(DEFAULT_ID,
                        DEFAULT_LOGIN,
                        null,
                        DEFAULT_AGE)));
    }

    @Test
    void register_userWithNullableAge_NotOk() {
        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(new User(DEFAULT_ID,
                        DEFAULT_LOGIN,
                        DEFAULT_VALID_PASSWORD,
                        null)));
    }

    @Test
    void register_UserWithSameLogin_NotOk() throws InvalidUserException {
        User user1 = new User(1L, "SameLogin123", "HardPassword123!", 18);
        User user2 = new User(2L, "SameLogin123", "SuperPassword$54", 22);
        registrationService.register(user1);
        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(user2));
    }

    @Test
    void register_validUsers_Ok() throws InvalidUserException {
        User user1 = new User(1L, "Mate123", "HardPassword123!", 18);
        User user2 = new User(2L, "JoeBaiden753", "SuperPassword$54", 22);
        User registeredUser1 = registrationService.register(user1);
        Assertions.assertEquals(registeredUser1, user1);
        User registeredUser2 = registrationService.register(user2);
        Assertions.assertEquals(registeredUser2, user2);
    }

    private User createDefaultUser() {
        return new User(DEFAULT_ID, DEFAULT_LOGIN, DEFAULT_VALID_PASSWORD, DEFAULT_AGE);
    }
}
