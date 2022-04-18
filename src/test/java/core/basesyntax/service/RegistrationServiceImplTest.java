package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User CAT_USER = new User("cat@gmail.com", "12345678", 40);
    private static final User DOG_USER = new User("dog@gmail.com", "1234567", 21);
    private static final User FROG_USER = new User("frog@gmail.com", "123458", 29);
    private static final User COW_USER = new User("cow@gmail.com", "12345678", 23);
    private static final User BIRD_USER = new User("bird@gmail.com", "12345678", 18);
    private static final User TURTLE_USER = new User("turtle@gmail.com", "123456", 11);
    private static final User SECOND_COW_USER = new User("cow@gmail.com", "134567", 23);
    private static final User FISH_USER = new User("fish@gmail.com", "1234", 52);
    private static final User SNAKE_USER = new User("snake@gmail.com", "12345678", 18);
    private static final User NULL_PASSWORD_USER = new User("null@gmail.com", null, 43);
    private static final User NULL_LOGIN_USER = new User(null, "143493934", 0);
    private static final User NULL_AGE_USER = new User("null@gmail.com", "143493934", null);
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        Storage.people.add(CAT_USER);
        Storage.people.add(DOG_USER);
        Storage.people.add(FROG_USER);
        Storage.people.add(COW_USER);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void userAlreadyWasAdded_NotOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(SECOND_COW_USER));
    }

    @Test
    void userOlder_NotOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(TURTLE_USER));
    }

    @Test
    void passwordLenght_NotOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(FISH_USER));
    }

    @Test
    void registerUser_Ok() {
        registrationService.register(BIRD_USER);
        registrationService.register(SNAKE_USER);
        assertEquals(6, Storage.people.size());
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
