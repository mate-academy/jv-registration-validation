package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class RegistrationServiceImplTest {
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final String VALID_LOGIN = "abcdef";
    private static final String VALID_PASSWORD = "abc12345abc";
    private static final int VALID_AGE = 25;
    private static final String[] INVALID_LOGINS = {"", "l", "ab", "abc", "abcd", "abcde"};
    private static final String[] INVALID_PASSWORDS = {"", "a", "abc", "abcd", "abcde"};
    private static final Integer[] INVALID_AGE = {0, 5, 10, 13, 17};
    private static RegistrationService service;
    private User validUser;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setLogin(VALID_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
        validUser.setAge(VALID_AGE);
    }

    @Test
    void register_nullUser_notOk() {
        Assertions.assertThrows(InvalidUserDataException.class, () -> service.register(null),
                "InvalidUserDataException must be thrown if User is null");
    }

    @Test
    void register_validUser_ok() {
        User registeredUser = service.register(validUser);
        Assertions.assertNotNull(registeredUser,
                "Method register(User user) must return registered User if it was successfully");
        Assertions.assertNotEquals(0, registeredUser.getId(),
                "Registered User must get Id after if registering was successfully");
        Assertions.assertEquals(validUser.getLogin(), registeredUser.getLogin(),
                "Method register(User user) must return user with the same login");
        Assertions.assertTrue(storageContainsUser(registeredUser),
                "Method register(User user) must add User in the Storage");
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin(null);
        Assertions.assertThrows(InvalidUserDataException.class, () -> service.register(validUser),
                "InvalidUserDataException must be thrown if User login is null");
    }

    @Test
    void register_duplicatedLogin_notOk() {
        Storage.people.add(validUser);
        Assertions.assertThrows(InvalidUserDataException.class, () -> service.register(validUser),
                "InvalidUserDataException must be thrown if User login has been in the Storage");
    }

    @TestFactory
    Stream<DynamicTest> register_invalidLogin_notOk() {
        String message = "InvalidUserDataException must be thrown if login length less than "
                + LOGIN_MIN_LENGTH + " characters";
        return Arrays.stream(INVALID_LOGINS).map(login ->
                DynamicTest.dynamicTest("Test login = \"" + login + "\"", () -> {
                    validUser.setLogin(login);
                    Assertions.assertThrows(InvalidUserDataException.class,
                            () -> service.register(validUser), message);
                }));
    }

    @Test
    void register_nullPassword_notOk() {
        validUser.setPassword(null);
        Assertions.assertThrows(InvalidUserDataException.class, () -> service.register(validUser),
                "InvalidUserDataException must be thrown if User login is null");
    }

    @TestFactory
    Stream<DynamicTest> register_invalidPassword_notOK() {
        String message = "InvalidUserDataException must be thrown if password length less than "
                + PASSWORD_MIN_LENGTH + " characters";
        return Arrays.stream(INVALID_PASSWORDS).map(password ->
                DynamicTest.dynamicTest("Test password = \"" + password + "\"", () -> {
                    validUser.setPassword(password);
                    Assertions.assertThrows(InvalidUserDataException.class,
                            () -> service.register(validUser), message);
                }));
    }

    @Test
    void register_ageIsNull_notOk() {
        validUser.setAge(null);
        Assertions.assertThrows(InvalidUserDataException.class, () -> service.register(validUser),
                "InvalidUserDataException must be thrown if age is null");
    }

    @Test
    void register_ageIsNegative_notOk() {
        validUser.setAge(-1);
        Assertions.assertThrows(InvalidUserDataException.class, () -> service.register(validUser),
                "InvalidUserDataException must be thrown if User has negative age");
    }

    @TestFactory
    Stream<DynamicTest> register_ageUnderMinAge_notOK() {
        String message = "InvalidUserDataException must be thrown if User has age less than "
                + MIN_AGE;
        return Arrays.stream(INVALID_AGE).map(age ->
                DynamicTest.dynamicTest("Test age = " + age, () -> {
                    validUser.setAge(age);
                    Assertions.assertThrows(InvalidUserDataException.class,
                            () -> service.register(validUser), message);
                }));
    }

    @Test
    void register_ageIsMinAge_ok() {
        validUser.setAge(MIN_AGE);
        Assertions.assertNotNull(service.register(validUser), "User " + MIN_AGE
                + " years old must be registered");
    }

    @Test
    void register_agaOverMinAge_ok() {
        validUser.setAge(25);
        Assertions.assertNotNull(service.register(validUser), "User over "
                + MIN_AGE + " years old must be registered");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    private boolean storageContainsUser(User user) {
        for (User current : Storage.people) {
            if (current != null && current.getLogin().equals(user.getLogin())) {
                return current.equals(user);
            }
        }
        return false;
    }
}
