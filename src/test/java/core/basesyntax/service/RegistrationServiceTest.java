package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.UserInvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @DisplayName("Duplicate login registration test")
    @Test
    void register_duplicatesLogin_NotOk() {
        User sameUser = new User();
        sameUser.setLogin("same_login");
        sameUser.setAge(20);
        sameUser.setPassword("same_password1234");
        Storage.PEOPLE.add(sameUser);
        User user = new User();
        user.setPassword("joker2004");
        user.setLogin("same_login");
        user.setAge(28);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }

    @DisplayName("Tests for invalid user fields")
    @Test
    void shortLogin_NotOk() {
        User user = new User();
        user.setPassword("PASSWORD");
        user.setLogin("LOG");
        user.setAge(20);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void shortPassword_NotOk() {
        User user = new User();
        user.setPassword("spell");
        user.setLogin("wizzard89");
        user.setAge(25);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void youngAge_NotOk() {
        User user = new User();
        user.setPassword("Avadacedabra");
        user.setLogin("Harry_Potter");
        user.setAge(17);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }

    @DisplayName("Tests for null user fields")
    @Test
    void nullUser_NotOk() {
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void nullLogin_NotOk() {
        User user = new User();
        user.setPassword("VALID_PASSWORD");
        user.setLogin(null);
        user.setAge(20);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void nullPassword_NotOk() {
        User user = new User();
        user.setPassword(null);
        user.setLogin("hoodwink189");
        user.setAge(28);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void nullAge_NotOk() {
        User user = new User();
        user.setPassword("pudgeTheHooker");
        user.setLogin("theStrongestPudge");
        user.setAge(null);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }

    @DisplayName("Tests for empty user fields")
    @Test
    void emptyLogin_NotOk() {
        User user = new User();
        user.setPassword("password_not_null");
        user.setLogin("");
        user.setAge(37);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void emptyPassword_NotOk() {
        User user = new User();
        user.setPassword("");
        user.setLogin("login_not_empty");
        user.setAge(23);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }
}
