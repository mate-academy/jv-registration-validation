package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NotValidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final String VALID_LOGIN = "CheckLogin";
    private static final String NULL_STRING = null;
    private static final String INCORRECT_LOGIN = "abcd";
    private static final String INCORRECT_PASSWORD = "logi";
    private static final Integer INCORRECT_AGE = 13;
    private static final String VALID_PASSWORD = "CheckPassword";
    private static final Integer NULL_AGE = null;
    private static final int VALID_AGE = 18;
    private static RegistrationService registrationService;

    @BeforeAll
    static void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_User_Ok() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_NullUser_notOk() {

        assertThrows(NotValidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void register_ExistsUser_notOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        Storage.PEOPLE.add(user);
        User user2 = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_NullLogin_notOk() {
        User user = new User(NULL_STRING, VALID_PASSWORD, VALID_AGE);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_IncorrectLogin_notOk() {
        User user = new User(INCORRECT_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullPassword_notOk() {
        User user = new User(VALID_LOGIN, NULL_STRING, VALID_AGE);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_IncorrectPassword_notOk() {
        User user = new User(VALID_LOGIN, INCORRECT_PASSWORD, VALID_AGE);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullAge_notOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, NULL_AGE);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_IncorrectAge_notOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, INCORRECT_AGE);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }
}
