package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.UserInvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
        User sameUser = new User();
        sameUser.setLogin("same_login");
        sameUser.setAge(20);
        sameUser.setPassword("same_password1234");
        Storage.PEOPLE.add(sameUser);
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.remove(user);
    }

    @Test
    void duplicatesLogin_NotOk() {
        user = new User();
        user.setPassword("joker2004");
        user.setLogin("same_login");
        user.setAge(28);
        assertThrows(core.basesyntax.exceptions.UserInvalidDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void shortLogin_NotOk() {
        user = new User();
        user.setPassword("PASSWORD");
        user.setLogin("LOG");
        user.setAge(20);
        assertThrows(core.basesyntax.exceptions.UserInvalidDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void shortPassword_NotOk() {
        user = new User();
        user.setPassword("spell");
        user.setLogin("wizzard89");
        user.setAge(25);
        assertThrows(core.basesyntax.exceptions.UserInvalidDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void youngAge_NotOk() {
        user = new User();
        user.setPassword("Avadacedabra");
        user.setLogin("Harry_Potter");
        user.setAge(15);
        assertThrows(core.basesyntax.exceptions.UserInvalidDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void nullUser_NotOk() {
        assertThrows(core.basesyntax.exceptions.UserInvalidDataException.class,
                () -> registrationService.register(null));
    }

    @Test
    void nullLogin_NotOk() {
        user = new User();
        user.setPassword("VALID_PASSWORD");
        user.setLogin(null);
        user.setAge(20);
        assertThrows(core.basesyntax.exceptions.UserInvalidDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void nullPassword_NotOk() {
        user = new User();
        user.setPassword(null);
        user.setLogin("hoodwink189");
        user.setAge(28);
        assertThrows(core.basesyntax.exceptions.UserInvalidDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void nullAge_NotOk() {
        user = new User();
        user.setPassword("pudgeTheHooker");
        user.setLogin("theStrongestPudge");
        user.setAge(null);
        assertThrows(core.basesyntax.exceptions.UserInvalidDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void emptyLogin_NotOk() {
        user = new User();
        user.setPassword("password_not_null");
        user.setLogin("");
        user.setAge(37);
        assertThrows(core.basesyntax.exceptions.UserInvalidDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void emptyPassword_NotOk() {
        user = new User();
        user.setPassword("");
        user.setLogin("login_not_empty");
        user.setAge(23);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }
}
