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
    private static final String VALID_PASSWORD = "123456";
    private static final String DEFAULT_LOGIN = "testUser";

    @BeforeAll
    static void initializeService() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void initialUser() {
        defaultUser = new User(DEFAULT_LOGIN, VALID_PASSWORD, MIN_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_CorrectUser_Ok() {
        User actual = registrationService.register(defaultUser);
        boolean cont = Storage.people.contains(actual);
        assertTrue(cont);
    }

    @Test
    void register_addNullUser_NotOk() {
        User user = null;
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(user),
                "User can't be null");
    }

    @Test
    void register_addNullLogin_NotOk() {
        defaultUser.setLogin(null);
        assertThrows(InvalidUserException.class, () -> registrationService.register(defaultUser),
                "Login can't be null, please fixed this. Login: "
                        + defaultUser.getLogin());
    }

    @Test
    void register_addNullPassword_NotOk() {
        defaultUser.setPassword(null);
        assertThrows(InvalidUserException.class, () -> registrationService.register(defaultUser),
                "Password can't be null, please fixed this. password = "
                        + defaultUser.getPassword());
    }

    @Test
    void register_passwordLengthIsLessThanMinLength_NotOk() {
        defaultUser.setPassword("12345");
        assertThrows(InvalidUserException.class, () ->
                        registrationService.register(defaultUser),
                "Password length should be least 6 characters, but length "
                        + defaultUser.getPassword().length());
    }

    @Test
    void register_passwordLengthMinLength_Ok() {
        defaultUser.setPassword("123456");
        registrationService.register(defaultUser);
        boolean actual = Storage.people.contains(defaultUser);
        assertTrue(actual, "Password length: "
                + defaultUser.getPassword().length()
                + ". User should be add to Storage");
    }

    @Test
    void register_passwordLengthIsGreaterThanMinLength_Ok() {
        defaultUser.setPassword("1234567");
        registrationService.register(defaultUser);
        boolean checkUser = Storage.people.contains(defaultUser);
        assertTrue(checkUser, "User should be add to Storage");
    }

    @Test
    void register_loginExists_NotOk() {
        User copyUser = new User();
        copyUser.setLogin(defaultUser.getLogin());
        copyUser.setPassword(defaultUser.getPassword());
        copyUser.setAge(defaultUser.getAge());
        registrationService.register(defaultUser);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(copyUser),
                "This User is already registered. Login = "
                        + defaultUser.getLogin());
    }

    @Test
    void register_addNullAge_NotOk() {
        defaultUser.setAge(null);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(defaultUser),
                "User age can't be null, but user age = "
                        + defaultUser.getAge());
    }

    @Test
    void register_addInvalidUsersAge_NotOk() {
        defaultUser.setAge(17);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(defaultUser),
                "User age should be at least 18 years old, but user age = "
                        + defaultUser.getAge());
    }

    @Test
    void register_WithExactlyAge() {
        defaultUser.setAge(18);
        User register = registrationService.register(defaultUser);
        assertEquals(register, defaultUser, "User should be add to Storage");
    }

    @Test
    void register_addAgeGreaterThanMinAge_ok() {
        defaultUser.setAge(19);
        User register = registrationService.register(defaultUser);
        assertEquals(register, defaultUser,
                "User should be add to Storage");
    }

}
