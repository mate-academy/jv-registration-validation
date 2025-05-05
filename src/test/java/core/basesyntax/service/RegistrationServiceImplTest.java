package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userIsExist_NotOK() {
        User shevchenko = new User("shevchenko", "password", 25);
        registrationService.register(shevchenko);
        assertThrows(RegistrationException.class, () -> registrationService.register(shevchenko));
    }

    @Test
    void register_nullLoginUser_NotOk() {
        User nullUserLogin = new User(null, "password", 23);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullUserLogin));
    }

    @Test
    void register_nullPasswordUser_NotOk() {
        User nullUserPassword = new User("shevchenko", null, 28);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullUserPassword));
    }

    @Test
    void register_nullAgeUser_NotOk() {
        User nullUserAge = new User("shevchenko", "password", null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullUserAge));
    }

    @Test
    void register_userLoginIsLessThenNeed_NotOk() {
        User lessLogin = new User("us", "password", 20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(lessLogin));
    }

    @Test
    void register_userPasswordIsLessThenNeed_NotOk() {
        User lessPassword = new User("shevchenko", "pass", 21);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(lessPassword));
    }

    @Test
    void register_userAgeIsLessThenNeed_NotOk() {
        User lessAge = new User("shevchenko", "password", 15);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(lessAge));
    }

    @Test
    void register_blankUserLogin_NotOk() {
        User blankLogin = new User(" ", "password", 30);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(blankLogin));
    }

    @Test
    void register_blankUserPassword_NotOk() {
        User blankPassword = new User("shevchenko", " ", 40);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(blankPassword));
    }

    @Test
    void register_userWithNegativeAge_NotOk() {
        User negativeAge = new User("shevchenko", "password", -22);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(negativeAge));
    }

    @Test
    void register_validUser_Ok() {
        User expected = new User("shevchenko", "password", 50);
        assertEquals(expected, registrationService.register(expected));
    }

    @Test
    void register_userEmptyLogin_NotOk() {
        User emptyLogin = new User("", "password", 56);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(emptyLogin));
    }

    @Test
    void register_userEmptyPassword_NotOk() {
        User emptyPassword = new User("shevchenko", "", 88);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(emptyPassword));
    }

    @Test
    void register_userAgeIsMinimum_Ok() {
        User userEighteenAge = new User("shevchenko", "password", 18);
        assertEquals(userEighteenAge, registrationService.register(userEighteenAge));
    }
}
