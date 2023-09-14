package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_addAndReturnCorrectUser_ok() {
        User correctUser = new User();
        correctUser.setAge(50);
        correctUser.setLogin("CorrectLogin@gmail.login");
        correctUser.setPassword("greatPassword12345");
        User returnedUser = registrationService.register(correctUser);
        User actualAddedUser = Storage.PEOPLE.get(0);
        assertEquals(actualAddedUser, correctUser);
        assertEquals(returnedUser, correctUser);
    }

    @Test
    void register_nullUser_notOk() {
        User nullUser = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(nullUser));
    }

    @Test
    void register_ageIsNull_notOk() {
        User user = new User();
        user.setAge(null);
        user.setLogin("CorrectLogin@gmail.login");
        user.setPassword("greatPassword12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsNull_notOk() {
        User user = new User();
        user.setAge(50);
        user.setLogin(null);
        user.setPassword("greatPassword12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_notOk() {
        User user = new User();
        user.setAge(50);
        user.setLogin("CorrectLogin@gmail.login");
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordAndLoginOfWhitespaces_notOk() {
        User user = new User();
        user.setAge(50);
        user.setLogin("     ");
        user.setPassword("       ");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_isNull_notOk() {
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_existingUser_notOk() {
        User newUser = new User();
        newUser.setAge(50);
        newUser.setPassword("greatPassword12345");
        newUser.setLogin("CorrectLogin@gmail.login");
        Storage.PEOPLE.add(newUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_ageIsLessThan18_notOk() {
        User youngUser = new User();
        youngUser.setAge(2);
        youngUser.setPassword("greatPassword12345");
        youngUser.setLogin("CorrectLogin@gmail.login");
        assertThrows(RegistrationException.class, () -> registrationService.register(youngUser));
    }

    @Test
    void register_negativeAge_notOk() {
        User userWithNegativeAge = new User();
        userWithNegativeAge.setAge(-100);
        userWithNegativeAge.setPassword("greatPassword12345");
        userWithNegativeAge.setLogin("CorrectLogin@gmail.login");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithNegativeAge));
    }

    @Test
    void register_passwordIsUnderSixSymbols() {
        User userWithSmallPassword = new User();
        userWithSmallPassword.setAge(50);
        userWithSmallPassword.setPassword("small");
        userWithSmallPassword.setLogin("CorrectLogin@gmail.login");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithSmallPassword));
    }

    @Test
    void register_loginIsUnderSixSymbols() {
        User userWithSmallLogin = new User();
        userWithSmallLogin.setAge(50);
        userWithSmallLogin.setPassword("greatPassword12345");
        userWithSmallLogin.setLogin("small");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithSmallLogin));
    }
}
