package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User defaultUser;
    private static final int MIN_AGE = 18;
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String DEFAULT_LOGIN = "testUser";

    @BeforeAll
    static void initializeService() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void initialUser() {
        defaultUser = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, MIN_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_correctUser_ok() {
        User actual = registrationService.register(defaultUser);
        boolean isExist = Storage.people.contains(actual);
        assertTrue(isExist, "User should be add to Storage");
    }

    @Test
    void register_addNullUser_notOk() {
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(null),
                "User can't be null");
    }

    @Test
    void register_addNullLogin_notOk() {
        defaultUser.setLogin(null);
        assertThrows(InvalidUserException.class, () -> registrationService.register(defaultUser),
                "Login can't be null, please fixed this. Login: "
                        + defaultUser.getLogin());
    }

    @Test
    void register_addNullPassword_notOk() {
        defaultUser.setPassword(null);
        assertThrows(InvalidUserException.class, () -> registrationService.register(defaultUser),
                "Password can't be null, please fixed this. password = "
                        + defaultUser.getPassword());
    }

    @Test
    void register_passwordLengthIsLessThanMinLength_notOk() {
        defaultUser.setPassword("12345");
        assertThrows(InvalidUserException.class, () ->
                        registrationService.register(defaultUser),
                "Password length should be least 6 characters, but length "
                        + defaultUser.getPassword().length());
    }

    @Test
    void register_passwordLengthMinLength_ok() {
        defaultUser.setPassword("123456");
        registrationService.register(defaultUser);
        boolean isExists = Storage.people.contains(defaultUser);
        assertTrue(isExists, "Password length: "
                + defaultUser.getPassword().length()
                + ". User should be add to Storage");
    }

    @Test
    void register_passwordLengthIsGreaterThanMinLength_ok() {
        defaultUser.setPassword("1234567");
        registrationService.register(defaultUser);
        boolean isExists = Storage.people.contains(defaultUser);
        assertTrue(isExists, "User should be add to Storage");
    }

    @Test
    void register_loginExists_notOk() {
        registrationService.register(defaultUser);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(defaultUser),
                "This User is already registered. Login = "
                        + defaultUser.getLogin());
    }

    @Test
    void register_addNullAge_notOk() {
        defaultUser.setAge(null);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(defaultUser),
                "User age can't be null, but user age = "
                        + defaultUser.getAge());
    }

    @Test
    void register_addInvalidUsersAge_notOk() {
        defaultUser.setAge(17);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(defaultUser),
                "User age should be at least 18 years old, but user age = "
                        + defaultUser.getAge());
    }

    @Test
    void register_withExactlyAge() {
        defaultUser.setAge(18);
        User register = registrationService.register(defaultUser);
        assertEquals(register, defaultUser, "User should be add to Storage");
    }

    @Test
    void register_addAgeGreaterThanMinAge_ok() {
        defaultUser.setAge(19);
        User user = registrationService.register(defaultUser);
        assertEquals(user, defaultUser,
                "User should be add to Storage");
    }
}
