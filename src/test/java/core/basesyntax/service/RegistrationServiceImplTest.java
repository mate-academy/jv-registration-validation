package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User FIRST_VALID_USER = new User("cat@gmail.com", "12345678", 40);
    private static final User SECOND_VALID_USER = new User("cow@gmail.com", "12345678", 23);
    private static final User THIRD_VALID_USER = new User("bird@gmail.com", "12345678", 18);
    private static final User FOURTH_VALID_USER = new User("snake@gmail.com", "12345678", 18);
    private static final User NO_VALID_AGE_USER = new User("turtle@gmail.com", "123456", 11);
    private static final User SECOND_REPEAT_USER = new User("cow@gmail.com", "134567", 23);
    private static final User NO_VALID_PASSWORD_USER = new User("fish@gmail.com", "1234", 52);
    private static final User NULL_PASSWORD_USER = new User("null@gmail.com", null, 43);
    private static final User NULL_LOGIN_USER = new User(null, "143493934", 0);
    private static final User NULL_AGE_USER = new User("null@gmail.com", "143493934", null);
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeAll
    static void beforeAll() {
        Storage.people.add(FIRST_VALID_USER);
        Storage.people.add(SECOND_VALID_USER);
    }

    @AfterEach
    void cleanUp() {
        Storage.people.clear();
    }

    @Test
    void register_userAlreadyWasAdded_NotOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(SECOND_REPEAT_USER));
    }

    @Test
    void userOlder_NotOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(NO_VALID_AGE_USER));
    }

    @Test
    void passwordLenght_NotOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(NO_VALID_PASSWORD_USER));
    }

    @Test
    void registerUser_Ok() {
        registrationService.register(THIRD_VALID_USER);
        registrationService.register(FOURTH_VALID_USER);
        assertEquals(2, Storage.people.size());
    }

    @Test
    void passwordIsNull_NotOk() {
        assertThrows(NullPointerException.class,
                () -> registrationService.register(NULL_PASSWORD_USER));
    }

    @Test
    void loginIsNull_NotOk() {
        assertThrows(NullPointerException.class,
                () -> registrationService.register(NULL_LOGIN_USER));
    }

    @Test
    void ageIsNull_NotOk() {
        assertThrows(NullPointerException.class,
                () -> registrationService.register(NULL_AGE_USER));
    }

    @Test
    void userIsNull_NotOk() {
        assertThrows(NullPointerException.class,
                () -> registrationService.register(null));
    }
}
