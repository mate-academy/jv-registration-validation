package core.basesyntax.service;

import static core.basesyntax.service.RegistrationServiceImpl.USER_MIN_ACCESSIBLE_AGE;
import static core.basesyntax.service.RegistrationServiceImpl.WHITE_SPACE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.UserRegistrationException;
import core.basesyntax.model.User;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static final int USER_AGE_LESS_18_NOT_OK = 17;
    private static final int USER_AGE_MORE_THAN_18_OK = 30;
    private static final String USER_LOGIN_LENGTH_LESS_6_SYMBOLS_NOT_OK = "Bob";
    private static final String USER_LOGIN_LENGTH_6_SYMBOLS_OK = "Bob123";
    private static final String USER_PASSWORD_LENGTH_LESS_6_SYMBOLS_NOT_OK = "bob";
    private static final String USER_PASSWORD_LENGTH_6_SYMBOLS_OK = "bob123";

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("register_userData_Ok")
    void register_addUserToDatabase_Ok(User input) {
        User actualResult = registrationService.register(input);
        assertEquals(input.getLogin(), actualResult.getLogin());
        assertEquals(input.getPassword(), actualResult.getPassword());
        assertEquals(input.getAge(), actualResult.getAge());
    }

    @ParameterizedTest
    @MethodSource("register_userData_NotOk")
    void register_addUserToDatabase_notOk(User input) {
        assertThrows(UserRegistrationException.class, () -> registrationService.register(input));
    }

    private static Stream<Arguments> register_userData_Ok() {
        return Stream.of(
                Arguments.of(new User(
                        USER_LOGIN_LENGTH_6_SYMBOLS_OK,
                        USER_PASSWORD_LENGTH_6_SYMBOLS_OK,
                        USER_MIN_ACCESSIBLE_AGE)),
                Arguments.of(new User(
                        USER_LOGIN_LENGTH_6_SYMBOLS_OK,
                        USER_PASSWORD_LENGTH_6_SYMBOLS_OK,
                        USER_AGE_MORE_THAN_18_OK))
        );
    }

    private static Stream<Arguments> register_userData_NotOk() {
        return Stream.of(
                Arguments.of(new User(
                        USER_LOGIN_LENGTH_LESS_6_SYMBOLS_NOT_OK,
                        USER_PASSWORD_LENGTH_6_SYMBOLS_OK,
                        USER_MIN_ACCESSIBLE_AGE)),
                Arguments.of(new User(
                        USER_LOGIN_LENGTH_6_SYMBOLS_OK,
                        USER_PASSWORD_LENGTH_LESS_6_SYMBOLS_NOT_OK,
                        USER_MIN_ACCESSIBLE_AGE)),
                Arguments.of(new User(
                        USER_LOGIN_LENGTH_6_SYMBOLS_OK,
                        USER_PASSWORD_LENGTH_6_SYMBOLS_OK,
                        USER_AGE_LESS_18_NOT_OK)),
                Arguments.of(new User(
                        USER_LOGIN_LENGTH_LESS_6_SYMBOLS_NOT_OK,
                        USER_PASSWORD_LENGTH_LESS_6_SYMBOLS_NOT_OK,
                        USER_MIN_ACCESSIBLE_AGE)),
                Arguments.of(new User(
                        USER_LOGIN_LENGTH_LESS_6_SYMBOLS_NOT_OK,
                        USER_PASSWORD_LENGTH_6_SYMBOLS_OK,
                        USER_AGE_LESS_18_NOT_OK)),
                Arguments.of(new User(
                        WHITE_SPACE,
                        USER_PASSWORD_LENGTH_6_SYMBOLS_OK,
                        USER_MIN_ACCESSIBLE_AGE)),
                Arguments.of(new User(
                        USER_LOGIN_LENGTH_6_SYMBOLS_OK,
                        WHITE_SPACE,
                        USER_MIN_ACCESSIBLE_AGE)),
                Arguments.of(new User(
                        null,
                        USER_PASSWORD_LENGTH_6_SYMBOLS_OK,
                        USER_MIN_ACCESSIBLE_AGE)),
                Arguments.of(new User(
                        USER_LOGIN_LENGTH_6_SYMBOLS_OK,
                        null,
                        USER_MIN_ACCESSIBLE_AGE)),
                Arguments.of(new User(
                        USER_LOGIN_LENGTH_6_SYMBOLS_OK,
                        USER_PASSWORD_LENGTH_6_SYMBOLS_OK,
                        null))
        );
    }
}





