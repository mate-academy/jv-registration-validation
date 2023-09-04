package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registator = new RegistrationServiceImpl();
    private User user = new User();

    @BeforeEach
    void setUpDefaultUser() {
        user.setLogin("GhostVoice");
        user.setPassword("123456");
        user.setAge(19);
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_ValidUser_Ok() {
        User actual = registator.register(user);
        assertEquals(actual, user);
    }

    @Test
    void register_NullAge_NotOk() {
        user.setAge(null);
        assertThrows(InvalidInputException.class, () -> registator.register(user));
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(InvalidInputException.class, () -> registator.register(null));
    }

    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidInputException.class, () -> registator.register(user));
    }

    @Test
    void register_NullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidInputException.class, () -> registator.register(user));
    }

    @Test
    void register_ShortLogin_NotOk() {
        user.setLogin("Wally");
        assertThrows(InvalidInputException.class, () -> registator.register(user));
    }

    @Test
    void register_ShortPassword_NotOk() {
        user.setPassword("Milky");
        assertThrows(InvalidInputException.class, () -> registator.register(user));
    }

    @Test
    void register_YoungAge_NotOk() {
        user.setAge(12);
        assertThrows(InvalidInputException.class, () -> registator.register(user));
    }

    @Test
    void register_LoginAlreadyExists_NotOk() {
        User existingUser = new User();
        existingUser.setLogin("GhostVoice");
        existingUser.setPassword("123456");
        existingUser.setAge(19);
        Storage.PEOPLE.add(user);
        assertThrows(InvalidInputException.class, () -> registator.register(existingUser));
    }
}
