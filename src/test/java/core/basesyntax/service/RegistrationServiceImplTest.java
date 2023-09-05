package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrator = new RegistrationServiceImpl();

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_ValidUser_Ok() {
        User userToRegister = new User();
        userToRegister.setLogin("GhostVoice");
        userToRegister.setPassword("123456");
        userToRegister.setAge(19);
        User registeredUser = registrator.register(userToRegister);
        assertEquals(userToRegister, registeredUser);
    }

    @Test
    void register_NullLogin_NotOk() {
        User userToRegister = new User();
        userToRegister.setPassword("123456");
        userToRegister.setAge(19);
        assertThrows(InvalidInputException.class, () -> registrator.register(userToRegister));
    }

    @Test
    void register_ShortLogin_NotOk() {
        User userToRegister = new User();
        userToRegister.setLogin("Wally");
        userToRegister.setPassword("123456");
        userToRegister.setAge(19);
        assertThrows(InvalidInputException.class, () -> registrator.register(userToRegister));
    }

    @Test
    void register_LoginAlreadyExists_NotOk() {
        User userToRegister = new User();
        userToRegister.setLogin("GhostVoice");
        userToRegister.setPassword("123456");
        userToRegister.setAge(19);
        Storage.PEOPLE.add(userToRegister);
        assertThrows(InvalidInputException.class, () -> registrator.register(userToRegister));
    }

    @Test
    void register_NullPassword_NotOk() {
        User userToRegister = new User();
        userToRegister.setLogin("GhostVoice");
        userToRegister.setAge(19);
        assertThrows(InvalidInputException.class, () -> registrator.register(userToRegister));
    }

    @Test
    void register_ShortPassword_NotOk() {
        User userToRegister = new User();
        userToRegister.setLogin("GhostVoice");
        userToRegister.setPassword("Milky");
        userToRegister.setAge(19);
        assertThrows(InvalidInputException.class, () -> registrator.register(userToRegister));
    }

    @Test
    void register_NullAge_NotOk() {
        User userToRegister = new User();
        userToRegister.setLogin("GhostVoice");
        userToRegister.setPassword("123456");
        assertThrows(InvalidInputException.class, () -> registrator.register(userToRegister));
    }

    @Test
    void register_YoungAge_NotOk() {
        User userToRegister = new User();
        userToRegister.setLogin("GhostVoice");
        userToRegister.setPassword("123456");
        userToRegister.setAge(12);
        assertThrows(InvalidInputException.class, () -> registrator.register(userToRegister));
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(InvalidInputException.class, () -> registrator.register(null));
    }
}
