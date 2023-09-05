package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String CORRECT_LOGIN = "GhostVoice";
    private static final String CORRECT_PASSWORD = "123456";
    private static final int CORRECT_AGE = 19;
    private static final String SHORT_LOGIN = "Wally";
    private static final String SHORT_PASSWORD = "Milky";
    private static final int YOUNG_AGE = 12;

    private RegistrationService registrator = new RegistrationServiceImpl();

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_ValidUser_Ok() {
        User userToRegister = new User();
        userToRegister.setLogin(CORRECT_LOGIN);
        userToRegister.setPassword(CORRECT_PASSWORD);
        userToRegister.setAge(CORRECT_AGE);
        User registeredUser = registrator.register(userToRegister);
        assertEquals(userToRegister, registeredUser);
    }

    @Test
    void register_NullAge_NotOk() {
        User userToRegister = new User();
        userToRegister.setLogin(CORRECT_LOGIN);
        userToRegister.setPassword(CORRECT_PASSWORD);
        assertThrows(InvalidInputException.class, () -> registrator.register(userToRegister));
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(InvalidInputException.class, () -> registrator.register(null));
    }

    @Test
    void register_NullLogin_NotOk() {
        User userToRegister = new User();
        userToRegister.setPassword(CORRECT_PASSWORD);
        userToRegister.setAge(CORRECT_AGE);
        assertThrows(InvalidInputException.class, () -> registrator.register(userToRegister));
    }

    @Test
    void register_NullPassword_NotOk() {
        User userToRegister = new User();
        userToRegister.setLogin(CORRECT_LOGIN);
        userToRegister.setAge(CORRECT_AGE);
        assertThrows(InvalidInputException.class, () -> registrator.register(userToRegister));
    }

    @Test
    void register_ShortLogin_NotOk() {
        User userToRegister = new User();
        userToRegister.setLogin(SHORT_LOGIN);
        userToRegister.setPassword(CORRECT_PASSWORD);
        userToRegister.setAge(CORRECT_AGE);
        assertThrows(InvalidInputException.class, () -> registrator.register(userToRegister));
    }

    @Test
    void register_ShortPassword_NotOk() {
        User userToRegister = new User();
        userToRegister.setLogin(SHORT_LOGIN);
        userToRegister.setPassword(SHORT_PASSWORD);
        userToRegister.setAge(CORRECT_AGE);
        assertThrows(InvalidInputException.class, () -> registrator.register(userToRegister));
    }

    @Test
    void register_YoungAge_NotOk() {
        User userToRegister = new User();
        userToRegister.setLogin(SHORT_LOGIN);
        userToRegister.setPassword(SHORT_PASSWORD);
        userToRegister.setAge(YOUNG_AGE);
        assertThrows(InvalidInputException.class, () -> registrator.register(userToRegister));
    }

    @Test
    void register_LoginAlreadyExists_NotOk() {
        User userToRegister = new User();
        userToRegister.setLogin(CORRECT_LOGIN);
        userToRegister.setPassword(CORRECT_PASSWORD);
        userToRegister.setAge(CORRECT_AGE);
        Storage.PEOPLE.add(userToRegister);
        assertThrows(InvalidInputException.class, () -> registrator.register(userToRegister));
    }
}
