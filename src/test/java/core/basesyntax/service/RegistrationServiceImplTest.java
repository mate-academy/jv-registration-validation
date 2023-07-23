package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserNotValidException;
import core.basesyntax.model.User;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RegistrationServiceImplTest {
    private static final String SHORTEST_VALID_LOGIN = "LoginN";
    private static final String SHORTEST_VALID_PASSWORD = "123456";
    private static final Integer MIN_VALID_AGE = RegistrationServiceImpl.MIN_AGE;

    private RegistrationServiceImpl registrationServiceImpl;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationServiceImpl = new RegistrationServiceImpl();
    }

    @Test
    void nullUser_NotOk() {
        assertThrows(UserNotValidException.class, () ->
                registrationServiceImpl.register(null), "Null user adding shouldn't work");
    }

    @Test
    void register_ExistedUserTest_NotOk() {
        User firstUser = createValidUser();
        User sameUser = createValidUser();

        Storage.people.add(firstUser);
        assertThrows(UserNotValidException.class, () ->
                        registrationServiceImpl.register(sameUser),
                "Adding user with existed login shouldn't work");
    }

    @ParameterizedTest
    @MethodSource("validUsersProvider")
    void register_validUsers_Ok(User validUser, String message) {
        User expected = validUser;

        assertDoesNotThrow(() -> {
            User actual = registrationServiceImpl.register(validUser);

            assertNotNull(actual, message);
            assertEquals(expected, actual, message);
            assertTrue(Storage.people.contains(expected), message);
        }, message);

    }

    static Stream<Arguments> validUsersProvider() {
        String messageStArt = "register() valid user doesnt work; ";
        return Stream.of(
                arguments(createValidUser(), messageStArt),

                arguments(createUserWithLogin("Login1"), messageStArt + "Login = Login1"),
                arguments(createUserWithLogin("LoginLogin"), messageStArt + "Login = LoginLogin"),

                arguments(createUserWithPassword("1234567"), messageStArt + "Password = 1234567"),
                arguments(createUserWithPassword("87654321"), messageStArt + "Password = 87654321"),

                arguments(createUserWithAge(19), messageStArt + "Age = 19"),
                arguments(createUserWithAge(40), messageStArt + "Age = 40")
                );
    }

    @ParameterizedTest
    @MethodSource("invalidUsersProvider")
    void register_invalidUsers_NotOk(User invalidUser, String message) {
        assertThrows(UserNotValidException.class, () ->
                registrationServiceImpl.register(invalidUser), message);
    }

    static Stream<Arguments> invalidUsersProvider() {
        String messageBegin = "Invalid user throw UserNotValidException; User has invalid ";
        return Stream.of(
                arguments(createUserWithAge(17), messageBegin + "age = 17"),
                arguments(createUserWithAge(11), messageBegin + "age = 11"),
                arguments(createUserWithAge(0), messageBegin + "age = 0"),
                arguments(createUserWithAge(-18), messageBegin + "age = -18"),
                arguments(createUserWithAge(null), messageBegin + "age is null"),

                arguments(createUserWithPassword("12345"), messageBegin + "password = 12345"),
                arguments(createUserWithPassword("123"), messageBegin + "password = 12345"),
                arguments(createUserWithPassword(""), messageBegin + "password = empty String"),
                arguments(createUserWithPassword(null), messageBegin + "password = null"),

                arguments(createUserWithLogin("Login"), messageBegin + "login = login"),
                arguments(createUserWithLogin("Log"), messageBegin + "login = login"),
                arguments(createUserWithLogin(""), messageBegin + "login = empty String"),
                arguments(createUserWithLogin(null), messageBegin + "login = null")
                );
    }

    private static User createValidUser() {
        User user = new User();
        user.setLogin(SHORTEST_VALID_LOGIN);
        user.setPassword(SHORTEST_VALID_PASSWORD);
        user.setAge(MIN_VALID_AGE);
        return user;
    }

    private static User createUserWithLogin(String login) {
        User userWithLogin = createValidUser();
        userWithLogin.setLogin(login);
        return userWithLogin;
    }

    private static User createUserWithPassword(String password) {
        User userWithPassword = createValidUser();
        userWithPassword.setPassword(password);
        return userWithPassword;
    }

    private static User createUserWithAge(Integer newAge) {
        User userWithAge = createValidUser();
        userWithAge.setAge(newAge);
        return userWithAge;
    }
}
