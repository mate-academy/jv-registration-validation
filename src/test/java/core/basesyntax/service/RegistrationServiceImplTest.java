package core.basesyntax.service;

import static core.basesyntax.service.RegistrationServiceImpl.USER_MIN_ACCESSIBLE_AGE;
import static core.basesyntax.service.RegistrationServiceImpl.WHITE_SPACE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.UserRegistrationException;
import core.basesyntax.model.User;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;
    private static final int USER_AGE_LESS_18_NOT_OK = 17;
    private static final int USER_AGE_MORE_THAN_18_OK = 30;
    private static final String USER_LOGIN_LENGTH_LESS_6_SYMBOLS_NOT_OK = "Bob";
    private static final String USER_LOGIN_LENGTH_6_SYMBOLS_OK = "Bob123";
    private static final String USER_PASSWORD_LENGTH_LESS_6_SYMBOLS_NOT_OK = "bob";
    private static final String USER_PASSWORD_LENGTH_6_SYMBOLS_OK = "bob123";

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @ParameterizedTest
    @MethodSource("validUserDataEntry")
    void register_userWithValidData_ok(User input) {
        User actualResult = registrationService.register(input);
        assertEquals(input.getLogin(), actualResult.getLogin());
        assertEquals(input.getPassword(), actualResult.getPassword());
        assertEquals(input.getAge(), actualResult.getAge());
    }

    @ParameterizedTest
    @MethodSource("invalidUserDataEntry")
    void register_userWithNotValidData_notOk(User input) {
        assertThrows(UserRegistrationException.class, () -> registrationService.register(input));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(UserRegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_existedUser_notOk() {
        User user = new User(
                USER_LOGIN_LENGTH_6_SYMBOLS_OK,
                USER_PASSWORD_LENGTH_6_SYMBOLS_OK,
                USER_MIN_ACCESSIBLE_AGE);
        storageDao.add(user);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    private static Stream<Arguments> validUserDataEntry() {
        return Stream.of(
                Arguments.of(new User(
                        "Alex123",
                        "alex123",
                        USER_MIN_ACCESSIBLE_AGE)),
                Arguments.of(new User(
                        "Alice123",
                        "alice123",
                        30))
        );
    }

    private static Stream<Arguments> invalidUserDataEntry() {
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
                        null)),
                Arguments.of(new User(null, null, null))
        );
    }
}
