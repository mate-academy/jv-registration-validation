package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl regService;

    @BeforeAll
    static void initialize() {
        regService = new RegistrationServiceImpl();
    }

    @Test
    void loginIsAlreadyInStorage_NotOk() {
        User user1 = new User("Alice", "123456", 19);
        User user2 = new User("Alice", "654321", 22);
        assertNotNull(regService.register(user1));
        assertThrows(RuntimeException.class, () -> regService.register(user2));
    }

    @Test
    void userAgeIsLessThanMustBe_NotOk() {
        User user = new User("Alice", "123456", 16);
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void userPasswordIsLessThanMustBe_NotOk() {
        User user = new User("Alice", "12345", 19);
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void validUser_Ok() {
        User actual = new User("Bob", "BobSlave123", 19);
        assertEquals(actual, regService.register(actual));
    }

    @Test
    void addNullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> regService.register(null));
    }

    @Test
    void addUserWithNullAge_NotOk() {
        User nullAge = new User("Alice", "123456", null);
        assertThrows(RuntimeException.class, () -> regService.register(nullAge));
    }

    @Test
    void addUserWithNullPassword_NotOk() {
        User nullPassword = new User("Alice", null, 19);
        assertThrows(RuntimeException.class, () -> regService.register(nullPassword));
    }

    @Test
    void addUserWithNullLogin_NotOk() {
        User nullLogin = new User(null, "123456", 19);
        assertThrows(RuntimeException.class, () -> regService.register(nullLogin));
    }

    @Test
    void addUserWithInvalidAge_NotOk() {
        User invalidAge = new User("Alice", "123456", -412);
        assertThrows(RuntimeException.class, () -> regService.register(invalidAge));
    }
}
