package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidDataException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final int ADULT_AGE = 18;
    private static final int ANOTHER_ADULT_AGE = 25;
    private static final int NEGATIVE_AGE = -10;
    private static final int TEEN_AGE = 17;
    private static final int ZERO_AGE = 0;

    private static RegistrationService registrationService;
    private User user;
    private User anotherUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User("MyName", "qwerty", ADULT_AGE);
        anotherUser = new User("UseName", "123abc456def", ANOTHER_ADULT_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_DifferentValidUsers_Ok() {
        User actual = registrationService.register(user);
        assertEquals(actual, user);
        User nextUserToAdd = registrationService.register(anotherUser);
        assertEquals(nextUserToAdd, anotherUser);
    }

    @Test
    void register_underageUser_NotOk() {
        user.setAge(TEEN_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_inadmissibleAge_NotOK() {
        user.setAge(ZERO_AGE);
        anotherUser.setAge(NEGATIVE_AGE);
        assertAll("inAdmissibleAgeTest",
                () -> assertThrows(InvalidDataException.class,
                        () -> registrationService.register(user)),
                () -> assertThrows(InvalidDataException.class,
                        () -> registrationService.register(anotherUser))
        );
    }

    @Test
    void register_shortLogin_NotOk() {
        user.setLogin("NameX");
        anotherUser.setLogin("ox");
        assertAll("shortLoginTest",
                () -> assertThrows(InvalidDataException.class,
                        () -> registrationService.register(user)),
                () -> assertThrows(InvalidDataException.class,
                        () -> registrationService.register(anotherUser))
        );
    }

    @Test
    void register_shortPassword_NotOk() {
        user.setPassword("asd34");
        anotherUser.setPassword("1a");
        assertAll("shortPasswordTest",
                () -> assertThrows(InvalidDataException.class,
                        () -> registrationService.register(user)),
                () -> assertThrows(InvalidDataException.class,
                        () -> registrationService.register(anotherUser))
        );
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_NotOK() {
        user.setPassword("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(InvalidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_duplicateUser_NotOk() {
        Storage.people.add(user);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userWithExistingLogin_NotOk() {
        Storage.people.add(user);
        anotherUser.setLogin("MyName");
        assertThrows(InvalidDataException.class, () -> registrationService.register(anotherUser));
    }
}
