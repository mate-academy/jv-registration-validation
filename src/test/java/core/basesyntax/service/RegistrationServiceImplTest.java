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
        defaultUser = new User(DEFAULT_LOGIN, VALID_PASSWORD, MIN_AGE);
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
    void registerCorrectUser_Ok() {
        User actual = registrationService.register(defaultUser);
        boolean cont = Storage.people.contains(actual);
        assertTrue(cont);
    }

    @Test
    void register_NullUser_NotOk() {
        User user = null;
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_Ok() {
        defaultUser.setLogin(null);
        assertThrows(InvalidUserException.class, () -> registrationService.register(defaultUser),
                "User can't be null, please fixed this. Login = "
                        + defaultUser.getLogin());
    }

    @Test
    void register_NullPassword_NotOk() {
        defaultUser.setPassword(null);
        assertThrows(InvalidUserException.class, () -> registrationService.register(defaultUser),
                "Password can't be null, please fixed this. password = "
                        + defaultUser.getPassword());
    }

    @Test
    void register_IncorrectPassword_fiveCharacters_NotOk() {
        defaultUser.setPassword("12345");
        assertThrows(InvalidUserException.class, () ->
                        registrationService.register(defaultUser),
                "Password length should be least 6 characters, but length "
                        + defaultUser.getPassword().length());
    }

    @Test
    void register_CorrectMinPassword_Characters_Ok() {
        defaultUser.setPassword("123456");
        registrationService.register(defaultUser);
        boolean checkUser = Storage.people.contains(defaultUser);
        assertTrue(checkUser);
    }

    @Test
    void register_CorrectPassword_Characters_Ok() {
        defaultUser.setPassword("123456789");
        registrationService.register(defaultUser);
        boolean checkUser = Storage.people.contains(defaultUser);
        assertTrue(checkUser);
    }

    @Test
    void registrationCopyUser_notOk() {
        User copyUser = new User();
        copyUser.setLogin(defaultUser.getLogin());
        copyUser.setPassword(defaultUser.getPassword());
        copyUser.setAge(defaultUser.getAge());
        registrationService.register(defaultUser);
        assertThrows(InvalidUserException.class, () -> registrationService.register(copyUser),
                "This User is already registered. Login = " + defaultUser.getLogin());
    }

    @Test
    void register_nullAge_notOk() {
        defaultUser.setAge(null);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(defaultUser),
                "User age can't be null, but user age = "
                + defaultUser.getAge());
    }

    @Test
    void registerWithIncorrectAge_NotOk() {
        defaultUser.setAge(17);
        assertThrows(InvalidUserException.class, () -> registrationService.register(defaultUser),
                "User age should be at least 18 years old, but user age = "
                        + defaultUser.getAge());
    }

    @Test
    void registerWithExactlyAge() {
        defaultUser.setAge(18);
        User register = registrationService.register(defaultUser);
        assertEquals(register, defaultUser);
    }

    @Test
    void registerWithCorrectAge_Ok() {
        defaultUser.setAge(50);
        User register = registrationService.register(defaultUser);
        assertEquals(register, defaultUser);
    }

}
