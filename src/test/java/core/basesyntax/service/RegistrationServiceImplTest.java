package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;
    private static List<User> validUsers;
    private static List<User> unValidUsers;
    private static List<User> nullFieldUsers;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();

        validUsers = new ArrayList<>();
        validUsers.add(new User(24L, "Sofia", "sofasofa", 25));
        validUsers.add(new User(735L, "Bogdan", "bogdanchik", 19));
        validUsers.add(new User(65449L, "Oleg", "olejik", 27));
        validUsers.add(new User(1649L, "Ksenia", "kseniyaa", 26));
        validUsers.add(new User(975L, "Dima", "demon228", 27));

        unValidUsers = new ArrayList<>();
        unValidUsers.add(new User(37L, "Valid", "validpassword", 25));
        unValidUsers.add(new User(7305L, "Valid", "bogdanchik", 19));
        unValidUsers.add(new User(679L, "Oleg", "olejik", 17));
        unValidUsers.add(new User(169L, "Ksenia", "ksyu", 26));
        unValidUsers.add(new User(856L, "", "demon228", 27));
        unValidUsers.add(new User(-234L, "Peter", "peterparker", 25));

        nullFieldUsers = new ArrayList<>();
        nullFieldUsers.add(new User(null, "Bony", "bonybony", 27));
        nullFieldUsers.add(new User(62345L, null, "davidbekham", 23));
        nullFieldUsers.add(new User(73L,"Anna", null, 30));
        nullFieldUsers.add(new User(623L, "Greg", "greg342", null));
    }

    @Test
    void register_negativeAge_NotOk() {
        User bob = new User(51323L, "Bob", "bobobob", -19);
        assertThrows(RuntimeException.class, () -> registrationService.register(bob));
    }

    @Test
    void register_nullFields_NotOk() {
        for (User user : nullFieldUsers) {
            assertThrows(RuntimeException.class, () -> registrationService.register(user));
        }
    }

    @Test
    void register_unValidUsers_NotOk() {
        assertEquals(unValidUsers.get(0), registrationService.register(unValidUsers.get(0)));
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(unValidUsers.get(1));
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(unValidUsers.get(2));
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(unValidUsers.get(3));
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(unValidUsers.get(4));
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(unValidUsers.get(5));
        });
    }

    @Test
    void register_fiveValidUsers_Ok() {
        for (User valid : validUsers) {
            assertEquals(valid, registrationService.register(valid));
        }
    }

    @Test
    void register_userIsAlreadyExist_NotOk() {
        User bob = new User(5436L, "Bob", "bobikbobik", 20);
        registrationService.register(bob);
        assertThrows(RuntimeException.class, () -> registrationService.register(bob));
    }

    @Test
    void register_inputUserIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userAgeIsIllegal_NotOk() {
        User alice = new User(1124L, "Alice", "alice321", 16);
        User andriy = new User(5452L, "Andriy", "andriy2000", 185);
        assertThrows(RuntimeException.class, () -> registrationService.register(andriy));
        assertThrows(RuntimeException.class, () -> registrationService.register(alice));
    }

    @Test
    void register_userPasswordIsUnValid_NotOk() {
        User john = new User(8678L, "John", "hello", 35);
        User jeremy = new User(73745L, "Jeremy", "", 23);
        assertThrows(RuntimeException.class, () -> registrationService.register(john));
        assertThrows(RuntimeException.class, () -> registrationService.register(jeremy));
    }

    @Test
    void register_fewNullFields() {
        User becky = new User(970L, "Becky", null, null);
        User simon = new User(null, "Simon", "simon2000", null);
        User frida = new User(null, null, "fridayfrida", null);
        assertThrows(RuntimeException.class, () -> registrationService.register(becky));
        assertThrows(RuntimeException.class, () -> registrationService.register(simon));
        assertThrows(RuntimeException.class, () -> registrationService.register(frida));

    }
}
