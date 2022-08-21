package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;
    private static List<User> validUsers;
    private static List<User> nullFieldUsers;

    @BeforeAll
    static void beforeAll() {
        validUsers = new ArrayList<>();
        validUsers.add(new User("Sofia", "sofasofa", 24));
        validUsers.add(new User("Bogdan", "bogdanchik", 19));
        validUsers.add(new User("Oleg", "olejik", 27));
        validUsers.add(new User("Ksenia", "kseniyaa", 24));
        validUsers.add(new User("Dima", "demon228", 27));

        nullFieldUsers = new ArrayList<>();
        nullFieldUsers.add(new User(null, "davidbekham", 23));
        nullFieldUsers.add(new User("Anna", null, 30));
        nullFieldUsers.add(new User("Greg", "greg342", null));
    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullFields_NotOk() {
        for (User user : nullFieldUsers) {
            assertThrows(RuntimeException.class, () -> registrationService.register(user));
        }
    }

    @Test
    void register_unValidLogin_NotOk() {
        User valid = new User("Valid", "validpassword", 25);
        assertEquals(valid, registrationService.register(valid));
        User sameLoginUser = new User("Valid", "bogdanchik", 19);
        assertThrows(RuntimeException.class, () -> registrationService.register(sameLoginUser));
        User emptyLoginUser = new User("", "demon228", 27);
        assertThrows(RuntimeException.class, () -> registrationService.register(emptyLoginUser));
    }

    @Test
    void register_fiveValidUsers_Ok() {
        for (User valid : validUsers) {
            assertEquals(valid, registrationService.register(valid));
        }
    }

    @Test
    void register_userIsAlreadyExist_NotOk() {
        User bob = new User("Bob", "bobikbobik", 20);
        registrationService.register(bob);
        assertThrows(RuntimeException.class, () -> registrationService.register(bob));
        User secondBob = new User("Bob", "bobobo123", 19);
        assertThrows(RuntimeException.class, () -> registrationService.register(secondBob));
    }

    @Test
    void register_inputUserIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userAgeIsUnValid_NotOk() {
        User kevin = new User("Kevin", "kevinz12", -23);
        User alice = new User("Alice", "alice321", 16);
        User andriy = new User("Andriy", "andriy2000", 185);
        assertThrows(RuntimeException.class, () -> registrationService.register(kevin));
        assertThrows(RuntimeException.class, () -> registrationService.register(andriy));
        assertThrows(RuntimeException.class, () -> registrationService.register(alice));
    }

    @Test
    void register_userPasswordIsUnValid_NotOk() {
        User john = new User("John", "hello", 35);
        User jeremy = new User("Jeremy", "", 23);
        User johny = new User("Johny", "johnydeppjohnydepp2211", 20);
        assertThrows(RuntimeException.class, () -> registrationService.register(john));
        assertThrows(RuntimeException.class, () -> registrationService.register(jeremy));
        assertThrows(RuntimeException.class, () -> registrationService.register(johny));
    }

    @Test
    void register_LegalAgeUsers_Ok() {
        int minAge = RegistrationServiceImpl.MIN_REGISTRATION_AGE;
        int maxAge = RegistrationServiceImpl.MAX_REGISTRATION_AGE;
        User expected;
        User actual;
        for (int i = minAge; i <= maxAge; i++) {
            expected = new User("User" + i, "validpassword", i);
            actual = registrationService.register(expected);
            assertEquals(expected, actual);
        }
    }
}
