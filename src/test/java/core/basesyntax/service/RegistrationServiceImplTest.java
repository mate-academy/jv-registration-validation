package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
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
        user.setAge(18);
        user.setLogin("vanish88");
        user.setPassword("qwerty123");
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
    void register_LoginTreeSymbols_NotOk() {
        user.setPassword("van");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginSixSymbols_Ok() {
        user.setLogin("vanish");
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
    void register_passwordTreeSymbols_NotOk() {
        user.setPassword("qwe");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordSixSymbols_Ok() {
        user.setPassword("qwerty");
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
    void register_validUserData_Ok() {
        registrationService.register(user);
        boolean actual = Storage.people.contains(user);
        assertTrue(actual);
    }

    @AfterEach
    void removeObject() {
        Storage.people.remove(user);
    }
}
