package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String CORRECT_LOGIN = "GhostVoice";
    private static final String CORRECT_PASSWORD = "123456";
    private static final int CORRECT_AGE = 19;
    private static final String SHORT_LOGIN = "Wally";
    private static final String SHORT_PASSWORD = "Milky";
    private static final int YOUNG_AGE = 12;

    private RegistrationService registrator = new RegistrationServiceImpl();
    private User user = new User();

    @BeforeAll
    static void beforeAll() {

    }

    @BeforeEach
    void setUpDefaultUser() {
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_ValidUser_Ok() {
        User actual = registrator.register(user);
        assertEquals(actual, user);
    }

    @Test
    void register_NullAge_NotOk() {
        user.setAge(null);
        assertThrows(InvalidInputException.class, () -> registrator.register(user));
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(InvalidInputException.class, () -> registrator.register(null));
    }

    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidInputException.class, () -> registrator.register(user));
    }

    @Test
    void register_NullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidInputException.class, () -> registrator.register(user));
    }

    @Test
    void register_ShortLogin_NotOk() {
        user.setLogin(SHORT_LOGIN);
        assertThrows(InvalidInputException.class, () -> registrator.register(user));
    }

    @Test
    void register_ShortPassword_NotOk() {
        user.setPassword(SHORT_PASSWORD);
        assertThrows(InvalidInputException.class, () -> registrator.register(user));
    }

    @Test
    void register_YoungAge_NotOk() {
        user.setAge(YOUNG_AGE);
        assertThrows(InvalidInputException.class, () -> registrator.register(user));
    }

    @Test
    void register_LoginAlreadyExists_NotOk() {
        Storage.PEOPLE.add(user);
        assertThrows(InvalidInputException.class, () -> registrator.register(user));
    }
}
