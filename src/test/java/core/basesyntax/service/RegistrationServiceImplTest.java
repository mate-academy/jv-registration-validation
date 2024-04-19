package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String CORRECT_LOGIN = "login555";
    private static final String CORRECT_PASSWORD = "password";
    private static final int CORRECT_AGE = 20;
    private static User user;
    private static User userTwo;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        user = new User();
        userTwo = new User();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user.setAge(CORRECT_AGE);
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
    }

    @Test
    void register_UserInStorage_Ok() {
        Storage.people.add(user);
        userTwo = registrationService.register(user);
        assertNull(userTwo);
    }

    @Test
    void register_UserNotInStorage_Ok() {
        userTwo = registrationService.register(user);
        assertEquals(userTwo, user);
    }

    @Test
    void register_userNull_NotOk() {
        assertThrows(NullPointerException.class, () -> registrationService.register(null));
    }

    @Test
    void register_loginNull_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginIsShort_NotOk() {
        user.setLogin("log");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginSixSymbols_Ok() {
        user.setLogin(CORRECT_LOGIN);
        registrationService.register(user);
        boolean actual = Storage.people.contains(user);
        assertTrue(actual);
    }

    @Test
    void register_passwordNull_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_NotOk() {
        user.setPassword("");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsShort_NotOk() {
        user.setPassword("123");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordSixSymbols_Ok() {
        user.setPassword(CORRECT_PASSWORD);
        registrationService.register(user);
        boolean actual = Storage.people.contains(user);
        assertTrue(actual);
    }

    @Test
    void register_ageNull_NotOk() {
        user.setAge(null);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageZero_NotOk() {
        user.setAge(0);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageTen_NotOk() {
        user.setAge(10);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageLowerZero_NotOk() {
        user.setAge(-23);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void validUserCase() {
        registrationService.register(user);
        boolean actual = Storage.people.contains(user);
        assertTrue(actual);
    }

    @AfterEach
    void removeObject() {
        Storage.people.remove(user);
    }
}
