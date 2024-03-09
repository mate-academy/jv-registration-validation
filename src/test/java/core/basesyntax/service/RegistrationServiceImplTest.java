package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User newUser;
    private static User actual;
    private static final int MIN_AGE = 18;
    private static final String CORRECT_LOGIN = "loginLog";
    private static final String CORRECT_PASSWORD = "password";

    @BeforeAll
    static void setUp() {
        newUser = new User();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setNewUser() {
        newUser.setAge(MIN_AGE);
        newUser.setLogin(CORRECT_LOGIN);
        newUser.setPassword(CORRECT_PASSWORD);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registerUserWithSameLogin_NotOk() {
        Storage.people.add(newUser);
        User user = new User();
        user.setLogin(CORRECT_LOGIN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullUser_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void nullLogin_NotOk() {
        newUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void nullPassword_NotOk() {
        newUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void fiveCharLogin_NotOk() {
        newUser.setLogin("login");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void oneCharLogin_NotOk() {
        newUser.setLogin("l");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void sixLoginLength_Ok() {
        newUser.setLogin("loginn");
        actual = registrationService.register(newUser);
        assertEquals(newUser, actual);
    }

    @Test
    void eightLoginLength_Ok() {
        newUser.setLogin("loginlog");
        actual = registrationService.register(newUser);
        assertEquals(newUser, actual);
    }

    @Test
    void onePasswordLength_NotOk() {
        newUser.setPassword("p");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void fivePasswordLength_NotOk() {
        newUser.setPassword("passw");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void sixPasswordLength_Ok() {
        newUser.setPassword("passwo");
        actual = registrationService.register(newUser);
        assertEquals(newUser, actual);
    }

    @Test
    void eightPasswordLength_Ok() {
        newUser.setPassword("password");
        actual = registrationService.register(newUser);
        assertEquals(newUser, actual);
    }

    @Test
    void minAge_Ok() {
        newUser.setAge(MIN_AGE);
        actual = registrationService.register(newUser);
        assertEquals(newUser, actual);
    }

    @Test
    void usersAge_NotOk() {
        newUser.setAge(1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }
}
