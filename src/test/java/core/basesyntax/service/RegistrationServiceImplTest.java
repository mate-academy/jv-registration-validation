package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_incorrectAge_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validUserCase_OK() {
        User expected = new User("Alice", "password", 20);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_storageContainsNewUser_OK() {
        User actual = new User("Alice", "password", 20);
        Storage.people.add(actual);
        assertTrue(Storage.people.contains(actual));
    }

    @Test
    void register_nullLogin_notOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsLessThanSixSymbols_notOK() {
        User actual = new User("user1", "12345", 20);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_UserWithThisLoginAlreadyExists_OK() {
        User actual = new User("User", "password", 21);
        registrationService.register(new User("User", "1234567", 45));
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }
}
