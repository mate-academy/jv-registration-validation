package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String GOOD_LOGIN = "GoodLogin";
    private static final String GOOD_PASSWORD = "12345678";
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLoginUser_notOk() {
        User userNullLogin = new User(null, "123456", 30);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullLogin));
    }

    @Test
    void register_shortLoginUser_notOk() {
        User userShortLogin1 = new User("12345", GOOD_PASSWORD, 30);
        User userShortLogin2 = new User("123", GOOD_PASSWORD, 30);
        User userEmptyLogin = new User("", GOOD_PASSWORD, 30);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userShortLogin1));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userShortLogin2));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userEmptyLogin));
    }

    @Test
    void register_existingUser_notOk() {
        User existingUser = new User(GOOD_LOGIN, GOOD_PASSWORD, 18);
        User userWithSameLogin = new User(GOOD_LOGIN, "7654321", 30);
        Storage.people.add(existingUser);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithSameLogin));
    }

    @Test
    void register_nullPasswordUser_notOk() {
        User userNullPassword = new User(GOOD_LOGIN, null, 30);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullPassword));
    }

    @Test
    void register_shortPasswordUser_notOk() {
        User userShortPassword1 = new User(GOOD_LOGIN, "12345", 30);
        User userShortPassword2 = new User(GOOD_LOGIN, "123", 30);
        User userEmptyPassword = new User(GOOD_LOGIN, "", 30);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userShortPassword1));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userShortPassword2));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userEmptyPassword));
    }

    @Test
    void register_nullAgeUser_notOk() {
        User userNullAge = new User(GOOD_LOGIN, "123456", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(userNullAge));
    }

    @Test
    void register_invalidAgeUser_notOk() {
        User userNegativeAge = new User(GOOD_LOGIN, GOOD_PASSWORD, -1);
        User userZeroAge = new User(GOOD_LOGIN, GOOD_PASSWORD, 0);
        User userNotAdult = new User(GOOD_LOGIN, GOOD_PASSWORD, 5);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNegativeAge));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userZeroAge));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNotAdult));
        User userNotAdult2 = new User(GOOD_LOGIN, GOOD_PASSWORD, 17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNotAdult2));
    }

    @Test
    void register_goodUserExample_Ok() {
        User userGoodParameters1 = new User("Good123", "123456", 18);
        User userGoodParameters2 = new User("123456", GOOD_PASSWORD, 19);
        User userGoodParameters3 = new User(GOOD_LOGIN, "123456", 100);
        User actualUser1 = registrationService.register(userGoodParameters1);
        User actualUser2 = registrationService.register(userGoodParameters2);
        User actualUser3 = registrationService.register(userGoodParameters3);
        assertEquals(userGoodParameters1, actualUser1);
        assertEquals(userGoodParameters2, actualUser2);
        assertEquals(userGoodParameters3, actualUser3);
    }
}
