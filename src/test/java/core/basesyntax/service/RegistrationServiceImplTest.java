package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "Yelena";
    private static final String VALID_PASSWORD = "Olena1";
    private static final int VALID_AGE = 18;
    private static RegistrationService register;
    private final User user = new User();

    @BeforeAll
    static void init() {
        register = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user.setAge(VALID_AGE);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registerValidUser_Ok() {
        User actual = register.register(user);
        assertEquals(user, actual);
    }

    @Test
    void registerLoginValidArbitraryLength_Ok() {
        user.setLogin("Olena_Stankevych");
        User actual = register.register(user);
        assertEquals(user, actual);
        assertTrue(Storage.people.contains(actual));
    }

    @Test
    void registerPasswordValidArbitraryLength_Ok() {
        user.setPassword("123456p");
        User actual = register.register(user);
        assertEquals(user, actual);
        assertTrue(Storage.people.contains(actual));
    }

    @Test
    void registerArbitraryValidAge_Ok() {
        user.setAge(25);
        User actual = register.register(user);
        assertEquals(user, actual);
        assertTrue(Storage.people.contains(actual));
    }

    @Test
    void registrationNullUser_NotOk() {
        assertThrows(UserRegistrationException.class, () -> register.register(null));
    }

    @Test
    void registerUserNullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(UserRegistrationException.class, () -> register.register(user),
                "Login can't be null");
    }

    @ParameterizedTest
    @MethodSource("invalidLoginPasswordData")
    void registerUserInvalidLengthLogin_NotOk(String login) {
        user.setLogin(login);
        assertThrows(UserRegistrationException.class, () -> register.register(user),
                "Login must contain at least 6 characters");
    }

    @Test
    void registerUserSameLogin_NotOk() {
        Storage.people.add(user);
        User testUser = new User();
        testUser.setPassword(VALID_PASSWORD);
        testUser.setAge(VALID_AGE);
        testUser.setLogin(VALID_LOGIN);
        assertThrows(UserRegistrationException.class, () -> register.register(user),
                "Login already exists");
    }

    @Test
    void registerUserNullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(UserRegistrationException.class, () -> register.register(user),
                "Password can't be null");
    }

    @ParameterizedTest
    @MethodSource("invalidLoginPasswordData")
    void registerUserInvalidLengthPassword_NotOk(String password) {
        user.setPassword(password);
        assertThrows(UserRegistrationException.class, () -> register.register(user),
                "Password must contain at least 6 characters");
    }

    @Test
    void registerNullAge_NotOk() {
        user.setAge(null);
        assertThrows(UserRegistrationException.class, () -> register.register(user),
                "Age can't be null");
    }

    @ParameterizedTest
    @MethodSource("invalidAgeData")
    void registerUserInvalidAge_NotOk(int age) {
        user.setAge(age);
        assertThrows(UserRegistrationException.class, () -> register.register(user),
                "Age is less than " + VALID_AGE);
    }

    private static Stream<String> invalidLoginPasswordData() {
        return Stream.of("",
                "notOk",
                "NOT");
    }

    private static Stream<Integer> invalidAgeData() {
        return Stream.of(-1, 0, 17);
    }
}
